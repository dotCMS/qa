package com.dotcms.qa.selenium.pages.backend;

import com.dotcms.qa.selenium.pages.IBasePage;

public interface IStructureAddOrEdit_FieldsPage extends IBasePage {
	public IStructureAddOrEdit_FieldsPage addBinaryField(String label, String hint, boolean required) throws Exception;
	public IStructureAddOrEdit_FieldsPage addCategory(String label, String category, String hint, boolean required, boolean userSearchable) throws Exception;
	public IStructureAddOrEdit_FieldsPage addCheckbox(String label, String value, String defaultValue, String hint, boolean required, boolean userSearchable, boolean systemIndexed) throws Exception;
	public IStructureAddOrEdit_FieldsPage addConstantField(String label, String value, String hint) throws Exception;
	public IStructureAddOrEdit_FieldsPage addCustomField(String label, String value, String validationRegEx, String validation, String defaultValue, String hint, boolean required, boolean userSearchable, boolean systemIndexed, boolean showInListing, boolean unique) throws Exception;
	public IStructureAddOrEdit_FieldsPage addDateField(String label, String defaultValue, String hint, boolean required, boolean userSearchable, boolean systemIndexed, boolean showInListing) throws Exception;
	public IStructureAddOrEdit_FieldsPage addDateAndTimeField(String label, String defaultValue, String hint, boolean required, boolean userSearchable, boolean systemIndexed, boolean showInListing) throws Exception;
	public IStructureAddOrEdit_FieldsPage addFileField(String label, String hint, boolean required) throws Exception;
	public IStructureAddOrEdit_FieldsPage addHiddenField(String label, String value) throws Exception;
	public IStructureAddOrEdit_FieldsPage addHostOrFolderField(String label, String hint, boolean required, boolean userSearchable) throws Exception;
	public IStructureAddOrEdit_FieldsPage addImageField(String label, String hint, boolean required) throws Exception;
	public IStructureAddOrEdit_FieldsPage addLineDividerField(String label) throws Exception;
	public IStructureAddOrEdit_FieldsPage addMultiSelectField(String label, String value, String defaultValue, String hint, boolean required, boolean userSearchable, boolean systemIndexed, boolean unique) throws Exception;
	public IStructureAddOrEdit_FieldsPage addPermissionsField(String label) throws Exception;
	public IStructureAddOrEdit_FieldsPage addRadioField(String label, String value, String defaultValue, String hint, boolean required, boolean userSearchable, boolean systemIndexed, boolean showInListing) throws Exception;
	public IStructureAddOrEdit_FieldsPage addRelationshipsField(String label) throws Exception;
	public IStructureAddOrEdit_FieldsPage addSelectField(String label, String value, String defaultValue, String hint, boolean required, boolean userSearchable, boolean systemIndexed, boolean showInListing) throws Exception;
	public IStructureAddOrEdit_FieldsPage addTabDividerField(String label) throws Exception;
	public IStructureAddOrEdit_FieldsPage addTagField(String label, String defaultValue, String hint, boolean required, boolean userSearchable) throws Exception;
	public IStructureAddOrEdit_FieldsPage addTextField(String label, boolean required, boolean userSearchable, boolean systemIndexed, boolean showInListing, boolean unique) throws Exception;
	public IStructureAddOrEdit_FieldsPage addTextareaField(String label, String validationRegEx, String validation, String defaultValue, String hint, boolean required, boolean userSearchable, boolean systemIndexed) throws Exception;
	public IStructureAddOrEdit_FieldsPage addTimeField(String label, String defaultValue, String hint, boolean required, boolean userSearchable, boolean systemIndexed, boolean showInListing) throws Exception;
	public IStructureAddOrEdit_FieldsPage addWYSIWYGField(String label, String defaultValue, String hint, boolean required, boolean userSearchable, boolean systemIndexed) throws Exception;
	public IStructureAddOrEdit_FieldsPage addKeyValueField(String label, String hint, boolean required, boolean userSearchable) throws Exception;
	/**
	 * Validate if a field exist
	 * @param label name of the field
	 * @return true if exist false if not
	 * @throws Exception
	 */
	public boolean doesFieldExist(String label) throws Exception;
	
	/**
	 * Delete a field from the structure
	 * @param label Name of the field
	 * @return IStructureAddOrEdit_FieldsPage
	 * @throws Exception
	 */
	public IStructureAddOrEdit_FieldsPage deleteField (String label) throws Exception;
}
