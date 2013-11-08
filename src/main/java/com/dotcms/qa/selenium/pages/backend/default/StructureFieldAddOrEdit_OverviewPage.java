package com.dotcms.qa.selenium.pages.backend.default;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.dotcms.qa.selenium.pages.backend.*;
import com.dotcms.qa.selenium.pages.default.BasePage;
import com.dotcms.qa.selenium.util.SeleniumPageManager;
	
public class StructureFieldAddOrEdit_OverviewPage extends BasePage implements IStructureFieldAddOrEdit_OverviewPage {

	private WebElement elementSelectBox;			// Display Type
	private WebElement dijit_form_TextBox_0;		// Label
	private WebElement dijit_form_TextBox_2;		// Hint
	private WebElement requiredCB;
	private WebElement searchableCB;
	private WebElement listedCB;
	private WebElement uniqueCB;
	private WebElement saveButton_label;			// Save Button

	public StructureFieldAddOrEdit_OverviewPage(WebDriver driver) {
		super(driver);
	}

	public IStructureAddOrEdit_FieldsPage addBinaryField(String label, String hint, boolean required) throws Exception {
	    elementSelectBox.sendKeys("Binary");         // Display type
	    Thread.sleep(250);
	    elementSelectBox.sendKeys(Keys.RETURN);      // Activates display type selection and dynamically additional fields appear
	    dijit_form_TextBox_0.sendKeys(label);        // Label
	    dijit_form_TextBox_2.sendKeys(hint);		 //Hint
	    if(required)
	    	requiredCB.click();                      // Required checkbox
	    saveButton_label.click();                    // Save button
	    Thread.sleep(250);
		return SeleniumPageManager.getPageManager().getPageObject(IStructureAddOrEdit_FieldsPage.class);
	}

	public IStructureAddOrEdit_FieldsPage addImageField(String label, String hint, boolean required) throws Exception {
	    elementSelectBox.sendKeys("Image");          // Display type
	    Thread.sleep(250);
	    elementSelectBox.sendKeys(Keys.RETURN);      // Activates display type selection and dynamically additional fields appear
	    dijit_form_TextBox_0.sendKeys(label);        // Label
	    dijit_form_TextBox_2.sendKeys(hint);		 //Hint
	    if(required)
	    	requiredCB.click();                      // Required checkbox
	    saveButton_label.click();                    // Save button
	    Thread.sleep(250);
		return SeleniumPageManager.getPageManager().getPageObject(IStructureAddOrEdit_FieldsPage.class);
	}

	public IStructureAddOrEdit_FieldsPage addTextField(String label, boolean required, boolean userSearchable, boolean systemIndexed, boolean showInListing, boolean unique) throws Exception{
		elementSelectBox.sendKeys("Text");           // Display type
	    Thread.sleep(500);
	    elementSelectBox.sendKeys(Keys.RETURN);      // Activates display type selection and dynamically additional fields appear
	    dijit_form_TextBox_0.sendKeys(label);      	 // Label
	    if(required)
	    	requiredCB.click();                      // Required checkbox
	    if(userSearchable)
	    	searchableCB.click();                    // User searchable checkbox
	    if(showInListing)
	    	listedCB.click();                        // Include in list checkbox
	    if(unique)
	    	uniqueCB.click();                        // Unique checkbox
	    Thread.sleep(250);
	    saveButton_label.click();                    // Save button
	    Thread.sleep(250);
		return SeleniumPageManager.getPageManager().getPageObject(IStructureAddOrEdit_FieldsPage.class);
	}
	
	public IStructureAddOrEdit_FieldsPage addWYSIWYGField(String label, String defaultValue, String hint, boolean required, boolean userSearchable, boolean systemIndexed) throws Exception {
	    elementSelectBox.sendKeys("WYSIWYG");        // Display type
	    Thread.sleep(250);
	    elementSelectBox.sendKeys(Keys.RETURN);      // Activates display type selection and dynamically additional fields appear
	    dijit_form_TextBox_0.sendKeys(label);    	 // Label
	    if(required)
	    	requiredCB.click();                      // Required checkbox
	    if(userSearchable)
	    	searchableCB.click();                    // User searchable checkbox
	    saveButton_label.click();                    // Save button
	    Thread.sleep(250);
		return SeleniumPageManager.getPageManager().getPageObject(IStructureAddOrEdit_FieldsPage.class);
	}
}
