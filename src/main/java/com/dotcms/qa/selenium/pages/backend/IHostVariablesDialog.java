package com.dotcms.qa.selenium.pages.backend;

import com.dotcms.qa.selenium.pages.IBasePage;

public interface IHostVariablesDialog extends IBasePage {
	public void addNewHostVariable(String name, String key, String value) throws Exception;
	public void close();
	public void deleteHostVariable(String variableName, boolean confirm);
	public boolean doesHostVariableExist(String variableName);
	public IHostVariablesAddOrEditDialog getHostVariablesAddScreen() throws Exception;
}
