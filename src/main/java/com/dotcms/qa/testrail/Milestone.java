package com.dotcms.qa.testrail;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.dotcms.qa.selenium.util.SeleniumConfig;
import com.gurock.testrail.APIClient;

public class Milestone {
	private static Map<String, Map<String, JSONObject>> milestonesByProjectId = null;
    private static final Logger logger = Logger.getLogger(Milestone.class);

	public static String getMilestoneId(String projectId, String milestoneName) {
		String retValue = null;
		if(milestonesByProjectId == null || milestonesByProjectId.get(projectId) == null)
			loadMilestones(projectId);
		Map<String, JSONObject> projectMilestones = milestonesByProjectId.get(projectId);
		if(projectMilestones != null) {
			JSONObject milestone = projectMilestones.get(milestoneName);
			if(milestone != null)
				retValue = milestone.get("id").toString();			
		}
		return retValue;
	}
	
	private static void loadMilestones(String projectId) {
		if(milestonesByProjectId == null)
			milestonesByProjectId = new HashMap<String, Map<String, JSONObject>>();
		
		Map<String, JSONObject> milestones = new HashMap<String, JSONObject>();
		SeleniumConfig config = SeleniumConfig.getConfig();
		APIClient client = new APIClient(config.getProperty("testrail.URL"));
		client.setUser(config.getProperty("testrail.User"));
		client.setPassword(config.getProperty("testrail.Password"));
		try {
			JSONArray milestoneList = (JSONArray) client.sendGet("get_milestones/" + projectId);
			for(int i=0; i<milestoneList.size(); i++) {
				JSONObject milestone = (JSONObject)milestoneList.get(i);
				milestones.put(milestone.get("name").toString(), milestone);
			}
			milestonesByProjectId.put(projectId, milestones);
		}
		catch(Exception e) {
			logger.error("Error loading milestone from testrail API", e);
		}
	}
}
