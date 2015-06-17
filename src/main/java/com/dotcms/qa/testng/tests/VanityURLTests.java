package com.dotcms.qa.testng.tests;

import java.net.*;

import com.dotcms.qa.selenium.util.SeleniumConfig;
import com.dotcms.qa.selenium.util.SeleniumPageManager;

import com.dotcms.qa.selenium.pages.IBasePage;
import com.dotcms.qa.selenium.pages.backend.*;
import com.dotcms.qa.util.language.LanguageManager;

import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.AfterGroups;
import org.testng.annotations.BeforeGroups;
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
    	try {
	    	logger.info("**VanityURLTests.init() beginning**");
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
	    	logger.info("**VanityURLTests.init() ending**");
    	}
    	catch(Exception e) {
    		logger.error("ERROR - VanityURLTests.init()", e);
    		throw(e);
    	}
    	
    }
    
    @AfterGroups (groups = {"VanityURLs"})
    public void teardown() throws Exception {
    	try {
	    	logger.info("**VanityURLTests.teardown() beginning**");
	        // logout
	        backendMgr.logoutBackend();
	    	logger.info("**VanityURLTests.teardown() ending**");
    	}
    	catch (Exception e) {
    		logger.error("ERROR - VanityURLTests.teardown()", e);
    		throw e;
    	}
    }

    public void sleep() {
        try{Thread.sleep(2000);}catch(Exception e){};
    }
    
    @Test (groups = {"VanityURLs"})
    public void tc383_AddVanityURLOnDemoHost() throws Exception {
    	backendMgr.loadPage(demoServerURL + "admin"); 
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
        sleep();
        String title = page.getTitle();
        Assert.assertTrue(title.contains("404"), "ERROR - Mapping for vanity URL already exists:  " + vurl383URL);

        // add vanity URL
        vanityURLPage.addVanityURLToHost(vurl383Title, targetHost, vurl383URL, "/about-us/our-team/index.html");

        // verify it was created and listed on page
        Assert.assertEquals(vanityURLPage.getSystemMessage().trim(), vanityURLPage.getLocalizedString("message.virtuallink.save"));
        Assert.assertTrue(vanityURLPage.doesVanityURLExist(vurl383Title));
        
        // verify that vanity url works
        page = frontendMgr.loadPage(demoServerURL + vurl383URL);
        sleep();
        title = page.getTitle();
        logger.info("title = " + title);
        Assert.assertTrue(title.equals("Our Team - Quest Financial"), "ERROR - Mapping for vanity URL does not work:  " + vurl383URL);

        // delete vanity URL
        vanityURLPage.deleteVanityURL(vurl383Title);
        Assert.assertEquals(vanityURLPage.getSystemMessage().trim(), vanityURLPage.getLocalizedString("message.virtuallink.delete"));

        // verify it is no longer listed
        Assert.assertFalse(vanityURLPage.doesVanityURLExist(vurl383Title));
        
        // verify it no longer works
        page = frontendMgr.loadPage(demoServerURL + vurl383URL);
        sleep();
        title = page.getTitle();
        Assert.assertTrue(title.contains("404"), "ERROR - Mapping still seems to exist for URL:  " + vurl383URL);
    }

    @Test (groups = {"VanityURLs"})
    public void tc384_EditVanityURLToDirectoryOnDemoHost() throws Exception{
    	backendMgr.loadPage(demoServerURL + "admin"); 
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
        sleep();
        String title = page.getTitle();
        Assert.assertTrue(title.contains("404"), "ERROR - Mapping for vanity URL already exists:  " + vurl384URL_org);
        page = frontendMgr.loadPage(demoServerURL + vurl384URL_new);
        sleep();
        title = page.getTitle();
        Assert.assertTrue(title.contains("404"), "ERROR - Mapping for vanity URL already exists:  " + vurl384URL_new);
        
        // create new vanity URL
        vanityURLPage.addVanityURLToHost(vurl384Title_org, targetHost, vurl384URL_org, "products/");

        // verify it was created and listed on page
        Assert.assertEquals(vanityURLPage.getSystemMessage().trim(), vanityURLPage.getLocalizedString("message.virtuallink.save"));
        Assert.assertTrue(vanityURLPage.doesVanityURLExist(vurl384Title_org));
        
        // verify that vanity url works
        page = frontendMgr.loadPage(demoServerURL + vurl384URL_org);
        sleep();
        title = page.getTitle();
        logger.info("title = " + title);
        Assert.assertTrue(title.equals("Products - Quest Financial"), "ERROR - Mapping for vanity URL does not work:  " + vurl384URL_org);
        
        // edit vanity url
        vanityURLPage.editVanityURL(vurl384Title_org, vurl384Title_new, vurl384URL_new, "/services/market-research/");
        Assert.assertEquals(vanityURLPage.getSystemMessage().trim(), vanityURLPage.getLocalizedString("message.virtuallink.save"));

        // verify new vanity url is listed and old one is not
        Assert.assertTrue(vanityURLPage.doesVanityURLExist(vurl384Title_new));
        Assert.assertFalse(vanityURLPage.doesVanityURLExist(vurl384Title_org));

        // verify that new vanity URL works
        page = frontendMgr.loadPage(demoServerURL + vurl384URL_new);
        sleep();
        title = page.getTitle();
        logger.info("title = " + title);
        Assert.assertTrue(title.equals("Market Research - Quest Financial"), "ERROR - Mapping for vanity URL does not work:  " + vurl384URL_new);
        
        // verify that original vanity URL does not work
        page = frontendMgr.loadPage(demoServerURL + vurl384URL_org);
        sleep();
        title = page.getTitle();
        Assert.assertTrue(title.contains("404"), "ERROR - Mapping for vanity URL still exists:  " + vurl384URL_org);
        
        // delete and verify it is no longer listed
        vanityURLPage.deleteVanityURL(vurl384Title_new);
        Assert.assertEquals(vanityURLPage.getSystemMessage().trim(), vanityURLPage.getLocalizedString("message.virtuallink.delete"));
        Assert.assertFalse(vanityURLPage.doesVanityURLExist(vurl384Title_new));
    }

    @Test (groups = {"VanityURLs"})
    public void tc385_AddVanityURLOnAllHosts() throws Exception{
    	backendMgr.loadPage(demoServerURL + "admin"); 
        IPortletMenu portletMenu = backendMgr.getPageObject(IPortletMenu.class);
        IVanityURLsPage vanityURLPage = portletMenu.getVanityURLsPage();
        String vurl385Title = "385 Vanity URL";
        String vurl385URL = "385";
        
        // verify vanity url does not already exist
        Assert.assertFalse(vanityURLPage.doesVanityURLExist(vurl385Title));

        // add url
        vanityURLPage.addVanityURLToAllHosts(vurl385Title, vurl385URL, "/alt-home/alt-home.html");
        Assert.assertEquals(vanityURLPage.getSystemMessage().trim(), vanityURLPage.getLocalizedString("message.virtuallink.save"));
        
        // verify it is listed and working
        Assert.assertTrue(vanityURLPage.doesVanityURLExist(vurl385Title));
        IBasePage demoPage = frontendMgr.loadPage(demoServerURL + vurl385URL);
        sleep();
        String demoTitle = demoPage.getTitle();
        Assert.assertTrue(demoTitle.equals("alt home - Quest Financial"), "ERROR - Mapping for vanity URL does not seem to be functioning properly:  " + vurl385URL);

        IBasePage mobilePage = frontendMgr.loadPage(mobileServerURL + vurl385URL);
        sleep();
        String mobileTitle = mobilePage.getTitle();
        Assert.assertTrue(mobileTitle.equals("alt home - Quest Financial"), "ERROR - Mapping for vanity URL does not seem to be functioning properly:  " + vurl385URL);

        IBasePage sharedPage = frontendMgr.loadPage(sharedServerURL + vurl385URL);
        sleep();
        String sharedTitle = sharedPage.getTitle();
        Assert.assertTrue(sharedTitle.equals("alt home - Quest Financial"), "ERROR - Mapping for vanity URL does not seem to be functioning properly:  " + vurl385URL);

        // delete and verify it is no longer listed
        vanityURLPage.deleteVanityURL(vurl385Title);
        Assert.assertEquals(vanityURLPage.getSystemMessage().trim(), vanityURLPage.getLocalizedString("message.virtuallink.delete"));
        Assert.assertFalse(vanityURLPage.doesVanityURLExist(vurl385Title));

        // verify it no longer works
        IBasePage page = frontendMgr.loadPage(demoServerURL + vurl385URL);
        sleep();
        String title = page.getTitle();
        Assert.assertTrue(title.contains("404"), "ERROR - Mapping still seems to exist for URL:  " + vurl385URL + "title=" + title);
    }

    @Test (groups = {"VanityURLs"})
    public void tc386_AddVanityURLToDirectoryOnAllHosts() throws Exception{
    	backendMgr.loadPage(demoServerURL + "admin"); 
        IPortletMenu portletMenu = backendMgr.getPageObject(IPortletMenu.class);
        IVanityURLsPage vanityURLPage = portletMenu.getVanityURLsPage();

        String vurl386Title = "386 Vanity URL";
        String vurl386URL = "386";
                
        // verify vanity url does not already exist
        Assert.assertFalse(vanityURLPage.doesVanityURLExist(vurl386Title));

        // add url
        vanityURLPage.addVanityURLToAllHosts(vurl386Title, vurl386URL, "/alt-home/");
        Assert.assertEquals(vanityURLPage.getSystemMessage().trim(), vanityURLPage.getLocalizedString("message.virtuallink.save"));
        
        // verify it is listed and working on all three hosts
        Assert.assertTrue(vanityURLPage.doesVanityURLExist(vurl386Title));
        IBasePage page = frontendMgr.loadPage(demoServerURL + vurl386URL);
        sleep();
        Assert.assertTrue(page.getTitle().equals("althome - Quest Financial"), "ERROR - Mapping for vanity URL does not seem to be functioning properly:  " + vurl386URL + " title=|" + page.getTitle() + "|");
        page = frontendMgr.loadPage(mobileServerURL + vurl386URL);
        sleep();
        Assert.assertTrue(page.isTextPresent("Maecenas dapibus tristique orci"), "ERROR - Mapping for vanity URL does not seem to be functioning properly:  " + vurl386URL);
        page = frontendMgr.loadPage(sharedServerURL + vurl386URL);
        sleep();
        Assert.assertTrue(page.getTitle().equals("index - Quest Financial"), "ERROR - Mapping for vanity URL does not seem to be functioning properly:  " + vurl386URL + " title=|" + page.getTitle() + "|");

        // delete and verify it is no longer listed
        vanityURLPage.deleteVanityURL(vurl386Title);
        Assert.assertEquals(vanityURLPage.getSystemMessage().trim(), vanityURLPage.getLocalizedString("message.virtuallink.delete"));
        Assert.assertFalse(vanityURLPage.doesVanityURLExist(vurl386Title));

        // verify it no longer works on any of the three hosts
        page = frontendMgr.loadPage(demoServerURL + vurl386URL);
        sleep();
        Assert.assertTrue(page.getTitle().contains("404"), "ERROR - Mapping still seems to exist for URL:  " + vurl386URL + " title=|" + page.getTitle() + "|");
        page = frontendMgr.loadPage(mobileServerURL + vurl386URL);
        sleep();
        String title = page.getTitle();
        Assert.assertTrue(page.getTitle().contains("404"), "ERROR - Mapping still seems to exist for URL:  " + vurl386URL + " title=|" + title + "|");
        page = frontendMgr.loadPage(sharedServerURL + vurl386URL);
        sleep();
        Assert.assertTrue(page.getTitle().contains("404"), "ERROR - Mapping still seems to exist for URL:  " + vurl386URL + " title=|" + page.getTitle() + "|");
  }

    @Test (groups = {"VanityURLs"})
    public void tc387_VerifyHostSpecificVanityURLOverridesAllHostsVanityURL() throws Exception{
    	backendMgr.loadPage(demoServerURL + "admin"); 
        IPortletMenu portletMenu = backendMgr.getPageObject(IPortletMenu.class);
        IVanityURLsPage vanityURLPage = portletMenu.getVanityURLsPage();
        
        String vurl387Title_global = "387 Vanity Global URL";
        String vurl387Title_specific = "387 Vanity Specific URL";
        String vurl387URL = "387";
        String targetHost = new URL(demoServerURL).getHost();
        vanityURLPage.selectBackendHost(targetHost);

        // verify vanity urls do not already exist
        Assert.assertFalse(vanityURLPage.doesVanityURLExist(vurl387Title_global));
        Assert.assertFalse(vanityURLPage.doesVanityURLExist(vurl387Title_specific));
        
        // add global and make sure it is working
        vanityURLPage.addVanityURLToAllHosts(vurl387Title_global, vurl387URL, "/alt-home/alt-home.html");
        Assert.assertEquals(vanityURLPage.getSystemMessage().trim(), vanityURLPage.getLocalizedString("message.virtuallink.save"));
        Assert.assertTrue(vanityURLPage.doesVanityURLExist(vurl387Title_global));
        IBasePage page = frontendMgr.loadPage(demoServerURL + vurl387URL);
        sleep();
        String title = page.getTitle();
        Assert.assertTrue(title.equals("alt home - Quest Financial"), "ERROR - Mapping for vanity URL does not seem to be functioning properly for " + demoServerURL + ":  " + vurl387URL + " title=|" + page.getTitle() + "|");
        page = frontendMgr.loadPage(mobileServerURL + vurl387URL);
        sleep();
        title = page.getTitle();
        Assert.assertTrue(title.equals("alt home - Quest Financial"), "ERROR - Mapping for vanity URL does not seem to be functioning properly for " + mobileServerURL + ":  " + vurl387URL + " title=|" + page.getTitle() + "|");
        page = frontendMgr.loadPage(sharedServerURL + vurl387URL);
        sleep();
        title = page.getTitle();
        Assert.assertTrue(title.equals("alt home - Quest Financial"), "ERROR - Mapping for vanity URL does not seem to be functioning properly for " + sharedServerURL + ":  " + vurl387URL + " title=|" + page.getTitle() + "|");
        
        // add vanity url for specific host demo.dotcms.com
        vanityURLPage.addVanityURLToHost(vurl387Title_specific, targetHost, vurl387URL, "/services/private-banking/index.html");
        Assert.assertEquals(vanityURLPage.getSystemMessage().trim(), vanityURLPage.getLocalizedString("message.virtuallink.save"));

        // verify specific vanity url works
        page = frontendMgr.loadPage(demoServerURL + vurl387URL);
        sleep();
        title = page.getTitle();
        Assert.assertTrue(title.equals("Private Banking - Quest Financial"), "ERROR - Global mapping still seems to be in effect for URL:  " + vurl387URL + " title=|" + page.getTitle() + "|");

        // temp test to verify whether or not the following code fails to load a new page.
        page = frontendMgr.loadPage(mobileServerURL + "/");
        sleep();

        // verify global vanity url still works for other hosts
        page = frontendMgr.loadPage(mobileServerURL + vurl387URL);
        sleep();
        title = page.getTitle();
        Assert.assertTrue(title.equals("alt home - Quest Financial"), "ERROR - Mapping for vanity URL does not seem to be functioning properly for " + mobileServerURL + ":  " + vurl387URL + " title=|" + title + "|");

        page = frontendMgr.loadPage(sharedServerURL + vurl387URL);
        sleep();
        title = page.getTitle();
        Assert.assertTrue(title.equals("alt home - Quest Financial"), "ERROR - Mapping for vanity URL does not seem to be functioning properly for " + sharedServerURL + ":  " + vurl387URL + " title=|" + title + "|");

        // delete and verify they are no longer listed
        vanityURLPage.deleteVanityURL(vurl387Title_global);
        Assert.assertEquals(vanityURLPage.getSystemMessage().trim(), vanityURLPage.getLocalizedString("message.virtuallink.delete"));
        Assert.assertFalse(vanityURLPage.doesVanityURLExist(vurl387Title_global));
        vanityURLPage.deleteVanityURL(vurl387Title_specific);
        Assert.assertEquals(vanityURLPage.getSystemMessage().trim(), vanityURLPage.getLocalizedString("message.virtuallink.delete"));
        Assert.assertFalse(vanityURLPage.doesVanityURLExist(vurl387Title_specific));
    }

    @Test (groups = {"VanityURLs"})
    public void tc14099_AddVanityURLToExternalURLOnDemoHost() throws Exception{
    	backendMgr.loadPage(demoServerURL + "admin"); 
        IPortletMenu portletMenu = backendMgr.getPageObject(IPortletMenu.class);
        IVanityURLsPage vanityURLPage = portletMenu.getVanityURLsPage();

        String vurl14099Title = "14099 Vanity URL";
        String vurl14099URL = "14099";
        String targetHost = new URL(demoServerURL).getHost();
        vanityURLPage.selectBackendHost(targetHost);

        // verify vanity url does not already exist
        Assert.assertFalse(vanityURLPage.doesVanityURLExist(vurl14099Title));
        
        // add url
        vanityURLPage.addVanityURLToHost(vurl14099Title, targetHost, vurl14099URL, "http://www.google.com/");
//        Assert.assertEquals(vanityURLPage.getSystemMessage().trim(), vanityURLPage.getLocalizedString("message.virtuallink.save"));
        
        // verify it is listed and working on demo host
        Assert.assertTrue(vanityURLPage.doesVanityURLExist(vurl14099Title));
        IBasePage page = frontendMgr.loadPage(demoServerURL + vurl14099URL);
        sleep();
        Assert.assertTrue(page.getTitle().equals("Google"), "ERROR - Mapping for vanity URL does not seem to be functioning properly:  " + vurl14099URL);

        // verify it is not working on other hosts
        page = frontendMgr.loadPage(mobileServerURL + vurl14099URL);
        sleep();
        Assert.assertTrue(page.getTitle().contains("404"), "ERROR - Vanity URL should not be working for this host: m.qademo.dotcms.com - URL:" + vurl14099URL);
        page = frontendMgr.loadPage(sharedServerURL + vurl14099URL);
        sleep();
        Assert.assertTrue(page.getTitle().contains("404"), "ERROR - Vanity URL should not be working for this host: qashared.dotcms.com - URL:" + vurl14099URL);
        
        // delete and verify no longer listed
        vanityURLPage.deleteVanityURL(vurl14099Title);
        Assert.assertEquals(vanityURLPage.getSystemMessage().trim(), vanityURLPage.getLocalizedString("message.virtuallink.delete"));
        Assert.assertFalse(vanityURLPage.doesVanityURLExist(vurl14099Title));
    }
    
    @Test (groups = {"VanityURLs"})
    public void tc14100_AddVanityURLToExternalURLOnAllHosts() throws Exception{
    	backendMgr.loadPage(demoServerURL + "admin"); 
        IPortletMenu portletMenu = backendMgr.getPageObject(IPortletMenu.class);
        IVanityURLsPage vanityURLPage = portletMenu.getVanityURLsPage();
        String vurl14100Title = "14100 Vanity URL";
        String vurl14100URL = "14100";
                
        // verify vanity url does not already exist
        Assert.assertFalse(vanityURLPage.doesVanityURLExist(vurl14100Title));

        // add url
        vanityURLPage.addVanityURLToAllHosts(vurl14100Title, vurl14100URL, "http://www.cnn.com/");
        Assert.assertEquals(vanityURLPage.getSystemMessage().trim(), vanityURLPage.getLocalizedString("message.virtuallink.save"));
        
        // verify it is listed and working on all three hosts
        Assert.assertTrue(vanityURLPage.doesVanityURLExist(vurl14100Title));
        IBasePage page = frontendMgr.loadPage(demoServerURL + vurl14100URL);
        sleep();
        Assert.assertTrue(page.getTitle().contains("CNN.com"), "ERROR - Mapping for vanity URL does not seem to be functioning properly:  " + vurl14100URL + " title=|" + page.getTitle() + "|");
        page = frontendMgr.loadPage(mobileServerURL + vurl14100URL);
        sleep();
        Assert.assertTrue(page.getTitle().contains("CNN.com"), "ERROR - Mapping for vanity URL does not seem to be functioning properly:  " + vurl14100URL + " title=|" + page.getTitle() + "|");
        page = frontendMgr.loadPage(sharedServerURL + vurl14100URL);
        sleep();
        Assert.assertTrue(page.getTitle().contains("CNN.com"), "ERROR - Mapping for vanity URL does not seem to be functioning properly:  " + vurl14100URL + " title=|" + page.getTitle() + "|");

        // delete and verify it is no longer listed
        vanityURLPage.deleteVanityURL(vurl14100Title);
        Assert.assertEquals(vanityURLPage.getSystemMessage().trim(), vanityURLPage.getLocalizedString("message.virtuallink.delete"));
        Assert.assertFalse(vanityURLPage.doesVanityURLExist(vurl14100Title));

        // verify it no longer works on any of the three hosts
        /*
        page = frontendMgr.loadPage(demoServerURL + vurl14100URL);
        Assert.assertTrue(page.getTitle().contains("404"), "ERROR - Mapping still seems to exist for URL:  " + vurl14100URL + " title=|" + page.getTitle() + "|");
        page = frontendMgr.loadPage(mobileServerURL + vurl14100URL);
        Assert.assertTrue(page.getTitle().contains("404"), "ERROR - Mapping still seems to exist for URL:  " + vurl14100URL + " title=|" + page.getTitle() + "|");
        page = frontendMgr.loadPage(sharedServerURL + vurl14100URL);
        Assert.assertTrue(page.getTitle().contains("404"), "ERROR - Mapping still seems to exist for URL:  " + vurl14100URL + " title=|" + page.getTitle() + "|");
        */
    }
    
    @Test (groups = {"VanityURLs"})
    public void tc14101_AddVanityURLWithParametersOnDemoHost() throws Exception{
    	backendMgr.loadPage(demoServerURL + "admin"); 
        IPortletMenu portletMenu = backendMgr.getPageObject(IPortletMenu.class);
        IVanityURLsPage vanityURLPage = portletMenu.getVanityURLsPage();
        String vurl14101Title = "14101 Vanity URL";
        String vurl14101URL = "14101";

        String targetHost = new URL(demoServerURL).getHost();
        vanityURLPage.selectBackendHost(targetHost);
                
        // verify vanity url does not already exist
        Assert.assertFalse(vanityURLPage.doesVanityURLExist(vurl14101Title));

        // add url
        vanityURLPage.addVanityURLToHost(vurl14101Title, targetHost, vurl14101URL, "/about-us/locations/index.html?direction=33133&milesR=100");
        Assert.assertEquals(vanityURLPage.getSystemMessage().trim(), vanityURLPage.getLocalizedString("message.virtuallink.save"));
        
        // verify it is listed and working on demo host
        Assert.assertTrue(vanityURLPage.doesVanityURLExist(vurl14101Title));
        IBasePage page = frontendMgr.loadPage(demoServerURL + vurl14101URL);
        sleep();
        Assert.assertTrue(page.getTitle().equals("Locations - Quest Financial"), "ERROR - Mapping for vanity URL does not seem to be functioning properly:  " + vurl14101URL + " title=|" + page.getTitle() + "|");
        Assert.assertTrue(page.isTextPresent("value=\"33133\""), "ERROR - Mapping for vanity URL does not seem to be functioning properly:  " + vurl14101URL);

        // delete and verify it is no longer listed
        vanityURLPage.deleteVanityURL(vurl14101Title);
        Assert.assertEquals(vanityURLPage.getSystemMessage().trim(), vanityURLPage.getLocalizedString("message.virtuallink.delete"));
        Assert.assertFalse(vanityURLPage.doesVanityURLExist(vurl14101Title));
    }

    @Test (groups = {"VanityURLs"})
    public void tc14102_AddVanityURLToExternalURLWithParametersOnDemoHost() throws Exception{
    	backendMgr.loadPage(demoServerURL + "admin"); 
        IPortletMenu portletMenu = backendMgr.getPageObject(IPortletMenu.class);
        IVanityURLsPage vanityURLPage = portletMenu.getVanityURLsPage();
        String vurl14102Title = "14102 Vanity URL";
        String vurl14102URL = "14102";

        String targetHost = new URL(demoServerURL).getHost();
        vanityURLPage.selectBackendHost(targetHost);
                
        // verify vanity url does not already exist
        Assert.assertFalse(vanityURLPage.doesVanityURLExist(vurl14102Title));

        // add url
        vanityURLPage.addVanityURLToHost(vurl14102Title, targetHost, vurl14102URL, "https://www.google.com/search?num=100&q=dotcms&oq=dotcms");
        Assert.assertEquals(vanityURLPage.getSystemMessage().trim(), vanityURLPage.getLocalizedString("message.virtuallink.save"));
        
        // verify it is listed and working on demo host
        Assert.assertTrue(vanityURLPage.doesVanityURLExist(vurl14102Title));
        IBasePage page = frontendMgr.loadPage(demoServerURL + vurl14102URL);
        sleep();
        Assert.assertTrue(page.getTitle().equals("dotcms - Google Search"), "ERROR - Mapping for vanity URL does not seem to be functioning properly:  " + vurl14102URL);

        // delete and verify it is no longer listed
        vanityURLPage.deleteVanityURL(vurl14102Title);
        Assert.assertEquals(vanityURLPage.getSystemMessage().trim(), vanityURLPage.getLocalizedString("message.virtuallink.delete"));
        Assert.assertFalse(vanityURLPage.doesVanityURLExist(vurl14102Title));
    }
    
    @Test (groups = {"VanityURLs"})
    public void tc14105_VanityURLWithAbsoluteAddressOnANewHost() throws Exception{
    	backendMgr.loadPage(demoServerURL + "admin"); 
        IPortletMenu portletMenu = backendMgr.getPageObject(IPortletMenu.class);
        IHostPage hostPage = portletMenu.getHostPage();
    	
        String hostName = "qahost01.dotcms.com";
///*
        // verify Host does not already exist
       Assert.assertFalse(hostPage.doesHostExist(hostName));
        	      
        // add host
        hostPage.addBlankHost(hostName);
        Thread.sleep(2000);
        
        // verify it was created and listed on page
       Assert.assertTrue(hostPage.doesHostExist(hostName));

       // setup folder for html page
       ISiteBrowserPage siteBrowserPage = portletMenu.getSiteBrowserPage();
       siteBrowserPage.selectBackendHost(hostName);
       Thread.sleep(2000);
       portletMenu = backendMgr.getPageObject(IPortletMenu.class);
       siteBrowserPage = portletMenu.getSiteBrowserPage();
       siteBrowserPage.createFolder("", "home");
       Assert.assertEquals(siteBrowserPage.getSystemMessage().trim(), LanguageManager.getValue("message.folder.save"));

       siteBrowserPage.selectFolder("home");
       
       // create html page
       siteBrowserPage.createHTMLPage("index.html", "qademo.dotcms.com Quest - 2 Column (Left Bar)");       
//       Assert.assertEquals(siteBrowserPage.getSystemMessage().trim(), LanguageManager.getValue("message.content.saved"));
       
       // escape preview page
       IBackendSideMenuPage sideMenu = SeleniumPageManager.getBackEndPageManager().getPageObject(IBackendSideMenuPage.class);
       portletMenu = sideMenu.gotoAdminScreen();

       // verify vanity url does not already exist
       portletMenu = backendMgr.getPageObject(IPortletMenu.class);
       IVanityURLsPage vanityURLPage = portletMenu.getVanityURLsPage();
       Assert.assertFalse(vanityURLPage.doesVanityURLExist("test1"));
       Assert.assertFalse(vanityURLPage.doesVanityURLExist("test2"));

       // create vanity URLs
       vanityURLPage.addVanityURLToHost("test1", hostName, "/test1", "/home/index.html");
       vanityURLPage.addVanityURLToHost("test2", hostName, "/test2", "http://"+ hostName + ":8080/home/index.html");
       
       // verify vanity URLs exist
       Assert.assertTrue(vanityURLPage.doesVanityURLExist("test1"));
       Assert.assertTrue(vanityURLPage.doesVanityURLExist("test2"));

       // test vanity URLs
       // TODO
       IBasePage page1 = frontendMgr.loadPage("http://"+ hostName + ":8080/test1");
       Assert.assertTrue(page1.getTitle().equals("index.html - Quest Financial"), "ERROR - Mapping for vanity URL does not seem to be functioning properly:  /test1");

       IBasePage page2 = frontendMgr.loadPage("http://"+ hostName + ":8080/test2");
       Assert.assertTrue(page2.getTitle().equals("index.html - Quest Financial"), "ERROR - Mapping for vanity URL does not seem to be functioning properly:  /test2");

       // delete vanity URLs
       // TODO - have to delete vanity urls because of a bug
       vanityURLPage = portletMenu.getVanityURLsPage();
       vanityURLPage.deleteVanityURL("test1");
       vanityURLPage.deleteVanityURL("test2");
       Assert.assertFalse(vanityURLPage.doesVanityURLExist("test1"));
       Assert.assertFalse(vanityURLPage.doesVanityURLExist("test2"));

       // set host back to qademo.dotcms.com
       portletMenu.selectBackendHost("qademo.dotcms.com");
       Thread.sleep(500);
//*/
       // Stop and delete newly created Host
       hostPage = portletMenu.getHostPage();
       Thread.sleep(1000);
       hostPage.stopHost(hostName, true);
       Thread.sleep(1000);						// TODO - remove cluginess and be able to remove this sleep call
       hostPage.archiveHost(hostName, true);
       hostPage.toggleShowArchived();
       hostPage.deleteHost(hostName, true);
    }
}
