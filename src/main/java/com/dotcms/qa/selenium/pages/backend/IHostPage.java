package com.dotcms.qa.selenium.pages.backend;

import com.dotcms.qa.selenium.pages.IBasePage;

public interface IHostPage extends IBasePage {
	public boolean doesHostExist(String hostID);
	public void addBlankHost(String hostID) throws Exception;
	public void addCopyExistingHost(String hostID) throws Exception;
	public boolean deleteHost(String hostID);
	public boolean editHost(String hostID, String newHostID, String palians);
}







