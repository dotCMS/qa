package com.dotcms.qa.selenium.pages.backend.common;

import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileFilter;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.dotcms.qa.selenium.pages.backend.IPublishingQueuePage;
import com.dotcms.qa.selenium.pages.backend.IPushPublishDialogPage;
import com.dotcms.qa.selenium.pages.common.BasePage;
import com.dotcms.qa.selenium.util.SeleniumPageManager;
import com.dotcms.qa.util.Evaluator;
import com.dotcms.qa.util.WebKeys;

/**
 * This class implements the methods defined in the IPublishingQueuePage interface
 * @author Oswaldo Gallango
 * @since 03/10/2015
 * @version 1.0
 * 
 */
public class PublishingQueuePage extends BasePage implements IPublishingQueuePage{

	private String bundle=null;
	public PublishingQueuePage(WebDriver driver) {
		super(driver);
	}

	/**
	 * Get the bundles tab active
	 * @throws Exception
	 */
	public void getBundlesTab() throws Exception {
		getWebElement(By.id("mainTabContainer_tablist_unpushedBundles")).click();
		sleep(2);
	}

	/**
	 * Search the specified bundle and click the push publish button. This method return the bundle Id
	 * @param bundleName Name of the bundle
	 * @return bundle id 
	 * @throws Exception
	 */
	public String pushPublishBundle(String bundleName) throws Exception{
		WebElement bundle = findBundle(bundleName);
		String bundleId = null;
		List<WebElement> ths = bundle.findElements(By.tagName("th"));
		bundleId = ths.get(0).getAttribute("onclick");
		bundleId=bundleId.substring(bundleId.indexOf("'")+1, bundleId.lastIndexOf("'"));
		List<WebElement> buttons = ths.get(1).findElements(By.cssSelector("span[class='dijitReset dijitInline dijitButtonText']"));
		for(WebElement button : buttons){
			if(button.getText().equals(getLocalizedString("Remote-Publish"))){
				button.click();
				break;
			}
		}
		sleep(2);
		IPushPublishDialogPage pushingDialog = SeleniumPageManager.getBackEndPageManager().getPageObject(IPushPublishDialogPage.class);
		pushingDialog.push(WebKeys.PUSH_TO_ADD, null, null, null, null, false);
		return bundleId;
	}

	/**
	 * Return the bundle row
	 * @param bundleName  Name of the bundle
	 * @return WebElement
	 * @throws Exception
	 */
	private WebElement findBundle(String bundleName) throws Exception{
		WebElement bundleRow = null;
		List<WebElement> bundles = getWebElement(By.id("unpushedBundlesDiv")).findElements(By.tagName("table"));
		for(WebElement bundle : bundles){
			List<WebElement> ths = bundle.findElements(By.tagName("th"));
			if(ths.get(0).getText().trim().equals(bundleName)){
				bundleRow=bundle;
				break;
			}
		}
		return bundleRow;
	}

	/**
	 * Get the pending bundles tab active
	 * @throws Exception
	 */
	public void getPendingBundlesTab() throws Exception {
		getWebElement(By.id("mainTabContainer_tablist_queue")).click();
	}

	/**
	 * Verifies if in the pending tab the bundle is still listed
	 * @param bundleId Id of the bundle
	 * @param poolInterval - how many milliseconds to wait between polling
	 * @param maxPoolCount - maximum number of times to poll before returning value of eval.evaluate()
	 * @return true if the bundle was pushed, false if is still pending
	 * @throws Exception
	 */
	public boolean isBundlePushed(String bundleId,long poolInterval,int maxPoolCount) throws Exception{
		bundle = bundleId;
		Evaluator eval = new Evaluator() {
			public boolean evaluate() throws Exception {  // returns true if host copy is done
				return !isBundlePending(bundle);
			}
		};
		return pollForValue(eval, true, poolInterval, maxPoolCount);
	}

