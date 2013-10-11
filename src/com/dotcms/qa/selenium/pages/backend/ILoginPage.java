package com.dotcms.qa.selenium.pages.backend;

import com.dotcms.qa.selenium.pages.IBasePage;

public interface ILoginPage extends IBasePage {
	public IPortletMenu login(String username, String password) throws Exception;
	public ILoginPage login_failure(String username, String password) throws Exception;
}
