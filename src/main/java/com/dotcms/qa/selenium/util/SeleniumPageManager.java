package com.dotcms.qa.selenium.util;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.WebDriver;

public class SeleniumPageManager{

    public static SeleniumPageManager mgrInstance = null;

    private WebDriver driver = null;

    public static SeleniumPageManager getPageManager() throws Exception {
        if(mgrInstance == null) {
            SeleniumConfig config = SeleniumConfig.getConfig();
            mgrInstance = new SeleniumPageManager(config);
        }
        return mgrInstance;
    }

    private SeleniumPageManager(SeleniumConfig config) throws Exception {
        String browserToTarget=config.getProperty("browserToTarget");
        if("FIREFOX".equals(browserToTarget)) {
            driver = new FirefoxDriver();
        }
        else if("CHROME".equals(browserToTarget)) {
            throw new UnsupportedOperationException("ERROR - Chrome WebDriver not supported at this time.");
        }
        else if("SAFARI".equals(browserToTarget)) {
            throw new UnsupportedOperationException("ERROR - Safari WebDriver not supported at this time.");
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

        // Standardize settings for maximizing consistency
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        driver.manage().window().setPosition(new Point(0,0));
        driver.manage().window().setSize(new Dimension(1024, 768));
    }

    public void loadPage(String url) {
        driver.get(url);
    }

    public void shutdown() {
        driver.quit();
    }

    @SuppressWarnings("unchecked")
	public <T> T getPageObject(Class<T> pageInterfaceToProxy) throws Exception {
        T page = null;
        ClassLoader classLoader = SeleniumPageManager.class.getClassLoader();
        Class<T> pageClassToProxy = null;
        try {
			pageClassToProxy = (Class<T>)classLoader.loadClass(SeleniumConfig.getConfig().getProperty(pageInterfaceToProxy.getSimpleName()));
        }
        catch(Exception e){
        	System.out.println("ERROR - failed to load class for pageInterfaceToProxy = " + pageInterfaceToProxy);
        	if(pageInterfaceToProxy != null) {
        		System.out.println("pageInterfaceToProxy.getSimpleName() = " + pageInterfaceToProxy.getSimpleName());
        		System.out.println("SeleniumConfig.getConfig().getProperty(\""+pageInterfaceToProxy.getSimpleName()+"\") = " + SeleniumConfig.getConfig().getProperty(pageInterfaceToProxy.getSimpleName()));
        	}
        	throw e;
        }
        page = PageFactory.initElements(driver, pageClassToProxy);
        return page;
    }
}
