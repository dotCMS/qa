package com.dotcms.qa.selenium.pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import com.dotcms.qa.util.LanguageManager;

/**
* Base Page class.  All page objects should extend this class.
* @author Brent Griffin
*/
public class BasePage implements IBasePage {
	private WebDriver driver;

	public BasePage(WebDriver driver) {
		this.driver = driver;
	}

	public void sendText(By by, String text) {
		driver.findElement(by).sendKeys(text);
	}

	/**
	* Send text keys to the element that finds by cssSelector.
	* It shortens "driver.findElement(By.cssSelector()).sendKeys()".
	* @param cssSelector
	* @param text
	*/
	public void sendText(String cssSelector, String text) {
		driver.findElement(By.cssSelector(cssSelector)).sendKeys(text);
	}


	public boolean isTextPresent(String text) {
		return driver.getPageSource().contains(text);
	}

	public boolean isElementPresent(By by) {
		try {
			driver.findElement(by);
			return true;
		} catch (NoSuchElementException e) {
			return false;
		}
	}

	/**
	* Is the Element present in the DOM.
	* 
	* @param _cssSelector 		element locater
	* @return					WebElement
	*/
	public boolean isElementPresent(String cssSelector) {
		try {
			driver.findElement(By.cssSelector(cssSelector));
			return true;
		} catch (NoSuchElementException e) {
			return false;
		}
	}


	/**
	* Checks if the element is in the DOM and displayed. 
	* 
	* @param by - selector to find the element
	* @return true or false
	*/
	public boolean isElementPresentAndDisplay(By by) {
		try {
			return driver.findElement(by).isDisplayed();
		} catch (NoSuchElementException e) {
			return false;
		}
	}

	/** 
	* @param by 	element locator. 
	* @return 		the first WebElement matching locator
	*/
	public WebElement getWebElement(By by){
		return driver.findElement(by);
	}

	public List<WebElement> getWebElements(By by){
		return driver.findElements(by);
	}

	public void hoverOverElement(WebElement element){
		Actions builder = new Actions(driver);
		builder.moveToElement(element).build().perform();
	}
	
    public void switchToFrame(String frameName) {
	    driver.switchTo().frame(frameName);
    }
    
    public void switchToDefaultContent() {
	    driver.switchTo().defaultContent();
    }
}
