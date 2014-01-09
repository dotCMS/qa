package com.dotcms.qa.selenium.pages.backend.common;

import org.apache.log4j.Logger;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.dotcms.qa.selenium.pages.backend.*;
import com.dotcms.qa.selenium.pages.common.BasePage;

public class HostAddOrEditPage extends BasePage implements IHostAddOrEditPage {
	  private static final Logger logger = Logger.getLogger(HostAddOrEditPage.class);
	  
	  private WebElement hostName;
	  private WebElement aliases;
	  private WebElement dijit_MenuItem_2_text;
	  private WebElement dijit_MenuItem_3_text;
	  private WebElement showDeleted;
	  private WebElement dijit_MenuItem_12_text;
	  
	  
	  public HostAddOrEditPage(WebDriver driver) {
			super(driver);  
		}

	  public void addHost(String hostID) {
		  hostName.sendKeys(hostID);
		  getWebElement(By.partialLinkText("Save / Activate")).click();	 	 

		}

	  	  
		public void deleteHost(String hostID) {
			doRightClick(hostID);
			dijit_MenuItem_2_text.click();
			dijit_MenuItem_3_text.click();
			Alert alert = switchToAlert();
			alert.accept();
			showDeleted.click();
			doRightClick(hostID);
			dijit_MenuItem_12_text.click();
			alert.accept();
		}
		

		public void editHost(String hostID, String palias) {
			 hostName.sendKeys(hostID);
			 aliases.sendKeys(palias);
			 getWebElement(By.partialLinkText("Save / Activate")).click();	 	 
			 
		}
		
}
