package com.dotcms.qa.testng.tests;


import com.dotcms.qa.selenium.util.SeleniumConfig;
import com.dotcms.qa.selenium.util.SeleniumPageManager;
import com.dotcms.qa.selenium.pages.IBasePage;
import com.dotcms.qa.selenium.pages.backend.*;

import org.apache.log4j.Logger;
import org.openqa.selenium.support.ui.Sleeper;
import org.testng.Assert;
import org.testng.annotations.AfterGroups;
import org.testng.annotations.BeforeGroups;
import org.testng.annotations.Test;

/**
 * This class manage the TestRail suite of test for Hosts
 * @author Bryan Boza
 * @author Brent Griffin
 * @author Oswaldo Gallango
 * @since 01/08/2015
 * @version 3.0
 */
public class HostTest {
	private static final Logger logger = Logger.getLogger(HostTest.class);

	private SeleniumPageManager backendMgr = null;
	private SeleniumPageManager frontendMgr = null;
	private String demoServerURL = null;
	private ILoginPage loginPage = null;

	//Backend User Info
	private String backendUserEmail = null;
	private String backendUserPassword = null;

	@BeforeGroups (groups = {"Host"})
	public void init() throws Exception {
		SeleniumConfig config = SeleniumConfig.getConfig();
		demoServerURL = config.getProperty("demoServerURL");
		logger.info("demoServerURL = " + demoServerURL);

		// create frontendMgr for verification of frontend functionality
		frontendMgr = RegressionSuiteEnv.getFrontendPageManager();

		// login
		backendUserEmail = config.getProperty("backend.user.Email");
		backendUserPassword = config.getProperty("backend.user.Password");
		backendMgr = RegressionSuiteEnv.getBackendPageManager();
		loginPage = backendMgr.getPageObject(ILoginPage.class);
		loginPage.login(backendUserEmail, backendUserPassword);       
	}

	@AfterGroups (groups = {"Host"})
	public void teardown() throws Exception {
		// logout
		backendMgr.logoutBackend();
	}

	/**
	 * Test the add host functionality case. See here:
	 * http://qa.dotcms.com/index.php?/cases/view/196
	 * @throws Exception
	 */
	@Test (groups = {"Host"})
	public void tc196_AddHostManually() throws Exception  {
		IPortletMenu portletMenu = backendMgr.getPageObject(IPortletMenu.class);
		IHostPage hostPage = portletMenu.getHostPage();

		String hostName = "qahost01.dotcms.com";

		// verify Host does not already exist
		Assert.assertFalse(hostPage.doesHostExist(hostName),"ERROR - The host ( "+hostName+" ) should not exist at this time");

		// add host
		hostPage.addBlankHost(hostName);
		hostPage.sleep(5000);

		// verify it was created and listed on page
		Assert.assertTrue(hostPage.doesHostExist(hostName),"ERROR - The host ( "+hostName+" ) was not created");

		// verify new host responds to traffic
		IBasePage homePage = frontendMgr.loadPage("http://" + hostName + ":8080/");
		String title = homePage.getTitle();
		Assert.assertTrue(title != null && title.startsWith("dotCMS: Page not found"),"ERROR - The host should not have a page set");

		/*
		hostPage.stopHost(hostName, true);
		hostPage.sleep(500);						// TODO - remove cluginess and be able to remove this sleep call
		hostPage.archiveHost(hostName, true);
		hostPage.toggleShowArchived();
		hostPage.deleteHost(hostName, true);

		// verify host is no longer listed on page
		hostPage.reload();
		Assert.assertFalse(hostPage.doesHostExist(hostName),"ERROR - The host ( "+hostName+" ) should not exist at this time");
		hostPage.toggleShowArchived();
		Assert.assertFalse(hostPage.doesHostExist(hostName),"ERROR - The host ( "+hostName+" ) should not exist at this time");

		// verify host is no longer responding to requests
		IBasePage demoHomePage = frontendMgr.loadPage(demoServerURL);
		String demoHomePageTitle = demoHomePage.getTitle();
		homePage = frontendMgr.loadPage("http://" + hostName + ":8080/");
		title = homePage.getTitle();
		Assert.assertTrue(demoHomePageTitle != null && title != null && demoHomePageTitle.equals(title), "Page titles do not match.  demoHomePageTitle=|" + demoHomePageTitle + "| title = |" + title + "|");
		 */
	}

