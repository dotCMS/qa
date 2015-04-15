package com.dotcms.qa.selenium.pages.backend.common;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.dotcms.qa.selenium.pages.backend.IMenuLinkAddOrEdit_Page;
import com.dotcms.qa.selenium.pages.backend.IMenuLinkPage;
import com.dotcms.qa.selenium.pages.common.BasePage;
import com.dotcms.qa.selenium.util.SeleniumPageManager;

/**
 * This class implements the methods defined in the IMenuLinkAddOrEdit_Page interface
 * @author Oswaldo Gallango
 * @since 04/14/2015
 * @version 1.0
 * 
 */
public class MenuLinkAddOrEdit_Page extends BasePage implements IMenuLinkAddOrEdit_Page{

	public static final String INTERNAL_LINK ="internal"; 
	public static final String EXTERNAL_LINK ="external"; 
	public static final String CODE_LINK ="code"; 

	public static final String SAME_TARGET="Same Window";
	public static final String NEW_TARGET="New Window";
	public static final String PARENT_TARGET="Parent Window";

	public static final String HTTP_PROTOCOL="http://";
	public static final String HTTPS_PROTOCOL="https://";
	public static final String FTP_TARGET="ftp://";
	public static final String MAILTO_TARGET="mailto:";
	public static final String JAVASCRIPT_TARGET="javascript:";

	public MenuLinkAddOrEdit_Page(WebDriver driver) {
		super(driver);
	}

	/**
	 * Set Menu link title
	 * @param title Title
	 * @throws Exception
	 */
	public void setLinkTitle(String title) throws Exception{
		WebElement titleField = getWebElement(By.id("titleField"));
		titleField.clear();
		titleField.sendKeys(title);
	}

	/**
	 * Get Menu Link Title
	 */
	public String getLinkTitle() throws Exception{
		WebElement title = getWebElement(By.id("titleField"));
		return title.getAttribute("value");
	}

	/**
	 * Set Menu Link folder
	 * @param folderName
	 * @throws Exception
	 */
	public void setLinkFolder(String folderName) throws Exception{
		WebElement folderSearch = getWebElement(By.id("folder-hostFolderSelect"));
		folderSearch.clear();
		folderSearch.sendKeys(folderName);
		List<WebElement> expandFolders = getWebElement(By.id("folder-hostFoldersTreeWrapper")).findElements(By.cssSelector("img[class='dijitTreeExpando dijitTreeExpandoClosed']"));
		for(WebElement plus : expandFolders){
			plus.click();
		}
		List<WebElement> folders = getWebElement(By.id("folder-hostFoldersTreeWrapper")).findElements(By.cssSelector("span[class='dijitTreeLabel']")); 
		for(WebElement folder : folders){
			if(folder.getText().trim().equals(folderName)){
				folder.click();
				break;
			}
		}
	}

	/**
	 * Get Menu Link Folder
	 * @return String
	 * @throws Exception
	 */
	public String getLinkFolder() throws Exception{
		WebElement folder = getWebElement(By.id("folder-hostFolderSelect"));
		return folder.getAttribute("value");
	}

	/**
	 * Set Menu Link type: internal,external or code link
	 * @param type (accepted values: internal,external or code)
	 * @throws Exception
	 */
	public void  setLinkType(String type) throws Exception{
		if(type.equals(INTERNAL_LINK)){
			WebElement internalLink = getWebElement(By.id("internalLinkType"));
			internalLink.click();
		}else if(type.equals(EXTERNAL_LINK)){
			WebElement externalLink = getWebElement(By.id("externalLinkType"));
			externalLink.click();
		}else{
			WebElement codeLink = getWebElement(By.id("codeLinkType"));
			codeLink.click();
		}
	}

	/**
	 * Get Menu Link Type
	 * @return String
	 * @throws Exception
	 */
	public String getLinkType() throws Exception{
		WebElement internalLink = getWebElement(By.id("internalLinkType"));
		if(internalLink.isSelected()){
			return INTERNAL_LINK;
		}
		WebElement externalLink = getWebElement(By.id("externalLinkType"));
		if(externalLink.isSelected()){
			return EXTERNAL_LINK;
		}
		return CODE_LINK;
	}

