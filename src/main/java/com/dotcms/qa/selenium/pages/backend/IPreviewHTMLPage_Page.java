package com.dotcms.qa.selenium.pages.backend;

import com.dotcms.qa.selenium.pages.IBasePage;

/**
 * Preview HTMLPage Interface
 * @author Oswaldo Gallango
 * @since 11/24/2014
 * @version 1.0
 *
 */
public interface IPreviewHTMLPage_Page extends IBasePage {
	
	/**
	 * Return current page  language
	 * @return String
	 * @throws Exception
	 */
	public String getCurrentLanguage() throws Exception;
	
	/**
	 * Change current page language
	 * @param language Language Name
	 * @throws Exception
	 */
	public void changeLanguage(String language) throws Exception;

	public String getPageSource()  throws Exception;
}
