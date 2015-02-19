package com.dotcms.qa.selenium.pages.backend;

import java.util.List;
import java.util.Map;

import org.openqa.selenium.WebElement;

import com.dotcms.qa.selenium.pages.IBasePage;

/**
 * Preview HTMLPage Interface
 * @author Oswaldo Gallango
 * @since 11/24/2014
 * @version 1.0
 *
 */
public interface IPreviewHTMLPage_Page extends IBasePage {
	
	/**
	 * Return current page  language
	 * @return String
	 * @throws Exception
	 */
	public String getCurrentLanguage() throws Exception;
	
	/**
	 * Change current page language
	 * @param language Language Name
	 * @throws Exception
	 */
	public void changeLanguage(String language) throws Exception;

	/**
	 * Get the source code of the current page
	 * @return String
	 * @throws Exception
	 */
	public String getPageSource()  throws Exception;
	
	/**
	 * Modify and existing content
	 * @param contentInode inode of the content to edit
	 * @param content HashMap with content info to change
	 * @param language language name
	 * @param keepPreviousContent keep original text  in new language content
	 * @return IContentAddOrEdit_ContentPage
	 */
	public IPreviewHTMLPage_Page editContent(String contentInode, List<Map<String,Object>> content, String language, boolean keepPreviousContent)  throws Exception;
	
	/**
	 * Add a content
	 * @param containerInode Inode of container where the contentlet will be added
	 * @param content HashMap with content info to add
	 * @param language Language 
	 * @return IContentAddOrEdit_ContentPage
	 */
	public IPreviewHTMLPage_Page addContent(String containerInode, List<Map<String,Object>> content, String language) throws Exception;
	
	/**
	 * Return to main portlet menu
	 */
	public void returnToPortletsMenu()  throws Exception;
	
	/**
	 * Get the container contents
	 * @param containerInode Inode of container
	 * @return List<String>
	 * @throws Exception
	 */
	public List<String> getContainerContents(String containerInode) throws Exception;
	
	/**
     * Get the container inode
     * @param containerName
     * @return String
     * @throws Exception
     */
	public String getContainerInode(String containerName) throws Exception;
	
	/**
	 * Click the edit mode tab
	 */
	public void selectEditModeView() throws Exception;
	
	/**
	 * Click the preview mode tab
	 */
	public void selectPreviewModeView() throws Exception;
	
	/**
	 * Click the live mode tab
	 */
	public void selectLiveModeView() throws Exception;
	
	/**
	 * Validate if the page is locked
	 * @return true if it is locked, false if not
	 * @throws Exception
	 */
	public boolean isLocked() throws Exception;
	
	/**
	 * Click the unlock link for the current page
	 */
	public void unLockPage() throws Exception;
}
