package com.dotcms.qa.selenium.pages.backend.common;

import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.dotcms.qa.selenium.pages.backend.ISelectAFileDialog;
import com.dotcms.qa.selenium.pages.common.BasePage;

public class SelectAFileDialog extends BasePage implements ISelectAFileDialog {
    private static final Logger logger = Logger.getLogger(SelectAFileDialog.class);

    public SelectAFileDialog(WebDriver driver){
		super(driver);
	}
	
	public void close() throws Exception{
		// TODO implement functionality
	}
	
	public void selectFile(String fullyqualifiedFilename) {

		// File Browse screen
        WebElement hostTreeNode = null;
        WebElement divTreeNodeContainer = getWebElement(By.className("dijitTreeNodeContainer"));
        // Find and expand hostTreeNode
        List<WebElement> nodeDivs = divTreeNodeContainer.findElements(By.tagName("div"));
        for(WebElement divNode : nodeDivs) {
        	String divNodeId = divNode.getAttribute("id");
        	if(!(divNodeId == null) & !divNodeId.trim().isEmpty()) {
            	logger.debug("divNodeId = " + divNodeId);
            	WebElement div = divNode.findElement(By.tagName("div"));
            	List<WebElement> spans = div.findElements(By.tagName("span"));
            	if(spans.size() >=2) {
            		WebElement hostNameSpan = spans.get(1);
            		logger.debug("hostNameSpan.getText() = " + hostNameSpan.getText());
            		WebElement img = div.findElement(By.tagName("img"));
            		logger.debug("data-dojo-attach-point=" + img.getAttribute("data-dojo-attach-point"));
            		if("qademo.dotcms.com".equals(hostNameSpan.getText())) {
            			hostTreeNode = divNode;
            			img.click();
            			break;
            		}
            	}
        	}
        }
        // qademo.dotcms.com node is expanded now expand next directory down
        WebElement folderNode = null;
        logger.debug("hostTreeNode.id = " + hostTreeNode.getAttribute("id"));
        WebElement subFolderContainerDiv = hostTreeNode.findElement(By.className("dijitTreeNodeContainer"));
        for(WebElement divNode : subFolderContainerDiv.findElements(By.tagName("div"))) {
        	String divNodeId = divNode.getAttribute("id");
        	if(!(divNodeId == null) & !divNodeId.trim().isEmpty()) {
	        	logger.debug("divNodeId = " + divNodeId);
	        	WebElement div = divNode.findElement(By.tagName("div"));
	        	List<WebElement> spans = div.findElements(By.tagName("span"));
	        	if(spans.size() >=2) {
	        		WebElement folderSpan = spans.get(1);
	        		logger.debug("folderSpan.getText() = " + folderSpan.getText());
            		WebElement img = div.findElement(By.tagName("img"));
            		logger.debug("data-dojo-attach-point=" + img.getAttribute("data-dojo-attach-point"));
	        		if("intranet".equals(folderSpan.getText())) {
	        			folderNode = divNode;
            			img.click();
            			break;
	        		}
	        	}
        	}
        }
        
        // folderNode is expanded now select next directory down
        WebElement subFolderNode = null;
        subFolderContainerDiv = folderNode.findElement(By.className("dijitTreeNodeContainer"));
        for(WebElement divNode : subFolderContainerDiv.findElements(By.tagName("div"))) {
        	String divNodeId = divNode.getAttribute("id");
        	if(!(divNodeId == null) & !divNodeId.trim().isEmpty()) {
	        	logger.info("divNodeId = " + divNodeId);
	        	WebElement div = divNode.findElement(By.tagName("div"));
	        	List<WebElement> spans = div.findElements(By.tagName("span"));
	        	if(spans.size() >=2) {
	        		WebElement folderSpan = spans.get(1);
	        		logger.info("folderSpan.getText() = " + folderSpan.getText());
            		WebElement img = div.findElement(By.tagName("img"));
            		logger.info("data-dojo-attach-point=" + img.getAttribute("data-dojo-attach-point"));
	        		if("documents".equals(folderSpan.getText())) {
	        			subFolderNode = divNode;
	        			subFolderNode.click();
	        			//img.click();
            			break;
	        		}
	        	}
        	}
        }
        
        // content page
        WebElement contentPane = getWebElement(By.id("dijit_layout_ContentPane_7"));
        WebElement listingTable = contentPane.findElement(By.className("listingTable"));
        WebElement tableBody = listingTable.findElement(By.tagName("tbody"));
        WebElement file = tableBody.findElement(By.linkText("global-central-banks.pdf"));
        file.click();
	
	}
}
