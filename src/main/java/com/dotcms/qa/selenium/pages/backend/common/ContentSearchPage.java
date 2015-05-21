package com.dotcms.qa.selenium.pages.backend.common;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.dotcms.qa.selenium.pages.backend.*;
import com.dotcms.qa.selenium.pages.common.BasePage;
import com.dotcms.qa.selenium.util.SeleniumPageManager;
import com.dotcms.qa.util.WebKeys;

public class ContentSearchPage extends BasePage implements IContentSearchPage {

	private WebElement structure_inode;
	private WebElement dijit_form_ComboButton_0_label;
	private WebElement dijit_form_ComboButton_0_arrow;
	private WebElement dijit_MenuItem_3_text;

	public ContentSearchPage(WebDriver driver) {
		super(driver);
	}

	public IContentAddOrEdit_ContentPage getAddContentPage(String structureName) throws Exception {
		structure_inode.clear();
		structure_inode.sendKeys(structureName);
		sleep(3);
		structure_inode.sendKeys(Keys.TAB);
		dijit_form_ComboButton_0_label.click();
		return SeleniumPageManager.getBackEndPageManager().getPageObject(IContentAddOrEdit_ContentPage.class);
	}

	/**
	 * Click the add new content button
	 * @param Structure Structure Name
	 * @return IContentAddOrEdit_ContentPage
	 * @throws Exception
	 */
	public IContentAddOrEdit_ContentPage addContent(String structure) throws Exception{
		boolean found = false;
		sleep(2);
		WebElement buttonDiv= getWebElement(By.cssSelector("table[id='dijit_form_ComboButton_0']")).findElement(By.cssSelector("td[id='dijit_form_ComboButton_0_arrow']"));
		buttonDiv.click();
		sleep(2);
		List<WebElement> options = getWebElement(By.cssSelector("div[id='dijit_form_ComboButton_0_dropdown']")).findElements(By.cssSelector("td[class='dijitReset dijitMenuItemLabel']"));
		for(WebElement elem : options){
			if(elem.getText().trim().equals(getLocalizedString("Add-New-Content"))){
				elem.click();
				sleep(2);
				try{
					List<WebElement> structures = getWebElement(By.id("selectStructureDiv")).findElements(By.tagName("a"));
					for(WebElement st : structures){
						if(st.getText().trim().contains(structure)){
							st.click();
							sleep(2);
							found=true;
							break;
						}
					}
				}catch(Exception e){
					//here the structure is already selected in the search box
					found=true;
				}
				break;
			}
		}
		if(!found){
			throw new Exception("unable to find Add New Content button");
		}
		return SeleniumPageManager.getBackEndPageManager().getPageObject(IContentAddOrEdit_ContentPage.class);
	}

	/**
	 * Edit a content
	 * @param contentName Contentlet name
	 * @param structure Contentlet structure
	 * @return IContentAddOrEdit_ContentPage
	 * @throws Exception
	 */
	public IContentAddOrEdit_ContentPage editContent(String contentName, String structure) throws Exception{
		WebElement content = findContentRow(contentName, structure);
		List<WebElement> columns = content.findElements(By.tagName("td"));
		columns.get(2).findElement(By.tagName("a")).click(); 
		return SeleniumPageManager.getBackEndPageManager().getPageObject(IContentAddOrEdit_ContentPage.class);
	}

	/**
	 * Push the specified contentlet
	 * @param contentName Contentlet name
	 * @param structure Contentlet structure
	 * @throws Exception
	 */
	public void pushContent(String contentName, String structure) throws Exception{
		WebElement content = findContentRow(contentName, structure);
		List<WebElement> columns = content.findElements(By.tagName("td"));
		selectRightClickPopupMenuOption(columns.get(2),getLocalizedString("Remote-Publish"));
		sleep(2);
		IPushPublishDialogPage pushingDialog = SeleniumPageManager.getBackEndPageManager().getPageObject(IPushPublishDialogPage.class);
		pushingDialog.push(WebKeys.PUSH_TO_ADD, null, null, null, null, false);
	}

