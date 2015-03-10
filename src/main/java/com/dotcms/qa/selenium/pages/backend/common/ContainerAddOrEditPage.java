package com.dotcms.qa.selenium.pages.backend.common;

import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.dotcms.qa.selenium.pages.backend.IContainerAddOrEditPage;
import com.dotcms.qa.selenium.pages.backend.IContainersPage;
import com.dotcms.qa.selenium.pages.common.BasePage;
import com.dotcms.qa.selenium.util.SeleniumPageManager;

/**
 * This class implements the methods defined in the IContainerAddOrEditPage interface
 * @author Oswaldo Gallango
 * @since 03/10/2015
 * @version 1.0
 * 
 */
public class ContainerAddOrEditPage extends BasePage implements IContainerAddOrEditPage{

	public ContainerAddOrEditPage(WebDriver driver) {
		super(driver);
	}

	/**
	 * Fill the container page fields
	 * @param containerFields	String map wit all the field of the container to be set
	 * @throws Exception
	 */
	public void setFields(Map<String,String> containerFields) throws Exception{

		for(String key : containerFields.keySet()){
			try{
				WebElement element = getWebElement(By.id(key));
				element.clear();
				element.sendKeys(containerFields.get(key));
				element.sendKeys(Keys.TAB);
			}catch(Exception e){
				WebElement elem = getWebElement(By.id(key+"EditorArea"));
				elem.click();
				
				WebElement element = elem.findElement(By.className("ace_text-input"));
				element.clear();
				element.sendKeys(containerFields.get(key));
			}
		}
	}

	/**
	 * Click the save and publish button
	 * @return IContainersPage 
	 */
	public IContainersPage saveAndPublish() throws Exception{
		List<WebElement> buttons = getWebElement(By.id("editContainerButtonRow")).findElements(By.cssSelector("span[class='dijitReset dijitInline dijitButtonText']"));
		for(WebElement button : buttons){
			if(button.getText().equals(getLocalizedString("save-and-publish"))){
				button.click();
				break;
			}
		}
		return SeleniumPageManager.getBackEndPageManager().getPageObject(IContainersPage.class);
	}

	/**
	 * Click the save button
	 * @return IContainersPage 
	 */
	public IContainersPage save() throws Exception{
		List<WebElement> buttons = getWebElement(By.id("editContainerButtonRow")).findElements(By.cssSelector("span[class='dijitReset dijitInline dijitButtonText']"));
		for(WebElement button : buttons){
			if(button.getText().equals(getLocalizedString("Save"))){
				button.click();
				break;
			}
		}
		return SeleniumPageManager.getBackEndPageManager().getPageObject(IContainersPage.class);
	}

	/**
	 * Click the cancel button
	 * @return IContainersPage 
	 */
	public IContainersPage cancel() throws Exception{
		List<WebElement> buttons = getWebElement(By.id("editContainerButtonRow")).findElements(By.cssSelector("span[class='dijitReset dijitInline dijitButtonText']"));
		for(WebElement button : buttons){
			if(button.getText().equals(getLocalizedString("Cancel"))){
				button.click();
				break;
			}
		}
		return SeleniumPageManager.getBackEndPageManager().getPageObject(IContainersPage.class);
	}
}
