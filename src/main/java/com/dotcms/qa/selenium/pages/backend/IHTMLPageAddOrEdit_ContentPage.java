package com.dotcms.qa.selenium.pages.backend;

import com.dotcms.qa.selenium.pages.IBasePage;

/**
 * Interface to control HTML page properties
 * @author Brent Griffin
 * @author Oswaldo Gallango
 *
 */
public interface IHTMLPageAddOrEdit_ContentPage extends IBasePage {
	public void setTitle(String title);
	public void setURL(String URL);
	public void setTemplate(String templateName) throws Exception;
	public void setFriendlyName(String title);
	public void save();
	public void saveAndPublish();
	public void cancel();
	/**
	 * Validate if the page is locked
	 * @return true is the page is locked, false if not
	 * @throws Exception
	 */
	public boolean isLocked() throws Exception;
	/**
	 * Unlock Page
	 * @throws Exception
	 */
	public void unlock() throws Exception;
}
