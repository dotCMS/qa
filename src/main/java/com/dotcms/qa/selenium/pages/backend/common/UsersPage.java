package com.dotcms.qa.selenium.pages.backend.common;

import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.dotcms.qa.selenium.pages.backend.IUsersPage;
import com.dotcms.qa.selenium.pages.common.BasePage;
import com.dotcms.qa.selenium.util.SeleniumPageManager;

public class UsersPage extends BasePage implements IUsersPage {
    private static final Logger logger = Logger.getLogger(VanityURLsPage.class);

    /**
     * User Filter input
     */
    private WebElement usersFilter;
    
    
	public UsersPage(WebDriver driver) {
		super(driver);
	}
	
	/**
	 * Search if a user exist in the user manager page
	 * @param email User Email
	 * @return boolean, true if the user exist, false if not.
	 */
	public boolean searchUserByEmail(String email) {
		boolean retValue = false;
		usersFilter.clear();
		usersFilter.sendKeys(email);
		sleep();
		List<WebElement> rows = getWebElementPresent(By.id("dijit_layout_ContentPane_2")).findElements(By.tagName("td"));
		for(WebElement row : rows) {
			try {
				if(row.getText().trim().equals(email)) {
					retValue = true;
					break;
				}
			}catch(NoSuchElementException e) {
				logger.trace("Row does not include td element", e);
				// Move on to next row and keep going
			}catch(Exception e) {
				logger.error("Unexpected error attempting to iterate over Users - email=" + email, e);
				// Move on to next row and keep going
			}
		}
		return retValue;		
	}
	
	 /**
     * Sleep method
     */
    public void sleep() {
        try{
        	Thread.sleep(200);
        }catch(Exception e){
        	logger.error(e);
        }
    }
}
