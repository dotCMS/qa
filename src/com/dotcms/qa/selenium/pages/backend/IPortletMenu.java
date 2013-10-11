package com.dotcms.qa.selenium.pages.backend;

import org.openqa.selenium.WebElement;

import com.dotcms.qa.selenium.pages.IBasePage;

public interface IPortletMenu extends IBasePage {

	public IContentSearchPage getContentSearchPage() throws Exception;
	public IStructuresPage getStructuresPage() throws Exception;
	
	public WebElement getPortletElement(String portletTextKey);
}
