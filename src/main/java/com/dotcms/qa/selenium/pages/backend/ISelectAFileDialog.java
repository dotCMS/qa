package com.dotcms.qa.selenium.pages.backend;

import com.dotcms.qa.selenium.pages.IBasePage;

public interface ISelectAFileDialog extends IBasePage {
	public void close() throws Exception;
	public void selectFile(String fullyqualifiedFilename);	// i.e qademo.dotcms.com/intranet/documents/global-central-banks.pdf
}
