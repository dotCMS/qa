package com.dotcms.qa.testng.tests;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.AfterGroups;
import org.testng.annotations.BeforeGroups;
import org.testng.annotations.Test;

import com.dotcms.qa.selenium.pages.backend.ILoginPage;
import com.dotcms.qa.selenium.pages.backend.IMailingListPage;
import com.dotcms.qa.selenium.pages.backend.IPortletMenu;
import com.dotcms.qa.selenium.pages.backend.IRolesPage;
import com.dotcms.qa.selenium.pages.backend.IUsersPage;
import com.dotcms.qa.selenium.util.SeleniumConfig;
import com.dotcms.qa.selenium.util.SeleniumPageManager;
import com.dotcms.qa.util.UsersPageUtil;

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
	private IRolesPage rolesPage = null;
	private IMailingListPage mailingListPage=null;
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
	private final String fakePassword = "testUser123#";
	private String fakeUserId ="";

	private final String editUserEmail = "bill@dotcms.com";
	private final String editUserPassword="bill";
	private final String tag = "my tc259 tag";
	private final String tagBase ="tcgroup";
	private final String roleName = "CMS Administrator";
	private final String tabName = "Mailing-List";
	private final String mailingListPortlet = "com.dotcms.repackage.javax.portlet.title.EXT_16";
	private final String mailingListName="ct-262";
	private final String frontendLoginPage="dotCMS/login?referrer=/intranet/";
	private final String frontendIntranetPage="intranet/";
	private final String frontendNews="news-events/news/";
	private final String frontendResources="resources/index.html";
	private final String frontendServices="services/private-banking/";
	private final String frontendProducts ="products/";
	private final String frontendLogoutPage="dotCMS/logout";

	private final String roleWithApostrophe="China's reviewer";
	private final String roleWithApostropheKey="tc14129";
	private final String roleWithApostropheDescription="tc14129";

	private SeleniumConfig config;
	/**
	 * Initialize variables and login the user to the backend
	 * @throws Exception
	 */
	@BeforeGroups (groups = {"Users"})
	public void init() throws Exception {
		try {
			logger.info("**UsersTests.init() beginning**");
			config = SeleniumConfig.getConfig();
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
			logger.info("**UsersTests.init() ending**");
		}
		catch(Exception e) {
    		logger.error("ERROR - UsersTests.init()", e);
    		throw(e);
    	}
	}

	/**
	 * Logout User from backEnd
	 * @throws Exception
	 */
	@AfterGroups (groups = {"Users"})
	public void teardown() throws Exception {

		try {
			logger.info("**UsersTests.teardown() beginning**");

			//setting userId to delete at the end of the test
			usersPage = portletMenu.getUsersPage();
			Map<String, String> fakeUser = usersPage.getUserProperties(fakeEmail);
			fakeUserId = fakeUser.get("userId");
			if(fakeUserId != null && !fakeUserId.equals("")){
				UsersPageUtil.deleteTag(tag);
				UsersPageUtil.deleteTag(tagBase);
				UsersPageUtil.deleteUser(fakeUserId);
			}

			// logout
			backendMgr.logoutBackend();
			logger.info("**UsersTests.teardown() ending**");
		}
		catch(Exception e) {
			logger.error("ERROR - UsersTests.teardown()", e);
			throw(e);
		}
	}

	/**
	 * Validate the search by user test case. Set here:
	 * http://qa.dotcms.com/index.php?/cases/view/257
	 * @throws Exception
	 */
	@Test (groups = {"Users"})
	public void tc257_SearchUserByEmailAddress() throws Exception {
		usersPage = portletMenu.getUsersPage();
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
		usersPage = portletMenu.getUsersPage();
		Map<String, String> originalUser = usersPage.getUserProperties(editUserEmail);

		String firstName2=originalUser.get("firstName")+"tc258";
		String lastName2=originalUser.get("lastName")+"tc258";
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
		usersPage = portletMenu.getUsersPage();
		/**
		 * Add/Remove Tag
		 */
		//validate that the user doesn't have the tag
		Assert.assertFalse(usersPage.doesHaveTag(tag,editUserEmail),"ERROR - User should not have this tag assigned: User Email:"+editUserEmail+" , Tag:"+tag);
		sleep();

		//add the tag
		usersPage.addTag(tag,editUserEmail);

		//validate that the tag was included
		Assert.assertTrue(usersPage.doesHaveTag(tag,editUserEmail),"ERROR - User should have this tag assigned: User Email:"+editUserEmail+" , Tag:"+tag);
		sleep();

		//remove the tag
		usersPage.removeTag(tag,editUserEmail);
		sleep();

		//validate that the tag was removed
		Assert.assertFalse(usersPage.doesHaveTag(tag,editUserEmail),"ERROR - User should not have this tag assigned: User Email:"+editUserEmail+" , Tag:"+tag);

		/**
		 * View History
		 */
		//validate if the user doesn't have visit history
		boolean haveVisitHistory = usersPage.doesHaveVisitHistory(editUserEmail);

		if(!haveVisitHistory){
			//generate some visit history
			UsersPageUtil.frontEndLogin(demoServerURL + frontendLoginPage,editUserEmail,editUserPassword);
			frontendMgr.loadPage(demoServerURL + frontendIntranetPage);
			frontendMgr.loadPage(demoServerURL + frontendNews);
			frontendMgr.loadPage(demoServerURL +frontendResources);
			frontendMgr.loadPage(demoServerURL +frontendServices);
			frontendMgr.loadPage(demoServerURL +frontendProducts);
			frontendMgr.loadPage(mobileServerURL);
			frontendMgr.loadPage(sharedServerURL);

			//generate some visit history
			frontendMgr.loadPage(demoServerURL + frontendIntranetPage);
			frontendMgr.loadPage(demoServerURL + frontendNews);
			frontendMgr.loadPage(demoServerURL +frontendResources);
			frontendMgr.loadPage(demoServerURL +frontendServices);
			frontendMgr.loadPage(demoServerURL +frontendProducts);
			frontendMgr.loadPage(demoServerURL + frontendLogoutPage);
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
		usersPage = portletMenu.getUsersPage();
		//Add a new User
		usersPage.addUser(fakeFirstName, fakeLastName, fakeEmail, fakePassword);
		//Verify if the user was created
		Assert.assertTrue(usersPage.doesUserEmailExist(fakeEmail), "ERROR - User was not created. UserEmail:"+fakeEmail);
	}

	/**
	 * Test the Import users functionality. Set here:
	 * http://qa.dotcms.com/index.php?/cases/view/262
	 * @throws Exception
	 */
	@Test (groups = {"Broken"})
	public void tc262_importUser() throws Exception{
		rolesPage = portletMenu.getRolesPage();
		//Creating test tab with mailing list portlet
		Assert.assertTrue(rolesPage.addPortletToRolesTabs(roleName, tabName, mailingListPortlet),"ERROR - The tab:"+tabName+" was not created.");;
		//reload page to show new tab
		rolesPage.reload();
		//import user from mailing list tab
		mailingListPage = portletMenu.getMailingListPage();
		Assert.assertTrue(mailingListPage.loadUsers(mailingListName),"ERROR - Users could not be imported. Mailing List:"+mailingListName);
		sleep();

		//validate that the users where imported
		List<String> users = mailingListPage.getMailingListSubscribers(mailingListName);
		Assert.assertTrue(users.size() > 0,"ERROR - The users could not be imported in the mailing list tab. Mailing List:"+mailingListName);

		usersPage = portletMenu.getUsersPage();
		//validate the user where created and delete it
		for(String user : users){
			//validate user
			Assert.assertTrue(usersPage.doesUserEmailExist(user),"ERROR - The user should exist. User Email:"+user);

			//delete User
			Map<String, String> userData = usersPage.getUserProperties(user);
			UsersPageUtil.deleteUser(userData.get("userId"));

			//validate user
			Assert.assertFalse(usersPage.doesUserEmailExist(user),"ERROR - The user should not exist. User Email:"+user);
		}
		sleep();

		//Delete mailing list
		mailingListPage = portletMenu.getMailingListPage();
		Assert.assertTrue(mailingListPage.deleteMailingList(mailingListName),"ERROR - Mailing List could not be deleted. Mailing List:"+mailingListName);
		sleep();

		//removing roles
		rolesPage = portletMenu.getRolesPage();
		Assert.assertTrue(rolesPage.removeTabFromRole(roleName, tabName),"ERROR - The tab:"+tabName+" was not removed.");
	}


	/**
	 * Test the add user roles functionality. Set here:
	 * http://qa.dotcms.com/index.php?/cases/view/263
	 * @throws Exception 
	 */
	@Test (groups = {"Users"})
	public void tc263_AddRolesToUser() throws Exception{
		usersPage = portletMenu.getUsersPage();
		//Validate that the user doesn't have the role
		Assert.assertFalse(usersPage.doesUserHaveRole(roleName, fakeEmail), "ERROR - User should not have assigned this role. UserEmail:"+fakeEmail+", Role:"+roleName);

		//Add role
		usersPage.addRoleToUser(roleName, fakeEmail);
		sleep();

		//Validate that the user have the role
		Assert.assertTrue(usersPage.doesUserHaveRole(roleName, fakeEmail), "ERROR - User should have assigned this role. UserEmail:"+fakeEmail+", Role:"+roleName);
		sleep();

		//Remove the role
		usersPage.removeRoleFromUser(roleName, fakeEmail);
		sleep();

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
		usersPage = portletMenu.getUsersPage();
		sleep();

		/**
		 * Add Tags
		 */
		for(int i =1; i <=20;i++ ){
			usersPage.addTag(tagBase+i,editUserEmail);
			sleep();
			if(!usersPage.doesHaveTag(tagBase+i,editUserEmail)){
				usersPage.addTag(tagBase+i,editUserEmail);
			}
		}

		//get the suggested tag, base on the tagBase text
		String suggestions = usersPage.getTagSuggestions(tagBase, editUserEmail);
		for(int i =1; i <=20;i++ ){
			Assert.assertTrue(suggestions.contains(tagBase+i+","), "ERROR - The tag should exist in suggestions box. Tag:"+tagBase+i);
		}

		//remove the tags from the user
		for(int i =1; i <=20;i++ ){
			usersPage.removeTag(tagBase+i,editUserEmail);
			sleep();
		}
	}

	/**
	 * Test adding role with apostrophe. Set here:
	 * http://qa.dotcms.com/index.php?/cases/view/14129
	 * @throws Exception
	 */
	@Test (groups = {"Broken", "Users"})
	public void tc14129_AddRoleWithApostrophe() throws Exception {
		rolesPage = portletMenu.getRolesPage();
		//Creating role with apostrophe
		rolesPage.createRole(roleWithApostrophe, roleWithApostropheKey, roleWithApostropheDescription, true, true, true);
		sleep();
		//Validate role creation
		Assert.assertTrue(rolesPage.doesRoleExist(roleWithApostrophe), "ERROR - Role with apostrophe should exist. Role Name:"+roleWithApostrophe);
		sleep();

		//Removing role with apostrophe
		rolesPage.removeRole(roleWithApostrophe);
		sleep();

		//validate role deletion
		Assert.assertFalse(rolesPage.doesRoleExist(roleWithApostrophe), "ERROR - Role with apostrophe should not exist. Role Name:"+roleWithApostrophe);
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
	 * Sleep method
	 * @param seconds
	 */
	public void sleep(int seconds) {
		try{
			Thread.sleep(seconds*1000);
		}catch(Exception e){
			logger.error(e);
		}
	}
}
