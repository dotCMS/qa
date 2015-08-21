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
	 * Click the push publish option from the right click menu options
	 * @param linkTitle Link Name
	 * @param pushType   Type of push to apply (PUSH_TO_REMOVE,PUSH_TO_ADD,PUSH_AND_REMOVE)
	 * @param pushDate   Date with format M/d/yyyy(optional)
	 * @param pushTime   Time format H:mm a (optional)
	 * @param expireDate Date with format M/d/yyyy(optional)
	 * @param expireTime Time format H:mm a (optional)
	 * @param force      Force to publish (Only valid for push and push a remove)
	 * @throws Exception
	 */
	public void pushLink(String linkTitle, String pushType, String pushDate, String pushTime, String expireDate, String expireTime, boolean force) throws Exception;
	
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
	
	/**
	 * Add the menu link to a particular bundle 
	 * @param linkName   Name of the link
	 * @param bundleName      Name of the bundle
	 * @throws Exception
	 */
	public void addToBundle(String linkName, String bundleName) throws Exception;
}