	/**
	 * Push the specified contentlet
	 * @param contentName Contentlet title
	 * @param structure   Structure name
	 * @param pushType   Type of push to apply (PUSH_TO_REMOVE,PUSH_TO_ADD,PUSH_AND_REMOVE)
	 * @param pushDate   Date with format M/d/yyyy(optional)
	 * @param pushTime   Time format H:mm a (optional)
	 * @param expireDate Date with format M/d/yyyy(optional)
	 * @param expireTime Time format H:mm a (optional)
	 * @param force      Force to publish (Only valid for push and push a remove)
	 * @throws Exception
	 */
	public void pushContent(String contentName, String structure,String pushType, String pushDate, String pushTime, String expireDate, String expireTime, boolean force) throws Exception{
		WebElement content = findContentRow(contentName, structure);
		List<WebElement> columns = content.findElements(By.tagName("td"));
		selectRightClickPopupMenuOption(columns.get(2),getLocalizedString("Remote-Publish"));
		sleep(2);
		IPushPublishDialogPage pushingDialog = SeleniumPageManager.getBackEndPageManager().getPageObject(IPushPublishDialogPage.class);
		pushingDialog.push(pushType, pushDate, pushTime, expireDate, expireTime, force);
	}

	/**
	 * Return the content row
	 * @param contentName Name of the content
	 * @param contentName Contentlet name
	 * @return WebElement
	 */
	private WebElement findContentRow(String contentName, String structure){
		return findContentRow(contentName, structure, false);
	}
	/**
	 * Return the content row
	 * @param contentName Name of the content
	 * @param structure Contentlet structure name
	 * @param showArchive Search for archive content
	 * @return WebElement
	 */
	private WebElement findContentRow(String contentName, String structure, boolean showArchive){
		WebElement content = null;
		WebElement searchfield = getWebElement(By.id("allFieldTB"));
		searchfield.clear();
		sleep(6);
		searchfield.sendKeys(contentName);

		if(structure != null && !structure.equals("")){
			WebElement structureFields = getWebElement(By.id("structure_inode"));
			try{
				structureFields.clear();
				sleep(2);
				structureFields.sendKeys(structure);
				sleep(1);
				getWebElement(By.id("structure_inode_popup0")).click();
			}catch(Exception e){
				structureFields.clear();
				getWebElement(By.id("widget_structure_inode")).findElement(By.cssSelector("div[class='dijitReset dijitRight dijitButtonNode dijitArrowButton dijitDownArrowButton dijitArrowButtonContainer']")).click();
				getWebElement(By.id("structure_inode_popup0")).click();
			}
		}else{
			getWebElement(By.id("widget_structure_inode")).findElement(By.cssSelector("div[class='dijitReset dijitRight dijitButtonNode dijitArrowButton dijitDownArrowButton dijitArrowButtonContainer']")).click();
			getWebElement(By.id("structure_inode_popup0")).click();
		}

		if(showArchive){
			WebElement advancedOptions = getWebElement(By.cssSelector("div[id='toggleDivText']"));
			if(advancedOptions.getText().equals(getLocalizedString("Advanced"))){
				getParent(advancedOptions).click();
				sleep(1);
			}
			WebElement showOptions = getWebElement(By.id("showingSelect"));
			showOptions.clear();
			showOptions.sendKeys(getLocalizedString("Archived"));
			sleep(1);
			getWebElement(By.id("showingSelect_popup0")).click();
		}else{
			WebElement advancedOptions = getWebElement(By.cssSelector("div[id='toggleDivText']"));
			if(!advancedOptions.getText().equals(getLocalizedString("Advanced"))){
				WebElement showOptions = getWebElement(By.id("showingSelect"));
				showOptions.clear();
				showOptions.sendKeys(getLocalizedString("All"));
				sleep(1);
				getWebElement(By.id("showingSelect_popup0")).click();

				getParent(advancedOptions).click();
				sleep(1);
			}
		}

		getWebElement(By.id("searchButton_label")).click();
		sleep(2);
		List<WebElement> results = getWebElement(By.id("results_table")).findElements(By.tagName("tr"));
		for(WebElement row : results){
			List<WebElement> columns = row.findElements(By.tagName("td"));
			if(columns.size() > 2){
				if(columns.get(2).getText().trim().equals(contentName)){
					content = row;
					break;
				}
			}
		}

		return content;
	}

