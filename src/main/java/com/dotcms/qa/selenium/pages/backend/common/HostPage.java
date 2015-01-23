package com.dotcms.qa.selenium.pages.backend.common;

import java.io.File;
import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.dotcms.qa.selenium.pages.backend.*;
import com.dotcms.qa.selenium.pages.common.BasePage;
import com.dotcms.qa.selenium.util.SeleniumPageManager;

public class HostPage extends BasePage implements IHostPage  {
	private static final Logger logger = Logger.getLogger(HostPage.class);

	@FindBy(how = How.ID, using = "dijit_form_Button_5")
	private WebElement addSiteButton;

	public HostPage(WebDriver driver) {
		super(driver);
	}

	/*
	 * To verify if any host exist
	 * @hostName = Name of the host to verify
	 */
	public boolean doesHostExist(String hostName) {
		return (returnHost(hostName) != null);
	}


	/*
	 * To return WebElement for any host
	 * @hostName = name of the host to retrieve
	 */
	public WebElement returnHost(String hostName) {
		WebElement retValue = null;	
		WebElement tableOfHost = getWebElement(By.className("listingTable"));
		for(WebElement anchor : tableOfHost.findElements(By.tagName("a"))) {
			String host = anchor.getAttribute("innerHTML").replaceAll("<b>", "").replaceAll("</b>", "");
			if(host.startsWith(hostName)) {
				retValue = anchor;
				break;
			}
		}		
		return retValue;
	}

	public void editHost(String hostName,String ahostName,String bhostName){
		// TODO - implement
	}

	public boolean doesHostVariableExist(String hostName, String variableName) throws Exception {
		IHostVariablesDialog hostVarPage = getHostVariablesPage(hostName);
		boolean retValue =  hostVarPage.doesHostVariableExist(variableName);
		hostVarPage.close();
		//    	reload();			// TODO - remove need for this reload call
		return retValue;
	}

	public void addBlankHost(String hostName) throws Exception {
		addSiteButton.click();
		IHostCreateNewSiteDialog createNewSiteDlg = SeleniumPageManager.getBackEndPageManager().getPageObject(IHostCreateNewSiteDialog.class);
		createNewSiteDlg.addBlankHost(hostName);
	}

	public void addCopyExistingHost(String hostName, String hostToCopy) throws Exception {
		addSiteButton.click();
		IHostCreateNewSiteDialog createNewSiteDlg = SeleniumPageManager.getBackEndPageManager().getPageObject(IHostCreateNewSiteDialog.class);
		createNewSiteDlg.addCopyExistingHost(hostName, hostToCopy);
	}

	public void archiveHost(String hostName, boolean confirm) throws Exception {
		this.selectPopupMenuOption(hostName, getLocalizedString("Archive-Host"));
		Alert alert = this.switchToAlert();
		if(confirm) {
			alert.accept();
		}
		else {
			alert.dismiss();
		}
		reload();			// TODO - remove need for this reload call
	}

	public void deleteHost(String hostName, boolean confirm) throws Exception {
		this.selectPopupMenuOption(hostName, getLocalizedString("Delete-Host"));
		Alert alert = this.switchToAlert();
		if(confirm) {
			alert.accept();
		}
		else {
			alert.dismiss();
		}
		reload();			// TODO - remove need for this reload call
	}

	public void stopHost(String hostName, boolean confirm) throws Exception {
		this.selectPopupMenuOption(hostName, getLocalizedString("Stop-Host"));
		Alert alert = this.switchToAlert();
		if(confirm) {
			alert.accept();
		}
		else {
			alert.dismiss();
		}
		reload();			// TODO - remove need for this reload call
	}

	public void addHostVariable(String hostName, String varName, String varKey, String varValue) throws Exception {
		IHostVariablesDialog varPage = getHostVariablesPage(hostName);
		varPage.addNewHostVariable(varName, varKey, varValue);
		varPage.close();
		reload();			// TODO - remove need for this reload call
	}	

	public void deleteHostVariable(String hostName, String varName, boolean confirm) throws Exception{
		IHostVariablesDialog varPage = getHostVariablesPage(hostName);
		varPage.deleteHostVariable(varName, confirm);
		varPage.close();	
		reload();			// TODO - remove need for this reload call
	}

