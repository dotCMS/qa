package com.dotcms.qa.selenium.pages.backend.common;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.dotcms.qa.selenium.pages.backend.IMailingListPage;
import com.dotcms.qa.selenium.pages.common.BasePage;

/**
 * This class implements the methods defined in the IMailingListPage interface
 * to do the Users TestRail validations
 * @author Oswaldo Gallango
 * @since 11/24/2014
 * @version 1.0
 * 
 */
public class MailingListPage  extends BasePage implements IMailingListPage {

	private static final Logger logger = Logger.getLogger(MailingListPage.class);

	/**
	 * Sleep method
	 */
	public void sleep() {
		try{
			Thread.sleep(1000);
		}catch(Exception e){
			logger.error(e);
		}
	}
	
	private WebDriver mydriver;
	public MailingListPage(WebDriver driver) {
		super(driver);
		mydriver=driver;
	}

	/**
	 * combo box
	 */
	private WebElement  comboCreate_arrow;
	
	/**
	 * combobox options
	 */
	private WebElement createMenu;
	
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
	
	/**
	 * Import a csv user file in dotcms
	 * @param filePath locstion of the csv file
	 * @return true if the user where imported, false if not
	 */
	public boolean loadUsers(String mailingListName, String filePath) {
		boolean retValue=false;
		comboCreate_arrow.click();
		sleep();
		List<WebElement> options = createMenu.findElements(By.tagName("td"));
		for(WebElement option : options){
			if(option.getText().equals(getLocalizedString("Load-Users"))){
				option.click();
				sleep();
				//set mailing list title
				usermanagerListTitle.sendKeys(mailingListName);
				//upload the csv users file
				File file = new File(filePath);
				mydriver.findElement(By.cssSelector("input[type='file'][name='_EXT_16_newUsersFile']")).sendKeys(file.getAbsolutePath());
				loadButton_label.click();
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
					if(getBrowserNameAndVersion().indexOf("firefox") != -1){
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
