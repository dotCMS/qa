package com.dotcms.qa.testng.tests;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.AfterGroups;
import org.testng.annotations.BeforeGroups;
import org.testng.annotations.Test;

import com.dotcms.qa.selenium.pages.IBasePage;
import com.dotcms.qa.selenium.pages.backend.ILoginPage;
import com.dotcms.qa.selenium.pages.backend.IPortletMenu;
import com.dotcms.qa.selenium.pages.backend.IUsersPage;
import com.dotcms.qa.selenium.util.SeleniumConfig;
import com.dotcms.qa.selenium.util.SeleniumPageManager;

/**
 * This class manage the TestRail suite of test for Users
 * @author Oswaldo Gallango
 * @since 11/12/2014
 * @version 1.0
 */
public class UsersTest {

	//Logger
	private static final Logger logger = Logger.getLogger(UsersTest.class);

	private IPortletMenu portletMenu = null;
	private IUsersPage usersPage = null;
	//BackEnd and FrontEnd managers
	private SeleniumPageManager backendMgr = null;
	private SeleniumPageManager frontendMgr = null;
	//Sites Info
	private String demoServerURL = null;
	private String mobileServerURL = null;
	private String sharedServerURL = null;
	private ILoginPage loginPage = null;
	//Backend User Info
	private String backendUserEmail = null;
	private String backendUserPassword = null;
	//Test User variables
	private final String nonExistingUserEmail ="nonexistinguser@dotcms.com";
	private final String fakeEmail ="fake@dotcms.com";
	private final String fakeFirstName ="DotCMSTest";
	private final String fakeLastName ="ToDelete";
	private final String fakePassword = "testUser123";
	private String fakeUserId ="";

	private final String editUserEmail = "bill@dotcms.com";
	private final String editUserPassword="bill";
	private final String tag = "my tc259 tag";
	private final String tagBase ="group";
	private final String roleName = "CMS Administrator";

	private final String frontendLoginPage="dotCMS/login?referrer=/intranet/";
	private final String frontendIntranetPage="intranet/";
	private final String frontendNews="news-events/news/";
	private final String frontendResources="resources/index.html";
	private final String frontendServices="services/private-banking/";
	private final String frontendProducts ="products/";
	private final String frontendLogoutPage="/dotCMS/logout";

	/**
	 * Initialize variables and login the user to the backend
	 * @throws Exception
	 */
	@BeforeGroups (groups = {"Users"})
	public void init() throws Exception {
		logger.info("**UsersTests.init() beginning**");
		SeleniumConfig config = SeleniumConfig.getConfig();
		demoServerURL = config.getProperty("demoServerURL");
		mobileServerURL = config.getProperty("mobileServerURL");
		sharedServerURL = config.getProperty("sharedServerURL");

		// Backend login
		backendUserEmail = config.getProperty("backend.user.Email");
		backendUserPassword = config.getProperty("backend.user.Password");
		backendMgr = RegressionSuiteEnv.getBackendPageManager();
		loginPage = backendMgr.getPageObject(ILoginPage.class);
		loginPage.login(backendUserEmail, backendUserPassword);

		//Frontend login
		frontendMgr = RegressionSuiteEnv.getFrontendPageManager(); 
		
		//Initialize portletMenu and UsersPage
		portletMenu = backendMgr.getPageObject(IPortletMenu.class);
		usersPage = portletMenu.getUsersPage();
	}

	/**
	 * Logout User from backEnd
	 * @throws Exception
	 */
	@AfterGroups (groups = {"Users"})
	public void teardown() throws Exception {
		logger.info("**UsersTests.teardown() beginning**");

		//Need to add delete test user
		//setting userId to delete at the end of the test
		Map<String, String> fakeUser = usersPage.getUserProperties(fakeEmail);
		fakeUserId = fakeUser.get("userId");
		if(fakeUserId != null && !fakeUserId.equals("")){
			usersPage.dropUser(fakeUserId,SeleniumConfig.getConfig());
		}

		// logout
		backendMgr.logoutBackend();
		logger.info("**UsersTests.teardown() ending**");
	}

