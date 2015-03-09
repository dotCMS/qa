package com.dotcms.qa.selenium.pages.backend;

import com.dotcms.qa.selenium.pages.IBasePage;

/**
 * Structure Page Interface
 * @author Brent Griffin
 * @author Oswaldo Gallango
 *
 */
public interface IStructuresPage extends IBasePage {
	
	/**
	 * Delete a structure and all it contents
	 * @param structureName Name of the structure
	 * @param confirm Validate confirmation box
	 * @throws Exception
	 */
	public void deleteStructureAndContent(String structureName, boolean confirm) throws Exception;
	
	/**
	 * Validate if the structure exist
	 * @param structureName Name of the structure
	 * @return true if the structure exist, false if not
	 */
	public boolean doesStructureExist(String structureName);
	
	public IStructureAddOrEdit_PropertiesPage getAddNewStructurePage() throws Exception;
	
	/**
	 * Get the field tab page
	 * @return IStructureAddOrEdit_FieldsPage
	 * @throws Exception
	 */
	public IStructureAddOrEdit_FieldsPage getFieldsPage() throws Exception;
	
	/**
	 * Get a structure field page
	 * @param structureName
	 * @return IStructureAddOrEdit_PropertiesPage
	 * @throws Exception
	 */
	public IStructureAddOrEdit_PropertiesPage getStructurePage(String structureName) throws Exception;
}
