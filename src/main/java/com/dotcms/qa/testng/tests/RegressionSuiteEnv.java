package com.dotcms.qa.testng.tests;

import java.util.Locale;
import java.util.Set;

import org.apache.log4j.Logger;

import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import com.dotcms.qa.selenium.util.SeleniumConfig;
import com.dotcms.qa.selenium.util.SeleniumPageManager;

public class RegressionSuiteEnv {
    private static final Logger logger = Logger.getLogger(RegressionSuiteEnv.class);
    
    private static SeleniumPageManager backendMgr = null;
    private static SeleniumPageManager frontendMgr = null;
    private static String demoServerURL = null;

    @BeforeSuite (alwaysRun = true)
    public void init() throws Exception {
    	logger.info("Locale = " + Locale.getDefault());
    	logger.info("file.encoding = " + System.getProperty("file.encoding"));
    	StringBuilder diagMsg = new StringBuilder("\r\n**************************\r\n");
        SeleniumConfig config = SeleniumConfig.getConfig();
        demoServerURL = config.getProperty("demoServerURL");
        Set<String> keys = System.getProperties().stringPropertyNames();
        for(String key : keys) {
            diagMsg.append(key + "=" + System.getProperty(key) + "\r\n");
        }       
        diagMsg.append("**************************");
        logger.info(diagMsg.toString());
    }
    
    @AfterSuite (alwaysRun = true)
    public void teardown() throws Exception {
        // shutdown
        logger.info("Shutting Down....");

        // logout of backend
    	if(backendMgr != null) {
	        backendMgr.logoutBackend();
	        backendMgr.shutdown();
	        backendMgr = null;
    	}
        
    	if(frontendMgr != null) {
    		frontendMgr.shutdown();
    		frontendMgr = null;
    	}
    }
    
    public static SeleniumPageManager getBackendPageManager() throws Exception {
    	if(backendMgr == null) {
            backendMgr = SeleniumPageManager.getPageManager();
            backendMgr.loadPage(demoServerURL + "admin"); 
            Thread.sleep(250);	// This pause improves login reliability with the chrome driver
    	}
    	return backendMgr;
    }
    
    public static SeleniumPageManager getFrontendPageManager() throws Exception {
    	if(frontendMgr == null) {
            frontendMgr = SeleniumPageManager.getNewPageManager();    		
    	}
    	return frontendMgr;
    }
}