	/**
	 * validate if the content exist
	 * @param contentName Contentlet name
	 * @param structure Contentlet structure
	 * @return true if exist, false if not
	 * @throws Exception
	 */
	public boolean doesContentExist(String contentName, String structure) throws Exception{
		boolean found=false;
		WebElement content = findContentRow(contentName, structure);
		if(content != null){
			found=true;
		}
		return found;
	}

	/**
	 * unpublish a content 
	 * @param contentName Contentlet name
	 * @param structure Contentlet structure
	 * @throws Exception
	 */
	public void unpublish(String contentName, String structure) throws Exception{
		WebElement content = findContentRow(contentName, structure);
		List<WebElement> columns = content.findElements(By.tagName("td"));
		columns.get(2).findElement(By.cssSelector("input[type='checkbox']")).click();
		getWebElementPresent(By.id("unPublishButton_label")).click();		
	}

	/**
	 * archive a content 
	 * @param contentName Contentlet name
	 * @param structure Contentlet structure
	 * @throws Exception
	 */
	public void archive(String contentName, String structure) throws Exception{
		WebElement content = findContentRow(contentName, structure);
		List<WebElement> columns = content.findElements(By.tagName("td"));
		columns.get(2).findElement(By.cssSelector("input[type='checkbox']")).click();
		getWebElementPresent(By.id("archiveButton_label")).click();
	}

	/**
	 * delete a content 
	 * @param contentName Contentlet name
	 * @param structure Contentlet structure
	 * @throws Exception
	 */
	public void delete(String contentName, String structure) throws Exception{
		WebElement content = findContentRow(contentName, structure,true);
		List<WebElement> columns = content.findElements(By.tagName("td"));
		columns.get(2).findElement(By.cssSelector("input[type='checkbox']")).click();
		getWebElementPresent(By.id("deleteButton_label")).click();
		switchToAlert().accept();
	}

	/**
	 * publish a content 
	 * @param contentName Contentlet name
	 * @param structure Contentlet structure
	 * @throws Exception
	 */
	public void publish(String contentName, String structure) throws Exception{
		WebElement content = findContentRow(contentName, structure);
		List<WebElement> columns = content.findElements(By.tagName("td"));
		columns.get(2).findElement(By.cssSelector("input[type='checkbox']")).click();
		getWebElementPresent(By.id("publishButton_label")).click();

	}

	/**
	 * unarchive a content 
	 * @param contentName Contentlet name
	 * @param structure Contentlet structure
	 * @throws Exception
	 */
	public void unArchive(String contentName, String structure) throws Exception{
		WebElement content = findContentRow(contentName, structure,true);
		List<WebElement> columns = content.findElements(By.tagName("td"));
		columns.get(2).findElement(By.cssSelector("input[type='checkbox']")).click();
		getWebElementPresent(By.id("unArchiveButton_label")).click();
	}

	/**
	 * Validate is the content is archived 
	 * @param contentName Contentlet name
	 * @param structure Contentlet structure
	 * @return true if exist, false if not
	 * @throws Exception
	 */
	public boolean isArchive(String contentName, String structure) throws Exception{
		boolean isArchive = false;
		WebElement content = findContentRow(contentName, structure,true);
		if(content != null){
			try{
				WebElement status = content.findElements(By.tagName("td")).get(0).findElement(By.cssSelector("span[class='archivedIcon']"));
				if(status != null){
					isArchive=true;
				}
			}catch(Exception e){}
		}
		return isArchive;
	}

	/**
	 * Validate is the content is unpublished 
	 * @param contentName Contentlet name
	 * @param structure Contentlet structure
	 * @return true if exist, false if not
	 * @throws Exception
	 */
	public boolean isUnpublish(String contentName, String structure) throws Exception{
		boolean isUnpublish = false;
		WebElement content = findContentRow(contentName, structure);
		if(content != null){
			try {
				WebElement status = content.findElements(By.tagName("td")).get(0).findElement(By.cssSelector("span[class='workingIcon']"));
				if(status != null){
					isUnpublish=true;
				}
			}catch(Exception e){}
		}
		return isUnpublish;
	}

