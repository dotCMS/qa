package com.dotcms.qa.selenium.pages.backend.common;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.dotcms.qa.selenium.pages.backend.IUsersPage;
import com.dotcms.qa.selenium.pages.common.BasePage;
import com.dotcms.qa.selenium.util.SeleniumConfig;
import com.dotcms.qa.util.WebKeys;

/**
 * This class implements the methods defined in the IUserPage interface
 * to do the Users TestRail validations
 * @author Oswaldo Gallango
 * @since 11/12/2014
 * @version 1.0
 * 
 */
public class UsersPage extends BasePage implements IUsersPage {
	private static final Logger logger = Logger.getLogger(UsersPage.class);

	/**
	 * User Filter input field
	 */
	private WebElement usersFilter;

	/**
	 * User Details Tab
	 */
	private WebElement userTabsContainer_tablist_userDetailsTab;

	/**
	 * User roles tab 
	 */
	private WebElement userTabsContainer_tablist_userRolesTab;

	/**
	 * Add Role button 
	 */
	private WebElement	addUserRoleBtn_label;

	/**
	 * Remove Role button 
	 */
	private WebElement	removeUserRoleBtn_label;

	/**
	 * Marketing tab
	 */
	private WebElement userTabsContainer_tablist_marketingInfoTab;

	/**
	 * Add Tags textarea 
	 */
	private WebElement tagName;

	/**
	 * Tags Suggestions
	 */
	private WebElement suggestedTagsDiv;

	/**
	 * Show Selected tags
	 */
	private WebElement tags_table;

	/**
	 * Roles granted select box
	 */
	private WebElement userRolesSelect;

	/**
	 * Get the user visit history 
	 */
	private WebElement userClickHistoryPane;

	/**
	 * User Detail inputs
	 */
	private WebElement firstName;
	private WebElement lastName;
	private WebElement emailAddress;
	private WebElement password;
	private WebElement passwordCheck;
	private WebElement userIdValue;

	public UsersPage(WebDriver driver) {
		super(driver);
	}

	/**
	 * Validates if the user exist in the user manager page, looking by the user email address
	 * @param email User Email
	 * @return boolean, true if the user exist, false if not.
	 */
	public boolean doesUserEmailExist(String email) {
		//if the user info is load is because the user exist
		return selectUser(email);		
	}

	/**
	 * Add a user in dotCMS
	 * @param firstname User first name
	 * @param lastname User last name
	 * @param email User email address
	 * @param password User password
	 */
	public void addUser(String firstname, String lastname, String email, String userPassword){
		//Click the add user button
		getAddUserButton().click();
		sleep();
		//set the user parameters
		firstName.sendKeys(firstname);
		lastName.sendKeys(lastname);
		emailAddress.sendKeys(email);
		password.sendKeys(userPassword);
		passwordCheck.sendKeys(userPassword);
		sleep();
		//Click the save button
		getSaveUserButton().click();
	}

	/**
	 * Update the properties/fields set in the map for the user with the specified email address
	 * @param email Current user email address 
	 * @param properties User inputs to modify
	 * @return true if the user was updated, false if not
	 */
	public boolean editUser(String email, Map<String,String> properties){
		boolean retValue = false;
		//Search for the user info
		if(selectUser(email)){
			//Load User details tab
			userTabsContainer_tablist_userDetailsTab.click();
			sleep();
			//edit the user properties
			for(String key : properties.keySet()){
				if(key.equals("firstName")){
					firstName.clear();
					firstName.sendKeys(properties.get(key));
				}else if(key.equals("lastName")){
					lastName.clear();
					lastName.sendKeys(properties.get(key));
				}else if(key.equals("emailAddress")){
					emailAddress.clear();
					emailAddress.sendKeys(properties.get(key));
				}else if(key.equals("password")){
					//password.clear();
					//password.sendKeys(properties.get(key));
				}else if(key.equals("passwordCheck")){
					//passwordCheck.clear();
					//passwordCheck.sendKeys(properties.get(key));
				}
			}

			//save the changes
			getSaveUserButton().click();
			retValue = true;
		}
		return retValue;
	}

