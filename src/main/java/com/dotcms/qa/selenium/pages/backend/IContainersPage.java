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
}
