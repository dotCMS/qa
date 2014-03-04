package com.dotcms.qa.selenium.pages.backend;

import com.dotcms.qa.selenium.pages.IBasePage;

public interface IStructuresPage extends IBasePage {
	public void deleteStructureAndContent(String structureName, boolean confirm) throws Exception;
	public boolean doesStructureExist(String structureName);
	public IStructureAddOrEdit_PropertiesPage getAddNewStructurePage() throws Exception;
}
