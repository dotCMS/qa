package com.dotcms.qa.selenium.pages.backend.common;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.dotcms.qa.selenium.pages.backend.IHTMLPageAddOrEdit_PropertiesPage;
import com.dotcms.qa.selenium.pages.common.BasePage;

public class HTMLPageAddOrEdit_PropertiesPage extends BasePage implements
		IHTMLPageAddOrEdit_PropertiesPage {
	
	private WebElement titleField;
	private WebElement pageUrl;
	private WebElement template;
	private WebElement widget_template;
	private WebElement dijit_form_Button_6;  	// save button
	private WebElement dijit_form_Button_7;		// save and publish button
	private WebElement dijit_form_Button_8;		// cancel button
	
	public HTMLPageAddOrEdit_PropertiesPage(WebDriver driver) {
		super(driver);
	}

	public void setTitle(String title) {
		titleField.clear();
		titleField.sendKeys(title);
		titleField.sendKeys(Keys.TAB);
	}

	public void setURL(String URL) {
		pageUrl.clear();
		pageUrl.sendKeys(URL);
	}

	public void setTemplate(String templateName) throws Exception {
		WebElement downArrow = this.widget_template.findElement(By.className("dijitDownArrowButton"));
		downArrow.click();
		try{Thread.sleep(1000);} catch(Exception e) {};
		template.sendKeys(Keys.ARROW_DOWN);
		template.sendKeys(Keys.ENTER);
		downArrow.click();
		try{Thread.sleep(1000);} catch(Exception e) {};
		WebElement popup = getWebElement(By.id("template_popup"));
		
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
		dijit_form_Button_6.click();
	}

	public void saveAndPublish() {
		dijit_form_Button_7.click();
	}

	public void cancel() {
		dijit_form_Button_8.click();
	}
}
