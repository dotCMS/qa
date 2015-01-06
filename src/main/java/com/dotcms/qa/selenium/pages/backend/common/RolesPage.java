package com.dotcms.qa.selenium.pages.backend.common;

import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.dotcms.qa.selenium.pages.backend.IRolesPage;
import com.dotcms.qa.selenium.pages.common.BasePage;
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
	 * Creates a role
	 * @param name Name of the role to create
	 * @param key Role Key
	 * @param description Role description
	 * @param canEditUser Check the edit user checkbox
	 * @param canEditPermision Check the edit permissions checkbox
	 * @param canEitTabs Check the edit tabs checkbox
	 */
	public void createRole(String name, String key, String description, boolean canEditUser, boolean canEditPermision, boolean canEitTabs){
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
	public boolean doesRoleExist(String roleName){
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
			}catch(NoSuchElementException e) {
				logger.trace("Row does not include td element", e);
				// Move on to next row and keep going
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
	public void removeRole(String roleName){
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
			}catch(NoSuchElementException e) {
				logger.trace("Row does not include td element", e);
				// Move on to next row and keep going
			}catch(Exception e) {
				logger.error("Unexpected error attempting to iterate over Roles - name=" + roleName, e);
				// Move on to next row and keep going
			}
		}
		if(retValue){
			sleep();
			getDeleteRoleButton().click();
			sleep();
			if(getBrowserName().equals(WebKeys.SAFARI_BROWSER_NAME)){
				Alert alert = this.switchToAlert();
				alert.accept();
			}else{
				this.switchToAlert().accept();
			}
		}

	}

	/**
	 * Search the add role button dynamically, because doesn't have a fixed id and
	 * dojo change it some times
	 */
	private WebElement getAddRoleButton(){
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
	private WebElement getSaveRoleButton(){
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
	private WebElement getDeleteRoleButton(){
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
	public boolean addPortletToRolesTabs(String roleName, String tabName, String portletName){
		boolean retValue=false;
		rolesFilter.clear();
		rolesFilter.sendKeys(roleName);
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
						layoutName.sendKeys(tabName);
						//set the portlet to include
						portletList.sendKeys(portletName);
					}else{
						//open the tab
						selectedTab.click();
						//set the portlet to include
						portletList.sendKeys(portletName);
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
				logger.trace("Row does not include td element", e);
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
	private WebElement getCreateCustomTabButton(){
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
	private WebElement getAddToolButton(){
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
	private WebElement getSaveTabButton(){
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
	public boolean removeTabFromRole(String roleName, String tabName){
		boolean retValue=false;
		rolesFilter.clear();
		rolesFilter.sendKeys(roleName);
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
			}catch(NoSuchElementException e) {
				logger.trace("Row does not include td element", e);
				// Move on to next row and keep going
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
	private WebElement getDeleteTabButton(){
		List<WebElement> buttonsArea = getWebElementPresent(By.id("newLayoutForm")).findElements(By.cssSelector("span[class='dijitReset dijitInline dijitButtonText']"));
		WebElement deleteTabButton = null;
		for(WebElement span : buttonsArea){
			if(span.getText().equals(getLocalizedString("Delete"))){
				deleteTabButton = span;
				break;
			}
		}
		return deleteTabButton;
	}
}