	/**
	 * Verifies if the bundle is pending for push
	 * @param bundleId Id of the bundle
	 * @return true if the bundle was pushed, false if is still pending
	 * @throws Exception
	 */
	public boolean isBundlePending(String bundleId) throws Exception{
		boolean isPending = false;
		getPendingBundlesTab();
		List<WebElement> bundles = getWebElement(By.id("queueContent")).findElements(By.tagName("table"));
		for(WebElement bundle: bundles){ 
			List<WebElement> rows = bundle.findElements(By.tagName("tr"));
			for(WebElement row : rows){
				List<WebElement> columns = row.findElements(By.tagName("th"));
				if(columns.size() > 1){
					if(columns.get(1).getText().contains(bundleId)){
						isPending=true;
						break;
					}
				}
			}
		}
		return isPending;
	}

	/**
	 * Get the status/History tab active
	 * @throws Exception
	 */
	public void getStatusHistoryTab() throws Exception{
		getWebElement(By.id("mainTabContainer_tablist_audit")).click();
	}

	/**
	 * Get the bundle most registered entries in the status history tab 
	 * @param bundleName Name of the bundle
	 * @return List<Map<String,String>>
	 * @throws Exception
	 */
	public List<Map<String,String>> getBundleHistoryStatus(String bundleName) throws Exception{
		getStatusHistoryTab();
		List<Map<String,String>> results = new ArrayList<Map<String,String>>();
		List<WebElement> bundles = getWebElement(By.id("auditContent")).findElements(By.tagName("table"));
		for(WebElement bundle: bundles){ 
			List<WebElement> rows = bundle.findElements(By.tagName("tr"));
			for(WebElement row : rows){
				List<WebElement> columns = row.findElements(By.tagName("td"));
				if(columns.size() > 1){
					if(columns.get(2).getText().trim().equals(bundleName)){
						Map<String, String> map = new HashMap<String, String>();
						String id = columns.get(1).getAttribute("onclick");
						map.put("bundleId", id.substring(id.indexOf("'")+1, id.lastIndexOf("'")));
						map.put("bundleName", columns.get(2).getText());
						map.put("bundleTitle", columns.get(3).getText());
						map.put("bundleStatus", columns.get(4).getText());
						map.put("bundleDateEntered", columns.get(5).getText());
						map.put("bundleDateUpdated", columns.get(6).getText());
						results.add(map);
					}
				}
			}
		}
		return results;
	}

	/**
	 * Delete all the entries in the history/status tab
	 * @throws Exception
	 */
	public void deleteAllHistoryStatus() throws Exception{
		getStatusHistoryTab();
		getWebElement(By.id("chkBoxAllAudits")).click();
		getWebElement(By.id("deleteAuditsBtn")).click();
	}

	/**
	 * Verifies if in the pending tab the object(container, cotentlet,etc) bundle is still listed
	 * @param elementTitle name of the containers,content,etc element pushed individually
	 * @param poolInterval - how many milliseconds to wait between polling
	 * @param maxPoolCount - maximum number of times to poll before returning value of eval.evaluate()
	 * @return true if the bundle was pushed, false if is still pending
	 * @throws Exception
	 */
	public boolean isObjectBundlePushed(String elementTitle,long poolInterval,int maxPoolCount) throws Exception{
		bundle = elementTitle;
		Evaluator eval = new Evaluator() {
			public boolean evaluate() throws Exception {  // returns true if host copy is done
				return !isObjectBundlePending(bundle);
			}
		};
		return pollForValue(eval, true, poolInterval, maxPoolCount);
	}

	/**
	 * Verifies if the object bundle is pending for push
	 * @param elementTitle name of the containers,content,etc element pushed individually
	 * @return true if the bundle was pushed, false if is still pending
	 * @throws Exception
	 */
	public boolean isObjectBundlePending(String elementTitle) throws Exception{
		boolean isPending = false;
		getPendingBundlesTab();
		List<WebElement> bundles = getWebElement(By.id("queueContent")).findElements(By.tagName("table"));
		for(WebElement bundle: bundles){ 
			List<WebElement> rows = bundle.findElements(By.tagName("tr"));
			for(WebElement row : rows){
				List<WebElement> columns = row.findElements(By.tagName("td"));
				if(columns.size() > 1){
					if(columns.get(1).getText().contains(elementTitle)){
						isPending=true;
						break;
					}
				}
			}
		}
		return isPending;
	}

