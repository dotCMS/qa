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

public class LicenseManagerPage extends BasePage implements ILicenseManagerPage {
    private static final Logger logger = Logger.getLogger(LicenseManagerPage.class);

	private WebElement uploadLicenseForm;
	private WebElement requestRadio;
	private WebElement pasteRadio;
	private WebElement license_text;
	private WebElement uploadButton_label;

	public LicenseManagerPage(WebDriver driver) {
		super(driver);
	}

	public void activateLicenseKey(boolean activateEvenIfCurrentValidLicense, String licenseKey) throws Exception {
		boolean alreadyHaveActiveLicense = false;
		try {
			pasteRadio.click();
			waitForVisibilityOfElement(By.name("license_text"), 10);
		}
		catch(NoSuchElementException e) {
			logger.trace("Must already have active license.", e);
			alreadyHaveActiveLicense = true;
		}
		
		license_text.clear();
		license_text.sendKeys(licenseKey);
		uploadButton_label.click();
		
		if(alreadyHaveActiveLicense) {
			try{
				Alert alert = switchToAlert();
				if(activateEvenIfCurrentValidLicense)
					alert.accept();
				else
					alert.dismiss();
			}
			catch (NoAlertPresentException e) {
				logger.trace("Must have not already been an active license after all.", e);
			}
		}
	}

	public String getLicenseLevel() throws Exception {

		// TODO - find a more reliable way of finding element (i.e. request id on elements)
		WebElement elem = uploadLicenseForm.findElement(By.tagName("dd"));
		if(elem != null)
			return elem.getText();
		else
			return null;
	}
}
