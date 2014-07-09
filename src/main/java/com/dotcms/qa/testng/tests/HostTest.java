package com.dotcms.qa.testng.tests;


import com.dotcms.qa.selenium.util.SeleniumConfig;
import com.dotcms.qa.selenium.util.SeleniumPageManager;

import com.dotcms.qa.selenium.pages.IBasePage;
import com.dotcms.qa.selenium.pages.backend.*;

import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.AfterGroups;
import org.testng.annotations.BeforeGroups;
import org.testng.annotations.Test;


public class HostTest {
	 private static final Logger logger = Logger.getLogger(HostTest.class);
	    
	    private SeleniumPageManager backendMgr = null;
	    private SeleniumPageManager frontendMgr = null;
	    private String demoServerURL = null;
	    private ILoginPage loginPage = null;

	    @BeforeGroups (groups = {"Host"})
	    public void init() throws Exception {
	        SeleniumConfig config = SeleniumConfig.getConfig();
	        demoServerURL = config.getProperty("demoServerURL");
	        logger.info("demoServerURL = " + demoServerURL);

	        // create frontendMgr for verification of frontend functionality
	        frontendMgr = RegressionSuiteEnv.getFrontendPageManager();

	        // login
	        backendMgr = RegressionSuiteEnv.getBackendPageManager();
	        loginPage = backendMgr.getPageObject(ILoginPage.class);
	        loginPage.login("admin@dotcms.com", "admin");	        
	    }
	    
	    @AfterGroups (groups = {"Host"})
	    public void teardown() throws Exception {
	        // logout
	        backendMgr.logoutBackend();
	    }
	    
	   @Test (groups = {"Host"})
	    public void tc196_AddHostManually() throws Exception  {
	       IPortletMenu portletMenu = backendMgr.getPageObject(IPortletMenu.class);
	       IHostPage hostPage = portletMenu.getHostPage();
	    	
	       String hostName = "qahost01.dotcms.com";

	        // verify Host does not already exist
	       Assert.assertFalse(hostPage.doesHostExist(hostName));
	        	      
	        // add host
	       hostPage.addBlankHost(hostName);
	       hostPage.sleep(5000);
	        
	        // verify it was created and listed on page
	       Assert.assertTrue(hostPage.doesHostExist(hostName));
	       
	       // verify new host responds to traffic
	       IBasePage homePage = frontendMgr.loadPage("http://" + hostName + ":8080/");
	       String title = homePage.getTitle();
	       Assert.assertTrue(title != null && title.startsWith("dotCMS: Page not found"));
	       
	       hostPage.stopHost(hostName, true);
	       hostPage.sleep(500);						// TODO - remove cluginess and be able to remove this sleep call
	       hostPage.archiveHost(hostName, true);
	       hostPage.toggleShowArchived();
	       hostPage.deleteHost(hostName, true);
	       
	       // verify host is no longer listed on page
	       hostPage.reload();
	       Assert.assertFalse(hostPage.doesHostExist(hostName));
	       hostPage.toggleShowArchived();
	       Assert.assertFalse(hostPage.doesHostExist(hostName));

	       // verify host is no longer responding to requests
	       IBasePage demoHomePage = frontendMgr.loadPage(demoServerURL);
	       String demoHomePageTitle = demoHomePage.getTitle();
	       homePage = frontendMgr.loadPage("http://" + hostName + ":8080/");
	       title = homePage.getTitle();
	       Assert.assertTrue(demoHomePageTitle != null && title != null && demoHomePageTitle.equals(title), "Page titles do not match.  demoHomePageTitle=|" + demoHomePageTitle + "| title = |" + title + "|");
	    }

	   @Test (groups = {"Host"})
	    public void tc197_AddNewHostVariable() throws Exception {
	        IPortletMenu portletMenu = backendMgr.getPageObject(IPortletMenu.class);
	        IHostPage hostPage = portletMenu.getHostPage();
	    	
	        String hostName = "qashared";
	        String hostVariableName = "var1";
	        String hostVariableKey = "key1";
	        String hostVariableValue = "value1";

	        Assert.assertFalse(hostPage.doesHostVariableExist(hostName, hostVariableName));
	        hostPage.addHostVariable(hostName, hostVariableName, hostVariableKey, hostVariableValue);
	        Assert.assertTrue(hostPage.doesHostVariableExist(hostName, hostVariableName));
	        hostPage = backendMgr.getPageObject(IHostPage.class);
	        hostPage.deleteHostVariable(hostName, hostVariableName, true);
	        Assert.assertFalse(hostPage.doesHostVariableExist(hostName, hostVariableName));
	   }
	    
	   @Test (groups = {"Host"})
	    public void tc206_CopyHost() throws Exception {
	        IPortletMenu portletMenu = backendMgr.getPageObject(IPortletMenu.class);
	        IHostPage hostPage = portletMenu.getHostPage();
	    	
        	String hostName = "qahost01.dotcms.com";
	        String hostToCopy  ="m.qademo.dotcms.com";
	        	        
	        // verify Host does not already exist
	        Assert.assertFalse(hostPage.doesHostExist(hostName));
	        	      
	        // copy host
	        hostPage.addCopyExistingHost(hostName, hostToCopy);
	        hostPage.sleep(10000);
	        
	        // verify it was created and listed on page
	       Assert.assertTrue(hostPage.doesHostExist(hostName));
	        	   	        
	       // verify new host responds to traffic
	       IBasePage homePage = frontendMgr.loadPage("http://" + hostName + ":8080/");
	       String title = homePage.getTitle();
	       Assert.assertTrue(title != null && title.startsWith("Quest Financial"));
	       
	       // delete host
	       hostPage.stopHost(hostName, true);
	       hostPage.sleep(500);
	       hostPage.archiveHost(hostName, true);
	       hostPage.toggleShowArchived();
	       hostPage.deleteHost(hostName, true);

	       hostPage.sleep(5000);
	       // verify it is no longer listed on page
	       Assert.assertFalse(hostPage.doesHostExist(hostName));
	       
	       // verify host is no longer responding to requests
	       IBasePage demoHomePage = frontendMgr.loadPage(demoServerURL);
	       String demoHomePageTitle = demoHomePage.getTitle();
	       homePage = frontendMgr.loadPage("http://" + hostName + ":8080/");
	       title = homePage.getTitle();
	       Assert.assertTrue(demoHomePageTitle != null && title != null && demoHomePageTitle.equals(title), "Page titles do not match.  demoHomePageTitle=|" + demoHomePageTitle + "| title = |" + title + "|");
	   }	
}
