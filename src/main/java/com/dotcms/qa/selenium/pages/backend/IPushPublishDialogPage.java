package com.dotcms.qa.selenium.pages.backend;

import com.dotcms.qa.selenium.pages.IBasePage;

/**
 * Push Publishing dialog Page Interface
 * @author Oswaldo Gallango
 * @since 05/08/2015
 * @version 1.0
 *
 */ 
public interface IPushPublishDialogPage extends IBasePage {

	/**
	 * Push the specified contentlet,containers,structure,category,template,htmlpage,etc
	 * @param pushType   Type of push to apply (PUSH_TO_REMOVE,PUSH_TO_ADD,PUSH_AND_REMOVE)
	 * @param pushDate   Date with format M/d/yyyy(optional)
	 * @param pushTime   Time format H:mm a (optional)
	 * @param expireDate Date with format M/d/yyyy(optional)
	 * @param expireTime Time format H:mm a (optional)
	 * @param force      Force to publish (Only valid for push and push a remove)
	 * @throws Exception
	 */
	public void push(String pushType, String pushDate, String pushTime, String expireDate, String expireTime, boolean force) throws Exception;
}
