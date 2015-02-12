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

    private String folderDetailContentPaneId = null;
    private String fileDetailContentPaneId = null;
    private ViewSelector view = null;
 
    public SelectAFileDialog(WebDriver driver){
		super(driver);
	}
	
    public void setContentPaneIds(String folderDetailContentPaneId, String fileDetailContentPaneId) {
    	this.folderDetailContentPaneId = folderDetailContentPaneId;
    	this.fileDetailContentPaneId = fileDetailContentPaneId;
    }
    
    public void setView(ViewSelector view) {
    	this.view = view;
    	switch(this.view) {
    		case LIST_VIEW:
    			return; // TODO - implement - rest of class only works with detail view
    		case DETAIL_VIEW:
    			this.getWebElement(By.cssSelector("#" + this.fileDetailContentPaneId + " > div > div.viewSelectorBox > img[alt=\"details view\"]")).click();
    			return;
    		case THUMBNAIL_VIEW:
    			return; // TODO - implement - rest of class only works with detail view
    	}
    }
    
	public void close() throws Exception{
		// TODO implement functionality
	}
	
	public void expandNode(WebElement node) {
    	WebElement div = node.findElement(By.tagName("div"));
    	List<WebElement> spans = div.findElements(By.tagName("span"));
    	if(spans.size() >=2) {
    		WebElement span = spans.get(1);
    		logger.info("span.getText() = " + span.getText());
    		WebElement img = div.findElement(By.tagName("img"));
    		logger.info("data-dojo-attach-point=" + img.getAttribute("data-dojo-attach-point"));
    		img.click();
    	}
	}
	
	public WebElement getChildNode(WebElement parentNode, String childNodeText) {
        WebElement retValue = null;
        WebElement startingNode = null;
        if(parentNode != null)
        	startingNode = parentNode.findElement(By.className("dijitTreeNodeContainer"));
    	else
        	startingNode = getWebElement(By.id(this.folderDetailContentPaneId)).findElement(By.className("dijitTreeNodeContainer"));
        List<WebElement> nodeDivs = startingNode.findElements(By.tagName("div"));
        for(WebElement divNode : nodeDivs) {
        	String divNodeId = divNode.getAttribute("id");
        	if(!(divNodeId == null) & !divNodeId.trim().isEmpty()) {
            	logger.info("divNodeId = " + divNodeId);
            	WebElement div = divNode.findElement(By.tagName("div"));
            	List<WebElement> spans = div.findElements(By.tagName("span"));
            	if(spans.size() >=2) {
            		WebElement span = spans.get(1);
            		logger.info("span.getText() = " + span.getText());
            		if(childNodeText.equals(span.getText())) {
            			retValue = divNode;
            			break;
            		}
            	}
        	}
        }
        return retValue;
	}

	public void selectFile(String fullyqualifiedFilename) throws IllegalArgumentException {
		String[] fileSegments = fullyqualifiedFilename.split("/");
		if(fileSegments.length < 2)
			throw new IllegalArgumentException("Must have a minimum of two segments - hostname and filename");
		
		WebElement currentTreeNode = null;
		for(int i=0; i<fileSegments.length -2; i++) {
			currentTreeNode = getChildNode(currentTreeNode, fileSegments[i]);
			logger.info("currentTreeNode = " + currentTreeNode + "| fileSegments[i] = " + fileSegments[i]);
			expandNode(currentTreeNode);
		}
		currentTreeNode = getChildNode(currentTreeNode, fileSegments[fileSegments.length -2]);
		currentTreeNode.click();
		
		// content page
        WebElement contentPane = null;
        try{
        	contentPane = getWebElement(By.id(this.fileDetailContentPaneId));
        }
        catch(Exception e) {
        	throw new IllegalArgumentException("Unable to find web element for specified detailContentPaneId: " + this.fileDetailContentPaneId, e);
        }
        WebElement listingTable = contentPane.findElement(By.className("listingTable"));
        WebElement tableBody = listingTable.findElement(By.tagName("tbody"));
        WebElement file = tableBody.findElement(By.linkText(fileSegments[fileSegments.length - 1]));
        file.click();
	}
}
