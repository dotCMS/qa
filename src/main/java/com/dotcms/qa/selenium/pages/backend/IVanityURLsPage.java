package com.dotcms.qa.selenium.pages.backend;

import com.dotcms.qa.selenium.pages.IBasePage;

public interface IVanityURLsPage extends IBasePage {
	public void addVanityURLToHost(String title, String hostName, String vanityURL, String URLtoRedirectTo) throws Exception;
	public void addVanityURLToAllHosts(String title, String vanityURL, String URLtoRedirectTo) throws Exception;
	public boolean deleteVanityURL(String title);
	public boolean editVanityURL(String oldTitle, String newTitle, String vanityURL, String URLtoRedirectTo);
}
