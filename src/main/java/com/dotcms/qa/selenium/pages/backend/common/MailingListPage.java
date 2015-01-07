package com.dotcms.qa.selenium.pages.backend.common;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.dotcms.qa.selenium.pages.backend.IMailingListPage;
import com.dotcms.qa.selenium.pages.common.BasePage;
import com.dotcms.qa.util.WebKeys;

/**
 * This class implements the methods defined in the IMailingListPage interface
 * to do the Users TestRail validations
 * @author Oswaldo Gallango
 * @since 11/24/2014
 * @version 1.0
 * 
 */
public class MailingListPage  extends BasePage implements IMailingListPage {


	/*
	 * Users file path
	 */
	private final String usersFilePath="/src/main/resources/users.csv";
	
	private static final Logger logger = Logger.getLogger(MailingListPage.class);

	public MailingListPage(WebDriver driver) {
		super(driver);
	}

	/**
	 * combo box
	 */
	//private WebElement  comboCreate_arrow;
	
	/**
	 * combobox options
	 */
	//private WebElement createMenu;
	
	/**
	 * mailing list name
	 */
	private WebElement usermanagerListTitle;
	
	/**
	 * load button 
	 */
	private WebElement loadButton_label;
	
	/**
	 * Mailing list form
	 */
	private WebElement fm;
	
	/**
	 * Mailing list subscriber table
	 */
	private WebElement subscribersTable;
	
	private WebElement _EXT_16_newUsersFile;
	
	/**
	 * Import a csv user file in dotcms
	 * @param filePath locstion of the csv file
	 * @return true if the user where imported, false if not
	 */
	public boolean loadUsers(String mailingListName) {
		boolean retValue=false;
		sleep();
		getWebElementPresent(By.id("comboCreate_arrow")).click();
		sleep();
		List<WebElement> options = getWebElementPresent(By.id("comboCreate_dropdown")).findElements(By.tagName("td"));
		for(WebElement option : options){
			if(option.getText().equals(getLocalizedString("Load-Users"))){
				option.click();
				sleep();
				//upload the csv users file
				String path = System.getProperty("user.dir");
				File file = new File(path+usersFilePath);
				if(getBrowserName().equals(WebKeys.SAFARI_BROWSER_NAME)){
					WebElement elem = getWebElement(By.xpath("//input[@type='file']"));
					elem.sendKeys(file.getAbsolutePath());
				}else{
					getWebElement(By.cssSelector("input[type='file'][name='_EXT_16_newUsersFile']")).sendKeys(file.getAbsolutePath());
				}
				sleep();
				//set mailing list title
				if(getBrowserName().equals(WebKeys.CHROME_BROWSER_NAME)){
					//chrome have issue with some web elements in that cases we use javascript calls
					executeJavaScript("document.getElementById('usermanagerListTitle').value='"+mailingListName+"'");
					executeJavaScript("document.getElementById('loadButton_label').click()");
				}else{
					usermanagerListTitle.sendKeys(mailingListName);
					loadButton_label.click();
				}
				sleep();
				retValue=true;
				break;
			}
		}
		return retValue;
	}
	
	/**
	 * Get the list of email addresses of the user subscribed to the mailing list
	 * @param mailingList Mailing List name
	 * @return List<String> with the susbcribers emails
	 */
	public List<String> getMailingListSubscribers(String mailingList){
		List<String> results = new ArrayList<String>();
		//form with the mailing list
		boolean found =false;
		List<WebElement> values = fm.findElements(By.tagName("tr"));
		for(WebElement row : values){
			List<WebElement> columns = row.findElements(By.tagName("a"));
			for(WebElement column : columns){
				if(column.getText().equals(mailingList)){
					column.click();
					sleep();
					//load the mailing list
					List<WebElement> elements = subscribersTable.findElements(By.tagName("td"));
					for(WebElement elem: elements){
						//get the users emails
						if(elem.getText().indexOf("@") != -1){
							results.add(elem.getText().trim());
						}
					}
					found=true;
				}
			}
			if(found){
				break;
			}
		}
		return results;
	}

	/**
	 * Delete the  mailing list
	 * @param mailingList Mailing List name
	 * @return true if the list was delete, false if not
	 */
	public boolean deleteMailingList(String mailingList){
		boolean retValue=false;
		//form with the mailing list
		List<WebElement> values = fm.findElements(By.tagName("tr"));
		for(WebElement row : values){
			List<WebElement> columns = row.findElements(By.tagName("td"));
			for(WebElement column : columns){
				if(column.getText().equals(mailingList)){
					WebElement checkbox = row.findElement(By.cssSelector("input[type='checkbox']"));
					if(getBrowserName().equals(WebKeys.FIREFOX_BROWSER_NAME)){
						checkbox.sendKeys(Keys.SPACE);
					}else{
						checkbox.click();
					}
					sleep();
					getDeleteListButton().click();
					sleep();
					this.switchToAlert().accept();
					retValue=true;
					break;
				}
			}
			if(retValue){
				break;
			}
		}
		return retValue;
	}
	
	/**
	 * Search the delete mailing list button dynamically, because doesn't have a fixed id and
	 * dojo change it some times
	 */
	private WebElement getDeleteListButton(){
		WebElement deleteListButton=null;
		List<WebElement> buttonsArea = fm.findElements(By.cssSelector("span[class='dijitReset dijitInline dijitButtonText']"));
		for(WebElement span : buttonsArea){
			if(span.getText().equals(getLocalizedString("Delete-Selected"))){
				deleteListButton = span;
				break;
			}
		}
		return deleteListButton;
	}
}
