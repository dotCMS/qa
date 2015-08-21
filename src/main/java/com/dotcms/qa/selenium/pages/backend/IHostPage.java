package com.dotcms.qa.selenium.pages.backend;

import org.openqa.selenium.WebElement;

import com.dotcms.qa.selenium.pages.IBasePage;

public interface IHostPage extends IBasePage {
	public boolean doesHostExist(String hostName);
	public void addBlankHost(String hostName) throws Exception;
	/**
	 * Create a new host by copying an existing one. 
	 * Checking every second for up to 2 minutes to see if the host was copied
	 * @param hostName - name of tne new host
	 * @param hostToCopy - name of the host to be copied
	 * @return true if the host was copied, false if not
	 * @throws Exception 
	 */
	public boolean addCopyExistingHost(String hostName, String hostToCopy) throws Exception;
	
	/**
	 * Create a new host by copying an existing one.This method allows to specify the time to wait to check if the host was copied
	 * @param hostName - name of tne new host
	 * @param hostToCopy - name of the host to be copied
	 * @param poolInterval - how many milliseconds to wait between polling
	 * @param maxPoolCount - maximum number of times to poll before returning value of eval.evaluate()
	 * @return true if the host was copied, false if not
	 * @throws Exception
	 */
	public boolean addCopyExistingHost(String hostName, String hostToCopy, long poolInterval, int maxPoolCount) throws Exception;
	
	public void archiveHost(String hostName, boolean confirm) throws Exception;
	
	/**
	 * Delete a host.Checking every 2 second for up to 2 minutes to see if the host was deleted
	 * @param hostName - name of tne new host
	 * @param confirm - accept or refuse the confirmation popup
	 * @param poolInterval - how many milliseconds to wait between polling
	 * @param maxPoolCount - maximum number of times to poll before returning value of eval.evaluate()
	 * @return true if the host was deleted, false if not
	 * @throws Exception
	 */
	public boolean deleteHost(String hostName, boolean confirm) throws Exception;
	
	/**
	 * Delete a host.This method allows to specify the time to wait to check if the host was deleted
	 * @param hostName - name of tne new host
	 * @param confirm - accept or refuse the confirmation popup
	 * @param poolInterval - how many milliseconds to wait between polling
	 * @param maxPoolCount - maximum number of times to poll before returning value of eval.evaluate()
	 * @return true if the host was deleted, false if not
	 * @throws Exception
	 */
	public boolean deleteHost(String hostName, boolean confirm, long poolInterval, int maxPoolCount) throws Exception;
	
	public void stopHost(String hostName, boolean confirm) throws Exception;
	public void editHost(String hostName, String newHostName, String palians);
	public void addHostVariable(String hostName, String varName, String varKey, String varValue) throws Exception;
	public void deleteHostVariable(String hostName, String varName, boolean confirm) throws Exception;
	public boolean doesHostVariableExist(String hostName, String variableName) throws Exception;
	public IHostVariablesDialog getHostVariablesPage(String hostName) throws Exception;
	public WebElement returnHost(String hostName);
	public void toggleShowArchived();
	
	/**
	 * Add a host thumbnail into the specified host
	 * @param hostName Name of the host where the thumbnail will be added
	 * @throws Exception
	 */
	public void addHostThumbnail(String hostName) throws Exception;
	
	/**
	 * Remove the host thumbnail from the specified host
	 * @param hostName Name of the host where the thumbnail will be added
	 * @throws Exception
	 */
	public void removeHostThumbnail(String hostName) throws Exception;
	
	/**
	 * Validate if the host have a host thumbnail
	 * @param hostName Name of the host where the thumbnail will be added
	 * @return true if the host have a thumbnail, false if not
	 * @throws Exception
	 */
	public boolean doesHostHaveHostThumbnail(String hostName) throws Exception;
	
	/**
	 * Validate is the host is active
	 * @param hostName Name of the host to validate
	 * @return true if the host is active false if not
	 */
	public boolean isHostActive(String hostName) throws Exception;
	
	/**
	 * Validate is the host is default
	 * @param hostName Name of the host to validate
	 * @return true if the host is active false if not
	 */
	public boolean isHostDefault(String hostName) throws Exception;
	
	/**
	 * Start a inactive host
	 * @param hostName Host name
	 * @param confirm
	 * @throws Exception
	 */
	public void startHost(String hostName, boolean confirm) throws Exception;
	

	/**
	 * make default the specified host
	 * @param hostName Host name
	 * @param confirm
	 * @throws Exception
	 */
	public void makeDefaultHost(String hostName, boolean confirm) throws Exception;
	
	public boolean isHostCopyInProgress(String hostName) throws Exception;
	
	/**
	 * Click the push publish option from the right click menu options
	 * @param hostName Host Name
	 * @throws Exception
	 */
	public void pushHost(String hostName) throws Exception;
	
	/**
	 * Click the push publish option from the right click menu options
	 * @param hostName Host name
	 * @param pushType   Type of push to apply (PUSH_TO_REMOVE,PUSH_TO_ADD,PUSH_AND_REMOVE)
	 * @param pushDate   Date with format M/d/yyyy(optional)
	 * @param pushTime   Time format H:mm a (optional)
	 * @param expireDate Date with format M/d/yyyy(optional)
	 * @param expireTime Time format H:mm a (optional)
	 * @param force      Force to publish (Only valid for push and push a remove)
	 * @throws Exception
	 */
	public void pushContent(String hostName, String pushType, String pushDate, String pushTime, String expireDate, String expireTime, boolean force) throws Exception;
	
}







