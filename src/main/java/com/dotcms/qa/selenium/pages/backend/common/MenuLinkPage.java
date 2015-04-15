package com.dotcms.qa.selenium.pages.backend.common;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.dotcms.qa.selenium.pages.backend.IContainerAddOrEditPage;
import com.dotcms.qa.selenium.pages.backend.IMenuLinkAddOrEdit_Page;
import com.dotcms.qa.selenium.pages.backend.IMenuLinkPage;
import com.dotcms.qa.selenium.pages.common.BasePage;
import com.dotcms.qa.selenium.util.SeleniumPageManager;

/**
 * This class implements the methods defined in the IMenuLinkPage interface
 * @author Oswaldo Gallango
 * @since 04/14/2015
 * @version 1.0
 * 
 */
public class MenuLinkPage extends BasePage implements IMenuLinkPage{

	public MenuLinkPage(WebDriver driver) {
		super(driver);
	}

	/**
	 * Click the add link button and return IMenuLinkAddOrEdit_Page
	 * @return IMenuLinkAddOrEdit_Page
	 * @throws Exception
	 */
	public IMenuLinkAddOrEdit_Page addLink() throws Exception{
		List<WebElement> buttons = getWebElement(By.cssSelector("div[class='yui-gc portlet-toolbar']")).findElements(By.cssSelector("span[class='dijitReset dijitInline dijitButtonText']"));
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
		boolean found=false;
		WebElement link = findLinkRow(linkTitle);
		if(link != null){
			found=true;
		}
		return found;
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
		WebElement remotePublishBundleDialog = getWebElement(By.id("remotePublisherDia"));
		remotePublishBundleDialog.findElement(By.id("remotePublishSaveButton")).click();
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
		sleep(2);
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
}
