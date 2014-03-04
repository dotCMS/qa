package com.dotcms.qa.selenium.pages.backend.common;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.dotcms.qa.selenium.pages.backend.*;
import com.dotcms.qa.selenium.pages.common.BasePage;
import com.dotcms.qa.selenium.util.SeleniumPageManager;

public class StructureAddOrEdit_FieldsPage extends BasePage implements IStructureAddOrEdit_FieldsPage {

	private WebElement field_label;		// Add a Field Button
	
	public StructureAddOrEdit_FieldsPage(WebDriver driver) {
		super(driver);
	}

	public IStructureAddOrEdit_FieldsPage addBinaryField(String label, String hint, boolean required) throws Exception {
		IStructureFieldAddOrEdit_OverviewPage addFieldsPage = getAddNewFieldPage();
		return addFieldsPage.addBinaryField(label, hint, required);
	}

	public IStructureAddOrEdit_FieldsPage addCategory(String label, String category, String hint, boolean required, boolean userSearchable) throws Exception {
		IStructureFieldAddOrEdit_OverviewPage addFieldsPage = getAddNewFieldPage();
		return addFieldsPage.addCategory(label, category, hint, required, userSearchable);
	}

	public IStructureAddOrEdit_FieldsPage addCheckbox(String label, String value, String defaultValue, String hint, boolean required, boolean userSearchable, boolean systemIndexed) throws Exception {
		IStructureFieldAddOrEdit_OverviewPage addFieldsPage = getAddNewFieldPage();
		return addFieldsPage.addCheckbox(label, value, defaultValue, hint, required, userSearchable, systemIndexed);
	}
	
	public IStructureAddOrEdit_FieldsPage addConstantField(String label, String value, String hint) throws Exception {
		IStructureFieldAddOrEdit_OverviewPage addFieldsPage = getAddNewFieldPage();
		return addFieldsPage.addConstantField(label, value, hint);
	}
	
	public IStructureAddOrEdit_FieldsPage addCustomField(String label, String value, String validationRegEx, String validation, String defaultValue, String hint, boolean required, boolean userSearchable, boolean systemIndexed, boolean showInListing, boolean unique) throws Exception {
		IStructureFieldAddOrEdit_OverviewPage addFieldsPage = getAddNewFieldPage();
		return addFieldsPage.addCustomField(label, value, validationRegEx, validation, defaultValue, hint, required, userSearchable, systemIndexed, showInListing, unique);
	}
	
	public IStructureAddOrEdit_FieldsPage addDateField(String label, String defaultValue, String hint, boolean required, boolean userSearchable, boolean systemIndexed, boolean showInListing) throws Exception {
		IStructureFieldAddOrEdit_OverviewPage addFieldsPage = getAddNewFieldPage();
		return addFieldsPage.addDateField(label, defaultValue, hint, required, userSearchable, systemIndexed, showInListing);
	}
	
	public IStructureAddOrEdit_FieldsPage addDateAndTimeField(String label, String defaultValue, String hint, boolean required, boolean userSearchable, boolean systemIndexed, boolean showInListing) throws Exception {
		IStructureFieldAddOrEdit_OverviewPage addFieldsPage = getAddNewFieldPage();
		return addFieldsPage.addDateAndTimeField(label, defaultValue, hint, required, userSearchable, systemIndexed, showInListing);
	}
	
	public IStructureAddOrEdit_FieldsPage addFileField(String label, String hint, boolean required) throws Exception {
		IStructureFieldAddOrEdit_OverviewPage addFieldsPage = getAddNewFieldPage();
		return addFieldsPage.addFileField(label, hint, required);
	}
	
	public IStructureAddOrEdit_FieldsPage addHiddenField(String label, String value) throws Exception {
		IStructureFieldAddOrEdit_OverviewPage addFieldsPage = getAddNewFieldPage();
		return addFieldsPage.addHiddenField(label, value);
	}
	
	public IStructureAddOrEdit_FieldsPage addHostOrFolderField(String label, String hint, boolean required, boolean userSearchable) throws Exception {
		IStructureFieldAddOrEdit_OverviewPage addFieldsPage = getAddNewFieldPage();
		return addFieldsPage.addHostOrFolderField(label, hint, required, userSearchable);
	}

	public IStructureAddOrEdit_FieldsPage addImageField(String label, String hint, boolean required) throws Exception {
		IStructureFieldAddOrEdit_OverviewPage addFieldsPage = getAddNewFieldPage();
		return addFieldsPage.addImageField(label, hint, required);
	}

	public IStructureAddOrEdit_FieldsPage addLineDividerField(String label) throws Exception {
		IStructureFieldAddOrEdit_OverviewPage addFieldsPage = getAddNewFieldPage();
		return addFieldsPage.addLineDividerField(label);
	}
	
