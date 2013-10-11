package com.dotcms.qa.util;

import java.util.*;

public class LanguageManager {
	private static ResourceBundle labels = ResourceBundle.getBundle("Language",Locale.getDefault());
	
	public static String getValue(String propKey) {
		return labels.getString(propKey);
	}
}