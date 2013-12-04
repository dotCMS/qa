package com.dotcms.qa.selenium.pages.backend.common;

import org.apache.log4j.Logger;
import org.openqa.selenium.Alert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.dotcms.qa.selenium.pages.backend.*;
import com.dotcms.qa.selenium.pages.common.BasePage;

public class VanityURLsAddOrEditPage extends BasePage implements IVanityURLsAddOrEditPage {
    private static final Logger logger = Logger.getLogger(VanityURLsAddOrEditPage.class);
    
    private WebElement vlTitle;
    private WebElement hostId;
    private WebElement url;
    private WebElement vlUri;
    private WebElement dijit_form_Button_6_label;	// Is save button if adding new vanity URL otherwise is delete button
    private WebElement dijit_form_Button_7_label;	// Only exists if edit scenario and in that case is save button
    
	public VanityURLsAddOrEditPage(WebDriver driver) {
		super(driver);
	}
	
	public void addVanityURL(String title, String hostName, String vanityURL, String URLtoRedirectTo) {
		vlTitle.sendKeys(title);
		hostId.clear();
		hostId.sendKeys(hostName);
		url.sendKeys(vanityURL);
		vlUri.sendKeys(URLtoRedirectTo);
		dijit_form_Button_6_label.click();
	}
	
	public void deleteVanityURL() {
		dijit_form_Button_6_label.click();
		Alert alert = switchToAlert();
		alert.accept();
	}

	public void editVanityURL(String title, String vanityURL, String URLtoRedirectTo) {
		vlTitle.clear();
		vlTitle.sendKeys(title);
		url.clear();
		url.sendKeys(vanityURL);
		vlUri.clear();
		vlUri.sendKeys(URLtoRedirectTo);
		dijit_form_Button_7_label.click();
	}
}
