package com.dotcms.qa.testrail;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.dotcms.qa.selenium.util.SeleniumConfig;
import com.gurock.testrail.APIClient;

public class Suite {
	private static Map<String, Map<String, JSONObject>> suitesByProjectId = null;
    private static final Logger logger = Logger.getLogger(Suite.class);

	public static String getSuiteId(String projectId, String suiteName) {
		String retValue = null;
		if(suitesByProjectId == null || suitesByProjectId.get(projectId) == null)
			loadSuites(projectId);
		Map<String, JSONObject> projectSuites = suitesByProjectId.get(projectId);
		if(projectSuites != null) {
			JSONObject suite = projectSuites.get(suiteName);
			if(suite != null)
				retValue = suite.get("id").toString();			
		}
		return retValue;
	}
	
	private static void loadSuites(String projectId) {
		if(suitesByProjectId == null)
			suitesByProjectId = new HashMap<String, Map<String, JSONObject>>();
		
		Map<String, JSONObject> suites = new HashMap<String, JSONObject>();
		SeleniumConfig config = SeleniumConfig.getConfig();
		APIClient client = new APIClient(config.getProperty("testrail.URL"));
		client.setUser(config.getProperty("testrail.User"));
		client.setPassword(config.getProperty("testrail.Password"));
		try {
			JSONArray suiteList = (JSONArray) client.sendGet("get_suites/" + projectId);
			for(int i=0; i<suiteList.size(); i++) {
				JSONObject suite = (JSONObject)suiteList.get(i);
				suites.put(suite.get("name").toString(), suite);
			}
			suitesByProjectId.put(projectId, suites);
		}
		catch(Exception e) {
			logger.error("Error loading suites from testrail API", e);
		}
	}
}
