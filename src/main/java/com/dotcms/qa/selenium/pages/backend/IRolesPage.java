package com.dotcms.qa.selenium.pages.backend;

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
	public void createRole(String name, String key, String description, boolean canEditUser, boolean canEditPermision, boolean canEitTabs);

	/**
	 * Validate if the role exist
	 * @param Name name of the role to search
	 * @return true if the role exist, false if not
	 */
	public boolean doesRoleExist(String name);

	/**
	 * Remove a role
	 * @param name name of the role to remove
	 */
	public void removeRole(String name);
	
	/**
	 * Include a new tab and portlet for a specific role
	 * @param roleName Name of the role where the tab will be added
	 * @param tabName Name of the new tab
	 * @param portletName Name of the portlet to include
	 * @return true if the portlet was added, false if not
	 */
	public boolean addPortletToRolesTabs(String roleName, String tabName, String portletName);
	
	/**
	 * Remove the specified tab from the role indicated
	 * @param roleName Name of the role where the tab will be removed
	 * @param tabName Name of the tab 
	 * @return
	 */
	public boolean removeTabFromRole(String roleName, String tabName);
}
