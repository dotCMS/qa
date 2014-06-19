package com.dotcms.qa.selenium.pages.backend.common;

import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.dotcms.qa.selenium.pages.backend.IFolderAddOrEditPage;
import com.dotcms.qa.selenium.pages.backend.IHTMLPageAddOrEdit_PropertiesPage;
import com.dotcms.qa.selenium.pages.backend.ISiteBrowserPage;
import com.dotcms.qa.selenium.pages.common.BasePage;
import com.dotcms.qa.selenium.util.SeleniumPageManager;

public class SiteBrowserPage extends BasePage implements ISiteBrowserPage {
    private static final Logger logger = Logger.getLogger(SiteBrowserPage.class);

    //private WebElement addNewButton_arrow;
	private WebElement TreeUL;
	
	public SiteBrowserPage(WebDriver driver) {
		super(driver);
	}

	public void createFolder(String parent, String title) throws Exception {
		
		// TODO - add code to select parent if parent is not an empty string
		
		getWebElementClickable(By.id("addNewButton_arrow")).click();
		WebElement addFolderButton = getWebElement(By.className("folderAddIcon"));
		addFolderButton.click();
		try{Thread.sleep(1000);} catch(InterruptedException e) {};
		IFolderAddOrEditPage folderAddPage = SeleniumPageManager.getBackEndPageManager().getPageObject(IFolderAddOrEditPage.class);
		folderAddPage.setTitle(title);
		folderAddPage.setName(title);
		folderAddPage.save();
	}
	
	public void createHTMLPage(String title, String templateName) throws Exception{
		getWebElementClickable(By.id("addNewButton_arrow")).click();
		WebElement addFolderButton = getWebElement(By.className("newPageIcon"));
		addFolderButton.click();
		IHTMLPageAddOrEdit_PropertiesPage htmlAddPage = SeleniumPageManager.getBackEndPageManager().getPageObject(IHTMLPageAddOrEdit_PropertiesPage.class);
		htmlAddPage.setTitle(title);
		htmlAddPage.setURL(title);
		htmlAddPage.setTemplate(templateName);
		htmlAddPage.saveAndPublish();
	}
	
	public IFolderAddOrEditPage createFolder(String parent, String title, String URL, int sortOrder, boolean showOnMenu, String allowedFileExtensionCSVList, String defaultFileAssetType) throws Exception {
		// TODO
		return SeleniumPageManager.getBackEndPageManager().getPageObject(IFolderAddOrEditPage.class);
	}

	public void selectFolder(String folderName) throws Exception {
		WebElement treeChildrenULTopLevel = null;
		List<WebElement> uls = TreeUL.findElements(By.tagName("ul"));
		for(WebElement ul : uls) {
			try {
				if(ul.getAttribute("id").trim().endsWith("TreeChildrenUL")) {
					treeChildrenULTopLevel = ul;
					break;
				}
			}
			catch(Exception e) {
				logger.error("Unexpected error attempting to iterate over uls", e);
				// Move on to next ul and keep going
			}
		}

		if(treeChildrenULTopLevel == null)
			throw new Exception("Unable to find treeChildrenULTopLevel");
		
		WebElement desiredFolderSpan = null;
		List<WebElement> spans = TreeUL.findElements(By.tagName("span"));
		for(WebElement span : spans) {
			try {
				if(span.getAttribute("id").trim().endsWith("TreeFolderName")  && span.getText().trim().equals(folderName)) {
					desiredFolderSpan = span;
					break;
				}
			}
			catch(Exception e) {
				logger.error("Unexpected error attempting to iterate over spans", e);
				// Move on to next span and keep going
			}
		}
		
		if(desiredFolderSpan == null)
			throw new Exception("Unable to find desired folder span");
		
		desiredFolderSpan.click();
	}
}