	public IHostVariablesDialog getHostVariablesPage(String hostName) throws Exception {
		IHostVariablesDialog retValue = null;
		if(selectPopupMenuOption(hostName, getLocalizedString("Edit-Host-Variables"))) {
			retValue = SeleniumPageManager.getBackEndPageManager().getPageObject(IHostVariablesDialog.class);
		}
		return retValue;
	}

	private boolean selectPopupMenuOption(String hostName, String menuOption) throws Exception {
		boolean foundValue = false;
		sleep(1);
		rightClickElement(returnHost(hostName));	
		WebElement popupMenu = getWebElementClickable(By.className("dijitMenuPopup"));
		//this.hoverOverElement(popupMenu);
		List<WebElement> rows = popupMenu.findElements(By.tagName("tr"));
		WebElement prevRow = null;
		for(WebElement row : rows) {
			//this.hoverOverElement(row);
			//row = this.getWebElementClickable(row);
			if(prevRow != null) {
				logger.info("* prevRow.isDisplayed() = " + prevRow.isDisplayed());
				logger.info("* prevRow.isEnabled() = " + prevRow.isEnabled());
			}
			logger.info("* isDisplayed() = " + row.isDisplayed());
			logger.info("* isEnabled() = " + row.isEnabled());
			List<WebElement> labels = row.findElements(By.className("dijitMenuItemLabel"));
			for(WebElement label : labels) {
				//this.hoverOverElement(label);
				//label = this.getWebElementClickable(label);
				logger.info("label innerHTML = |" + label.getAttribute("innerHTML") + "|");
				if(label.getAttribute("innerHTML").trim().startsWith(menuOption)) {
					this.hoverOverElement(label);
					getWebElementClickable(label).click();
					foundValue = true;
					break;
				}
			}
			if(foundValue)
				break;
			prevRow = row;
		}
		return foundValue;
	}

	public void toggleShowArchived() {
		WebElement checkBox = null;
		List<WebElement> inputs = this.getWebElements(By.tagName("input"));
		for(WebElement input : inputs) {
			if(input.getAttribute("name").trim().equals("showDeleted")) {
				checkBox = input;
				break;
			}
		}
		if(checkBox != null) {
			checkBox.click();
		}
	}

	/**
	 * Add a host thumbnail into the specified host
	 * @param hostName Name of the host where the thumbnail will be added
	 * @throws Exception
	 */
	public void addHostThumbnail(String hostName) throws Exception{
		returnHost(hostName).click();
		sleep();
		//Allow to edit
		WebElement lockContentButton = getLockForEditingButton();
		if(lockContentButton != null){
			lockContentButton.click();
		}
		//add the logo
		String path = System.getProperty("user.dir");
		File file = new File(path+"/dotcms_logo.png");
		getWebElement(By.cssSelector("input[type='file'][name='binary1FileUpload']")).sendKeys(file.getAbsolutePath());
		sleep(5);
		//Save the changes
		getSaveActivateButton().click();
	}

	/**
	 * Search the Save/Activate button dynamically, because doesn't have a fixed id and
	 * dojo change it some times
	 * @return WebElement
	 */
	private WebElement getSaveActivateButton(){
		WebElement saveActivateButton=null;
		List<WebElement> atags = getWebElementPresent(By.id("contentletActionsHanger")).findElements(By.tagName("a"));
		for(WebElement link : atags){
			if(link.getText().equals(getLocalizedString("Save-Activate"))){
				saveActivateButton = link;
				break;
			}
		}
		return saveActivateButton;
	}

	/**
	 * Search the Cancel button dynamically, because doesn't have a fixed id and
	 * dojo change it some times
	 * @return WebElement
	 */
	private WebElement getCancelButton(){
		WebElement saveActivateButton=null;
		List<WebElement> atags = getWebElementPresent(By.id("contentletActionsHanger")).findElements(By.tagName("a"));
		for(WebElement link : atags){
			if(link.getText().equals(getLocalizedString("Cancel"))){
				saveActivateButton = link;
				break;
			}
		}
		return saveActivateButton;
	}

