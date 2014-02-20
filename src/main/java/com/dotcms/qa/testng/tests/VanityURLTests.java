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

public class VanityURLTests {
    private static final Logger logger = Logger.getLogger(VanityURLTests.class);
    
    private SeleniumPageManager backendMgr = null;
    private SeleniumPageManager frontendMgr = null;
    private String demoServerURL = null;
    private String mobileServerURL = null;
    private String sharedServerURL = null;
    private ILoginPage loginPage = null;

    @BeforeGroups (groups = {"VanityURLs"})
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
    
    @AfterGroups (groups = {"VanityURLs"})
    public void teardown() throws Exception {
        // logout
        backendMgr.logoutBackend();
    }
    
    @Test (groups = {"VanityURLs"})
    public void testCase383_CreateVanityURL() throws Exception {
        IPortletMenu portletMenu = backendMgr.getPageObject(IPortletMenu.class);
        IVanityURLsPage vanityURLPage = portletMenu.getVanityURLsPage();
    	
        String vurl383Title = "383 Vanity URL";
        String vurl383URL = "383";
        String targetHost = new URL(demoServerURL).getHost();
        
        vanityURLPage.selectBackendHost(targetHost);
        
        // verify vanity URL does not already exist
        Assert.assertFalse(vanityURLPage.doesVanityURLExist(vurl383Title));
        
        // verify it does not already work
        IBasePage page = frontendMgr.loadPage(demoServerURL + vurl383URL);
        String title = page.getTitle();
        Assert.assertTrue(title.contains("404"), "ERROR - Mapping for vanity URL already exists:  " + vurl383URL);

        // add vanity URL
        vanityURLPage.addVanityURLToHost(vurl383Title, targetHost, vurl383URL, "/about-us/our-team/index.html");

        // verify it was created and listed on page
        Assert.assertTrue(vanityURLPage.doesVanityURLExist(vurl383Title));
        
        // verify that vanity url works
        page = frontendMgr.loadPage(demoServerURL + vurl383URL);
        title = page.getTitle();
        logger.info("title = " + title);
        Assert.assertTrue(title.equals("Our Team - Quest Financial"), "ERROR - Mapping for vanity URL does not work:  " + vurl383URL);

        // delete vanity URL
        vanityURLPage.deleteVanityURL(vurl383Title);

        // verify it is no longer listed
        Assert.assertFalse(vanityURLPage.doesVanityURLExist(vurl383Title));
        
        // verify it no longer works
        page = frontendMgr.loadPage(demoServerURL + vurl383URL);
        title = page.getTitle();
        Assert.assertTrue(title.contains("404"), "ERROR - Mapping still seems to exist for URL:  " + vurl383URL);
    }

