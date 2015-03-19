package com.dotcms.qa.selenium.util;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;

import com.dotcms.qa.selenium.pages.IBasePage;

public class SeleniumPageManager{
    private static final Logger logger = Logger.getLogger(SeleniumPageManager.class);

    public static SeleniumPageManager backendMgrInstance = null;
    public static SeleniumPageManager frontendMgrInstance = null;

    private WebDriver driver = null;
    private String demoServerURL = null;

    public static SeleniumPageManager getBackEndPageManager() throws Exception {
        if(backendMgrInstance == null) {
            SeleniumConfig config = SeleniumConfig.getConfig();
            backendMgrInstance = new SeleniumPageManager(config);
        }
        return backendMgrInstance;
    }

    public static SeleniumPageManager getFrontEndPageManager() throws Exception {
        if(frontendMgrInstance == null) {
            SeleniumConfig config = SeleniumConfig.getConfig();
            frontendMgrInstance = new SeleniumPageManager(config);
        }
        return frontendMgrInstance;
    }

    public static SeleniumPageManager getNewPageManager() throws Exception {
        SeleniumConfig config = SeleniumConfig.getConfig();
        return new SeleniumPageManager(config);
    }

    public static void takeSnapshots(String name) {
    	DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_HHmmss");
    	String prefix = dateFormat.format(new Date());
    	if(backendMgrInstance != null)
    		backendMgrInstance.takeScreenshot(prefix + "_" + name + "_jbgbackend.png");
    	if(frontendMgrInstance != null)
    		frontendMgrInstance.takeScreenshot(prefix + "_" + name + "_jbgfrontend.png");
    }
    
    private SeleniumPageManager(SeleniumConfig config) throws Exception {
        String useBrowserStack=config.getProperty("useBrowserStack");
        logger.info("useBrowserStack=" + useBrowserStack);

        if(useBrowserStack.toLowerCase().equals("true")) {
            logger.info("Using BrowserStack");

            String browserstackURL = config.getProperty("browserStack.URL");
            logger.info("browserstackURL=" + browserstackURL);

            DesiredCapabilities desiredCapabilities = new DesiredCapabilities();

            String browserstackBrowser = config.getProperty("browserStack.capability.browser");
            logger.info("browserstackBrowser=" + browserstackBrowser);
            desiredCapabilities.setCapability("browser", browserstackBrowser);

            String browserstackBrowserVersion = config.getProperty("browserStack.capability.browser.version");
            logger.info("browserstackBrowserVersion=" + browserstackBrowserVersion);            
            desiredCapabilities.setCapability("browser_version", browserstackBrowserVersion);

            String browserstackOS = config.getProperty("browserStack.capability.browser.os");
            logger.info("browserstackOS=" + browserstackOS);
            desiredCapabilities.setCapability("os", browserstackOS);

            String browserstackOSVersion = config.getProperty("browserStack.capability.os.version");
            logger.info("browserstackOSVersion=" + browserstackOSVersion);
            desiredCapabilities.setCapability("os_version", browserstackOSVersion);

            String browserStackDebug = config.getProperty("browserStack.debug");
            logger.info("browserStackDebug=" + browserStackDebug);
            desiredCapabilities.setCapability("browserstack.debug", browserStackDebug);

            driver = new RemoteWebDriver(new URL(browserstackURL), desiredCapabilities);
        }
        else {
            String browserToTarget=config.getProperty("browserToTarget");
            logger.info("Targeting local " + browserToTarget + " browser.");
            if("FIREFOX".equals(browserToTarget)) {
                driver = new FirefoxDriver();
            }
            else if("CHROME".equals(browserToTarget)) {
                System.setProperty("webdriver.chrome.driver", config.getProperty("chromeDriver.location"));
                driver = new ChromeDriver();
            }
            else if("SAFARI".equals(browserToTarget)) {
            	driver = new SafariDriver();
                //throw new UnsupportedOperationException("ERROR - Safari WebDriver not supported at this time.");
            }
            else if("IE".equals(browserToTarget)) {
            	File file = new File(config.getProperty("ieDriver.location"));
            	System.setProperty("webdriver.ie.driver", file.getAbsolutePath());
                driver = new InternetExplorerDriver();
            }
            else if("ANDROID".equals(browserToTarget)) {
                throw new UnsupportedOperationException("ERROR - Android WebDriver not supported at this time.");
            }
            else if("HTMLUNIT".equals(browserToTarget)) {
                throw new UnsupportedOperationException("ERROR - HTMLUnit WebDriver not supported at this time.");
            }
            else {
                throw new Exception("Config Error - unrecognized browserToTarget setting");
            }
        }

        demoServerURL = config.getProperty("demoServerURL");

        // Standardize settings for maximizing consistency
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        driver.manage().window().setPosition(new Point(0,0));
        driver.manage().window().setSize(new Dimension(1024, 768));
    }
    
