package com.dotcms.qa.selenium.pages.backend.common;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import com.dotcms.qa.selenium.pages.backend.*;
import com.dotcms.qa.selenium.pages.common.BasePage;
import com.dotcms.qa.selenium.util.SeleniumPageManager;



public class HostPage extends BasePage implements IHostPage  {
	 private static final Logger logger = Logger.getLogger(VanityURLsPage.class);
	 	
	 	private WebElement dijit_form_Button_5_label, dijit_form_Button_9_label, dijit_form_Button_6_label, dijit_form_Button_14_label;
		private WebElement startBlankHostRadio, copyHostRadio;
		private WebElement id;
	 	@FindBy(how = How.CLASS_NAME, using = "listingTable")
	    private WebElement tableOfHost;
		private WebElement dijit_form_Button_13_label;
		private WebElement hostVariableName, hostVariableValue, hostVariableKey;
		private Map <String, WebElement> hostMapa = new HashMap <String , WebElement>();
	 	public HostPage(WebDriver driver) {
			super(driver);
		}
		
		
		/*
		 * To verify if any host exist
		 * @hostName = Name of the host to verify
		 * @see com.dotcms.qa.selenium.pages.backend.IHostPage#doesHostExist(java.lang.String)
		 */
	 	public boolean doesHostExist(String hostName) {
		boolean retValue = false;	
		try {	
			for(WebElement anchor : tableOfHost.findElements(By.tagName("a"))) {
					if(anchor.getAttribute("innerHTML").matches(hostName)) {
						retValue = true;
					}

			}
		}
			catch(NoSuchElementException e) {
				logger.trace("This host does not existing yet", e);
				// Move on to next row and keep going
		}
		
	return retValue;
	}
	

		/*
		 * To return any host as String
		 * @hostName = name of the host to verify
		 * @see com.dotcms.qa.selenium.pages.backend.IHostPage#returnHost(java.lang.String)
		 */
		public String returnHost(String hostName) {
			String retValue = "";	
			try {	
				for(WebElement anchor : tableOfHost.findElements(By.tagName("a"))) {
						if(anchor.getAttribute("innerHTML").matches(hostName)) {
						 hostMapa.put(anchor.getAttribute("innerHTML"), anchor);
						 retValue = anchor.getAttribute("innerHTML");
						}
				}
			}
			catch(NoSuchElementException e) {
					logger.trace("This host does not existing yet", e);
					// Move on to next row and keep going
			}
			
		return retValue;
		}

		
		/*
		 * To select any sub menu option when you right click any host in the host manager
		 * @Option = The option that you want select as String 
		 */
		public void SubMenuOption(String option) {
			WebElement hostDiv = getWebElement(By.id("doc3"));
			if(hostDiv != null) {
				List<WebElement> rows = hostDiv.findElements(By.tagName("tr"));
					for(WebElement row : rows) {
						List<WebElement> cols = row.findElements(By.tagName("td"));
							for(WebElement col : cols) {	
								try{
									this.hoverMove(col);
									if ((col.getAttribute("innerHTML").trim().equals(option)) /*&&  (row.getAttribute("class").contains("dijitFocuset"))*/) {
										row.click();
										
									}
								}
								catch(NoSuchElementException e) {
									logger.trace("This option is not in the list", e);
								}
								catch(Exception e) {
									logger.error("Unexpected error attempting to iterate over option=" + option, e);
								}	
							}
					
					}
				}
					
		}
			// this.searchElementList("dijit dijitReset dijitMenuTable dotContextMenu dijitMenu dijitMenuPassive", "td", "dijitMenuItemLabel", "html.dj_gecko body.dmundra div#popup_18.dijitPopup table#dijit_Menu_1.dijit tbody.dijitReset tr#dijit_MenuItem_4.dijitReset td#dijit_MenuItem_4_text.dijitReset", "Edit Host variables");
			 
		 
	   public boolean editHost(String hostName,String ahostName,String bhostName){
		   boolean retValue = false;
		   return retValue;
	   }
		
		public void addBlankHost(String hostName) throws Exception {
			dijit_form_Button_5_label.click();
			startBlankHostRadio.click();
			dijit_form_Button_6_label.click();
			IHostAddOrEditPage addPage = SeleniumPageManager.getPageManager().getPageObject(IHostAddOrEditPage.class);
			addPage.addHost(hostName);
		}
		
		
		public void addCopyExistingHost(String hostName, String setHost) throws Exception {
			dijit_form_Button_5_label.click();
			copyHostRadio.click();
			dijit_form_Button_6_label.click();
			id.clear();
			id.sendKeys(setHost);
			dijit_form_Button_9_label.click();
			IHostAddOrEditPage addPage = SeleniumPageManager.getPageManager().getPageObject(IHostAddOrEditPage.class);
			addPage.addHost(hostName);			
		}
			
		
		public boolean deleteHost(String hostName) {
			boolean retValue = false;
			List<WebElement> rows = tableOfHost.findElements(By.tagName("tr"));
			for(WebElement row : rows) {
				try {
					WebElement col = row.findElement(By.tagName("td"));
					if(col.getText().trim().equals(hostName)) {
						IHostAddOrEditPage delPage = SeleniumPageManager.getPageManager().getPageObject(IHostAddOrEditPage.class);
						delPage.deleteHost(hostName);						
						retValue = true;
					}
				}
				catch(NoSuchElementException e) {
					logger.trace("Row does not include td element", e);
					// Move on to next row and keep going
				}
				catch(Exception e) {
					logger.error("Unexpected error attempting to iterate over Host - HostName=" + hostName, e);
					// Move on to next row and keep going
				}
			}
			return retValue;		
		}
		
		public void addHostVariable(String hostName, String hostVariable) {
			String host= this.returnHost(hostName);
			this.doRigthClick(hostMapa.get(host));		
			SubMenuOption("Edit Host variables");
			//subMenuMapa.get("Edit Host variables").click();
			//dijit_form_Button_13_label.click();
			/*hostVariableName.clear();
			hostVariableName.sendKeys("new variale");
			hostVariableValue.clear();
			hostVariableValue.sendKeys("new value");
			hostVariableKey.clear();
			hostVariableKey.sendKeys("new key");
			dijit_form_Button_14_label.click();*/
			
		}
	
}
