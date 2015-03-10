package com.dotcms.qa.selenium.pages.backend;

import com.dotcms.qa.selenium.pages.IBasePage;

/**
 * Publishing Queue Page Interface
 * @author Oswaldo Gallango
 * @since 03/10/2015
 * @version 1.0
 *
 */ 
public interface IPublishingQueuePage extends IBasePage{

	/**
	 * Get the bundles tab active
	 * @throws Exception
	 */
	public void getBundlesTab() throws Exception;
	
	/**
	 * Search the especified bundle and click the push publish button
	 * @param bundleName Name of the bundle
	 * @throws Exception
	 */
	public void pushPublishBundle(String bundleName) throws Exception;
}
