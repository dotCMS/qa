package com.dotcms.qa.selenium.pages.backend;

import com.dotcms.qa.selenium.pages.IBasePage;

public interface IHostAddOrEditPage extends IBasePage{
	public void addHost(String hostID);
	public void editHost(String hostID ,String palias);
}
