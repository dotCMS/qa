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
	 * @return IUsersPage
	 * @throws Exception
	 */
	public IUsersPage getUsersPage() throws Exception;
	
	/**
	 * Get the Role manager page
	 * @return IRolesPage
	 * @throws Exception
	 */
	public IRolesPage getRolesPage() throws Exception;
	
	/**
	 * Get the Mailing List manager page
	 * @return IMailingListPage
	 * @throws Exception
	 */
	public IMailingListPage getMailingListPage() throws Exception;
	
	/**
	 * Get the Templates manager page
	 * @return IMailingListPage
	 * @throws Exception
	 */
	public ITemplatesPage getTemplatesPage() throws Exception;

	/**
	 * Get the configuration manager page
	 * @return IConfigurationPage
	 * @throws Exception
	 */
	public IConfigurationPage getConfigurationPage() throws Exception;

	
	public WebElement getPortletElement(String portletTextKey);
}
