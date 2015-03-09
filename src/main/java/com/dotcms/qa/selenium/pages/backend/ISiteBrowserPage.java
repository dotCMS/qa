package com.dotcms.qa.selenium.pages.backend;

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
}
