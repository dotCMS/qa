package com.dotcms.qa.selenium.pages.backend.common;

import java.util.List;
import org.apache.log4j.Logger;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.dotcms.qa.selenium.pages.backend.*;
import com.dotcms.qa.selenium.pages.common.BasePage;
import com.dotcms.qa.selenium.util.SeleniumPageManager;

public class HostSelectSiteToCopyDialog extends BasePage implements IHostSelectSiteToCopyDialog {
	private static final Logger logger = Logger.getLogger(HostSelectSiteToCopyDialog.class);

	@FindBy(how = How.ID, using ="id")
	private WebElement siteToCopyDropdown;
	@FindBy(how = How.ID, using = "dijit_form_Button_8")
	private WebElement previousButton;
	@FindBy(how = How.ID, using = "dijit_form_Button_9")
	private WebElement nextButton;
	@FindBy(how = How.ID, using = "dijit_form_Button_10")
	private WebElement cancelButton;
	
	public HostSelectSiteToCopyDialog(WebDriver driver) {
		super(driver);
	}

	public void selectSiteToCopy(String siteToCopy) {
		siteToCopyDropdown.clear();
		siteToCopyDropdown.sendKeys(siteToCopy);
		sleep(1);
		siteToCopyDropdown.sendKeys(Keys.TAB);
		sleep(1);
	}
	
	public IHostAddOrEditPage next() throws Exception{
		nextButton.click();
		return SeleniumPageManager.getBackEndPageManager().getPageObject(IHostAddOrEditPage.class);
	}
	
	public IHostCreateNewSiteDialog previous() throws Exception{
		previousButton.click();
		return SeleniumPageManager.getBackEndPageManager().getPageObject(IHostCreateNewSiteDialog.class);
	}
	
	public IHostPage cancel() throws Exception{
		cancelButton.click();
		return SeleniumPageManager.getBackEndPageManager().getPageObject(IHostPage.class);
	}
}
