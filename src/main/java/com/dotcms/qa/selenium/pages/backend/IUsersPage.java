package com.dotcms.qa.selenium.pages.backend;

import java.sql.SQLException;
import java.util.Map;

import org.testng.Assert;

import com.dotcms.qa.selenium.pages.IBasePage;
import com.dotcms.qa.selenium.util.SeleniumConfig;
/**
 * Users Page Interface
 * @author Oswaldo Gallango
 * @since 11/12/2014
 * @version 1.0
 *
 */
public interface IUsersPage extends IBasePage {
	/**
	 * Validates if the user exist in the user manager page, looking by the user email address
	 * @param email User Email
	 * @return boolean, true if the user exist, false if not.
	 */
	public boolean doesUserEmailExist(String email);
	
	/**
	 * Add a user in dotCMS
	 * @param firstname User firstname
	 * @param lastname User lastname
	 * @param email User email address
	 * @param password User password
	 */
	public void addUser(String firstname, String lastname, String email, String password);
	
	/**
	 * Update the properties/fields set in the map for the user with the specified email address
	 * @param email Current user email address 
	 * @param properties User inputs to modify
	 * @return true if the user was updated, false if not
	 */
	public boolean editUser(String email, Map<String,String> properties);
	
	/**
	 * Get the map with the properties of the user to validate
	 * @param email User email to search
	 * @return Map with the user pages properties
	 */
	public Map<String,String> getUserProperties(String email);
	
	/**
	 * Add the specified role to user.
	 * @param roleName Role to add
	 * @param userEmail User email to search
	 * @return true is the role was added, false if not
	 */
	public boolean addRoleToUser(String roleName, String userEmail);
	
	/**
	 * Remove the specified role from the user.
	 * @param roleName Role to remove
	 * @param userEmail User email to search
	 * @return true is the role was removed, false if not
	 */
	public boolean removeRoleFromUser(String roleName, String userEmail);
	
	/**
	 * Validate if the user have assigned the specified role
	 * @param roleName Role to remove
	 * @param userEmail User email to search
	 * @return true is the role is assigned, false if not
	 */
	public boolean doesUserHaveRole(String roleName, String userEmail);
	
	/**
	 * Add a tag to a user in the user admin portlet
	 * @param tag Tag to add
	 * @param userEmail User email to search
	 */
	public void addTag(String tag, String userEmail);
	
	/**
	 * Validates if the user have assigned that tag in the user admin portlet
	 * @param tag Tag to add
	 * @param userEmail User email to search
	 * @return true if the user have the tag, false if not
	 */
	public boolean doesHaveTag(String tag, String userEmail);
	
	/**
	 * Remove a user tag in the user admin portlet
	 * @param tag Tag to add
	 * @param userEmail User email to search
	 */
	public void removeTag(String tag,String userEmail);
	
	/**
	 * Validate if the user have some visit history in the
	 * user admin portlet
	 * @param userEmail User email to search
	 * @return true if the user have visit history set, false if not
	 */
	public boolean doesHaveVisitHistory(String userEmail);
	
	/**
	 * Return the tag suggestions for the text passed
	 * @param userEmail User email to search
	 * @param tagText Base text to search
	 * @return The string suggestion for the tag text set
	 */
	public String getTagSuggestions(String tagText, String userEmail);
	
	/**
	 * Delete the user create in the test 
	 * @param userId Id of the user to delete
	 */
	public void dropUser(String userId, SeleniumConfig config) throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException;
}