	/**
	 * Get the map with the properties of the user to validate
	 * @param email User email to search
	 * @return Map with the user pages properties
	 */
	public Map<String,String> getUserProperties(String email){
		Map<String,String> properties = new HashMap<String, String>();
		if(selectUser(email)){

			//Load User details tab
			userTabsContainer_tablist_userDetailsTab.click();
			sleep();
			//load user properties
			properties.put("firstName", firstName.getAttribute("value"));
			properties.put("lastName", lastName.getAttribute("value"));
			properties.put("emailAddress", emailAddress.getAttribute("value"));
			properties.put("userId", userIdValue.getText());

		}
		return properties;
	}

	/**
	 * Add the specified role to user.
	 * @param roleName Role to add
	 * @param userEmail User email to search
	 * @return true is the role was added, false if not
	 */
	public boolean addRoleToUser(String roleName, String userEmail){
		boolean retValue = false;
		if(selectUser(userEmail)){
			//load roles tab
			userTabsContainer_tablist_userRolesTab.click();
			sleep();
			//Expand roles tree for search until second level deep
			List<WebElement> imgs = getWebElementsPresent(By.cssSelector("img[class='dijitTreeExpando dijitTreeExpandoClosed'][src='/html/js/dojo/release/dojo/dojo/resources/blank.gif']"));
			for(WebElement img :  imgs){
				try{
					img.click();
				}catch(Exception e){
					logger.trace("error expanding roles", e);
				}
			}
			sleep();
			//checking roles list
			List<WebElement> spans = getWebElementPresent(By.id("treeNode-root")).findElements(By.tagName("span"));
			for(WebElement elem :  spans){
				List<WebElement> labels = elem.findElements(By.tagName("label"));
				for(WebElement element : labels){
					String id = element.getAttribute("id");
					if(element.getText().trim().equals(roleName)){
						//Select checkbox
						String checkboxId = element.getAttribute("id");
						checkboxId = checkboxId.replace("_label", "")+"_chk";
						WebElement checkbox = getWebElementPresent(By.id("treeNode-root")).findElement(By.id(checkboxId));
						checkbox.click();
						sleep();
						//Add role to roles granted list
						addUserRoleBtn_label.click();
						sleep();
						//Save role change
						getSaveUserRoleButton().click();
						retValue = true;
						break;
					}
				}
				if(retValue){
					break;
				}
			}
		}
		return retValue;
	}

	/**
	 * Remove the specified role from the user.
	 * @param roleName Role to remove
	 * @param userEmail User email to search
	 * @return true is the role was removed, false if not
	 */
	public boolean removeRoleFromUser(String roleName, String userEmail){
		boolean retValue = false;
		if(selectUser(userEmail)){
			//load roles tab
			userTabsContainer_tablist_userRolesTab.click();
			sleep();
			List<WebElement> options = userRolesSelect.findElements(By.tagName("option"));
			for(WebElement option : options){
				if(option.getAttribute("title").indexOf(roleName) != -1){
					retValue=true;
					option.click();
					if(!getBrowserName().equals(WebKeys.SAFARI_BROWSER_NAME)){
						option.click();
					}
				}else{
					option.click();
				}
			}
			//remove from select lisst
			removeUserRoleBtn_label.click();
			sleep();
			//save role change
			getSaveUserRoleButton().click();
		}
		return retValue;
	}

	/**
	 * Validate if the user have assigned the specified role
	 * @param roleName Role to remove
	 * @param userEmail User email to search
	 * @return true is the role is assigned, false if not
	 */
	public boolean doesUserHaveRole(String roleName, String userEmail){
		boolean retValue = false;
		if(selectUser(userEmail)){
			//load roles tab
			userTabsContainer_tablist_userRolesTab.click();
			sleep();
			List<WebElement> options = userRolesSelect.findElements(By.tagName("option"));
			for(WebElement option : options){
				if(option.getAttribute("title").indexOf(roleName) != -1){
					retValue = true;
					break;
				}
			}
		}
		return retValue;
	}

	/**
	 * Load the user info if the user exist.
	 * @param userEmail User email address
	 * @return true is the User info was load, false if not
	 */
	private boolean selectUser(String userEmail){
		boolean retValue = false;
		usersFilter.clear();
		usersFilter.sendKeys(userEmail);
		sleep(2);
		//Search in the right panel for the search results
		List<WebElement> rows = getWebElementPresent(By.id("dijit_layout_ContentPane_2")).findElements(By.tagName("td"));
		for(WebElement row : rows) {
			try {
				if(row.getText().trim().equals(userEmail)) {
					//load the user form
					row.click();
					sleep();
					retValue = true;
					break;
				}
			}catch(NoSuchElementException e) {
				logger.trace("Row does not include td element", e);
				// Move on to next row and keep going
			}catch(Exception e) {
				logger.error("Unexpected error attempting to iterate over Users - email=" + userEmail, e);
				// Move on to next row and keep going
			}
		}
		return retValue;
	}

