package com.dotcms.qa.selenium.pages.backend;

import com.dotcms.qa.selenium.pages.IBasePage;

public interface IVanityURLsAddOrEditPage extends IBasePage {
	public void addVanityURL(String title, String hostName, String vanityURL, String URLtoRedirectTo);
	public void deleteVanityURL();
	public void editVanityURL(String title, String vanityURL, String URLtoRedirectTo);
}
