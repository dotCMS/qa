package com.dotcms.qa.selenium.pages.backend;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.dotcms.qa.selenium.pages.IBasePage;

/**
 * Categories Page Interface
 * @author Oswaldo Gallango
 * @since 06/08/2015
 * @version 1.0
 *
 */
public interface ICategoriesPage extends IBasePage{
	
	/**
	 * Add a category under the specified parent category
	 * @param parent    Parent category name
	 * @param category  New category name
	 * @param key       New category key
	 * @param keywords  New category keywords
	 * @throws Exception
	 */
	public void addCategory(String parent,String category, String key, String keywords) throws Exception;
	
	/**
	 * Delete a category under the specified parent category
	 * @param parent    Parent category name
	 * @param category  Category name or key of the category to be deleted
	 * @throws Exception
	 */
	public void deleteCategory(String parent,String category) throws Exception;
	
	/**
	 * Select category under the specified parent category
	 * @param parent    Parent category name
	 * @param category  Category name or key of the category to be deleted
	 * @throws Exception
	 */
	public void selectCategory(String parent,String category) throws Exception;
	
	/**
	 * Select category under the specified parent category, and display children categories
	 * @param parent    Parent category name
	 * @param category  Category name or key of the category to be deleted
	 * @throws Exception
	 */
	public void showCategoryChildrens(String parent,String category) throws Exception;
	
	/**
	 * Return One level to parent category
	 * @throws Exception
	 */
	public void returnToParentCategory() throws Exception;
	
	/**
	 * Push the selected category including it children
	 * @param categoryName Category to push
	 * @throws Exception
	 */
	public void pushCategory(String categoryName) throws Exception;
	
	/**
	 * Validate is a category exist in the current tier
	 * @param categoryName Category name
	 * @return true if exist false if not
	 */
	public boolean doesCategoryExist(String categoryName) throws Exception;
	
	/**
	 * Add the category to a particular bundle 
	 * @param categoryName   Name of the category
	 * @param bundleName      Name of the bundle
	 * @throws Exception
	 */
	public void addToBundle(String categoryName, String bundleName) throws Exception;

}
