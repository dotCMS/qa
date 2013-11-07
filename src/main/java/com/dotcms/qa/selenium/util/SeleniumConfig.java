package com.dotcms.qa.selenium.util;

import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;

public class SeleniumConfig {
    private static final Logger logger = Logger.getLogger(SeleniumConfig.class);

    private static SeleniumConfig configInstance = null;
    private Properties props = new Properties();

    public static SeleniumConfig getConfig()  throws Exception{
        if(configInstance == null) {
            configInstance = new SeleniumConfig();
        }
        return configInstance;
    }

    private SeleniumConfig() throws Exception {
        this("POC.properties");
    }

    private SeleniumConfig(String configFileName) throws Exception {
        InputStream in = getClass().getClassLoader().getResourceAsStream(configFileName);
        logger.info("configFileName = " + configFileName);
        logger.debug("in = " + in);
        try {
            props.load(in);
        } finally {
            in.close();
        }
    }

    public String getProperty(String propertyName) {
        return props.getProperty(propertyName);
    }
}
