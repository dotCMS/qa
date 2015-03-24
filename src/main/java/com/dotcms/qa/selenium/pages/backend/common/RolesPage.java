package com.dotcms.qa.selenium.pages.backend.common;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.dotcms.qa.selenium.pages.backend.IRolesPage;
import com.dotcms.qa.selenium.pages.common.BasePage;
import com.dotcms.qa.util.Evaluator;
import com.dotcms.qa.util.WebKeys;

/**
 * This class implements the methods defined in the IRolesPage interface
 * to do the Users TestRail validations
 * @author Oswaldo Gallango
 * @since 11/18/2014
 * @version 1.0
 * 
 */
public class RolesPage extends BasePage implements IRolesPage {


	public RolesPage(WebDriver driver) {
		super(driver);
	}

	private static final Logger logger = Logger.getLogger(UsersPage.class);

	/**
	 * Role Name input
	 */
	private WebElement roleName;

	/**
	 * Role description input
	 */
	private WebElement roleDescription;

	/**
	 * Edit User check box input
	 */
	private WebElement editUsers;

	/**
	 * Edit permissions check box input
	 */
	private WebElement editPermissions;

	/**
	 * Edit Tabs check box input
	 */
	private WebElement editTabs;

	/**
	 * Role search filter input
	 */
	private WebElement rolesFilter;

	/**
	 * CMS Tab
	 */
	private WebElement roleTabsContainer_tablist_cmsTabsTab;

	/**
	 * tab name
	 */
	private WebElement layoutName;

	/**
	 * tool select
	 */
	private WebElement portletList;

	/**
	 * save role layout
	 */
	private WebElement saveRoleLayoutsButton_label;
	
	/**
	 * delete a role
	 */
	private WebElement deleteRoleButton_label;

	/**
	 * Creates a role
	 * @param name Name of the role to create
	 * @param key Role Key
	 * @param description Role description
	 * @param canEditUser Check the edit user checkbox
	 * @param canEditPermision Check the edit permissions checkbox
	 * @param canEitTabs Check the edit tabs checkbox
	 */
	public void createRole(String name, String key, String description, boolean canEditUser, boolean canEditPermision, boolean canEitTabs) throws Exception{
		//click add role button
		getAddRoleButton().click();
		sleep();
		//set text inputs
		//roleName.clear();
		roleName.sendKeys(name);

		//roleKey.clear();
		//roleKey.sendKeys(key);

		//roleDescription.clear();
		roleDescription.sendKeys(description);

		/**
		 * Check boxes
		 */
		//Edit users checkbox
		if(canEditUser){
			editUsers.click();
		}
		//Edit permissions checkbox
		if(canEditPermision){
			editPermissions.click();
		}
		//Edit tabss checkbox
		if(canEitTabs){
			editTabs.click();
		}
		//click save button
		getSaveRoleButton().click();
	}

	/**
	 * Validate if the role exist
	 * @param roleName
	 * @return true if the role exist, false if not
	 */
	public boolean doesRoleExist(String roleName) throws Exception{
		boolean retValue = false;
		rolesFilter.clear();
		rolesFilter.sendKeys(roleName);
		sleep();
		//Search in the right panel for the search results
		List<WebElement> rows = getWebElementPresent(By.id("rolesTree")).findElements(By.tagName("span"));
		for(WebElement row : rows) {
			try {
				if(row.getText().replace("\\", "").trim().equals(roleName)) {
					retValue = true;
					break;
				}
			}catch(Exception e) {
				logger.error("Unexpected error attempting to iterate over Roles - name=" + roleName, e);
				// Move on to next row and keep going
			}
		}
		return retValue;
	}

