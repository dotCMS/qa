package com.dotcms.qa.selenium.pages.backend;

import com.dotcms.qa.selenium.pages.IBasePage;

/**
 * Configuration Page Interface
 * @author Oswaldo Gallango
 * @since 03/06/2015
 * @version 1.0
 *
 */ 
public interface IConfigurationPage extends IBasePage {

	/**
	 * Get the Publishing Environments tab in the configuration portlet
	 * @return IPublishingEnvironments
	 * @throws Exception
	 */
	public IPublishingEnvironments getPublishingEnvironmentsTab() throws Exception;
	
}
