package com.dotcms.qa.selenium.pages.backend.common;

import java.io.File;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

import com.dotcms.qa.selenium.pages.backend.*;
import com.dotcms.qa.selenium.pages.backend.ISelectAFileDialog.ViewSelector;
import com.dotcms.qa.selenium.pages.common.BasePage;
import com.dotcms.qa.selenium.util.SeleniumPageManager;
import com.dotcms.qa.util.Evaluator;
import com.dotcms.qa.util.WebKeys;

public class ContentAddOrEdit_ContentPage extends BasePage implements IContentAddOrEdit_ContentPage {
	private static final Logger logger = Logger.getLogger(ContentAddOrEdit_ContentPage.class);

	@FindBy(how = How.CSS, using = "span.mceIcon.mce_bold")
	@CacheLookup
	private WebElement boldButton;

	@FindBy(how = How.CSS, using = "span.mceIcon.mce_italic")
	@CacheLookup
	private WebElement italicButton;

	@FindBy(how = How.CSS, using = "span.mceIcon.mce_underline")
	@CacheLookup
	private WebElement underlineButton;

	private WebElement tinymce;

	public ContentAddOrEdit_ContentPage(WebDriver driver) {
		super(driver);
	}

	public ICategoriesDialog getCategoriesDialog(By linkBy) throws Exception{
		WebElement elem = getWebElement(linkBy);
		elem.click();		
		return SeleniumPageManager.getBackEndPageManager().getPageObject(ICategoriesDialog.class);
	}

	public ISelectAFileDialog getSelectAFileDialog(By linkBy, String folderDetailContentPaneId, String fileDetailContentPaneId) throws Exception {
		WebElement fileBrowseButton = getWebElement(linkBy);
		executeJavaScript("arguments[0].click();", fileBrowseButton);
		ISelectAFileDialog retValue = SeleniumPageManager.getBackEndPageManager().getPageObject(ISelectAFileDialog.class);
		retValue.setContentPaneIds(folderDetailContentPaneId, fileDetailContentPaneId);
		retValue.setView(ViewSelector.DETAIL_VIEW);
		return retValue;
	}

	public void setHostOrFolder(String hostNameOrFolderPath) throws IllegalArgumentException {
		if(hostNameOrFolderPath == null || hostNameOrFolderPath.trim().isEmpty())
			throw new IllegalArgumentException("hostNameOrFolderPath is null or empty:  hostNameOrFolderPath = " + hostNameOrFolderPath);
		WebElement hostSelector = getWebElement(By.id("HostSelector"));
		WebElement comboBox = hostSelector.findElement(By.className("dijitComboBox"));
		WebElement hostFolderSelect = comboBox.findElement(By.id("HostSelector-hostFolderSelect"));
		WebElement downArrow = comboBox.findElement(By.className("dijitDownArrowButton"));
		WebElement inputContainer = comboBox.findElement(By.className("dijitInputContainer"));

		String[] segments = hostNameOrFolderPath.split("/");
		downArrow.click();

		WebElement currentTreeNode = null;
		for(int i=0; i<segments.length -1; i++) {
			currentTreeNode = getChildNode(currentTreeNode, segments[i]);
			logger.info("currentTreeNode = " + currentTreeNode + "| segments[i] = " + segments[i]);
			expandNode(currentTreeNode);
		}
		currentTreeNode = getChildNode(currentTreeNode, segments[segments.length -1]);
		currentTreeNode.click();
	}

	public void toggleWYSIWYGBold() {
		boldButton.click();
	}

	public void toggleWYSIWYGItalic() {
		italicButton.click();		
	}

	public void toggleWYSIWYGUnderline() {
		underlineButton.click();
		//this.getWebElement(By.cssSelector("span.mceIcon.mce_underline")).click();
	}

	public void addWYSIWYGText(String textToAdd) {
		switchToFrame("wysiwygfield_ifr");
		tinymce.sendKeys(textToAdd);
		switchToDefaultContent();
	}

	public void addKeyValuePair(By by) {
		getWebElement(by).click();
	}

	public IContentSearchPage saveAndPublish() throws Exception {
		getWebElement(By.linkText(getLocalizedString("Save-Publish"))).click();
		return SeleniumPageManager.getBackEndPageManager().getPageObject(IContentSearchPage.class);
	}

