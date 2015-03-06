package com.dotcms.qa.testng.tests;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.AfterGroups;
import org.testng.annotations.BeforeGroups;
import org.testng.annotations.Test;

import com.dotcms.qa.selenium.pages.backend.IConfigurationPage;
import com.dotcms.qa.selenium.pages.backend.ILoginPage;
import com.dotcms.qa.selenium.pages.backend.IPortletMenu;
import com.dotcms.qa.selenium.pages.backend.IPublishingEnvironments;
import com.dotcms.qa.selenium.util.SeleniumConfig;
import com.dotcms.qa.selenium.util.SeleniumPageManager;

/**
 * This class manage the TestRail suite of test for Push Publishing
 * @author Oswaldo Gallango
 * @since 03/06/2015
 * @version 1.0
 */
public class PushPublishTest {

	//General properties
	private static final Logger logger = Logger.getLogger(HostTest.class);
	private SeleniumConfig config = null;
	private String backendUserEmail = null;
	private String backendUserPassword = null;
	
	
	//Push publishing general properties
	private String serversProtocol=null;
	private String serversKey=null;

	//Authoring server properties
	private SeleniumPageManager authoringBackendMgr = null;
	private String authoringServerURL = null;
	private String authoringServerPort = null;
	private ILoginPage authoringLoginPage = null;
	private IPortletMenu authoringPortletMenu = null;
	private IConfigurationPage authoringConfigurationPage=null;
	private IPublishingEnvironments authoringPublishingEnvironments = null;

	//Receiver server properties
	private SeleniumPageManager receiverBackendMgr = null;
	private String receiverServerURL = null;
	private String receiverServerPort = null;
	private ILoginPage receiverLoginPage = null;
	private IPortletMenu receiverPortletMenu = null;
	private IConfigurationPage receiverConfigurationPage=null;
	private IPublishingEnvironments receiverPublishingEnvironments = null;

	@BeforeGroups (groups = {"PushPublishing"})
	public void init() throws Exception {
		try {
			logger.info("**PushPublishTests.init() beginning**");
			
			config = SeleniumConfig.getConfig();
			backendUserEmail = config.getProperty("backend.user.Email");
			backendUserPassword = config.getProperty("backend.user.Password");

			serversProtocol=config.getProperty("pushpublising.server.protocol");
			serversKey=config.getProperty("pushpublising.server.key");

			//login Authoring server
			authoringServerURL = config.getProperty("pushpublising.autoring.server");
			authoringServerPort = config.getProperty("pushpublising.autoring.server.port");
			logger.info("serverURL = " + authoringServerURL);

			authoringBackendMgr = RegressionSuiteEnv.getBackendPageManager();
			authoringLoginPage = authoringBackendMgr.getPageObject(ILoginPage.class);
			authoringLoginPage.login(backendUserEmail, backendUserPassword);

			//Validate if push publishing servers are configured
			authoringPortletMenu = authoringBackendMgr.getPageObject(IPortletMenu.class);
			authoringConfigurationPage = authoringPortletMenu.getConfigurationPage();
			
			//load the Pushblish environment tab
			authoringPublishingEnvironments = authoringConfigurationPage.getPublishingEnvironmentsTab();
			List<String> whoCanUse = new ArrayList<String>();
			whoCanUse.add("Admin User");
			authoringPublishingEnvironments.createEnvironment("Sender 1", whoCanUse, "pushToOne");
			
			
			
/*
			//login Receiver server
			receiverServerURL = config.getProperty("pushpublising.autoring.server");
			receiverServerPort = config.getProperty("pushpublising.autoring.server.port");

			receiverBackendMgr = RegressionSuiteEnv.getBackendPageManager();
			receiverLoginPage = receiverBackendMgr.getPageObject(ILoginPage.class);
			receiverLoginPage.login(backendUserEmail, backendUserPassword);	    

			//Validate if push publishing servers are configured
			receiverPortletMenu = receiverBackendMgr.getPageObject(IPortletMenu.class);
			receiverConfigurationPage = receiverPortletMenu.getConfigurationPage();
			receiverConfigurationPage.getPublishingEnvironmentsTab();
	*/		
			logger.info("**PushPublishTests.init() ending**");
		}catch(Exception e) {
			logger.error("ERROR - PushPublishTests.init()", e);
			throw(e);
		}
	}

	@AfterGroups (groups = {"PushPublishing"})
	public void teardown() throws Exception {
		try {
			logger.info("**PushPublishTests.teardown() beginning**");
			// logout
			authoringBackendMgr.logoutBackend();
			receiverBackendMgr.logoutBackend();

			logger.info("**PushPublishTests.teardown() ending**");
		}catch(Exception e) {
			logger.error("ERROR - PushPublishTests.teardown()", e);
			throw(e);
		}
	}
	
	
	/**
	 * Test the edit user info functionality. Set here:
	 * http://qa.dotcms.com/index.php?/cases/view/258
	 * @throws Exception
	 */
	@Test (groups = {"PushPublishing"})
	public void tc559_EditUser() throws Exception {
		logger.info("is here");
	}
}
