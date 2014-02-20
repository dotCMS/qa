package com.dotcms.qa.testng.tests;

import java.net.*;
import java.util.*;

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

public class StructureTests {
    private static final Logger logger = Logger.getLogger(StructureTests.class);
    
    private SeleniumPageManager backendMgr = null;
    private SeleniumPageManager frontendMgr = null;
    private String demoServerURL = null;
    private String mobileServerURL = null;
    private String sharedServerURL = null;
    private ILoginPage loginPage = null;

    @BeforeGroups (groups = {"Structures"})
    public void init() throws Exception {
        SeleniumConfig config = SeleniumConfig.getConfig();
        demoServerURL = config.getProperty("demoServerURL");
        mobileServerURL = config.getProperty("mobileServerURL");
        sharedServerURL = config.getProperty("sharedServerURL");

        // login
        backendMgr = RegressionSuiteEnv.getBackendPageManager();
        loginPage = backendMgr.getPageObject(ILoginPage.class);
        loginPage.login("admin@dotcms.com", "admin");
        
        // create frontendMgr for verification of frontend functionality
        frontendMgr = RegressionSuiteEnv.getFrontendPageManager();

    }

    @AfterGroups (groups = {"Structures"})
    public void teardown() throws Exception {
        // logout
        backendMgr.logoutBackend();
    }

    @Test (groups = {"Structures"})
    public void testCase630_AddStructureWithEveryFieldType() throws Exception {
    	IPortletMenu portletMenu = backendMgr.getPageObject(IPortletMenu.class);
        IStructuresPage structuresPage = portletMenu.getStructuresPage();
    	
        
    }
}
