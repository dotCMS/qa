package com.dotcms.qa.selenium.pages.backend;

import org.openqa.selenium.WebElement;

import com.dotcms.qa.selenium.pages.IBasePage;

public interface IHostCreateNewSiteDialog extends IBasePage {
	public void addBlankHost(String hostName) throws Exception;
	public void addCopyExistingHost(String hostName, String setHost) throws Exception;
}







