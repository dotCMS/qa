package com.dotcms.qa.selenium.pages.backend;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.dotcms.qa.selenium.pages.IBasePage;

public interface ISiteBrowserPage extends IBasePage {
	public void createFolder(String parent, String title) throws Exception;
	public IFolderAddOrEditPage createFolder(String parent, String title, String URL, int sortOrder, boolean showOnMenu, String allowedFileExtensionCSVList, String defaultFileAssetType) throws Exception;
	public void createHTMLPage(String title, String templateName) throws Exception;
	public void selectFolder(String folderName) throws Exception;
	
	/**
	 * Open the element in the right side of the site browser portlet (simulate double click over the element)
	 * @param elementName Name of the page or file asset
	 * @throws Exception
	 */
	public IPreviewHTMLPage_Page selectPageElement(String elementName)  throws Exception;
	
	/**
	 * Change the host displayed in the site browser view
	 * @param hostName Name of the host
	 * @throws Exception
	 */
	public void changeHost(String hostName)  throws Exception;
	
	/**
	 * Validates if a page,file asset, link or folder exist
	 * @param elementName Name of the page, folder or file asset
	 * @return true if exist false if not
	 * @throws Exception
	 */
	public boolean doesElementExist(String elementName)  throws Exception;
	
	/**
	 * Create a page
	 * @param title         Name of the page
	 * @param templateName  Name of the template
	 * @param url           Page url
	 * @throws Exception
	 */
	public void createHTMLPage(String title, String templateName,String url) throws Exception;
	
	/**
	 * Click the push publish option from the right click menu options
	 * @param elementName Element Name
	 * @throws Exception
	 */
	public void pushElement(String elementName) throws Exception;
	
	/**
	 * Publish a site browser element
	 * @param elementName Element name
	 * @throws Exception
	 */
	public void publishElement(String elementName) throws Exception;
	
	/**
	 * Unpublish a site browser element
	 * @param elementName   Element name
	 * @throws Exception
	 */
	public void unPublishElement(String elementName) throws Exception;
	
	/**
	 * Archive a site browser element
	 * @param elementName  Element name
	 * @throws Exception
	 */
	public void archiveElement(String elementName) throws Exception;
	
	/**
	 * Delete a page
	 * @param pageName    Page name
	 * @throws Exception
	 */
	public void deletePage(String pageName) throws Exception;
}