	/**
	 * Validate is the content is published 
	 * @param contentName Contentlet name
	 * @param structure Contentlet structure
	 * @return true if exist, false if not
	 * @throws Exception
	 */
	public boolean isPublish(String contentName, String structure) throws Exception{
		boolean isPublish = false;
		WebElement content = findContentRow(contentName, structure);
		if(content != null){
			try{
				WebElement status = content.findElements(By.tagName("td")).get(0).findElement(By.cssSelector("span[class='liveIcon']"));
				if(status != null){
					isPublish=true;
				}
			}catch(Exception e){}
		}
		return isPublish;
	}

	/**
	 * 
	 * @param contentTitles Title of the content
	 * @param structure     Contentlet structure name
	 * @param searchFilter  Text to filter the set of contentlet to search
	 * @param showArchive   Search for archive content
	 * @return List<WebElement>
	 * @throws Exception
	 */
	private List<WebElement> findContentListRows(List<String> contentTitles, String structure, String searchFilter,boolean showArchive) throws Exception{
		List<WebElement> contents = new ArrayList<WebElement>();
		WebElement searchfield = getWebElement(By.id("allFieldTB"));
		searchfield.clear();
		sleep(6);
		searchfield.sendKeys(searchFilter);

		if(structure != null && !structure.equals("")){
			WebElement structureFields = getWebElement(By.id("structure_inode"));
			structureFields.clear();
			sleep(2);
			structureFields.sendKeys(structure);
			sleep(1);
			getWebElement(By.id("structure_inode_popup0")).click();
		}else{
			getWebElement(By.id("widget_structure_inode")).findElement(By.cssSelector("div[class='dijitReset dijitRight dijitButtonNode dijitArrowButton dijitDownArrowButton dijitArrowButtonContainer']")).click();
			getWebElement(By.id("structure_inode_popup0")).click();
		}

		if(showArchive){
			WebElement advancedOptions = getWebElement(By.cssSelector("div[id='toggleDivText']"));
			if(!advancedOptions.getText().equals(getLocalizedString("Advanced"))){
				getParent(advancedOptions).click();
				sleep(1);
			}
			WebElement showOptions = getWebElement(By.id("showingSelect"));
			showOptions.clear();
			showOptions.sendKeys(getLocalizedString("Archived"));
			sleep(1);
			getWebElement(By.id("showingSelect_popup0")).click();
		}

		getWebElement(By.id("searchButton_label")).click();
		sleep(2);
		List<WebElement> results = getWebElement(By.id("results_table")).findElements(By.tagName("tr"));
		for(WebElement row : results){
			List<WebElement> columns = row.findElements(By.tagName("td"));
			if(columns.size() > 2){
				for(String contentTitle : contentTitles){
					if(columns.get(2).getText().trim().equals(contentTitle)){
						contents.add(row);
					}
				}
			}
		}

		return contents;
	}

	/**
	 * push a list of contentlets
	 * @param listOfContent  List of contentlet titles
	 * @param structure      Content structure name
	 * @param contentSearchFilterKey String to filter the search to get the contentlets
	 * @throws Exception
	 */
	public void pushContentList(List<String>listOfContent,String structure,String contentSearchFilterKey) throws Exception{
		List<WebElement> contents = findContentListRows(listOfContent, structure, contentSearchFilterKey,false);
		for(WebElement content : contents){
			List<WebElement> columns = content.findElements(By.tagName("td"));
			columns.get(2).findElement(By.cssSelector("input[type='checkbox']")).click();
			sleep(2);
		}
		getWebElement(By.id("pushPublishButton_label")).click();
		sleep(2);
		IPushPublishDialogPage pushingDialog = SeleniumPageManager.getBackEndPageManager().getPageObject(IPushPublishDialogPage.class);
		pushingDialog.push(WebKeys.PUSH_TO_ADD, null, null, null, null, false);

	}

