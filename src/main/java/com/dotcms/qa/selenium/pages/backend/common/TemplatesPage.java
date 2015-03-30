package com.dotcms.qa.selenium.pages.backend.common;

import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.dotcms.qa.selenium.pages.backend.IContainerAddOrEditPage;
import com.dotcms.qa.selenium.pages.backend.ITemplateAddOrEditAdvanceTemplatePage;
import com.dotcms.qa.selenium.pages.backend.ITemplateAddOrEditDesignTemplatePage;
import com.dotcms.qa.selenium.pages.backend.ITemplatesPage;
import com.dotcms.qa.selenium.pages.common.BasePage;
import com.dotcms.qa.selenium.util.SeleniumPageManager;
/**
 * This class implements the methods defined in the ITemplatesPage interface
 * to do the Users TestRail validations
 * @author Oswaldo Gallango
 * @since 01/22/2015
 * @version 1.0
 * 
 */
public class TemplatesPage extends BasePage implements ITemplatesPage {
	private static final Logger logger = Logger.getLogger(StructuresPage.class);

	/**
	 * Templates change host
	 */
	private WebElement changeHostId;
	private WebElement subNavHost;
	private WebElement fm_publish;

	public TemplatesPage(WebDriver driver) {
		super(driver);
	}

	/**
	 * Return the number of templates created for that host 
	 * @param hostName Name of the Host
	 * @return int 
	 * @throws Exception
	 */
	public int getNumberOfHostTemplates(String hostName) throws Exception{
		int retValue = 0;
		changeHostId.click();
		subNavHost.clear();
		subNavHost.sendKeys(hostName);
		subNavHost.sendKeys(Keys.RETURN);
		sleep(2);
		List<WebElement> templateViewingArea = fm_publish.findElement(By.className("buttonRow")).findElements(By.tagName("div"));
		for(WebElement div : templateViewingArea){
			String value = div.getAttribute("innerHTML");
			if(value.indexOf(getLocalizedString("Viewing")) != -1){
				value = value.substring(value.indexOf(getLocalizedString("of1")));
				value =value.replace(getLocalizedString("of1"), "").trim();
				retValue = Integer.parseInt(value);
				break;
			}
		}
		return retValue;
	}

	/**
	 * Add a new advance template
	 * @return ITemplateAddOrEditAdvanceTemplatePage
	 * @throws Exception
	 */
	public ITemplateAddOrEditAdvanceTemplatePage addAdvanceTemplate() throws Exception{
		boolean found = false;
		WebElement toolbardiv = getWebElement(By.cssSelector("form[id='fm']")).findElement(By.cssSelector("div[class='yui-gc portlet-toolbar']"));
		List<WebElement> spans= toolbardiv.findElements(By.cssSelector("span[class='dijitReset dijitInline dijitButtonNode']"));
		for(WebElement span : spans){
			if(span.getText().equals(getLocalizedString("Add-Template"))){
				span.click();
				sleep(4);
				for(WebElement option : getWebElementsPresent(By.cssSelector("td[class='dijitReset dijitMenuItemLabel']"))){
					if(option.getText().trim().equals(getLocalizedString("code-template"))){
						option.click();
						sleep(2);
						found=true;
						break;
					}
				}
				break;
			}
		}
		if(!found){
			throw new Exception("Add advance template button not found"); 
		}
		return SeleniumPageManager.getBackEndPageManager().getPageObject(ITemplateAddOrEditAdvanceTemplatePage.class);
	}

	/**
	 * Add a new design template
	 * @return ITemplateAddOrEditDesignTemplatePage
	 * @throws Exception
	 */
	public ITemplateAddOrEditDesignTemplatePage addDesignTemplate() throws Exception{
		boolean found=false;
		List<WebElement> spans = getWebElement(By.cssSelector("form[id='fm']")).findElement(By.cssSelector("div[class='yui-gc portlet-toolbar']")).findElements(By.cssSelector("span[class='dijitReset dijitInline dijitButtonNode']"));
		for(WebElement span : spans){
			if(span.getText().equals(getLocalizedString("Add-Template"))){
				span.click();
				for(WebElement option : getWebElements(By.cssSelector("td[class='dijitReset dijitMenuItemLabel']"))){
					if(option.getText().trim().equals(getLocalizedString("design-template"))){
						option.click();
						found=true;
						sleep(2);
						break;
					}
				}
				break;
			}
		}
		if(!found){
			throw new Exception("Add design template button not found"); 
		}
		return SeleniumPageManager.getBackEndPageManager().getPageObject(ITemplateAddOrEditDesignTemplatePage.class);
	}

