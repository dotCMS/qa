package com.dotcms.qa.selenium.pages.backend;

import com.dotcms.qa.selenium.pages.IBasePage;

/**
 * Workflow Schemes Add or Edit Page Interface
 * @author Oswaldo Gallango
 * @since 04/30/2015
 * @version 1.0
 *
 */ 
public interface IWorkflowSchemeAddOrEditPage extends IBasePage{

	/**
	 * Set the scheme name
	 * @param name Scheme name
	 * @throws Exception
	 */
	public void setName(String name) throws Exception;
	
	/**
	 * Get the scheme name
	 * @return String
	 * @throws Exception
	 */
	public String getName() throws Exception;
	
	/**
	 * Set the scheme description
	 * @param description Scheme description
	 * @throws Exception
	 */
	public void setDescription(String description) throws Exception;
	
	/**
	 * Get the scheme description
	 * @return String
	 * @throws Exception
	 */
	public String getDescription() throws Exception;
	
	/**
	 * Set the scheme as archived or not
	 * @param archive archive status
	 * @throws Exception
	 */
	public void setArchive(boolean archive) throws Exception;
	
	/**
	 * Return if the scheme is marked as archived
	 * @return boolean
	 * @throws Exception
	 */
	public boolean isArchived() throws Exception;
	
	/**
	 * Click the save button
	 * @param archive archive status
	 * @throws Exception
	 */
	public void save() throws Exception;
	
	/**
	 * Click the cancel button
	 * @param archive archive status
	 * @throws Exception
	 */
	public void cancel() throws Exception;
	
	
}
