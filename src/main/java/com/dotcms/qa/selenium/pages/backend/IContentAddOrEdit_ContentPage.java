package com.dotcms.qa.selenium.pages.backend;

import org.openqa.selenium.By;

import com.dotcms.qa.selenium.pages.IBasePage;

public interface IContentAddOrEdit_ContentPage extends IBasePage {
	
	public ICategoriesDialog getCategoriesDialog(By linkBy) throws Exception;
	public ISelectAFileDialog getSelectAFileDialog(By linkBy) throws Exception;
	
	public void toggleWYSIWYGBold();
	public void toggleWYSIWYGItalic();
	public void toggleWYSIWYGUnderline();
	public void addWYSIWYGText(String textToAdd);

	public IContentSearchPage saveAndPublish() throws Exception;
}
