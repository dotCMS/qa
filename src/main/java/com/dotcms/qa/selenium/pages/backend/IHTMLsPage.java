package com.dotcms.qa.selenium.pages.backend;

import com.dotcms.qa.selenium.pages.IBasePage;

/**
 * Manage the html portlet methods
 * @author Oswaldo Gallango
 * @since 03/24/2015
 * @version 1.0
 * 
 */
public interface IHTMLsPage extends IBasePage {

	/**
	 * Validates if a page exist
	 * @param pageTitle Page title
	 * @param templateTitle Template title
	 * @return true if the page exist, false if not
	 * @throws Exception
	 */
	public boolean doesHTMLPageExist(String pageTitle, String templateTitle) throws Exception;
	
	/**
	 * Click the push publish option from the right click menu options
	 * @param pageName HTML page Name
	 * @throws Exception
	 */
	public void pushHTMLPage(String pageName) throws Exception;
}