    @Test (groups = {"VanityURLs"})
    public void testCase384_EditVanityURL() throws Exception{
        IPortletMenu portletMenu = backendMgr.getPageObject(IPortletMenu.class);
        IVanityURLsPage vanityURLPage = portletMenu.getVanityURLsPage();

        String vurl384Title_org = "384 Vanity URL";
        String vurl384URL_org = "384";
        String vurl384Title_new = "384 Vanity NEW URL";
        String vurl384URL_new = "384New";
        String targetHost = new URL(demoServerURL).getHost();
        
        vanityURLPage.selectBackendHost(targetHost);
        
        // verify neither vanity URL currently exists
        Assert.assertFalse(vanityURLPage.doesVanityURLExist(vurl384Title_org));
        Assert.assertFalse(vanityURLPage.doesVanityURLExist(vurl384Title_new));

        // verify neither vanity URL currently works
        IBasePage page = frontendMgr.loadPage(demoServerURL + vurl384URL_org);
        String title = page.getTitle();
        Assert.assertTrue(title.contains("404"), "ERROR - Mapping for vanity URL already exists:  " + vurl384URL_org);
        page = frontendMgr.loadPage(demoServerURL + vurl384URL_new);
        title = page.getTitle();
        Assert.assertTrue(title.contains("404"), "ERROR - Mapping for vanity URL already exists:  " + vurl384URL_new);
        
        // create new vanity URL and verify it exists and is working
        vanityURLPage.addVanityURLToHost(vurl384Title_org, targetHost, vurl384URL_org, "products/");
//        vanityURLPage.addVanityURLToHost(vurl384Title_org, targetHost, vurl384URL_org, "products/index.html");

        // verify it was created and listed on page
        Assert.assertTrue(vanityURLPage.doesVanityURLExist(vurl384Title_org));
        
        // verify that vanity url works
        page = frontendMgr.loadPage(demoServerURL + vurl384URL_org);
        title = page.getTitle();
        logger.info("title = " + title);
        Assert.assertTrue(title.equals("Products - Quest Financial"), "ERROR - Mapping for vanity URL does not work:  " + vurl384URL_org);
        
        // edit vanity url
        vanityURLPage.editVanityURL(vurl384Title_org, vurl384Title_new, vurl384URL_new, "/services/market-research/");

        // verify new vanity url is listed and old one is not
        Assert.assertTrue(vanityURLPage.doesVanityURLExist(vurl384Title_new));
        Assert.assertFalse(vanityURLPage.doesVanityURLExist(vurl384Title_org));

        // verify that new vanity URL works
        page = frontendMgr.loadPage(demoServerURL + vurl384URL_new);
        title = page.getTitle();
        logger.info("title = " + title);
        Assert.assertTrue(title.equals("Market Research - Quest Financial"), "ERROR - Mapping for vanity URL does not work:  " + vurl384URL_new);
        
        // verify that original vanity URL does not work
        page = frontendMgr.loadPage(demoServerURL + vurl384URL_org);
        title = page.getTitle();
        Assert.assertTrue(title.contains("404"), "ERROR - Mapping for vanity URL still exists:  " + vurl384URL_org);
        
        // delete and verify it is no longer listed
        vanityURLPage.deleteVanityURL(vurl384Title_new);
        Assert.assertFalse(vanityURLPage.doesVanityURLExist(vurl384Title_new));
    }

    @Test (groups = {"VanityURLs"})
    public void testCase385_CreateVanityURLForSpecificHost() throws Exception{
        IPortletMenu portletMenu = backendMgr.getPageObject(IPortletMenu.class);
        IVanityURLsPage vanityURLPage = portletMenu.getVanityURLsPage();
        // seems to be a redundant and unnecessary test case
    }

    @Test (groups = {"VanityURLs"})
    public void testCase386_CreateVanityURLOnAllHosts() throws Exception{
        IPortletMenu portletMenu = backendMgr.getPageObject(IPortletMenu.class);
        IVanityURLsPage vanityURLPage = portletMenu.getVanityURLsPage();

        String vurl386Title = "386 Vanity URL";
        String vurl386URL = "386";
        
        // verify vanity url does not already exist
        Assert.assertFalse(vanityURLPage.doesVanityURLExist(vurl386Title));

        // add url
        vanityURLPage.addVanityURLToAllHosts(vurl386Title, vurl386URL, "/alt-home/alt-home.html");
        
        // verify it is listed and working
        Assert.assertTrue(vanityURLPage.doesVanityURLExist(vurl386Title));
        IBasePage page = frontendMgr.loadPage(demoServerURL + vurl386URL);
        String title = page.getTitle();
        Assert.assertTrue(title.equals("alt home - Quest Financial"), "ERROR - Mapping for vanity URL does not seem to be functioning properly:  " + vurl386URL);

        // delete and verify it is no longer listed
        vanityURLPage.deleteVanityURL(vurl386Title);
        Assert.assertFalse(vanityURLPage.doesVanityURLExist(vurl386Title));

        //Thread.sleep(3000);
        // verify it no longer works
        page = frontendMgr.loadPage(demoServerURL + vurl386URL);
        title = page.getTitle();
        Assert.assertTrue(title.contains("404"), "ERROR - Mapping still seems to exist for URL:  " + vurl386URL + "title=" + title);
  }