	/**
	 * Add a tag to a user in the user admin portlet
	 * @param tag Tag to add
	 * @param userEmail User email to search
	 */
	public void addTag(String tag, String userEmail){
		//Search for the user a validate
		if(selectUser(userEmail)){
			//open the marketing tab
			if(getBrowserName().equals(WebKeys.CHROME_BROWSER_NAME)){
				//chrome have issue with some web elements in that cases we use javascript calls
				executeJavaScript("document.getElementById('userTabsContainer_tablist_marketingInfoTab').click()");
				executeJavaScript("document.getElementById('tagName').value='"+tag+"'");
			}else{
				userTabsContainer_tablist_marketingInfoTab.click();
				sleep();
				//set the tag in the textarea
				tagName.clear();
				tagName.sendKeys(tag);
			}
			//press the update button
			getAddUserTagButton().click();

		}
	}

	/**
	 * Validates if the user have assigned that tag in the user admin portlet
	 * @param tag Tag to add
	 * @param userEmail User email to search
	 * @return true if the user have the tag, false if not
	 */
	public boolean doesHaveTag(String tag, String userEmail){
		boolean retValue = false;
		//Search for the user a validate
		if(selectUser(userEmail)){
			//open the marketing tab
			if(getBrowserName().equals(WebKeys.CHROME_BROWSER_NAME)){
				//chrome have issue with some web elements in that cases we use javascript calls
				executeJavaScript("document.getElementById('userTabsContainer_tablist_marketingInfoTab').click()");
			}else{
				userTabsContainer_tablist_marketingInfoTab.click();
			}
			sleep();
			//get the list of tags
			List<WebElement> tagsList = tags_table.findElements(By.tagName("tr"));
			for(WebElement row : tagsList){
				List<WebElement> columns = row.findElements(By.tagName("td"));
				for(WebElement column : columns){
					if(column.getText().equalsIgnoreCase(tag)){
						retValue=true;
						break;
					}
				}
				if(retValue){
					break;
				}
			}
		}
		return retValue;
	}

	/**
	 * Remove a user tag in the user admin portlet
	 * @param tag Tag to add
	 * @param userEmail User email to search
	 */
	public void removeTag(String tag,String userEmail){
		boolean retValue = false;
		//Search for the user a validate
		if(selectUser(userEmail)){
			//open the marketing tab
			if(getBrowserName().equals(WebKeys.CHROME_BROWSER_NAME)){
				//chrome have issue with some web elements in that cases we use javascript calls
				executeJavaScript("document.getElementById('userTabsContainer_tablist_marketingInfoTab').click()");
			}else{
				userTabsContainer_tablist_marketingInfoTab.click();
			}
			sleep();
			//get the list of tags
			List<WebElement> tagsList = tags_table.findElements(By.tagName("tr"));
			for(WebElement row : tagsList){
				//search for the row with the specified tag
				List<WebElement> columns = row.findElements(By.tagName("td"));
				for(WebElement column : columns){
					if(column.getText().equalsIgnoreCase(tag)){
						retValue=true;
						break;
					}
				}
				//remove the tag if exist
				if(retValue){
					WebElement removebutton = row.findElement(By.tagName("a"));
					if(getBrowserName().equals(WebKeys.CHROME_BROWSER_NAME)){
						String value = removebutton.getAttribute("href");
						//chrome have issue with some web elements in that cases we use javascript calls
						executeJavaScript("var v = document.getElementById('tags_table').getElementsByTagName('a');for(var i =0;i < v.length; i++){if(v[i].href==\""+value+"\"){v[i].click();break;}}");
					}else{
						removebutton.click();
					}
					break;
				}
			}
		}
	}

	/**
	 * Search the add user button dynaically, because doesn't have a fixed id and
	 * dojo change it some times
	 */
	private WebElement getAddUserButton(){
		List<WebElement> buttonsArea = getWebElementPresent(By.className("buttonBoxRight")).findElements(By.cssSelector("span[class='dijitReset dijitInline dijitButtonText']"));
		WebElement addUserButton = null;
		for(WebElement span : buttonsArea){
			if(span.getText().equals(getLocalizedString("Add-User"))){
				addUserButton = span;
				break;
			}
		}
		return addUserButton;
	}

