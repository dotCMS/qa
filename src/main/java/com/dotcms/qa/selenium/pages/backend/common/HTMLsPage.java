package com.dotcms.qa.selenium.pages.backend.common;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.dotcms.qa.selenium.pages.backend.IHTMLsPage;
import com.dotcms.qa.selenium.pages.common.BasePage;

/**
 * This class implements the methods defined in the IHTMLsPage interface
 * @author Oswaldo Gallango
 * @since 03/24/2015
 * @version 1.0
 * 
 */
public class HTMLsPage extends BasePage implements IHTMLsPage{

	public HTMLsPage(WebDriver driver) {
		super(driver);
	}

	/**
	 * Validates if a page exist
	 * @param pageName Page title
	 * @param templateName Template title
	 * @return true if the page exist, false if not
	 * @throws Exception
	 */
	public boolean doesHTMLPageExist(String pageName, String templateName) throws Exception{
		boolean exist=false;
		WebElement row =  findHTMLPageRow( pageName,  templateName);
		List<WebElement> columns = row.findElements(By.tagName("td"));
		if(columns.size() > 2){
			if(columns.get(1).getText().trim().equals(pageName)){
				exist=true;
			}
		}
		
		return exist;
	}
	
	/**
	 * Click the push publish option from the right click menu options
	 * @param pageName HTML page Name
	 * @throws Exception
	 */
	public void pushHTMLPage(String pageName) throws Exception{
		WebElement htmlpage = findHTMLPageRow(pageName,null);
		List<WebElement> columns = htmlpage.findElements(By.tagName("td"));
		selectRightClickPopupMenuOption(columns.get(1),getLocalizedString("Remote-Publish"));
		sleep(2);
		WebElement remotePublishBundleDialog = getWebElement(By.id("remotePublisherDia"));
		remotePublishBundleDialog.findElement(By.id("remotePublishSaveButton")).click();
	}
	
	/**
	 * Get the page row in the result list
	 * @param pageName      Page name
	 * @param templateName  Template name
	 * @return WebElement
	 * @throws Exception
	 */
	private WebElement findHTMLPageRow(String pageName, String templateName) throws Exception{
		WebElement pageRow=null;
		if(templateName != null && templateName !=""){
			WebElement searchTemplate = getWebElement(By.id("template"));
			searchTemplate.clear();
			searchTemplate.sendKeys(templateName);
			getWebElement(By.id("template_popup0")).click();
		}
		WebElement searchHTML = getWebElement(By.name("query"));
		searchHTML.clear();
		searchHTML.sendKeys(pageName);
		List<WebElement> buttons = getWebElement(By.cssSelector("form[id='fm']")).findElements(By.cssSelector("span[class='dijitReset dijitInline dijitButtonText']"));
		for(WebElement button : buttons){
			if(button.getText().trim().equals(getLocalizedString("search"))){
				button.click();
				break;
			}
		}
		List<WebElement> results = getWebElement(By.cssSelector("form[id='fm_publish']")).findElement(By.cssSelector("table[class='listingTable']")).findElements(By.tagName("tr"));
		for(WebElement row : results){
			List<WebElement> columns = row.findElements(By.tagName("td"));
			if(columns.size() > 2){
				if(columns.get(1).getText().trim().equals(pageName)){
					pageRow=row;
					break;
				}
			}
		}
		return pageRow;
	}
}
