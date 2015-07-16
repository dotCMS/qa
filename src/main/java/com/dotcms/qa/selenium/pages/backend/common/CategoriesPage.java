package com.dotcms.qa.selenium.pages.backend.common;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.dotcms.qa.selenium.pages.backend.ICategoriesPage;
import com.dotcms.qa.selenium.pages.backend.ICategoryAddDialogPage;
import com.dotcms.qa.selenium.pages.backend.IPushPublishDialogPage;
import com.dotcms.qa.selenium.pages.common.BasePage;
import com.dotcms.qa.selenium.util.SeleniumPageManager;
import com.dotcms.qa.util.WebKeys;

/**
 * This class implements the methods defined in the ICategoriesPage interface
 * to do the Users TestRail validations
 * @author Oswaldo Gallango
 * @since 06/08/2015
 * @version 1.0
 * */
public class CategoriesPage extends BasePage implements ICategoriesPage {

	public CategoriesPage(WebDriver driver) {
		super(driver);
	}

	/**
	 * Add a category under the specified parent category
	 * @param parent    Parent category name
	 * @param category  New category name
	 * @param key       New category key
	 * @param keywords  New category keywords
	 * @throws Exception
	 */
	public void addCategory(String parent,String category, String key, String keywords) throws Exception{
		getWebElement(By.id("mainTabContainer_tablist_TabOne")).click();
		if(parent != null){
			WebElement parentCategory = findCategoryRow(parent);
			List<WebElement> columns = parentCategory.findElements(By.tagName("td"));
			columns.get(1).findElement(By.tagName("a")).click();
			sleep(2);
		}
		List<WebElement> buttons = getWebElement(By.id("TabOne")).findElements(By.cssSelector("span[class='dijitReset dijitInline dijitButtonText']"));
		for(WebElement button : buttons){
			if(button.getText().equals(getLocalizedString("Add"))){
				button.click();
				break;
			}
		}
		ICategoryAddDialogPage addCategoryDialog = SeleniumPageManager.getBackEndPageManager().getPageObject(ICategoryAddDialogPage.class);
		addCategoryDialog.setCategoryName(category);
		sleep();
		addCategoryDialog.setKey(key);
		sleep();
		addCategoryDialog.setKeywords(keywords);
		addCategoryDialog.save();
		//to close add categories dialog
		addCategoryDialog.cancel();
	}

	private WebElement findCategoryRow(String category) throws Exception{
		WebElement categoryRow=null;
		//filter
		WebElement filter = getWebElement(By.id("catFilter"));
		filter.clear();
		filter.sendKeys(category);
		sleep(2);
		//select showAll
		List<WebElement> links = getWebElements(By.cssSelector("span[class='dojoxGridInactiveSwitch']"));
		for(WebElement link : links){
			if(link.getText().equals(getLocalizedString("All"))){
				link.click();
				break;
			}
		}

		//search results
		List<WebElement> rows = getWebElement(By.id("catHolder")).findElements(By.cssSelector("table[class='dojoxGridRowTable']"));
		for(WebElement row : rows){
			List<WebElement> columns = row.findElements(By.tagName("td"));
			if(columns.size() > 2){
				if(columns.get(1).getText().trim().equals(category)){
					categoryRow=row;
					break;
				}
			}
		}
		return categoryRow;		
	}

	/**
	 * Delete a category under the specified parent category
	 * @param parent    Parent category name
	 * @param category  Category name or key of the category to be deleted
	 * @throws Exception
	 */
	public void deleteCategory(String parent,String category) throws Exception{
		getWebElement(By.id("mainTabContainer_tablist_TabOne")).click();
		if(parent != null){
			WebElement parentCategory = findCategoryRow(parent);
			List<WebElement> columns = parentCategory.findElements(By.tagName("td"));
			columns.get(0).findElement(By.tagName("a")).click();
			sleep(2);
		}
		WebElement currentCategory = findCategoryRow(category);
		List<WebElement> columns = currentCategory.findElements(By.tagName("td"));
		columns.get(0).click();

		List<WebElement> buttons = getWebElement(By.id("TabOne")).findElements(By.cssSelector("span[class='dijitReset dijitInline dijitButtonText']"));
		for(WebElement button : buttons){
			if(button.getText().equals(getLocalizedString("delete"))){
				button.click();
				sleep(2);
				List<WebElement> buttons2 = getWebElements(By.cssSelector("span[class='dijitReset dijitInline dijitButtonText']"));
				for(WebElement bt : buttons2){
					if(bt.getText().trim().equals(getLocalizedString("Yes"))){
						bt.click();
						break;
					}

				}
				break;
			}
		}		
	}

