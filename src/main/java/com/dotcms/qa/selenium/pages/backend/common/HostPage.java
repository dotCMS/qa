package com.dotcms.qa.selenium.pages.backend.common;

import java.io.File;
import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.dotcms.qa.selenium.pages.backend.*;
import com.dotcms.qa.selenium.pages.common.BasePage;
import com.dotcms.qa.selenium.util.SeleniumPageManager;
import com.dotcms.qa.util.Evaluator;
import com.dotcms.qa.util.WebKeys;

public class HostPage extends BasePage implements IHostPage  {
	private static final Logger logger = Logger.getLogger(HostPage.class);

	private String newHostName,deleteHostName;
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
		sleep(2);
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

	/**
	 * Create a new host by copying an existing one. 
	 * Checking every second for up to 100 minutes to see if the host was copied
	 * @param hostName - name of tne new host
	 * @param hostToCopy - name of the host to be copied
	 * @return true if the host was copied, false if not
	 * @throws Exception 
	 */
	public boolean addCopyExistingHost(String hostName, String hostToCopy) throws Exception {
		boolean retValue = addCopyExistingHost(hostName, hostToCopy, 1000, 6000); // Poll every second for up to 100 minutes
//		sleep(10);
		return retValue;
	}
	
	/**
	 * Create a new host by copying an existing one.This method allows to specify the time to wait to check if the host was copied
	 * @param hostName - name of tne new host
	 * @param hostToCopy - name of the host to be copied
	 * @param pollInterval - how many milliseconds to wait between polling
	 * @param maxPollCount - maximum number of times to poll before returning value of eval.evaluate()
	 * @return true if the host was copied, false if not
	 * @throws Exception
	 */
	public boolean addCopyExistingHost(String hostName, String hostToCopy, long pollInterval, int maxPollCount) throws Exception {
		addSiteButton.click();
		IHostCreateNewSiteDialog createNewSiteDlg = SeleniumPageManager.getBackEndPageManager().getPageObject(IHostCreateNewSiteDialog.class);
		createNewSiteDlg.addCopyExistingHost(hostName, hostToCopy);
		//Wait until the host is created using evaluator pattern
		newHostName = hostName;
		Evaluator eval = new Evaluator() {
			public boolean evaluate() throws Exception {  // returns true if host copy is done
				return !isHostCopyInProgress(newHostName);
			}
			
		};
		return pollForValue(eval, true, pollInterval, maxPollCount);
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

	/**
	 * Delete a host.Checking every 2 second for up to 2 minutes to see if the host was deleted
	 * @param hostName - name of tne new host
	 * @param confirm - accept or refuse the confirmation popup
	 * @return true if the host was deleted, false if not
	 * @throws Exception
	 */
	public boolean deleteHost(String hostName, boolean confirm) throws Exception {
		return deleteHost(hostName, confirm,  5000, 1200);	// Check every 5 seconds for up to 100 minutes
	}
	
	/**
	 * Delete a host.This method allows to specify the time to wait to check if the host was deleted
	 * @param hostName - name of tne new host
	 * @param confirm - accept or refuse the confirmation popup
	 * @param pollInterval - how many milliseconds to wait between polling
	 * @param maxPollCount - maximum number of times to poll before returning value of eval.evaluate()
	 * @return true if the host was deleted, false if not
	 * @throws Exception
	 */
	public boolean deleteHost(String hostName, boolean confirm, long pollInterval, int maxPollCount) throws Exception {
		this.selectPopupMenuOption(hostName, getLocalizedString("Delete-Host"));
		Alert alert = this.switchToAlert();
		if(confirm) {
			alert.accept();
		}
		else {
			alert.dismiss();
		}
		reload();			// TODO - remove need for this reload call
		//Wait until the host is deleted using evaluator pattern
		deleteHostName = hostName;
		Evaluator eval = new Evaluator() {
			public boolean evaluate() throws Exception {  // returns false if host exists
				reload();
				toggleShowArchived();
				return !doesHostExist(deleteHostName);
			}
		};
		return pollForValue(eval, true, pollInterval, maxPollCount);
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
		List<WebElement> labels = getWebElements(By.cssSelector(".dijitMenuPopup tr .dijitMenuItemLabel"));
		for(WebElement label : labels) {
			logger.debug("label innerHTML = |" + label.getAttribute("innerHTML") + "|");
			if(label.getAttribute("innerHTML").trim().startsWith(menuOption)) {
				this.hoverOverElement(label);
				getWebElementClickable(label).click();
				foundValue = true;
				break;
			}
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
	 * WARNING This test doesn't work in chrome
	 * Add a host thumbnail into the specified host. 
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
		File file = new File(path+"/artifacts/testdata/dotcms_logo.png");
		WebElement input = getWebElement(By.cssSelector("input[type='file'][name='binary1FileUpload']"));
		if(getBrowserName().equals(WebKeys.CHROME_BROWSER_NAME)){
			/*For some unknown reason this throws a render time out exception  */
			input.sendKeys(file.getAbsolutePath());
		}else{
			input.sendKeys(file.getAbsolutePath());
		}		

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
					logger.debug("Unknown exception in isHostActive()", e);
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
					logger.debug("Unknown exception in isHostDefault()", e);
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
	public void makeDefaultHost(String hostName, boolean confirm) throws Exception{
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

	public boolean isHostCopyInProgress(String hostName) throws Exception {
		boolean copyInProgress = true;
		WebElement progressBar = null;
		WebElement tableOfHost = getWebElement(By.className("listingTable"));
		for(WebElement tr : tableOfHost.findElements(By.className("alternate_1"))) {
			WebElement anchor = tr.findElement(By.tagName("a"));
			String host = anchor.getAttribute("innerHTML").replaceAll("<b>", "").replaceAll("</b>", "");
			if(host.startsWith(hostName)) {
				progressBar = tr.findElement(By.className("dijitProgressBar"));
				if(progressBar != null) {
					logger.trace("progressBar.getAttribute('style') = " + progressBar.getAttribute("style"));
					String style = progressBar.getAttribute("style").trim();
					if(style.contains("display: none;"))
						copyInProgress = false;
				}
				else	// if no progress bar element then page must have been refreshed and there is no copy in progress
					copyInProgress = false;
				break;
			}
		}
		return copyInProgress;
	}
	
	/**
	 * Click the push publish option from the right click menu options
	 * @param hostName Host Name
	 * @throws Exception
	 */
	public void pushHost(String hostName) throws Exception{
		this.selectPopupMenuOption(hostName, getLocalizedString("Remote-Publish"));
		sleep(2);
		IPushPublishDialogPage pushingDialog = SeleniumPageManager.getBackEndPageManager().getPageObject(IPushPublishDialogPage.class);
		pushingDialog.push(WebKeys.PUSH_TO_ADD, null, null, null, null, false);
	}
	
	/**
	 * Click the push publish option from the right click menu options
	 * @param hostName Host name
	 * @param pushType   Type of push to apply (PUSH_TO_REMOVE,PUSH_TO_ADD,PUSH_AND_REMOVE)
	 * @param pushDate   Date with format M/d/yyyy(optional)
	 * @param pushTime   Time format H:mm a (optional)
	 * @param expireDate Date with format M/d/yyyy(optional)
	 * @param expireTime Time format H:mm a (optional)
	 * @param force      Force to publish (Only valid for push and push a remove)
	 * @throws Exception
	 */
	public void pushContent(String hostName, String pushType, String pushDate, String pushTime, String expireDate, String expireTime, boolean force) throws Exception{
		this.selectPopupMenuOption(hostName, getLocalizedString("Remote-Publish"));
		sleep(2);
		IPushPublishDialogPage pushingDialog = SeleniumPageManager.getBackEndPageManager().getPageObject(IPushPublishDialogPage.class);
		pushingDialog.push(pushType, pushDate, pushTime, expireDate, expireTime, force);
	}
}
