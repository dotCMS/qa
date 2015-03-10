package com.dotcms.qa.selenium.pages.backend;

import java.util.List;

import org.openqa.selenium.WebElement;

import com.dotcms.qa.selenium.pages.IBasePage;

/**
 * PublishingEnvironments Page Interface
 * @author Oswaldo Gallango
 * @since 03/06/2015
 * @version 1.0
 *
 */ 
public interface IPublishingEnvironments extends IBasePage {
	
	/**
	 * Generates a new Send to environment
	 * @param environmentName String name of the new environment
	 * @param whocanUse       String list with the User or roles who can use the push publish
	 * @param pushMode        String indicating if the push option is: "pushToOne" or "pushToAll"
	 * @throws Exception
	 */
	public void createEnvironment(String environmentName, List<String> whocanUse, String pushMode) throws Exception;
	
	/**
	 * Add a server to the specified environment
	 * @param environmentName String name of the new environment 
	 * @param serverName      Receiver Server Name 
	 * @param address         Receiver Server address 
	 * @param port            Port number (as a String)
	 * @param protocal        Protocol to use http or https
	 * @param key			  Receiver authorization key
	 * @throws Exception
	 */
	public void addServerToEnvironment(String environmentName, String serverName, String address, String port, String protocol, String key) throws Exception;

	/**
	 * Add a receive from server
	 * @param serverName      Receiver Server Name 
	 * @param address         Receiver Server address 
	 * @param key			  Receiver authorization key
	 * @throws Exception
	 */
	public void addReceiveFrom(String serverName, String address,String key) throws Exception;
	
	/**
	 * Delete the specified environment
	 * @param environmentName Name of the environment
	  * @throws Exception
	 */
	public void deleteEnvironment(String environmentName) throws Exception;
	
	/**
	 * Delete the specified receive from server
	 * @param serverName  Receiver Server Name 
	 * @throws Exception
	 */
	public void deleteReceiveFromServer(String serverName) throws Exception;
}
