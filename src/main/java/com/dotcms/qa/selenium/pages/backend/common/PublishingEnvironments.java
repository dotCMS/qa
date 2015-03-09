package com.dotcms.qa.selenium.pages.backend.common;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.dotcms.qa.selenium.pages.backend.IPublishingEnvironments;
import com.dotcms.qa.selenium.pages.common.BasePage;

/**
 * This class implements the methods defined in the IPublishingEnvironments interface
 * to do the pushpublishing TestRail validations
 * @author Oswaldo Gallango
 * @since 03/06/2015
 * @version 1.0
 * 
 */
public class PublishingEnvironments extends BasePage implements IPublishingEnvironments {

	public PublishingEnvironments(WebDriver driver) {
		super(driver);
	}

	/**
	 * Generates a new Send to environment
	 * @param environmentName String name of the new environment
	 * @param whocanUse String list with the User or roles who can use the push publish
	 * @param pushMode String indicating if the push option is pushToOne or pushToAll
	 * @throws Exception
	 */
	public void createEnvironment(String environmentName, List<String> whocanUse, String pushMode) throws Exception{

		//Clien Add Environment button
		WebElement addEnvironment = getAddEnvironmentButton();
		addEnvironment.click();

		//Fill the Add environment Dialog options
		WebElement AddEnvironmentDialog = getWebElement(By.id("addEnvironment"));
		AddEnvironmentDialog.findElement(By.id("environmentName")).sendKeys(environmentName);
		for(String user : whocanUse){
			WebElement select = AddEnvironmentDialog.findElement(By.id("whoCanUseSelect"));
			select.sendKeys(user);
			sleep(1);
			getWebElement(By.id("whoCanUseSelect_popup")).findElement(By.id("whoCanUseSelect_popup0")).findElement(By.tagName("span")).click();			
		}
		AddEnvironmentDialog.findElement(By.id(pushMode)).click();
		AddEnvironmentDialog.findElement(By.id("save_label")).click();
		
		//Add server
		
		
	}
	
	/**
	 * Return the environment row
	 * @param environmentName Name of the environment
	 * @return WebElement
	 */
	public WebElement findEnvironment(String environmentName) throws Exception{
		List<WebElement> tables = getWebElement(By.id("remotePublishingTabContent")).findElements(By.tagName("table"));
		boolean found = false;
		WebElement environmentRow=null;
		for(WebElement table : tables){
			List<WebElement> rows = table.findElements(By.tagName("tr"));
			for(WebElement row : rows){
				List<WebElement> columns = row.findElements(By.tagName("td"));
				if(columns.get(1).getText().equals(environmentName)){
					environmentRow=row;
					found=true;
					break;
				}
			}
			if(found){
				break;
			}
		}
		return environmentRow;
	}

	/**
	 * Get the Add Envrironment button
	 * @return WebElement
	 * @throws Exception
	 */
	private WebElement getAddEnvironmentButton() throws Exception{
		WebElement button = null;
		List<WebElement> spans = getWebElement(By.id("remotePublishingTabContent")).findElements(By.cssSelector("span[class='dijitReset dijitInline dijitButtonText']"));
		for(WebElement span : spans){
			if(span.getText().equals(getLocalizedString("publisher_Add_Environment"))){
				button = span;
				break;
			}
		}
		return button;
	}
}