	/**
	 * Test the add new host variable .See here:
	 * http://qa.dotcms.com/index.php?/cases/view/197
	 * @throws Exception
	 */
	@Test (groups = {"Host"})
	public void tc197_AddNewHostVariable() throws Exception {
		IPortletMenu portletMenu = backendMgr.getPageObject(IPortletMenu.class);
		IHostPage hostPage = portletMenu.getHostPage();

		String hostName = "qashared";
		String hostVariableName = "var1";
		String hostVariableKey = "key1";
		String hostVariableValue = "value1";

		Assert.assertFalse(hostPage.doesHostVariableExist(hostName, hostVariableName),"ERROR -  the host variable ("+hostVariableName+") should not exist in host ("+hostName+")");
		hostPage.addHostVariable(hostName, hostVariableName, hostVariableKey, hostVariableValue);
		Assert.assertTrue(hostPage.doesHostVariableExist(hostName, hostVariableName),"ERROR -  the host variable ("+hostVariableName+") does not exist in host ("+hostName+")");
		hostPage = backendMgr.getPageObject(IHostPage.class);
		hostPage.deleteHostVariable(hostName, hostVariableName, true);
		Assert.assertFalse(hostPage.doesHostVariableExist(hostName, hostVariableName),"ERROR -  the host variable ("+hostVariableName+") shoud not exist in host ("+hostName+")");
	}

	/**
	 * Test the edit host add binary thubnail .See here:
	 * http://qa.dotcms.com/index.php?/cases/view/198
	 * @throws Exception
	 */
	@Test (groups = {"Host"})
	public void tc198_AddHostBinaryThumbnail() throws Exception {
		IPortletMenu portletMenu = backendMgr.getPageObject(IPortletMenu.class);
		IHostPage hostPage = portletMenu.getHostPage();

		String hostName = "qashared";

		Assert.assertFalse(hostPage.doesHostHaveHostThumbnail(hostName),"ERROR -  the host ("+hostName+") should not have a host thumbnail");
		hostPage.addHostThumbnail(hostName);
		hostPage.reload();
		Assert.assertTrue(hostPage.doesHostHaveHostThumbnail(hostName),"ERROR -  the host ("+hostName+") does not have a host thumbnail");
		hostPage.removeHostThumbnail(hostName);
		hostPage.sleep(5000);
		Assert.assertFalse(hostPage.doesHostHaveHostThumbnail(hostName),"ERROR -  the host ("+hostName+") should not have a host thumbnail");
	}

	/**
	 * Test the delete host added manually functionality case. See here:
	 * http://qa.dotcms.com/index.php?/cases/view/199
	 * @throws Exception
	 */
	@Test (groups = {"Host"})
	public void tc199_DeleteHostAddedManually() throws Exception  {
		IPortletMenu portletMenu = backendMgr.getPageObject(IPortletMenu.class);
		IHostPage hostPage = portletMenu.getHostPage();

		String hostName = "qahost01.dotcms.com";

		// verify it was created and listed on page
		Assert.assertTrue(hostPage.doesHostExist(hostName),"ERROR - The host ( "+hostName+" ) was not created");

		// verify new host responds to traffic
		IBasePage homePage = frontendMgr.loadPage("http://" + hostName + ":8080/");
		String title = homePage.getTitle();

		hostPage.stopHost(hostName, true);
		hostPage.sleep(500);						// TODO - remove cluginess and be able to remove this sleep call
		hostPage.archiveHost(hostName, true);
		hostPage.toggleShowArchived();
		hostPage.deleteHost(hostName, true);

		// verify host is no longer listed on page
		hostPage.reload();
		Assert.assertFalse(hostPage.doesHostExist(hostName),"ERROR - The host ( "+hostName+" ) should not exist at this time");
		hostPage.toggleShowArchived();
		Assert.assertFalse(hostPage.doesHostExist(hostName),"ERROR - The host ( "+hostName+" ) should not exist at this time");

		// verify host is no longer responding to requests
		IBasePage demoHomePage = frontendMgr.loadPage(demoServerURL);
		String demoHomePageTitle = demoHomePage.getTitle();
		homePage = frontendMgr.loadPage("http://" + hostName + ":8080/");
		title = homePage.getTitle();
		Assert.assertTrue(demoHomePageTitle != null && title != null && demoHomePageTitle.equals(title), "Page titles do not match.  demoHomePageTitle=|" + demoHomePageTitle + "| title = |" + title + "|");
	}

