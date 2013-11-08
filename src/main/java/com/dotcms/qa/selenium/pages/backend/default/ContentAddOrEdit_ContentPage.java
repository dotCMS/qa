package com.dotcms.qa.selenium.pages.backend.default;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

import com.dotcms.qa.util.*;
import com.dotcms.qa.util.language.LanguageManager;
import com.dotcms.qa.selenium.pages.backend.*;
import com.dotcms.qa.selenium.pages.default.BasePage;
import com.dotcms.qa.selenium.util.SeleniumPageManager;

public class ContentAddOrEdit_ContentPage extends BasePage implements IContentAddOrEdit_ContentPage {

	private WebElement title;
	
	@FindBy(how = How.CSS, using = "span.mceIcon.mce_bold")
	@CacheLookup
	private WebElement boldButton;
	
	@FindBy(how = How.CSS, using = "span.mceIcon.mce_italic")
	@CacheLookup
	private WebElement italicButton;
	
	@FindBy(how = How.CSS, using = "span.mceIcon.mce_underline")
	@CacheLookup
	private WebElement underlineButton;
	
	private WebElement tinymce;
	
	public ContentAddOrEdit_ContentPage(WebDriver driver) {
		super(driver);
	}

	public void setTitle(String title) {
	    this.title.sendKeys(title);
	}
		
	public void toggleWYSIWYGBold() {
	    boldButton.click();
	}
	
	public void toggleWYSIWYGItalic() {
	    italicButton.click();		
	}
	
	public void toggleWYSIWYGUnderline() {
	    underlineButton.click();		
	}
	
	public void addWYSIWYGText(String textToAdd) {
	    switchToFrame("article_ifr");
	    tinymce.sendKeys(textToAdd);
	    switchToDefaultContent();
	}

	public IContentSearchPage saveAndPublish() throws Exception {
	    getWebElement(By.linkText(LanguageManager.getValue("Save-Publish"))).click();
		return SeleniumPageManager.getPageManager().getPageObject(IContentSearchPage.class);
	}
}
