package com.dotcms.qa.selenium.pages.backend;

import com.dotcms.qa.selenium.pages.IBasePage;

/**
 * Menu Link Interface
 * @author Oswaldo Gallango
 * @since 04/14/2015
 * @version 1.0
 */
public interface IMenuLinkPage extends IBasePage{

	/**
	 * Click the add link button and return IMenuLinkAddOrEdit_Page
	 * @return IMenuLinkAddOrEdit_Page
	 * @throws Exception
	 */
	public IMenuLinkAddOrEdit_Page addLink() throws Exception;
	
	/**
	 * Validate if a link exist
	 * @param linkTitle Link Name
	 * @return true if exist, false if not
	 * @throws Exception
	 */
	public boolean doesLinkExist(String linkTitle) throws Exception;
	
	/**
	 * Click the push publish option from the right click menu options
	 * @param linkTitle Link Name
	 * @throws Exception
	 */
	public void pushLink(String linkTitle) throws Exception;
	
	/**
	 * Delete a menu link
	 * @param linkTitle Link Name
	 * @throws Exception
	 */
	public void deleteLink(String linkTitle) throws Exception;
	
	/**
	 * Edit a menu link
	 * @param linkTitle Link Name
	 * @return IMenuLinkAddOrEdit_Page
	 * @throws Exception
	 */
	public IMenuLinkAddOrEdit_Page editLink(String linkTitle) throws Exception;
}
