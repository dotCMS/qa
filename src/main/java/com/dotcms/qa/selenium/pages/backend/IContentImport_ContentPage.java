package com.dotcms.qa.selenium.pages.backend;

import com.dotcms.qa.selenium.pages.IBasePage;

/**
 * Content import Page Interface
 * @author Oswaldo Gallango
 * @since 05/20/2015
 * @version 1.0
 *
 */ 
public interface IContentImport_ContentPage extends IBasePage{
	
	/**
	 * set the structure of the content to be imported
	 * @param structureName Structure Name
	 * @throws Exception
	 */
	public void setStructure(String structureName) throws Exception;
	
	/**
	 * set the csv file to import
	 * @param usersFilePath  Path to csv file
	 * @throws Exception
	 */
	public void setFile(String path) throws Exception;

	/**
	 * Click the go to preview button
	 */
	public void gotToPreview() throws Exception;
	
	/**
	 * Click the import content button
	 * @throws Exception
	 */
	public void importContent() throws Exception;
	
}
