package com.dotcms.qa.selenium.pages.backend.common;

import org.apache.log4j.Logger;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.dotcms.qa.selenium.pages.backend.*;
import com.dotcms.qa.selenium.pages.common.BasePage;
import com.dotcms.qa.selenium.util.SeleniumPageManager;
	
public class StructureFieldAddOrEdit_OverviewPage extends BasePage implements IStructureFieldAddOrEdit_OverviewPage {

    private final Logger logger = Logger.getLogger(StructureFieldAddOrEdit_OverviewPage.class);

	private WebElement elementSelectBox;			// Display Type
	private WebElement dijit_form_TextBox_0;		// Label
	private WebElement dataTypetext;				// Data Type Radio Button
	private WebElement categories;
	private WebElement textAreaValues;				// Value
	private WebElement regexCheck;					// Validation RegEx
	private WebElement validation;					// RegEx Validation Selection
	private WebElement dijit_form_TextBox_1;		// Default Value
	private WebElement dijit_form_TextBox_2;		// Hint
	private WebElement requiredCB;
	private WebElement searchableCB;				// User Searchable
	private WebElement listedCB;
	private WebElement uniqueCB;
	private WebElement indexedCB;					// System Indexed
	private WebElement saveButton_label;			// Save Button

	public StructureFieldAddOrEdit_OverviewPage(WebDriver driver) {
		super(driver);
	}

	public IStructureAddOrEdit_FieldsPage addBinaryField(String label, String hint, boolean required) throws Exception {
	    elementSelectBox.sendKeys("Binary");         // Display type
	    Thread.sleep(250);
	    elementSelectBox.sendKeys(Keys.RETURN);      // Activates display type selection and dynamically additional fields appear
	    dijit_form_TextBox_0.sendKeys(label);        // Label
	    dijit_form_TextBox_2.sendKeys(hint);		 // Hint
	    if(required)
	    	requiredCB.click();                      // Required Checkbox
	    saveButton_label.click();                    // Save button
	    Thread.sleep(250);
		return SeleniumPageManager.getBackEndPageManager().getPageObject(IStructureAddOrEdit_FieldsPage.class);
	}

	public IStructureAddOrEdit_FieldsPage addCategory(String label, String category, String hint, boolean required, boolean userSearchable) throws Exception {
	    elementSelectBox.sendKeys("Category");       // Display type
	    Thread.sleep(250);
	    elementSelectBox.sendKeys(Keys.RETURN);      // Activates display type selection and dynamically additional fields appear
	    dijit_form_TextBox_0.sendKeys(label); 		 // Label
	    if(category != null && !category.trim().isEmpty()) {
		    categories.clear();
		    categories.sendKeys(category);
		    categories.sendKeys(Keys.RETURN);
	    }
	    dijit_form_TextBox_2.sendKeys(hint);		 // Hint
	    if(required)
	    	requiredCB.click();                      // Required Checkbox
	    if(userSearchable)							 
	    	searchableCB.click();					 // User Searchable Checkbox
	    saveButton_label.click();                    // Save button
	    Thread.sleep(250);
		return SeleniumPageManager.getBackEndPageManager().getPageObject(IStructureAddOrEdit_FieldsPage.class);
	}

	public IStructureAddOrEdit_FieldsPage addCheckbox(String label, String value, String defaultValue, String hint, boolean required, boolean userSearchable, boolean systemIndexed) throws Exception {
	    elementSelectBox.sendKeys("Checkbox");       // Display type
	    Thread.sleep(250);
	    elementSelectBox.sendKeys(Keys.RETURN);      // Activates display type selection and dynamically additional fields appear
	    dijit_form_TextBox_0.sendKeys(label);        // Label
	    textAreaValues.sendKeys(value);
	    dijit_form_TextBox_1.sendKeys(defaultValue); // Default Value
	    dijit_form_TextBox_2.sendKeys(hint);		 // Hint

	    moveToElement(saveButton_label);

	    if(required)
	    	requiredCB.click();                      // Required Checkbox
	    if(userSearchable)							 
	    	searchableCB.click();					 // User Searchable Checkbox
	    if(systemIndexed)
	    	indexedCB.click();						 // System Indexed Checkbox
	    saveButton_label.click();                    // Save button
	    Thread.sleep(250);
		return SeleniumPageManager.getBackEndPageManager().getPageObject(IStructureAddOrEdit_FieldsPage.class);		
	}
	
