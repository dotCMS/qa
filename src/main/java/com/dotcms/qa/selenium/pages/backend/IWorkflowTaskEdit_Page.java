package com.dotcms.qa.selenium.pages.backend;

import com.dotcms.qa.selenium.pages.IBasePage;

/**
 * Workflow Tasks Edit Page Interface
 * @author Oswaldo Gallango
 * @since 05/22/2015
 * @version 1.0
 *
 */ 
public interface IWorkflowTaskEdit_Page extends IBasePage{
	
	/**
	 * Select the specified action if is available
	 * @param actionName Action name
	 * @throws Exception
	 */
	public void selectAction(String actionName) throws Exception;

}
