package com.dotcms.qa.selenium.pages.backend;

import com.dotcms.qa.selenium.pages.IBasePage;


/**
 * Workflow Tasks Page Interface
 * @author Oswaldo Gallango
 * @since 05/12/2015
 * @version 1.0
 *
 */ 
public interface IWorkflowTasksPage extends IBasePage{


	/**
	 * Get the current step of the specified workflow taks
	 * @param title   Title of the content
	 * @param workflowScheme Scheme
	 * @return String
	 * @throws Exception
	 */
	public String getWorflowTaskCurrentStep(String title, String workflowScheme) throws Exception;
	
	/**
	 * Get the the specified workflow task
	 * @param title   Title of the content
	 * @param workflowScheme Scheme
	 * @return IWorkflowTaskEdit_Page
	 * @throws Exception
	 */
	public IWorkflowTaskEdit_Page getWorflowTask(String title, String workflowScheme) throws Exception;
	
	/**
	 * validate if a workflows task exist
	 * @param title   Title of the content
	 * @param workflowScheme Scheme
	 * @return true if exist , false if not
	 * @throws Exception
	 */
	public boolean doesWorflowTaskExist(String title, String workflowScheme) throws Exception;

}
