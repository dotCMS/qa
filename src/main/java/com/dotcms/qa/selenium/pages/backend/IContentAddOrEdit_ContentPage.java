package com.dotcms.qa.selenium.pages.backend;

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
	 * Set Content Fields
	 * @param map Map with contents fields
	 * @throws Exception
	 */
	public void setFields(Map<String, Object> map) throws Exception;
}