	public IStructureAddOrEdit_FieldsPage addConstantField(String label, String value, String hint) throws Exception {
	    elementSelectBox.sendKeys("Constant"); // Workaround for https://github.com/dotCMS/dotCMS/issues/4943
//	    elementSelectBox.sendKeys("Constant Field"); // Display type
	    Thread.sleep(250);
	    elementSelectBox.sendKeys(Keys.RETURN);      // Activates display type selection and dynamically additional fields appear
	    dijit_form_TextBox_0.sendKeys(label);        // Label
	    textAreaValues.sendKeys(value);			 // Value
	    dijit_form_TextBox_2.sendKeys(hint);		 // Hint
	    saveButton_label.click();                    // Save button
	    Thread.sleep(250);
		return SeleniumPageManager.getBackEndPageManager().getPageObject(IStructureAddOrEdit_FieldsPage.class);
	}
	
	public IStructureAddOrEdit_FieldsPage addCustomField(String label, String value, String validationRegEx, String validation, String defaultValue, String hint, boolean required, boolean userSearchable, boolean systemIndexed, boolean showInListing, boolean unique) throws Exception {
	    elementSelectBox.sendKeys("Custom"); // Workaround for https://github.com/dotCMS/dotCMS/issues/4943
//	    elementSelectBox.sendKeys("Custom Field");   // Display type
	    Thread.sleep(250);
	    elementSelectBox.sendKeys(Keys.RETURN);      // Activates display type selection and dynamically additional fields appear
	    dijit_form_TextBox_0.sendKeys(label);        // Label
	    textAreaValues.sendKeys(value);			 	 // Value
	    if(validation == null || validation.trim().isEmpty()) {
	    	regexCheck.sendKeys(validationRegEx);		 // Validation RegEx
	    }
	    else
	    {
		    this.validation.clear();					 // RegEx Validation Selection
		    this.validation.sendKeys(validation);		 
		    this.validation.sendKeys(Keys.RETURN);
	    }
	    dijit_form_TextBox_1.sendKeys(defaultValue); // Default Value
	    dijit_form_TextBox_2.sendKeys(hint);		 // Hint
	    
	    moveToElement(saveButton_label);

	    if(required)
	    	requiredCB.click();                      // Required Checkbox
	    if(userSearchable)							 
	    	searchableCB.click();					 // User Searchable Checkbox
	    if(systemIndexed)
	    	indexedCB.click();						 // System Indexed Checkbox
	    if(showInListing)
	    	listedCB.click();						 // Show In Listing Checkbox
	    if(unique)
	    	uniqueCB.click();						 // Unique Checkbox
	    saveButton_label.click();                    // Save button
	    Thread.sleep(250);
		return SeleniumPageManager.getBackEndPageManager().getPageObject(IStructureAddOrEdit_FieldsPage.class);
	}
	
	public IStructureAddOrEdit_FieldsPage addDateField(String label, String defaultValue, String hint, boolean required, boolean userSearchable, boolean systemIndexed, boolean showInListing) throws Exception {
	    elementSelectBox.sendKeys("Date");   		 // Display type
	    Thread.sleep(250);
	    elementSelectBox.sendKeys(Keys.RETURN);      // Activates display type selection and dynamically additional fields appear
	    dijit_form_TextBox_0.sendKeys(label);        // Label
	    dijit_form_TextBox_1.sendKeys(defaultValue); // Default Value
	    dijit_form_TextBox_2.sendKeys(hint);		 // Hint
	    if(required)
	    	requiredCB.click();                      // Required Checkbox
	    if(userSearchable)							 
	    	searchableCB.click();					 // User Searchable Checkbox
	    if(systemIndexed)
	    	indexedCB.click();						 // System Indexed Checkbox
	    if(showInListing)
	    	listedCB.click();						 // Show In Listing Checkbox
	    saveButton_label.click();                    // Save button
	    Thread.sleep(250);
		return SeleniumPageManager.getBackEndPageManager().getPageObject(IStructureAddOrEdit_FieldsPage.class);		
	}
	
