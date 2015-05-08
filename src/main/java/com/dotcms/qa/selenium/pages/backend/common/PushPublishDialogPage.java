package com.dotcms.qa.selenium.pages.backend.common;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.dotcms.qa.selenium.pages.backend.IPushPublishDialogPage;
import com.dotcms.qa.selenium.pages.common.BasePage;

/**
 * This class implements the methods defined in the IPushPublishDialogPage interface
 * @author Oswaldo Gallango
 * @since 05/08/2015
 * @version 1.0
 * 
 */
public class PushPublishDialogPage extends BasePage implements IPushPublishDialogPage{

	private WebElement remotePublisherDia;
	
	public PushPublishDialogPage(WebDriver driver) {
		super(driver);
	}

	/**
	 * Push the specified contentlet,containers,structure,category,template,htmlpage,etc
	 * @param pushType   Type of push to apply (PUSH_TO_REMOVE,PUSH_TO_ADD,PUSH_AND_REMOVE)
	 * @param pushDate   Date with format M/d/yyyy(optional)
	 * @param pushTime   Time format H:mm a (optional)
	 * @param expireDate Date with format M/d/yyyy(optional)
	 * @param expireTime Time format H:mm a (optional)
	 * @param force      Force to publish (Only valid for push and push a remove)
	 * @throws Exception
	 */
	public void push(String pushType, String pushDate, String pushTime, String expireDate, String expireTime, boolean force) throws Exception{
		List<WebElement> pushOptions = remotePublisherDia.findElements(By.cssSelector("input[type='radio']"));
		for(WebElement option:pushOptions){
			if(option.getAttribute("value").trim().equals(pushType)){
				option.click();
				break;
			}
		}
		sleep(2);

		if(pushDate != null && !pushDate.equals("")){
			remotePublisherDia.findElement(By.id("wfPublishDateAux")).clear();
			remotePublisherDia.findElement(By.id("wfPublishDateAux")).sendKeys(pushDate);
		}
		if(pushTime != null && !pushTime.equals("")){
			remotePublisherDia.findElement(By.id("wfPublishTimeAux")).clear();
			remotePublisherDia.findElement(By.id("wfPublishTimeAux")).sendKeys(pushTime);
		}
		if(force){
			remotePublisherDia.findElement(By.id("forcePush")).click();
		}
		if(expireDate != null && !expireDate.equals("")){
			remotePublisherDia.findElement(By.id("wfExpireDateAux")).clear();
			remotePublisherDia.findElement(By.id("wfExpireDateAux")).sendKeys(expireDate);
		}
		if(expireTime != null && !expireTime.equals("")){
			remotePublisherDia.findElement(By.id("wfExpireTimeAux")).clear();
			remotePublisherDia.findElement(By.id("wfExpireTimeAux")).sendKeys(expireDate);
		}
		//environmentSelect
		//whereToSendTable

		remotePublisherDia.findElement(By.id("remotePublishSaveButton")).click();
	}

}
