package com.dotcms.qa.selenium.pages.backend.common;

import com.dotcms.qa.selenium.pages.backend.IHTMLPageAddDialog;

import org.openqa.selenium.WebElement;

public class HTMLPageAddDialog implements IHTMLPageAddDialog {

	private WebElement selectedPageAssetButton;
	
	@Override
	public void select() {
		selectedPageAssetButton.click();
	}

}