	public IStructureAddOrEdit_FieldsPage addDateAndTimeField(String label, String defaultValue, String hint, boolean required, boolean userSearchable, boolean systemIndexed, boolean showInListing) throws Exception {
	    elementSelectBox.sendKeys("Date"); // Workaround for https://github.com/dotCMS/dotCMS/issues/4943
//	    elementSelectBox.sendKeys("Date and Time");	 // Display type
	    Thread.sleep(250);
	    elementSelectBox.sendKeys(Keys.RETURN);      // Activates display type selection and dynamically additional fields appear
	    
	    // next two lines workaround for https://github.com/dotCMS/dotCMS/issues/4943
	    elementSelectBox.sendKeys(Keys.ARROW_DOWN);
	    elementSelectBox.sendKeys(Keys.RETURN);      // Activates display type selection and dynamically additional fields appear
	    
	    dijit_form_TextBox_0.sendKeys(label);        // Label
	    dijit_form_TextBox_1.sendKeys(defaultValue); // Default Value
	    dijit_form_TextBox_2.sendKeys(hint);		 // Hint
	    if(required)
	    	requiredCB.click();                      // Required Checkbox
	    if(userSearchable)							 
	    	searchableCB.click();					 // User Searchable Checkbox
	    if(systemIndexed)
	    	indexedCB.click();						 // System Indexed Checkbox
	    if(showInListing)
	    	listedCB.click();						 // Show In Listing Checkbox
	    saveButton_label.click();                    // Save button
	    Thread.sleep(250);
		return SeleniumPageManager.getBackEndPageManager().getPageObject(IStructureAddOrEdit_FieldsPage.class);				
	}
	
	public IStructureAddOrEdit_FieldsPage addFileField(String label, String hint, boolean required) throws Exception {
	    elementSelectBox.sendKeys("File");			 // Display type
	    Thread.sleep(250);
	    elementSelectBox.sendKeys(Keys.RETURN);      // Activates display type selection and dynamically additional fields appear
	    dijit_form_TextBox_0.sendKeys(label);        // Label
	    dijit_form_TextBox_2.sendKeys(hint);		 // Hint
	    if(required)
	    	requiredCB.click();                      // Required Checkbox
	    saveButton_label.click();                    // Save button
	    Thread.sleep(250);
		return SeleniumPageManager.getBackEndPageManager().getPageObject(IStructureAddOrEdit_FieldsPage.class);						
	}
	
	public IStructureAddOrEdit_FieldsPage addHiddenField(String label, String value) throws Exception {
	    elementSelectBox.sendKeys("Hidden"); // Workaround for https://github.com/dotCMS/dotCMS/issues/4943
//	    elementSelectBox.sendKeys("Hidden Field");	 // Display type
	    Thread.sleep(250);
	    elementSelectBox.sendKeys(Keys.RETURN);      // Activates display type selection and dynamically additional fields appear
	    dijit_form_TextBox_0.sendKeys(label);        // Label
	    textAreaValues.sendKeys(value);			 // Value
	    saveButton_label.click();                    // Save button
	    Thread.sleep(250);
		return SeleniumPageManager.getBackEndPageManager().getPageObject(IStructureAddOrEdit_FieldsPage.class);						
	}
	
	public IStructureAddOrEdit_FieldsPage addHostOrFolderField(String label, String hint, boolean required, boolean userSearchable) throws Exception {
	    elementSelectBox.sendKeys("Host"); // Workaround for https://github.com/dotCMS/dotCMS/issues/4943
//	    elementSelectBox.sendKeys("Host or Folder"); // Display type
	    Thread.sleep(250);
	    elementSelectBox.sendKeys(Keys.RETURN);      // Activates display type selection and dynamically additional fields appear
	    dijit_form_TextBox_0.sendKeys(label);        // Label
	    dijit_form_TextBox_2.sendKeys(hint);		 // Hint
	    if(required)
	    	requiredCB.click();                      // Required Checkbox
	    if(userSearchable)							 
	    	searchableCB.click();					 // User Searchable Checkbox
	    saveButton_label.click();                    // Save button
	    Thread.sleep(250);
		return SeleniumPageManager.getBackEndPageManager().getPageObject(IStructureAddOrEdit_FieldsPage.class);								
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
		return SeleniumPageManager.getBackEndPageManager().getPageObject(IStructureAddOrEdit_FieldsPage.class);
	}

