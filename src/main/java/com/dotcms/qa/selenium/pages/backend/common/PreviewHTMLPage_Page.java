package com.dotcms.qa.selenium.pages.backend.common;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.dotcms.qa.selenium.pages.backend.IPreviewHTMLPage_Page;
import com.dotcms.qa.selenium.pages.common.BasePage;

/**
 * This class implements the methods defined in the IPreviewHTMLPage_Page interface
 * @author Oswaldo Gallango
 * @since 01/21/2015
 * @version 1.0
 * 
 */
public class PreviewHTMLPage_Page extends BasePage implements IPreviewHTMLPage_Page {

	//private WebElement language_id;
	private WebDriver myDriver=null;
	public PreviewHTMLPage_Page(WebDriver driver) {
		super(driver);
		myDriver = driver;
	}
	
	/**
	 * Return current page  language
	 * @return String
	 * @throws Exception
	 */
	public String getCurrentLanguage() throws Exception{
		return getWebElement(By.id("combo_zone2")).findElement(By.cssSelector("input[type='text'][id='language_id']")).getText();
		//return getWebElement(By.id("language_id")).getText();
	}
	
	/**
	 * Change current page language
	 * @param language Language Name
	 * @throws Exception
	 */
	public void changeLanguage(String language) throws Exception{
		WebElement language_id = getWebElement(By.id("combo_zone2")).findElement(By.cssSelector("input[type='text'][id='language_id']"));
		language_id.sendKeys(language);
		language_id.sendKeys(Keys.RETURN);
	}

	public String getPageSource()  throws Exception{
		return myDriver.getPageSource();
	}
}
