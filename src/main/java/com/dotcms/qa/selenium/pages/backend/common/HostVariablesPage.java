package com.dotcms.qa.selenium.pages.backend.common;

import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

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
	
	public void deleteHostVariable(String variableName, boolean confirm) {
		WebElement elemToDelete = getHostVariableRowWebElement(variableName);
		if(elemToDelete != null) {
			elemToDelete.findElement(By.className("deleteIcon")).click();
		}
		Alert alert = this.switchToAlert();
		if(confirm) {
			alert.accept();
		}
		else {
			alert.dismiss();
		}
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
				System.out.println("*** column.getAttribute(\"innerHTML\").trim() = |" + column.getAttribute("innerHTML").trim() + "|***");
				if(variableName.equals(column.getAttribute("innerHTML").trim())) {
					retValue = row;
					break;
				}
			}
			if(retValue != null)
				break;
		}
		return retValue;
	}
	
	public IHostVariablesAddOrEditPage getHostVariablesAddScreen() throws Exception {
		WebDriverWait wait = this.getWaitObject(20);
		wait.until(ExpectedConditions.elementToBeClickable(By.id("dijit_form_Button_13")));
		getWebElement(By.id("dijit_form_Button_13")).click();
		return SeleniumPageManager.getPageManager().getPageObject(IHostVariablesAddOrEditPage.class);
	}
}
