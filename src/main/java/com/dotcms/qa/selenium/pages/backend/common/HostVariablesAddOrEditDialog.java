package com.dotcms.qa.selenium.pages.backend.common;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

import com.dotcms.qa.selenium.pages.backend.IHostVariablesAddOrEditDialog;
import com.dotcms.qa.selenium.pages.common.BasePage;

public class HostVariablesAddOrEditDialog extends BasePage implements IHostVariablesAddOrEditDialog {
	private static final Logger logger = Logger.getLogger(HostVariablesAddOrEditDialog.class);
	private WebElement hostVariableName;
	private WebElement hostVariableKey;
	private WebElement hostVariableValue;
	@FindBy(how = How.ID, using = "dijit_form_Button_14")
	private WebElement saveButton;
	@FindBy(how = How.ID, using = "dijit_form_Button_15")
	private WebElement cancelButton;
	
	public HostVariablesAddOrEditDialog(WebDriver driver) {
		super(driver);
	}

	public void setFields(String name, String key, String value) {
		setName(name);
		setKey(key);
		setValue(value);
	}
	
	public void setName(String name){
		hostVariableName.clear();
		hostVariableName.sendKeys(name);
	}
	
	public void setKey(String key){
		hostVariableKey.clear();
		hostVariableKey.sendKeys(key);		
	}
	
	public void setValue(String value) {
		hostVariableValue.clear();
		hostVariableValue.sendKeys(value);		
	}
	
	public void save() {
		saveButton.click();
	}
	
	public void cancel() {
		cancelButton.click();		
	}
}