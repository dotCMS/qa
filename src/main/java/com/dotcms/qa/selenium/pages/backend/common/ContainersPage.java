package com.dotcms.qa.selenium.pages.backend.common;


import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.dotcms.qa.selenium.pages.backend.IContainerAddOrEditPage;
import com.dotcms.qa.selenium.pages.backend.IContainersPage;
import com.dotcms.qa.selenium.pages.common.BasePage;
import com.dotcms.qa.selenium.util.SeleniumPageManager;

public class ContainersPage extends BasePage implements IContainersPage {

	public ContainersPage(WebDriver driver) {
		super(driver);
	}

	/**
	 * Allows to add a new container
	 * @return IContentAddOrEdit_ContentPage
	 * @throws Exception
	 */
	public IContainerAddOrEditPage addContainer() throws Exception{
		
		List<WebElement> spans = getWebElement(By.cssSelector("div[class='yui-gc portlet-toolbar']")).findElements(By.cssSelector("span[class='dijitReset dijitInline dijitButtonNode']"));
		for(WebElement span : spans){
			if(span.getText().equals(getLocalizedString("add-container"))){
				span.click();
				break;
			}
		}
		return SeleniumPageManager.getBackEndPageManager().getPageObject(IContainerAddOrEditPage.class);
	}
}
