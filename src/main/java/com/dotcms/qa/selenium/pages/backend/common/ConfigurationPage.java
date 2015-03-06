package com.dotcms.qa.selenium.pages.backend.common;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.dotcms.qa.selenium.pages.backend.IConfigurationPage;
import com.dotcms.qa.selenium.pages.backend.IPublishingEnvironments;
import com.dotcms.qa.selenium.pages.backend.IStructureAddOrEdit_PropertiesPage;
import com.dotcms.qa.selenium.pages.common.BasePage;
import com.dotcms.qa.selenium.util.SeleniumPageManager;

/**
 * This class implements the methods defined in the IConfigurationPage interface
 * to do the pushpublishing TestRail validations
 * @author Oswaldo Gallango
 * @since 03/06/2015
 * @version 1.0
 * 
 */
public class ConfigurationPage extends BasePage implements IConfigurationPage {

	private static final Logger logger = Logger.getLogger(ConfigurationPage.class);

	public ConfigurationPage(WebDriver driver) {
		super(driver);
	}

	
	/**
	 * Get the Publishing Environments tab in the configuration portlet
	 * @return IPublishingEnvironments 
	 * @throws Exception
	 */
	public IPublishingEnvironments getPublishingEnvironmentsTab() throws Exception{
		WebElement tab = getWebElement(By.id("mainTabContainer_tablist_remotePublishingTab"));
		tab.click();
		return SeleniumPageManager.getBackEndPageManager().getPageObject(IPublishingEnvironments.class);
	}


}
