package com.dotcms.qa.selenium.pages.backend;

import java.util.Map;

import com.dotcms.qa.selenium.pages.IBasePage;

/**
 * Add or Edit Containers Page Interface
 * @author Oswaldo Gallango
 * @since 03/10/2015
 * @version 1.0
 *
 */ 
public interface IContainerAddOrEditPage extends IBasePage {

	/**
	 * Fill the container page fields
	 * @param containerFields	String map wit all the field of the container to be set
	 * @throws Exception
	 */
	public void setFields(Map<String,String> containerFields) throws Exception;
	
	/**
	 * Click the save and publish button
	 * @retun IContainersPage
	 */
	public IContainersPage saveAndPublish() throws Exception;
	
	/**
	 * Click the save button
	 * @return IContainersPage
	 */
	public IContainersPage save() throws Exception;
	
	/**
	 * Click the cancel button
	 * @return ICOntainersPage
	 */
	public IContainersPage cancel() throws Exception;
}
