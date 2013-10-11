package com.dotcms.qa.selenium.pages.backend;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.dotcms.qa.selenium.pages.BasePage;
import com.dotcms.qa.selenium.pages.IBasePage;
import com.dotcms.qa.selenium.util.SeleniumPageManager;

public class StructuresPage extends BasePage implements IStructuresPage {

	private WebElement dijit_form_ComboButton_0_label;

	public StructuresPage(WebDriver driver) {
		super(driver);
	}

	public IStructureAddOrEdit_PropertiesPage getAddNewStructurePage() throws Exception {
		dijit_form_ComboButton_0_label.click();	// Add new structure button
		return SeleniumPageManager.getPageManager().getPageObject(IStructureAddOrEdit_PropertiesPage.class);
	}
}
