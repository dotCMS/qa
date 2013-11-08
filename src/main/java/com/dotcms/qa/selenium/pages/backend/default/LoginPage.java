package com.dotcms.qa.selenium.pages.backend.default;

import java.util.*;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.dotcms.qa.selenium.pages.backend.*;
import com.dotcms.qa.selenium.pages.default.BasePage;
import com.dotcms.qa.selenium.util.SeleniumPageManager;

/**
* @author Brent Griffin
*/

public class LoginPage extends BasePage implements ILoginPage {
	private WebElement loginTextBox;
	private WebElement loginPasswordTextBox;
	private WebElement dijit_form_Button_1_label;
	private WebElement myLanguageImage;
	private Locale locale = Locale.getDefault();
	private boolean isInitialized = false;

	public LoginPage(WebDriver driver){
		super(driver);
	}

	public IPortletMenu login(String username, String password) throws Exception {
		if(!isInitialized)
			selectProperLanguage();
		loginTextBox.clear();
	    loginTextBox.sendKeys(username);
	    Thread.sleep(500);
	    loginPasswordTextBox.clear();
	    loginPasswordTextBox.sendKeys(password);
	    dijit_form_Button_1_label.click();
		return SeleniumPageManager.getPageManager().getPageObject(IPortletMenu.class);
	}

	public ILoginPage login_failure(String username, String password) throws Exception {
		if(!isInitialized)
			selectProperLanguage();
		// TODO - implement
		return SeleniumPageManager.getPageManager().getPageObject(ILoginPage.class);
	}
	
	private void selectProperLanguage() {
		String src = myLanguageImage.getAttribute("src");
		String partialLink = locale.getLanguage() + "_" + locale.getCountry();
		if(!src.contains(partialLink)) {
			myLanguageImage.click();
			String cssSelector = "img[alt=\"" + locale.getDisplayLanguage(locale) + "\"]";
			WebElement languageFlag = getWebElement(By.cssSelector(cssSelector));
			languageFlag.click();
		}
		isInitialized = true;
	}
}
