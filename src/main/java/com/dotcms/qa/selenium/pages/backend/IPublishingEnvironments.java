package com.dotcms.qa.selenium.pages.backend;

import java.util.List;

import com.dotcms.qa.selenium.pages.IBasePage;

/**
 * PublishingEnvironments Page Interface
 * @author Oswaldo Gallango
 * @since 03/06/2015
 * @version 1.0
 *
 */ 
public interface IPublishingEnvironments extends IBasePage {
	
	/**
	 * Generates a new Send to environment
	 * @param environmentName String name of the new environment
	 * @param whocanUse String list with the User or roles who can use the push publish
	 * @param pushMode String indicating if the push option is pushToOne or pushToAll
	 * @throws Exception
	 */
	public void createEnvironment(String environmentName, List<String> whocanUse, String pushMode) throws Exception;

}
