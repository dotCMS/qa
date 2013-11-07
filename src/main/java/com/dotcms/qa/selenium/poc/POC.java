package com.dotcms.qa.selenium.poc;

import java.util.*;

//import org.junit.*;
//import static org.junit.Assert.*;
//import static org.hamcrest.CoreMatchers.*;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.*;

import com.dotcms.qa.util.language.LanguageManager;

import com.dotcms.qa.selenium.util.SeleniumConfig;
import com.dotcms.qa.selenium.util.SeleniumPageManager;

import com.dotcms.qa.selenium.pages.backend.*;

public class POC {
  private static String baseUrl;


  public static void main(String args[]) throws Exception{
    System.out.println("Locale = " + Locale.getDefault());
    System.out.println("file.encoding = " +System.getProperty("file.encoding"));
/*    System.out.println("**************************");
    Set<String> keys = System.getProperties().stringPropertyNames();
    for(String key : keys) {
        System.out.println(key + "=" + System.getProperty(key));
    }       
    System.out.println("**************************");*/
    SeleniumConfig config = SeleniumConfig.getConfig();
    baseUrl = config.getProperty("serverURL");

    SeleniumPageManager pageMgr = SeleniumPageManager.getPageManager();
    pageMgr.loadPage(baseUrl + "admin");
    ILoginPage loginPage = pageMgr.getPageObject(ILoginPage.class);
    IPortletMenu portletMenu = loginPage.login("admin@dotcms.com", "admin");

    String structureName = "JBG" + System.currentTimeMillis();
    IStructuresPage structsPage = portletMenu.getStructuresPage();
    IStructureAddOrEdit_PropertiesPage structAddPage= structsPage.getAddNewStructurePage();
    IStructureAddOrEdit_FieldsPage addFieldPage = structAddPage.createNewStructure(structureName, structureName+"Desc", "System Host");
    addFieldPage = addFieldPage.addTextField("Title", true, true, true, true, true);
    addFieldPage = addFieldPage.addWYSIWYGField("Article", "", "", true, true, true);
    addFieldPage = addFieldPage.addImageField("Photo", "You know the photo / aka the picture file", false);
    addFieldPage = addFieldPage.addBinaryField("Binary File", "", false);
    Thread.sleep(5000);
    IContentSearchPage contentSearchPage = portletMenu.getContentSearchPage();
    IContentAddOrEdit_ContentPage addContentPage = contentSearchPage.getAddContentPage(structureName);
    addContentPage.setTitle("Article #0001");
    addContentPage.toggleWYSIWYGBold();
    addContentPage.toggleWYSIWYGItalic();
    addContentPage.toggleWYSIWYGUnderline();
    addContentPage.addWYSIWYGText("A T T E N T I O N ! ! !" + Keys.RETURN);
    addContentPage.toggleWYSIWYGBold();
    addContentPage.toggleWYSIWYGItalic();
    addContentPage.toggleWYSIWYGUnderline();
    addContentPage.addWYSIWYGText("This is the story that goes on and on my friend." + Keys.RETURN + Keys.RETURN + ".... but since you are my friend, I will end it now.");
    addContentPage.saveAndPublish();
    System.out.println("Pause - admire handiwork");
    Thread.sleep(10000);
    System.out.println("Shutting Down....");
    pageMgr.shutdown();
    return;

/*
//    System.setProperty("webdriver.chrome.driver", "/Users/brent/dev/repos/GriffinCentral/experiment/java/selenium/chromedriver");
//    driver = new ChromeDriver();

//    driver.findElement(By.cssSelector("span.mceIcon.mce_bold")).sendKeys(Keys.PAGE_DOWN); // scroll the screen so the browse button is visible

//    JavascriptExecutor jse = (JavascriptExecutor)driver;
//    jse.executeScript("window.scrollBy(0,300)");
//    jse.executeScript("scroll(0,300)");
    
 //   driver.findElement(By.id("dijit_form_Button_14_label")).click();          // Browse button
 //   driver.findElement(By.cssSelector("img.dijitTreeExpando.dijitTreeExpandoClosed")).click();
 //   driver.findElement(By.cssSelector("div.dijitTreeRow.dijitTreeRowHover > img.dijitTreeExpando.dijitTreeExpandoClosed")).click();

    String verificationErrorString = verificationErrors.toString();
    if (!"".equals(verificationErrorString)) {
//      fail(verificationErrorString);
      System.out.println(verificationErrorString);
    }
    System.out.println("Done");
*/
  }

}
