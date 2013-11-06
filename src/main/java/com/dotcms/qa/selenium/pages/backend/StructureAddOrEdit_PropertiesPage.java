package com.dotcms.qa.selenium.pages.backend;

import org.openqa.selenium.support.*;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.dotcms.qa.selenium.pages.BasePage;
import com.dotcms.qa.selenium.util.SeleniumPageManager;

public class StructureAddOrEdit_PropertiesPage extends BasePage implements	IStructureAddOrEdit_PropertiesPage {
	private WebElement dijit_form_TextBox_0;			// Structure name
	private WebElement dijit_form_TextBox_1;			// Structure description

	@FindBy(how = How.ID, using = "HostSelector-hostFolderSelect")
	@CacheLookup
	private WebElement hostSelector;

	private WebElement saveButton_label;				// Save Button

	public StructureAddOrEdit_PropertiesPage(WebDriver driver) {
		super(driver);
	}
	
	public IStructureAddOrEdit_FieldsPage createNewStructure(String structureName, String structureDescription, String hostName) throws Exception {
	    dijit_form_TextBox_0.sendKeys(structureName);
	    dijit_form_TextBox_1.sendKeys(structureDescription);

	    // Host selection
	    hostSelector.click();
	    hostSelector.sendKeys(hostName);
	    Thread.sleep(250);
	    hostSelector.sendKeys(Keys.TAB);
	    saveButton_label.click();
		return SeleniumPageManager.getPageManager().getPageObject(IStructureAddOrEdit_FieldsPage.class);
	}

}
