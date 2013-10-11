package com.dotcms.qa.selenium.util;

import java.io.InputStream;
import java.util.Properties;

public class SeleniumConfig {

    private static SeleniumConfig configInstance = null;
    private Properties props = new Properties();

    public static SeleniumConfig getConfig()  throws Exception{
        if(configInstance == null) {
            configInstance = new SeleniumConfig();
        }
        return configInstance;
    }

    private SeleniumConfig() throws Exception {
        this("config.properties");
    }

    private SeleniumConfig(String configFileName) throws Exception {
        InputStream in = getClass().getClassLoader().getResourceAsStream(configFileName);
        System.out.println("configFileName = " + configFileName);
        System.out.println("in = " + in);
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
