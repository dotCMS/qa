package com.dotcms.qa.util;

import com.dotcms.qa.selenium.pages.IBasePage;
import com.dotcms.qa.selenium.util.SeleniumConfig;
import com.dotcms.qa.selenium.util.SeleniumPageManager;
import com.dotcms.qa.testng.tests.RegressionSuiteEnv;

/**
 * This class call dynamic plugin methods used in the qa tests.
 * @author Oswaldo Gallango
 *
 */
public class WorkflowPageUtil {
	/**
	 * Call the qa_automation plugin to delete the workflow from db
	 * @param worflowName Name of the workflow
	 * @param serverURL Server url
	 * @return IBasePage
	 * @throws Exception
	 */
	public static IBasePage deleteWorkflow(String worflowName, String serverURL ) throws Exception{
		SeleniumConfig config = SeleniumConfig.getConfig();
		SeleniumPageManager frontendMgr = RegressionSuiteEnv.getFrontendPageManager();
		IBasePage page = frontendMgr.loadPage(serverURL + "api/qa_automation/workflows/delete/name/"+worflowName+"/user/"+config.getProperty("backend.user.Email")+"/password/"+config.getProperty("backend.user.Password"));
		return page;
	}
}
