package com.dotcms.qa.testng.tests;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.AfterGroups;
import org.testng.annotations.BeforeGroups;
import org.testng.annotations.Test;

import com.dotcms.qa.selenium.pages.backend.IConfigurationPage;
import com.dotcms.qa.selenium.pages.backend.IContainerAddOrEditPage;
import com.dotcms.qa.selenium.pages.backend.IContainersPage;
import com.dotcms.qa.selenium.pages.backend.IHostPage;
import com.dotcms.qa.selenium.pages.backend.ILoginPage;
import com.dotcms.qa.selenium.pages.backend.IPortletMenu;
import com.dotcms.qa.selenium.pages.backend.IPublishingEnvironments;
import com.dotcms.qa.selenium.pages.backend.IPublishingQueuePage;
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
	private String environmentName="Sender1";

	//Authoring server properties
	private SeleniumPageManager authoringBackendMgr = null;
	private String authoringServer = null;
	private String authoringServerPort = null;
	private ILoginPage authoringLoginPage = null;
	private IPortletMenu authoringPortletMenu = null;
	private IConfigurationPage authoringConfigurationPage=null;
	private IPublishingEnvironments authoringPublishingEnvironments = null;

	//Receiver server properties
	private SeleniumPageManager receiverBackendMgr = null;
	private String receiverServer = null;
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

			authoringServer = config.getProperty("pushpublising.autoring.server");
			authoringServerPort = config.getProperty("pushpublising.autoring.server.port");
			logger.info("Authoring server = " + authoringServer+":"+authoringServerPort);
			
			receiverServer = config.getProperty("pushpublising.receiver.server");
			receiverServerPort = config.getProperty("pushpublising.receiver.server.port");
			logger.info("Receiver server = " + authoringServer+":"+receiverServerPort);

			//login Receiver server
			receiverBackendMgr = RegressionSuiteEnv.getBackendPageManager(serversProtocol+"://"+receiverServer+":"+receiverServerPort+"/");
			receiverLoginPage = receiverBackendMgr.getPageObject(ILoginPage.class);
			receiverLoginPage.login(backendUserEmail, backendUserPassword);	    

			//Validate if push publishing servers are configured
			receiverPortletMenu = receiverBackendMgr.getPageObject(IPortletMenu.class);
			receiverConfigurationPage = receiverPortletMenu.getConfigurationPage();
			receiverPublishingEnvironments = receiverConfigurationPage.getPublishingEnvironmentsTab();
			receiverPublishingEnvironments.addReceiveFrom(authoringServer, authoringServer, serversKey);
			
			//login Authoring server
			authoringBackendMgr = RegressionSuiteEnv.getBackendPageManager(serversProtocol+"://"+authoringServer+":"+authoringServerPort+"/");
			authoringLoginPage = authoringBackendMgr.getPageObject(ILoginPage.class);
			authoringLoginPage.login(backendUserEmail, backendUserPassword);

			//Validate if push publishing servers are configured
			authoringPortletMenu = authoringBackendMgr.getPageObject(IPortletMenu.class);
			authoringConfigurationPage = authoringPortletMenu.getConfigurationPage();
			
			//load the Push publish environment tab
			authoringPublishingEnvironments = authoringConfigurationPage.getPublishingEnvironmentsTab();
			List<String> whoCanUse = new ArrayList<String>();
			whoCanUse.add("Admin User");
			authoringPublishingEnvironments.createEnvironment(environmentName, whoCanUse, "pushToOne");
			//Adding receiver server to the environment
			authoringPublishingEnvironments.addServerToEnvironment(environmentName, receiverServer, receiverServer, receiverServerPort, serversProtocol, serversKey);
	
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
			authoringBackendMgr = RegressionSuiteEnv.getBackendPageManager(serversProtocol+"://"+authoringServer+":"+authoringServerPort+"/");
			authoringLoginPage = authoringBackendMgr.getPageObject(ILoginPage.class);
			try{
				authoringLoginPage.login(backendUserEmail, backendUserPassword);
			}catch(Exception e){
				//its already logged
			}
			//Validate if push publishing servers are configured
			authoringPortletMenu = authoringBackendMgr.getPageObject(IPortletMenu.class);
			authoringConfigurationPage = authoringPortletMenu.getConfigurationPage();
			
			//load the Push publish environment tab
			authoringPublishingEnvironments = authoringConfigurationPage.getPublishingEnvironmentsTab();
			authoringPublishingEnvironments.deleteEnvironment(environmentName);
			authoringBackendMgr.logoutBackend();
			
			//cleaning receiver
			receiverBackendMgr = RegressionSuiteEnv.getBackendPageManager(serversProtocol+"://"+receiverServer+":"+receiverServerPort+"/");
			receiverLoginPage = receiverBackendMgr.getPageObject(ILoginPage.class);
			receiverLoginPage.login(backendUserEmail, backendUserPassword);
			
			//Validate if push publishing servers are configured
			receiverPortletMenu = receiverBackendMgr.getPageObject(IPortletMenu.class);
			receiverConfigurationPage = receiverPortletMenu.getConfigurationPage();
			receiverPublishingEnvironments = receiverConfigurationPage.getPublishingEnvironmentsTab();
			receiverPublishingEnvironments.deleteReceiveFromServer(authoringServer);
			receiverBackendMgr.logoutBackend();

			logger.info("**PushPublishTests.teardown() ending**");
		}catch(Exception e) {
			logger.error("ERROR - PushPublishTests.teardown()", e);
			throw(e);
		}
	}
	
	/**
	 * CONTAINERS PUSH PUBLIHING TESTS
	 */
	
	/**
	 * 	Add a new Container and push to remote server:
	 * http://qa.dotcms.com/index.php?/cases/view/559
	 * @throws Exception
	 */
	@Test (groups = {"PushPublishing"})
	public void tc559_AddNewContainerAndPush() throws Exception {
		IPortletMenu portletMenu = authoringBackendMgr.getPageObject(IPortletMenu.class);
		IContainersPage containersPage = portletMenu.getContainersPage();
		
		IContainerAddOrEditPage addContainerPage = containersPage.getAddContainerPage();
		
		String containerTitle="Test 559 Container";
		String containerCode ="<h2>Test 559</h2><br/><p>This is a test for push publishing</p>";
		//simple container
		Map<String, String> container = new HashMap<String,String>();
		container.put("titleField", containerTitle);
		container.put("friendlyNameField", containerTitle);
		container.put("code", containerCode);
		
		addContainerPage.setFields(container);
		containersPage = addContainerPage.saveAndPublish();
		
		String bundleName = "test559";
		containersPage.addToBundle(containerTitle, bundleName);
		
		IPublishingQueuePage publishingQueuePage = portletMenu.getPublishingQueuePage();
		publishingQueuePage.getBundlesTab();
		publishingQueuePage.pushPublishBundle(bundleName);
		 
	}
}
