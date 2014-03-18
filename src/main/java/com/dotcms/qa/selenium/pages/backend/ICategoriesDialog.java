package com.dotcms.qa.selenium.pages.backend;

import com.dotcms.qa.selenium.pages.IBasePage;

public interface ICategoriesDialog extends IBasePage {
	public void close() throws Exception;
	public void addCategory(int index);
	public void addTopLevelCategory(String categoryName) throws Exception;
	public void search(String categoryName);
}
