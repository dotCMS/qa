package com.dotcms.qa.util.Language;

import java.util.*;

public class LanguageManager {
	private static ResourceBundle labels = ResourceBundle.getBundle("Language",Locale.getDefault(), new UTF8ResourceBundleControl());
	
	public static String getValue(String propKey) {
		String retValue = labels.getString(propKey);
		System.out.println("LanguageManager.getValue() - propKey=" + propKey + "|retValue=" + retValue);
		return retValue;
	}

}