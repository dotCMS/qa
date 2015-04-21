package com.dotcms.qa.selenium.pages.backend.common;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.dotcms.qa.selenium.pages.backend.*;
import com.dotcms.qa.selenium.pages.common.BasePage;
import com.dotcms.qa.selenium.util.SeleniumPageManager;

public class ContentSearchPage extends BasePage implements IContentSearchPage {

	private WebElement structure_inode;
	private WebElement dijit_form_ComboButton_0_label;

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
				List<WebElement> structures = getWebElement(By.id("selectStructureDiv")).findElements(By.tagName("a"));
				for(WebElement st : structures){
					if(st.getText().trim().contains(structure)){
						st.click();
						sleep(2);
						found=true;
						break;
					}
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
		columns.get(2).click();
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
		WebElement remotePublishBundleDialog = getWebElement(By.id("remotePublisherDia"));
		remotePublishBundleDialog.findElement(By.id("remotePublishSaveButton")).click();
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
	 * @param contentName Contentlet name
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
			getParent(advancedOptions).click();
			sleep(1);
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
			if(columns.size() > 1){
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
}
