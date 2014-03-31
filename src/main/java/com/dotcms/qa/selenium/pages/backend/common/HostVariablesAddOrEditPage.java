package com.dotcms.qa.selenium.pages.backend.common;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.dotcms.qa.selenium.pages.backend.IHostVariablesAddOrEditPage;
import com.dotcms.qa.selenium.pages.common.BasePage;

public class HostVariablesAddOrEditPage extends BasePage implements IHostVariablesAddOrEditPage {
	private static final Logger logger = Logger.getLogger(HostVariablesAddOrEditPage.class);
	private WebElement editHostVariable = null;  // addOrEditVariable dialog div
	
	public HostVariablesAddOrEditPage(WebDriver driver) {
		super(driver);
	}

	public void setFields(String name, String key, String value) {
		setName(name);
		setKey(key);
		setValue(value);
	}
	
	public void setName(String name){
		WebElement nameField = this.editHostVariable.findElement(By.id("widget_hostVariableName")).findElement(By.tagName("input"));
		logger.info("name isDisplayed? - " + nameField.isDisplayed());
		logger.info("name isEnabled? - " + nameField.isEnabled());
		nameField.clear();
		nameField.sendKeys(name);
	}
	
	public void setKey(String key){
		WebElement keyField = this.editHostVariable.findElement(By.id("widget_hostVariableKey")).findElement(By.tagName("input"));
		keyField.clear();
		keyField.sendKeys(key);		
	}
	
	public void setValue(String value) {
		WebElement valueField = this.editHostVariable.findElement(By.id("hostVariableValue"));
		valueField.clear();
		valueField.sendKeys(value);		
	}
	
	public void save() {
		this.editHostVariable.findElement(By.id("dijit_form_Button_14")).click();
	}
	
	public void cancel() {
		this.editHostVariable.findElement(By.id("dijit_form_Button_15")).click();		
	}
}