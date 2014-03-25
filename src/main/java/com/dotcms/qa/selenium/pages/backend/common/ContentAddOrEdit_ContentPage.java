package com.dotcms.qa.selenium.pages.backend.common;

import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

import com.dotcms.qa.util.language.LanguageManager;
import com.dotcms.qa.selenium.pages.backend.*;
import com.dotcms.qa.selenium.pages.backend.ISelectAFileDialog.ViewSelector;
import com.dotcms.qa.selenium.pages.common.BasePage;
import com.dotcms.qa.selenium.util.SeleniumPageManager;

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
		return SeleniumPageManager.getPageManager().getPageObject(ICategoriesDialog.class);
	}
	
	public ISelectAFileDialog getSelectAFileDialog(By linkBy, String folderDetailContentPaneId, String fileDetailContentPaneId) throws Exception {
		WebElement fileBrowseButton = getWebElement(linkBy);
        executeJavaScript("arguments[0].click();", fileBrowseButton);
        ISelectAFileDialog retValue = SeleniumPageManager.getPageManager().getPageObject(ISelectAFileDialog.class);
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
	    getWebElement(By.linkText(LanguageManager.getValue("Save-Publish"))).click();
		return SeleniumPageManager.getPageManager().getPageObject(IContentSearchPage.class);
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
}
