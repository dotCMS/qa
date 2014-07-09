package com.dotcms.qa.selenium.pages.backend;

import org.openqa.selenium.WebElement;

import com.dotcms.qa.selenium.pages.IBasePage;

public interface IHostSelectSiteToCopyDialog extends IBasePage {
	public void selectSiteToCopy(String siteToCopy);
	public IHostAddOrEditPage next() throws Exception;	
	public IHostCreateNewSiteDialog previous() throws Exception;
	public IHostPage cancel() throws Exception;
}







