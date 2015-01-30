package com.dotcms.qa.testng.tests;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.dotcms.qa.selenium.util.SeleniumConfig;
import com.dotcms.qa.selenium.util.SeleniumPageManager;
import com.dotcms.qa.selenium.pages.IBasePage;
import com.dotcms.qa.selenium.pages.backend.*;
import com.dotcms.qa.util.WebKeys;
import com.thoughtworks.selenium.webdriven.commands.GetLocation;

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
	private String mobileDemoServerURL = null;
	private ILoginPage loginPage = null;

	//Backend User Info
	private String backendUserEmail = null;
	private String backendUserPassword = null;

	//Test Host
	private String qasharedHostName = "qashared";
	private String demoHostName = "qademo.dotcms.com";
	private String mobiledemoHostName = "m.qademo.dotcms.com";
	private String testHostName1 = "qahost01.dotcms.com";
	private String testHostName2 = "qahost02.dotcms.com";
	private String textFieldLabel = "MyHostTest";

	@BeforeGroups (groups = {"Host"})
	public void init() throws Exception {
		SeleniumConfig config = SeleniumConfig.getConfig();
		demoServerURL = config.getProperty("demoServerURL");
		mobileDemoServerURL = config.getProperty("mobileServerURL");
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
	//@Test (groups = {"Host"})
	public void tc196_AddHostManually() throws Exception  {
		IPortletMenu portletMenu = backendMgr.getPageObject(IPortletMenu.class);
		IHostPage hostPage = portletMenu.getHostPage();

		// verify Host does not already exist
		Assert.assertFalse(hostPage.doesHostExist(testHostName1),"ERROR - The host ( "+testHostName1+" ) should not exist at this time");

		// add host
		hostPage.addBlankHost(testHostName1);
		hostPage.sleep(5);

		// verify it was created and listed on page
		Assert.assertTrue(hostPage.doesHostExist(testHostName1),"ERROR - The host ( "+testHostName1+" ) was not created");

		// verify new host responds to traffic
		IBasePage homePage = frontendMgr.loadPage("http://" + testHostName1 + ":8080/");
		String title = homePage.getTitle();
		Assert.assertTrue(title != null && title.startsWith("dotCMS: Page not found"),"ERROR - The host should not have a page set");

		/*
		hostPage.stopHost(testHostName1, true);
		hostPage.sleep(500);						// TODO - remove cluginess and be able to remove this sleep call
		hostPage.archiveHost(testHostName1, true);
		hostPage.toggleShowArchived();
		hostPage.deleteHost(testHostName1, true);

		// verify host is no longer listed on page
		hostPage.reload();
		Assert.assertFalse(hostPage.doesHostExist(testHostName1),"ERROR - The host ( "+testHostName1+" ) should not exist at this time");
		hostPage.toggleShowArchived();
		Assert.assertFalse(hostPage.doesHostExist(testHostName1),"ERROR - The host ( "+testHostName1+" ) should not exist at this time");

		// verify host is no longer responding to requests
		IBasePage demoHomePage = frontendMgr.loadPage(demoServerURL);
		String demoHomePageTitle = demoHomePage.getTitle();
		homePage = frontendMgr.loadPage("http://" + testHostName1 + ":8080/");
		title = homePage.getTitle();
		Assert.assertTrue(demoHomePageTitle != null && title != null && demoHomePageTitle.equals(title), "Page titles do not match.  demoHomePageTitle=|" + demoHomePageTitle + "| title = |" + title + "|");
		 */
	}

	/**
	 * Test the add new host variable .See here:
	 * http://qa.dotcms.com/index.php?/cases/view/197
	 * @throws Exception
	 */
	//@Test (groups = {"Host"})
	public void tc197_AddNewHostVariable() throws Exception {
		IPortletMenu portletMenu = backendMgr.getPageObject(IPortletMenu.class);
		IHostPage hostPage = portletMenu.getHostPage();

		String hostVariableName = "var1";
		String hostVariableKey = "key1";
		String hostVariableValue = "value1";

		Assert.assertFalse(hostPage.doesHostVariableExist(qasharedHostName, hostVariableName),"ERROR -  the host variable ("+hostVariableName+") should not exist in host ("+qasharedHostName+")");
		hostPage.addHostVariable(qasharedHostName, hostVariableName, hostVariableKey, hostVariableValue);
		hostPage.sleep(1);
		Assert.assertTrue(hostPage.doesHostVariableExist(qasharedHostName, hostVariableName),"ERROR -  the host variable ("+hostVariableName+") does not exist in host ("+qasharedHostName+")");
		hostPage = backendMgr.getPageObject(IHostPage.class);
		hostPage.deleteHostVariable(qasharedHostName, hostVariableName, true);
		hostPage.sleep(1);
		Assert.assertFalse(hostPage.doesHostVariableExist(qasharedHostName, hostVariableName),"ERROR -  the host variable ("+hostVariableName+") shoud not exist in host ("+qasharedHostName+")");
	}

	/**
	 * Test the edit host add binary thubnail .See here:
	 * http://qa.dotcms.com/index.php?/cases/view/198
	 * @throws Exception
	 */
	//@Test (groups = {"Host"})
	public void tc198_AddHostBinaryThumbnail() throws Exception {
		IPortletMenu portletMenu = backendMgr.getPageObject(IPortletMenu.class);
		IHostPage hostPage = portletMenu.getHostPage();

		Assert.assertFalse(hostPage.doesHostHaveHostThumbnail(qasharedHostName),"ERROR -  the host ("+qasharedHostName+") should not have a host thumbnail");
		hostPage.addHostThumbnail(qasharedHostName);
		hostPage.sleep(5);
		hostPage.reload();
		Assert.assertTrue(hostPage.doesHostHaveHostThumbnail(qasharedHostName),"ERROR -  the host ("+qasharedHostName+") does not have a host thumbnail");
		hostPage.removeHostThumbnail(qasharedHostName);
		hostPage.sleep(5);
		Assert.assertFalse(hostPage.doesHostHaveHostThumbnail(qasharedHostName),"ERROR -  the host ("+qasharedHostName+") should not have a host thumbnail");
	}

	/**
	 * Test the delete host added manually functionality case. See here:
	 * http://qa.dotcms.com/index.php?/cases/view/199
	 * @throws Exception
	 */
	//@Test (groups = {"Host"})
	public void tc199_DeleteHostAddedManually() throws Exception  {
		IPortletMenu portletMenu = backendMgr.getPageObject(IPortletMenu.class);
		IHostPage hostPage = portletMenu.getHostPage();

		// verify it was created and listed on page
		Assert.assertTrue(hostPage.doesHostExist(testHostName1),"ERROR - The host ( "+testHostName1+" ) was not created");

		// verify new host responds to traffic
		IBasePage homePage = frontendMgr.loadPage("http://" + testHostName1 + ":8080/");
		String title = homePage.getTitle();

		hostPage.stopHost(testHostName1, true);
		hostPage.sleep(1);						// TODO - remove cluginess and be able to remove this sleep call
		hostPage.archiveHost(testHostName1, true);
		hostPage.sleep(1);
		hostPage.toggleShowArchived();
		hostPage.deleteHost(testHostName1, true);
		hostPage.sleep(1);
		// verify host is no longer listed on page
		hostPage.reload();
		Assert.assertFalse(hostPage.doesHostExist(testHostName1),"ERROR - The host ( "+testHostName1+" ) should not exist at this time");
		hostPage.toggleShowArchived();
		Assert.assertFalse(hostPage.doesHostExist(testHostName1),"ERROR - The host ( "+testHostName1+" ) should not exist at this time");

		// verify host is no longer responding to requests
		IBasePage demoHomePage = frontendMgr.loadPage(demoServerURL);
		String demoHomePageTitle = demoHomePage.getTitle();
		homePage = frontendMgr.loadPage("http://" + testHostName1 + ":8080/");
		title = homePage.getTitle();
		Assert.assertTrue(demoHomePageTitle != null && title != null && demoHomePageTitle.equals(title), "Page titles do not match.  demoHomePageTitle=|" + demoHomePageTitle + "| title = |" + title + "|");
	}

	/**
	 * Test the delete host added manually functionality case. See here:
	 * http://qa.dotcms.com/index.php?/cases/view/200
	 * @throws Exception
	 */
	//@Test (groups = {"Host"})
	public void tc200_DeleteHostAddedThroughCopyHost() throws Exception  {
		IPortletMenu portletMenu = backendMgr.getPageObject(IPortletMenu.class);
		IHostPage hostPage = portletMenu.getHostPage();

		String hostToCopy  = mobiledemoHostName;

		// verify Host does not already exist
		Assert.assertFalse(hostPage.doesHostExist(testHostName2),"ERROR - The host ( "+testHostName2+" ) should not exist at this time");

		// copy host
		hostPage.addCopyExistingHost(testHostName2, hostToCopy);
		hostPage.sleep(10);

		// verify it was created and listed on page
		Assert.assertTrue(hostPage.doesHostExist(testHostName2),"ERROR - The host ( "+testHostName2+" ) was not created");

		// verify new host responds to traffic
		IBasePage homePage = frontendMgr.loadPage("http://" + testHostName2 + ":8080/");
		String title = homePage.getTitle();
		Assert.assertTrue(title != null && title.startsWith("Quest Financial"),"ERROR - The host should not have a page set");

		// delete host
		hostPage.stopHost(testHostName2, true);
		hostPage.sleep(1);
		hostPage.archiveHost(testHostName2, true);
		hostPage.toggleShowArchived();
		hostPage.deleteHost(testHostName2, true);

		hostPage.sleep(10);
		// verify it is no longer listed on page
		Assert.assertFalse(hostPage.doesHostExist(testHostName2),"ERROR - The host ( "+testHostName2+" ) should not exist at this time");

		// verify host is no longer responding to requests
		IBasePage demoHomePage = frontendMgr.loadPage(demoServerURL);
		String demoHomePageTitle = demoHomePage.getTitle();
		homePage = frontendMgr.loadPage("http://" + testHostName2 + ":8080/");
		title = homePage.getTitle();
		Assert.assertTrue(demoHomePageTitle != null && title != null && demoHomePageTitle.equals(title), "Page titles do not match.  demoHomePageTitle=|" + demoHomePageTitle + "| title = |" + title + "|");
	}

	/**
	 * Test the stop host functionality. See here:
	 * http://qa.dotcms.com/index.php?/cases/view/201
	 * @throws Exception
	 */
	//@Test (groups = {"Host"})
	public void tc201_StopAHost() throws Exception {
		IPortletMenu portletMenu = backendMgr.getPageObject(IPortletMenu.class);
		IHostPage hostPage = portletMenu.getHostPage();

		Assert.assertTrue(hostPage.isHostActive(mobiledemoHostName), "ERROR -  This Host ("+mobiledemoHostName+") should be  active at this moment");
		hostPage.stopHost(mobiledemoHostName, true);
		hostPage.sleep(1);
		Assert.assertFalse(hostPage.isHostActive(mobiledemoHostName), "ERROR -  This Host ("+mobiledemoHostName+") should not be active at this moment");

	}

	/**
	 * Test the activate host functionality. See here:
	 * http://qa.dotcms.com/index.php?/cases/view/202
	 * @throws Exception
	 */
	//@Test (groups = {"Host"})
	public void tc202_ActivateHost() throws Exception {
		IPortletMenu portletMenu = backendMgr.getPageObject(IPortletMenu.class);
		IHostPage hostPage = portletMenu.getHostPage();

		Assert.assertFalse(hostPage.isHostActive(mobiledemoHostName), "ERROR -  This Host ("+mobiledemoHostName+") should not be active at this moment");
		hostPage.startHost(mobiledemoHostName, true);
		hostPage.sleep(1);
		Assert.assertTrue(hostPage.isHostActive(mobiledemoHostName), "ERROR -  This Host ("+mobiledemoHostName+") should be active at this moment");
	}

	/**
	 * Test changing default host functionality. See here:
	 * http://qa.dotcms.com/index.php?/cases/view/203
	 * @throws Exception
	 */
	//@Test (groups = {"Host"})
	public void tc203_ChangeDefaultHost() throws Exception {
		IPortletMenu portletMenu = backendMgr.getPageObject(IPortletMenu.class);
		IHostPage hostPage = portletMenu.getHostPage();

		Assert.assertFalse(hostPage.isHostDefault(mobiledemoHostName), "ERROR -  This Host ("+mobiledemoHostName+") should not be a default host at this moment");
		hostPage.makeDefultHost(mobiledemoHostName, true);
		hostPage.sleep(1);
		Assert.assertTrue(hostPage.isHostDefault(mobiledemoHostName), "ERROR -  This Host ("+mobiledemoHostName+") should be a default host at this moment");
		Assert.assertFalse(hostPage.isHostDefault(demoHostName), "ERROR -  This Host ("+demoHostName+") should not be a default host at this moment");

		/** 
		 * some times the change default host left inactive the previous default host
		 */
		if(!hostPage.isHostActive(demoHostName)){
			hostPage.startHost(demoHostName, true);
		}
		hostPage.makeDefultHost(demoHostName, true);
		hostPage.sleep(1);
		Assert.assertTrue(hostPage.isHostDefault(demoHostName), "ERROR -  This Host ("+demoHostName+") should be a default host at this moment");
		Assert.assertFalse(hostPage.isHostDefault(mobiledemoHostName), "ERROR -  This Host ("+mobiledemoHostName+") should not be a default host at this moment");

	}

	/**
	 * Test adding text field to host Structure. See here:
	 * http://qa.dotcms.com/index.php?/cases/view/204
	 * @throws Exception
	 */
	//@Test (groups = {"Host"})
	public void tc204_AddTextFieldToHostStructure() throws Exception {
		IPortletMenu portletMenu = backendMgr.getPageObject(IPortletMenu.class);
		IStructuresPage structuresPage = portletMenu.getStructuresPage();

		//Search structure and add fields
		Assert.assertTrue(structuresPage.doesStructureExist(WebKeys.HOST_STRUCTURE_NAME),"ERROR - Host Structure is missing");
		IStructureAddOrEdit_PropertiesPage propPage = structuresPage.getStructurePage(WebKeys.HOST_STRUCTURE_NAME);
		IStructureAddOrEdit_FieldsPage fieldsPage = structuresPage.getFieldsPage();

		//Test that the field doesn't exist
		Assert.assertFalse(fieldsPage.doesFieldExist(textFieldLabel),"ERROR - The field ("+textFieldLabel+") shoudl not exist at this time");
		fieldsPage = fieldsPage.addTextField(textFieldLabel, false, false, false, false, false);
		//Test that the field does exist
		Assert.assertTrue(fieldsPage.doesFieldExist(textFieldLabel),"ERROR - The field ("+textFieldLabel+") shoudl not exist at this time");

		//delete field
		fieldsPage = fieldsPage.deleteField(textFieldLabel);
		Assert.assertFalse(fieldsPage.doesFieldExist(textFieldLabel),"ERROR - The field ("+textFieldLabel+") shoudl not exist at this time");	
	}

	/**
	 * Test adding unique text field to host Structure. See here:
	 * http://qa.dotcms.com/index.php?/cases/view/205
	 * @throws Exception
	 */
	//@Test (groups = {"Host"})
	public void tc205_AddUniqueTextFieldToHostStructure() throws Exception {
		IPortletMenu portletMenu = backendMgr.getPageObject(IPortletMenu.class);
		IStructuresPage structuresPage = portletMenu.getStructuresPage();

		//Search structure and add fields
		Assert.assertTrue(structuresPage.doesStructureExist(WebKeys.HOST_STRUCTURE_NAME),"ERROR - Host Structure is missing");
		IStructureAddOrEdit_PropertiesPage propPage = structuresPage.getStructurePage(WebKeys.HOST_STRUCTURE_NAME);
		IStructureAddOrEdit_FieldsPage fieldsPage = structuresPage.getFieldsPage();

		//Test that the field doesn't exist
		Assert.assertFalse(fieldsPage.doesFieldExist(textFieldLabel),"ERROR - The field ("+textFieldLabel+") shoudl not exist at this time");
		fieldsPage = fieldsPage.addTextField(textFieldLabel, false, false, false, false, true);
		//Test that the field does exist
		Assert.assertTrue(fieldsPage.doesFieldExist(textFieldLabel),"ERROR - The field ("+textFieldLabel+") shoudl not exist at this time");

		//delete field
		fieldsPage = fieldsPage.deleteField(textFieldLabel);
		Assert.assertFalse(fieldsPage.doesFieldExist(textFieldLabel),"ERROR - The field ("+textFieldLabel+") shoudl not exist at this time");	
	}

	/**
	 * Test the copy host functionality. See here:
	 * http://qa.dotcms.com/index.php?/cases/view/206
	 * @throws Exception
	 */
	//@Test (groups = {"Host"})
	public void tc206_CopyHost() throws Exception {
		IPortletMenu portletMenu = backendMgr.getPageObject(IPortletMenu.class);
		IHostPage hostPage = portletMenu.getHostPage();

		String hostToCopy  = mobiledemoHostName;

		// verify Host does not already exist
		Assert.assertFalse(hostPage.doesHostExist(testHostName1),"ERROR - The host ( "+testHostName1+" ) should not exist at this time");

		// copy host
		hostPage.addCopyExistingHost(testHostName1, hostToCopy);
		hostPage.sleep(5);

		// verify it was created and listed on page
		Assert.assertTrue(hostPage.doesHostExist(testHostName1),"ERROR - The host ( "+testHostName1+" ) was not created");

		// verify new host responds to traffic
		IBasePage homePage = frontendMgr.loadPage("http://" + testHostName1 + ":8080/");
		String title = homePage.getTitle();
		Assert.assertTrue(title != null && title.startsWith("Quest Financial"),"ERROR - The host should not have a page set");

		// delete host
		hostPage.stopHost(testHostName1, true);
		hostPage.sleep(1);
		hostPage.archiveHost(testHostName1, true);
		hostPage.toggleShowArchived();
		hostPage.deleteHost(testHostName1, true);

		hostPage.sleep(5);
		// verify it is no longer listed on page
		Assert.assertFalse(hostPage.doesHostExist(testHostName1),"ERROR - The host ( "+testHostName1+" ) should not exist at this time");

	}

	/**
	 * Test a complete copy of a host and make sure all advanced templates 
	 * and design templates are copied. See here:
	 * http://qa.dotcms.com/index.php?/cases/view/207
	 * @throws Exception
	 */
	//@Test (groups = {"Host"})
	public void tc207_CopyHostWithAdvancedTemplates() throws Exception {
		IPortletMenu portletMenu = backendMgr.getPageObject(IPortletMenu.class);
		IHostPage hostPage = portletMenu.getHostPage();
		ITemplatesPage templatePage = null;
		String hostToCopy  = demoHostName;

		// verify Host does not already exist
		Assert.assertFalse(hostPage.doesHostExist(testHostName2),"ERROR - The host ( "+testHostName2+" ) should not exist at this time");

		// copy host
		hostPage.addCopyExistingHost(testHostName2, hostToCopy);
		hostPage.sleep(8);

		// verify it was created and listed on page
		Assert.assertTrue(hostPage.doesHostExist(testHostName2),"ERROR - The host ( "+testHostName2+" ) was not created");

		// verify new host responds to traffic
		//IBasePage homePage = frontendMgr.loadPage("http://" + testHostName2 + ":8080/");
		//String title = homePage.getTitle();
		//Assert.assertTrue(title != null && title.startsWith("Home Page - Quest Financial"),"ERROR - The host should not have a page set");

		templatePage = portletMenu.getTemplatesPage();
		int intCopyHostTemplates = templatePage.getNumberOfHostTemplates(testHostName2);
		int intBaseHostTemplates = templatePage.getNumberOfHostTemplates(demoHostName);
		Assert.assertTrue(intBaseHostTemplates == intCopyHostTemplates,"ERROR - the number of templates don't match between the two host");

		hostPage = portletMenu.getHostPage();
		// delete host
		hostPage.stopHost(testHostName2, true);
		hostPage.sleep(1);
		hostPage.archiveHost(testHostName2, true);
		hostPage.toggleShowArchived();
		hostPage.deleteHost(testHostName2, true);

		hostPage.sleep(10);
		// verify it is no longer listed on page
		Assert.assertFalse(hostPage.doesHostExist(testHostName2),"ERROR - The host ( "+testHostName2+" ) should not exist at this time");

	}

	/**
	 * Make sure the content is not disappearing and is not linked 
	 * after copy host. See here:
	 * http://qa.dotcms.com/index.php?/cases/view/684
	 * @throws Exception
	 */
	@Test (groups = {"Host"})
	public void tc684_ContentNotDisappearingAfterCopy() throws Exception {
		IPortletMenu portletMenu = backendMgr.getPageObject(IPortletMenu.class);
		ISiteBrowserPage browserPage = portletMenu.getSiteBrowserPage();
		browserPage.changeHost(demoHostName);
		String folderName = "about-us";
		browserPage.selectFolder(folderName);
		String pageName = "index.html";
		IPreviewHTMLPage_Page previewHTMLPage = browserPage.selectPageElement(pageName);

		String containerInode = "97b67789-453c-487b-858c-82c9e09623bd";
		//Adding spanish version to current content
		String spanishLanguage = "Espanol (ES)";
		String spanish ="Espanol "; 

		//current amount of content in english in the container
		List<String> originalContainerEnglishContents = previewHTMLPage.getContainerContents(containerInode);
		
		//Editing container
		Map<String,Object> content = new HashMap<String, Object>();
		content.put("inode", "74f9338d-69de-4d7a-b82d-23c24add6a5b");
		content.put("identifier", "767509b1-2392-4661-a16b-e0e31ce27719");
		content.put("title", "prueba");
		content.put("body", "prueba prueba prueba prueba prueba");

		String currentLanguaje = previewHTMLPage.getCurrentLanguage();
		currentLanguaje = currentLanguaje.substring(0, currentLanguaje.indexOf(" "));
		Assert.assertFalse(currentLanguaje.equals(spanishLanguage), "ERROR - Spanish should not be the current language");

		previewHTMLPage.editContent(content, spanish,true);

		//Adding extra content in spanish version
		Map<String,Object> content2 = new HashMap<String, Object>();
		content2.put("title", "prueba2");
		content2.put("body", "prueba2 prueba2 prueba2 prueba2 prueba2");
		previewHTMLPage.addContent(containerInode,content2, spanish);

		List<String> originalContainerSpanishContents = previewHTMLPage.getContainerContents(containerInode);
		
		String newLanguaje = previewHTMLPage.getCurrentLanguage();
		Assert.assertTrue(newLanguaje.equals(spanishLanguage), "ERROR - Spanish should be the current language");

		previewHTMLPage.changeLanguage(currentLanguaje);
		newLanguaje = previewHTMLPage.getCurrentLanguage();
		Assert.assertFalse(newLanguaje.equals(spanishLanguage), "ERROR - Spanish should not be the current language");

		//Add New Host
		previewHTMLPage.returnToPortletsMenu();
		IHostPage hostPage = portletMenu.getHostPage();
		hostPage.addCopyExistingHost(testHostName1, demoHostName);
		hostPage.sleep(10);

		// verify it was created and listed on page
		Assert.assertTrue(hostPage.doesHostExist(testHostName1),"ERROR - The host ( "+testHostName1+" ) was not created");

		//Validate in new host that the page have the two content in spanish
		browserPage = portletMenu.getSiteBrowserPage();
		browserPage.changeHost(testHostName1);
		browserPage.selectFolder(folderName);
		previewHTMLPage = browserPage.selectPageElement(pageName);
		
		List<String> copyHostContainerEnglishContents = previewHTMLPage.getContainerContents(containerInode);
		Assert.assertTrue(originalContainerEnglishContents.size() == copyHostContainerEnglishContents.size(),"ERROR - The number of contents in english is different in host ("+demoHostName+","+testHostName1+")");
		
		previewHTMLPage.changeLanguage(spanish);
		List<String> copyHostContainerSpaishContents = previewHTMLPage.getContainerContents(containerInode);
		
		Assert.assertTrue(originalContainerSpanishContents.size() == copyHostContainerSpaishContents.size(),"ERROR - The number of contents in spanish is different in host ("+demoHostName+","+testHostName1+")");
		
		//delete newly added host
		hostPage = portletMenu.getHostPage();
		hostPage.stopHost(testHostName1, true);
		hostPage.sleep(1);						// TODO - remove cluginess and be able to remove this sleep call
		hostPage.archiveHost(testHostName1, true);
		hostPage.toggleShowArchived();
		hostPage.deleteHost(testHostName1, true);
		hostPage.sleep(1);
		// verify host is no longer listed on page
		hostPage.reload();
		Assert.assertFalse(hostPage.doesHostExist(testHostName1),"ERROR - The host ( "+testHostName1+" ) should not exist at this time");
	}

	/**
	 * Test that only one host could be set as default functionality. See here:
	 * http://qa.dotcms.com/index.php?/cases/view/688
	 * @throws Exception
	 */
	//@Test (groups = {"Host"})
	public void tc688_OnlyOneDefaultHost() throws Exception {
		IPortletMenu portletMenu = backendMgr.getPageObject(IPortletMenu.class);
		IHostPage hostPage = portletMenu.getHostPage();

		int numberOfDefaultHosts =0;

		// add host
		hostPage.addBlankHost(testHostName1);
		hostPage.sleep(5);
		//set host as default
		hostPage.makeDefultHost(testHostName1, true);
		hostPage.sleep(1);	
		hostPage.reload();
		// verify it was created and listed on page
		Assert.assertTrue(hostPage.doesHostExist(testHostName1),"ERROR - The host ( "+testHostName1+" ) was not created");
		//validate that the host is set as default
		Assert.assertTrue(hostPage.isHostDefault(testHostName1), "ERROR -  This Host ("+testHostName1+") should be a default host at this moment");


		//setting list of servers to test
		List<String> servers = new ArrayList<String>();
		servers.add(mobiledemoHostName);
		servers.add(qasharedHostName);
		servers.add(testHostName1);
		servers.add(demoHostName);
		//validate the number of defaults servers
		for(String server : servers){
			if(hostPage.isHostDefault(server)){
				numberOfDefaultHosts=numberOfDefaultHosts+1;
			}
		}
		Assert.assertFalse(numberOfDefaultHosts > 1, "ERROR - There should be only one default server and there are:"+numberOfDefaultHosts+" right now.");
		numberOfDefaultHosts =0;
		/*
		//set default each server to validate the code is strong
		for(String server : servers){
			hostPage.makeDefultHost(server, true);
			hostPage.sleep(1);
		}
		//validate the number of defaults servers
		for(String server : servers){
			if(hostPage.isHostDefault(server)){
				numberOfDefaultHosts=numberOfDefaultHosts+1;
			}
		}
		Assert.assertFalse(numberOfDefaultHosts > 1, "ERROR - There should be only one default server and there are:"+numberOfDefaultHosts+" right now.");
		 */
		//Setting qademo as default host
		if(!hostPage.isHostActive(demoHostName)){
			hostPage.startHost(demoHostName, true);
		}
		if(!hostPage.isHostDefault(demoHostName)){
			hostPage.makeDefultHost(demoHostName, true);
			hostPage.sleep(1);
			Assert.assertTrue(hostPage.isHostDefault(demoHostName), "ERROR -  This Host ("+demoHostName+") should be a default host at this moment");
		}

		//delete newly added host
		hostPage.stopHost(testHostName1, true);
		hostPage.sleep(1);						// TODO - remove cluginess and be able to remove this sleep call
		hostPage.archiveHost(testHostName1, true);
		hostPage.toggleShowArchived();
		hostPage.deleteHost(testHostName1, true);
		hostPage.sleep(1);
		// verify host is no longer listed on page
		hostPage.reload();
		Assert.assertFalse(hostPage.doesHostExist(testHostName1),"ERROR - The host ( "+testHostName1+" ) should not exist at this time");

	}

	/**
	 * Test that only one host could be set as default functionality. See here:
	 * http://qa.dotcms.com/index.php?/cases/view/14093
	 * @throws Exception
	 */
	//@Test (groups = {"Host"})
	public void tc14093_RemoveHostWithForeignLanguageContent() throws Exception {
		IPortletMenu portletMenu = backendMgr.getPageObject(IPortletMenu.class);
		IHostPage hostPage = portletMenu.getHostPage();
		/*
		// verify Host does not already exist
		Assert.assertFalse(hostPage.doesHostExist(testHostName1),"ERROR - The host ( "+testHostName1+" ) should not exist at this time");

		// add host
		hostPage.addBlankHost(testHostName1);
		hostPage.sleep(5);

		// verify Host does exist
		Assert.assertTrue(hostPage.doesHostExist(testHostName1),"ERROR - The host ( "+testHostName1+" ) should exist at this time");

		//TODO
		//TEST LOGIC

		//delete test host
		hostPage.stopHost(testHostName1, true);
		hostPage.sleep(1);						// TODO - remove cluginess and be able to remove this sleep call
		hostPage.archiveHost(testHostName1, true);
		hostPage.sleep(1);
		hostPage.toggleShowArchived();
		hostPage.deleteHost(testHostName1, true);
		hostPage.sleep(1);
		// verify host is no longer listed on page
		hostPage.reload();
		Assert.assertFalse(hostPage.doesHostExist(testHostName1),"ERROR - The host ( "+testHostName1+" ) should not exist at this time");
		hostPage.toggleShowArchived();
		Assert.assertFalse(hostPage.doesHostExist(testHostName1),"ERROR - The host ( "+testHostName1+" ) should not exist at this time");
		 */
	}

}
