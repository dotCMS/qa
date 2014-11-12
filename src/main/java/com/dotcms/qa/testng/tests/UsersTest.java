package com.dotcms.qa.testng.tests;

import java.net.URL;

import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.AfterGroups;
import org.testng.annotations.BeforeGroups;
import org.testng.annotations.Test;

import com.dotcms.qa.selenium.pages.IBasePage;
import com.dotcms.qa.selenium.pages.backend.ILoginPage;
import com.dotcms.qa.selenium.pages.backend.IPortletMenu;
import com.dotcms.qa.selenium.pages.backend.IUsersPage;
import com.dotcms.qa.selenium.util.SeleniumConfig;
import com.dotcms.qa.selenium.util.SeleniumPageManager;

/**
 * This class manage the TestRail suite of test for Users
 * @author Oswaldo Gallango
 * @since 11/12/2014
 * @version 1.0
 */
public class UsersTest {
	
	//Logger
	private static final Logger logger = Logger.getLogger(UsersTest.class);
    
	//BackEnd and FrontEnd managers
    private SeleniumPageManager backendMgr = null;
    private SeleniumPageManager frontendMgr = null;
    //Sites Info
    private String demoServerURL = null;
    private String mobileServerURL = null;
    private String sharedServerURL = null;
    private ILoginPage loginPage = null;
    //Backend User Info
    private String backendUserEmail = null;
    private String backendUserPassword = null;

    /**
     * Initialize variables and login the user to the backend
     * @throws Exception
     */
    @BeforeGroups (groups = {"Users"})
    public void init() throws Exception {
    	logger.info("**UsersTests.init() beginning**");
        SeleniumConfig config = SeleniumConfig.getConfig();
        demoServerURL = config.getProperty("demoServerURL");
        mobileServerURL = config.getProperty("mobileServerURL");
        sharedServerURL = config.getProperty("sharedServerURL");

        // Backend login
        backendUserEmail = config.getProperty("backend.user.Email");
        backendUserPassword = config.getProperty("backend.user.Password");
        backendMgr = RegressionSuiteEnv.getBackendPageManager();
        loginPage = backendMgr.getPageObject(ILoginPage.class);
        loginPage.login(backendUserEmail, backendUserPassword);
    }
    
    /**
     * Logout User from backEnd
     * @throws Exception
     */
    @AfterGroups (groups = {"Users"})
    public void teardown() throws Exception {
    	logger.info("**UsersTests.teardown() beginning**");
        // logout
        backendMgr.logoutBackend();
    	logger.info("**UsersTests.teardown() ending**");
    }

    /**
     * Sleep method
     */
    public void sleep() {
        try{
        	Thread.sleep(500);
        }catch(Exception e){
        	logger.error(e);
        }
    }
    /**
     * Validate the search by user test case. Set here:
     * http://qa.dotcms.com/index.php?/cases/view/257
     * @throws Exception
     */
    @Test (groups = {"Users"})
    public void tc257_SearchUserByEmailAddress() throws Exception {
        IPortletMenu portletMenu = backendMgr.getPageObject(IPortletMenu.class);
        IUsersPage usersPage = portletMenu.getUsersPage();
    	
        //Verify an existing user
        Assert.assertTrue(usersPage.searchUserByEmail(backendUserEmail));
        
        //Verify a non existing user
        String fakeEmail ="fake@dotcms.com";
        Assert.assertFalse(usersPage.searchUserByEmail(fakeEmail));     
    }
    
}
