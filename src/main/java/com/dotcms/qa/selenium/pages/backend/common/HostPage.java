package com.dotcms.qa.selenium.pages.backend.common;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
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
 	//@FindBy(how = How.CLASS_NAME, using = "listingTable")
    //private WebElement tableOfHost;
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
				if(anchor.getAttribute("innerHTML").matches(hostName)) {
				 retValue = anchor;
				 break;
				}
		}		
		return retValue;
	}
			  
    public boolean editHost(String hostName,String ahostName,String bhostName){
	    boolean retValue = false;
	    return retValue;
    }
	
    public boolean doesHostVariableExist(String hostName, String variableName) throws Exception {
    	IHostVariablesPage hostVarPage = getHostVariablesPage(hostName);
    	boolean retValue =  hostVarPage.doesHostVariableExist(variableName);
    	hostVarPage.close();
    	reload();
    	return retValue;
    }
    
	public void addBlankHost(String hostName) throws Exception {
		dijit_form_Button_5_label.click();
		startBlankHostRadio.click();
		dijit_form_Button_6_label.click();
		IHostAddOrEditPage addPage = SeleniumPageManager.getPageManager().getPageObject(IHostAddOrEditPage.class);
		addPage.addHost(hostName);
	}
		
	public void addCopyExistingHost(String hostName, String setHost) throws Exception {
		dijit_form_Button_5_label.click();
		copyHostRadio.click();
		dijit_form_Button_6_label.click();
		id.clear();
		id.sendKeys(setHost);
		dijit_form_Button_9_label.click();
		IHostAddOrEditPage addPage = SeleniumPageManager.getPageManager().getPageObject(IHostAddOrEditPage.class);
		addPage.addHost(hostName);			
	}
			
	public boolean deleteHost(String hostName) {
		boolean retValue = false;
		WebElement tableOfHost = getWebElement(By.className("listingTable"));
		List<WebElement> rows = tableOfHost.findElements(By.tagName("tr"));
		for(WebElement row : rows) {
			try {
				WebElement col = row.findElement(By.tagName("td"));
				if(col.getText().trim().equals(hostName)) {
					IHostAddOrEditPage delPage = SeleniumPageManager.getPageManager().getPageObject(IHostAddOrEditPage.class);
					delPage.deleteHost(hostName);						
					retValue = true;
				}
			}
			catch(NoSuchElementException e) {
				logger.trace("Row does not include td element", e);
				// Move on to next row and keep going
			}
			catch(Exception e) {
				logger.error("Unexpected error attempting to iterate over Host - HostName=" + hostName, e);
				// Move on to next row and keep going
			}
		}
		return retValue;		
	}
		
	public void addHostVariable(String hostName, String varName, String varKey, String varValue) throws Exception {
		IHostVariablesPage varPage = getHostVariablesPage(hostName);
		varPage.addNewHostVariable(varName, varKey, varValue);
		varPage.close();
		reload();
	}	
	
	public void deleteHostVariable(String hostName, String varName, boolean confirm) throws Exception{
		IHostVariablesPage varPage = getHostVariablesPage(hostName);
		varPage.deleteHostVariable(varName, confirm);
		varPage.close();	
		reload();
	}
	
	public IHostVariablesPage getHostVariablesPage(String hostName) throws Exception {
		IHostVariablesPage retValue = null;
		WebDriverWait wait = getWaitObject(30);
		rightClickElement(returnHost(hostName));	
		WebElement popupMenu = getWebElement(By.className("dijitMenuPopup"));
		wait.until(ExpectedConditions.visibilityOf(popupMenu));
		this.hoverOverElement(popupMenu);
		List<WebElement> rows = popupMenu.findElements(By.tagName("tr"));
		for(WebElement row : rows) {
			this.hoverOverElement(row);
			List<WebElement> labels = row.findElements(By.className("dijitMenuItemLabel"));
			for(WebElement label : labels) {
				this.hoverOverElement(label);
				logger.info("label innerHTML = " + label.getAttribute("innerHTML"));
				if("Edit Host variables".equals(label.getAttribute("innerHTML").trim())) {
					wait.until(ExpectedConditions.visibilityOf(label));
					label.click();
					retValue = SeleniumPageManager.getPageManager().getPageObject(IHostVariablesPage.class);
					break;
				}
			}
			if(retValue != null)
				break;
		}
		return retValue;
	}
}
