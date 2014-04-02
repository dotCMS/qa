package com.dotcms.qa.testng.tests;

import junit.framework.Assert;

import com.dotcms.qa.selenium.util.SeleniumConfig;
import com.dotcms.qa.selenium.util.SeleniumPageManager;


import com.dotcms.qa.selenium.pages.backend.*;

import org.apache.log4j.Logger;
import org.testng.annotations.AfterGroups;
import org.testng.annotations.BeforeGroups;
import org.testng.annotations.Test;


public class HostTest {
	 private static final Logger logger = Logger.getLogger(HostTest.class);
	    
	    private SeleniumPageManager backendMgr = null;
	    //private SeleniumPageManager frontendMgr = null;
	    private String serverURL = null;
	    private ILoginPage loginPage = null;

	    @BeforeGroups (groups = {"Host"})
	    public void init() throws Exception {
	        SeleniumConfig config = SeleniumConfig.getConfig();
	        serverURL = config.getProperty("serverURL");
	        logger.info("serverURL = " + serverURL);

	        // login
	        backendMgr = RegressionSuiteEnv.getBackendPageManager();
	        loginPage = backendMgr.getPageObject(ILoginPage.class);
	        loginPage.login("admin@dotcms.com", "admin");
	        
	        // create frontendMgr for verification of frontend functionality
	        //frontendMgr = SeleniumPageManager.getNewPageManager();
	    }
	    
	    @AfterGroups (groups = {"Host"})
	    public void teardown() throws Exception {
	        // logout
	        backendMgr.logoutBackend();
	    }
	    
	   @Test (groups = {"Host"})
	    public void testCase196_AddHostManually() throws Exception {
	        IPortletMenu portletMenu = backendMgr.getPageObject(IPortletMenu.class);
	        IHostPage hostPage = portletMenu.getHostPage();
	    	
	        String hostName = "qahost01.dotcms.com";

	        // verify Host does not already exist
	       Assert.assertFalse(hostPage.doesHostExist(hostName));
	        	      
	        // add host
	        hostPage.addBlankHost(hostName);
	        Thread.sleep(5000);
	        
	        // verify it was created and listed on page
	       Assert.assertTrue(hostPage.doesHostExist(hostName));
	       
	       // TODO add code to verify host responds to traffic
	       hostPage.stopHost(hostName, true);
	       Thread.sleep(500);						// TODO - remove cluginess and be able to remove this sleep call
	       hostPage.archiveHost(hostName, true);
	       hostPage.toggleShowArchived();
	       hostPage.deleteHost(hostName, true);
	    }

	   @Test (groups = {"Host"})
	    public void testCase197_AddNewHostVariable() throws Exception {
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
	    
	   @Test (groups = {"Broken", "Host"})
	    public void testCase206_CopyHost() throws Exception {
	        IPortletMenu portletMenu = backendMgr.getPageObject(IPortletMenu.class);
	        IHostPage hostPage = portletMenu.getHostPage();
	    	
	        String hostName = "dotcms03";
	        String sentHost  ="demo.dotcms.com";
	        	        
	        // verify Host does not already exist
	       Assert.assertFalse(hostPage.doesHostExist(hostName));
	        	      
	        // add host
	        hostPage.addCopyExistingHost(hostName,sentHost);
	        Thread.sleep(5000);
	        
	        // verify it was created and listed on page
	       Assert.assertTrue(hostPage.doesHostExist(hostName));
	        	   	        
	    }	
}
