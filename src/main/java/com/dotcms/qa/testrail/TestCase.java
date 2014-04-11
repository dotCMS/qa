package com.dotcms.qa.testrail;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.json.simple.JSONObject;

import com.dotcms.qa.selenium.util.SeleniumConfig;
import com.gurock.testrail.APIClient;

public class TestCase {
    private static final Logger logger = Logger.getLogger(TestCase.class);
    
    public static enum Status { PASSED, BLOCKED, UNTESTED, RETEST, FAILED }
    
    public static void addResult(String runId, String testCaseId, Status status) throws Exception {
		SeleniumConfig config = SeleniumConfig.getConfig();
		APIClient client = new APIClient(config.getProperty("testrail.URL"));
		client.setUser(config.getProperty("testrail.User"));
		client.setPassword(config.getProperty("testrail.Password"));

		Map data = new HashMap();
		data.put("status_id", new Integer(getStatusCode(status)));
		client.sendPost("add_result_for_case/" + runId + "/" + testCaseId, data);
    }
    
    private static int getStatusCode(Status status) {
    	int retValue = 0;
    	switch(status) {
    	case PASSED:
    		retValue = 1;
    		break;
    	case BLOCKED:
    		retValue = 2;
    		break;
    	case UNTESTED:
    		retValue = 3;
    		break;
    	case RETEST:
    		retValue = 4;
    	case FAILED:
    		retValue = 5;
    	}
    	return retValue;
    }
}
