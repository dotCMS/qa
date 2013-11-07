package com.dotcms.qa.util.language;

import java.util.*;

import org.apache.log4j.Logger;

public class LanguageManager {
    private static final Logger logger = Logger.getLogger(LanguageManager.class);

	private static ResourceBundle labels = ResourceBundle.getBundle("Language",Locale.getDefault(), new UTF8ResourceBundleControl());
	
	public static String getValue(String propKey) {
		String retValue = labels.getString(propKey);
		logger.trace("LanguageManager.getValue() - propKey=" + propKey + "|retValue=" + retValue);
		return retValue;
	}

}