package com.dotcms.qa.testng.tests;

import java.util.*;

import com.dotcms.qa.selenium.util.SeleniumConfig;
import com.dotcms.qa.selenium.util.SeleniumPageManager;

import com.dotcms.qa.selenium.pages.backend.*;
import com.dotcms.qa.util.language.LanguageManager;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.testng.Assert;
import org.testng.annotations.AfterGroups;
import org.testng.annotations.BeforeGroups;
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
        addOrEditFieldsPage.addMultiSelectField("MultiSelectField", "Option 000000001|000000001\r\nOption 000000002|000000002\r\nOption 000000003|000000003\r\n", "000000001", "MultiSelectField Hint", false, false, false, false);
        Assert.assertEquals(structuresPage.getSystemMessage().trim(), LanguageManager.getValue("message.structure.savefield"));
        addOrEditFieldsPage.addRadioField("RadioField", "Florida|FL\r\nMichigan|MI\r\nOhio|OH\r\nWest Virginia|WV\r\nWisconsin|WI", "WI", "RadioField Hint", false, false, false, false);
        Assert.assertEquals(structuresPage.getSystemMessage().trim(), LanguageManager.getValue("message.structure.savefield"));
        addOrEditFieldsPage.addRelationshipsField("RelationshipsField");
        Assert.assertEquals(structuresPage.getSystemMessage().trim(), LanguageManager.getValue("message.structure.savefield"));
        addOrEditFieldsPage.addSelectField("SelectField", "OptionA|A\r\nOptionB|B\r\nOptionC|C\r\nOptionD|D", "B", "SelectField Hint", false, false, false, false);
        Assert.assertEquals(structuresPage.getSystemMessage().trim(), LanguageManager.getValue("message.structure.savefield"));
        addOrEditFieldsPage.addTagField("TagField", "google", "TagField Hint", false, false);
        Assert.assertEquals(structuresPage.getSystemMessage().trim(), LanguageManager.getValue("message.structure.savefield"));
        addOrEditFieldsPage.addTextareaField("TextAreaField", "", "Letters only", "TextAreaField Default Area", "TextAreaField Hint", false, false, false);
        Assert.assertEquals(structuresPage.getSystemMessage().trim(), LanguageManager.getValue("message.structure.savefield"));
        addOrEditFieldsPage.addTimeField("TimeField", "", "TimeField Hint", false, false, false, false);
        Assert.assertEquals(structuresPage.getSystemMessage().trim(), LanguageManager.getValue("message.structure.savefield"));
        addOrEditFieldsPage.addWYSIWYGField("WYSIWYGField", "default text here", "WYSIWYGField Hint", false, true, true);
        Assert.assertEquals(structuresPage.getSystemMessage().trim(), LanguageManager.getValue("message.structure.savefield"));
        addOrEditFieldsPage.addKeyValueField("KeyValueField", "KeyValueField Hint", false, false);
        addOrEditFieldsPage.addTabDividerField("Permissions Tab");
        Assert.assertEquals(structuresPage.getSystemMessage().trim(), LanguageManager.getValue("message.structure.savefield"));
        addOrEditFieldsPage.addPermissionsField("PermissionsFields");
        Assert.assertEquals(structuresPage.getSystemMessage().trim(), LanguageManager.getValue("message.structure.savefield"));
        
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

        
        ISelectAFileDialog selectFileDlg = contentAddOrEditPage.getSelectAFileDialog(By.id("dijit_form_Button_18"), "dijit_layout_ContentPane_6", "dijit_layout_ContentPane_7");
        selectFileDlg.selectFile("qademo.dotcms.com/intranet/documents/global-central-banks.pdf");
		
        contentAddOrEditPage.scroll(0, 300);
//        contentAddOrEditPage.setHostOrFolder("qademo.dotcms.com");
        contentAddOrEditPage.setHostOrFolder("qademo.dotcms.com/about-us/locations");
        
        ISelectAFileDialog selectImageDlg = contentAddOrEditPage.getSelectAFileDialog(By.id("dijit_form_Button_25"), "dijit_layout_ContentPane_8", "dijit_layout_ContentPane_9");
        selectImageDlg.selectFile("qademo.dotcms.com/images/photos/The-Gherkin-London-England.jpg");

        Calendar cal2 = Calendar.getInstance();
        cal2.set(Calendar.HOUR_OF_DAY, 13);
        cal2.set(Calendar.MINUTE, 10);
        contentAddOrEditPage.setTimeField(By.id("timefieldTime"), cal2.getTime());
        
        // TODO verify underline functionality after github issue #5209 resolved - https://github.com/dotCMS/dotCMS/issues/5209
        contentAddOrEditPage.toggleWYSIWYGBold();
        contentAddOrEditPage.toggleWYSIWYGItalic();
        contentAddOrEditPage.toggleWYSIWYGUnderline();
        contentAddOrEditPage.addWYSIWYGText("A T T E N T I O N ! ! !" + Keys.RETURN);
        contentAddOrEditPage.toggleWYSIWYGBold();
        contentAddOrEditPage.toggleWYSIWYGItalic();
        contentAddOrEditPage.toggleWYSIWYGUnderline();
        contentAddOrEditPage.addWYSIWYGText("This is the story that goes on and on my friend." + Keys.RETURN + Keys.RETURN + ".... but since you are my friend, I will end it now.");

        contentAddOrEditPage.setTextField(By.id("keyvaluefield_key"), "key01");
        contentAddOrEditPage.setTextField(By.id("keyvaluefield_value"), "value01");
        contentAddOrEditPage.addKeyValuePair(By.id("text_area6_addbutton"));
        contentAddOrEditPage.saveAndPublish();

        // Cleanup
        /*
        structuresPage = portletMenu.getStructuresPage();
        structuresPage.deleteStructureAndContent("OneOfEverything", true);
        Assert.assertEquals(structuresPage.getSystemMessage().trim(), LanguageManager.getValue("message.structure.deletestructure"));
        */
    }
}
