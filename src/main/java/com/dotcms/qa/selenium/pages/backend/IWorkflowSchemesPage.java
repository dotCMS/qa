package com.dotcms.qa.selenium.pages.backend;

import com.dotcms.qa.selenium.pages.IBasePage;

/**
 * Workflow Schemes Page Interface
 * @author Oswaldo Gallango
 * @since 04/30/2015
 * @version 1.0
 *
 */ 
public interface IWorkflowSchemesPage extends IBasePage {

	/**
	 * Returns the add a new Scheme page
	 * @return IWorkflowSchemeAddOrEditPage
	 * @throws Exception
	 */
	public IWorkflowSchemeAddOrEditPage getAddSchemePage() throws Exception;
	
	/**
	 * Returns edit scheme steps page
	 * @param workflowName Name of the workflow
	 * @return IWorkFlowStepsAddOrEdit_Page
	 * @throws Exception
	 */
	public IWorkFlowStepsAddOrEdit_Page getEditSchemeStepsPage(String workflowName) throws Exception;
	
	/**
	 * Validate if a Workflow Scheme exist
	 * @param workflowName Name of the workflow scheme
	 * @return boolean
	 * @throws Exception
	 */
	public boolean doesWorkflowSchemeExist(String workflowName) throws Exception;
}
