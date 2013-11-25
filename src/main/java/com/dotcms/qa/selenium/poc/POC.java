package com.dotcms.qa.selenium.poc;

import java.util.*;

//import org.junit.*;
//import static org.junit.Assert.*;
//import static org.hamcrest.CoreMatchers.*;
import com.dotcms.qa.selenium.util.SeleniumConfig;
import com.dotcms.qa.selenium.util.SeleniumPageManager;

import com.dotcms.qa.selenium.pages.backend.*;
import org.apache.log4j.Logger;
import org.openqa.selenium.Keys;

public class POC {
    private static final Logger logger = Logger.getLogger(POC.class);

    public static void main(String args[]) throws Exception{
        logger.info("Locale = " + Locale.getDefault());
        logger.info("file.encoding = " +System.getProperty("file.encoding"));
        SeleniumConfig config = SeleniumConfig.getConfig();
        String serverURL = config.getProperty("serverURL");
        logger.info("serverURL = " + serverURL);

        logger.trace("**************************");
        Set<String> keys = System.getProperties().stringPropertyNames();
        for(String key : keys) {
            logger.trace(key + "=" + System.getProperty(key));
        }       
        logger.trace("**************************");

        // login
        SeleniumPageManager pageMgr = SeleniumPageManager.getPageManager();
        pageMgr.loadPage(serverURL + "admin");
        ILoginPage loginPage = pageMgr.getPageObject(ILoginPage.class);
        IPortletMenu portletMenu = loginPage.login("admin@dotcms.com", "admin");

        /*
        // add license
        ILicenseManagerPage licPage = portletMenu.getLicenseManagerPage();
        String licenseLevel = licPage.getLicenseLevel();
        logger.info("License Level = " + licenseLevel);
        licPage.activateLicenseKey(false, "k8Xd32+edtuiKO2N24OxLmPBS+/m9cEjyLoGETbKO1+U3d0ytLc0iaGhg1Tmb24bgs67Q/7yxRVYj1jheW9TPcPBd0E0fc1GkiTR21y1FGRwdoq1aiMZh/zv4QxvoZJg3h5kXJ2pGCi34bv70Urknhy7vRYrccUjdiL/HzC6GcgAAAAJZGV2ZWxvcGVyAAAABAAAAL4AAAAIAAABPaHfL2wAAAAIAAABRPdlEgAAAAAIAAAAAAAYxOMAAAAEAAABkAAAAAEB");
        licenseLevel = licPage.getLicenseLevel();
        logger.info("License Level = " + licenseLevel);
        */

        // Add,edit, and delete vanity URLs
        String vurlTitle1 = "QA Vanity Demo URL";
        String vurl1 = "/team";
        String vurlTitle2 = "QA Vanity AllHosts URL";
        String vurl2 = "/us";
        IVanityURLsPage vanityURLPage = portletMenu.getVanityURLsPage();
        vanityURLPage.addVanityURLToHost(vurlTitle1, "demo.dotcms.com", vurl1, "/about-us/our-team/index.html");
        vanityURLPage.editVanityURL(vurlTitle1, vurlTitle1, "bg", "/about-us/our-team/index.html");
        vanityURLPage.addVanityURLToAllHosts(vurlTitle2, vurl2, "/about-us/index.html");
        vanityURLPage.deleteVanityURL(vurlTitle1);
        vanityURLPage.deleteVanityURL(vurlTitle2);

        // logout
        pageMgr.loadPage(serverURL + "c/portal/logout?referer=/c");
    	loginPage = pageMgr.getPageObject(ILoginPage.class);

        /*
        String structureName = "QA" + System.currentTimeMillis();
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
        logger.debug("Pause - admire handiwork");
		 */
        
        Thread.sleep(5000);
        logger.info("Shutting Down....");
        pageMgr.shutdown();
        return;

    /*

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
