package com.dotcms.qa.selenium.pages.backend;

import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;

import com.dotcms.qa.selenium.pages.IBasePage;

public interface IContentAddOrEdit_ContentPage extends IBasePage {
	
	public ICategoriesDialog getCategoriesDialog(By linkBy) throws Exception;
	public ISelectAFileDialog getSelectAFileDialog(By linkBy, String folderDetailContentPaneId, String fileDetailContentPaneId) throws Exception;
	public void setHostOrFolder(String hostNameOrFolderPath) throws IllegalArgumentException; // i.e. "qademo.dotcms.com" or "qademo.dotcms.com/about-us/locations"
	public void toggleWYSIWYGBold();
	public void toggleWYSIWYGItalic();
	public void toggleWYSIWYGUnderline();
	public void addWYSIWYGText(String textToAdd);
	public void addKeyValuePair(By by);

	public IContentSearchPage saveAndPublish() throws Exception;
	
	/**
	 * Modify the current content language
	 * @param language language name
	 * @param keepPreviousContent keep original text  in new language content
	 * @throws Exception
	 */
	public void changeContentLanguage(String language, boolean keepPreviousContent) throws Exception;
	
	/**
	 *  Set Content text, textarea and wysiwyg Fields
	 * @param map List Map with contents fields
	 * @throws Exception
	 */
	public void setFields(List<Map<String, Object>> map) throws Exception;
	
	/**
	 * This method validate if the add content didn't find the structure name.
	 * Displaying the select structure dialog
	 * @return true if the box is displayed, false if not
	 * @throws Exception
	 */
	public boolean isStructureBoxDisplayed() throws Exception;
	
	/**
	 * Set the structure name in the select structure dialog 
	 */
	public void setStructure(String structureName) throws Exception;
	
	/**
	 * Check if the lock for editing button is present
	 * @return true if the content is locked, false if not
	 * @throws Exception
	 */
	public boolean isPresentContentLockButton() throws Exception;
	
	/**
	 * Click the lock for editing button
	 * @throws Exception
	 */
	public void clickLockForEditingButton() throws Exception;
	
	/**
	 * Click the release lock button
	 * @throws Exception
	 */
	public void clickReleaseLockButton() throws Exception;
	
	/**
	 * Click save button
	 * @throws Exception
	 */
	public void save() throws Exception;
	
	/**
	 * Click cancel button
	 * @throws Exception
	 */
	public void cancel() throws Exception;
	
	/**
	 * Get Content field value
	 * @param fieldName Field name
	 * @return String
	 * @throws Exception
	 */
	public String getFieldValue(String fieldName) throws Exception;
	
}
