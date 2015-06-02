package com.dotcms.qa.selenium.pages.backend;

import com.dotcms.qa.selenium.pages.IBasePage;

/**
 * Workflow Schemes steps Add or Edit Page Interface
 * @author Oswaldo Gallango
 * @since 05/04/2015
 * @version 1.0
 *
 */ 
public interface IWorkFlowStepsAddOrEdit_Page extends IBasePage{

	/**
	 * Add a new step on the workflow scheme
	 * @param stepName Name of the step
	 * @throws Exception
	 */
	public void addWorkflowStep(String stepName) throws Exception;
	
	/**
	 * Edit workflow step properties
	 * @param stepName     Current stepName
	 * @param newStepName  New Step Name
	 * @param stepOrder        Step order 
	 * @param resolveTask  if step is resolved task
	 * @param scheduleEnable if step is schedule enable
	 * @param scheduleAction scheduled action
	 * @param scheduleIn     schedule in value
	 * @throws Exception
	 */
	public void editWorkflowStep(String stepName, String newStepName, String stepOrder, boolean resolveTask, boolean scheduleEnable, String scheduleAction, String scheduleIn) throws Exception;
	
	/**
	 * Add a new action in the specified step
	 * @param stepName Name of the step
	 * @return IWorkflowActionAddOrEdit_Page
	 * @throws Exception
	 */
	public IWorkflowActionAddOrEdit_Page addActionToStep(String stepName) throws Exception;
	
	/**
	 * Validates if a step exist in the workflow scheme
	 * @param stepName Name of the step
	 * @return boolean
	 * @throws Exception
	 */
	public boolean doesWorkflowStepExist(String stepName) throws Exception;
	
	/**
	 *  Delete step on Workflow scheme 
	 * @param stepName	Name of the step
	 * @throws Exception
	 */
	public void deleteStep(String stepName) throws Exception;
	
	/**
	 * Edit the specified action under the requested step
	 * @param stepName Step Name
	 * @param actionName Action Name
	 * @return IWorkflowActionAddOrEdit_Page
	 * @throws Exception
	 */
	public IWorkflowActionAddOrEdit_Page editWorkflowAction(String stepName, String actionName) throws Exception;
	
	/**
	 * Validated if the step action exist
	 * @param stepName   Name of the step
	 * @param actionName Name of the action
	 * @return true if exist, false if not
	 * @throws Exception
	 */
	public boolean doesWorkflowStepActionExist(String stepName, String actionName) throws Exception;
}