	/**
	 * unlock a content 
	 * @param contentName Contentlet name
	 * @param structure Contentlet structure
	 * @throws Exception
	 */
	public void unLock(String contentName, String structure) throws Exception{
		WebElement content = findContentRow(contentName, structure);
		List<WebElement> columns = content.findElements(By.tagName("td"));
		columns.get(2).findElement(By.cssSelector("input[type='checkbox']")).click();
		getWebElementPresent(By.id("unlockButton_label")).click();
	}

	/**
	 * Validate is the content is locked 
	 * @param contentName Contentlet name
	 * @param structure Contentlet structure
	 * @return true if exist, false if not
	 * @throws Exception
	 */
	public boolean islock(String contentName, String structure) throws Exception{
		boolean isLock = false;
		WebElement content = findContentRow(contentName, structure);
		if(content != null){
			try{
				WebElement status = content.findElements(By.tagName("td")).get(0).findElement(By.cssSelector("span[class='lockIcon']"));
				if(status != null){
					isLock=true;
				}
			}catch(Exception e){}
		}
		return isLock;
	}

	/**
	 * Add to the bundle all the structure contentlets 
	 * @param contentStructureName Structure Name
	 * @param bundleName Bundle Name
	 * @throws Exception
	 */
	public void addToBundleAllStructureContent(String contentStructureName, String bundleName) throws Exception{
		//search contentlets
		if(contentStructureName != null && !contentStructureName.equals("")){
			WebElement structureFields = getWebElement(By.id("structure_inode"));
			try{
				structureFields.clear();
				sleep(2);
				structureFields.sendKeys(contentStructureName);
				sleep(1);
				getWebElement(By.id("structure_inode_popup0")).click();
			}catch(Exception e){
				structureFields.clear();
				getWebElement(By.id("widget_structure_inode")).findElement(By.cssSelector("div[class='dijitReset dijitRight dijitButtonNode dijitArrowButton dijitDownArrowButton dijitArrowButtonContainer']")).click();
				getWebElement(By.id("structure_inode_popup0")).click();
			}
		}else{
			getWebElement(By.id("widget_structure_inode")).findElement(By.cssSelector("div[class='dijitReset dijitRight dijitButtonNode dijitArrowButton dijitDownArrowButton dijitArrowButtonContainer']")).click();
			getWebElement(By.id("structure_inode_popup0")).click();
		}

		getWebElement(By.id("searchButton_label")).click();
		sleep(2);
		List<WebElement> results = getWebElement(By.id("results_table")).findElements(By.tagName("th"));
		results.get(2).findElement(By.id("checkAll")).click();
		sleep(2);
		try{
			getWebElement(By.id("tablemessage")).findElement(By.tagName("a")).click();
		}catch(Exception e){
			//less than 40 results
		}
		getWebElement(By.id("addToBundleButton_label")).click();

		getWebElement(By.id("addToBundleDia")).findElement(By.id("bundleSelect")).clear();
		getWebElement(By.id("addToBundleDia")).findElement(By.id("bundleSelect")).sendKeys(bundleName);
		getWebElement(By.id("addToBundleDia")).findElement(By.id("addToBundleSaveButton_label")).click();
	}

	/**
	 * Click the import content button
	 * @return IContentImport_ContentPage
	 * @throws Exception
	 */
	public IContentImport_ContentPage importContent() throws Exception{
		dijit_form_ComboButton_0_arrow.click();
		dijit_MenuItem_3_text.click();
		return SeleniumPageManager.getBackEndPageManager().getPageObject(IContentImport_ContentPage.class);
	}

	/**
	 * Change the language in the search
	 * @param language Language
	 */
	public void changeLanguage(String language) throws Exception{
		WebElement advancedOptions = getWebElement(By.cssSelector("div[id='toggleDivText']"));
		if(advancedOptions.getText().equals(getLocalizedString("Advanced"))){
			getParent(advancedOptions).click();
			sleep(1);
		}
		WebElement languageField = getWebElement(By.id("language_id"));
		languageField.clear();
		languageField.sendKeys(language);
		getWebElement(By.id("language_id_popup0")).click();
	}
}
