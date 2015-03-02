package com.dotcms.qa.testng.tests;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.AfterGroups;
import org.testng.annotations.BeforeGroups;
import org.testng.annotations.Test;

import com.dotcms.qa.selenium.pages.IBasePage;
import com.dotcms.qa.selenium.pages.backend.IContentAddOrEdit_ContentPage;
import com.dotcms.qa.selenium.pages.backend.IContentSearchPage;
import com.dotcms.qa.selenium.pages.backend.IHostPage;
import com.dotcms.qa.selenium.pages.backend.ILoginPage;
import com.dotcms.qa.selenium.pages.backend.IPortletMenu;
import com.dotcms.qa.selenium.pages.backend.IPreviewHTMLPage_Page;
import com.dotcms.qa.selenium.pages.backend.ISiteBrowserPage;
import com.dotcms.qa.selenium.pages.backend.IStructureAddOrEdit_FieldsPage;
import com.dotcms.qa.selenium.pages.backend.IStructureAddOrEdit_PropertiesPage;
import com.dotcms.qa.selenium.pages.backend.IStructuresPage;
import com.dotcms.qa.selenium.pages.backend.ITemplatesPage;
import com.dotcms.qa.selenium.util.SeleniumConfig;
import com.dotcms.qa.selenium.util.SeleniumPageManager;
import com.dotcms.qa.util.Evaluator;
import com.dotcms.qa.util.WebKeys;

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
	private String testHostName3 = "qahost03.dotcms.com";
	private String testHostName4 = "qahost04.dotcms.com";
	private String testHostName5 = "qahost05.dotcms.com";
	private String testHostName6 = "qahost06.dotcms.com";
	private String testHostName7 = "qahost07.dotcms.com";
	
	private String textFieldLabel = "MyHostTest";
	private String textFieldLabel2 = "MyHostTest2";
	//Spanish language
	private String spanishLanguage = "Espanol (ES)";
	private String spanish ="Espanol "; 

	@BeforeGroups (groups = {"Host"})
	public void init() throws Exception {
		try{
	    	logger.info("**HostTest.init() beginning**");
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
	    	logger.info("**HostTest.init() ending**");
		}
		catch (Exception e) {
    		logger.error("ERROR - HostTest.init()", e);
    		throw(e);
		}
	}

	@AfterGroups (groups = {"Host"})
	public void teardown() throws Exception {
		// logout
		backendMgr.logoutBackend();
	}
	
	/**
	 * Test that only one host could be set as default functionality. See here:
	 * http://qa.dotcms.com/index.php?/cases/view/14093
	 * @throws Exception
	 */
	@Test (groups = {"Host"})
	public void tc14093_RemoveHostWithForeignLanguageContent() throws Exception {
		IPortletMenu portletMenu = backendMgr.getPageObject(IPortletMenu.class);
		IHostPage hostPage = portletMenu.getHostPage();
		
		// verify Host does not already exist
		//Assert.assertFalse(hostPage.doesHostExist(testHostName1),"ERROR - The host ( "+testHostNgit pusame1+" ) should not exist at this time");
		if(!hostPage.doesHostExist(testHostName1)){
			// add host
			hostPage.addBlankHost(testHostName1);
			hostPage.sleep(5);
		}
		// verify Host does exist
		Assert.assertTrue(hostPage.doesHostExist(testHostName1),"ERROR - The host ( "+testHostName1+" ) should exist at this time");

		IContentSearchPage contentSearch = portletMenu.getContentSearchPage();
		String structureName = "Content (Generic)";
		if(hostPage.getBrowserName().equals(WebKeys.FIREFOX_BROWSER_NAME)){
			structureName = "Content ";
		}
		IContentAddOrEdit_ContentPage content = contentSearch.getAddContentPage(structureName);
		content.changeContentLanguage(spanish, false);
		
		//content.
		List<Map<String,Object>> content3 = new ArrayList<Map<String,Object>>();
		Map<String,Object> text_fields = new HashMap<String, Object>();
		text_fields.put("type", WebKeys.TEXT_FIELD);
		text_fields.put("title", "prueba3");
		Map<String,Object> host_fields = new HashMap<String, Object>();
		host_fields.put("type", WebKeys.HOST_FIELD);
		host_fields.put("HostSelector-hostFolderSelect", testHostName1);
		Map<String,Object> wysiwyg_fields = new HashMap<String, Object>();
		wysiwyg_fields.put("type", WebKeys.WYSIWYG_FIELD);
		wysiwyg_fields.put("body", "prueba3 prueba3 prueba3 prueba3 prueba3");
		
		content3.add(text_fields);
		content3.add(host_fields);
		content3.add(wysiwyg_fields);
		
		content.setFields(content3);
		content.sleep(2);
		content.saveAndPublish();
		
		//delete test host
		content.sleep(2);
		hostPage = portletMenu.getHostPage();
		content.sleep(2);
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
	}

	/**
	 * Test the add host functionality case. See here:
	 * http://qa.dotcms.com/index.php?/cases/view/196
	 * @throws Exception
	 */
	@Test (groups = {"Host"})
	public void tc196_AddAndDeleteHostManually() throws Exception  {
		IPortletMenu portletMenu = backendMgr.getPageObject(IPortletMenu.class);
		IHostPage hostPage = portletMenu.getHostPage();

		// verify Host does not already exist
		Assert.assertFalse(hostPage.doesHostExist(testHostName2),"ERROR - The host ( "+testHostName2+" ) should not exist at this time");

		// add host
		hostPage.addBlankHost(testHostName2);
		hostPage.sleep(5);

		// verify it was created and listed on page
		Assert.assertTrue(hostPage.doesHostExist(testHostName2),"ERROR - The host ( "+testHostName2+" ) was not created");

		// verify new host responds to traffic
		IBasePage homePage = frontendMgr.loadPage("http://" + testHostName2 + ":8080/");
		String title = homePage.getTitle();
		Assert.assertTrue(title != null && title.startsWith("dotCMS: Page not found"),"ERROR - The host should not have a page set");
		
		// delete host
		hostPage.stopHost(testHostName2, true);
		hostPage.sleep(3);
		hostPage.archiveHost(testHostName2, true);
		hostPage.toggleShowArchived();
		hostPage.deleteHost(testHostName2, true);
		Evaluator eval = new Evaluator() {
			public boolean evaluate() throws Exception {  // returns false if host exists
				IHostPage evalHostPage = backendMgr.getPageObject(IHostPage.class);
				evalHostPage.reload();
				evalHostPage.toggleShowArchived();
				return !evalHostPage.doesHostExist(testHostName2);
			}
		};
		hostPage.pollForValue(eval, true, 5000, 24);	// Check every 5 seconds for up to 2 minutes
		
		// verify host is no longer listed on page
		hostPage.reload();
		Assert.assertFalse(hostPage.doesHostExist(testHostName2),"ERROR - The host ( "+testHostName2+" ) should not exist at this time");
		hostPage.toggleShowArchived();
		Assert.assertFalse(hostPage.doesHostExist(testHostName2),"ERROR - The host ( "+testHostName2+" ) should not exist at this time");

		// verify host is no longer responding to requests
		IBasePage homePage2 = frontendMgr.loadPage("http://" + testHostName2 + ":8080/");
		String title2 = homePage2.getTitle();
		Assert.assertFalse(title != null && title2 != null && title2.equals(title), "Page should not be responding.  title=|" + title + "| title2 = |" + title2 + "|");
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

		String hostVariableName = "var1";
		String hostVariableKey = "key1";
		String hostVariableValue = "value1";
		hostPage.sleep(2);
		Assert.assertFalse(hostPage.doesHostVariableExist(qasharedHostName, hostVariableName),"ERROR -  the host variable ("+hostVariableName+") should not exist in host ("+qasharedHostName+")");
		hostPage.addHostVariable(qasharedHostName, hostVariableName, hostVariableKey, hostVariableValue);
		hostPage.sleep(2);
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
	@Test (groups = {"Host"})
	public void tc198_AddHostBinaryThumbnail() throws Exception {
		IPortletMenu portletMenu = backendMgr.getPageObject(IPortletMenu.class);
		IHostPage hostPage = portletMenu.getHostPage();
		Assert.assertFalse(hostPage.doesHostHaveHostThumbnail(qasharedHostName),"ERROR -  the host ("+qasharedHostName+") should not have a host thumbnail");
		/**
		 * This "if" condition should be removed, once the problem with the upload 
		 * thumbnail is fixed on google chrome browser
		 */
		if(!hostPage.getBrowserName().equals(WebKeys.CHROME_BROWSER_NAME)){
			hostPage.addHostThumbnail(qasharedHostName);
		}
		hostPage.sleep(5);
		Assert.assertTrue(hostPage.doesHostHaveHostThumbnail(qasharedHostName),"ERROR -  the host ("+qasharedHostName+") does not have a host thumbnail");
		hostPage.removeHostThumbnail(qasharedHostName);
		hostPage.sleep(5);
		Assert.assertFalse(hostPage.doesHostHaveHostThumbnail(qasharedHostName),"ERROR -  the host ("+qasharedHostName+") should not have a host thumbnail");
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

		String hostToCopy  = mobiledemoHostName;

		// verify Host does not already exist
		Assert.assertFalse(hostPage.doesHostExist(testHostName3),"ERROR - The host ( "+testHostName3+" ) should not exist at this time");

		// copy host
		hostPage.addCopyExistingHost(testHostName3, hostToCopy);
		hostPage.sleep(10);

		// verify it was created and listed on page
		Assert.assertTrue(hostPage.doesHostExist(testHostName3),"ERROR - The host ( "+testHostName3+" ) was not created");

		// verify new host responds to traffic
		IBasePage homePage = frontendMgr.loadPage("http://" + testHostName3 + ":8080/");
		String title = homePage.getTitle();
		Assert.assertTrue(title != null && title.startsWith("Quest Financial"),"ERROR - The host should not have a page set");

		// delete host
		hostPage.stopHost(testHostName3, true);
		hostPage.sleep(1);
		hostPage.archiveHost(testHostName3, true);
		hostPage.toggleShowArchived();
		hostPage.deleteHost(testHostName3, true);

		hostPage.sleep(10);
		// verify it is no longer listed on page
		Assert.assertFalse(hostPage.doesHostExist(testHostName3),"ERROR - The host ( "+testHostName3+" ) should not exist at this time");

		// verify host is no longer responding to requests
		IBasePage demoHomePage = frontendMgr.loadPage(demoServerURL);
		String demoHomePageTitle = demoHomePage.getTitle();
		homePage = frontendMgr.loadPage("http://" + testHostName3 + ":8080/");
		title = homePage.getTitle();
		Assert.assertTrue(demoHomePageTitle != null && title != null && demoHomePageTitle.equals(title), "Page titles do not match.  demoHomePageTitle=|" + demoHomePageTitle + "| title = |" + title + "|");
	}

	/**
	 * Test the stop host functionality. See here:
	 * http://qa.dotcms.com/index.php?/cases/view/201
	 * @throws Exception
	 */
	@Test (groups = {"Host"})
	public void tc201_StopAndActivateHost() throws Exception {
		IPortletMenu portletMenu = backendMgr.getPageObject(IPortletMenu.class);
		IHostPage hostPage = portletMenu.getHostPage();

		Assert.assertTrue(hostPage.isHostActive(mobiledemoHostName), "ERROR -  This Host ("+mobiledemoHostName+") should be  active at this moment");
		hostPage.stopHost(mobiledemoHostName, true);
		hostPage.sleep(1);
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
	// Marked as broken, pending fix for https://github.com/dotCMS/core/issues/7362
	@Test (groups = {"Broken", "Host"})
	public void tc203_ChangeDefaultHost() throws Exception {
		IPortletMenu portletMenu = backendMgr.getPageObject(IPortletMenu.class);
		IHostPage hostPage = portletMenu.getHostPage();

		Assert.assertFalse(hostPage.isHostDefault(mobiledemoHostName), "ERROR -  This Host ("+mobiledemoHostName+") should not be a default host at this moment");
		hostPage.makeDefaultHost(mobiledemoHostName, true);
		hostPage.sleep(1);
		Assert.assertTrue(hostPage.isHostDefault(mobiledemoHostName), "ERROR -  This Host ("+mobiledemoHostName+") should be a default host at this moment");
		Assert.assertFalse(hostPage.isHostDefault(demoHostName), "ERROR -  This Host ("+demoHostName+") should not be a default host at this moment");

		/** 
		 * some times the change default host left inactive the previous default host
		 */
		if(!hostPage.isHostActive(demoHostName)){
			hostPage.startHost(demoHostName, true);
		}
		hostPage.makeDefaultHost(demoHostName, true);
		hostPage.sleep(1);
		Assert.assertTrue(hostPage.isHostDefault(demoHostName), "ERROR -  This Host ("+demoHostName+") should be a default host at this moment");
		Assert.assertFalse(hostPage.isHostDefault(mobiledemoHostName), "ERROR -  This Host ("+mobiledemoHostName+") should not be a default host at this moment");

	}

	/**
	 * Test adding text field to host Structure. See here:
	 * http://qa.dotcms.com/index.php?/cases/view/204
	 * @throws Exception
	 */
	@Test (groups = {"Host"})
	public void tc204_AddTextFieldToHostStructure() throws Exception {
		IPortletMenu portletMenu = backendMgr.getPageObject(IPortletMenu.class);
		IStructuresPage structuresPage = portletMenu.getStructuresPage();

		//Search structure and add fields
		Assert.assertTrue(structuresPage.doesStructureExist(WebKeys.HOST_STRUCTURE_NAME),"ERROR - Host Structure is missing");
		@SuppressWarnings("unused")
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
	@Test (groups = {"Host"})
	public void tc205_AddUniqueTextFieldToHostStructure() throws Exception {
		IPortletMenu portletMenu = backendMgr.getPageObject(IPortletMenu.class);
		IStructuresPage structuresPage = portletMenu.getStructuresPage();

		//Search structure and add fields
		Assert.assertTrue(structuresPage.doesStructureExist(WebKeys.HOST_STRUCTURE_NAME),"ERROR - Host Structure is missing");
		@SuppressWarnings("unused")
		IStructureAddOrEdit_PropertiesPage propPage = structuresPage.getStructurePage(WebKeys.HOST_STRUCTURE_NAME);
		IStructureAddOrEdit_FieldsPage fieldsPage = structuresPage.getFieldsPage();

		//Test that the field doesn't exist
		Assert.assertFalse(fieldsPage.doesFieldExist(textFieldLabel2),"ERROR - The field ("+textFieldLabel2+") shoudl not exist at this time");
		fieldsPage = fieldsPage.addTextField(textFieldLabel2, false, false, false, false, true);
		//Test that the field does exist
		Assert.assertTrue(fieldsPage.doesFieldExist(textFieldLabel2),"ERROR - The field ("+textFieldLabel2+") shoudl not exist at this time");

		//delete field
		fieldsPage = fieldsPage.deleteField(textFieldLabel2);
		Assert.assertFalse(fieldsPage.doesFieldExist(textFieldLabel2),"ERROR - The field ("+textFieldLabel2+") shoudl not exist at this time");	
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

		String hostToCopy  = mobiledemoHostName;

		// verify Host does not already exist
		Assert.assertFalse(hostPage.doesHostExist(testHostName4),"ERROR - The host ( "+testHostName4+" ) should not exist at this time");

		// copy host
		hostPage.addCopyExistingHost(testHostName4, hostToCopy);
		hostPage.sleep(1);
		Evaluator eval1 = new Evaluator() {
			public boolean evaluate() throws Exception {  // returns true if host copy is done
				IHostPage evalHostPage = backendMgr.getPageObject(IHostPage.class);
				return !evalHostPage.isHostCopyInProgress(testHostName4);
			}
		};
		hostPage.pollForValue(eval1, true, 1000, 120);		// Poll every second for up to 2 minutes

		// verify it was created and listed on page
		Assert.assertTrue(hostPage.doesHostExist(testHostName4),"ERROR - The host ( "+testHostName4+" ) was not created");

		// verify new host responds to traffic
		IBasePage homePage = frontendMgr.loadPage("http://" + testHostName4 + ":8080/");
		String title = homePage.getTitle();
		Assert.assertTrue(title != null && title.startsWith("Quest Financial"),"ERROR - The host should not have a page set");

		// delete host
		hostPage.stopHost(testHostName4, true);
		hostPage.sleep(1);
		hostPage.archiveHost(testHostName4, true);
		hostPage.toggleShowArchived();
		hostPage.deleteHost(testHostName4, true);
		Evaluator eval3 = new Evaluator() {
			public boolean evaluate() throws Exception {  // returns false if host exists
				IHostPage evalHostPage = backendMgr.getPageObject(IHostPage.class);
				evalHostPage.reload();
				evalHostPage.toggleShowArchived();
				return !evalHostPage.doesHostExist(testHostName4);
			}
		};
		hostPage.pollForValue(eval3, true, 5000, 24);	// Check every 5 seconds for up to 2 minutes

		// verify it is no longer listed on page
		Assert.assertFalse(hostPage.doesHostExist(testHostName4),"ERROR - The host ( "+testHostName4+" ) should not exist at this time");

	}

	/**
	 * Test a complete copy of a host and make sure all advanced templates 
	 * and design templates are copied. See here:
	 * http://qa.dotcms.com/index.php?/cases/view/207
	 * @throws Exception
	 */
	@Test (groups = {"Host"})
	public void tc207_CopyHostWithAdvancedTemplates() throws Exception {
		IPortletMenu portletMenu = backendMgr.getPageObject(IPortletMenu.class);
		IHostPage hostPage = portletMenu.getHostPage();
		ITemplatesPage templatePage = null;
		String hostToCopy  = demoHostName;

		// verify Host does not already exist
		Assert.assertFalse(hostPage.doesHostExist(testHostName5),"ERROR - The host ( "+testHostName5+" ) should not exist at this time");

		// copy host
		hostPage.addCopyExistingHost(testHostName5, hostToCopy);
		hostPage.sleep(2); // wait to ensure hostpage is loaded
		Evaluator eval1 = new Evaluator() {
			public boolean evaluate() throws Exception {  // returns true if host copy is done
				IHostPage evalHostPage = backendMgr.getPageObject(IHostPage.class);
				return !evalHostPage.isHostCopyInProgress(testHostName5);
			}
		};
		hostPage.pollForValue(eval1, true, 1000, 120);		// Poll every second for up to 2 minutes

		// verify it was created and listed on page
		Assert.assertTrue(hostPage.doesHostExist(testHostName5),"ERROR - The host ( "+testHostName5+" ) was not created");

		templatePage = portletMenu.getTemplatesPage();
		int intCopyHostTemplates = templatePage.getNumberOfHostTemplates(testHostName5);
		int intBaseHostTemplates = templatePage.getNumberOfHostTemplates(demoHostName);
		Assert.assertTrue(intBaseHostTemplates == intCopyHostTemplates,"ERROR - the number of templates don't match between the two host");

		hostPage = portletMenu.getHostPage();
		Evaluator eval2 = new Evaluator() {
			public boolean evaluate() throws Exception {  // returns true if host exists
				IHostPage evalHostPage = backendMgr.getPageObject(IHostPage.class);
				return evalHostPage.doesHostExist(testHostName5);
			}
		};
		hostPage.pollForValue(eval2, true, 1000, 20);
		// delete host
		hostPage.stopHost(testHostName5, true);
		hostPage.sleep(1);
		hostPage.archiveHost(testHostName5, true);
		hostPage.toggleShowArchived();
		hostPage.deleteHost(testHostName5, true);
		Evaluator eval3 = new Evaluator() {
			public boolean evaluate() throws Exception {  // returns false if host exists
				IHostPage evalHostPage = backendMgr.getPageObject(IHostPage.class);
				evalHostPage.reload();
				evalHostPage.toggleShowArchived();
				return !evalHostPage.doesHostExist(testHostName5);
			}
		};
		boolean hostDeleted = hostPage.pollForValue(eval3, true, 5000, 24);	// Check every 5 seconds for up to 2 minutes
		
		// verify it host has been deleted
		Assert.assertTrue(hostDeleted,"ERROR - The host ( "+testHostName5+" ) should not exist at this time");

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
		browserPage.sleep(2);
		browserPage.selectFolder(folderName);
		String pageName = "index.html";
		browserPage.sleep(2);
		IPreviewHTMLPage_Page previewHTMLPage = browserPage.selectPageElement(pageName);
		//validate that the edit mode is selected
		try{
			if(previewHTMLPage.isLocked()){
				previewHTMLPage.unLockPage();
			}
		}catch(Exception e){
			//is not a page asset contentlet
		}
		previewHTMLPage.selectEditModeView();
		String containerName = "Default 1 (Page Content)";
		String containerInode = previewHTMLPage.getContainerInode( containerName);

		//current amount of content in english in the container
		List<String> originalContainerEnglishContents = previewHTMLPage.getContainerContents(containerInode);
		previewHTMLPage.sleep(1);
		//Editing container
		List<Map<String,Object>> content1 = new ArrayList<Map<String,Object>>();
		Map<String,Object> text_fields = new HashMap<String, Object>();
		text_fields.put("type", WebKeys.TEXT_FIELD);
		text_fields.put("title", "prueba1");
		Map<String,Object> wysiwyg_fields = new HashMap<String, Object>();
		wysiwyg_fields.put("type", WebKeys.WYSIWYG_FIELD);
		wysiwyg_fields.put("body", "prueba1 prueba1 prueba1 prueba1 prueba1");
		content1.add(text_fields);
		content1.add(wysiwyg_fields);
		String contentInode= originalContainerEnglishContents.get(0);
		

		String currentLanguage = previewHTMLPage.getCurrentLanguage();
		currentLanguage = currentLanguage.substring(0, currentLanguage.indexOf(" "));
		
		//Adding spanish version to current content
		Assert.assertFalse(currentLanguage.equals(spanishLanguage), "ERROR - Spanish should not be the current language");
		previewHTMLPage.sleep(3);
		previewHTMLPage.editContent(contentInode,content1, spanish,true);
		previewHTMLPage.sleep(1);
		
		
		//Adding extra content in spanish version
		List<Map<String,Object>> content2 = new ArrayList<Map<String,Object>>();
		text_fields = new HashMap<String, Object>();
		text_fields.put("type", WebKeys.TEXT_FIELD);
		text_fields.put("title", "prueba2");
		wysiwyg_fields = new HashMap<String, Object>();
		wysiwyg_fields.put("type", WebKeys.WYSIWYG_FIELD);
		wysiwyg_fields.put("body", "prueba2 prueba2 prueba2 prueba2 prueba2");
		content2.add(text_fields);
		content2.add(wysiwyg_fields);
		try{
			if(previewHTMLPage.isLocked()){
				previewHTMLPage.unLockPage();
			}
		}catch(Exception e){
			logger.error("This is not a page asset contentlet", e);
		}
		previewHTMLPage.selectEditModeView();
		previewHTMLPage.addContent(containerInode,content2, spanish);
		previewHTMLPage.sleep(3);
		try{
			if(previewHTMLPage.isLocked()){
				previewHTMLPage.unLockPage();
			}
		}catch(Exception e){
			logger.error("This is not a page asset contentlet", e);
		}
		previewHTMLPage.selectEditModeView();
		List<String> originalContainerSpanishContents = previewHTMLPage.getContainerContents(containerInode);
		
		String newLanguaje = previewHTMLPage.getCurrentLanguage();
		Assert.assertTrue(newLanguaje.equals(spanishLanguage), "ERROR - Spanish should be the current language");
		previewHTMLPage.sleep(1);
		previewHTMLPage.changeLanguage(currentLanguage);
		newLanguaje = previewHTMLPage.getCurrentLanguage();
		Assert.assertFalse(newLanguaje.equals(spanishLanguage), "ERROR - Spanish should not be the current language");
		previewHTMLPage.sleep(1);
		//Add New Host
		previewHTMLPage.returnToPortletsMenu();
		IHostPage hostPage = portletMenu.getHostPage();
		hostPage.addCopyExistingHost(testHostName6, demoHostName);
		hostPage.sleep(20);

		// verify it was created and listed on page
		Assert.assertTrue(hostPage.doesHostExist(testHostName6),"ERROR - The host ( "+testHostName6+" ) was not created");

		//Validate in new host that the page have the two content in spanish
		browserPage = portletMenu.getSiteBrowserPage();
		browserPage.changeHost(testHostName6);
		browserPage.selectFolder(folderName);
		previewHTMLPage = browserPage.selectPageElement(pageName);
		previewHTMLPage.sleep(1);
		try{
			if(previewHTMLPage.isLocked()){
				previewHTMLPage.unLockPage();
			}
		}catch(Exception e){
			//is not a page asset contentlet
		}
		previewHTMLPage.selectEditModeView();
		String newContainerInode = previewHTMLPage.getContainerInode( containerName);
		List<String> copyHostContainerEnglishContents = previewHTMLPage.getContainerContents(newContainerInode);
		Assert.assertTrue(originalContainerEnglishContents.size() == copyHostContainerEnglishContents.size(),"ERROR - The number of contents in english is different in host ("+demoHostName+","+testHostName6+")");
		previewHTMLPage.changeLanguage(spanish);
		previewHTMLPage.sleep(1);
		try{
			if(previewHTMLPage.isLocked()){
				previewHTMLPage.unLockPage();
			}
		}catch(Exception e){
			//is not a page asset contentlet
		}
		previewHTMLPage.selectEditModeView();
		List<String> copyHostContainerSpaishContents = previewHTMLPage.getContainerContents(newContainerInode);
		Assert.assertTrue(originalContainerSpanishContents.size() == copyHostContainerSpaishContents.size(),"ERROR - The number of contents in spanish is different in host ("+demoHostName+","+testHostName6+")");
		
		//delete newly added host
		previewHTMLPage.returnToPortletsMenu();
		hostPage.sleep(2);
		hostPage = portletMenu.getHostPage();
		hostPage.stopHost(testHostName6, true);
		hostPage.sleep(1);						// TODO - remove cluginess and be able to remove this sleep call
		hostPage.archiveHost(testHostName6, true);
		hostPage.toggleShowArchived();
		hostPage.deleteHost(testHostName6, true);
		hostPage.sleep(1);
		// verify host is no longer listed on page
		hostPage.reload();
		Assert.assertFalse(hostPage.doesHostExist(testHostName6),"ERROR - The host ( "+testHostName6+" ) should not exist at this time");
	}

	/**
	 * Test that only one host could be set as default functionality. See here:
	 * http://qa.dotcms.com/index.php?/cases/view/688
	 * @throws Exception
	 */
	// Marked as broken, pending fix for https://github.com/dotCMS/core/issues/7362
	@Test (groups = {"Broken", "Host"})
	public void tc688_OnlyOneDefaultHost() throws Exception {
		IPortletMenu portletMenu = backendMgr.getPageObject(IPortletMenu.class);
		IHostPage hostPage = portletMenu.getHostPage();

		int numberOfDefaultHosts =0;

		// add host
		hostPage.addBlankHost(testHostName7);
		hostPage.sleep(5);
		//set host as default
		hostPage.makeDefaultHost(testHostName7, true);
		hostPage.sleep(1);	
		hostPage.reload();
		// verify it was created and listed on page
		Assert.assertTrue(hostPage.doesHostExist(testHostName7),"ERROR - The host ( "+testHostName7+" ) was not created");
		//validate that the host is set as default
		Assert.assertTrue(hostPage.isHostDefault(testHostName7), "ERROR -  This Host ("+testHostName7+") should be a default host at this moment");


		//setting list of servers to test
		List<String> servers = new ArrayList<String>();
		servers.add(mobiledemoHostName);
		servers.add(qasharedHostName);
		servers.add(testHostName7);
		servers.add(demoHostName);
		//validate the number of defaults servers
		for(String server : servers){
			if(hostPage.isHostDefault(server)){
				numberOfDefaultHosts=numberOfDefaultHosts+1;
			}
		}
		Assert.assertFalse(numberOfDefaultHosts > 1, "ERROR - There should be only one default server and there are:"+numberOfDefaultHosts+" right now.");
		numberOfDefaultHosts =0;
		
		//Setting qademo as default host
		if(!hostPage.isHostActive(demoHostName)){
			hostPage.startHost(demoHostName, true);
		}
		if(!hostPage.isHostDefault(demoHostName)){
			hostPage.makeDefaultHost(demoHostName, true);
			hostPage.sleep(1);
			Assert.assertTrue(hostPage.isHostDefault(demoHostName), "ERROR -  This Host ("+demoHostName+") should be a default host at this moment");
		}

		//delete newly added host
		hostPage.stopHost(testHostName7, true);
		hostPage.sleep(1);						// TODO - remove cluginess and be able to remove this sleep call
		hostPage.archiveHost(testHostName7, true);
		hostPage.toggleShowArchived();
		hostPage.deleteHost(testHostName7, true);
		hostPage.sleep(1);
		// verify host is no longer listed on page
		hostPage.reload();
		Assert.assertFalse(hostPage.doesHostExist(testHostName7),"ERROR - The host ( "+testHostName7+" ) should not exist at this time");

	}

}
