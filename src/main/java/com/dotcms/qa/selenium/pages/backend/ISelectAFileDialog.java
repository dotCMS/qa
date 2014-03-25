package com.dotcms.qa.selenium.pages.backend;

import org.openqa.selenium.WebElement;

import com.dotcms.qa.selenium.pages.IBasePage;

public interface ISelectAFileDialog extends IBasePage {
	
	enum ViewSelector
	{
		LIST_VIEW,
		DETAIL_VIEW,
		THUMBNAIL_VIEW
	}
	public void setContentPaneIds(String folderDetailContentPaneId, String fileDetailContentPaneId);
	public void setView(ViewSelector view);
	public void close() throws Exception;
	public void expandNode(WebElement node);
	public WebElement getChildNode(WebElement parentNode, String childNodeText);
	public void selectFile(String fullyqualifiedFilename) throws IllegalArgumentException;	// i.e qademo.dotcms.com/intranet/documents/global-central-banks.pdf
}
