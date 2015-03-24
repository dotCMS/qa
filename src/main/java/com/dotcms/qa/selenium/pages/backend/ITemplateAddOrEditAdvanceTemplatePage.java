package com.dotcms.qa.selenium.pages.backend;

import java.util.Map;

import com.dotcms.qa.selenium.pages.IBasePage;

/**
 * Add or Edit template Page Interface
 * @author Oswaldo Gallango
 *
 */
public interface ITemplateAddOrEditAdvanceTemplatePage extends IBasePage{

	/**
	 * Fill the advance template page fields
	 * @param containerFields	List of String map wit all the field of the container to be set
	 * @throws Exception
	 */
	public void setTemplateFields(Map<String,String> templateFields) throws Exception;

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

}
