package com.dotcms.qa.selenium.pages.backend;

import com.dotcms.qa.selenium.pages.IBasePage;

public interface IStructureFieldAddOrEdit_OverviewPage extends IBasePage {
	public IStructureAddOrEdit_FieldsPage addBinaryField(String label, String hint, boolean required) throws Exception;
	public IStructureAddOrEdit_FieldsPage addImageField(String label, String hint, boolean required) throws Exception;
	public IStructureAddOrEdit_FieldsPage addTextField(String label, boolean required, boolean userSearchable, boolean systemIndexed, boolean showInListing, boolean unique) throws Exception;
	public IStructureAddOrEdit_FieldsPage addWYSIWYGField(String label, String defaultValue, String hint, boolean required, boolean userSearchable, boolean systemIndexed) throws Exception;
}
