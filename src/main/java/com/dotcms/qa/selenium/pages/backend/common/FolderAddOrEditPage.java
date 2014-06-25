package com.dotcms.qa.selenium.pages.backend.common;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.dotcms.qa.selenium.pages.common.BasePage;
import com.dotcms.qa.selenium.pages.backend.IFolderAddOrEditPage;

public class FolderAddOrEditPage extends BasePage implements IFolderAddOrEditPage {
	
	private WebElement friendlyNameField;
	private WebElement titleField;
	private WebElement dijit_form_Button_5;	// Save button
	
	public FolderAddOrEditPage(WebDriver driver) {
		super(driver);
	}

	public void setTitle(String title) {
		friendlyNameField.clear();
		friendlyNameField.sendKeys(title);
		friendlyNameField.sendKeys(Keys.TAB);
	}

	public void setName(String name) {
		titleField.clear();
		titleField.sendKeys(name);
		titleField.sendKeys(Keys.TAB);
	}

	public void setSortOrder(int sortOrder) {
		// TODO Auto-generated method stub
	}

	public void setShowOnMenu(boolean showOnMenu) {
		// TODO Auto-generated method stub
	}

	public void setAllowedFileExtensions(String allowedFileExtensions) {
		// TODO Auto-generated method stub
	}

	public void setDefaultFileAssetType(String fileAssetType) {
		// TODO Auto-generated method stub
	}

	public void save() {
		dijit_form_Button_5.click();
	}

	public void cancel() {
		// TODO Auto-generated method stub
	}
}
