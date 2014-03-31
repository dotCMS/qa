package com.dotcms.qa.selenium.pages.backend;

import com.dotcms.qa.selenium.pages.IBasePage;

public interface IHostVariablesPage extends IBasePage {
	public void addNewHostVariable(String name, String key, String value) throws Exception;
	public void close();
	public void deleteHostVariable(String name);
	public boolean doesHostVariableExist(String name);
	public IHostVariablesAddOrEditPage getHostVariablesAddScreen() throws Exception;
}
