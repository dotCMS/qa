package com.dotcms.qa.selenium.pages.backend;

import org.openqa.selenium.WebElement;

import com.dotcms.qa.selenium.pages.IBasePage;

public interface IHostPage extends IBasePage {
	public boolean doesHostExist(String hostID);
	public void addBlankHost(String hostID) throws Exception;
	public void addCopyExistingHost(String hostID, String setHost) throws Exception;
	public boolean deleteHost(String hostID);
	public boolean editHost(String hostID, String newHostID, String palians);
	public void addHostVariable(String hostName, String varName, String varKey, String varValue) throws Exception;
	public void deleteHostVariable(String hostName, String varName, boolean confirm) throws Exception;
	public boolean doesHostVariableExist(String hostName, String variableName) throws Exception;
	public IHostVariablesPage getHostVariablesPage(String hostName) throws Exception;
	public WebElement returnHost(String hostName);
}







