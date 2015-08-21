package com.dotcms.qa.selenium.pages.backend;

import com.dotcms.qa.selenium.pages.IBasePage;

/**
 * Roles Page Interface
 * @author Oswaldo Gallango
 * @since 03/23/2015
 * @version 1.0
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
	
	/**
	 * Add a new advance template
	 * @return ITemplateAddOrEditPage
	 * @throws Exception
	 */
	public ITemplateAddOrEditAdvanceTemplatePage addAdvanceTemplate() throws Exception;
	
	/**
	 * Add a new design template
	 * @return ITemplateAddOrEditPage
	 * @throws Exception
	 */
	public ITemplateAddOrEditDesignTemplatePage addDesignTemplate() throws Exception;
	
	/**
	 * Validate if a template exist
	 * @param templateName Name of the template
	 * @return true if the template exist, false if not
	 * @throws Exception
	 */
	public boolean doesTemplateExist(String templateName) throws Exception;

	/**
	 * delete a template
	 * @param templateName Name of the template
	 * @throws Exception
	 */
	public void deleteTemplate(String templateName) throws Exception;
	
	/**
	 * Click the push publish option from the right click menu options
	 * @param templateName Template Name
	 * @throws Exception
	 */
	public void pushTemplate(String templateName) throws Exception;
	
	/**
	 * Click the push publish option from the right click menu options
	 * @param templateName Template Name
	 * @param pushType   Type of push to apply (PUSH_TO_REMOVE,PUSH_TO_ADD,PUSH_AND_REMOVE)
	 * @param pushDate   Date with format M/d/yyyy(optional)
	 * @param pushTime   Time format H:mm a (optional)
	 * @param expireDate Date with format M/d/yyyy(optional)
	 * @param expireTime Time format H:mm a (optional)
	 * @param force      Force to publish (Only valid for push and push a remove)
	 * @throws Exception
	 */
	public void pushTemplate(String templateName, String pushType, String pushDate, String pushTime, String expireDate, String expireTime, boolean force) throws Exception;
	
	/**
	 * This method allows to go to the editing page for templates
	 * @param templateName  Name of the template
	 * @throws Exception
	 */
	public void editTemplate(String templateName) throws Exception;
}
