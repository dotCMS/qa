package com.dotcms.qa.selenium.pages.backend;

import com.dotcms.qa.selenium.pages.IBasePage;

public interface ILicenseManagerPage extends IBasePage {
	public void activateLicenseKey(boolean activateEvenIfCurrentValidLicense, String licenseKey) throws Exception;
	public String getLicenseLevel() throws Exception;
}