	/**
	 * Remove a role
	 * @param roleName name of the role to remove
	 * @return true if the role was removed, false if not
	 */
	public void removeRole(String roleName) throws Exception{
		boolean retValue = false;
		rolesFilter.clear();
		rolesFilter.sendKeys(roleName);
		sleep();
		//Search in the right panel for the search results
		List<WebElement> rows = getWebElementPresent(By.id("rolesTree")).findElements(By.tagName("span"));
		for(WebElement row : rows) {
			try {
				if(row.getText().replace("\\", "").trim().equals(roleName)) {
					row.click();
					retValue = true;
					break;
				}
			}catch(Exception e) {
				logger.error("Unexpected error attempting to iterate over Roles - name=" + roleName, e);
				// Move on to next row and keep going
			}
		}
		if(retValue){
			sleep();
			getDeleteRoleButton().click();
			sleep();
			this.switchToAlert().accept();
		}

	}

	/**
	 * Search the add role button dynamically, because doesn't have a fixed id and
	 * dojo change it some times
	 */
	private WebElement getAddRoleButton() throws Exception{
		List<WebElement> buttonsArea = getWebElementPresent(By.className("buttonBoxRight")).findElements(By.cssSelector("span[class='dijitReset dijitInline dijitButtonText']"));
		WebElement addRoleButton = null;
		for(WebElement span : buttonsArea){
			if(span.getText().equals(getLocalizedString("Add-Role"))){
				addRoleButton = span;
				break;
			}
		}
		return addRoleButton;
	}

	/**
	 * Search the save role button dynamically, because doesn't have a fixed id and
	 * dojo change it some times
	 */
	private WebElement getSaveRoleButton() throws Exception{
		List<WebElement> buttonsArea = getWebElementPresent(By.id("addRoleDialog")).findElements(By.cssSelector("span[class='dijitReset dijitInline dijitButtonText']"));
		WebElement saveRoleButton = null;
		for(WebElement span : buttonsArea){
			if(span.getText().equals(getLocalizedString("Save"))){
				saveRoleButton = span;
				break;
			}
		}
		return saveRoleButton;
	}

	/**
	 * Search the delete role button dynamically, because doesn't have a fixed id and
	 * dojo change it some times
	 */
	private WebElement getDeleteRoleButton() throws Exception{
		List<WebElement> buttonsArea = getWebElementPresent(By.className("buttonBoxRight")).findElements(By.cssSelector("span[class='dijitReset dijitInline dijitButtonText']"));
		WebElement saveRoleButton = null;
		for(WebElement span : buttonsArea){
			if(span.getText().equals(getLocalizedString("delete-role"))){
				saveRoleButton = span;
				break;
			}
		}
		return saveRoleButton;
	}

	/**
	 * Include a new tab and portlet for a specific role
	 * @param roleName Name of the role where the tab will be added
	 * @param tabName Name of the new tab
	 * @param portletName Name of the portlet to include
	 * @return true if the portlet was added, false if not
	 */
	public boolean addPortletToRolesTabs(String roleName, String tabName, String portletName) throws Exception{
		boolean retValue=false;
		rolesFilter.clear();
		rolesFilter.sendKeys(roleName);
		sleep();
		tabName = getLocalizedString(tabName);
		//Search in the right panel for the search results
		List<WebElement> rows = getWebElementPresent(By.id("rolesTree")).findElements(By.tagName("span"));
		for(WebElement row : rows) {
			try {
				if(row.getText().replace("\\", "").trim().equals(roleName)) {
					row.click();
					sleep();
					//click CMS Tab
					roleTabsContainer_tablist_cmsTabsTab.click();
					List<WebElement> currentTabs = getWebElementPresent(By.id("cmsTabsTab")).findElements(By.tagName("td"));

					boolean existTab = false;
					WebElement selectedTab = null;
					for(WebElement tab : currentTabs){
						if(tab.getText().equals(tabName)){
							selectedTab=tab;
							existTab=true;
							break;
						}
					}
					sleep();
					if(!existTab){
						getCreateCustomTabButton().click();
						//set tab name
						layoutName.clear();
						layoutName.sendKeys(tabName);
						//set the portlet to include
						portletList.clear();
						portletList.sendKeys(getLocalizedString(portletName));
					}else{
						//open the tab
						selectedTab.click();
						//set the portlet to include
						portletList.clear();
						portletList.sendKeys(getLocalizedString(portletName));
					}
					sleep();
					getAddToolButton().click();
					//save the tab
					sleep();
					getSaveTabButton().click();

					//check the tab
					sleep();
					currentTabs = getWebElementPresent(By.id("cmsTabsTab")).findElements(By.tagName("tr"));
					for(WebElement tab : currentTabs){
						List<WebElement> columns =tab.findElements(By.tagName("td"));
						for(WebElement column : columns){
							if(column.getText().equals(tabName)){
								WebElement checkbox = tab.findElement(By.tagName("input"));
								if(!checkbox.isSelected()){
									if(getBrowserName().equals(WebKeys.FIREFOX_BROWSER_NAME)){
										checkbox.sendKeys(Keys.SPACE);
									}else{
										checkbox.click();
									}
									sleep();
									saveRoleLayoutsButton_label.click();
								}
								retValue=true;
								break;
							}
						}
						if(retValue){
							break;
						}
					}
					if(retValue){
						break;
					}
				}
			}catch(NoSuchElementException e) {
				logger.trace("Row does not include input element", e);
				// Move on to next row and keep going
			}catch(Exception e) {
				logger.error("Unexpected error attempting to iterate over Roles - name=" + roleName, e);
				// Move on to next row and keep going
			}
		}
		return retValue;
	}