	/**
	 * Search the add user button dynaically, because doesn't have a fixed id and
	 * dojo change it some times
	 */
	private WebElement getSaveUserButton(){
		List<WebElement> buttonsArea = getWebElementPresent(By.id("userDetailsTab")).findElements(By.cssSelector("span[class='dijitReset dijitInline dijitButtonText']"));
		WebElement saveUserButton = null;
		for(WebElement span : buttonsArea){
			if(span.getText().equals(getLocalizedString("Save"))){
				saveUserButton = span;
				break;
			}
		}
		return saveUserButton;
	}

	/**
	 * Search the add user button dynaically, because doesn't have a fixed id and
	 * dojo change it some times
	 */
	private WebElement getSaveUserRoleButton(){
		List<WebElement> buttonsArea = getWebElementPresent(By.id("userRolesTab")).findElements(By.cssSelector("span[class='dijitReset dijitInline dijitButtonText']"));
		WebElement saveUserButton = null;
		for(WebElement span : buttonsArea){
			if(span.getText().equals(getLocalizedString("Save"))){
				saveUserButton = span;
				break;
			}
		}
		return saveUserButton;
	}

	/**
	 * Search the add user button dynaically, because doesn't have a fixed id and
	 * dojo change it some times
	 */
	private WebElement getAddUserTagButton(){
		List<WebElement> buttonsArea = getWebElementPresent(By.id("marketingInfoWrapper")).findElements(By.cssSelector("span[class='dijitReset dijitInline dijitButtonText']"));
		WebElement addUserButton = null;
		for(WebElement span : buttonsArea){
			if(span.getText().equals(getLocalizedString("update"))){
				addUserButton = span;
				break;
			}
		}
		return addUserButton;
	}

	/**
	 * Validate if the user have some visit history in the
	 * user admin portlet
	 * @param userEmail User email to search
	 * @return
	 */
	public boolean doesHaveVisitHistory(String userEmail){
		boolean retValue = false;
		//open the marketing tab
		if(getBrowserName().equals(WebKeys.CHROME_BROWSER_NAME)){
			//chrome have issue with some web elements in that cases we use javascript calls
			executeJavaScript("document.getElementById('userTabsContainer_tablist_marketingInfoTab').click()");
		}else{
			userTabsContainer_tablist_marketingInfoTab.click();
		}
		sleep();
		//check Full Visit History
		List<WebElement> buttonsArea = getWebElementPresent(By.id("marketingInfoWrapper")).findElements(By.cssSelector("span[class='dijitReset dijitInline dijitButtonText']"));
		for(WebElement span : buttonsArea){
			if(span.getText().equals(getLocalizedString("Full-Visit-History"))){
				span.click();
				break;
			}
		}
		//get history
		String value = userClickHistoryPane.getText();
		if(!value.equals("User has no visitor data available.")){
			retValue = true;
		}
		getWebElementPresent(By.id("userClickHistoryDialog")).findElement(By.className("dijitDialogCloseIcon")).click();
		return retValue;
	}

	/**
	 * Return the tag suggestions for the text passed
	 * @param userEmail User email to search
	 * @param tagText Base text to search
	 * @return The string suggestion for the tag text set
	 */
	public String getTagSuggestions(String tagText, String userEmail){
		String retValue="";
		//Search for the user a validate
		if(selectUser(userEmail)){
			//open the marketing tab
			if(getBrowserName().equals(WebKeys.CHROME_BROWSER_NAME)){
				//chrome have issue with some web elements in that cases we use javascript calls
				executeJavaScript("document.getElementById('userTabsContainer_tablist_marketingInfoTab').click()");
			}else{
				userTabsContainer_tablist_marketingInfoTab.click();
			}
			sleep(1);
			//set the tag in the textarea to get the suggestions
			tagName.sendKeys(tagText);
			sleep();
			//get the list of tags
			List<WebElement> tagSuggestionsList = suggestedTagsDiv.findElements(By.tagName("a"));
			for(WebElement a : tagSuggestionsList){
				retValue+=a.getText().trim()+",";
			}
		}
		return retValue;
	}
}
