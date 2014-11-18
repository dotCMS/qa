package com.dotcms.qa.selenium.pages.backend.common;

import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.dotcms.qa.selenium.pages.backend.IRolesPage;
import com.dotcms.qa.selenium.pages.common.BasePage;

/**
 * This class implements the methods defined in the IRolesPage interface
 * to do the Users TestRail validations
 * @author Oswaldo Gallango
 * @since 11/18/2014
 * @version 1.0
 * 
 */
public class RolesPage extends BasePage implements IRolesPage {

	/**
	 * Sleep method
	 */
	public void sleep() {
		try{
			Thread.sleep(1000);
		}catch(Exception e){
			logger.error(e);
		}
	}

	/**
	 * Sleep method
	 * @param seconds
	 */
	public void sleep(int seconds) {
		try{
			Thread.sleep(seconds*1000);
		}catch(Exception e){
			logger.error(e);
		}
	}

	public RolesPage(WebDriver driver) {
		super(driver);
	}

	private static final Logger logger = Logger.getLogger(UsersPage.class);

	/**
	 * Role Name input
	 */
	private WebElement roleName;

	/**
	 * Role key input
	 */
	private WebElement roleKey;

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
			Alert alert = this.switchToAlert();
			alert.accept();
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
}
