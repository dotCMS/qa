package com.dotcms.qa.selenium.pages.backend.common;

import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import bsh.This;

import com.dotcms.qa.selenium.pages.backend.IHostVariablesAddOrEditPage;
import com.dotcms.qa.selenium.pages.backend.IHostVariablesPage;
import com.dotcms.qa.selenium.pages.common.BasePage;
import com.dotcms.qa.selenium.util.SeleniumPageManager;

public class HostVariablesPage extends BasePage implements IHostVariablesPage {
	private static final Logger logger = Logger.getLogger(HostVariablesPage.class);

	private WebElement viewHostVariablesDialog = null;
	
	public HostVariablesPage(WebDriver driver) {
		super(driver);
	}
	
	public void addNewHostVariable(String name, String key, String value) throws Exception {
		IHostVariablesAddOrEditPage addVarPage = getHostVariablesAddScreen();
		addVarPage.setFields(name, key, value);
		addVarPage.save();
	}
	
	public void close() {
		this.viewHostVariablesDialog.findElement(By.className("dijitDialogCloseIcon")).click();
	}
	
	public void deleteHostVariable(String name) {
		
	}
	
	public boolean doesHostVariableExist(String variableName) {
		return this.getHostVariableRowWebElement(variableName) != null;
	}
		
	private WebElement getHostVariableRowWebElement(String variableName) {
		WebElement retValue = null;
		WebElement tbody = this.getWebElement(By.id("hostVariablesTable"));
		List<WebElement> rows = tbody.findElements(By.tagName("tr"));
		for(WebElement row : rows) {
			List<WebElement> columns = row.findElements(By.tagName("td"));
			for(WebElement column : columns) {
				if(variableName.equals(column.getAttribute("innerHTML").trim())) {
					retValue = row;
					break;
				}
			}
		}
		return retValue;
	}
	
	public IHostVariablesAddOrEditPage getHostVariablesAddScreen() throws Exception {
		WebElement button = viewHostVariablesDialog.findElement(By.id("dijit_form_Button_13"));

		WebDriverWait wait = new WebDriverWait(driver, 20/*seconds*/);
		wait.until(ExpectedConditions.elementToBeClickable(By.id("dijit_form_Button_13")));
		this.getWebElement(By.id("dijit_form_Button_13")).click();

		//button.click();
		return SeleniumPageManager.getPageManager().getPageObject(IHostVariablesAddOrEditPage.class);
	}
}
