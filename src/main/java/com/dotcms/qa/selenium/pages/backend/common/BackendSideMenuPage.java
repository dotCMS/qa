package com.dotcms.qa.selenium.pages.backend.common;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.dotcms.qa.selenium.pages.backend.IBackendSideMenuPage;
import com.dotcms.qa.selenium.pages.backend.IPortletMenu;
import com.dotcms.qa.selenium.pages.common.BasePage;
import com.dotcms.qa.selenium.util.SeleniumPageManager;

public class BackendSideMenuPage extends BasePage implements
		IBackendSideMenuPage {

	//private WebElement dijit_form_Button_0_label;  // Admin Screen Button
	
	public BackendSideMenuPage(WebDriver driver) {
		super(driver);
	}

	public IPortletMenu gotoAdminScreen() throws Exception {
		//this.switchToFrame("frameMenu");
		//dijit_form_Button_0_label.click();
		this.switchToFrame("frameMenu");
		getWebElement(By.id("buttonRow")).findElement(By.cssSelector("span[id*='dijit_form_Button_'][class='dijitReset dijitInline dijitButtonText']")).click();
		this.switchToDefaultContent();
		return SeleniumPageManager.getBackEndPageManager().getPageObject(IPortletMenu.class);
	}

}
