package com.dotcms.qa.selenium.pages.backend;

import java.util.List;

import com.dotcms.qa.selenium.pages.IBasePage;

/**
 * Mailing List Page Interface
 * @author Oswaldo Gallango
 * @since 11/24/2014
 * @version 1.0
 *
 */
public interface IMailingListPage extends IBasePage {

	/**
	 * Import a csv user file in dotcms
	 * @return true if the user where imported, false if not
	 */
	public boolean loadUsers(String mailingListName);
	
	/**
	 * Get the list of email addresses of the user subscribed to the mailing list
	 * @param mailingList Mailing List name
	 * @return List<String> with the susbcribers emails
	 */
	public List<String> getMailingListSubscribers(String mailingList);
	
	/**
	 * Delete the  mailing list
	 * @param mailingList Mailing List name
	 * @return true if the list was delete, false if not
	 */
	public boolean deleteMailingList(String mailingList);
}
