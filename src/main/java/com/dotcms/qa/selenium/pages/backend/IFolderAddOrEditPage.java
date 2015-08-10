package com.dotcms.qa.selenium.pages.backend;

import java.util.List;
import java.util.Map;

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
	
	/**
	 * Select the properties tab
	 * @throws Exception
	 */
	public void getPropertiesTab() throws Exception;
	
	/**
	 * Select the permission tab
	 * @throws Exception
	 */
	public void getPermissionTab() throws Exception;
	
	/**
	 * Click the permission individually button
	 * @throws Exception
	 */
	public void activatePermissionIndividually() throws Exception;
	
	/**
	 * Add a role
	 * @param roleName role name
	 * @param subpermissions subpermissions
	 * @param view permission to view
	 * @param addChildren permission to add children
	 * @param edit permission to edit
	 * @param publish permission to publish
	 * @param editPermission permission to edit permissions
	 * @param vanityUrl permission to add vanity urls
	 * @throws Exception
	 */
	public void addRole(String roleName, List<Map<String,Object>> subpermissions, boolean view, boolean addChildren, boolean edit, boolean publish, boolean editPermission, boolean vanityUrl) throws Exception;
	
	/**
	 * Click the apply permission button
	 * @throws Exception
	 */
	public void applyPermissionChanges() throws Exception;
	
	/**
	 * Click the option in the Permission confirmation box
	 * @param value Yes or no
	 * @throws Exception
	 */
	public void folderPermissionAlert(String value) throws Exception;
}