	private WebElement getChildNode(WebElement parentNode, String childNodeText) {
		WebElement retValue = null;
		WebElement startingNode = null;
		if(parentNode != null) {
			startingNode = parentNode.findElement(By.className("dijitTreeNodeContainer"));
		}
		else {
			WebElement treeNodeRoot = getWebElement(By.id("HostSelector-tree-treeNode-root"));
			startingNode = treeNodeRoot.findElement(By.className("dijitTreeNodeContainer"));
		}

		List<WebElement> nodeDivs = startingNode.findElements(By.className("dijitTreeNode"));
		logger.info("nodeDivs.size()" + nodeDivs.size());
		for(WebElement nodeDiv : nodeDivs) {
			logger.info("nodeDivs.class = " + nodeDiv.getAttribute("class"));
			WebElement nodeLabel = nodeDiv.findElement(By.className("dijitTreeLabel"));
			logger.info("nodeLabel.tagName = " + nodeLabel.getTagName());
			logger.info("nodeLabel.getText() = " + nodeLabel.getText());
			logger.info("nodeLabel.role = " + nodeLabel.getAttribute("role"));
			logger.info("nodeLabel = " + nodeLabel.toString());
			logger.info("nodeLabel.isDisplayed() = " + nodeLabel.isDisplayed());
			logger.info("nodeLabel.isEnabled() = " + nodeLabel.isEnabled());
			if(childNodeText.equals(nodeLabel.getText())) {
				retValue = nodeDiv;
				break;
			}
		}
		return retValue;
	}

	private void expandNode(WebElement node) {
		WebElement expandNode = node.findElement(By.tagName("img"));
		expandNode.click();
	}

	/**
	 * Modify the current content language
	 * @param language language name
	 * @param keepPreviousContent keep original text  in new language content
	 * @throws Exception
	 */
	public void changeContentLanguage(String language, boolean keepPreviousContent) throws Exception{
		if(language != null && !language.equals("")){
			WebElement lang = getWebElement(By.id("langcombo"));
			lang.clear();
			lang.sendKeys(language);
			sleep(3);
			lang.sendKeys(Keys.RETURN);
			try{
				switchToAlert().accept();
			}catch(Exception e){
				logger.error(e.getMessage());
			}
			switchToPopup();
			String compareText  = (keepPreviousContent?getLocalizedString("Yes"):getLocalizedString("No"));
			List<WebElement> buttons = getWebElements(By.cssSelector("span[class='dijitReset dijitInline dijitButtonText']"));
			for(WebElement elem : buttons){
				if(elem.getText().equals(compareText)){
					elem.click();
					break;
				}
			}
			switchToDefaultContent();
		}
	}

	/**
	 * Set Content text, textarea and wysiwyg Fields
	 * @param map List Map with contents fields
	 * @throws Exception
	 */
	public void setFields(List<Map<String, Object>> map) throws Exception{
		for(Map<String, Object> content : map){
			try{
				String type = (String) content.get("type");
				WebElement tab = getWebElement(By.id("properties"));
				content.remove("type");
				if(type.equals(WebKeys.WYSIWYG_FIELD)){
					for(String key : content.keySet()){
						//For WYSIWYG fields
						this.switchToFrame(key+"_ifr");
						WebElement elem = getWebElement(By.id("tinymce"));
						elem.click();
						elem.clear();
						elem.sendKeys((String)content.get(key));
						this.switchToDefaultContent();
					}
				}else if(type.equals(WebKeys.HOST_FIELD)){
					for(String key : content.keySet()){
						WebElement elem = tab.findElement(By.id(key));
						elem.clear();
						elem.sendKeys((String)content.get(key));
						elem.sendKeys(Keys.TAB);
						WebElement selectBox = getWebElement(By.id("HostSelector-hostFoldersTreeWrapper"));
						List<WebElement> options = selectBox.findElements(By.cssSelector("span[class='dijitTreeLabel']"));
						for(WebElement option : options){
							if(option.getAttribute("innerHTML").equals((String)content.get(key))){
								option.click();
								sleep(2);
								break;
							}
						}
					}
				}else if(type.equals(WebKeys.BINARY_FIELD)){
					for(String key : content.keySet()){
						String path = System.getProperty("user.dir");
						File file = new File(path+(String)content.get(key));
						if(getBrowserName().equals(WebKeys.SAFARI_BROWSER_NAME)){
							WebElement elem = getWebElement(By.xpath("//input[@type='file']"));
							elem.sendKeys(file.getAbsolutePath());
						}else{
							getWebElement(By.cssSelector("input[type='file'][name='"+key+"']")).sendKeys(file.getAbsolutePath());
						}	
						//waiting image to be loaded
						Evaluator eval = new Evaluator() {
							public boolean evaluate() throws Exception {  
								//validate if the thumbnail image is loaded
								boolean found=false;
								try{
									WebElement thumbnail = getWebElement(By.id("thumbnailParentfileAsset"));
									if(thumbnail != null){
										found=true;
									}
								}catch(Exception e){
									//element is not loaded
								}
								return  found;
							}
						};
						pollForValue(eval, true, 5000,60);

					}

				}else {
					for(String key : content.keySet()){
						WebElement elem = tab.findElement(By.id(key));
						elem.clear();
						elem.sendKeys((String)content.get(key));
						elem.sendKeys(Keys.TAB);
					}
				}

			}catch(Exception e){
				logger.error("ERROR - Setting field value. Detail: " + e.getMessage());
			}
		}
	}

