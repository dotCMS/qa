package com.dotcms.qa.selenium.pages.backend.common;

import org.openqa.selenium.support.*;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.dotcms.qa.selenium.pages.backend.*;
import com.dotcms.qa.selenium.pages.common.BasePage;
import com.dotcms.qa.selenium.util.SeleniumPageManager;

public class StructureAddOrEdit_PropertiesPage extends BasePage implements	IStructureAddOrEdit_PropertiesPage {
	private WebElement dijit_form_TextBox_0;			// Structure name
	private WebElement dijit_form_TextBox_1;			// Structure description
	private WebElement structureType;                   //structure type 
	private WebElement structureType_popup0;
	@FindBy(how = How.ID, using = "HostSelector-hostFolderSelect")
	@CacheLookup
	private WebElement hostSelector;

	private WebElement saveButton_label;				// Save Button
	private WebElement workflowScheme;                  //workflow;
	private WebElement workflowScheme_popup0;

	public StructureAddOrEdit_PropertiesPage(WebDriver driver) {
		super(driver);
	}
	
	/**
	 * Create a new structure
	 * @param structureName Name of the structure
	 * @param type Structure type: content, widget,file, page
	 * @param structureDescription Description of the structure
	 * @param hostName Host associated to the structure
	 * @return IStructureAddOrEdit_FieldsPage
	 * @throws Exception
	 */
	public IStructureAddOrEdit_FieldsPage createNewStructure(String structureName, String type, String structureDescription, String hostName) throws Exception {
		return createNewStructure(structureName, type, structureDescription, hostName, null);
	}
	
	/**
	 * Create a new structure
	 * @param structureName Name of the structure
	 * @param type Structure type: content, widget,file, page
	 * @param structureDescription Description of the structure
	 * @param hostName Host associated to the structure
	 * @param workflowName Name of the worflow to use (Optional)
	 * @return IStructureAddOrEdit_FieldsPage
	 * @throws Exception
	 */
	public IStructureAddOrEdit_FieldsPage createNewStructure(String structureName, String type, String structureDescription, String hostName, String workflowName) throws Exception {
		structureType.clear();
		structureType.sendKeys(type);
		structureType_popup0.click();
	    Thread.sleep(500);
		dijit_form_TextBox_0.sendKeys(structureName);
	    dijit_form_TextBox_1.sendKeys(structureDescription);
	    Thread.sleep(500);
	    // Host selection
	    hostSelector.click();
	    hostSelector.sendKeys(hostName);
	    Thread.sleep(250);
	    hostSelector.sendKeys(Keys.TAB);
	    Thread.sleep(250);
	    if(workflowName != null && !workflowName.equals("")){
	    	workflowScheme.clear();
	    	workflowScheme.sendKeys(workflowName);
	    	workflowScheme_popup0.click();
	    	Thread.sleep(250);
	    }
	    
	    saveButton_label.click();
		return SeleniumPageManager.getBackEndPageManager().getPageObject(IStructureAddOrEdit_FieldsPage.class);
	}

}
