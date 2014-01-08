package com.dotcms.qa.testng.tests;

import java.util.Locale;
import java.util.Set;

import com.dotcms.qa.selenium.util.SeleniumConfig;
import com.dotcms.qa.selenium.util.SeleniumPageManager;

import com.dotcms.qa.selenium.pages.IBasePage;
import com.dotcms.qa.selenium.pages.backend.*;

import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterGroups;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeGroups;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;


public class HostTest {
	 private static final Logger logger = Logger.getLogger(HostTest.class);
	    
	    private SeleniumPageManager backendMgr = null;
	    private SeleniumPageManager frontendMgr = null;
	    private String serverURL = null;
	    private ILoginPage loginPage = null;

	    @BeforeGroups (groups = {"Host"})
	    public void init() throws Exception {
	        logger.info("Locale = " + Locale.getDefault());
	        logger.info("file.encoding = " +System.getProperty("file.encoding"));
	        SeleniumConfig config = SeleniumConfig.getConfig();
	        serverURL = config.getProperty("serverURL");
	        logger.info("serverURL = " + serverURL);

	        logger.trace("**************************");
	        Set<String> keys = System.getProperties().stringPropertyNames();
	        for(String key : keys) {
	            logger.trace(key + "=" + System.getProperty(key));
	        }       
	        logger.trace("**************************");

	        // login
	        backendMgr = SeleniumPageManager.getPageManager();
	        backendMgr.loadPage(serverURL + "admin");
	        loginPage = backendMgr.getPageObject(ILoginPage.class);
	        loginPage.login("admin@dotcms.com", "admin");
	        
	        // create frontendMgr for verification of frontend functionality
	        frontendMgr = SeleniumPageManager.getNewPageManager();
	    }
	    
	    @AfterGroups (groups = {"Host"})
	    public void teardown() throws Exception {
	        // logout
	        backendMgr.loadPage(serverURL + "c/portal/logout?referer=/c");
	    	loginPage = backendMgr.getPageObject(ILoginPage.class);
	        
	        // shutdown
	        logger.info("Shutting Down....");
	        backendMgr.shutdown();

	        frontendMgr.shutdown();
	    }
	    
	    @Test (groups = {"Host"})
	    public void testCase_CreateBlankHost() throws Exception {
	        IPortletMenu portletMenu = backendMgr.getPageObject(IPortletMenu.class);
	        IHostPage hostPage = portletMenu.getHostPage();
	    	
	        String BHostName = "dotcms";
	        	        
	        // verify Host does not already exist
	        Assert.assertFalse(hostPage.doesHostExist(BHostName));
	        	      
	        // add host
	        hostPage.addBlankHost(BHostName);

	        // verify it was created and listed on page
	       Assert.assertTrue(hostPage.doesHostExist(BHostName));
	        	      
	        // delete host
	       hostPage.deleteHost(BHostName);

	        // verify it is no longer listed
	       Assert.assertFalse(hostPage.doesHostExist(BHostName));
	        
	    }
	    
	    @Test (groups = {"Host"})
	    public void testCase_CreateCopyExistingHost() throws Exception {
	        IPortletMenu portletMenu = backendMgr.getPageObject(IPortletMenu.class);
	        IHostPage hostPage = portletMenu.getHostPage();
	    	
	        String BHostName = "democopy001";
	        String host = "demo.dotcms.com";
	        	        
	        // verify Host does not already exist
	        Assert.assertFalse(hostPage.doesHostExist(BHostName));
	        	      
	        // add host
	        hostPage.addCopyExistingHost(BHostName, host);

	        // verify it was created and listed on page
	        //Assert.assertTrue(hostPage.doesHostExist(BHostName));
	        	      
	        // delete host
	        hostPage.deleteHost(BHostName);

	        // verify it is no longer listed
	        Assert.assertFalse(hostPage.doesHostExist(BHostName));
	        
	    }
}
