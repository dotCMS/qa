package com.dotcms.qa.selenium.pages.backend.common;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.dotcms.qa.selenium.pages.common.BasePage;
import com.dotcms.qa.selenium.pages.backend.IFolderAddOrEditPage;

public class FolderAddOrEditPage extends BasePage implements IFolderAddOrEditPage {

	private WebElement friendlyNameField;
	private WebElement titleField;
	
	public FolderAddOrEditPage(WebDriver driver) {
		super(driver);
	}

	public void setTitle(String title) {
		friendlyNameField.clear();
		friendlyNameField.sendKeys(title);
		friendlyNameField.sendKeys(Keys.TAB);
	}

	public void setName(String name) {
		titleField.clear();
		titleField.sendKeys(name);
		titleField.sendKeys(Keys.TAB);
	}

	public void setSortOrder(int sortOrder) {
		WebElement field = getWebElement(By.name("sortOrder"));
		field.sendKeys(String.valueOf(sortOrder));
	}

	public void setShowOnMenu(boolean showOnMenu) {
		WebElement field = getWebElement(By.name("showOnMenu"));
		if(!field.isSelected() && showOnMenu){
			field.click();
		}else{
			if(field.isSelected() && !showOnMenu){
				field.click();
			}
		}
	}

	public void setAllowedFileExtensions(String allowedFileExtensions) {
		WebElement field = getWebElement(By.name("filesMasks"));
		field.sendKeys(String.valueOf(allowedFileExtensions));
	}

	public void setDefaultFileAssetType(String fileAssetType) {
		WebElement field = getWebElement(By.id("defaultFileType"));
		field.clear();
		field.sendKeys(String.valueOf(fileAssetType));
		getWebElement(By.id("defaultFileType")).click();
	}

	public void save() {
		List<WebElement> buttons = getWebElement(By.id("editFolderButtonRow")).findElements(By.cssSelector("span[class='dijitReset dijitInline dijitButtonText']"));
		for(WebElement button : buttons){
			if(button.getText().trim().equals(getLocalizedString("save"))){
				button.click();
				break;
			}
		}
	}

	public void cancel() {
		List<WebElement> buttons = getWebElement(By.id("editFolderButtonRow")).findElements(By.cssSelector("span[class='dijitReset dijitInline dijitButtonText']"));
		for(WebElement button : buttons){
			if(button.getText().trim().equals(getLocalizedString("cancel"))){
				button.click();
				break;
			}
		}
	}
	
	/**
	 * Get the Folder Title
	 * @return String
	 * @throws Exception
	 */
	public String getFolderTitle() throws Exception{
		return friendlyNameField.getAttribute("value");
	}
	/**
	 * Get the Folder name
	 * @return String
	 * @throws Exception
	 */
	public String getFolderName() throws Exception{
		return titleField.getAttribute("value");
	}
	/**
	 * Get the Folder sort order
	 * @return int
	 * @throws Exception
	 */
	public int getSortOrder() throws Exception{
		WebElement field = getWebElement(By.name("sortOrder"));
		return Integer.parseInt(field.getAttribute("value"));
	}
	/**
	 * Indicates if the Folder is show on menu
	 * @return boolean
	 * @throws Exception
	 */
	public boolean isShowOnMenu() throws Exception{
		WebElement field = getWebElement(By.name("showOnMenu"));
		return field.isSelected();
	}
	/**
	 * Get the Folder alled file extension
	 * @return String
	 * @throws Exception
	 */
	public String getAllowedFileExtensions() throws Exception{
		WebElement field = getWebElement(By.name("filesMasks"));
		return field.getAttribute("value");
	}
	/**
	 * Get the Folder default file asset type
	 * @return String
	 * @throws Exception
	 */
	public String getDefaultFileAssetType() throws Exception{
		WebElement field = getWebElement(By.id("defaultFileType"));
		return field.getAttribute("value");
	}
}
