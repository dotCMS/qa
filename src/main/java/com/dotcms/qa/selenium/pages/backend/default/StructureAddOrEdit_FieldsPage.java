package com.dotcms.qa.selenium.pages.backend.default;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.dotcms.qa.selenium.pages.BasePage;
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

	public IStructureAddOrEdit_FieldsPage addImageField(String label, String hint, boolean required) throws Exception {
		IStructureFieldAddOrEdit_OverviewPage addFieldsPage = getAddNewFieldPage();
		return addFieldsPage.addImageField(label, hint, required);
	}

	public IStructureAddOrEdit_FieldsPage addTextField(String label, boolean required, boolean userSearchable, boolean systemIndexed, boolean showInListing, boolean unique) throws Exception {
		IStructureFieldAddOrEdit_OverviewPage addFieldsPage = getAddNewFieldPage();
		return addFieldsPage.addTextField(label, required, userSearchable, systemIndexed, showInListing, unique);
	}
	
	public IStructureAddOrEdit_FieldsPage addWYSIWYGField(String label, String defaultValue, String hint, boolean required, boolean userSearchable, boolean systemIndexed) throws Exception {
		
		IStructureFieldAddOrEdit_OverviewPage addFieldsPage = getAddNewFieldPage();
		return addFieldsPage.addWYSIWYGField(label, defaultValue, hint, required, userSearchable, systemIndexed);
	}

	private IStructureFieldAddOrEdit_OverviewPage getAddNewFieldPage() throws Exception{
	    field_label.click();
		return SeleniumPageManager.getPageManager().getPageObject(IStructureFieldAddOrEdit_OverviewPage.class);
	}

}