    public IBasePage loadPage(String url) throws Exception{
        driver.get(url);
        return getPageObject(IBasePage.class);
    }
    
    public void logoutBackend() throws Exception {
        loadPage(demoServerURL + "c/portal/logout?referer=/c");
    }
    
    public void logoutBackend(String serverURL) throws Exception {
        loadPage(serverURL + "c/portal/logout?referer=/c");
    }
    public void shutdown() {
    	if(this == backendMgrInstance)
    		backendMgrInstance = null;
    	else if(this == frontendMgrInstance)
    		frontendMgrInstance = null;
        driver.quit();
    }
    
    public void takeScreenshot(String filename) {
    	try {
	    	File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
	    	FileUtils.copyFile(scrFile, new File(filename),true);
    	}
    	catch(IOException e) {
    		logger.error("Unable to takeScreenshot()", e);
    	}
    }

    // Allow specific browser version
    // Start by looking for class specifically for the browser being targeted.  If not found, will load common version.
    @SuppressWarnings("unchecked")
	public <T> T getPageObject(Class<T> pageInterfaceToProxy) throws Exception {
        T page = null;
        SeleniumConfig config = SeleniumConfig.getConfig();
        ClassLoader classLoader = SeleniumPageManager.class.getClassLoader();
        Class<T> pageClassToProxy = null;
        try {
            String interfaceSimpleName = pageInterfaceToProxy.getSimpleName();
            String classSimpleName = interfaceSimpleName.substring(1);
            String interfacePackageName = pageInterfaceToProxy.getPackage().getName();
            String genericClassName = interfacePackageName + ".common." + classSimpleName;
            String browserToTarget = config.getProperty("browserToTarget").toLowerCase();
            String browserSpecificClassName = interfacePackageName + "." + browserToTarget;
            logger.trace("interfaceSimpleName=" + interfaceSimpleName);
            logger.trace("classSimpleName=" + classSimpleName);
            logger.trace("interfacePackageName=" + interfacePackageName);
            logger.trace("genericClassName=" + genericClassName);
            logger.trace("browserToTarget=" + browserToTarget);
            logger.trace("browserSpecificClassName=" + browserSpecificClassName);
            // Try to load browser specific class if it exists
            try {
                pageClassToProxy = (Class<T>)classLoader.loadClass(browserSpecificClassName);
            }
            catch (Exception e) {
                logger.trace("Unable to load browser specific class - " + browserSpecificClassName + " - Attempting to load generic class: " + genericClassName);
            }
			if(pageClassToProxy == null)
                pageClassToProxy = (Class<T>)classLoader.loadClass(genericClassName);
        }
        catch(Exception e){
        	logger.error("Failed to load class for pageInterfaceToProxy = " + pageInterfaceToProxy);
        	throw e;
        }
        page = PageFactory.initElements(driver, pageClassToProxy);
        return page;
    }
}
