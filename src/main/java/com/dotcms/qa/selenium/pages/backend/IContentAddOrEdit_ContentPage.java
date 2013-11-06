package com.dotcms.qa.selenium.pages.backend;

import com.dotcms.qa.selenium.pages.IBasePage;

public interface IContentAddOrEdit_ContentPage extends IBasePage {
	public void setTitle(String title);
		
	public void toggleWYSIWYGBold();
	
	public void toggleWYSIWYGItalic();
	
	public void toggleWYSIWYGUnderline();
	
	public void addWYSIWYGText(String textToAdd);

	public IContentSearchPage saveAndPublish() throws Exception;
}
