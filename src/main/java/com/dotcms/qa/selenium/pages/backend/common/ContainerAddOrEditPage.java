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
	 * @param containerFields	List of String map wit all the field of the container to be set
	 * @throws Exception
	 */
	public void setFields(Map<String,String> containerFields) throws Exception{
		if(containerFields.keySet().contains("maxContentlets")){
			WebElement element = getWebElement(By.id("maxContentlets"));
			element.click();
			element.clear();
			element.sendKeys(containerFields.get("maxContentlets"));
			getWebElement(By.id("properties")).click();
			containerFields.remove("maxContentlets");
		}
		
		if(containerFields.keySet().contains("structureSelect")){
			
			//remove default structure
			getWebElement(By.id("tabContainer_tablist")).findElement(By.cssSelector("span[class='dijitInline dijitTabCloseButton dijitTabCloseIcon']")).click();
			//search structure
			WebElement element = getWebElement(By.id("structureSelect"));
			element.clear();
			element.sendKeys(containerFields.get("structureSelect"));
			sleep(2);
			element.sendKeys(Keys.TAB);
			getAddContentTypeButton().click();
			sleep(3);
			containerFields.remove("structureSelect");
		}
		
		if(containerFields.keySet().contains("AddVariables")){
			String values=containerFields.get("AddVariables");
			for(String variable : values.split(",")){
				addVariable(variable);
			}
			containerFields.remove("AddVariables");
		}
		for(String key : containerFields.keySet()){
			try{
				WebElement element = getWebElement(By.id(key));
				element.clear();
				element.sendKeys(containerFields.get(key));
				element.sendKeys(Keys.TAB);
			}catch(Exception e){
				//if is a ace editor field
				sleep(2);
				executeJavaScript("ace.edit('"+key+"Editor').getSession().setValue('"+containerFields.get(key)+"');");
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

	/**
	 * Return the string value of the specified container field
	 * @param key	Key Name of the field
	 * @throws Exception
	 */
	public String getFieldValue(String key) throws Exception{
		String value=null;
		try{
			WebElement element = getWebElement(By.id(key));
			value = element.getText();

			if(value.equals("")){
				value = (String) executeScript("var editor = ace.edit('"+key+"Editor');return editor.getSession().getValue();");
			}
		}catch(Exception e){
			value = (String) executeScript("var editor = ace.edit('"+key+"Editor');return editor.getSession().getValue();");
		}
		return value;
	}

	/**
	 * Add a content type variable in current container 
	 * @param variableName name of the field
	 */
	private void addVariable(String variableName){
		List<WebElement> spans = getWebElement(By.className("buttonCaption")).findElements(By.cssSelector("span[class='dijitReset dijitInline dijitButtonText']"));
		for(WebElement span:spans){ 
			if(span.getText().equals(getLocalizedString("add-variable"))){
				span.click();
				break;
			}
		}
		boolean found=false;
		WebElement addVariableDialog = getWebElement(By.id("variablesDialog")); 
		List<WebElement> variables = addVariableDialog.findElement(By.cssSelector("table[class='myVarTable']")).findElements(By.tagName("tr"));
		for(WebElement row: variables){
			List<WebElement> columns = row.findElements(By.tagName("td"));
			if(columns.size() ==2){
				if(columns.get(1).getText().equals(variableName)){
					WebElement elem = columns.get(0).findElement(By.cssSelector("span[class='dijitReset dijitInline dijitButtonText']"));
					elem.click();
					sleep(2);
					found=true;
					break;
				}
			}
			if(found){
				break;
			}
		}
	}
	
	/**
	 * Get the addContent type button
	 * @return WebElement
	 */
	private WebElement getAddContentTypeButton(){
		WebElement elem = null;
		List<WebElement> spans = getWebElement(By.id("structureSelecttDiv")).findElements(By.cssSelector("span[class='dijitReset dijitInline dijitButtonText']"));
		for(WebElement span:spans){ 
			if(span.getText().equals(getLocalizedString("Add Content-Type"))){
				elem =span;
				break;
			}
		}
		return elem;
	}
}