	/**
	 * Search the save role button dynamically, because doesn't have a fixed id and
	 * dojo change it some times
	 */
	private WebElement getCreateCustomTabButton() throws Exception{
		List<WebElement> buttonsArea = getWebElementPresent(By.id("cmsTabsTab")).findElements(By.cssSelector("span[class='dijitReset dijitInline dijitButtonText']"));
		WebElement createCustomButton = null;
		for(WebElement span : buttonsArea){
			if(span.getText().equals(getLocalizedString("create-custom-tab"))){
				createCustomButton = span;
				break;
			}
		}
		return createCustomButton;
	}

	/**
	 * Search the add tool button dynamically, because doesn't have a fixed id and
	 * dojo change it some times
	 */
	private WebElement getAddToolButton() throws Exception{
		List<WebElement> buttonsArea = getWebElementPresent(By.id("newLayoutForm")).findElements(By.cssSelector("span[class='dijitReset dijitInline dijitButtonText']"));
		WebElement addTool = null;
		for(WebElement span : buttonsArea){
			if(span.getText().equals(getLocalizedString("add"))){
				addTool = span;
				break;
			}
		}
		return addTool;
	}

	/**
	 * Search the save tab button dynamically, because doesn't have a fixed id and
	 * dojo change it some times
	 */
	private WebElement getSaveTabButton() throws Exception{
		List<WebElement> buttonsArea = getWebElementPresent(By.id("newLayoutForm")).findElements(By.cssSelector("span[class='dijitReset dijitInline dijitButtonText']"));
		WebElement saveCustomButton = null;
		for(WebElement span : buttonsArea){
			if(span.getText().equals(getLocalizedString("Save"))){
				saveCustomButton = span;
				break;
			}
		}
		return saveCustomButton;
	}

