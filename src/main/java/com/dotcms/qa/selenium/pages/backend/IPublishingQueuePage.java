package com.dotcms.qa.selenium.pages.backend;

import java.util.List;
import java.util.Map;

import com.dotcms.qa.selenium.pages.IBasePage;

/**
 * Publishing Queue Page Interface
 * @author Oswaldo Gallango
 * @since 03/10/2015
 * @version 1.0
 *
 */ 
public interface IPublishingQueuePage extends IBasePage{

	/**
	 * Get the bundles tab active
	 * @throws Exception
	 */
	public void getBundlesTab() throws Exception;
	
	/**
	 * Search the specified bundle and click the push publish button. This method return the bundle Id
	 * @param bundleName Name of the bundle
	 * @return bundle id 
	 * @throws Exception
	 */
	public String pushPublishBundle(String bundleName) throws Exception;
	
	/**
	 * Get the pending bundles tab active
	 * @throws Exception
	 */
	public void getPendingBundlesTab() throws Exception;
	
	/**
	 * Verifies if in the pending tab the bundle is still listed
	 * @param bundleId Id of the bundle
	 * @param poolInterval - how many milliseconds to wait between polling
	 * @param maxPoolCount - maximum number of times to poll before returning value of eval.evaluate()
	 * @return true if the bundle was pushed, false if is still pending
	 * @throws Exception
	 */
	public boolean isBundlePushed(String bundleId,long poolInterval,int maxPoolCount) throws Exception;
	
	/**
	 * Verifies if the bundle is pending for push
	 * @param bundleId Id of the bundle
	 * @return true if the bundle was pushed, false if is still pending
	 * @throws Exception
	 */
	public boolean isBundlePending(String bundleId) throws Exception;
	
	/**
	 * Get the status/History tab active
	 * @throws Exception
	 */
	public void getStatusHistoryTab() throws Exception;
	
	/**
	 * Get the bundle most registered entries in the status history tab 
	 * @param bundleName Name of the bundle
	 * @return List<Map<String,String>>
	 * @throws Exception
	 */
	public List<Map<String,String>> getBundleHistoryStatus(String bundleName) throws Exception;
	
	/**
	 * Delete all the entries in the history/status tab
	 * @throws Exception
	 */
	public void deleteAllHistoryStatus() throws Exception;
}
