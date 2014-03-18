package com.dotcms.qa.testng.tests;

import java.net.*;
import java.util.*;

import com.dotcms.qa.selenium.util.SeleniumConfig;
import com.dotcms.qa.selenium.util.SeleniumPageManager;

import com.dotcms.qa.selenium.pages.IBasePage;
import com.dotcms.qa.selenium.pages.backend.*;
import com.dotcms.qa.selenium.pages.backend.common.CategoriesDialog;
import com.dotcms.qa.util.language.LanguageManager;

import org.apache.log4j.Logger;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterGroups;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeGroups;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class StructureTests {
    private static final Logger logger = Logger.getLogger(StructureTests.class);
    
    private SeleniumPageManager backendMgr = null;
    private SeleniumPageManager frontendMgr = null;
    private String demoServerURL = null;
    private String mobileServerURL = null;
    private String sharedServerURL = null;
    private ILoginPage loginPage = null;

    @BeforeGroups (groups = {"Structures"})
    public void init() throws Exception {
        SeleniumConfig config = SeleniumConfig.getConfig();
        demoServerURL = config.getProperty("demoServerURL");
        mobileServerURL = config.getProperty("mobileServerURL");
        sharedServerURL = config.getProperty("sharedServerURL");

        // login
        backendMgr = RegressionSuiteEnv.getBackendPageManager();
        loginPage = backendMgr.getPageObject(ILoginPage.class);
        loginPage.login("admin@dotcms.com", "admin");
        
        // create frontendMgr for verification of frontend functionality
        //frontendMgr = RegressionSuiteEnv.getFrontendPageManager();

    }

    @AfterGroups (groups = {"Structures"})
    public void teardown() throws Exception {
        // logout
        backendMgr.logoutBackend();
    }

    @Test (groups = {"Structures"})
    public void testCase630_AddStructureWithEveryFieldType() throws Exception {
    	IPortletMenu portletMenu = backendMgr.getPageObject(IPortletMenu.class);
        IStructuresPage structuresPage = portletMenu.getStructuresPage();
    	
        /*
        // Create structure and add fields
        IStructureAddOrEdit_PropertiesPage propPage = structuresPage.getAddNewStructurePage();
        IStructureAddOrEdit_FieldsPage addOrEditFieldsPage = propPage.createNewStructure("OneOfEverything", "Structure with every field type", "qashared");
        Assert.assertEquals(structuresPage.getSystemMessage().trim(), LanguageManager.getValue("message.structure.savestructure"));
        addOrEditFieldsPage.addTextField("TextField", false, true, true, true, false);
        Assert.assertEquals(structuresPage.getSystemMessage().trim(), LanguageManager.getValue("message.structure.savefield"));
        addOrEditFieldsPage.addBinaryField("BinaryField", "BinaryField Hint", false);
        Assert.assertEquals(structuresPage.getSystemMessage().trim(), LanguageManager.getValue("message.structure.savefield"));
        addOrEditFieldsPage.addCategory("CategoryField", "Topic", "Category Hint", false, false);
        Assert.assertEquals(structuresPage.getSystemMessage().trim(), LanguageManager.getValue("message.structure.savefield"));
        addOrEditFieldsPage.addCheckbox("CheckboxField", "Checkbox Value", "Checkbox Default Value", "Checkbox Hint", false, false, false);
        Assert.assertEquals(structuresPage.getSystemMessage().trim(), LanguageManager.getValue("message.structure.savefield"));
        addOrEditFieldsPage.addConstantField("ConstantField", "ConstantField Value", "ConstantField Hint");
        Assert.assertEquals(structuresPage.getSystemMessage().trim(), LanguageManager.getValue("message.structure.savefield"));
        addOrEditFieldsPage.addCustomField("CustomField", "CustomField Value", "", "Letters only", "CustomField Default Value", "CustomField Hint", false, false, false, false, false);
        Assert.assertEquals(structuresPage.getSystemMessage().trim(), LanguageManager.getValue("message.structure.savefield"));
        addOrEditFieldsPage.addDateField("DateField", "2020-01-31", "DateField Hint", false, false, false, false);
        Assert.assertEquals(structuresPage.getSystemMessage().trim(), LanguageManager.getValue("message.structure.savefield"));
        addOrEditFieldsPage.addDateAndTimeField("DateAndTimeField", "2020-01-31 12:34:56", "DateAndTimeField Hint", false, false, false, false);
        Assert.assertEquals(structuresPage.getSystemMessage().trim(), LanguageManager.getValue("message.structure.savefield"));
        addOrEditFieldsPage.addFileField("FileField", "FileField Hint", false);
        Assert.assertEquals(structuresPage.getSystemMessage().trim(), LanguageManager.getValue("message.structure.savefield"));
        addOrEditFieldsPage.addHiddenField("HiddenField", "HiddenField Hint");
        Assert.assertEquals(structuresPage.getSystemMessage().trim(), LanguageManager.getValue("message.structure.savefield"));
        addOrEditFieldsPage.addHostOrFolderField("HostOrFolderField", "HostOrFolderField Hint", false, false);
        Assert.assertEquals(structuresPage.getSystemMessage().trim(), LanguageManager.getValue("message.structure.savefield"));
        addOrEditFieldsPage.addImageField("ImageFields", "ImageField Hint", false);
        Assert.assertEquals(structuresPage.getSystemMessage().trim(), LanguageManager.getValue("message.structure.savefield"));
        addOrEditFieldsPage.addLineDividerField("LineDivider");
        Assert.assertEquals(structuresPage.getSystemMessage().trim(), LanguageManager.getValue("message.structure.savefield"));
        addOrEditFieldsPage.addMultiSelectField("MultiSelectField", "MultiSelectField Value", "MultiSelectField Default Value", "MultiSelectField Hint", false, false, false, false);
        Assert.assertEquals(structuresPage.getSystemMessage().trim(), LanguageManager.getValue("message.structure.savefield"));
        addOrEditFieldsPage.addPermissionsField("PermissionsFields");
        Assert.assertEquals(structuresPage.getSystemMessage().trim(), LanguageManager.getValue("message.structure.savefield"));
        addOrEditFieldsPage.addRadioField("RadioField", "RadioField Value", "RadioField Value", "RadioField Hint", false, false, false, false);
        Assert.assertEquals(structuresPage.getSystemMessage().trim(), LanguageManager.getValue("message.structure.savefield"));
        addOrEditFieldsPage.addRelationshipsField("RelationshipsField");
        Assert.assertEquals(structuresPage.getSystemMessage().trim(), LanguageManager.getValue("message.structure.savefield"));
        addOrEditFieldsPage.addSelectField("SelectField", "SelectField Value", "SelectField Default Value", "SelectField Hint", false, false, false, false);
        Assert.assertEquals(structuresPage.getSystemMessage().trim(), LanguageManager.getValue("message.structure.savefield"));
        addOrEditFieldsPage.addTabDividerField("TabDivider");
        Assert.assertEquals(structuresPage.getSystemMessage().trim(), LanguageManager.getValue("message.structure.savefield"));
        addOrEditFieldsPage.addTagField("TagField", "TagField Default Value", "TagField Hint", false, false);
        Assert.assertEquals(structuresPage.getSystemMessage().trim(), LanguageManager.getValue("message.structure.savefield"));
        addOrEditFieldsPage.addTextareaField("TextAreaField", "", "Letters only", "TextAreaField Default Area", "TextAreaField Hint", false, false, false);
        Assert.assertEquals(structuresPage.getSystemMessage().trim(), LanguageManager.getValue("message.structure.savefield"));
        addOrEditFieldsPage.addTimeField("TimeField", "", "TimeField Hint", false, false, false, false);
        Assert.assertEquals(structuresPage.getSystemMessage().trim(), LanguageManager.getValue("message.structure.savefield"));
        addOrEditFieldsPage.addWYSIWYGField("WYSIWYGField", "default text here", "WYSIWYGField Hint", false, true, true);
        Assert.assertEquals(structuresPage.getSystemMessage().trim(), LanguageManager.getValue("message.structure.savefield"));
        addOrEditFieldsPage.addKeyValueField("KeyValueField", "KeyValueField Hint", false, false);
		*/
        
        IContentSearchPage contentSearchPage = portletMenu.getContentSearchPage();
        IContentAddOrEdit_ContentPage contentAddOrEditPage = contentSearchPage.getAddContentPage("OneOfEverything");
        
        contentAddOrEditPage.setTextField(By.id("textfield"), "Text field value");

        // TODO - add properties setting for location of files for uploading
        contentAddOrEditPage.setBinaryFileField(By.name("binary1FileUpload"), "/Users/brent/Documents/gwt.sublime-project");
        
        ICategoriesDialog catsDlg = contentAddOrEditPage.getCategoriesDialog(By.id("link1"));
        catsDlg.addTopLevelCategory("Investment Banking");
        catsDlg.addTopLevelCategory("Wealth Management");
        catsDlg.close();
        
        contentAddOrEditPage.toggleCheckbox(By.id("text30Checkbox"));

        Calendar cal = Calendar.getInstance();
        cal.set(2000, java.util.Calendar.JANUARY, 01);
        contentAddOrEditPage.setDateField(By.id("datefieldDate"), cal.getTime());
        
        // TODO - fix this to be a date/time field when github issues is resolved:  https://github.com/dotCMS/dotCMS/issues/4943
        cal.set(2010,  java.util.Calendar.FEBRUARY, 10);
        contentAddOrEditPage.setDateField(By.id("dateandtimefieldDate"), cal.getTime());

        //WebElement xPath = contentAddOrEditPage.getWebElement(By.xpath("(//input[@value=''])[15]"));
        //contentAddOrEditPage.executeJavaScript("arguments[0].click();", xPath);
        //xPath.click();
        
        
        WebElement fileBrowseButton = contentAddOrEditPage.getWebElement(By.id("dijit_form_Button_18"));
        contentAddOrEditPage.executeJavaScript("arguments[0].click();", fileBrowseButton);
        
        // File Browse screen
        WebElement hostTreeNode = null;
        WebElement divTreeNodeContainer = contentAddOrEditPage.getWebElement(By.className("dijitTreeNodeContainer"));
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
        WebElement contentPane = contentAddOrEditPage.getWebElement(By.id("dijit_layout_ContentPane_7"));
        WebElement listingTable = contentPane.findElement(By.className("listingTable"));
        WebElement tableBody = listingTable.findElement(By.tagName("tbody"));
        WebElement file = tableBody.findElement(By.linkText("global-central-banks.pdf"));
        file.click();
        // nothing - contentAddOrEditPage.executeJavaScript("dijit_form_Button_17_label.click();");
        // nothing contentAddOrEditPage.executeJavaScript("dijit_form_Button_17.click();");
        //contentAddOrEditPage.executeJavaScript("dijit.byId('dijit_form_Button_17').dispatchEvent(\"click\");");
        //fileBrowseButton.click();
        Thread.sleep(5000);
        contentAddOrEditPage.saveAndPublish();

        //Thread.sleep(10000);
        // Cleanup
        /*
        structuresPage = portletMenu.getStructuresPage();
        structuresPage.deleteStructureAndContent("OneOfEverything", true);
        Assert.assertEquals(structuresPage.getSystemMessage().trim(), LanguageManager.getValue("message.structure.deletestructure"));
        */
    }
}
