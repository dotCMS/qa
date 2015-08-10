package com.dotcms.qa.selenium.pages.backend.common;

import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.dotcms.qa.selenium.pages.common.BasePage;
import com.dotcms.qa.selenium.pages.backend.IFolderAddOrEditPage;
import com.dotcms.qa.util.Evaluator;

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
		field.clear();
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
		field.clear();
		field.sendKeys(String.valueOf(allowedFileExtensions));
	}

	public void setDefaultFileAssetType(String fileAssetType) {
		WebElement field = getWebElement(By.id("defaultFileType"));
		field.clear();
		field.sendKeys(String.valueOf(fileAssetType));
		getWebElement(By.id("defaultFileType_popup0")).click();
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

	/**
	 * Select the properties tab
	 * @throws Exception
	 */
	public void getPropertiesTab() throws Exception{
		getWebElement(By.cssSelector("span[id='mainTabContainer_tablist_folderPropertiesTab']")).click();
	}

	/**
	 * Select the permission tab
	 * @throws Exception
	 */
	public void getPermissionTab() throws Exception{
		getWebElement(By.cssSelector("span[id='mainTabContainer_tablist_permissionsTab']")).click();
	}

	/**
	 * Click the permission individually button
	 * @throws Exception
	 */
	public void activatePermissionIndividually() throws Exception{
		List<WebElement> elements = getWebElement(By.id("permissionIndividuallyButtonWrapper")).findElements(By.cssSelector("span[id*='dijit_form_Button_'][class='dijitReset dijitInline dijitButtonText']"));
		for(WebElement ele : elements){
			if(ele.getText().trim().equals(getLocalizedString("want-to-permission-individually"))){
				ele.click();
				sleep(3);
				break;
			}
		}
	}

	/**
	 * Add a role
	 * @param roleName role name
	 * @param subpermissions subpermissions
	 * @param view permission to view
	 * @param addChildren permission to add children
	 * @param edit permission to edit
	 * @param publish permission to publish
	 * @param editPermission permission to edit permissions
	 * @param vanityUrl permission to add vanity urls
	 * @throws Exception
	 */
	public void addRole(String roleName, List<Map<String,Object>> subpermissions, boolean view, boolean addChildren, boolean edit, boolean publish, boolean editPermission, boolean vanityUrl) throws Exception{
		try{
			WebElement roleSelector = getWebElement(By.id("permissionsRoleSelector"));
			WebElement roleInput = roleSelector.findElement(By.cssSelector("input[class='dijitReset']"));
			roleInput.clear();
			roleInput.sendKeys(roleName);
			List<WebElement> roles = getWebElement(By.cssSelector("div[id*='permissionsRoleSelector-rolesTree-treeNode-']")).findElements(By.cssSelector("span[class='dijitTreeLabel']"));
			for(WebElement role :  roles){
				if(role.getText().trim().equals(roleName)){
					role.click();
					break;
				}
			}
			List<WebElement> buttons = getWebElement(By.id("permissionsTabFt")).findElements(By.cssSelector("span[class='dijitReset dijitInline dijitButtonText']"));
			for(WebElement button :  buttons){
				if(button.getText().trim().equals(getLocalizedString("Add-Role"))){
					button.click();
					break;
				}
			}

			List<WebElement> entries = getWebElements(By.cssSelector("span[id*='permissionsAccordionPane-']"));
			for(WebElement entry: entries){
				WebElement title = entry.findElement(By.cssSelector("th[class='permissionType permissionTitle']"));
				if(title.getText().trim().equals(roleName)){
					List<WebElement> columns = entry.findElements(By.tagName("td"));
					if(view){
						columns.get(0).findElement(By.tagName("input")).click();
					}
					if(addChildren){
						columns.get(1).findElement(By.tagName("input")).click();
					}
					if(edit){
						columns.get(2).findElement(By.tagName("input")).click();
					}
					if(editPermission){
						columns.get(4).findElement(By.tagName("input")).click();
					}

					String id = entry.getAttribute("id").replace("permissionsAccordionPane-","").replace("_button_title","");
					if(!subpermissions.isEmpty()){
						List<WebElement> subper = getWebElement(By.cssSelector("div[id='permissionsAccordionPane-"+id+"']")).findElements(By.tagName("tr"));
						for(Map<String,Object> element : subpermissions){
							String property = getLocalizedString((String)element.get("name"));
							boolean viewProperty = (Boolean) element.get("view");
							boolean addChildrenProperty = (Boolean) element.get("addChildren");
							boolean editProperty = (Boolean) element.get("edit");
							boolean publishProperty = (Boolean) element.get("publish");
							boolean editPermissionProperty = (Boolean) element.get("editPermission");
							boolean vanityUrlProperty = (Boolean) element.get("vanityUrl");

							for(WebElement row : subper){
								List<WebElement> propertiesColumns = row.findElements(By.tagName("td"));
								if(propertiesColumns.size() == 6){
									String rowProperty=row.findElement(By.className("permissionType")).getText().trim();
									if(rowProperty.trim().equals(property)){
										if(viewProperty){
											propertiesColumns.get(0).findElement(By.cssSelector("input[type='checkbox']")).click();
										}
										if(addChildrenProperty){
											propertiesColumns.get(1).findElement(By.cssSelector("input[type='checkbox']")).click();
										}
										if(editProperty){
											propertiesColumns.get(2).findElement(By.cssSelector("input[type='checkbox']")).click();
										}
										if(publishProperty){
											propertiesColumns.get(3).findElement(By.cssSelector("input[type='checkbox']")).click();
										}
										if(editPermissionProperty){
											propertiesColumns.get(4).findElement(By.cssSelector("input[type='checkbox']")).click();
										}
										if(vanityUrlProperty){
											propertiesColumns.get(5).findElement(By.cssSelector("input[type='checkbox']")).click();
										}
										break;
									}
								}
							}
						}
					}

					getWebElement(By.id("permissionsActions")).findElement(By.id("applyChangesButton_label")).click();
					/*
					Evaluator eval = new Evaluator() {
						public boolean evaluate() throws Exception {  // returns true if host copy is done
							boolean wasSaved = !getWebElement(By.id("disabledZone")).isDisplayed();
							return wasSaved;
						}

					};
					//wait until 15min to check if the permission where saved
					pollForValue(eval, true, 5000, 120);
					 */
				}
			}
		}catch(Exception e){
			//role already exist
			try{
				switchToAlert().accept();
			}catch(Exception e2){
				throw new Exception("Role can't be assigned");
			}
		}
	}

	/**
	 * Click the apply permission button
	 * @throws Exception
	 */
	public void applyPermissionChanges() throws Exception{
		getWebElement(By.id("permissionsActions")).findElement(By.id("applyChangesButton_label")).click();

		Evaluator eval = new Evaluator() {
			public boolean evaluate() throws Exception {  // returns true if host copy is done
				boolean wasSaved = !getWebElement(By.id("disabledZone")).isDisplayed();
				return wasSaved;
			}

		};
		//wait until 15min to check if the permission where saved
		pollForValue(eval, true, 5000, 120);
	}
	
	/**
	 * Click the option in the Permission confirmation box
	 * @param value Yes or no
	 * @throws Exception
	 */
	public void folderPermissionAlert(String value) throws Exception{
		List<WebElement> buttons = getWebElement(By.id("applyPermissionsChangesDialog")).findElements(By.cssSelector("span[class='dijitReset dijitInline dijitButtonText']"));
		for(WebElement button : buttons){
			if(button.getText().trim().equals(getLocalizedString(value))){
				button.click();
				break;
			}
		}
	}
}
