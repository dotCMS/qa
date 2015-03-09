package com.dotcms.qa.selenium.pages.backend;

import com.dotcms.qa.selenium.pages.IBasePage;

public interface IContainersPage extends IBasePage {

	/**
	 * Allows to add a new container
	 * @return IContentAddOrEdit_ContentPage
	 * @throws Exception
	 */
	public IContainerAddOrEditPage addContainer() throws Exception;
}
