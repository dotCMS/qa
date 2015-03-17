package com.dotcms.qa.selenium.pages.backend;

import com.dotcms.qa.selenium.pages.IBasePage;

public interface IStructureAddOrEdit_PropertiesPage extends IBasePage {
	/**
	 * Create a new structure
	 * @param structureName Name of the structure
	 * @param type Structure type: content, widget,file, page
	 * @param structureDescription Description of the structure
	 * @param hostName Host associated to the structure
	 * @return IStructureAddOrEdit_FieldsPage
	 * @throws Exception
	 */
	public IStructureAddOrEdit_FieldsPage createNewStructure(String structureName, String type, String structureDescription, String hostName) throws Exception;
}
