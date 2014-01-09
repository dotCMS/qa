package com.dotcms.qa.selenium.pages.backend.common;

import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.internal.seleniumemulation.Open;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

import com.dotcms.qa.selenium.pages.backend.*;
import com.dotcms.qa.selenium.pages.common.BasePage;
import com.dotcms.qa.selenium.util.SeleniumPageManager;
import com.dotcms.qa.util.language.LanguageManager;


public class HostPage extends BasePage implements IHostPage  {
	 private static final Logger logger = Logger.getLogger(VanityURLsPage.class);
	 	
	 	private WebElement dijit_form_Button_5_label;
		private WebElement startBlankHostRadio;
		private WebElement copyHostRadio;
		private WebElement id;
		private WebElement dijit_form_Button_9_label;
		private WebElement dijit_form_Button_6_label;
	    private WebElement hostsTableBody;
	    
		public HostPage(WebDriver driver) {
			super(driver);
		}
		
		
		public boolean doesHostExist(String hostName) {
			boolean retValue = false;
			List<WebElement> rows = hostsTableBody.findElements(By.tagName("tr"));
			for(WebElement row : rows) {
				try {
					WebElement col = row.findElement(By.tagName("td"));
					if(col.getText().trim().equals(hostName)) {
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
				
		public boolean editHost(String hostName, String newHostName ,String aliases) {
			boolean retValue = false;
			/*List<WebElement> rows = tableOfHost.findElements(By.tagName("tr"));
			for(WebElement row : rows) {
				try {
					WebElement col = row.findElement(By.tagName("td"));
					if(col.getText().trim().equals(hostName)) {
						row.click();
						IHostAddOrEditPage editPage = SeleniumPageManager.getPageManager().getPageObject(IHostAddOrEditPage.class);
						editPage.editHost(hostName, aliases);
						retValue = true;
					}
				}
				catch(NoSuchElementException e) {
					logger.trace("Row does not include td element", e);
					// Move on to next row and keep going
				}
				catch(Exception e) {
					logger.error("Unexpected error attempting to edit Host - oldHostName=" + hostName, e);
					break;	// break out of loop returning false because unable to edit vanity URL as requested
				}
			}*/
			return retValue; 
		}
		
		
		public boolean deleteHost(String hostName) {
			boolean retValue = false;
			List<WebElement> rows = hostsTableBody.findElements(By.tagName("tr"));
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
					logger.error("Unexpected error attempting to delete Host - hostName=" + hostName, e);
					break;	// break out of loop returning false because unable to delete vanity URL as requested
				}
			}
			return retValue;  
		}



		
}
