package com.dotcms.qa.selenium.pages.backend;

import com.dotcms.qa.selenium.pages.IBasePage;

public interface IFolderAddOrEditPage extends IBasePage {
	public void setTitle(String title);
	public void setName(String name);
	public void setSortOrder(int sortOrder);
	public void setShowOnMenu(boolean showOnMenu);
	public void setAllowedFileExtensions(String allowedFileExtensions);
	public void setDefaultFileAssetType(String fileAssetType);
	public void save();
	public void cancel();
}
