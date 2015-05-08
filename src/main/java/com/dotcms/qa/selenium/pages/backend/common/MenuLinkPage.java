package com.dotcms.qa.selenium.pages.backend.common;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.dotcms.qa.selenium.pages.backend.IContainerAddOrEditPage;
import com.dotcms.qa.selenium.pages.backend.IMenuLinkAddOrEdit_Page;
import com.dotcms.qa.selenium.pages.backend.IMenuLinkPage;
import com.dotcms.qa.selenium.pages.backend.IPushPublishDialogPage;
import com.dotcms.qa.selenium.pages.common.BasePage;
import com.dotcms.qa.selenium.util.SeleniumPageManager;
import com.dotcms.qa.util.Evaluator;
import com.dotcms.qa.util.WebKeys;

/**
 * This class implements the methods defined in the IMenuLinkPage interface
 * @author Oswaldo Gallango
 * @since 04/14/2015
 * @version 1.0
 * 
 */
public class MenuLinkPage extends BasePage implements IMenuLinkPage{

	private String linkName=null;
	
	public MenuLinkPage(WebDriver driver) {
		super(driver);
	}

	/**
	 * Click the add link button and return IMenuLinkAddOrEdit_Page
	 * @return IMenuLinkAddOrEdit_Page
	 * @throws Exception
	 */
	public IMenuLinkAddOrEdit_Page addLink() throws Exception{
		sleep(2);
		List<WebElement> buttons = getWebElement(By.cssSelector("form[id='fm']")).findElement(By.cssSelector("div[class='yui-gc portlet-toolbar']")).findElements(By.cssSelector("span[class='dijitReset dijitInline dijitButtonText']"));
		for(WebElement button : buttons){
			if(button.getText().trim().equals(getLocalizedString("add-link"))){
				button.click();
				sleep(2);
				break;
			}
		}
		return SeleniumPageManager.getBackEndPageManager().getPageObject(IMenuLinkAddOrEdit_Page.class);
	}

	/**
	 * Validate if a link exist
	 * @param linkTitle Link Name
	 * @return true if exist, false if not
	 * @throws Exception
	 */
	public boolean doesLinkExist(String linkTitle) throws Exception{
		linkName = linkTitle;
		Evaluator eval = new Evaluator() {
			public boolean evaluate() throws Exception {  
				boolean found=false;
				reload();
				WebElement link = findLinkRow(linkName);
				if(link != null){
					found=true;
				}
				return found;
			}
		};
		return pollForValue(eval, true, 2000, 5);		
	}

	/**
	 * Click the push publish option from the right click menu options
	 * @param linkTitle Link Name
	 * @throws Exception
	 */
	public void pushLink(String linkTitle) throws Exception{
		WebElement link = findLinkRow(linkTitle);
		List<WebElement> columns = link.findElements(By.tagName("td"));
		selectRightClickPopupMenuOption(columns.get(1),getLocalizedString("Remote-Publish"));
		sleep(2);
		IPushPublishDialogPage pushingDialog = SeleniumPageManager.getBackEndPageManager().getPageObject(IPushPublishDialogPage.class);
		pushingDialog.push(WebKeys.PUSH_TO_ADD, null, null, null, null, false);
	}

	/**
	 * Get the link row Web Element if exist
	 * @param linkTitle Link Title
	 * @return WebElement
	 * @throws Exception
	 */
	private WebElement findLinkRow(String linkTitle) throws Exception{
		WebElement link = null;
		List<WebElement> linkRows = getWebElement(By.cssSelector("table[class='listingTable']")).findElements(By.tagName("tr"));
		for(WebElement row : linkRows){
			List<WebElement> columns = row.findElements(By.tagName("td"));
			if(columns.size() > 1){
				if(columns.get(1).getText().trim().equals(linkTitle)){
					link=row;
					break;
				}
			}
		}
		return link;
	}
	
	/**
	 * Delete a menu link
	 * @param linkTitle Link Name
	 * @throws Exception
	 */
	public void deleteLink(String linkTitle) throws Exception{
		WebElement link = findLinkRow(linkTitle);
		List<WebElement> columns = link.findElements(By.tagName("td"));
		WebElement checkbox = columns.get(0).findElement(By.cssSelector("input[class='dijitReset dijitCheckBoxInput']"));
		checkbox.click();
		sleep(2);
		getWebElement(By.id("deleteButton_label")).click();
		switchToAlert().accept();
		sleep(3);
	}
	
	/**
	 * Edit a menu link
	 * @param linkTitle Link Name
	 * @return IMenuLinkAddOrEdit_Page
	 * @throws Exception
	 */
	public IMenuLinkAddOrEdit_Page editLink(String linkTitle) throws Exception{
		WebElement link = findLinkRow(linkTitle);
		List<WebElement> columns = link.findElements(By.tagName("td"));
		columns.get(1).click();
		return SeleniumPageManager.getBackEndPageManager().getPageObject(IMenuLinkAddOrEdit_Page.class);
	}
	
	/**
	 * Add the menu link to a particular bundle 
	 * @param linkName   Name of the link
	 * @param bundleName      Name of the bundle
	 * @throws Exception
	 */
	public void addToBundle(String linkName, String bundleName) throws Exception{
		WebElement linkRow = findLinkRow(linkName);
		List<WebElement> columns = linkRow.findElements(By.tagName("td"));
		selectRightClickPopupMenuOption(columns.get(1),getLocalizedString("Add-To-Bundle"));
		getWebElement(By.id("addToBundleDia")).findElement(By.id("bundleSelect")).clear();
		getWebElement(By.id("addToBundleDia")).findElement(By.id("bundleSelect")).sendKeys(bundleName);
		getWebElement(By.id("addToBundleDia")).findElement(By.id("addToBundleSaveButton_label")).click();

	}
}
