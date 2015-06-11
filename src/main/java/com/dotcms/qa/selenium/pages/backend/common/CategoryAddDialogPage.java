package com.dotcms.qa.selenium.pages.backend.common;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.dotcms.qa.selenium.pages.backend.ICategoryAddDialogPage;
import com.dotcms.qa.selenium.pages.common.BasePage;

/**
 * This class implements the methods defined in the ICategoryAddDialogPage interface
 * to do the Users TestRail validations
 * @author Oswaldo Gallango
 * @since 06/08/2015
 * @version 1.0
 * */
public class CategoryAddDialogPage extends BasePage implements ICategoryAddDialogPage{

	public CategoryAddDialogPage(WebDriver driver) {
		super(driver);
	}

	/**
	 * Set the new category name
	 * @param categoryName
	 * @throws Exception
	 */
	public void setCategoryName(String categoryName) throws Exception{
		WebElement name = getWebElement(By.id("addCatName"));
		name.clear();
		name.sendKeys(categoryName);
	}
	
	/**
	 * Set the category key
	 * @param key
	 * @throws Exception
	 */
	public void setKey(String key) throws Exception{
		WebElement name = getWebElement(By.id("addCatKey"));
		name.clear();
		name.sendKeys(key);
	}
	
	/**
	 * Set the category keywords
	 * @param keywords
	 * @throws Exception
	 */
	public void setKeywords(String keywords) throws Exception{
		WebElement name = getWebElement(By.id("addCatKeywords"));
		name.clear();
		name.sendKeys(keywords);
	}
	
	/**
	 * Click the save button
	 * @throws Exception
	 */
	public void save() throws Exception{
		List<WebElement> buttons = getWebElement(By.id("add_category_dialog")).findElements(By.cssSelector("span[class='dijitReset dijitInline dijitButtonText']"));
		for(WebElement button : buttons){
			if(button.getText().trim().equals(getLocalizedString("Save"))){
				button.click();
				break;
			}
		}
	}
	
	/**
	 * Click the cancel button
	 * @throws Exception
	 */
	public void cancel() throws Exception{
		List<WebElement> buttons = getWebElement(By.id("add_category_dialog")).findElements(By.cssSelector("span[class='dijitReset dijitInline dijitButtonText']"));
		for(WebElement button : buttons){
			if(button.getText().trim().equals(getLocalizedString("Cancel"))){
				button.click();
				break;
			}
		}
	}
}
