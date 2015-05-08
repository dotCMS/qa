package com.dotcms.qa.selenium.pages.backend.common;


import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.dotcms.qa.selenium.pages.backend.IContainerAddOrEditPage;
import com.dotcms.qa.selenium.pages.backend.IContainersPage;
import com.dotcms.qa.selenium.pages.backend.IPushPublishDialogPage;
import com.dotcms.qa.selenium.pages.common.BasePage;
import com.dotcms.qa.selenium.util.SeleniumPageManager;
import com.dotcms.qa.util.WebKeys;

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
		selectRightClickPopupMenuOption(columns.get(1),getLocalizedString("Add-To-Bundle"));
		getWebElement(By.id("addToBundleDia")).findElement(By.id("bundleSelect")).clear();
		getWebElement(By.id("addToBundleDia")).findElement(By.id("bundleSelect")).sendKeys(bundleName);
		getWebElement(By.id("addToBundleDia")).findElement(By.id("addToBundleSaveButton_label")).click();

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
		div.findElement(By.id("dijit_form_TextBox_0")).clear();
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
	
	/**
	 * Delete the specified container
	 * @param containerName Name of the container
	 * @throws Exception
	 */
	public void deleteContainer(String containerName) throws Exception{
		WebElement containerRow = findContainerRow(containerName);
		List<WebElement> columns = containerRow.findElements(By.tagName("td"));
		WebElement checkbox = columns.get(0).findElement(By.cssSelector("input[class='dijitReset dijitCheckBoxInput']"));
		checkbox.click();
		sleep(2);
		getWebElement(By.id("deleteButton_label")).click();
		switchToAlert().accept();
		sleep(2);
	}
	
	/**
	 * Validate if the specified container exist
	 * @param containerName Name of the container
	 * @return true if the containers exist, false if not
	 * @throws Exception
	 */
	public boolean existContainer(String containerName) throws Exception{
		boolean exist=false;
		WebElement containerRow = findContainerRow(containerName);
		if(containerRow != null){
			exist=true;
		}
		return exist;
	}
	
	/**
	 * Returns the edit container page
	 * @param containerName Container Name
	 * @return IContentAddOrEdit_ContentPage
	 * @throws Exception
	 */
	public IContainerAddOrEditPage getEditContainerPage(String containerName) throws Exception{
		WebElement container = findContainerRow(containerName);
		List<WebElement> columns = container.findElements(By.tagName("td"));
		columns.get(1).click();
		return SeleniumPageManager.getBackEndPageManager().getPageObject(IContainerAddOrEditPage.class);
	}
	
	/**
	 * Click the push publish option from the right click menu options
	 * @param containerName Container Name
	 * @throws Exception
	 */
	public void pushContainer(String containerName) throws Exception{
		WebElement container = findContainerRow(containerName);
		List<WebElement> columns = container.findElements(By.tagName("td"));
		selectRightClickPopupMenuOption(columns.get(1),getLocalizedString("Remote-Publish"));
		sleep(2);
		IPushPublishDialogPage pushingDialog = SeleniumPageManager.getBackEndPageManager().getPageObject(IPushPublishDialogPage.class);
		pushingDialog.push(WebKeys.PUSH_TO_ADD, null, null, null, null, false);
	}
}
