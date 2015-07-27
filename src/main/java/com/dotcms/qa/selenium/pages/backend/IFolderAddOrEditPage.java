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
	/**
	 * Get the Folder Title
	 * @return String
	 * @throws Exception
	 */
	public String getFolderTitle() throws Exception;
	/**
	 * Get the Folder name
	 * @return String
	 * @throws Exception
	 */
	public String getFolderName() throws Exception;
	/**
	 * Get the Folder sort order
	 * @return int
	 * @throws Exception
	 */
	public int getSortOrder() throws Exception;
	/**
	 * Indicates if the Folder is show on menu
	 * @return boolean
	 * @throws Exception
	 */
	public boolean isShowOnMenu() throws Exception;
	/**
	 * Get the Folder alled file extension
	 * @return String
	 * @throws Exception
	 */
	public String getAllowedFileExtensions() throws Exception;
	/**
	 * Get the Folder default file asset type
	 * @return String
	 * @throws Exception
	 */
	public String getDefaultFileAssetType() throws Exception;
}