	/**
	 * Sleep method
	 */
	public void sleep() {
		try{
			Thread.sleep(1000);
		}catch(Exception e){
			logger.error(e);
		}
	}
	/**
	 * Validate the search by user test case. Set here:
	 * http://qa.dotcms.com/index.php?/cases/view/257
	 * @throws Exception
	 */
	@Test (groups = {"Users"})
	public void tc257_SearchUserByEmailAddress() throws Exception {
		//Verify an existing user
		Assert.assertTrue(usersPage.doesUserEmailExist(backendUserEmail),"ERROR - The user should exist. User Email:"+backendUserEmail);

		//Verify a non existing user
		Assert.assertFalse(usersPage.doesUserEmailExist(nonExistingUserEmail),"ERROR - The user should not exist. User Email:"+nonExistingUserEmail);     
	}

	/**
	 * Test the edit user info functionality. Set here:
	 * http://qa.dotcms.com/index.php?/cases/view/258
	 * @throws Exception
	 */
	@Test (groups = {"Users"})
	public void tc258_EditUser() throws Exception {
		Map<String, String> originalUser = usersPage.getUserProperties(editUserEmail);

		String firstName2=originalUser.get("firstName")+"_tc258";
		String lastName2=originalUser.get("lastName")+"_tc258";
		String emailAddress2="fake_tc258@dotcms.com";
		//User values to update
		Map<String, String> properties = new HashMap<String,String>();
		properties.put("firstName", firstName2);
		properties.put("lastName", lastName2);
		properties.put("emailAddress", emailAddress2);

		//Update User
		Assert.assertTrue(usersPage.editUser(editUserEmail,properties),"ERROR - User could not be edited. User Email:"+editUserEmail);

		//Verify if the user still exist with the previous email
		Assert.assertFalse(usersPage.doesUserEmailExist(editUserEmail),"ERROR - User email was not edited. It is using the old User Email:"+editUserEmail);     

		//Verify if the user exist with the new email
		Assert.assertTrue(usersPage.doesUserEmailExist(emailAddress2),"ERROR - User email was not edited. It is not using the new User Email:"+emailAddress2); 

		//Validate that all the fields where modified
		Map<String, String> currentUser = usersPage.getUserProperties(emailAddress2);

		/*
		 * Validate user change
		 */
		Assert.assertTrue(firstName2.equals(currentUser.get("firstName")),"ERROR - User first name was not edited. User Email:"+currentUser.get("emailAddress")); 
		//validate last name change
		Assert.assertTrue(lastName2.equals(currentUser.get("lastName")),"ERROR - User last name was not edited. User Email:"+currentUser.get("emailAddress")); 
		//validate email address
		Assert.assertTrue(emailAddress2.equals(currentUser.get("emailAddress")),"ERROR - User email was not edited. User Email:"+currentUser.get("emailAddress")); 

		/*
		 * Validate user restoration change
		 */
		Assert.assertTrue(usersPage.editUser(emailAddress2,originalUser),"ERROR - User could not be restored. User Email:"+emailAddress2);

		//Validate that all the fields where modified
		currentUser = usersPage.getUserProperties(originalUser.get("emailAddress"));

		Assert.assertTrue(currentUser.get("firstName").equals(originalUser.get("firstName")),"ERROR - User first name was not restored. User Email:"+currentUser.get("emailAddress")); 
		//validate last name change
		Assert.assertTrue(currentUser.get("lastName").equals(originalUser.get("lastName")),"ERROR - User last name was not restored. User Email:"+currentUser.get("emailAddress")); 
		//validate email address
		Assert.assertTrue(currentUser.get("emailAddress").equals(originalUser.get("emailAddress")),"ERROR - User email was not restored. User Email:"+currentUser.get("emailAddress")); 
	}

