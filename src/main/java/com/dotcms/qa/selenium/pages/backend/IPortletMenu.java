package com.dotcms.qa.selenium.pages.backend;

import org.openqa.selenium.WebElement;

import com.dotcms.qa.selenium.pages.IBasePage;

public interface IPortletMenu extends IBasePage {

	public IContentSearchPage getContentSearchPage() throws Exception;
	public ILicenseManagerPage getLicenseManagerPage() throws Exception;
	public IStructuresPage getStructuresPage() throws Exception;
	public IVanityURLsPage getVanityURLsPage() throws Exception;
	public IHostPage getHostPage() throws Exception;
	public ISiteBrowserPage getSiteBrowserPage() throws Exception;
	
	/**
	 * Get the User manager page
	 * @return ISiteBrowserPage
	 * @throws Exception
	 */
	public IUsersPage getUsersPage() throws Exception;
	
	/**
	 * Get the Role manager page
	 * @return ISiteBrowserPage
	 * @throws Exception
	 */
	public IRolesPage getRolesPage() throws Exception;
	
	public WebElement getPortletElement(String portletTextKey);
}