	/**
	 * Remove the specified tab from the role indicated
	 * @param roleName Name of the role where the tab will be removed
	 * @param tabName Name of the tab 
	 * @return
	 */
	public boolean removeTabFromRole(String roleName, String tabName) throws Exception{
		boolean retValue=false;
		rolesFilter.clear();
		rolesFilter.sendKeys(roleName);
		tabName=getLocalizedString(tabName);
		sleep();
		//Search in the right panel for the search results
		List<WebElement> rows = getWebElementPresent(By.id("rolesTree")).findElements(By.tagName("span"));
		for(WebElement row : rows) {
			try {
				if(row.getText().replace("\\", "").trim().equals(roleName)) {
					row.click();
					sleep();
					//click CMS Tab
					roleTabsContainer_tablist_cmsTabsTab.click();
					List<WebElement> currentTabs = getWebElementPresent(By.id("cmsTabsTab")).findElements(By.tagName("td"));

					for(WebElement tab : currentTabs){
						if(tab.getText().equals(tabName)){
							//open tab info
							tab.click();
							sleep();
							sleep();
							//click delete button
							getDeleteTabButton().click();
							this.switchToAlert().accept();
							retValue=true;
							break;
						}
					}
				}
				if(retValue){
					break;
				}
			}catch(Exception e) {
				logger.error("Unexpected error attempting to iterate over Roles - name=" + roleName, e);
				// Move on to next row and keep going
			}
		}
		return retValue;
	}

	/**
	 * Search the delete tab button dynamically, because doesn't have a fixed id and
	 * dojo change it some times
	 */
	private WebElement getDeleteTabButton() throws Exception{
		List<WebElement> buttonsArea = getWebElementPresent(By.id("newLayoutForm")).findElement(By.cssSelector("div[class='buttonRow']")).findElements(By.cssSelector("span[class='dijitReset dijitInline dijitButtonText']"));
		WebElement deleteTabButton = null;
		for(WebElement span : buttonsArea){
			if(span.getText().equals(getLocalizedString("Delete"))){
				deleteTabButton = span;
				break;
			}
		}
		return deleteTabButton;
	}

	/**
	 * Load the role properties
	 * @param roleName Name of the role
	 */
	private void loadRole(String roleName) throws Exception{
		rolesFilter.clear();
		rolesFilter.sendKeys(roleName);
		sleep();
		//Search in the right panel for the search results
		List<WebElement> rows = getWebElementPresent(By.id("rolesTree")).findElements(By.tagName("span"));
		for(WebElement row : rows) {
			try {
				if(row.getText().replace("\\", "").trim().equals(roleName)) {
					row.click();
					break;
				}
			}catch(NoSuchElementException e) {
				logger.trace("Row does not include td element", e);
				// Move on to next row and keep going
			}catch(Exception e) {
				logger.error("Unexpected error attempting to iterate over Roles - name=" + roleName, e);
				// Move on to next row and keep going
			}
		}
	}

