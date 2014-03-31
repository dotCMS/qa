package com.dotcms.qa.selenium.pages.backend;

import com.dotcms.qa.selenium.pages.IBasePage;

public interface IHostVariablesAddOrEditPage extends IBasePage {

	public void setFields(String name, String key, String value);
	public void setName(String name);
	public void setKey(String key);
	public void setValue(String value);
	public void save();
	public void cancel();
}
