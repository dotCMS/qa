package com.dotcms.qa.selenium.pages.backend;

import com.dotcms.qa.selenium.pages.IBasePage;

public interface IStructureAddOrEdit_PropertiesPage extends IBasePage {
	public IStructureAddOrEdit_FieldsPage createNewStructure(String structureName, String structureDescription, String hostName) throws Exception;
}
