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
	 * Search if a user exist in the user manager page
	 * @param email User Email
	 * @return boolean, true if the user exist, false if not.
	 */
	public boolean searchUserByEmail(String email);
}
