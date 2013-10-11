package com.dotcms.qa.selenium.pages.backend;

import com.dotcms.qa.selenium.pages.IBasePage;

public interface IContentSearchPage extends IBasePage {
	public IContentAddOrEdit_ContentPage getAddContentPage(String structureName) throws Exception;
}
