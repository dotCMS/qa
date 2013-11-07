package com.dotcms.qa.selenium.pages.backend;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.dotcms.qa.selenium.pages.BasePage;
import com.dotcms.qa.selenium.util.SeleniumPageManager;
import com.dotcms.qa.util.language.LanguageManager;

public class PortletMenu extends BasePage implements IPortletMenu {

	public PortletMenu(WebDriver driver) {
		super(driver);
	}

	public IContentSearchPage getContentSearchPage() throws Exception {
	    hoverOverElement(getPortletElement("Content"));
	    getWebElement(By.linkText(LanguageManager.getValue("javax.portlet.title.EXT_11"))).click();  // this only works if you hover over the content portlet
		return SeleniumPageManager.getPageManager().getPageObject(IContentSearchPage.class);		
	}
	
	public IStructuresPage getStructuresPage() throws Exception {
	    hoverOverElement(getPortletElement("Structures"));
	    getWebElement(By.linkText(LanguageManager.getValue("javax.portlet.title.EXT_STRUCTURE"))).click();  // this only works if you hover over the structures portlet
		return SeleniumPageManager.getPageManager().getPageObject(IStructuresPage.class);
	}
	
	public WebElement getPortletElement(String portletTextKey) {
		WebElement retValue = null;
		String portletText = LanguageManager.getValue(portletTextKey);
		System.out.println("structure - " + LanguageManager.getValue("structure"));
		System.out.println("Structure - " + LanguageManager.getValue("Structure"));
		System.out.println("structures - " + LanguageManager.getValue("structures"));
		System.out.println("portletTextKey - " + portletText);
		List<WebElement> allElements = getWebElements(By.className("navMenu-title")); 
		for (WebElement element: allElements) {
			if(portletText.equals(element.getText())){
				System.out.println(element.getTagName() + "|" + element.getText());
				retValue = element;
			    break;
			}
			else {
				System.out.println("portletText=" + portletText + "|element.getText()=" + element.getText() +"|element.getTagName()=" + element.getTagName());				
			}
		}
		return retValue;
	}

}
