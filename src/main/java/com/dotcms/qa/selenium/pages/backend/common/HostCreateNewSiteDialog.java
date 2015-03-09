package com.dotcms.qa.selenium.pages.backend.common;

import java.util.List;
import org.apache.log4j.Logger;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.dotcms.qa.selenium.pages.backend.*;
import com.dotcms.qa.selenium.pages.common.BasePage;
import com.dotcms.qa.selenium.util.SeleniumPageManager;

public class HostCreateNewSiteDialog extends BasePage implements IHostCreateNewSiteDialog  {
	private static final Logger logger = Logger.getLogger(HostCreateNewSiteDialog.class);

	private WebElement startBlankHostRadio;
	private WebElement copyHostRadio;
	@FindBy(how = How.ID, using = "dijit_form_Button_6")
	private WebElement nextButton;
	@FindBy(how = How.ID, using = "dijit_form_Button_7")
 	private WebElement cancelButton;
 	
	public HostCreateNewSiteDialog(WebDriver driver) {
		super(driver);
	}
	
	public void addBlankHost(String hostName) throws Exception {
		startBlankHostRadio.click();
		nextButton.click();
		IHostAddOrEditPage addPage = SeleniumPageManager.getBackEndPageManager().getPageObject(IHostAddOrEditPage.class);
		addPage.addHost(hostName);
	}
		
	public void addCopyExistingHost(String hostName, String hostToCopy) throws Exception {
		copyHostRadio.click();
		nextButton.click();

		IHostSelectSiteToCopyDialog selectSiteToCopyDlg = SeleniumPageManager.getBackEndPageManager().getPageObject(IHostSelectSiteToCopyDialog.class);
		selectSiteToCopyDlg.selectSiteToCopy(hostToCopy);
		IHostAddOrEditPage addHostPage = selectSiteToCopyDlg.next();
		addHostPage.addHost(hostName);			
	}			
}