	/**
	 * Add or remove a existing tab for a role
	 * @param roleName Name of the role where the tab will be removed
	 * @param tabName Name of the tab 
	 */
	public void checkUncheckCMSTab(String roleName, String tabName) throws Exception{
		//get role
		loadRole(roleName);

		roleTabsContainer_tablist_cmsTabsTab.click();
		sleep(2);
		List<WebElement> currentTabs = getWebElementPresent(By.id("cmsTabsTab")).findElements(By.tagName("tr"));
		for(WebElement tab : currentTabs){
			List<WebElement> columns =tab.findElements(By.tagName("td"));
			if(columns.size()==4){
				if(columns.get(1).getText().equals(tabName)){
					WebElement checkbox = tab.findElement(By.tagName("input"));
					if(!checkbox.isSelected()){
						if(getBrowserName().equals(WebKeys.FIREFOX_BROWSER_NAME)){
							checkbox.sendKeys(Keys.SPACE);
						}else{
							checkbox.click();
						}
						sleep();
						saveRoleLayoutsButton_label.click();
					}
					break;
				}
			}
		}
	}

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
	public void addPermissionOnHost(String roleName, String hostName, List<Map<String,Object>> subpermissions, boolean view, boolean addChildren, boolean edit, boolean publish, boolean editPermission, boolean vanityUrl) throws Exception{

		//get role
		loadRole(roleName);
		//get the permission tabs
		getWebElement(By.id("roleTabsContainer_tablist_permissionsTab")).click();
		//add host
		WebElement  siteOrFolder = getWebElement(By.id("rolePermissionsHostSelector-hostFolderSelect"));
		siteOrFolder.clear();
		siteOrFolder.sendKeys(hostName);
		siteOrFolder.sendKeys(Keys.TAB);
		sleep(2);
		List<WebElement> results = getWebElements(By.cssSelector("span[class='dijitTreeLabel']"));
		for(WebElement span : results){
			if(span.getText().contains(hostName)){
				span.click();
				break;
			}
		}
		List<WebElement> buttons = getWebElement(By.id("rolePermissionsHostSelectorWrapper")).findElements(By.cssSelector("span[class='dijitReset dijitInline dijitButtonText']"));
		for(WebElement button : buttons){
			if(button.getText().equals(getLocalizedString("add"))){
				button.click();
				break;
			}
		}
		//add permissions
		List<WebElement> entries = getWebElements(By.className("permissionTableTitle"));
		for(WebElement entry: entries){
			List<WebElement> columns = entry.findElements(By.tagName("td"));
			if(columns.get(0).getText().trim().equals(hostName)){
				if(view){
					columns.get(1).findElement(By.tagName("input")).click();
				}
				if(addChildren){
					columns.get(2).findElement(By.tagName("input")).click();
				}
				if(edit){
					columns.get(3).findElement(By.tagName("input")).click();
				}
				if(editPermission){
					columns.get(4).findElement(By.tagName("input")).click();
				}
				WebElement parentDiv = getParent(entry);
				String id = parentDiv.getAttribute("id").replace("hostFolderAccordionPermissionsTitleWrapper-","");
				if(!subpermissions.isEmpty()){
					List<WebElement> subper = getWebElement(By.id("permissionsAccordionPane-"+id)).findElement(By.cssSelector("table[class='permissionTable']")).findElements(By.tagName("tr"));
					for(Map<String,Object> element : subpermissions){
						String property = getLocalizedString((String)element.get("name"));
						boolean viewProperty = (Boolean) element.get("view");
						boolean addChildrenProperty = (Boolean) element.get("addChildren");
						boolean editProperty = (Boolean) element.get("edit");
						boolean publishProperty = (Boolean) element.get("publish");
						boolean editPermissionProperty = (Boolean) element.get("editPermission");
						boolean vanityUrlProperty = (Boolean) element.get("vanityUrl");
						
						for(WebElement row : subper){
							List<WebElement> propertiesColumns = row.findElements(By.tagName("td"));
							if(propertiesColumns.size() == 7){
								if(propertiesColumns.get(0).getText().trim().contains(property)){
									if(viewProperty){
										propertiesColumns.get(1).findElement(By.tagName("input")).click();
									}
									if(addChildrenProperty){
										propertiesColumns.get(2).findElement(By.tagName("input")).click();
									}
									if(editProperty){
										propertiesColumns.get(3).findElement(By.tagName("input")).click();
									}
									if(publishProperty){
										propertiesColumns.get(4).findElement(By.tagName("input")).click();
									}
									if(editPermissionProperty){
										propertiesColumns.get(5).findElement(By.tagName("input")).click();
									}
									if(vanityUrlProperty){
										propertiesColumns.get(6).findElement(By.tagName("input")).click();
									}
									break;
								}
							}
						}
					}
				}
				getWebElement(By.id("permissionsAccordionPane-"+id)).findElement(By.className("permissionsActions")).findElement(By.cssSelector("span[class='dijitReset dijitInline dijitButtonText']")).click();
				
				
				Evaluator eval = new Evaluator() {
					public boolean evaluate() throws Exception {  // returns true if host copy is done
						boolean wasSaved = !getWebElement(By.id("disabledZone")).isDisplayed();
						return wasSaved;
					}
					
				};
				//wait until 15min to check if the permission where saved
				pollForValue(eval, true, 5000, 120);
			}

		}
	}

	/**
	 * Delete a specific role
	 * @param roleName  Role name
	 * @throws Exception
	 */
	public void deleteRole(String roleName) throws Exception{
		//get role
		loadRole(roleName);
		
		deleteRoleButton_label.click();
		switchToAlert().accept();
	}
}
