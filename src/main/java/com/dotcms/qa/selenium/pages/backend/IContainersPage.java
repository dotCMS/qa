package com.dotcms.qa.selenium.pages.backend;

import com.dotcms.qa.selenium.pages.IBasePage;

/**
 * Containers Page Interface
 * @author Oswaldo Gallango
 * @since 03/10/2015
 * @version 1.0
 *
 */ 
public interface IContainersPage extends IBasePage {

	/**
	 * Returns the add a new container page
	 * @return IContentAddOrEdit_ContentPage
	 * @throws Exception
	 */
	public IContainerAddOrEditPage getAddContainerPage() throws Exception;
	
	/**
	 * Add the container to a particular bundle 
	 * @param containerName   Name of the container
	 * @param bundleName      Name of the bundle
	 * @throws Exception
	 */
	public void addToBundle(String containerName, String bundleName) throws Exception;
	
	/**
	 * Delete the specified container
	 * @param containerName Name of the container
	 * @throws Exception
	 */
	public void deleteContainer(String containerName) throws Exception;
	
	/**
	 * Validate if the specified container exist
	 * @param containerName Name of the container
	 * @return true if the containers exist, false if not
	 * @throws Exception
	 */
	public boolean existContainer(String containerName) throws Exception;
	
	/**
	 * Returns the edit container page
	 * @param containerName Container Name
	 * @return IContentAddOrEdit_ContentPage
	 * @throws Exception
	 */
	public IContainerAddOrEditPage getEditContainerPage(String containerName) throws Exception;
	
	/**
	 * Click the push publish option from the right click menu options
	 * @param containerName Container Name
	 * @throws Exception
	 */
	public void pushContainer(String containerName) throws Exception;
	
	/**
	 * Click the push publish option from the right click menu options
	 * @param containerName Container name
	 * @param pushType   Type of push to apply (PUSH_TO_REMOVE,PUSH_TO_ADD,PUSH_AND_REMOVE)
	 * @param pushDate   Date with format M/d/yyyy(optional)
	 * @param pushTime   Time format H:mm a (optional)
	 * @param expireDate Date with format M/d/yyyy(optional)
	 * @param expireTime Time format H:mm a (optional)
	 * @param force      Force to publish (Only valid for push and push a remove)
	 * @throws Exception
	 */
	public void pushContent(String containerName, String pushType, String pushDate, String pushTime, String expireDate, String expireTime, boolean force) throws Exception;
	
}
