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
	
	/**
	 * Push the selected structure
	 * @param structureName Name of the structure
	 * @throws Exception
	 */
	public void pushStructure(String structureName) throws Exception;
	
	/**
	 * Push the selected structure
	 * @param structureName Name of the structure
	 * @param pushType   Type of push to apply (PUSH_TO_REMOVE,PUSH_TO_ADD,PUSH_AND_REMOVE)
	 * @param pushDate   Date with format M/d/yyyy(optional)
	 * @param pushTime   Time format H:mm a (optional)
	 * @param expireDate Date with format M/d/yyyy(optional)
	 * @param expireTime Time format H:mm a (optional)
	 * @param force      Force to publish (Only valid for push and push a remove)
	 * @throws Exception
	 */
	public void pushStructure(String structureName, String pushType, String pushDate, String pushTime, String expireDate, String expireTime, boolean force) throws Exception;
}
