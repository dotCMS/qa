package com.dotcms.qa.testrail;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.dotcms.qa.selenium.util.SeleniumConfig;
import com.gurock.testrail.APIClient;
import com.gurock.testrail.APIException;

public class Run {
	private static Map<String, Map<String, JSONObject>> runsByProjectId = null;
    private static final Logger logger = Logger.getLogger(Run.class);

    public static String currentRunId = "";
    public static String currentRunName = "";
    
	public static String getRunId(String projectId, String runName) {
		String retValue = null;
		if(runsByProjectId == null || runsByProjectId.get(projectId) == null)
			loadRuns(projectId);
		Map<String, JSONObject> projectRuns = runsByProjectId.get(projectId);
		if(projectRuns != null) {
			JSONObject run = projectRuns.get(runName);
			if(run != null)
				retValue = run.get("id").toString();			
		}
		return retValue;
	}
	
	private static void loadRuns(String projectId) {
		if(runsByProjectId == null)
			runsByProjectId = new HashMap<String, Map<String, JSONObject>>();
		
		Map<String, JSONObject> runs = new HashMap<String, JSONObject>();
		SeleniumConfig config = SeleniumConfig.getConfig();
		APIClient client = new APIClient(config.getProperty("testrail.URL"));
		client.setUser(config.getProperty("testrail.User"));
		client.setPassword(config.getProperty("testrail.Password"));
		try {
			JSONArray runList = (JSONArray) client.sendGet("get_runs/" + projectId);
			for(int i=0; i<runList.size(); i++) {
				JSONObject run = (JSONObject)runList.get(i);
				runs.put(run.get("name").toString(), run);
			}
			runsByProjectId.put(projectId, runs);
		}
		catch(Exception e) {
			logger.error("Error loading runs from testrail API", e);
		}
	}

	public static String createRun(String projectId, String suiteId, String milestoneId, String name, String description, String assignedUserId) throws APIException, IOException {
		SeleniumConfig config = SeleniumConfig.getConfig();
		APIClient client = new APIClient(config.getProperty("testrail.URL"));
		client.setUser(config.getProperty("testrail.User"));
		client.setPassword(config.getProperty("testrail.Password"));

		Map data = new HashMap();
		data.put("suite_id", new Integer(suiteId));
		data.put("milestone_id", new Integer(milestoneId));
		data.put("name", name);
		data.put("description", description);
		data.put("assignedto_id", new Integer(assignedUserId));
		data.put("include_all", new Boolean(true));
		JSONObject run = (JSONObject) client.sendPost("add_run/" + projectId, data);

		runsByProjectId = null;	// empty out to force reloading of test runs
		currentRunId = run.get("id").toString();
		currentRunName = run.get("name").toString();
		return run.get("id").toString();
	}
}