    @Test (groups = {"VanityURLs"})
    public void testCase387_HostSpecificOverridesGlobalVanityURL() throws Exception{
    	// NOTE - must have hosts demo.dotcms.com and m.demo.dotcms.com resolving properly for this test to work as expected
        IPortletMenu portletMenu = backendMgr.getPageObject(IPortletMenu.class);
        IVanityURLsPage vanityURLPage = portletMenu.getVanityURLsPage();

        String vurl387Title_global = "387 Vanity Global URL";
        String vurl387Title_specific = "387 Vanity Specific URL";
        String vurl387URL = "387";
        String targetHost = new URL(mobileServerURL).getHost();
        vanityURLPage.selectBackendHost(targetHost);

        // verify vanity urls do not already exist
        Assert.assertFalse(vanityURLPage.doesVanityURLExist(vurl387Title_global));
        Assert.assertFalse(vanityURLPage.doesVanityURLExist(vurl387Title_specific));
        
        // add global and make sure it is working
        vanityURLPage.addVanityURLToAllHosts(vurl387Title_global, vurl387URL, "/alt-home/alt-home.html");
        Assert.assertTrue(vanityURLPage.doesVanityURLExist(vurl387Title_global));
        IBasePage page = frontendMgr.loadPage(demoServerURL + vurl387URL);
        String title = page.getTitle();
        Assert.assertTrue(title.equals("alt home - Quest Financial"), "ERROR - Mapping for vanity URL does not seem to be functioning properly for " + demoServerURL + ":  " + vurl387URL + "title=" + title);
        page = frontendMgr.loadPage(mobileServerURL + vurl387URL);
        title = page.getTitle();
        Assert.assertTrue(title.equals("alt home - Quest Financial"), "ERROR - Mapping for vanity URL does not seem to be functioning properly for " + mobileServerURL + ":  " + vurl387URL + "title=" + title);
        page = frontendMgr.loadPage(sharedServerURL + vurl387URL);
        title = page.getTitle();
        Assert.assertTrue(title.equals("alt home - Quest Financial"), "ERROR - Mapping for vanity URL does not seem to be functioning properly for " + sharedServerURL + ":  " + vurl387URL + "title=" + title);
        
        // add vanity url for specific host m.demo.dotcms.com
        vanityURLPage.addVanityURLToHost(vurl387Title_specific, targetHost, vurl387URL, "http://www.google.com/");

        // verify specific vanity url works
        page = frontendMgr.loadPage(mobileServerURL + vurl387URL);
        title = page.getTitle();
        Assert.assertTrue(title.equals("Google"), "ERROR - Global mapping still seems to be in effect for URL:  " + vurl387URL + "title=" + title);

        // verify global vanity url still works for other hosts
        page = frontendMgr.loadPage(demoServerURL + vurl387URL);
        title = page.getTitle();
        Assert.assertTrue(title.equals("alt home - Quest Financial"), "ERROR - Mapping for vanity URL does not seem to be functioning properly for " + demoServerURL + ":  " + vurl387URL + "title=" + title);

        page = frontendMgr.loadPage(sharedServerURL + vurl387URL);
        title = page.getTitle();
        Assert.assertTrue(title.equals("alt home - Quest Financial"), "ERROR - Mapping for vanity URL does not seem to be functioning properly for " + sharedServerURL + ":  " + vurl387URL + "title=" + title);

        // delete and verify they are no longer listed
        vanityURLPage.deleteVanityURL(vurl387Title_global);
        Assert.assertFalse(vanityURLPage.doesVanityURLExist(vurl387Title_global));
        vanityURLPage.deleteVanityURL(vurl387Title_specific);
        Assert.assertFalse(vanityURLPage.doesVanityURLExist(vurl387Title_specific));
    }
}
