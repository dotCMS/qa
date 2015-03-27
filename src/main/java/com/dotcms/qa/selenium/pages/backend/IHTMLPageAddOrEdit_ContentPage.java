package com.dotcms.qa.selenium.pages.backend;

import com.dotcms.qa.selenium.pages.IBasePage;

public interface IHTMLPageAddOrEdit_ContentPage extends IBasePage {
	public void setTitle(String title);
	public void setURL(String URL);
	public void setTemplate(String templateName) throws Exception;
	public void setFriendlyName(String title);
	public void save();
	public void saveAndPublish();
	public void cancel();
}
