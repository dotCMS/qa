package com.dotcms.qa.selenium.pages.backend.common;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.dotcms.qa.selenium.pages.backend.IContentAddOrEdit_ContentPage;
import com.dotcms.qa.selenium.pages.backend.IFolderAddOrEditPage;
import com.dotcms.qa.selenium.pages.backend.IPreviewHTMLPage_Page;
import com.dotcms.qa.selenium.pages.common.BasePage;
import com.dotcms.qa.selenium.util.SeleniumPageManager;
import com.dotcms.qa.util.WebKeys;

/**
 * This class implements the methods defined in the IPreviewHTMLPage_Page interface
 * @author Oswaldo Gallango
 * @since 01/21/2015
 * @version 1.0
 * 
 */
public class PreviewHTMLPage_Page extends BasePage implements IPreviewHTMLPage_Page {

	//private WebElement language_id;
	private WebDriver myDriver=null;
	public PreviewHTMLPage_Page(WebDriver driver) {
		super(driver);
		myDriver = driver;
	}

	/**
	 * Return current page  language
	 * @return String
	 * @throws Exception
	 */
	public String getCurrentLanguage() throws Exception{
		String value = getMenuFrameElement("combo_zone2",WebKeys.BY_ID).findElement(By.id("language_id")).getAttribute("value");
		returnToPageDefaultContent();
		return value;
	}

	/**
	 * Change current page language
	 * @param language Language Name
	 * @throws Exception
	 */
	public void changeLanguage(String language) throws Exception{
		WebElement language_id = getMenuFrameElement("combo_zone2",WebKeys.BY_ID).findElement(By.id("language_id"));
		language_id.clear();
		language_id.sendKeys(language);
		sleep(1);
		language_id.sendKeys(Keys.RETURN);
		returnToPageDefaultContent();
	}

	/**
	 * Get the source code of the current page
	 * @return String
	 * @throws Exception
	 */
	public String getPageSource()  throws Exception{
		return myDriver.getPageSource();
	}

	/**
	 * Get current page menu iframe WebElement
	 * @return WebElement
	 * @throws Exception
	 */
	private WebElement getMenuFrameElement(String elementName, String bySearch) throws Exception{
		WebElement elem = null;
		if(bySearch.equals(WebKeys.BY_ID)){
			elem = myDriver.switchTo().frame("frameMenu").findElement(By.id(elementName));
		}else if(bySearch.equals(WebKeys.BY_CSS_SELECTOR)){
			elem = myDriver.switchTo().frame("frameMenu").findElement(By.cssSelector(elementName));
		} else if(bySearch.equals(WebKeys.BY_INPUT_NAME)){
			elem = myDriver.switchTo().frame("frameMenu").findElement(By.tagName(elementName));
		}else if(bySearch.equals(WebKeys.BY_CLASS_NAME)){
			elem = myDriver.switchTo().frame("frameMenu").findElement(By.className(elementName));
		}
		return elem; 
	}

	/**
	 * Get current page main iframe WebElement
	 * @return WebElement
	 * @throws Exception
	 */
	private WebElement getMainFrameElement(String elementName, String bySearch) throws Exception{
		WebElement elem = null;
		if(bySearch.equals(WebKeys.BY_ID)){
			elem = myDriver.switchTo().frame("frameMain").findElement(By.id(elementName));
		}else if(bySearch.equals(WebKeys.BY_CSS_SELECTOR)){
			elem = myDriver.switchTo().frame("frameMain").findElement(By.cssSelector(elementName));
		} else if(bySearch.equals(WebKeys.BY_INPUT_NAME)){
			elem = myDriver.switchTo().frame("frameMain").findElement(By.tagName(elementName));
		}else if(bySearch.equals(WebKeys.BY_CLASS_NAME)){
			elem = myDriver.switchTo().frame("frameMain").findElement(By.className(elementName));
		}
		return elem; 
	}

	/**
	 * Return to defaultContent after watch Iframe
	 * @throws Exception
	 */
	private void returnToPageDefaultContent() throws Exception{
		myDriver.switchTo().defaultContent();
	}
	
	/**
	 * Return to Main Iframe
	 * @throws Exception
	 */
	private void returnToMainFrame() throws Exception{
		myDriver.switchTo().frame("frameMain");
	}
	
	/**
	 * Return to Menu Iframe
	 * @throws Exception
	 */
	private void returnToMenuFrame() throws Exception{
		myDriver.switchTo().frame("frameMenu");
	}

