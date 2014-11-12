package com.dotcms.qa.selenium.pages.backend;

import com.dotcms.qa.selenium.pages.IBasePage;
/**
 * Users Page Interface
 * @author Oswaldo Gallango
 * @since 11/12/2014
 * @version 1.0
 *
 */
public interface IUsersPage extends IBasePage {
	/**
	 * Validates if the user exist in the user manager page, looking by the user email address
	 * @param email User Email
	 * @return boolean, true if the user exist, false if not.
	 */
	public boolean doesUserEmailExist(String email);
	
	/**
	 * Add a user in dotCMS
	 * @param firstname User firstname
	 * @param lastname User lastname
	 * @param email User email address
	 * @param password User password
	 */
	public void addUser(String firstname, String lastname, String email, String password);
}
