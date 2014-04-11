package com.dotcms.qa.selenium.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;

public class SeleniumConfig {
    private static final Logger logger = Logger.getLogger(SeleniumConfig.class);

    private static SeleniumConfig configInstance = null;
    private Properties systemProps = System.getProperties();
    private Properties props = new Properties();

    public static SeleniumConfig getConfig(){
        if(configInstance == null) {
            configInstance = new SeleniumConfig();
        }
        return configInstance;
    }

    private SeleniumConfig() {
        this("qa.properties");
    }

    private SeleniumConfig(String configFileName) {
        InputStream in = getClass().getClassLoader().getResourceAsStream(configFileName);
        logger.info("configFileName = " + configFileName);
        logger.debug("in = " + in);
        try {
            props.load(in);
        }
        catch(IOException e) {
        	logger.error("Error loading config properties file:" + configFileName , e);
        }
    	finally {
    		try {
    			in.close();
    		}
    		catch(Exception e) {
    			logger.error("Error closing, config properties file" + configFileName, e);
    		}
        }
    }

    public String getProperty(String propertyName) {
    	String retValue = null;
    	retValue = systemProps.getProperty(propertyName);
    	if(retValue == null)
    		retValue = props.getProperty(propertyName);
    	return retValue;
    }
}
