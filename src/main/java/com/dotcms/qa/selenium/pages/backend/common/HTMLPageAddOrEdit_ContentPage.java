package com.dotcms.qa.selenium.pages.backend.common;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

import com.dotcms.qa.selenium.pages.backend.IHTMLPageAddOrEdit_ContentPage;
import com.dotcms.qa.selenium.pages.common.BasePage;
import com.dotcms.qa.util.language.LanguageManager;

public class HTMLPageAddOrEdit_ContentPage extends BasePage implements
		IHTMLPageAddOrEdit_ContentPage {
	
	private WebElement titleBox;
	private WebElement url;
	private WebElement templateSel;
	private WebElement widget_templateSel;
	
	public HTMLPageAddOrEdit_ContentPage(WebDriver driver) {
		super(driver);
	}

	public void setTitle(String title) {
		titleBox.clear();
		titleBox.sendKeys(title);
		titleBox.sendKeys(Keys.TAB);
	}

	public void setURL(String URL) {
		url.clear();
		url.sendKeys(URL);
	}

	public void setTemplate(String templateName) throws Exception {
		WebElement downArrow = this.widget_templateSel.findElement(By.className("dijitDownArrowButton"));
		downArrow.click();
		try{Thread.sleep(1000);} catch(Exception e) {};
		templateSel.sendKeys(Keys.ARROW_DOWN);
		templateSel.sendKeys(Keys.ENTER);
		downArrow.click();
		try{Thread.sleep(1000);} catch(Exception e) {};
		WebElement popup = getWebElement(By.id("templateSel_popup"));
		
		WebElement desiredTemplate = null;
		List<WebElement> divs = popup.findElements(By.tagName("div"));
		for(WebElement div : divs) {
			if(div.getAttribute("class").contains("dijitMenuItem") && div.getText().trim().equals(templateName)) {
				desiredTemplate = div;
				break;
			}
		}
		if(desiredTemplate == null)
			throw new Exception("Unable to find template with name:  " + templateName);
		desiredTemplate.click();
	}

	public void save() {
		getWebElement(By.className("saveIcon")).click();
	}

	public void saveAndPublish() {
		WebElement we = getWebElement(By.partialLinkText(LanguageManager.getValue("Save-Publish")));
		we.click();
	}

	public void cancel() {
		getWebElement(By.className("cancelIcon")).click();
	}
}
