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
	  
	  
	  
	  public HostAddOrEditPage(WebDriver driver) {
			super(driver);
		}

	  public void addBlankHost(String hostID) {
		  hostName.sendKeys(hostID);
		  getWebElement(By.partialLinkText("Save / Activate")).click();		  
		}

	  public void addCopyHost(String hostID) {
		  hostName.sendKeys(hostID);
		  getWebElement(By.partialLinkText("Save / Activate")).click();		
		}
	  
		public void deleteHost() {
			//rigth click function 
		}
		

		public void editHost(String hostID, String palias) {
			 hostName.sendKeys(hostID);
			 aliases.sendKeys(palias);
			 //need save 	 
			 
		}
	  
	  
	  
	  
}
