package com.dotcms.qa.selenium.pages.backend.common;

import org.apache.log4j.Logger;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.dotcms.qa.selenium.pages.backend.*;
import com.dotcms.qa.selenium.pages.common.BasePage;

public class VanityURLsPage extends BasePage implements IVanityURLsPage {
    private static final Logger logger = Logger.getLogger(VanityURLsPage.class);

    private WebElement dijit_form_Button_7_label;
    
	public VanityURLsPage(WebDriver driver) {
		super(driver);
	}
	
	public void addVanityURL() {
		dijit_form_Button_7_label.click();
	}
}
