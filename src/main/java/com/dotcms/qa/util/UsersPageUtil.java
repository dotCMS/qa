package com.dotcms.qa.util;

import org.openqa.selenium.By;

import com.dotcms.qa.selenium.pages.IBasePage;
import com.dotcms.qa.selenium.util.SeleniumConfig;
import com.dotcms.qa.selenium.util.SeleniumPageManager;
import com.dotcms.qa.testng.tests.RegressionSuiteEnv;

/**
 * This class call dynamic plugin methods used in the qa tests.
 * @author Oswaldo Gallango
 *
 */
public class UsersPageUtil {


	/**
	 * Call the qa_automation plugin to delete the user from db
	 * @param userId User Identitity
	 * @return IBasePage
	 * @throws Exception
	 */
	public static IBasePage deleteUser(String userId) throws Exception{
		SeleniumConfig config = SeleniumConfig.getConfig();
		return deleteUser(userId, config.getProperty("demoServerURL"));
	}
	
	/**
	 * Call the qa_automation plugin to delete the user from db
	 * @param userId User Identitity
	 * @param serverURL  serverURL
	 * @return IBasePage
	 * @throws Exception
	 */
	public static IBasePage deleteUser(String userId, String serverURL) throws Exception{
		SeleniumConfig config = SeleniumConfig.getConfig();
		SeleniumPageManager frontendMgr = RegressionSuiteEnv.getFrontendPageManager();
		IBasePage page = frontendMgr.loadPage(serverURL + "api/qa_automation/user/delete/userId/"+userId+"/user/"+config.getProperty("backend.user.Email")+"/password/"+config.getProperty("backend.user.Password"));
		return page;
	}

	/**
	 * Call the qa_automation plugin to delete tags from db
	 * @param tag Tag name
	 * @return IBasePage
	 * @throws Exception
	 */
	public static IBasePage deleteTag(String tag) throws Exception{
		SeleniumConfig config = SeleniumConfig.getConfig();
		return deleteTag(tag, config.getProperty("demoServerURL"));
	}
	
	/**
	 * Call the qa_automation plugin to delete tags from db
	 * @param tag Tag name
	 * @param serverURL
	 * @return IBasePage
	 * @throws Exception
	 */
	public static IBasePage deleteTag(String tag, String serverURL) throws Exception{
		SeleniumConfig config = SeleniumConfig.getConfig();
		SeleniumPageManager frontendMgr = RegressionSuiteEnv.getFrontendPageManager();
		IBasePage page = frontendMgr.loadPage(serverURL+ "api/qa_automation/tag/delete/tagname/"+tag+"/user/"+config.getProperty("backend.user.Email")+"/password/"+config.getProperty("backend.user.Password"));
		return page;
	}
	
	/**
	 * Log the user in the specified urlpath
	 * @param urlPath Path to login page
	 * @param login  User login
	 * @param password User Password
	 * @return IBasePage
	 * @throws Exception
	 */
	public static IBasePage frontEndLogin(String urlPath, String login,String password) throws Exception{
		SeleniumPageManager frontendMgr = RegressionSuiteEnv.getFrontendPageManager();
		IBasePage page = frontendMgr.loadPage(urlPath);
		page.getWebElementPresent(By.id("macro-login-user-name")).clear();
		page.getWebElementPresent(By.id("macro-login-user-name")).sendKeys(login);
		page.getWebElementPresent(By.id("macro-login-password")).clear();
		page.getWebElementPresent(By.id("macro-login-password")).sendKeys(password);
		page.getWebElementPresent(By.id("macro-login-button")).click();
		return page;
	}
}
