package com.dotcms.qa.selenium.pages.backend.common;

import java.util.List;
import org.apache.log4j.Logger;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.dotcms.qa.selenium.pages.backend.*;
import com.dotcms.qa.selenium.pages.common.BasePage;
import com.dotcms.qa.selenium.util.SeleniumPageManager;

public class HostPage extends BasePage implements IHostPage  {
	private static final Logger logger = Logger.getLogger(HostPage.class);
	 	
 	private WebElement dijit_form_Button_5_label, dijit_form_Button_9_label, dijit_form_Button_6_label, dijit_form_Button_14_label;
	private WebElement startBlankHostRadio, copyHostRadio;
	private WebElement id;
	private WebElement dijit_form_Button_13_label;
	private WebElement hostVariableName, hostVariableValue, hostVariableKey;
 	
	public HostPage(WebDriver driver) {
		super(driver);
	}
	
	/*
	 * To verify if any host exist
	 * @hostName = Name of the host to verify
	 */
 	public boolean doesHostExist(String hostName) {
		return (returnHost(hostName) != null);
	}
	

	/*
	 * To return WebElement for any host
	 * @hostName = name of the host to retrieve
	 */
	public WebElement returnHost(String hostName) {
		WebElement retValue = null;	
		WebElement tableOfHost = getWebElement(By.className("listingTable"));
		for(WebElement anchor : tableOfHost.findElements(By.tagName("a"))) {
				if(anchor.getAttribute("innerHTML").startsWith(hostName)) {
				 retValue = anchor;
				 break;
				}
		}		
		return retValue;
	}
			  
    public void editHost(String hostName,String ahostName,String bhostName){
	    // TODO - implement
    }
	
    public boolean doesHostVariableExist(String hostName, String variableName) throws Exception {
    	IHostVariablesPage hostVarPage = getHostVariablesPage(hostName);
    	boolean retValue =  hostVarPage.doesHostVariableExist(variableName);
    	hostVarPage.close();
//    	reload();			// TODO - remove need for this reload call
    	return retValue;
    }
    
	public void addBlankHost(String hostName) throws Exception {
		dijit_form_Button_5_label.click();
		startBlankHostRadio.click();
		dijit_form_Button_6_label.click();
		IHostAddOrEditPage addPage = SeleniumPageManager.getBackEndPageManager().getPageObject(IHostAddOrEditPage.class);
		addPage.addHost(hostName);
	}
		
	public void addCopyExistingHost(String hostName, String setHost) throws Exception {
		dijit_form_Button_5_label.click();
		copyHostRadio.click();
		dijit_form_Button_6_label.click();
		id.clear();
		id.sendKeys(setHost);
		dijit_form_Button_9_label.click();
		IHostAddOrEditPage addPage = SeleniumPageManager.getBackEndPageManager().getPageObject(IHostAddOrEditPage.class);
		addPage.addHost(hostName);			
	}
			
	public void archiveHost(String hostName, boolean confirm) throws Exception {
		this.selectPopupMenuOption(hostName, getLocalizedString("Archive-Host"));
		Alert alert = this.switchToAlert();
		if(confirm) {
			alert.accept();
		}
		else {
			alert.dismiss();
		}
		reload();			// TODO - remove need for this reload call
	}

	public void deleteHost(String hostName, boolean confirm) throws Exception {
		this.selectPopupMenuOption(hostName, getLocalizedString("Delete-Host"));
		Alert alert = this.switchToAlert();
		if(confirm) {
			alert.accept();
		}
		else {
			alert.dismiss();
		}
		reload();			// TODO - remove need for this reload call
	}

	public void stopHost(String hostName, boolean confirm) throws Exception {
		this.selectPopupMenuOption(hostName, getLocalizedString("Stop-Host"));
		Alert alert = this.switchToAlert();
		if(confirm) {
			alert.accept();
		}
		else {
			alert.dismiss();
		}
		reload();			// TODO - remove need for this reload call
	}
		
	public void addHostVariable(String hostName, String varName, String varKey, String varValue) throws Exception {
		IHostVariablesPage varPage = getHostVariablesPage(hostName);
		varPage.addNewHostVariable(varName, varKey, varValue);
		varPage.close();
		reload();			// TODO - remove need for this reload call
	}	
	
	public void deleteHostVariable(String hostName, String varName, boolean confirm) throws Exception{
		IHostVariablesPage varPage = getHostVariablesPage(hostName);
		varPage.deleteHostVariable(varName, confirm);
		varPage.close();	
		reload();			// TODO - remove need for this reload call
	}
	
	public IHostVariablesPage getHostVariablesPage(String hostName) throws Exception {
		IHostVariablesPage retValue = null;
		if(selectPopupMenuOption(hostName, getLocalizedString("Edit-Host-Variables"))) {
			retValue = SeleniumPageManager.getBackEndPageManager().getPageObject(IHostVariablesPage.class);
		}
		return retValue;
	}
	
	private boolean selectPopupMenuOption(String hostName, String menuOption) throws Exception {
		boolean foundValue = false;
		sleep(500);
		rightClickElement(returnHost(hostName));	
		WebElement popupMenu = getWebElementClickable(By.className("dijitMenuPopup"));
		//this.hoverOverElement(popupMenu);
		List<WebElement> rows = popupMenu.findElements(By.tagName("tr"));
		WebElement prevRow = null;
		for(WebElement row : rows) {
			//this.hoverOverElement(row);
			//row = this.getWebElementClickable(row);
			if(prevRow != null) {
				logger.info("* prevRow.isDisplayed() = " + prevRow.isDisplayed());
				logger.info("* prevRow.isEnabled() = " + prevRow.isEnabled());
			}
			logger.info("* isDisplayed() = " + row.isDisplayed());
			logger.info("* isEnabled() = " + row.isEnabled());
			List<WebElement> labels = row.findElements(By.className("dijitMenuItemLabel"));
			for(WebElement label : labels) {
				//this.hoverOverElement(label);
				//label = this.getWebElementClickable(label);
				logger.info("label innerHTML = |" + label.getAttribute("innerHTML") + "|");
				if(label.getAttribute("innerHTML").trim().startsWith(menuOption)) {
					this.hoverOverElement(label);
					getWebElementClickable(label).click();
					foundValue = true;
					break;
				}
			}
			if(foundValue)
				break;
			prevRow = row;
		}
		return foundValue;
	}

	public void toggleShowArchived() {
		WebElement checkBox = null;
		List<WebElement> inputs = this.getWebElements(By.tagName("input"));
		for(WebElement input : inputs) {
			if(input.getAttribute("name").trim().equals("showDeleted")) {
				checkBox = input;
				break;
			}
		}
		if(checkBox != null) {
			checkBox.click();
		}
	}
}
