package com.dotcms.qa.selenium.pages.backend.common;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.dotcms.qa.selenium.pages.backend.ILanguagesPage;
import com.dotcms.qa.selenium.pages.common.BasePage;

/**
 * This class implements the methods defined in the ILanguagesPage interface
 * 
 * @author Oswaldo Gallango
 * @since 05/21/2015
 * @version 1.0
 * 
 */
public class LanguagesPage extends BasePage implements ILanguagesPage {

	public LanguagesPage(WebDriver driver) {
		super(driver);
	}

	/**
	 * Add a new language to the dotcms
	 * @param langCode Language code (2 character)
	 * @param countryCode Country code (2 character)
	 * @param language Language name
	 * @param country Country name
	 * @throws Exception
	 */
	public void addLanguage(String langCode, String countryCode, String language, String country) throws Exception {
		List<WebElement> buttons = getWebElement(By.className("buttonRow")).findElements(By.cssSelector("span[class='dijitReset dijitInline dijitButtonText']"));
		for (WebElement button : buttons) {
			if (button.getText().trim().equals(getLocalizedString("Add-New-Language"))) {
				button.click();
				sleep(1);
				break;
			}
		}
		getWebElement(By.name("languageCode")).sendKeys(langCode);
		getWebElement(By.name("countryCode")).sendKeys(langCode);
		getWebElement(By.name("language")).sendKeys(language);
		getWebElement(By.name("country")).sendKeys(country);

		buttons = getWebElements(By.cssSelector("span[class='dijitReset dijitInline dijitButtonText']"));
		for (WebElement button : buttons) {
			if (button.getText().trim().equals(getLocalizedString("save"))) {
				button.click();
				sleep(1);
				break;
			}
		}
	}

	/**
	 * Validates if the language exist
	 * 
	 * @param language Language name
	 * @param countryCode Two digits country code
	 * @return true if exist, false if not
	 * @throws Exception
	 */
	public boolean doesLanguageExist(String language, String countryCode) throws Exception {
		boolean languageExist = false;
		try{
			WebElement languageRow = findLanguageRow(language, countryCode);
			if(languageRow != null){
				languageExist=true;
			}
		}catch(Exception e){}
		return languageExist;
	}

	/**
	 * Find the specified language line
	 * 
	 * @param language
	 *            Language name
	 * @param countryCode
	 *            Two digits country code
	 * @return WebElement
	 * @throws Exception
	 */
	private WebElement findLanguageRow(String language, String countryCode) throws Exception {
		WebElement languageRow = null;
		List<WebElement> languages = getWebElement(By.className("listingTable")).findElements(By.tagName("td"));
		for(WebElement lang : languages){
			if(lang.getText().trim().contains(language+" ("+countryCode+")")){
				languageRow=lang;
				break;
			}
		}
		return languageRow;
	}
	
	/**
	 * Delete the specified language
	 * @param language Language name
	 * @param countryCode Two digits country code
	 * @throws Exception
	 */
	public void deleteLanguage(String language,String countryCode) throws Exception{
		WebElement languageRow = findLanguageRow(language, countryCode);
		List<WebElement> links = languageRow.findElements(By.tagName("a"));
		links.get(2).click();
		sleep(2);
		List<WebElement> buttons = getWebElements(By.cssSelector("span[class='dijitReset dijitInline dijitButtonText']"));
		for (WebElement button : buttons) {
			if (button.getText().trim().equals(getLocalizedString("Delete"))) {
				button.click();
				switchToAlert().accept();
				sleep(1);
				break;
			}
		}
	}
}
