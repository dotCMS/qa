package com.dotcms.qa.selenium.pages.backend;

import java.util.List;

import com.dotcms.qa.selenium.pages.IBasePage;

/**
 * Workflow Action Add or Edit Page Interface
 * @author Oswaldo Gallango
 * @since 05/05/2015
 * @version 1.0
 *
 */
public interface IWorkflowActionAddOrEdit_Page extends IBasePage{

	/**
	 * Set the action name
	 * @param workflowActionName Action name
	 * @throws Exception
	 */
	public void setActionName(String workflowActionName) throws Exception;

	/**
	 * Get the action Name
	 * @return String
	 * @throws Exception
	 */
	public String getActionName() throws Exception;

	/**
	 * Set if the teh user can save or not content
	 * @param saveContent Boolean
	 * @throws Exception
	 */
	public void setSaveContent(boolean saveContent) throws Exception;

	/**
	 * Indicate if the save content checkbox is set
	 * @return boolean
	 * @throws Exception
	 */
	public boolean isSaveContent() throws Exception;

	/**
	 * Add a user or roles to the list of who can use
	 * @param userRole Name of the role or user
	 * @throws Exception
	 */
	public void setWhoCanUse(String userRole) throws Exception;

	/**
	 * Get the list of user or roles that can use the action
	 * @return List<String>
	 * @throws Exception
	 */
	public List<String> getWhocanUse() throws Exception;

	/**
	 * Set if the action allows comments
	 * @param allowsComment Boolean
	 * @throws Exception
	 */
	public void setAllowComment(boolean allowsComment) throws Exception;

	/**
	 * Get if the action allows comments
	 * @return boolean
	 * @throws Exception
	 */
	public boolean areAllowedComments() throws Exception;

	/**
	 * Set If a user can assign
	 * @param userCanAssign
	 * @throws Exception
	 */
	public void setUserCanAssign(boolean userCanAssign) throws Exception;
	
	/**
	 * Get if a user can assign
	 * @param userCanAssign
	 * @return
	 * @throws Exception
	 */
	public boolean userCanAssign(boolean userCanAssign) throws Exception;
	
	/**
	 * Set the user or role that can assign this action
	 * @param userRole   Name of the user or role
	 * @throws Exception
	 */
	public void setAssignTo(String userRole) throws Exception;
	
	/**
	 * Get the name of the user or role that can assign
	 * @return String
	 * @throws Exception
	 */
	public String getAssignTo() throws Exception;
	
	/**
	 * Set the next step
	 * @param stepName Name of the next step
	 * @throws Exception
	 */
	public void setNextStep(String stepName) throws Exception;
	
	/**
	 * Get the next step name
	 * @return String
	 * @throws Exception
	 */
	public String getNextStep() throws Exception;
	
	/**
	 * Set the icon for this action
	 * @param iconName Name of the icon
	 * @throws Exception
	 */
	public void setIcon(String iconName) throws Exception;
	
	/**
	 * Get the name of the icon assigned to this action
	 * @return String
	 * @throws Exception
	 */
	public String getIcon() throws Exception;
	
	/**
	 * Set the custom code field
	 * @param code    The custom code
	 * @throws Exception
	 */
	public void setCustomCode(String code) throws Exception;
	
	/**
	 * Get the custom field
	 * @return String
	 * @throws Exception
	 */
	public String getCustomCode() throws Exception;
	
	/**
	 * Add the save button
	 * @throws Exception
	 */
	public void save() throws Exception;
	
	/**
	 * Click the cancel button
	 * @throws Exception
	 */
	public void cancel() throws Exception;

	/**
	 * Click the delete button
	 * @throws Exception
	 */
	public void delete() throws Exception;
	
	/**
	 * Add a subaction 
	 * @param worflowSubaction Name of the subaction
	 * @throws Exception
	 */
	public void addSubAction(String worflowSubaction) throws Exception;

}
