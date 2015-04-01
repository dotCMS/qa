package com.dotcms.qa.selenium.pages.backend;

import java.util.List;
import java.util.Map;

import com.dotcms.qa.selenium.pages.IBasePage;

public interface ITemplateAddOrEditDesignTemplatePage extends IBasePage {

	/**
	 * Set the template name
	 * @param name	Template name
	 * @throws Exception
	 */
	public void setTemplateTitle(String name) throws Exception;
	
	/**
	 * Get the template name
	 * @return the Template name
	 * @throws Exception
	 */
	public String getTemplateTitle() throws Exception;
	
	/**
	 * Set the template theme
	 * @param theme	name of the theme
	 * @throws Exception
	 */
	public void setTheme(String theme) throws Exception;
	
	/**
	 * Get the template theme
	 * @return name of the theme
	 * @throws Exception
	 */
	public String getTheme() throws Exception;
	
	/**
	 * Add a container to a design template
	 * @param containerName Name of the container
	 * @throws Exception
	 */
	public void addContainer(String containerName) throws Exception;
	
	/**
	 * Click the save and publish button
	 * @return ITemplatesPage
	 * @throws Exception
	 */
	public ITemplatesPage saveAndPublish() throws Exception;

	/**
	 * Click the save button
	 * @return ITemplatesPage
	 * @throws Exception
	 */
	public ITemplatesPage save() throws Exception;
	
	/**
	 * Click the cancel button
	 * @return ITemplatesPage
	 * @throws Exception
	 */
	public ITemplatesPage cancel() throws Exception;
	
	/**
	 * Get the list of containers associated to the template
	 * @return List<Map<String,String>>
	 * @throws Exception
	 */
	public List<Map<String,String>> getTemplateContainers() throws Exception; 
}