	/**
	 * Validate if a template exist
	 * @param templateName Name of the template
	 * @return true if the template exist, false if not
	 * @throws Exception
	 */
	public boolean doesTemplateExist(String templateName) throws Exception{
		boolean exist = false;
		WebElement searchBox = getWebElement(By.cssSelector("form[id='fm']")).findElement(By.name("query"));
		searchBox.clear();
		searchBox.sendKeys(templateName);

		List<WebElement> buttons = getWebElement(By.cssSelector("form[id='fm']")).findElements(By.cssSelector("span[class='dijitReset dijitInline dijitButtonText']"));
		for(WebElement button : buttons){
			if(button.getText().trim().equals(getLocalizedString("search"))){
				button.click();
				break;
			}
		}
		sleep(2);
		List<WebElement> results = getWebElement(By.cssSelector("form[id='fm_publish']")).findElement(By.cssSelector("table[class='listingTable']")).findElements(By.tagName("tr"));
		for(WebElement row : results){
			List<WebElement> columns = row.findElements(By.tagName("td"));
			if(columns.size()> 2){
				if(columns.get(1).getText().trim().equals(templateName)){
					exist=true;
					break;
				}
			}
		}
		return exist;
	}

	/**
	 * delete a template
	 * @param templateName Name of the template
	 * @throws Exception
	 */
	public void deleteTemplate(String templateName) throws Exception{
		boolean exist = false;
		WebElement searchBox = getWebElement(By.cssSelector("form[id='fm']")).findElement(By.name("query"));
		searchBox.clear();
		searchBox.sendKeys(templateName);

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
				if(columns.get(1).getText().trim().equals(templateName)){
					columns.get(0).findElement(By.cssSelector("input[type='checkbox']")).click();
					break;
				}
			}
		}

		getWebElement(By.id("deleteButton_label")).click();
		switchToAlert().accept();
	}
	
	/**
	 * Click the push publish option from the right click menu options
	 * @param templateName Template Name
	 * @throws Exception
	 */
	public void pushTemplate(String templateName) throws Exception{
		WebElement container = findTemplateRow(templateName);
		List<WebElement> columns = container.findElements(By.tagName("td"));
		selectRightClickPopupMenuOption(columns.get(1),getLocalizedString("Remote-Publish"));
		sleep(2);
		WebElement remotePublishBundleDialog = getWebElement(By.id("remotePublisherDia"));
		remotePublishBundleDialog.findElement(By.id("remotePublishSaveButton")).click();
	}
	
	/**
	 * Get a template row 
	 * @param templateName Name of the template
	 * @return WebElement
	 * @throws Exception
	 */
	private WebElement findTemplateRow(String templateName) throws Exception{
		WebElement templateRow=null;
		WebElement searchBox = getWebElement(By.cssSelector("form[id='fm']")).findElement(By.name("query"));
		searchBox.clear();
		searchBox.sendKeys(templateName);

		List<WebElement> buttons = getWebElement(By.cssSelector("form[id='fm']")).findElements(By.cssSelector("span[class='dijitReset dijitInline dijitButtonText']"));
		for(WebElement button : buttons){
			if(button.getText().trim().equals(getLocalizedString("search"))){
				button.click();
				break;
			}
		}
		sleep(2);
		List<WebElement> results = getWebElement(By.cssSelector("form[id='fm_publish']")).findElement(By.cssSelector("table[class='listingTable']")).findElements(By.tagName("tr"));
		for(WebElement row : results){
			List<WebElement> columns = row.findElements(By.tagName("td"));
			if(columns.size()> 2){
				if(columns.get(1).getText().trim().equals(templateName)){
					templateRow=row;
					break;
				}
			}
		}
		
		return templateRow;
	}

}
