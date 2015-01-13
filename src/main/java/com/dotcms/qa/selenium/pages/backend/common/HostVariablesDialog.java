package com.dotcms.qa.selenium.pages.backend.common;

import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.ui.ExpectedConditions;
import com.dotcms.qa.selenium.pages.backend.IHostVariablesAddOrEditDialog;
import com.dotcms.qa.selenium.pages.backend.IHostVariablesDialog;
import com.dotcms.qa.selenium.pages.common.BasePage;
import com.dotcms.qa.selenium.util.SeleniumPageManager;

public class HostVariablesDialog extends BasePage implements IHostVariablesDialog {
	private static final Logger logger = Logger.getLogger(HostVariablesDialog.class);
/*
	@FindBy(how = How.CLASS_NAME, using = "dijitDialogCloseIcon")
	private WebElement closeButton;
*/
	private WebElement hostVariablesTable;
	
	public HostVariablesDialog(WebDriver driver) {
		super(driver);
	}
	
	public void addNewHostVariable(String name, String key, String value) throws Exception {
		IHostVariablesAddOrEditDialog addVarPage = getHostVariablesAddScreen();
		addVarPage.setFields(name, key, value);
		addVarPage.save();
	}
	
	public void close() {
		this.executeJavaScript("dijit.byId('viewHostVariablesDialog').hide();");
		sleep(1);
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
		return getHostVariableRowWebElement(variableName) != null;
	}
		
	private WebElement getHostVariableRowWebElement(String variableName) {
		WebElement retValue = null;
		List<WebElement> rows = hostVariablesTable.findElements(By.tagName("tr"));
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
	
	public IHostVariablesAddOrEditDialog getHostVariablesAddScreen() throws Exception {
		WebElement addNewSiteVariableButton = null;
		WebElement div = this.getWebElement(By.id("viewHostVariablesDialog"));
		List<WebElement> spans = div.findElements(By.tagName("span"));
		for(WebElement span : spans) {
			String widgetId = span.getAttribute("widgetid");
			logger.info("widgetId = " + widgetId);
			if(widgetId != null && "dijit_form_Button_13".equals(widgetId.trim())) {
				addNewSiteVariableButton = span.findElement(By.className("dijitButtonNode"));
				break;
			}
		}
		addNewSiteVariableButton.click();
		return SeleniumPageManager.getBackEndPageManager().getPageObject(IHostVariablesAddOrEditDialog.class);
	}
}
