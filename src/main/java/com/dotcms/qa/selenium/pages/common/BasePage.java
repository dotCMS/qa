package com.dotcms.qa.selenium.pages.common;

import java.util.List;

import org.apache.log4j.Logger;

import org.openqa.selenium.Alert;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.dotcms.qa.selenium.pages.IBasePage;



/**
* Base Page class.  All page objects should extend this class.
* @author Brent Griffin
*/
public class BasePage implements IBasePage {
    private static final Logger logger = Logger.getLogger(BasePage.class);

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

	public String getTitle() {
		return driver.getTitle();
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

	public void scroll(int horizontalScroll, int verticalScroll) {
		((JavascriptExecutor) driver).executeScript("scroll(" + horizontalScroll + "," + verticalScroll + ");");
	}
	
    public void switchToFrame(String frameName) {
	    driver.switchTo().frame(frameName);
    }
    
    public Alert switchToAlert() {
    	return driver.switchTo().alert();
    }
    
    public void switchToDefaultContent() {
	    driver.switchTo().defaultContent();
    }

    public void waitForVisibilityOfElement(By by, int secondsToWait) {
		WebDriverWait wait = new WebDriverWait(driver, secondsToWait);
		wait.until(ExpectedConditions.visibilityOfElementLocated(by));
    }
    
	public void selectBackendHost(String host) throws NoSuchElementException {
		WebElement hostDiv = getWebElement(By.id("selectHostDiv"));
		if(hostDiv != null) {
			String hostName = hostDiv.getText();
			if (!host.equals(hostName)) {
				WebElement changeHost = getWebElement(By.className("changeHost"));
				changeHost.click();
				WebElement subNavHost = getWebElement(By.id("subNavHost"));
				subNavHost.clear();
				subNavHost.sendKeys(host);
				subNavHost.sendKeys(Keys.TAB);
			}
		}
	}
	
	public void doRigthClick(String IDToRigthClick){
		WebElement elementToRightClick = driver.findElement(By.id(IDToRigthClick));
		Actions action = new Actions(driver);
		action.contextClick(elementToRightClick);
		action.perform();
	}
	
	

}
