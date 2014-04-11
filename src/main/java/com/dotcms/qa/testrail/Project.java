package com.dotcms.qa.testrail;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.dotcms.qa.selenium.util.SeleniumConfig;
import com.gurock.testrail.APIClient;

public class Project {
	private static Map<String, JSONObject> projects = null;
    private static final Logger logger = Logger.getLogger(Project.class);

	public static String getProjectId(String projectName) {
		String retValue = null;
		if(projects == null)
			loadProjects();
		JSONObject project = projects.get(projectName);
		if(project != null)
			retValue = project.get("id").toString();
		return retValue;
	}
	
	private static void loadProjects() {
		projects = new HashMap<String, JSONObject>();
		SeleniumConfig config = SeleniumConfig.getConfig();
		APIClient client = new APIClient(config.getProperty("testrail.URL"));
		client.setUser(config.getProperty("testrail.User"));
		client.setPassword(config.getProperty("testrail.Password"));
		try {
			JSONArray projectList = (JSONArray) client.sendGet("get_projects");
			for(int i=0; i<projectList.size(); i++) {
				JSONObject project = (JSONObject)projectList.get(i);
				projects.put(project.get("name").toString(), project);
			}
		}
		catch(Exception e) {
			logger.error("Error loading projects from testrail API", e);
		}
	}
}