	public IStructureAddOrEdit_FieldsPage addMultiSelectField(String label, String value, String defaultValue, String hint, boolean required, boolean userSearchable, boolean systemIndexed, boolean unique) throws Exception {
		IStructureFieldAddOrEdit_OverviewPage addFieldsPage = getAddNewFieldPage();
		return addFieldsPage.addMultiSelectField(label, value, defaultValue, hint, required, userSearchable, systemIndexed, unique);
	}
	
	public IStructureAddOrEdit_FieldsPage addPermissionsField(String label) throws Exception {
		IStructureFieldAddOrEdit_OverviewPage addFieldsPage = getAddNewFieldPage();
		return addFieldsPage.addPermissionsField(label);
	}
	
	public IStructureAddOrEdit_FieldsPage addRadioField(String label, String value, String defaultValue, String hint, boolean required, boolean userSearchable, boolean systemIndexed, boolean showInListing) throws Exception {
		IStructureFieldAddOrEdit_OverviewPage addFieldsPage = getAddNewFieldPage();
		return addFieldsPage.addRadioField(label, value, defaultValue, hint, required, userSearchable, systemIndexed, showInListing);
	}
	
	public IStructureAddOrEdit_FieldsPage addRelationshipsField(String label) throws Exception {
		IStructureFieldAddOrEdit_OverviewPage addFieldsPage = getAddNewFieldPage();
		return addFieldsPage.addRelationshipsField(label);
	}
	
	public IStructureAddOrEdit_FieldsPage addSelectField(String label, String value, String defaultValue, String hint, boolean required, boolean userSearchable, boolean systemIndexed, boolean showInListing) throws Exception {
		IStructureFieldAddOrEdit_OverviewPage addFieldsPage = getAddNewFieldPage();
		return addFieldsPage.addSelectField(label, value, defaultValue, hint, required, userSearchable, systemIndexed, showInListing);
	}
	
	public IStructureAddOrEdit_FieldsPage addTabDividerField(String label) throws Exception {
		IStructureFieldAddOrEdit_OverviewPage addFieldsPage = getAddNewFieldPage();
		return addFieldsPage.addTabDividerField(label);
	}
	
	public IStructureAddOrEdit_FieldsPage addTagField(String label, String defaultValue, String hint, boolean required, boolean userSearchable) throws Exception {
		IStructureFieldAddOrEdit_OverviewPage addFieldsPage = getAddNewFieldPage();
		return addFieldsPage.addTagField(label, defaultValue, hint, required, userSearchable);
	}

	public IStructureAddOrEdit_FieldsPage addTextField(String label, boolean required, boolean userSearchable, boolean systemIndexed, boolean showInListing, boolean unique) throws Exception {
		IStructureFieldAddOrEdit_OverviewPage addFieldsPage = getAddNewFieldPage();
		return addFieldsPage.addTextField(label, required, userSearchable, systemIndexed, showInListing, unique);
	}

	public IStructureAddOrEdit_FieldsPage addTextareaField(String label, String validationRegEx, String validation, String defaultValue, String hint, boolean required, boolean userSearchable, boolean systemIndexed) throws Exception {
		IStructureFieldAddOrEdit_OverviewPage addFieldsPage = getAddNewFieldPage();
		return addFieldsPage.addTextareaField(label, validationRegEx, validation, defaultValue, hint, required, userSearchable, systemIndexed);
	}
	
	public IStructureAddOrEdit_FieldsPage addTimeField(String label, String defaultValue, String hint, boolean required, boolean userSearchable, boolean systemIndexed, boolean showInListing) throws Exception {
		IStructureFieldAddOrEdit_OverviewPage addFieldsPage = getAddNewFieldPage();
		return addFieldsPage.addTimeField(label, defaultValue, hint, required, userSearchable, systemIndexed, showInListing);
	}

	public IStructureAddOrEdit_FieldsPage addWYSIWYGField(String label, String defaultValue, String hint, boolean required, boolean userSearchable, boolean systemIndexed) throws Exception {
		
		IStructureFieldAddOrEdit_OverviewPage addFieldsPage = getAddNewFieldPage();
		return addFieldsPage.addWYSIWYGField(label, defaultValue, hint, required, userSearchable, systemIndexed);
	}

	public IStructureAddOrEdit_FieldsPage addKeyValueField(String label, String hint, boolean required, boolean userSearchable) throws Exception {
		IStructureFieldAddOrEdit_OverviewPage addFieldsPage = getAddNewFieldPage();
		return addFieldsPage.addKeyValueField(label, hint, required, userSearchable);
	}

	private IStructureFieldAddOrEdit_OverviewPage getAddNewFieldPage() throws Exception{
	    field_label.click();
		return SeleniumPageManager.getPageManager().getPageObject(IStructureFieldAddOrEdit_OverviewPage.class);
	}

}
