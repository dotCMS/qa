package com.dotcms.qa.selenium.pages;

import java.util.List;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.dotcms.qa.selenium.util.SeleniumConfig;

public interface IBasePage {
	public String getLocalizedString(String key);
	public void executeJavaScript(String javaScript);
	public void executeJavaScript(String javaScript, Object args);
	public void sendText(By by, String text);
    public void sendText(String cssSelector, String text);
	public void setBinaryFileField(By by, String fileName);
	public void setDateField(By by, java.util.Date date);
	public void setTimeField(By by, java.util.Date time);
	public void setTextField(By by, String text);
    public boolean isTextPresent(String text);
    public boolean isElementPresent(By by);
    public boolean isElementPresent(String cssSelector);
    public boolean isElementPresentAndDisplay(By by);
	public String getSystemMessage();
    public String getTitle();
    public WebDriverWait getWaitObject(long timeoutInSeconds);
    public WebDriverWait getWaitObject(long timeoutInSeconds, long pollingIntervalInMilliseconds);
    public WebElement getWebElement(By by);
    public WebElement getWebElementClickable(By by);
    public WebElement getWebElementClickable(WebElement element);
    public WebElement getWebElementPresent(By by);
    public WebElement getWebElementVisible(By by);
    public List<WebElement> getWebElements(By by);
    public List<WebElement> getWebElementsPresent(By by);
    public List<WebElement> getWebElementsVisible(By by);
    public void hoverOverElement(WebElement element);
    public void moveToElement(WebElement element);
    public void rightClickElement(WebElement element);
    public void reload();
    public void scroll(int horizontalScroll, int verticalScroll);
    public void switchToFrame(String frameName);
    public Alert switchToAlert();
    public void switchToDefaultContent();
	public void toggleCheckbox(By by);
    public void waitForPresenseOfElement(By by, int secondsToWait);
    public void waitForVisibilityOfElement(By by, int secondsToWait);
    public void selectBackendHost(String host) throws NoSuchElementException;
	public void sleep();
	public void sleep(long durationMS);
    public Object executeScript(String script);
    
    /**
	 * Get the browser name and version
	 * @return String
	 */
	public String getBrowserName();
	
	/**
	 * Sleep method
	 */
	public void sleep();
	
	/**
	 * Sleep method
	 * @param seconds
	 */
	public void sleep(int seconds);
    
    
}
