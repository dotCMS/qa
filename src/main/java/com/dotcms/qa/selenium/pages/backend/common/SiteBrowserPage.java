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
	private WebElement trashLabel;

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

	/**
	 * Validates if a page,file asset, link exist
	 * @param elementName Name of the page or file asset
	 * @return true if exist false if not
	 * @throws Exception
	 */
	public boolean doesElementExist(String elementName)  throws Exception{
		boolean exist=false;
		List<WebElement>  elements = assetListBody.findElements(By.cssSelector("span[id*='-NameSPAN']"));
		for(WebElement elem : elements){
			if(elem.getText().equals(elementName)){
				exist=true;
				break;
			}
		}
		return exist;
	}

	/**
	 * Create a page
	 * @param title         Name of the page
	 * @param templateName  Name of the template
	 * @param url           Page url
	 * @throws Exception
	 */
	public void createHTMLPage(String title, String templateName,String url) throws Exception{
		getWebElementClickable(By.id("addNewButton_arrow")).click();
		WebElement addFolderButton = getWebElement(By.className("newPageIcon"));
		addFolderButton.click();
		IHTMLPageAddDialog htmlAddDlg = SeleniumPageManager.getBackEndPageManager().getPageObject(IHTMLPageAddDialog.class);
		htmlAddDlg.select();
		IHTMLPageAddOrEdit_ContentPage htmlAddPage = SeleniumPageManager.getBackEndPageManager().getPageObject(IHTMLPageAddOrEdit_ContentPage.class);
		htmlAddPage.setTitle(title);
		htmlAddPage.setURL(url);
		htmlAddPage.setTemplate(templateName);
		htmlAddPage.saveAndPublish();
	}


	/**
	 * Click the push publish option from the right click menu options
	 * @param elementName Element Name
	 * @throws Exception
	 */
	public void pushElement(String elementName) throws Exception{
		List<WebElement>  elements = assetListBody.findElements(By.cssSelector("span[id*='-NameSPAN']"));
		for(WebElement elem : elements){
			if(elem.getText().equals(elementName)){
				selectRightClickPopupMenuAction(elem,getLocalizedString("Remote-Publish"));
				sleep(2);
				WebElement remotePublishBundleDialog = getWebElement(By.id("remotePublisherDia"));
				remotePublishBundleDialog.findElement(By.id("remotePublishSaveButton")).click();
				break;
			}
		}		
	}

	/**
	 * Publish a site browser element
	 * @param elementName Element Name
	 * @throws Exception
	 */
	public void publishElement(String elementName) throws Exception{
		List<WebElement>  elements = assetListBody.findElements(By.cssSelector("span[id*='-NameSPAN']"));
		for(WebElement elem : elements){
			if(elem.getText().equals(elementName)){
				selectRightClickPopupMenuAction(elem,getLocalizedString("Publish"));
				break;
			}
		}	
		sleep(2);
	}
	/**
	 * Unpublish a site browser element
	 * @param elementName Element Name
	 * @throws Exception
	 */
	public void unPublishElement(String elementName) throws Exception{
		List<WebElement>  elements = assetListBody.findElements(By.cssSelector("span[id*='-NameSPAN']"));
		for(WebElement elem : elements){
			if(elem.getText().equals(elementName)){
				selectRightClickPopupMenuAction(elem,getLocalizedString("Unpublish"));
				break;
			}
		}
		sleep(2);
	}

	/**
	 * Archive a site browser element
	 * @param elementName Element name
	 * @throws Exception
	 */
	public void archiveElement(String elementName) throws Exception{
		List<WebElement>  elements = assetListBody.findElements(By.cssSelector("span[id*='-NameSPAN']"));
		for(WebElement elem : elements){
			if(elem.getText().equals(elementName)){
				selectRightClickPopupMenuAction(elem,getLocalizedString("Archive"));
				break;
			}
		}	
		sleep(2);
	}

	/**
	 * Delete a page
	 * @param pageName Page name
	 * @throws Exception
	 */
	public void deletePage(String pageName) throws Exception{
		trashLabel.click();
		List<WebElement>  elements = assetListBody.findElements(By.cssSelector("span[id*='-NameSPAN']"));
		for(WebElement elem : elements){
			if(elem.getText().equals(pageName)){
				selectRightClickPopupMenuAction(elem,getLocalizedString("Delete-Page"));
				break;
			}
		}
		sleep(2);
	}

	/**
	 * allows to select right click menu options over a WebElement 
	 * @param elem Element where the right click will be applied
	 * @param menuOption Name of the option label to select
	 * @return true if the option was found and clicked, false if not
	 * @throws Exception
	 */
	private boolean selectRightClickPopupMenuAction(WebElement elem, String menuOption) throws Exception {
		boolean foundValue = false;
		sleep(1);
		rightClickElement(elem);	
		List<WebElement> rows = getWebElements(By.className("contextPopupMenu"));
		for(WebElement row : rows) {
			logger.debug("label innerHTML = |" + row.getAttribute("innerHTML") + "|");
			if(row.getText().trim().startsWith(menuOption)) {
				this.hoverOverElement(row);
				getWebElementClickable(row).click();
				try{
					switchToAlert().accept();
				}catch(Exception e){

				}
				foundValue = true;
				break;
			}
		}
		return foundValue;
	}

	/**
	 * Delete a folder
	 * @param folderName    Folder name
	 * @throws Exception
	 */
	public void deleteFolder(String folderName) throws Exception{
		WebElement folder = getFolder(folderName);
		selectRightClickPopupMenuAction(folder, getLocalizedString("delete"));
		sleep(2);
	}

	/**
	 * Get Folder WebElement
	 * @param folderName Folder name
	 * @return WebElement
	 * @throws Exception
	 */
	private WebElement getFolder(String folderName) throws Exception {
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
		
		return desiredFolderSpan;
	}

	/**
	 * Click the push publish option from the right click menu options
	 * @param folderName Folder Name
	 * @throws Exception
	 */
	public void pushFolder(String folderName) throws Exception{
		WebElement  folder = getFolder(folderName);
		selectRightClickPopupMenuAction(folder,getLocalizedString("Remote-Publish"));
		sleep(2);
		WebElement remotePublishBundleDialog = getWebElement(By.id("remotePublisherDia"));
		remotePublishBundleDialog.findElement(By.id("remotePublishSaveButton")).click();		
	}

	/**
	 * Validates if a folder exist
	 * @param folderName Name of the folder
	 * @return true if exist false if not
	 * @throws Exception
	 */
	public boolean doesFolderExist(String folderName) throws Exception{
		boolean exist=false;
		WebElement  elem = getFolder(folderName);
		if(elem != null && elem.getText().equals(folderName)){
			exist=true;
		}
		return exist;
	}
}
