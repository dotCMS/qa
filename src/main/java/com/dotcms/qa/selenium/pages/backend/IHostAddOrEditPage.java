package com.dotcms.qa.selenium.pages.backend;

import com.dotcms.qa.selenium.pages.IBasePage;

public interface IHostAddOrEditPage extends IBasePage{
	public void addBlankHost(String hostID);
	public void addCopyHost(String hostID);
	public void deleteHost();
	public void editHost(String hostID, String palias);
}
