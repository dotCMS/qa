package com.dotcms.qa.selenium.pages.backend;

import com.dotcms.qa.selenium.pages.IBasePage;

/**
 * Templates Page Interface
 * @author Oswaldo Gallango
 *
 */
public interface ITemplatesPage extends IBasePage {
	
	/**
	 * Return the number of templates created for that host 
	 * @param hostName Name of the Host
	 * @return int 
	 * @throws Exception
	 */
	public int getNumberOfHostTemplates(String hostName) throws Exception;


}
