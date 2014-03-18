package com.dotcms.qa.selenium.pages.backend.common;

import org.openqa.selenium.WebDriver;

import com.dotcms.qa.selenium.pages.backend.ISelectAFileDialog;
import com.dotcms.qa.selenium.pages.common.BasePage;

public class SelectAFileDialog extends BasePage implements ISelectAFileDialog {
	public SelectAFileDialog(WebDriver driver){
		super(driver);
	}
	
	public void close() throws Exception{
		// TODO implement functionality
	}
}