	/**
	 * Set Menu Link order
	 * @param position
	 * @throws Exception
	 */
	public void  setLinkOrder(int position) throws Exception{
		WebElement order = getWebElement(By.id("sortOrder"));
		order.clear();
		order.sendKeys(String.valueOf(position));
	}

	/**
	 * Get Menu Link order
	 * @return String
	 * @throws Exception
	 */
	public String getLinkOrder() throws Exception{
		WebElement order = getWebElement(By.id("sortOrder"));
		return order.getAttribute("value");
	}

	/**
	 * Set Menu Link show on menu
	 * @param position
	 * @throws Exception
	 */
	public void  setLinkShowOnMenu(boolean showOnMenu) throws Exception{
		WebElement isShowOnMenu = getWebElement(By.id("showOnMenu"));
		if(showOnMenu){
			if(!isShowOnMenu.isSelected()){
				isShowOnMenu.click();
			}
		}else{
			if(isShowOnMenu.isSelected()){
				isShowOnMenu.click();
			}
		} 
	}


	/**
	 * Get if Menu Link is show on menu
	 * @return String
	 * @throws Exception
	 */
	public boolean isLinkShowOnMenu() throws Exception{
		WebElement isShowOnMenu = getWebElement(By.id("showOnMenu"));
		return isShowOnMenu.isSelected();
	}

	/**
	 * Set Menu Link target: same, new or parent window
	 * @param type (accepted values: same, new or parent)
	 * @throws Exception
	 */
	public void  setLinkTarget(String type) throws Exception{
		getWebElement(By.id("widget_target")).findElement(By.cssSelector("input[type='text'][class='dijitReset dijitInputField dijitArrowButtonInner']")).click();
		List<WebElement> targetList = getWebElement(By.id("target_popup")).findElements(By.cssSelector("div[ class='dijitReset dijitMenuItem']"));
		for(WebElement target : targetList){
			if(target.getText().trim().equals(type)){
				target.click();
				break;
			}
		}		
	}

	/**
	 * Get Menu Link target
	 * @return String
	 * @throws Exception
	 */
	public String getLinkTarget() throws Exception{
		WebElement target = getWebElement(By.cssSelector("input[id='target']"));
		return target.getAttribute("value");
	}

	/**
	 * Set the link if the menu link type is a code link
	 * @param code
	 * @throws Exception
	 */
	public void setLinkCode(String code) throws Exception{
		WebElement codeField = getWebElement(By.id("linkCode"));
		codeField.clear();
		codeField.sendKeys(code);
	}

	/**
	 * Get the link if the link type is code link
	 * @return String
	 * @throws Exception
	 */
	public String getLinkCode() throws Exception{
		WebElement code = getWebElement(By.id("linkCode"));
		return code.getAttribute("value");
	}

	/**
	 * Set the link if the menu link type is external link
	 * @param protocol valids protocol http://, https://, ftp://, mailto: or javascript:
	 * @param url  Url path
	 * @throws Exception
	 */
	public void setLinkExternalCode(String protocol, String url) throws Exception{
		WebElement protocolField = getWebElement(By.id("protocal"));
		protocolField.clear();
		protocolField.sendKeys(protocol);
		protocolField.sendKeys(Keys.TAB);

		WebElement urlField = getWebElement(By.id("url"));
		urlField.clear();
		urlField.sendKeys(url);
	}

	/**
	 * Get the link  if the link type is code link
	 * @return String
	 * @throws Exception
	 */
	public String getLinkExternalCode() throws Exception{
		WebElement protocolField = getWebElement(By.id("protocal"));
		WebElement urlField = getWebElement(By.id("url"));
		return protocolField.getAttribute("value")+urlField.getAttribute("value");
	}

