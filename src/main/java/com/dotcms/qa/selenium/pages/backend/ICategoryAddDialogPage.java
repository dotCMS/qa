package com.dotcms.qa.selenium.pages.backend;

import com.dotcms.qa.selenium.pages.IBasePage;

/**
 * Add Category Dialog Page Interface
 * @author Oswaldo Gallango
 * @since 06/08/2015
 * @version 1.0
 *
 */
public interface ICategoryAddDialogPage extends IBasePage{
	
	/**
	 * Set the new category name
	 * @param categoryName
	 * @throws Exception
	 */
	public void setCategoryName(String categoryName) throws Exception;
	
	/**
	 * Set the category key
	 * @param key
	 * @throws Exception
	 */
	public void setKey(String key) throws Exception;
	
	/**
	 * Set the category keywords
	 * @param keywords
	 * @throws Exception
	 */
	public void setKeywords(String keywords) throws Exception;
	
	/**
	 * Click the save button
	 * @throws Exception
	 */
	public void save() throws Exception;
	
	/**
	 * Click the cancel button
	 * @throws Exception
	 */
	public void cancel() throws Exception;

}
