package com.dotcms.qa.selenium.pages.backend.common;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.dotcms.qa.selenium.pages.backend.ICategoriesDialog;
import com.dotcms.qa.selenium.pages.common.BasePage;

public class CategoriesDialog extends BasePage implements ICategoriesDialog {
    private static final Logger logger = Logger.getLogger(CategoriesDialog.class);

    public CategoriesDialog(WebDriver driver) {
		super(driver);
	}
	
	public void close() throws Exception{
		this.executeJavaScript("dijit.byId('categoriesDialog1').hide();");
		Thread.sleep(250);
	}

	public void addCategory(int index) {
		this.executeJavaScript("addCat1(" + index + ");");
	}

	public void addTopLevelCategory(String categoryName) throws Exception {
        search(categoryName);
        Thread.sleep(250);
        addCategory(0);
	}
	
	public void search(String categoryName) {
		this.executeJavaScript("clearCatFilter1();");
        setTextField(By.id("catFilter1"), categoryName);
        this.executeJavaScript("doSearch1();");
	}
}
