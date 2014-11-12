package com.dotcms.qa.selenium.pages.backend.common;

import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.dotcms.qa.selenium.pages.backend.IUsersPage;
import com.dotcms.qa.selenium.pages.common.BasePage;
import com.dotcms.qa.selenium.util.SeleniumPageManager;

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
	 * Add User Main button (Display the Add User form)
	 */
	private WebElement dijit_form_Button_5;
	/**
	 * Save button
	 */
	private WebElement dijit_form_Button_6;
	/**
	 * User Detail inputs
	 */
	private WebElement firstName;
	private WebElement lastName;
	private WebElement emailAddress;
	private WebElement password;
	private WebElement passwordCheck;
	
	/**
	 * Sleep method
	 */
	public void sleep() {
		try{
			Thread.sleep(200);
		}catch(Exception e){
			logger.error(e);
		}
	}

	public UsersPage(WebDriver driver) {
		super(driver);
	}

	/**
	 * Validates if the user exist in the user manager page, looking by the user email address
	 * @param email User Email
	 * @return boolean, true if the user exist, false if not.
	 */
	public boolean doesUserEmailExist(String email) {
		boolean retValue = false;
		usersFilter.clear();
		usersFilter.sendKeys(email);
		sleep();
		List<WebElement> rows = getWebElementPresent(By.id("dijit_layout_ContentPane_2")).findElements(By.tagName("td"));
		for(WebElement row : rows) {
			try {
				if(row.getText().trim().equals(email)) {
					retValue = true;
					break;
				}
			}catch(NoSuchElementException e) {
				logger.trace("Row does not include td element", e);
				// Move on to next row and keep going
			}catch(Exception e) {
				logger.error("Unexpected error attempting to iterate over Users - email=" + email, e);
				// Move on to next row and keep going
			}
		}
		return retValue;		
	}

	/**
	 * Add a user in dotCMS
	 * @param firstname User firstname
	 * @param lastname User lastname
	 * @param email User email address
	 * @param password User password
	 */
	public void addUser(String firstname, String lastname, String email, String userPassword){
		dijit_form_Button_5.click();
		
		firstName.sendKeys(firstname);
		lastName.sendKeys(lastname);
		emailAddress.sendKeys(email);
		password.sendKeys(userPassword);
		passwordCheck.sendKeys(userPassword);
		
		dijit_form_Button_6.click();
		
	}
}
