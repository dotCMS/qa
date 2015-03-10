package com.dotcms.qa.selenium.pages.backend.common;


import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.dotcms.qa.selenium.pages.backend.IContainerAddOrEditPage;
import com.dotcms.qa.selenium.pages.backend.IContainersPage;
import com.dotcms.qa.selenium.pages.common.BasePage;
import com.dotcms.qa.selenium.util.SeleniumPageManager;

/**
 * This class implements the methods defined in the IContainersPage interface
 * @author Oswaldo Gallango
 * @since 03/10/2015
 * @version 1.0
 * 
 */
public class ContainersPage extends BasePage implements IContainersPage {

	private static final Logger logger = Logger.getLogger(ContainersPage.class);
	
	public ContainersPage(WebDriver driver) {
		super(driver);
	}

	/**
	 * Returns the add a new container page
	 * @return IContentAddOrEdit_ContentPage
	 * @throws Exception
	 */
	public IContainerAddOrEditPage getAddContainerPage() throws Exception{
		
		List<WebElement> spans = getWebElement(By.cssSelector("div[class='yui-gc portlet-toolbar']")).findElements(By.cssSelector("span[class='dijitReset dijitInline dijitButtonNode']"));
		for(WebElement span : spans){
			if(span.getText().equals(getLocalizedString("add-container"))){
				span.click();
				break;
			}
		}
		return SeleniumPageManager.getBackEndPageManager().getPageObject(IContainerAddOrEditPage.class);
	}
	
	/**
	 * Add the container to a particular bundle 
	 * @param containerName   Name of the container
	 * @param bundleName      Name of the bundle
	 * @throws Exception
	 */
	public void addToBundle(String containerName, String bundleName) throws Exception{
		WebElement containerRow = findContainerRow(containerName);
		List<WebElement> columns = containerRow.findElements(By.tagName("td"));
		 selectPopupMenuOption(columns.get(1),getLocalizedString("Add-To-Bundle"));
		 getWebElement(By.id("addToBundleDia")).findElement(By.id("bundleSelect")).sendKeys(bundleName);
		 getWebElement(By.id("addToBundleDia")).findElement(By.id("addToBundleSaveButton_label")).click();
		 
	}
	
	private boolean selectPopupMenuOption(WebElement elem, String menuOption) throws Exception {
		boolean foundValue = false;
		sleep(1);
		rightClickElement(elem);	
		WebElement popupMenu = getWebElementClickable(By.className("dijitMenuPopup"));
		//this.hoverOverElement(popupMenu);
		List<WebElement> rows = popupMenu.findElements(By.tagName("tr"));
		WebElement prevRow = null;
		for(WebElement row : rows) {
			if(prevRow != null) {
				logger.debug("* prevRow.isDisplayed() = " + prevRow.isDisplayed());
				logger.debug("* prevRow.isEnabled() = " + prevRow.isEnabled());
			}
			logger.debug("* isDisplayed() = " + row.isDisplayed());
			logger.debug("* isEnabled() = " + row.isEnabled());
			List<WebElement> labels = row.findElements(By.className("dijitMenuItemLabel"));
			for(WebElement label : labels) {
				logger.debug("label innerHTML = |" + label.getAttribute("innerHTML") + "|");
				if(label.getAttribute("innerHTML").trim().startsWith(menuOption)) {
					this.hoverOverElement(label);
					getWebElementClickable(label).click();
					foundValue = true;
					break;
				}
			}
			if(foundValue)
				break;
			prevRow = row;
		}
		return foundValue;
	}
	
	/**
	 * Get the container row
	 * @param containerName
	 * @return
	 * @throws Exception
	 */
	public WebElement findContainerRow(String containerName) throws Exception{
		WebElement containerRow = null;
		WebElement div = getWebElement(By.cssSelector("div[class='yui-gc portlet-toolbar']"));
		div.findElement(By.id("dijit_form_TextBox_0")).sendKeys(containerName);
		div.findElement(By.cssSelector("span[class='dijitReset dijitInline dijitButtonText']")).click();
		sleep(3);
		List<WebElement> rows = getWebElement(By.cssSelector("table[class='listingTable']")).findElements(By.tagName("tr"));
		for(WebElement row : rows){
			List<WebElement> columns = row.findElements(By.tagName("td"));
			if(columns.size() >= 4){
				if(columns.get(1).getText().trim().equals(containerName)){
					containerRow=row;
					break;
				}
			}
		}
		return containerRow;
	}
}