	/**
	 * Search the Cancel button dynamically, because doesn't have a fixed id and
	 * dojo change it some times
	 * @return WebElement
	 */
	private WebElement getLockForEditingButton(){
		WebElement saveActivateButton=null;
		List<WebElement> atags = getWebElementPresent(By.id("contentletActionsHanger")).findElements(By.tagName("a"));
		for(WebElement link : atags){
			if(link.getText().equals(getLocalizedString("Make-Editable"))){
				saveActivateButton = link;
				break;
			}
		}
		return saveActivateButton;
	}
	/**
	 * Remove the host thumbnail from the specified host
	 * @param hostName Name of the host where the thumbnail will be added
	 * @throws Exception
	 */
	public void removeHostThumbnail(String hostName) throws Exception{
		returnHost(hostName).click();
		sleep();
		//Allow to edit
		WebElement lockContentButton = getLockForEditingButton();
		if(lockContentButton != null){
			lockContentButton.click();
		}
		List<WebElement> spans = getWebElement(By.cssSelector("div[id='hostThumbnail']")).findElements(By.cssSelector("span[class='dijitReset dijitInline dijitButtonText']"));
		for(WebElement span : spans){
			if(span.getText().equals(getLocalizedString("remove"))){
				span.click();
				break;
			}
		}
		getSaveActivateButton().click();
	}

	/**
	 * Validate if the host have a host thumbnail
	 * @param hostName Name of the host where the thumbnail will be added
	 * @return true if the host have a thumbnail, false if not
	 * @throws Exception
	 */
	public boolean doesHostHaveHostThumbnail(String hostName) throws Exception{
		boolean retValue = false;
		returnHost(hostName).click();
		sleep();
		WebElement binary = getWebElement(By.cssSelector("div[name='binary1FileName']"));
		if(!binary.getText().isEmpty()){
			retValue=true;
		}
		getCancelButton().click();
		return retValue;
	}

	/**
	 * Validate is the host is active
	 * @param hostName Name of the host to validate
	 * @return true if the host is active false if not
	 */
	public boolean isHostActive(String hostName) throws Exception{
		boolean retValue = false;

		List<WebElement> tableOfHosts = getWebElement(By.className("listingTable")).findElements(By.tagName("tbody"));
		for(WebElement elem : tableOfHosts) {
			for(WebElement element : elem.findElements(By.tagName("tr"))){
				try{
					WebElement host = element.findElement(By.tagName("a"));
					WebElement icon = element.findElement(By.tagName("span"));
					String name = host.getAttribute("innerHTML").replaceAll("<b>", "").replaceAll("</b>", "");
					if(name.startsWith(hostName) && !icon.getAttribute("class").equals("hostStoppedIcon")) {
						retValue = true;
						break;
					}
				}catch(Exception e){

				}
			}
			if(retValue){
				break;
			}
		}
		return retValue;
	}	

	/**
	 * Validate is the host is default
	 * @param hostName Name of the host to validate
	 * @return true if the host is active false if not
	 */
	public boolean isHostDefault(String hostName) throws Exception{
		boolean retValue = false;

		List<WebElement> tableOfHosts = getWebElement(By.className("listingTable")).findElements(By.tagName("tbody"));
		for(WebElement elem : tableOfHosts) {
			for(WebElement element : elem.findElements(By.tagName("tr"))){
				try{
					WebElement host = element.findElement(By.tagName("a"));
					WebElement icon = element.findElement(By.tagName("span"));
					String name = host.getAttribute("innerHTML").replaceAll("<b>", "").replaceAll("</b>", "");
					if(name.equals(hostName+" ("+getLocalizedString("Default")+")") && icon.getAttribute("class").equals("hostDefaultIcon")) {
						retValue = true;
						break;
					}
				}catch(Exception e){

				}
			}
			if(retValue){
				break;
			}
		}
		return retValue;
	}	

	/**
	 * Start a inactive host
	 * @param hostName Host name
	 * @param confirm
	 * @throws Exception
	 */
	public void startHost(String hostName, boolean confirm) throws Exception {
		this.selectPopupMenuOption(hostName, getLocalizedString("Start-Host"));
		Alert alert = this.switchToAlert();
		if(confirm) {
			alert.accept();
		}
		else {
			alert.dismiss();
		}
		reload();			// TODO - remove need for this reload call
	}

	/**
	 * make default the specified host
	 * @param hostName Host name
	 * @param confirm
	 * @throws Exception
	 */
	public void makeDefultHost(String hostName, boolean confirm) throws Exception{
		sleep(2);
		this.selectPopupMenuOption(hostName, getLocalizedString("Make-Default"));
		Alert alert = this.switchToAlert();
		sleep(2);
		if(confirm) {
			alert.accept();
		}
		else {
			alert.dismiss();
		}
		reload();
	}
}