	/**
	 * This method validate if the add content didn't find the structure name.
	 * Displaying the select structure dialog
	 * @return true if the box is displayed, false if not
	 * @throws Exception
	 */
	public boolean isStructureBoxDisplayed() throws Exception{
		boolean isShown = false;
		try {
			WebElement selectStructureBox = getWebElement(By.id("selectStructureDiv"));
			if(selectStructureBox.getAttribute("id") != null){
				isShown = true;
			}
		}catch(Exception e){
			logger.debug("ERROR - Getting field. Detail: " + e.getMessage());
		}
		return  isShown;
	}
	/**
	 * Set the structure name in the select structure dialog 
	 */
	public void setStructure(String structureName) throws Exception{
		WebElement selectStructureBox = getWebElement(By.id("selectStructureDiv"));
		WebElement input = selectStructureBox.findElement(By.id("selectedStructAux"));
		input.sendKeys(structureName);
		sleep(2);
		input.sendKeys(Keys.TAB);
		sleep(1);
		input.sendKeys(Keys.RETURN);
		this.switchToAlert().accept();
	}

	/**
	 * Check if the lock for editing button is present
	 * @return true if the content is locked, false if not
	 * @throws Exception
	 */
	public boolean isPresentContentLockButton() throws Exception{
		boolean isShown = false;

		try{
			WebElement lockButton = getWebElement(By.id("lockContentButton"));
			if(lockButton.getAttribute("id") != null){
				isShown = true;
			} 
		}catch(Exception e){
			logger.debug("ERROR - Getting field. Detail: " + e.getMessage());
		}
		return isShown;
	}

	/**
	 * Click the lock for editing button
	 * @throws Exception
	 */
	public void clickLockForEditingButton() throws Exception{
		getWebElement(By.id("lockContentButton")).click();
	}

	/**
	 * Click the release lock button
	 * @throws Exception
	 */
	public void clickReleaseLockButton() throws Exception{
		getWebElement(By.id("unlockContentButton")).click();
	}

	/**
	 * Click save button
	 * @throws Exception
	 */
	public void save() throws Exception{
		getWebElement(By.linkText(getLocalizedString("save"))).click();
	}

	/**
	 * Click cancel button
	 * @throws Exception
	 */
	public void cancel() throws Exception{
		getWebElement(By.linkText(getLocalizedString("cancel"))).click();
	}

	/**
	 * Get Content field value
	 * @param fieldName Field name
	 * @return String
	 * @throws Exception
	 */
	public String getFieldValue(String fieldName) throws Exception{
		String value=null;
		try{
			WebElement element = getWebElement(By.id(fieldName));
			value = element.getText();

			if(value.equals("")){
				value = (String) executeScript("var editor = ace.edit('"+fieldName+"Editor');return editor.getSession().getValue();");
			}
		}catch(Exception e){
			try{
				this.switchToFrame(fieldName+"_ifr");
				WebElement elem = getWebElement(By.id("tinymce"));	
				value = elem.getText();
				switchToDefaultContent();
			}catch(Exception e1){
				value = (String) executeScript("var editor = ace.edit('"+fieldName+"Editor');return editor.getSession().getValue();");
			}
		}
		return value;
	}

	/**
	 * Select workflow action if it is available
	 * @param actionName       Name of the action
	 * @param parameters Parameters required in step (optional)
	 * @throws Exception
	 */
	public void selectWorkflowAction(String actionName, List<Map<String,String>> parameters) throws Exception{
		List<WebElement> actions = getWebElement(By.id("contentletActionsHanger")).findElements(By.tagName("a"));
		for(WebElement action: actions){
			if(action.getText().trim().equals(actionName)){
				action.click();
				if(parameters.size() > 0){
					for(Map<String,String> param : parameters){
						for(String key : param.keySet()){
							if(!key.equals("clickButton")){
								if(key.equals("environmentSelect")){
									WebElement field = getWebElement(By.id("contentletWfDialog")).findElement(By.id(key));
									field.clear();
									field.sendKeys(param.get(key));
									getWebElement(By.id("environmentSelect_popup1")).click();
									List<WebElement> buttons = getWebElement(By.id("contentletWfDialog")).findElements(By.cssSelector("span[class='dijitReset dijitInline dijitButtonText']"));
									for(WebElement button : buttons){
										if(button.getText().trim().equals(getLocalizedString("Add"))){
											button.click();
											break;
										}
									}
								}else{
									sleep(1);
									WebElement field = getWebElement(By.id("contentletWfDialog")).findElement(By.id(key));
									field.clear();
									field.sendKeys(param.get(key));
									sleep(2);
									field.sendKeys(Keys.TAB);
								}
							}else{
								List<WebElement> buttons = getWebElement(By.id("contentletWfDialog")).findElements(By.cssSelector("span[class='dijitReset dijitInline dijitButtonText']"));
								for(WebElement button : buttons){
									if(button.getText().trim().equals(getLocalizedString(param.get(key)))){
										button.click();
										break;
									}
								}
							}
						}
					}
				}
				break;
			}
		}
	}
}
