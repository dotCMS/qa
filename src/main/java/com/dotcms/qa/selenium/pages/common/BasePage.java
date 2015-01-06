package com.dotcms.qa.selenium.pages.common;

import java.text.DateFormat;
import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.Alert;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.dotcms.qa.selenium.pages.IBasePage;
import com.dotcms.qa.selenium.util.SeleniumConfig;
import com.dotcms.qa.util.WebKeys;
import com.dotcms.qa.util.language.LanguageManager;



/**
* Base Page class.  All page objects should extend this class.
* @author Brent Griffin
*/
public class BasePage implements IBasePage {
    private static final Logger logger = Logger.getLogger(BasePage.class);

	private WebDriver driver;
	private WebDriverWait wait = null;

	public BasePage(WebDriver driver) {
		this.driver = driver;
		this.wait = getWaitObject(30, 500);
	}

	public String getLocalizedString(String key) {
		return LanguageManager.getValue(key);
	}
	
	public void executeJavaScript(String javaScript) {
		((JavascriptExecutor)this.driver).executeScript(javaScript);
	}

	/*
	 * Per the Javadoc for JavascriptExecutor:
	 *   Arguments must be a number, a boolean, a String, WebElement, or a List of any combination of the above.
	 *   An exception will be thrown if the arguments do not meet these criteria. The arguments will be made available
	 *   to the JavaScript via the "arguments" magic variable, as if the function were called via "Function.apply"
	 */
	public void executeJavaScript(String javaScript, Object args) {
		((JavascriptExecutor)this.driver).executeScript(javaScript, args);
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

	public void setBinaryFileField(By by, String fileName) {
		sendText(by, fileName);
	}

	public void setDateField(By by, java.util.Date date) {
		WebElement elem = driver.findElement(by);
		elem.clear();
		elem.sendKeys(DateFormat.getDateInstance(DateFormat.SHORT).format(date));
		elem.sendKeys(Keys.TAB);
	}
	
	public void setTimeField(By by, java.util.Date time) {
		WebElement elem = driver.findElement(by);
		elem.clear();
		logger.info("DateFormat.getTimeInstance(DateFormat.SHORT).format(time) = " + DateFormat.getTimeInstance(DateFormat.SHORT).format(time));
		elem.sendKeys(DateFormat.getTimeInstance(DateFormat.SHORT).format(time));
		elem.sendKeys(Keys.TAB);
		
	}
	
	public void setTextField(By by, String text) {
		sendText(by, text);
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

	public String getSystemMessage() {
		this.waitForPresenseOfElement(By.className("systemMessages"), 10);
		return driver.findElement(By.className("systemMessages")).getText();
	}

	public String getTitle() {
		return driver.getTitle();
	}
	
	public WebDriverWait getWaitObject(long timeoutInSeconds) {
		return new WebDriverWait(driver, timeoutInSeconds);	
	}

	public WebDriverWait getWaitObject(long timeoutInSeconds, long pollingIntervalInMilliseconds) {
		return new WebDriverWait(driver, timeoutInSeconds, pollingIntervalInMilliseconds);	
	}

	/** 
	* @param by 	element locator. 
	* @return 		the first WebElement matching locator
	*/
	public WebElement getWebElement(By by){
		return driver.findElement(by);
	}

    public WebElement getWebElementClickable(By by) {
		wait.until(ExpectedConditions.elementToBeClickable(by));
		return getWebElement(by);
    }
    
    public WebElement getWebElementClickable(WebElement element) {
		wait.until(ExpectedConditions.elementToBeClickable(element));
		return element;    	
    }

    public WebElement getWebElementPresent(By by) {
		wait.until(ExpectedConditions.presenceOfElementLocated(by));
		return getWebElement(by);
    }

    public WebElement getWebElementVisible(By by) {
		wait.until(ExpectedConditions.visibilityOfElementLocated(by));
		return getWebElement(by);
    }

	public List<WebElement> getWebElements(By by){
		return driver.findElements(by);
	}

	public List<WebElement> getWebElementsPresent(By by){
		wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(by));
		return getWebElements(by);
	}

	public List<WebElement> getWebElementsVisible(By by){
		wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(by));
		return getWebElements(by);
	}

	public void hoverOverElement(WebElement element){
		Actions builder = new Actions(driver);
		if(getBrowserName().equals(WebKeys.SAFARI_BROWSER_NAME)){
			String mouseOverScript = "if(document.createEvent){var evObj = document.createEvent('MouseEvents');evObj.initEvent('mouseover', true, false); arguments[0].dispatchEvent(evObj);} else if(document.createEventObject) { arguments[0].fireEvent('onmouseover');}";
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript(mouseOverScript, element);
		}else{
			builder.moveToElement(element).build().perform();
		}
		try{Thread.sleep(1000);} catch(InterruptedException e){}
	}

	/**
	 * Move the mouse to the specified webelement
	 */
	public void moveToElement(WebElement element) {
		// actions moveToElement does not seem to work in chrome or firefox until selenium ver 2.40
		Actions builder = new Actions(driver);
		builder.moveToElement(element, 0, 0).build().perform();
	}
	
	public void rightClickElement(WebElement element){
		Actions action = new Actions(driver);
		action.moveToElement(element).contextClick(element);
		action.build().perform();
	}

	public void reload(){
		driver.navigate().refresh();	
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

	public void toggleCheckbox(By by) {
		WebElement elem = getWebElement(by);
		elem.click();
	}
	
    public void waitForPresenseOfElement(By by, int secondsToWait) {
		WebDriverWait wait = new WebDriverWait(driver, secondsToWait);
		wait.until(ExpectedConditions.presenceOfElementLocated(by));
    }

    public void waitForVisibilityOfElement(By by, int secondsToWait) {
		WebDriverWait wait = new WebDriverWait(driver, secondsToWait);
		wait.until(ExpectedConditions.visibilityOfElementLocated(by));
    }
    
	public void selectBackendHost(String host) throws NoSuchElementException {
		WebElement hostDiv = getWebElement(By.id("selectHostDiv"));
		if(hostDiv == null)
			throw new NoSuchElementException("Missing id: selectHostDiv");
		String hostName = hostDiv.getText();
		if (!host.equals(hostName)) {
			WebElement changeHost = getWebElement(By.className("changeHost"));
			changeHost.click();
			WebElement subNavHost = getWebElement(By.id("subNavHost"));
			subNavHost.clear();
			subNavHost.sendKeys(host);
			subNavHost.sendKeys(Keys.ENTER);
//			subNavHost.sendKeys(Keys.TAB);
		}
	}
		
	public Object  executeScript(final String script){
		Object  obj = null;
		JavascriptExecutor js = (JavascriptExecutor) driver;
		obj =  js.executeScript(script);
		return obj;
	}
	
	/**
	 * Get the browser name and version
	 * @return String
	 */
	public String getBrowserName(){
		return SeleniumConfig.getConfig().getProperty("browserToTarget").toLowerCase();
	}
	
	/**
	 * Sleep method
	 */
	public void sleep() {
		try{
			Thread.sleep(1000);
		}catch(Exception e){
			logger.error(e);
		}
	}
	
	/**
	 * Sleep method
	 * @param seconds
	 */
	public void sleep(int seconds) {
		try{
			Thread.sleep(seconds*1000);
		}catch(Exception e){
			logger.error(e);
		}
	}
}
