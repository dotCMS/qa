package com.dotcms.qa.selenium.pages.backend;

import com.dotcms.qa.selenium.pages.IBasePage;

public interface ISiteBrowserPage extends IBasePage {
	public void createFolder(String parent, String title) throws Exception;
	public IFolderAddOrEditPage createFolder(String parent, String title, String URL, int sortOrder, boolean showOnMenu, String allowedFileExtensionCSVList, String defaultFileAssetType) throws Exception;
	public void createHTMLPage(String title, String templateName) throws Exception;
	public void selectFolder(String folderName) throws Exception;
}
