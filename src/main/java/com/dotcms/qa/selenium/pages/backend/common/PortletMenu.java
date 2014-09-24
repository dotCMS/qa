package com.dotcms.qa.selenium.pages.backend.common;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.dotcms.qa.selenium.pages.backend.*;
import com.dotcms.qa.selenium.pages.common.BasePage;
import com.dotcms.qa.selenium.util.SeleniumPageManager;

import org.apache.log4j.Logger;

public class PortletMenu extends BasePage implements IPortletMenu {
    private static final Logger logger = Logger.getLogger(PortletMenu.class);

	public PortletMenu(WebDriver driver) {
		super(driver);
	}

	public IContentSearchPage getContentSearchPage() throws Exception {
	    hoverOverElement(getPortletElement("Content"));
	    getWebElement(By.linkText(getLocalizedString("com.dotcms.repackage.javax.portlet.title.EXT_11"))).click();
		return SeleniumPageManager.getBackEndPageManager().getPageObject(IContentSearchPage.class);		
	}

	public ILicenseManagerPage getLicenseManagerPage() throws Exception {
	    hoverOverElement(getPortletElement("System"));
	    getWebElement(By.linkText(getLocalizedString("com.dotcms.repackage.javax.portlet.title.EXT_LICENSE_MANAGER"))).click();
		return SeleniumPageManager.getBackEndPageManager().getPageObject(ILicenseManagerPage.class);
	}
	
	public IStructuresPage getStructuresPage() throws Exception {
	    hoverOverElement(getPortletElement("Structures"));
	    getWebElement(By.linkText(getLocalizedString("com.dotcms.repackage.javax.portlet.title.EXT_STRUCTURE"))).click();
		return SeleniumPageManager.getBackEndPageManager().getPageObject(IStructuresPage.class);
	}

	public IVanityURLsPage getVanityURLsPage() throws Exception {
	    hoverOverElement(getPortletElement("Site Browser"));
	    getWebElement(By.linkText(getLocalizedString("com.dotcms.repackage.javax.portlet.title.EXT_VIRTUAL_LINKS"))).click();
		return SeleniumPageManager.getBackEndPageManager().getPageObject(IVanityURLsPage.class);
	}
	
	public IHostPage getHostPage() throws Exception {
	    hoverOverElement(getPortletElement("System"));
	    getWebElementClickable(By.linkText(getLocalizedString("com.dotcms.repackage.javax.portlet.title.EXT_HOSTADMIN"))).click();
		return SeleniumPageManager.getBackEndPageManager().getPageObject(IHostPage.class);
	}

	public ISiteBrowserPage getSiteBrowserPage() throws Exception {
	    hoverOverElement(getPortletElement("Site Browser"));
	    getWebElementClickable(By.linkText(getLocalizedString("com.dotcms.repackage.javax.portlet.title.EXT_BROWSER"))).click();
		return SeleniumPageManager.getBackEndPageManager().getPageObject(ISiteBrowserPage.class);		
	}
	
	public WebElement getPortletElement(String portletTextKey) {
		WebElement retValue = null;
		String portletText = getLocalizedString(portletTextKey);
		logger.info("portletTextKey=" + portletTextKey + "|portletText=" + portletText);
		List<WebElement> allElements = getWebElements(By.className("navMenu-title")); 
		for (WebElement element: allElements) {
			try {
				if(portletText.equals(element.getText())){
					retValue = element;
				    break;
				}
				else {
//					logger.trace("portletText=" + portletText + "|element.getText()=" + element.getText() +"|element.getTagName()=" + element.getTagName());				
				}
			}
			catch (StaleElementReferenceException e) {
				// do nothing - keep iterating
				logger.info("Stale element exception - " + e.getMessage() + e.getStackTrace());
			}
		}
		if(retValue == null)
			throw new NullPointerException("Active element not found:  portletTextKey=" + portletTextKey + " portletText=" + portletText);
		return retValue;
	}
}
