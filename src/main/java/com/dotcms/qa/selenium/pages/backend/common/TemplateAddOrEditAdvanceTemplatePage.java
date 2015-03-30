package com.dotcms.qa.selenium.pages.backend.common;

import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.dotcms.qa.selenium.pages.backend.ITemplateAddOrEditAdvanceTemplatePage;
import com.dotcms.qa.selenium.pages.backend.ITemplatesPage;
import com.dotcms.qa.selenium.pages.common.BasePage;
import com.dotcms.qa.selenium.util.SeleniumPageManager;

/**
 * This class implements the methods defined in the ITemplateAddOrEditAdvanceTemplatePage interface
 * to do the Users TestRail validations
 * @author Oswaldo Gallango
 * @since 03/23/2015
 * @version 1.0
 * 
 */
public class TemplateAddOrEditAdvanceTemplatePage extends BasePage implements ITemplateAddOrEditAdvanceTemplatePage {

	public TemplateAddOrEditAdvanceTemplatePage(WebDriver driver) {
		super(driver);
	}
	
	/**
	 * Fill the advance template page fields
	 * @param containerFields	List of String map wit all the field of the container to be set
	 * @throws Exception
	 */
	public void setTemplateFields(Map<String,String> templateFields) throws Exception{
		
		if(templateFields.keySet().contains("AddContainers")){
			String values=templateFields.get("AddContainers");
			for(String variable : values.split(",")){
				sleep(3);
				addContainer(variable);
			}
			templateFields.remove("AddContainers");
		}
		for(String key : templateFields.keySet()){
			try{
				WebElement element = getWebElement(By.id(key));
				element.clear();
				element.sendKeys(templateFields.get(key));
				element.sendKeys(Keys.TAB);
			}catch(Exception e){
				//if is a ace editor field
				executeJavaScript("ace.edit('"+key+"EditorArea').getSession().setValue('"+templateFields.get(key)+"');");
			}
		}
	}
	
	/**
	 * Add a content type variable in current container 
	 * @param containerName name of the container
	 */
	private void addContainer(String containerName){
		sleep(2);
		List<WebElement> spans = getWebElements(By.cssSelector(("span[class='dijitReset dijitInline dijitButtonText']")));
		for(WebElement span:spans){ 
			if(span.getText().equals(getLocalizedString("add-container"))){
				span.click();
				break;
			}
		}
		sleep(2);
		WebElement addVariableDialog = getWebElement(By.id("containerSelector")); 
		WebElement searchBox = addVariableDialog.findElement(By.cssSelector("input[id='containersList']"));
		searchBox.clear();
		searchBox.sendKeys(containerName);
		sleep(2);
		getWebElement(By.id("containersList_popup0")).click();
		sleep(2);
		List<WebElement> buttons = addVariableDialog.findElements(By.cssSelector("span[class='dijitReset dijitInline dijitButtonText']"));
		for(WebElement button : buttons){
			if(button.getText().trim().equals(getLocalizedString("Add"))){
				button.click();
				sleep(2);
				break;
			}
		}
		
	}
	
	/**
	 * Click the save and publish button
	 * @return ITemplatesPage
	 * @throws Exception
	 */
	public ITemplatesPage saveAndPublish() throws Exception{
		List<WebElement> buttons = getWebElement(By.id("editTemplateButtonRow")).findElements(By.cssSelector("span[class='dijitReset dijitInline dijitButtonText']"));
		for(WebElement button : buttons){
			if(button.getText().trim().equals(getLocalizedString("save-and-publish"))){
				button.click();
				sleep(2);
				break;
			}
		}
		return SeleniumPageManager.getBackEndPageManager().getPageObject(ITemplatesPage.class);
	}
	
	/**
	 * Click the save button
	 * @return ITemplatesPage
	 * @throws Exception
	 */
	public ITemplatesPage save() throws Exception{
		List<WebElement> buttons = getWebElement(By.id("editTemplateButtonRow")).findElements(By.cssSelector("span[class='dijitReset dijitInline dijitButtonText']"));
		for(WebElement button : buttons){
			if(button.getText().trim().equals(getLocalizedString("Save"))){
				button.click();
				break;
			}
		}
		return SeleniumPageManager.getBackEndPageManager().getPageObject(ITemplatesPage.class);
	}
	
	/**
	 * Click the cancel button
	 * @return ITemplatesPage
	 * @throws Exception
	 */
	public ITemplatesPage cancel() throws Exception{
		List<WebElement> buttons = getWebElement(By.id("editTemplateButtonRow")).findElements(By.cssSelector("span[class='dijitReset dijitInline dijitButtonText']"));
		for(WebElement button : buttons){
			if(button.getText().trim().equals(getLocalizedString("Cancel"))){
				button.click();
				break;
			}
		}
		return SeleniumPageManager.getBackEndPageManager().getPageObject(ITemplatesPage.class);
	}
	
}
