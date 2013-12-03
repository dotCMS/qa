package com.dotcms.qa.selenium.pages.backend.common;

import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

import com.dotcms.qa.selenium.pages.backend.*;
import com.dotcms.qa.selenium.pages.common.BasePage;
import com.dotcms.qa.selenium.util.SeleniumPageManager;
import com.dotcms.qa.util.language.LanguageManager;

public class VanityURLsPage extends BasePage implements IVanityURLsPage {
    private static final Logger logger = Logger.getLogger(VanityURLsPage.class);

    private WebElement dijit_form_Button_7_label;
	@FindBy(how = How.CLASS_NAME, using = "listingTable")
    private WebElement tableOfVURLs;
    
	public VanityURLsPage(WebDriver driver) {
		super(driver);
	}
	
	public boolean doesVanityURLExist(String title) {
		boolean retValue = false;
		List<WebElement> rows = tableOfVURLs.findElements(By.tagName("tr"));
		for(WebElement row : rows) {
			try {
				WebElement col = row.findElement(By.tagName("td"));
				if(col.getText().trim().equals(title)) {
					retValue = true;
				}
			}
			catch(NoSuchElementException e) {
				logger.trace("Row does not include td element", e);
				// Move on to next row and keep going
			}
			catch(Exception e) {
				logger.error("Unexpected error attempting to iterate over vanity URLs - title=" + title, e);
				// Move on to next row and keep going
			}
		}
		return retValue;		
	}
	
	public void addVanityURLToHost(String title, String hostName, String vanityURL, String URLtoRedirectTo) throws Exception {
		dijit_form_Button_7_label.click();
		IVanityURLsAddOrEditPage addPage = SeleniumPageManager.getPageManager().getPageObject(IVanityURLsAddOrEditPage.class);
		addPage.addVanityURL(title, hostName, vanityURL, URLtoRedirectTo);
	}

	public void addVanityURLToAllHosts(String title, String vanityURL, String URLtoRedirectTo) throws Exception {
		this.addVanityURLToHost(title, LanguageManager.getValue("All-Hosts"), vanityURL, URLtoRedirectTo);
	}

	public boolean deleteVanityURL(String title) {
		boolean retValue = false;
		List<WebElement> rows = tableOfVURLs.findElements(By.tagName("tr"));
		for(WebElement row : rows) {
			try {
				WebElement col = row.findElement(By.tagName("td"));
				if(col.getText().trim().equals(title)) {
					row.click();
					IVanityURLsAddOrEditPage delPage = SeleniumPageManager.getPageManager().getPageObject(IVanityURLsAddOrEditPage.class);
					delPage.deleteVanityURL();
					retValue = true;
				}
			}
			catch(NoSuchElementException e) {
				logger.trace("Row does not include td element", e);
				// Move on to next row and keep going
			}
			catch(Exception e) {
				logger.error("Unexpected error attempting to delete vanity URL - title=" + title, e);
				break;	// break out of loop returning false because unable to delete vanity URL as requested
			}
		}
		return retValue;
	}
	
	public boolean editVanityURL(String oldTitle, String newTitle, String vanityURL, String URLtoRedirectTo) {
		boolean retValue = false;
		List<WebElement> rows = tableOfVURLs.findElements(By.tagName("tr"));
		for(WebElement row : rows) {
			try {
				WebElement col = row.findElement(By.tagName("td"));
				if(col.getText().trim().equals(oldTitle)) {
					row.click();
					IVanityURLsAddOrEditPage editPage = SeleniumPageManager.getPageManager().getPageObject(IVanityURLsAddOrEditPage.class);
					editPage.editVanityURL(newTitle, vanityURL, URLtoRedirectTo);
					retValue = true;
				}
			}
			catch(NoSuchElementException e) {
				logger.trace("Row does not include td element", e);
				// Move on to next row and keep going
			}
			catch(Exception e) {
				logger.error("Unexpected error attempting to edit vanity URL - oldTitle=" + oldTitle, e);
				break;	// break out of loop returning false because unable to edit vanity URL as requested
			}
		}
		return retValue;
	}
}