	public IStructureAddOrEdit_FieldsPage addLineDividerField(String label) throws Exception {
	    elementSelectBox.sendKeys("Line"); // Workaround for https://github.com/dotCMS/dotCMS/issues/4943
//	    elementSelectBox.sendKeys("Line Divider");   // Display type
	    Thread.sleep(250);
	    elementSelectBox.sendKeys(Keys.RETURN);      // Activates display type selection and dynamically additional fields appear
	    dijit_form_TextBox_0.sendKeys(label);        // Label
	    saveButton_label.click();                    // Save button
	    Thread.sleep(250);
		return SeleniumPageManager.getBackEndPageManager().getPageObject(IStructureAddOrEdit_FieldsPage.class);
	}
	
	public IStructureAddOrEdit_FieldsPage addMultiSelectField(String label, String value, String defaultValue, String hint, boolean required, boolean userSearchable, boolean systemIndexed, boolean unique) throws Exception{
	    elementSelectBox.sendKeys("Multi"); // Workaround for https://github.com/dotCMS/dotCMS/issues/4943
//	    elementSelectBox.sendKeys("Multi Select");   // Display type
	    Thread.sleep(250);
	    elementSelectBox.sendKeys(Keys.RETURN);      // Activates display type selection and dynamically additional fields appear
	    dijit_form_TextBox_0.sendKeys(label);        // Label
	    textAreaValues.sendKeys(value);			 // Value
	    dijit_form_TextBox_1.sendKeys(defaultValue); // Default Value
	    dijit_form_TextBox_2.sendKeys(hint);		 // Hint
	    if(required)
	    	requiredCB.click();                      // Required Checkbox
	    if(userSearchable)							 
	    	searchableCB.click();					 // User Searchable Checkbox
	    if(systemIndexed)
	    	indexedCB.click();						 // System Indexed Checkbox
	    if(unique)
	    	uniqueCB.click();						 // Unique Checkbox

	    moveToElement(saveButton_label);

	    saveButton_label.click();                    // Save button
	    Thread.sleep(250);
		return SeleniumPageManager.getBackEndPageManager().getPageObject(IStructureAddOrEdit_FieldsPage.class);
	}
	
	public IStructureAddOrEdit_FieldsPage addPermissionsField(String label) throws Exception {
	    elementSelectBox.sendKeys("Permissions"); // Workaround for https://github.com/dotCMS/dotCMS/issues/4943
//	    elementSelectBox.sendKeys("Permisions Field");// Display type
	    Thread.sleep(250);
	    elementSelectBox.sendKeys(Keys.RETURN);      // Activates display type selection and dynamically additional fields appear
	    dijit_form_TextBox_0.sendKeys(label);        // Label
	    saveButton_label.click();                    // Save button
	    Thread.sleep(250);
		return SeleniumPageManager.getBackEndPageManager().getPageObject(IStructureAddOrEdit_FieldsPage.class);
	}
	
	public IStructureAddOrEdit_FieldsPage addRadioField(String label, String value, String defaultValue, String hint, boolean required, boolean userSearchable, boolean systemIndexed, boolean showInListing) throws Exception {
	    elementSelectBox.sendKeys("Radio");			 // Display type
	    Thread.sleep(250);
	    elementSelectBox.sendKeys(Keys.RETURN);      // Activates display type selection and dynamically additional fields appear
	    dijit_form_TextBox_0.sendKeys(label);        // Label
	    textAreaValues.sendKeys(value);			 // Value
	    dijit_form_TextBox_1.sendKeys(defaultValue); // Default Value
	    dijit_form_TextBox_2.sendKeys(hint);		 // Hint
	    if(required)
	    	requiredCB.click();                      // Required Checkbox
	    if(userSearchable)							 
	    	searchableCB.click();					 // User Searchable Checkbox
	    if(systemIndexed)
	    	indexedCB.click();						 // System Indexed Checkbox
	    if(showInListing)
	    	listedCB.click();						 // Show In Listing Checkbox

	    moveToElement(saveButton_label);

	    saveButton_label.click();                    // Save button
	    Thread.sleep(250);
		return SeleniumPageManager.getBackEndPageManager().getPageObject(IStructureAddOrEdit_FieldsPage.class);
	}
	
