package com.dotcms.qa.testrail;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.dotcms.qa.selenium.util.SeleniumConfig;
import com.gurock.testrail.APIClient;

public class User {
	private static Map<String, JSONObject> usersByEmail = null;
    private static final Logger logger = Logger.getLogger(User.class);

	public static String getUserIdByEmail(String email) {
		String retValue = null;
		if(usersByEmail == null)
			loadUsersByEmail();
		JSONObject user = usersByEmail.get(email);
		if(user != null)
			retValue = user.get("id").toString();
		return retValue;
	}
	
	private static void loadUsersByEmail() {
		usersByEmail = new HashMap<String, JSONObject>();
		SeleniumConfig config = SeleniumConfig.getConfig();
		APIClient client = new APIClient(config.getProperty("testrail.URL"));
		client.setUser(config.getProperty("testrail.User"));
		client.setPassword(config.getProperty("testrail.Password"));
		try {
			JSONArray userList = (JSONArray) client.sendGet("get_users");
			for(int i=0; i<userList.size(); i++) {
				JSONObject user = (JSONObject)userList.get(i);
				usersByEmail.put(user.get("email").toString(), user);
			}
		}
		catch(Exception e) {
			logger.error("Error loading users from testrail API", e);
		}
	}
}
