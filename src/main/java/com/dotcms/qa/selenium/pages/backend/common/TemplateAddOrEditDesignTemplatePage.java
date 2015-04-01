package com.dotcms.qa.selenium.pages.backend.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.dotcms.qa.selenium.pages.backend.ITemplateAddOrEditDesignTemplatePage;
import com.dotcms.qa.selenium.pages.backend.ITemplatesPage;
import com.dotcms.qa.selenium.pages.common.BasePage;
import com.dotcms.qa.selenium.util.SeleniumPageManager;

/**
 * This class implements the methods defined in the ITemplateAddOrEditDesignTemplatePage interface
 * to do the Users TestRail validations
 * @author Oswaldo Gallango
 * @since 03/30/2015
 * @version 1.0
 * 
 */
public class TemplateAddOrEditDesignTemplatePage extends BasePage implements ITemplateAddOrEditDesignTemplatePage {

	private WebElement titleField;
	private WebElement editContentletButtonRow;
	private WebElement containerBodyTemplate;
	
	public TemplateAddOrEditDesignTemplatePage(WebDriver driver) {
		super(driver);
	}

	/**
	 * Set the template name
	 * @param name	Template name
	 * @throws Exception
	 */
	public void setTemplateTitle(String name) throws Exception{
		titleField.clear();
		titleField.sendKeys(name);
	}

	/**
	 * Get the template name
	 * @return the Template name
	 * @throws Exception
	 */
	public String getTemplateTitle() throws Exception{
		return titleField.getText();
	}

	/**
	 * Set the template theme
	 * @param theme	name of the theme
	 * @throws Exception
	 */
	public void setTheme(String theme) throws Exception{
		getWebElement(By.id("themeDiv")).findElement(By.cssSelector("input[class='dijitReset dijitInputField dijitArrowButtonInner']")).click();
		List<WebElement> expands = getWebElement(By.id("themeDiv-tree-treeNode-root")).findElements(By.cssSelector("img[class='dijitTreeExpando dijitTreeExpandoClosed']"));
		for(WebElement expand : expands){
			expand.click();
		}
		sleep(2);
		List<WebElement> themes = getWebElement(By.id("themeDiv-hostFoldersTreeWrapper")).findElements(By.cssSelector("span[class='dijitTreeLabel']"));
		for(WebElement tm : themes){
			if(tm.getText().trim().equals(theme)){
				tm.click();
				break;
			}
		}
	}

	/**
	 * Get the template theme
	 * @return name of the theme
	 * @throws Exception
	 */
	public String getTheme() throws Exception{
		WebElement theme = getWebElement(By.id("editContentletButtonRow")).findElement(By.id("themeDiv-hostFolderSelect")); 
		return theme.getText();
	}

	/**
	 * Add a container to a design template
	 * @param containerName Name of the container
	 * @throws Exception
	 */
	public void addContainer(String containerName) throws Exception{
		getWebElement(By.cssSelector("div[class='addContainerSpan']")).findElement(By.tagName("a")).click();
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
		List<WebElement> buttons = getWebElement(By.id("contentletActionsHanger")).findElements(By.tagName("a"));
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
		List<WebElement> buttons = getWebElement(By.id("contentletActionsHanger")).findElements(By.tagName("a"));
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
		List<WebElement> buttons = getWebElement(By.id("contentletActionsHanger")).findElements(By.tagName("a"));
		for(WebElement button : buttons){
			if(button.getText().trim().equals(getLocalizedString("Cancel"))){
				button.click();
				break;
			}
		}
		return SeleniumPageManager.getBackEndPageManager().getPageObject(ITemplatesPage.class);
	}
	
	/**
	 * Get the list of containers associated to the template
	 * @return List<Map<String,String>>
	 * @throws Exception
	 */
	public List<Map<String,String>> getTemplateContainers() throws Exception{
		List<Map<String,String>> results = new ArrayList<Map<String,String>>();
		List<WebElement> spans = getWebElements(By.cssSelector("span[class='titleContainerSpan']"));
		for(WebElement span: spans){
			String id=span.getAttribute("title").replace("container_", "");
			String name= span.findElement(By.tagName("h2")).getText().trim();
			Map<String,String> values = new HashMap<String, String>();
			values.put("id", id);
			values.put("name", name);
			results.add(values);
		}
		return results;
	}
}
