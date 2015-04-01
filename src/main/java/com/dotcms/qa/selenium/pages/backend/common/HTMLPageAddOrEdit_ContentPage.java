package com.dotcms.qa.selenium.pages.backend.common;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.dotcms.qa.selenium.pages.backend.IHTMLPageAddOrEdit_ContentPage;
import com.dotcms.qa.selenium.pages.backend.IPreviewHTMLPage_Page;
import com.dotcms.qa.selenium.pages.common.BasePage;
import com.dotcms.qa.selenium.util.SeleniumPageManager;
import com.dotcms.qa.util.language.LanguageManager;

public class HTMLPageAddOrEdit_ContentPage extends BasePage implements
IHTMLPageAddOrEdit_ContentPage {

	private WebElement titleBox;
	private WebElement url;
	private WebElement templateSel;
	private WebElement widget_templateSel;
	private WebElement mainTabContainer_tablist_properties;
	private WebElement mainTabContainer_tablist_advancetab;
	private WebElement friendlyname;

	public HTMLPageAddOrEdit_ContentPage(WebDriver driver) {
		super(driver);
	}

	public void setTitle(String title) {
		mainTabContainer_tablist_properties.click();
		titleBox.clear();
		titleBox.sendKeys(title);
		titleBox.sendKeys(Keys.TAB);

		//set Friendly Name
		setFriendlyName(title);

		mainTabContainer_tablist_properties.click();
	}

	public void setURL(String URL) {
		mainTabContainer_tablist_properties.click();
		url.clear();
		url.sendKeys(URL);
	}

	public void setFriendlyName(String title) {
		//set Friendly Name
		mainTabContainer_tablist_advancetab.click();
		friendlyname.clear();
		friendlyname.sendKeys(title);
		friendlyname.sendKeys(Keys.TAB);
	}

	public void setTemplate(String templateName) throws Exception {
		mainTabContainer_tablist_properties.click();
		WebElement downArrow = this.widget_templateSel.findElement(By.className("dijitDownArrowButton"));
		downArrow.click();
		try{Thread.sleep(1000);} catch(Exception e) {};
		templateSel.sendKeys(Keys.ARROW_DOWN);
		templateSel.sendKeys(Keys.ENTER);
		downArrow.click();
		try{Thread.sleep(1000);} catch(Exception e) {};
		WebElement popup = getWebElement(By.id("templateSel_popup"));

		WebElement desiredTemplate = null;
		boolean seeMoreOption=true;
		while(seeMoreOption){
			seeMoreOption=false;
			List<WebElement> divs = popup.findElements(By.tagName("div"));
			for(WebElement div : divs) {
				if(div.getAttribute("class").contains("dijitMenuItem") && div.getText().trim().contains(templateName)) {
					desiredTemplate = div;
					break;
				}
				if(div.getAttribute("id").equals("templateSel_popup_next")){
					div.click();
					seeMoreOption=true;
				}
			}
		}
		if(desiredTemplate == null)
			throw new Exception("Unable to find template with name:  " + templateName);
		desiredTemplate.click();
	}

	public void save(){
		getWebElement(By.className("saveIcon")).click();
	}

	public void saveAndPublish() {
		WebElement we = getWebElement(By.partialLinkText(LanguageManager.getValue("Save-Publish")));
		we.click();
	}

	public void cancel(){
		getWebElement(By.className("cancelIcon")).click();
	}
}
