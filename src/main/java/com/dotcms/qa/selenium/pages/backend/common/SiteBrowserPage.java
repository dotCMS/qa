package com.dotcms.qa.selenium.pages.backend.common;

import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.dotcms.qa.selenium.pages.backend.IFolderAddOrEditPage;
import com.dotcms.qa.selenium.pages.backend.IHTMLPageAddDialog;
import com.dotcms.qa.selenium.pages.backend.IHTMLPageAddOrEdit_ContentPage;
import com.dotcms.qa.selenium.pages.backend.IPreviewHTMLPage_Page;
import com.dotcms.qa.selenium.pages.backend.ISiteBrowserPage;
import com.dotcms.qa.selenium.pages.common.BasePage;
import com.dotcms.qa.selenium.util.SeleniumPageManager;

public class SiteBrowserPage extends BasePage implements ISiteBrowserPage {
    private static final Logger logger = Logger.getLogger(SiteBrowserPage.class);

    //private WebElement addNewButton_arrow;
	private WebElement TreeUL;
	private WebElement assetListBody;
	private WebElement changeHostId;
	private WebElement subNavHost;
	private WebElement fm_publish;
	
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
		IHTMLPageAddDialog htmlAddDlg = SeleniumPageManager.getBackEndPageManager().getPageObject(IHTMLPageAddDialog.class);
		htmlAddDlg.select();
		IHTMLPageAddOrEdit_ContentPage htmlAddPage = SeleniumPageManager.getBackEndPageManager().getPageObject(IHTMLPageAddOrEdit_ContentPage.class);
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
		sleep(1);
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
	
	/**
	 * Open the element in the right side of the site browser portlet (simulate double click over the element)
	 * @param elementName Name of the page or file asset
	 * @return IPreviewHTMLPage_Page
	 * @throws Exception
	 */
	public IPreviewHTMLPage_Page selectPageElement(String elementName)  throws Exception{
		List<WebElement>  elements = assetListBody.findElements(By.cssSelector("span[id*='-NameSPAN']"));
		for(WebElement elem : elements){
			if(elem.getText().equals(elementName)){
				this.selectPopupMenuOption(elem, getLocalizedString("Open-Preview"));
				//doubleClickElement(elem);
				break;
			}
		}
		return SeleniumPageManager.getBackEndPageManager().getPageObject(IPreviewHTMLPage_Page.class);
	}
	
	/**
	 * Change the host displayed in the site browser view
	 * @param hostName Name of the host
	 * @throws Exception
	 */
	public void changeHost(String hostName)  throws Exception{
		changeHostId.click();
		subNavHost.clear();
		subNavHost.sendKeys(hostName);
		subNavHost.sendKeys(Keys.RETURN);
	}
	/**
	 * 
	 * @param element
	 * @param menuOption
	 * @return
	 * @throws Exception
	 */
	private boolean selectPopupMenuOption(WebElement element, String menuOption) throws Exception {
		boolean foundValue = false;
		sleep(1);
		rightClickElement(element);	
		WebElement popupMenu = getWebElement(By.id("popups"));
		//this.hoverOverElement(popupMenu);
		List<WebElement> rows = popupMenu.findElements(By.tagName("a"));
		WebElement prevRow = null;
		for(WebElement row : rows) {
			if(row.getText().trim().endsWith(menuOption)) {
					row.click();
					foundValue = true;
					break;
			}
		}
		return foundValue;
	}
}