	/**
	 * Test the delete host added manually functionality case. See here:
	 * http://qa.dotcms.com/index.php?/cases/view/200
	 * @throws Exception
	 */
	@Test (groups = {"Host"})
	public void tc200_DeleteHostAddedThroughCopyHost() throws Exception  {
		IPortletMenu portletMenu = backendMgr.getPageObject(IPortletMenu.class);
		IHostPage hostPage = portletMenu.getHostPage();

		String hostName = "qahost02.dotcms.com";
		String hostToCopy  ="m.qademo.dotcms.com";

		// verify Host does not already exist
		Assert.assertFalse(hostPage.doesHostExist(hostName),"ERROR - The host ( "+hostName+" ) should not exist at this time");

		// copy host
		hostPage.addCopyExistingHost(hostName, hostToCopy);
		hostPage.sleep(10000);

		// verify it was created and listed on page
		Assert.assertTrue(hostPage.doesHostExist(hostName),"ERROR - The host ( "+hostName+" ) was not created");

		// verify new host responds to traffic
		IBasePage homePage = frontendMgr.loadPage("http://" + hostName + ":8080/");
		String title = homePage.getTitle();
		Assert.assertTrue(title != null && title.startsWith("Quest Financial"),"ERROR - The host should not have a page set");

		// delete host
		hostPage.stopHost(hostName, true);
		hostPage.sleep(500);
		hostPage.archiveHost(hostName, true);
		hostPage.toggleShowArchived();
		hostPage.deleteHost(hostName, true);

		hostPage.sleep(5000);
		// verify it is no longer listed on page
		Assert.assertFalse(hostPage.doesHostExist(hostName),"ERROR - The host ( "+hostName+" ) should not exist at this time");

		// verify host is no longer responding to requests
		IBasePage demoHomePage = frontendMgr.loadPage(demoServerURL);
		String demoHomePageTitle = demoHomePage.getTitle();
		homePage = frontendMgr.loadPage("http://" + hostName + ":8080/");
		title = homePage.getTitle();
		Assert.assertTrue(demoHomePageTitle != null && title != null && demoHomePageTitle.equals(title), "Page titles do not match.  demoHomePageTitle=|" + demoHomePageTitle + "| title = |" + title + "|");
	}

	/**
	 * Test the copy host functionality. See here:
	 * http://qa.dotcms.com/index.php?/cases/view/206
	 * @throws Exception
	 */
	@Test (groups = {"Host"})
	public void tc206_CopyHost() throws Exception {
		IPortletMenu portletMenu = backendMgr.getPageObject(IPortletMenu.class);
		IHostPage hostPage = portletMenu.getHostPage();

		String hostName = "qahost02.dotcms.com";
		String hostToCopy  ="m.qademo.dotcms.com";

		// verify Host does not already exist
		Assert.assertFalse(hostPage.doesHostExist(hostName),"ERROR - The host ( "+hostName+" ) should not exist at this time");

		// copy host
		hostPage.addCopyExistingHost(hostName, hostToCopy);
		hostPage.sleep(10000);

		// verify it was created and listed on page
		Assert.assertTrue(hostPage.doesHostExist(hostName),"ERROR - The host ( "+hostName+" ) was not created");

		// verify new host responds to traffic
		IBasePage homePage = frontendMgr.loadPage("http://" + hostName + ":8080/");
		String title = homePage.getTitle();
		Assert.assertTrue(title != null && title.startsWith("Quest Financial"),"ERROR - The host should not have a page set");

		// delete host
		hostPage.stopHost(hostName, true);
		hostPage.sleep(500);
		hostPage.archiveHost(hostName, true);
		hostPage.toggleShowArchived();
		hostPage.deleteHost(hostName, true);

		hostPage.sleep(5000);
		// verify it is no longer listed on page
		Assert.assertFalse(hostPage.doesHostExist(hostName),"ERROR - The host ( "+hostName+" ) should not exist at this time");

		// verify host is no longer responding to requests
		IBasePage demoHomePage = frontendMgr.loadPage(demoServerURL);
		String demoHomePageTitle = demoHomePage.getTitle();
		homePage = frontendMgr.loadPage("http://" + hostName + ":8080/");
		title = homePage.getTitle();
		Assert.assertTrue(demoHomePageTitle != null && title != null && demoHomePageTitle.equals(title), "Page titles do not match.  demoHomePageTitle=|" + demoHomePageTitle + "| title = |" + title + "|");
	}	
}