	public IStructureAddOrEdit_FieldsPage addRelationshipsField(String label) throws Exception {
	    elementSelectBox.sendKeys("Relationships"); // Workaround for https://github.com/dotCMS/dotCMS/issues/4943
//	    elementSelectBox.sendKeys("Relationships Field");// Display type
	    Thread.sleep(250);
	    elementSelectBox.sendKeys(Keys.RETURN);      // Activates display type selection and dynamically additional fields appear
	    dijit_form_TextBox_0.sendKeys(label);        // Label
	    saveButton_label.click();                    // Save button
	    Thread.sleep(250);
		return SeleniumPageManager.getBackEndPageManager().getPageObject(IStructureAddOrEdit_FieldsPage.class);
	}
	
	public IStructureAddOrEdit_FieldsPage addSelectField(String label, String value, String defaultValue, String hint, boolean required, boolean userSearchable, boolean systemIndexed, boolean showInListing) throws Exception {
	    elementSelectBox.sendKeys("Select");		 // Display type
	    Thread.sleep(250);
	    elementSelectBox.sendKeys(Keys.RETURN);      // Activates display type selection and dynamically additional fields appear
	    dijit_form_TextBox_0.sendKeys(label);        // Label
	    //TODO dataTypetext;
	    textAreaValues.sendKeys(value);			 // Value
	    dijit_form_TextBox_1.sendKeys(defaultValue); // Default Value
	    dijit_form_TextBox_2.sendKeys(hint);		 // Hint
	    if(required)
	    	requiredCB.click();                      // Required Checkbox
	    if(userSearchable)							 
	    	searchableCB.click();					 // User Searchable Checkbox
	    if(systemIndexed)
	    	indexedCB.click();						 // System Indexed Checkbox
	    if(showInListing)
	    	listedCB.click();						 // Show In Listing Checkbox

	    moveToElement(saveButton_label);
	    
	    saveButton_label.click();                    // Save button
	    Thread.sleep(250);
		return SeleniumPageManager.getBackEndPageManager().getPageObject(IStructureAddOrEdit_FieldsPage.class);
	}
	
	public IStructureAddOrEdit_FieldsPage addTabDividerField(String label) throws Exception {
	    elementSelectBox.sendKeys("Tab"); // Workaround for https://github.com/dotCMS/dotCMS/issues/4943
//	    elementSelectBox.sendKeys("Tab Divider");	 // Display type
	    Thread.sleep(250);
	    elementSelectBox.sendKeys(Keys.RETURN);      // Activates display type selection and dynamically additional fields appear
	    dijit_form_TextBox_0.sendKeys(label);        // Label
	    saveButton_label.click();                    // Save button
	    Thread.sleep(250);
		return SeleniumPageManager.getBackEndPageManager().getPageObject(IStructureAddOrEdit_FieldsPage.class);
	}
	
	public IStructureAddOrEdit_FieldsPage addTagField(String label, String defaultValue, String hint, boolean required, boolean userSearchable) throws Exception {
	    elementSelectBox.sendKeys("Tag");   // Display type
	    Thread.sleep(250);
	    elementSelectBox.sendKeys(Keys.RETURN);      // Activates display type selection and dynamically additional fields appear
	    dijit_form_TextBox_0.sendKeys(label);        // Label
	    dijit_form_TextBox_1.sendKeys(defaultValue); // Default Value
	    dijit_form_TextBox_2.sendKeys(hint);		 // Hint
	    if(required)
	    	requiredCB.click();                      // Required Checkbox
	    if(userSearchable)							 
	    	searchableCB.click();					 // User Searchable Checkbox
	    saveButton_label.click();                    // Save button
	    Thread.sleep(250);
		return SeleniumPageManager.getBackEndPageManager().getPageObject(IStructureAddOrEdit_FieldsPage.class);		
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

	    moveToElement(saveButton_label);
	    scroll(0, 200);								 // Klugy workaround for Chrome Driver
	    saveButton_label.click();                    // Save button
	    
		return SeleniumPageManager.getBackEndPageManager().getPageObject(IStructureAddOrEdit_FieldsPage.class);
	}

