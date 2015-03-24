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
}
