package com.dotcms.qa.selenium.pages.backend;

import java.util.List;
import java.util.Map;

import com.dotcms.qa.selenium.pages.IBasePage;

/**
 * Roles Page Interface
 * @author Oswaldo Gallango
 * @since 11/18/2014
 * @version 1.0
 *
 */
public interface IRolesPage extends IBasePage {

	/**
	 * Creates a role
	 * @param name Name of the role to create
	 * @param key Role Key
	 * @param description Role description
	 * @param canEditUser Check the edit user checkbox
	 * @param canEditPermision Check the edit permissions checkbox
	 * @param canEitTabs Check the edit tabs checkbox
	 */
	public void createRole(String name, String key, String description, boolean canEditUser, boolean canEditPermision, boolean canEitTabs) throws Exception;

	/**
	 * Validate if the role exist
	 * @param Name name of the role to search
	 * @return true if the role exist, false if not
	 */
	public boolean doesRoleExist(String name) throws Exception;

	/**
	 * Remove a role
	 * @param name name of the role to remove
	 */
	public void removeRole(String name) throws Exception;
	
	/**
	 * Include a new tab and portlet for a specific role
	 * @param roleName Name of the role where the tab will be added
	 * @param tabName Name of the new tab
	 * @param portletName Name of the portlet to include
	 * @return true if the portlet was added, false if not
	 */
	public boolean addPortletToRolesTabs(String roleName, String tabName, String portletName) throws Exception;
	
	/**
	 * Remove the specified tab from the role indicated
	 * @param roleName Name of the role where the tab will be removed
	 * @param tabName Name of the tab 
	 * @return
	 */
	public boolean removeTabFromRole(String roleName, String tabName) throws Exception;
	
	/**
	 * Add or remove a existing tab for a role
	 * @param roleName Name of the role where the tab will be removed
	 * @param tabName Name of the tab 
	 */
	public void checkUncheckCMSTab(String roleName, String tabName) throws Exception;
	
	/**
	 * Set role permissions on a host
	 * @param roleName		Role name
	 * @param hostName		Host name
	 * @param subpermissions	Allows to set specific sub permission 
	 * @param view          Permission to view
	 * @param addChildren	Permission to add child
	 * @param edit			Permission to  edit
	 * @param publish		Permission to publish
	 * @param editPermission	Permission to edit permission
	 * @param vanityUrl		Permission to add vanity url
	 */
	public void addPermissionOnHost(String roleName, String hostName, List<Map<String,Object>> subpermissions, boolean view, boolean addChildren, boolean edit, boolean publish, boolean editPermission, boolean vanityUrl) throws Exception;

	/**
	 * Delete a specific role
	 * @param roleName  Role name
	 * @throws Exception
	 */
	public void deleteRole(String roleName) throws Exception;
}