	/**
	 * Set the link if the menu link type is internal link
	 * @param host  Host name
	 * @param folder Folder name
	 * @param url Url path
	 * @throws Exception
	 */
	public void setLinkInternalCode(String host, String folder, String url) throws Exception{
		WebElement browseButton = getWebElement(By.id("dotcms_dijit_form_FileSelector_0")).findElement(By.cssSelector("span[class='dijitReset dijitStretch dijitButtonContents']"));
		browseButton.click();

		WebElement leftPanel = getWebElement(By.id("dijit_layout_ContentPane_2"));
		List<WebElement> hosts = leftPanel.findElements(By.cssSelector("span[class='dijitTreeLabel']"));
		for(WebElement hostE : hosts){
			if(hostE.getText().trim().equals(host)){
				WebElement parent = getParent(getParent(hostE));
				sleep(2);
				parent.findElement(By.cssSelector("img[class='dijitTreeExpando dijitTreeExpandoClosed']")).click();
				sleep(2);
				boolean found=false;
				WebElement folderDiv = getParent(parent);
				sleep(2);
				List<WebElement> folders = folderDiv.findElement(By.cssSelector("div[class='dijitTreeRow']")).findElements(By.cssSelector("span[class='dijitTreeLabel']"));
				for(WebElement folderP : folders){
					if(folderP.getText().trim().equals(folder)){
						found=true;
						folderP.click();
						break;
					}
				}
				if(!found){
					List<WebElement> expandFolders = folderDiv.findElement(By.cssSelector("div[class='dijitTreeRow']")).findElements(By.cssSelector("img[class='dijitTreeExpando dijitTreeExpandoClosed']"));
					for(WebElement plus : expandFolders){
						plus.click();
					}
					folders = folderDiv.findElement(By.cssSelector("div[class='dijitTreeRow']")).findElements(By.cssSelector("span[class='dijitTreeLabel']"));
					for(WebElement folderP : folders){
						if(folderP.getText().trim().equals(folder)){
							folderP.click();
							break;
						}
					}
				}
				sleep(2);
				WebElement rightPanel = getWebElement(By.id("dijit_layout_ContentPane_3"));
				List<WebElement> results = rightPanel.findElements(By.cssSelector("a[class='selectableFile']"));
				for(WebElement page : results){
					if(page.getText().trim().equals(url)){
						page.click();
						sleep(2);
						break;
					}
				}
				break;
			}
		}
	}

	/**
	 * Get the link  if the link type is internal link
	 * @return String
	 * @throws Exception
	 */
	public String getLinkInternalCode() throws Exception{
		WebElement internalfieldField = getWebElement(By.name("internalLinkIdentifier-filename"));
		return internalfieldField.getAttribute("value");
	}

	/**
	 * Click the save and publish button
	 * @retun IMenuLinkPage
	 */
	public IMenuLinkPage saveAndPublish() throws Exception{
		List<WebElement> buttons = getWebElement(By.id("editLinkButtonRow")).findElements(By.cssSelector("span[class='dijitReset dijitInline dijitButtonText']"));
		for(WebElement button : buttons){
			if(button.getText().equals(getLocalizedString("save-and-publish"))){
				button.click();
				break;
			}
		}
		return SeleniumPageManager.getBackEndPageManager().getPageObject(IMenuLinkPage.class);
	}

	/**
	 * Click the save button
	 * @return IMenuLinkPage
	 */
	public IMenuLinkPage save() throws Exception{
		List<WebElement> buttons = getWebElement(By.id("editLinkButtonRow")).findElements(By.cssSelector("span[class='dijitReset dijitInline dijitButtonText']"));
		for(WebElement button : buttons){
			if(button.getText().equals(getLocalizedString("Save"))){
				button.click();
				break;
			}
		}
		return SeleniumPageManager.getBackEndPageManager().getPageObject(IMenuLinkPage.class);
	}

	/**
	 * Click the cancel button
	 * @return IMenuLinkPage
	 */
	public IMenuLinkPage cancel() throws Exception{
		List<WebElement> buttons = getWebElement(By.id("editLinkButtonRow")).findElements(By.cssSelector("span[class='dijitReset dijitInline dijitButtonText']"));
		for(WebElement button : buttons){
			if(button.getText().equals(getLocalizedString("Cancel"))){
				button.click();
				break;
			}
		}
		return SeleniumPageManager.getBackEndPageManager().getPageObject(IMenuLinkPage.class);
	}	
}