	public IStructureAddOrEdit_FieldsPage addTextareaField(String label, String validationRegEx, String validation, String defaultValue, String hint, boolean required, boolean userSearchable, boolean systemIndexed) throws Exception {
	    elementSelectBox.sendKeys("Textarea");		 // Display type
	    Thread.sleep(250);
	    elementSelectBox.sendKeys(Keys.RETURN);      // Activates display type selection and dynamically additional fields appear
	    dijit_form_TextBox_0.sendKeys(label);        // Label
	    if(validation == null || validation.trim().isEmpty()) {
	    	regexCheck.sendKeys(validationRegEx);		 // Validation RegEx
	    }
	    else {
		    this.validation.clear();					 // RegEx Validation Selection
		    this.validation.sendKeys(validation);		 
		    this.validation.sendKeys(Keys.RETURN);
	    }
	    dijit_form_TextBox_1.sendKeys(defaultValue); // Default Value
	    dijit_form_TextBox_2.sendKeys(hint);		 // Hint
	    if(required)
	    	requiredCB.click();                      // Required Checkbox
	    if(userSearchable)							 
	    	searchableCB.click();					 // User Searchable Checkbox
	    if(systemIndexed)
	    	indexedCB.click();						 // System Indexed Checkbox
	    saveButton_label.click();                    // Save button
	    Thread.sleep(250);
		return SeleniumPageManager.getBackEndPageManager().getPageObject(IStructureAddOrEdit_FieldsPage.class);
	}
	
	public IStructureAddOrEdit_FieldsPage addTimeField(String label, String defaultValue, String hint, boolean required, boolean userSearchable, boolean systemIndexed, boolean showInListing) throws Exception {
	    elementSelectBox.sendKeys("Time");   // Display type
	    Thread.sleep(250);
	    elementSelectBox.sendKeys(Keys.RETURN);      // Activates display type selection and dynamically additional fields appear
	    dijit_form_TextBox_0.sendKeys(label);        // Label
	    dijit_form_TextBox_1.sendKeys(defaultValue); // Default Value
	    dijit_form_TextBox_2.sendKeys(hint);		 // Hint
	    if(required)
	    	requiredCB.click();                      // Required Checkbox
	    if(userSearchable)							 
	    	searchableCB.click();					 // User Searchable Checkbox
	    if(systemIndexed)
	    	indexedCB.click();						 // System Indexed Checkbox
	    if(showInListing)
	    	listedCB.click();						 // Show In Listing Checkbox
	    saveButton_label.click();                    // Save button
	    Thread.sleep(250);
		return SeleniumPageManager.getBackEndPageManager().getPageObject(IStructureAddOrEdit_FieldsPage.class);
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
		return SeleniumPageManager.getBackEndPageManager().getPageObject(IStructureAddOrEdit_FieldsPage.class);
	}

	public IStructureAddOrEdit_FieldsPage addKeyValueField(String label, String hint, boolean required, boolean userSearchable) throws Exception {
	    elementSelectBox.sendKeys("Key"); // Workaround for https://github.com/dotCMS/dotCMS/issues/4943
//	    elementSelectBox.sendKeys("Key/Value");		 // Display type
	    Thread.sleep(250);
	    elementSelectBox.sendKeys(Keys.RETURN);      // Activates display type selection and dynamically additional fields appear
	    dijit_form_TextBox_0.sendKeys(label);        // Label
	    dijit_form_TextBox_2.sendKeys(hint);		 // Hint
	    if(required)
	    	requiredCB.click();                      // Required Checkbox
	    if(userSearchable)							 
	    	searchableCB.click();					 // User Searchable Checkbox
	    saveButton_label.click();                    // Save button
	    Thread.sleep(250);
		return SeleniumPageManager.getBackEndPageManager().getPageObject(IStructureAddOrEdit_FieldsPage.class);
	}
}
