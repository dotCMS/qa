package com.dotcms.qa.selenium.pages.backend.common;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.dotcms.qa.selenium.pages.backend.IPublishingQueuePage;
import com.dotcms.qa.selenium.pages.common.BasePage;

/**
 * This class implements the methods defined in the IPublishingQueuePage interface
 * @author Oswaldo Gallango
 * @since 03/10/2015
 * @version 1.0
 * 
 */
public class PublishingQueuePage extends BasePage implements IPublishingQueuePage{

	public PublishingQueuePage(WebDriver driver) {
		super(driver);
	}

	/**
	 * Get the bundles tab active
	 * @throws Exception
	 */
	public void getBundlesTab() throws Exception {
		getWebElement(By.id("mainTabContainer_tablist_unpushedBundles")).click();

	}

	/**
	 * Search the especified bundle and click the push publish button
	 * @param bundleName Name of the bundle
	 * @throws Exception
	 */
	public void pushPublishBundle(String bundleName) throws Exception{
		WebElement bundle = findBundle(bundleName);
		List<WebElement> ths = bundle.findElements(By.tagName("th"));
		List<WebElement> buttons = ths.get(1).findElements(By.cssSelector("span[class='dijitReset dijitInline dijitButtonText']"));
		for(WebElement button : buttons){
			if(button.getText().equals(getLocalizedString("Remote-Publish"))){
				button.click();
				break;
			}
		}
	}

	/**
	 * Return the bundle row
	 * @param bundleName  Name of the bundle
	 * @return WebElement
	 * @throws Exception
	 */
	private WebElement findBundle(String bundleName) throws Exception{
		WebElement bundleRow = null;
		List<WebElement> bundles = getWebElement(By.id("unpushedBundlesDiv")).findElements(By.tagName(bundleName));
		boolean found = false;
		for(WebElement bundle : bundles){
			List<WebElement> ths = bundle.findElements(By.tagName("th"));
			if(ths.get(0).getText().trim().equals(bundleName)){
				bundleRow=bundle;
				break;
			}
		}
		return bundleRow;
	}


}