	/**
	 * Modify and existing content
	 * @param contentInode inode of the content to edit
	 * @param content HashMap with content info to change
	 * @param language language name
	 * @param keepPreviousContent keep original text  in new language content
	 * @return IContentAddOrEdit_ContentPage
	 */
	public IPreviewHTMLPage_Page editContent(String contentInode, List<Map<String,Object>> content, String language, boolean keepPreviousContent)  throws Exception{
		getMainFrameElement("edit-"+contentInode,WebKeys.BY_ID).findElement(By.className("dotEditContent")).click();
		IContentAddOrEdit_ContentPage contentPage = SeleniumPageManager.getBackEndPageManager().getPageObject(IContentAddOrEdit_ContentPage.class);
		contentPage.changeContentLanguage(language,keepPreviousContent);
		contentPage.setFields(content);
		contentPage.saveAndPublish();
		return SeleniumPageManager.getBackEndPageManager().getPageObject(IPreviewHTMLPage_Page.class);
	}

	/**
	 * Add a content
	 * @param containerInode Inode of container where the contentlet will be added
	 * @param content HashMap with content info to add
	 * @param language Language 
	 * @return IContentAddOrEdit_ContentPage
	 */
	public IPreviewHTMLPage_Page addContent(String containerInode, List<Map<String,Object>> content, String language)   throws Exception{
		WebElement elem = getMainFrameElement("controlAnchor"+containerInode,WebKeys.BY_ID);
		elem.click();
		returnToPageDefaultContent();
		getWebElement(By.id("control-"+containerInode)).findElement(By.cssSelector("span[class='dotNewContent']")).findElement(By.tagName("a")).click();
		IContentAddOrEdit_ContentPage contentPage = SeleniumPageManager.getBackEndPageManager().getPageObject(IContentAddOrEdit_ContentPage.class);;
		contentPage.changeContentLanguage(language, false);
		contentPage.setFields(content);
		contentPage.saveAndPublish();
		return SeleniumPageManager.getBackEndPageManager().getPageObject(IPreviewHTMLPage_Page.class);
	}

	/**
	 * Return to main portlet menu
	 */
	public void returnToPortletsMenu()  throws Exception{
		returnToPageDefaultContent();
		WebElement admin_screen = getMenuFrameElement("buttonRow",WebKeys.BY_ID).findElement(By.cssSelector("span[id*='dijit_form_Button_'][class='dijitReset dijitInline dijitButtonText']"));
		admin_screen.click();
	}

	/**
	 * Get the container contents inodes
	 * @param containerInode Inode of container
	 * @return List<String>
	 * @throws Exception
	 */
	public List<String> getContainerContents(String containerInode) throws Exception{
		List<String> results = new ArrayList<String>(); 
		WebElement anchorContainerDiv = getMainFrameElement("controlAnchor"+containerInode,WebKeys.BY_ID);
		WebElement parentDiv = getParent(getParent(getParent(anchorContainerDiv)));
		List<WebElement> elements = parentDiv.findElements(By.className("dotContentlet"));
		for(WebElement elem : elements){
			WebElement el = elem.findElement(By.cssSelector("div[id*='edit-']"));
			results.add(el.getAttribute("id").replace("edit-", ""));
		}
		returnToPageDefaultContent();
		return results;
	}

    /**
     * Get the container inode
     * @param containerName
     * @return String
     * @throws Exception
     */
	public String getContainerInode(String containerName) throws Exception{
		String inode = null;
		List<WebElement> containersList = getMainFrameElement("template-bd", WebKeys.BY_CLASS_NAME).findElements(By.className("dotContainerNotesAnchor"));
		for(WebElement elem : containersList){
			WebElement link = elem.findElement(By.tagName("a"));
			link.click();
			inode = link.getAttribute("id").replaceAll("containerNotes","");
			returnToPageDefaultContent();
			WebElement noteDiv = getWebElement(By.cssSelector("div[id*='control-notes-']")).findElement(By.className("dotContainerInfo"));
			if(noteDiv.getAttribute("innerHTML").contains(containerName)){
				break;
			} else {
				getWebElement(By.className("dotNoteBoxButtons")).findElement(By.className("dotNoteClose")).click();
				inode=null;
			}
			returnToMainFrame();
		}
		return inode;
	}
}
