package com.dotcms.qa.testng.tests;

import java.util.*;

import com.dotcms.qa.selenium.util.SeleniumConfig;
import com.dotcms.qa.selenium.util.SeleniumPageManager;

import com.dotcms.qa.selenium.pages.IBasePage;
import com.dotcms.qa.selenium.pages.backend.*;

import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class VanityURLTests {
    private static final Logger logger = Logger.getLogger(VanityURLTests.class);
    
    private SeleniumPageManager backendMgr = null;
    private SeleniumPageManager frontendMgr = null;
    private String serverURL = null;
    private ILoginPage loginPage = null;

    @BeforeClass
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

        /*
        // add license
        ILicenseManagerPage licPage = portletMenu.getLicenseManagerPage();
        String licenseLevel = licPage.getLicenseLevel();
        logger.info("License Level = " + licenseLevel);
        licPage.activateLicenseKey(false, "k8Xd32+edtuiKO2N24OxLmPBS+/m9cEjyLoGETbKO1+U3d0ytLc0iaGhg1Tmb24bgs67Q/7yxRVYj1jheW9TPcPBd0E0fc1GkiTR21y1FGRwdoq1aiMZh/zv4QxvoZJg3h5kXJ2pGCi34bv70Urknhy7vRYrccUjdiL/HzC6GcgAAAAJZGV2ZWxvcGVyAAAABAAAAL4AAAAIAAABPaHfL2wAAAAIAAABRPdlEgAAAAAIAAAAAAAYxOMAAAAEAAABkAAAAAEB");
        licenseLevel = licPage.getLicenseLevel();
        logger.info("License Level = " + licenseLevel);
        */

    }
    
    @AfterClass
    public void teardown() throws Exception {
        // logout
        backendMgr.loadPage(serverURL + "c/portal/logout?referer=/c");
    	loginPage = backendMgr.getPageObject(ILoginPage.class);
        
        // shutdown
        logger.info("Shutting Down....");
        backendMgr.shutdown();

        frontendMgr.shutdown();
    }
    
    @Test (groups = {"VanityURLs"})
    public void testCase383_CreateVanityURL() throws Exception {
        IPortletMenu portletMenu = backendMgr.getPageObject(IPortletMenu.class);
        IVanityURLsPage vanityURLPage = portletMenu.getVanityURLsPage();
    	
        String vurl383Title = "383 Vanity URL";
        String vurl383URL = "383";
        String targetHost = "demo.dotcms.com";
        
        vanityURLPage.selectBackendHost(targetHost);
        
        // verify vanity URL does not already exist
        Assert.assertFalse(vanityURLPage.doesVanityURLExist(vurl383Title));
        
        // verify it does not already work
        IBasePage page = frontendMgr.loadPage(serverURL + vurl383URL);
        String title = page.getTitle();
        Assert.assertTrue(title.contains("404"), "ERROR - Mapping for vanity URL already exists:  " + vurl383URL);

        // add vanity URL
        vanityURLPage.addVanityURLToHost(vurl383Title, targetHost, vurl383URL, "/about-us/our-team/index.html");

        // verify it was created and listed on page
        Assert.assertTrue(vanityURLPage.doesVanityURLExist(vurl383Title));
        
        // verify that vanity url works
        page = frontendMgr.loadPage(serverURL + vurl383URL);
        title = page.getTitle();
        logger.info("title = " + title);
        Assert.assertTrue(title.equals("Our Team - Quest Financial"), "ERROR - Mapping for vanity URL does not work:  " + vurl383URL);

        // delete vanity URL
        vanityURLPage.deleteVanityURL(vurl383Title);

        // verify it is no longer listed
        Assert.assertFalse(vanityURLPage.doesVanityURLExist(vurl383Title));
        
        // verify it no longer works
        page = frontendMgr.loadPage(serverURL + vurl383URL);
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
        String targetHost = "demo.dotcms.com";
        
        vanityURLPage.selectBackendHost(targetHost);
        
        // verify neither vanity URL currently exists
        Assert.assertFalse(vanityURLPage.doesVanityURLExist(vurl384Title_org));
        Assert.assertFalse(vanityURLPage.doesVanityURLExist(vurl384Title_new));

        // verify neither vanity URL currently works
        IBasePage page = frontendMgr.loadPage(serverURL + vurl384URL_org);
        String title = page.getTitle();
        Assert.assertTrue(title.contains("404"), "ERROR - Mapping for vanity URL already exists:  " + vurl384URL_org);
        page = frontendMgr.loadPage(serverURL + vurl384URL_new);
        title = page.getTitle();
        Assert.assertTrue(title.contains("404"), "ERROR - Mapping for vanity URL already exists:  " + vurl384URL_new);
        
        // create new vanity URL and verify it exists and is working
        vanityURLPage.addVanityURLToHost(vurl384Title_org, targetHost, vurl384URL_org, "products/");
//        vanityURLPage.addVanityURLToHost(vurl384Title_org, targetHost, vurl384URL_org, "products/index.html");

        // verify it was created and listed on page
        Assert.assertTrue(vanityURLPage.doesVanityURLExist(vurl384Title_org));
        
        // verify that vanity url works
        page = frontendMgr.loadPage(serverURL + vurl384URL_org);
        title = page.getTitle();
        logger.info("title = " + title);
        Assert.assertTrue(title.equals("Products - Quest Financial"), "ERROR - Mapping for vanity URL does not work:  " + vurl384URL_org);
        
        // edit vanity url
        vanityURLPage.editVanityURL(vurl384Title_org, vurl384Title_new, vurl384URL_new, "Market Research - Quest Financial");

        // verify new vanity url is listed and old one is not
        Assert.assertTrue(vanityURLPage.doesVanityURLExist(vurl384Title_new));
        Assert.assertFalse(vanityURLPage.doesVanityURLExist(vurl384Title_org));

        // verify that new vanity URL works
        page = frontendMgr.loadPage(serverURL + vurl384URL_new);
        title = page.getTitle();
        logger.info("title = " + title);
        Assert.assertTrue(title.equals("Market Research - Quest Financial"), "ERROR - Mapping for vanity URL does not work:  " + vurl384URL_new);
        
        // verify that original vanity URL does not work
        page = frontendMgr.loadPage(serverURL + vurl384URL_org);
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
        String vurl386URL = "/386";
        
        // verify vanity url does not already exist
        Assert.assertFalse(vanityURLPage.doesVanityURLExist(vurl386Title));

        // add url
        vanityURLPage.addVanityURLToAllHosts(vurl386Title, vurl386URL, "http://demo.dotcms.com:8080/about-us/index.html");
        
        // verify it is listed and working
        Assert.assertTrue(vanityURLPage.doesVanityURLExist(vurl386Title));
        IBasePage page = frontendMgr.loadPage(serverURL + vurl386URL);
        String title = page.getTitle();
        Assert.assertTrue(title.equals("About Us - Quest Financial"), "ERROR - Mapping for vanity URL does not seem to be functioning properly:  " + vurl386URL);

        // delete and verify it is no longer listed
        vanityURLPage.deleteVanityURL(vurl386Title);
        Assert.assertFalse(vanityURLPage.doesVanityURLExist(vurl386Title));

        // verify it no longer works
        page = frontendMgr.loadPage(serverURL + vurl386URL);
        title = page.getTitle();
        Assert.assertTrue(title.contains("404"), "ERROR - Mapping still seems to exist for URL:  " + vurl386URL);
  }

    @Test (groups = {"VanityURLs"})
    public void testCase387_HostSpecificOverridesGlobalVanityURL() throws Exception{
    	// NOTE - must have hosts demo.dotcms.com and m.demo.dotcms.com resolving properly for this test to work as expected
        IPortletMenu portletMenu = backendMgr.getPageObject(IPortletMenu.class);
        IVanityURLsPage vanityURLPage = portletMenu.getVanityURLsPage();

        String vurl387Title_global = "387 Vanity Global URL";
        String vurl387Title_specific = "387 Vanity Specific URL";
        String vurl387URL = "387";
        String targetHost = "m.demo.dotcms.com";
        vanityURLPage.selectBackendHost(targetHost);

        // verify vanity urls do not already exist
        Assert.assertFalse(vanityURLPage.doesVanityURLExist(vurl387Title_global));
        Assert.assertFalse(vanityURLPage.doesVanityURLExist(vurl387Title_specific));
        
        // add global and make sure it is working
        vanityURLPage.addVanityURLToAllHosts(vurl387Title_global, vurl387URL, "http://demo.dotcms.com:8080/about-us/index.html");
        Assert.assertTrue(vanityURLPage.doesVanityURLExist(vurl387Title_global));
        IBasePage page = frontendMgr.loadPage("http://demo.dotcms.com:8080/" + vurl387URL);
        String title = page.getTitle();
        Assert.assertTrue(title.equals("About Us - Quest Financial"), "ERROR - Mapping for vanity URL does not seem to be functioning properly for demo.dotcms.com:  " + vurl387URL);
        page = frontendMgr.loadPage("http://m.demo.dotcms.com:8080/" + vurl387URL);
        title = page.getTitle();
        Assert.assertTrue(title.equals("About Us - Quest Financial"), "ERROR - Mapping for vanity URL does not seem to be functioning properly for m.demo.dotcms.com:  " + vurl387URL);
        
        // add vanity url for specific host m.demo.dotcms.com
        vanityURLPage.addVanityURLToHost(vurl387Title_specific, targetHost, vurl387URL, "http://www.dotcms.com/");

        // verify specific vanity url works
        page = frontendMgr.loadPage("http://m.demo.dotcms.com:8080/" + vurl387URL);
        title = page.getTitle();
        Assert.assertTrue(title.contains("dotcms Open Source CMS"), "ERROR - Global mapping still seems to be in effect for URL:  " + vurl387URL);

        // verify global vanity url still works for other hosts
        page = frontendMgr.loadPage("http://demo.dotcms.com:8080/" + vurl387URL);
        title = page.getTitle();
        Assert.assertTrue(title.equals("About Us - Quest Financial"), "ERROR - Mapping for vanity URL does not seem to be functioning properly for demo.dotcms.com:  " + vurl387URL);

        // delete and verify they are no longer listed
        vanityURLPage.deleteVanityURL(vurl387Title_global);
        Assert.assertFalse(vanityURLPage.doesVanityURLExist(vurl387Title_global));
        vanityURLPage.deleteVanityURL(vurl387Title_specific);
        Assert.assertFalse(vanityURLPage.doesVanityURLExist(vurl387Title_specific));
    }
}
