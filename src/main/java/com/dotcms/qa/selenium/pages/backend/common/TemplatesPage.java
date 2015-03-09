package com.dotcms.qa.selenium.pages.backend.common;

import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.dotcms.qa.selenium.pages.backend.ITemplatesPage;
import com.dotcms.qa.selenium.pages.common.BasePage;
/**
 * This class implements the methods defined in the ITemplatesPage interface
 * to do the Users TestRail validations
 * @author Oswaldo Gallango
 * @since 01/22/2015
 * @version 1.0
 * 
 */
public class TemplatesPage extends BasePage implements ITemplatesPage {
	private static final Logger logger = Logger.getLogger(StructuresPage.class);

	/**
	 * Templates change host
	 */
	private WebElement changeHostId;
	private WebElement subNavHost;
	private WebElement fm_publish;

	public TemplatesPage(WebDriver driver) {
		super(driver);
	}

	/**
	 * Return the number of templates created for that host 
	 * @param hostName Name of the Host
	 * @return int 
	 * @throws Exception
	 */
	public int getNumberOfHostTemplates(String hostName) throws Exception{
		int retValue = 0;
		changeHostId.click();
		subNavHost.clear();
		subNavHost.sendKeys(hostName);
		subNavHost.sendKeys(Keys.RETURN);
		sleep(2);
		List<WebElement> templateViewingArea = fm_publish.findElement(By.className("buttonRow")).findElements(By.tagName("div"));
		for(WebElement div : templateViewingArea){
			String value = div.getAttribute("innerHTML");
			if(value.indexOf(getLocalizedString("Viewing")) != -1){
				value = value.substring(value.indexOf(getLocalizedString("of1")));
				value =value.replace(getLocalizedString("of1"), "").trim();
				retValue = Integer.parseInt(value);
				break;
			}
		}
		return retValue;
	}

}