	/**
	 * Download a bundle file
	 * @param bundleName Name of the bundle
	 * @param forPublish Indicate if the download is for publish or unpublish
	 * @throws Exception
	 */
	public void downloadBundle(String bundleName, boolean forPublish) throws Exception{
		WebElement bundle = findBundle(bundleName);
		String bundleId = null;
		List<WebElement> ths = bundle.findElements(By.tagName("th"));
		bundleId = ths.get(0).getAttribute("onclick");
		bundleId=bundleId.substring(bundleId.indexOf("'")+1, bundleId.lastIndexOf("'"));
		List<WebElement> buttons = ths.get(1).findElements(By.cssSelector("span[class='dijitReset dijitInline dijitButtonText']"));
		String localizedString = "";
		if(forPublish){
			localizedString=getLocalizedString("download-for-Publish");
		}else{
			localizedString=getLocalizedString("download-for-UnPublish");
		}

		for(WebElement button : buttons){
			if(button.getText().equals(localizedString)){
				button.click();
				break;
			}
		}		
	}

	/**
	 * Upload a bundle file
	 * @param filePath Name and path of the bundle
	 * @throws Exception
	 */
	public void uploadBundle(String filePath) throws Exception{
		List<WebElement> buttons = getWebElement(By.cssSelector("div[class='yui-g portlet-toolbar']")).findElements(By.cssSelector("span[class='dijitReset dijitInline dijitButtonText']"));
		for(WebElement button : buttons){
			if(button.getText().trim().equals(getLocalizedString("publisher_upload"))){
				button.click();
				sleep(2);
				break;
			}
		}
		WebElement uploadDiv = getWebElement(By.id("uploadBundleDiv"));
		String path = System.getProperty("user.dir");
		File folder = new File(path+filePath);
		File[] files =folder.listFiles(new FilenameFilter() {			
			@Override
			public boolean accept(File dir, String name) {
				return name.toLowerCase().endsWith(".tar.gz");
			}
		});
		File file =files[0];
		if(getBrowserName().equals(WebKeys.SAFARI_BROWSER_NAME)){
			WebElement elem = uploadDiv.findElement(By.xpath("//input[@type='file']"));
			elem.sendKeys(file.getAbsolutePath());
		}else{
			uploadDiv.findElement(By.cssSelector("input[type='file'][name='uploadBundleFile']")).sendKeys(file.getAbsolutePath());
		}
		buttons = uploadDiv.findElements(By.cssSelector("span[class='dijitReset dijitInline dijitButtonText']"));
		for(WebElement button : buttons){
			if(button.getText().trim().equals(getLocalizedString("publisher_upload"))){
				button.click();
				break;
			}
		}
		file.delete();
	}
	
	/**
	 * Delete and existing bundle
	 * @param bundleName  Bundle name
	 * @throws Exception
	 */
	public void deleteBundle(String bundleName) throws Exception{
		WebElement bundle = findBundle(bundleName);
		String bundleId = null;
		List<WebElement> ths = bundle.findElements(By.tagName("th"));
		bundleId = ths.get(0).getAttribute("onclick");
		bundleId=bundleId.substring(bundleId.indexOf("'")+1, bundleId.lastIndexOf("'"));
		List<WebElement> buttons = ths.get(1).findElements(By.cssSelector("span[class='dijitReset dijitInline dijitButtonText']"));
		String localizedString =getLocalizedString("delete");
		
		for(WebElement button : buttons){
			if(button.getText().equals(localizedString)){
				button.click();
				switchToAlert().accept();
				break;
			}
		}
	}
	
	/**
	 * Validate if a bundle exist
	 * @param bundleName Bundle name
	 * @return true if exist, false if not
	 * @throws Exception
	 */
	public boolean doesBundleExist(String bundleName) throws Exception{
		boolean exist=false;
		try{
			WebElement bundle = findBundle(bundleName);
			if(bundle != null){
				exist=true;
			}
		}catch(Exception e){}
		return exist;
	}

}