	/**
	 * Select category under the specified parent category
	 * @param parent    Parent category name
	 * @param category  Category name or key of the category to be deleted
	 * @throws Exception
	 */
	public void selectCategory(String parent,String category) throws Exception{
		getWebElement(By.id("mainTabContainer_tablist_TabOne")).click();
		if(parent != null){
			WebElement parentCategory = findCategoryRow(parent);
			List<WebElement> columns = parentCategory.findElements(By.tagName("td"));
			columns.get(1).findElement(By.tagName("a")).click();
			sleep(2);
		}

		WebElement parentCategory = findCategoryRow(category);
		List<WebElement> columns = parentCategory.findElements(By.tagName("td"));
		columns.get(0).click();
	}

	/**
	 * Select category under the specified parent category, and display children categories
	 * @param parent    Parent category name
	 * @param category  Category name or key of the category to be deleted
	 * @throws Exception
	 */
	public void showCategoryChildrens(String parent,String category) throws Exception{
		getWebElement(By.id("mainTabContainer_tablist_TabOne")).click();
		if(parent != null){
			WebElement parentCategory = findCategoryRow(parent);
			List<WebElement> columns = parentCategory.findElements(By.tagName("td"));
			columns.get(1).findElement(By.tagName("a")).click();
			sleep(2);
		}

		WebElement parentCategory = findCategoryRow(category);
		List<WebElement> columns = parentCategory.findElements(By.tagName("td"));
		columns.get(1).findElement(By.tagName("a")).click();
	}

	/**
	 * Return One level to parent category
	 * @throws Exception
	 */
	public void returnToParentCategory() throws Exception{
		List<WebElement> values = getWebElement(By.id("ulNav")).findElements(By.tagName("li"));
		values.get(values.size()-2).click();
	}
	
	/**
	 * Push the selected category including it children
	 * @param categoryName Category to push
	 * @throws Exception
	 */
	public void pushCategory(String categoryName) throws Exception{
		selectCategory(null,categoryName);
		List<WebElement> buttons = getWebElement(By.id("TabOne")).findElements(By.cssSelector("span[class='dijitReset dijitInline dijitButtonText']"));
		for(WebElement button : buttons){
			if(button.getText().equals(getLocalizedString("Remote-Publish"))){
				button.click();
				sleep(2);
				IPushPublishDialogPage pushingDialog = SeleniumPageManager.getBackEndPageManager().getPageObject(IPushPublishDialogPage.class);
				pushingDialog.push(WebKeys.PUSH_TO_ADD, null, null, null, null, false);
				break;
			}
		}	
	}
	
	/**
	 * Validate is a category exist in the current tier
	 * @param categoryName Category name
	 * @return true if exist false if not
	 */
	public boolean doesCategoryExist(String categoryName) throws Exception{
		boolean exist = false;
		try{
			WebElement row = findCategoryRow(categoryName);
			if(row != null){
				exist=true;
			}
		}catch(Exception e){}
		return exist;
	}
	
	
	/**
	 * Add the category to a particular bundle 
	 * @param categoryName   Name of the category
	 * @param bundleName      Name of the bundle
	 * @throws Exception
	 */
	public void addToBundle(String categoryName, String bundleName) throws Exception{
		selectCategory(null, categoryName);
		List<WebElement> buttons = getWebElement(By.id("TabOne")).findElements(By.cssSelector("span[class='dijitReset dijitInline dijitButtonText']"));
		for(WebElement button : buttons){
			if(button.getText().equals(getLocalizedString("Add-To-Bundle"))){
				button.click();
				sleep(2);
				getWebElement(By.id("addToBundleDia")).findElement(By.id("bundleSelect")).clear();
				getWebElement(By.id("addToBundleDia")).findElement(By.id("bundleSelect")).sendKeys(bundleName);
				getWebElement(By.id("addToBundleDia")).findElement(By.id("addToBundleSaveButton_label")).click();
				sleep(2);
				break;
			}
		}
		

	}
}