	/**
	 * Test Marketing History and add tag o user. Set here:
	 * http://qa.dotcms.com/index.php?/cases/view/259
	 * @throws Exception
	 */
	@Test (groups = {"Users"})
	public void tc259_MarketingHistoryAndTag() throws Exception {
		/**
		 * Add/Remove Tag
		 */
		//validate that the user doesn't have the tag
		Assert.assertFalse(usersPage.doesHaveTag(tag,editUserEmail),"ERROR - User should not have this tag assigned: User Email:"+editUserEmail+" , Tag:"+tag);

		//add the tag
		usersPage.addTag(tag,editUserEmail);

		//validate that the tag was included
		Assert.assertTrue(usersPage.doesHaveTag(tag,editUserEmail),"ERROR - User should have this tag assigned: User Email:"+editUserEmail+" , Tag:"+tag);

		//remove the tag
		usersPage.removeTag(tag,editUserEmail);

		//validate that the tag was removed
		Assert.assertFalse(usersPage.doesHaveTag(tag,editUserEmail),"ERROR - User should not have this tag assigned: User Email:"+editUserEmail+" , Tag:"+tag);

		/**
		 * View History
		 */
		//validate if the user doesn't have visit history
		boolean haveVisitHistory = usersPage.doesHaveVisitHistory(editUserEmail);

		if(!haveVisitHistory){
			//generate some visit history
			IBasePage page = frontendMgr.loadPage(demoServerURL + frontendLoginPage);
			sleep();
			page.getWebElementPresent(By.id("macro-login-user-name")).clear();
			page.getWebElementPresent(By.id("macro-login-user-name")).sendKeys(editUserEmail);
			page.getWebElementPresent(By.id("macro-login-password")).clear();
			page.getWebElementPresent(By.id("macro-login-password")).sendKeys(editUserPassword);
			page.getWebElementPresent(By.id("macro-login-button")).click();
			sleep();
			page = frontendMgr.loadPage(demoServerURL + frontendIntranetPage);
			sleep();
			page = frontendMgr.loadPage(demoServerURL + frontendNews);
			sleep();
			page = frontendMgr.loadPage(demoServerURL +frontendResources);
			sleep();
			page = frontendMgr.loadPage(demoServerURL +frontendServices);
			sleep();
			page = frontendMgr.loadPage(demoServerURL +frontendProducts);
			sleep();
			page = frontendMgr.loadPage(demoServerURL + frontendLogoutPage);
			sleep();
			haveVisitHistory = usersPage.doesHaveVisitHistory(editUserEmail);
		}
		Assert.assertTrue(haveVisitHistory,"ERROR -  User does not have click history. User Email:"+editUserEmail);
	}

	/**
	 * Test the add user functionality. Set here:
	 * http://qa.dotcms.com/index.php?/cases/view/261
	 * @throws Exception 
	 */
	@Test (groups = {"Users"})
	public void tc261_AddUser() throws Exception{
		//Add a new User
		usersPage.addUser(fakeFirstName, fakeLastName, fakeEmail, fakePassword);
		//Verify if the user was created
		Assert.assertTrue(usersPage.doesUserEmailExist(fakeEmail), "ERROR - User was not created. UserEmail:"+fakeEmail);
	}

	/**
	 * Test the add user roles functionality. Set here:
	 * http://qa.dotcms.com/index.php?/cases/view/263
	 * @throws Exception 
	 */
	@Test (groups = {"Users"})
	public void tc263_AddRolesToUser() throws Exception{
		//Validate that the user doesn't have the role
		Assert.assertFalse(usersPage.doesUserHaveRole(roleName, fakeEmail), "ERROR - User should not have assigned this role. UserEmail:"+fakeEmail+", Role:"+roleName);

		//Add role
		usersPage.addRoleToUser(roleName, fakeEmail);

		//Validate that the user have the role
		Assert.assertTrue(usersPage.doesUserHaveRole(roleName, fakeEmail), "ERROR - User should have assigned this role. UserEmail:"+fakeEmail+", Role:"+roleName);

		//Remove the role
		usersPage.removeRoleFromUser(roleName, fakeEmail);

		//Validate that the user doesn't have the role
		Assert.assertFalse(usersPage.doesUserHaveRole(roleName, fakeEmail), "ERROR - User should not have assigned this role. UserEmail:"+fakeEmail+", Role:"+roleName);
	}

	/**
	 * Test adding several tags to user and validate suggestion box. Set here:
	 * http://qa.dotcms.com/index.php?/cases/view/655
	 * @throws Exception
	 */
	@Test (groups = {"Users"})
	public void tc655_AddSeveralTagsAndValidateSuggestion() throws Exception {
		/**
		 * Add Tags
		 */
		for(int i =1; i <=20;i++ ){
			usersPage.addTag(tagBase+i,editUserEmail);
		}
		
		sleep();
		//get the suggested tag, base on the tagBase text
		String suggestions = usersPage.getTagSuggestions(tagBase, fakeEmail);
		for(int i =1; i <=20;i++ ){
			Assert.assertTrue(suggestions.contains(tagBase+i+","), "ERROR - The tag should exist in suggestions box. Tag:"+tagBase+i);
		}
		
		//remove the tags from the user
		for(int i =1; i <=20;i++ ){
			usersPage.removeTag(tagBase+i,editUserEmail);
		}
	}

}
