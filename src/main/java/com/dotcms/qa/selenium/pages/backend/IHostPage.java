package com.dotcms.qa.selenium.pages.backend;

import org.openqa.selenium.WebElement;

import com.dotcms.qa.selenium.pages.IBasePage;

public interface IHostPage extends IBasePage {
	public boolean doesHostExist(String hostName);
	public void addBlankHost(String hostName) throws Exception;
	public void addCopyExistingHost(String hostName, String setHost) throws Exception;
	public void archiveHost(String hostName, boolean confirm) throws Exception;
	public void deleteHost(String hostName, boolean confirm) throws Exception;
	public void stopHost(String hostName, boolean confirm) throws Exception;
	public void editHost(String hostName, String newHostName, String palians);
	public void addHostVariable(String hostName, String varName, String varKey, String varValue) throws Exception;
	public void deleteHostVariable(String hostName, String varName, boolean confirm) throws Exception;
	public boolean doesHostVariableExist(String hostName, String variableName) throws Exception;
	public IHostVariablesPage getHostVariablesPage(String hostName) throws Exception;
	public WebElement returnHost(String hostName);
	public void toggleShowArchived();
}







