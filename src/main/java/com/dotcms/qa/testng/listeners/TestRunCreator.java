package com.dotcms.qa.testng.listeners;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.apache.log4j.Logger;
import org.testng.ISuite;
import org.testng.ISuiteListener;

import com.dotcms.qa.selenium.util.SeleniumConfig;
import com.dotcms.qa.testrail.Milestone;
import com.dotcms.qa.testrail.Project;
import com.dotcms.qa.testrail.Run;
import com.dotcms.qa.testrail.Suite;
import com.dotcms.qa.testrail.User;

public class TestRunCreator implements ISuiteListener {
    private static final Logger logger = Logger.getLogger(TestRunCreator.class);

	@Override
	public void onStart(ISuite suite) {
		SeleniumConfig config = SeleniumConfig.getConfig();
		
		String recordResultsInTestRail = config.getProperty("reportResultsInTestrail");
		if(recordResultsInTestRail != null && recordResultsInTestRail.trim().toLowerCase().equalsIgnoreCase("true")) {
			String projectId = Project.getProjectId(config.getProperty("testrail.Project"));
			String milestoneId = Milestone.getMilestoneId(projectId, config.getProperty("testrail.Milestone"));
			String suiteId = Suite.getSuiteId(projectId, config.getProperty("testrail.Suite"));
			String userId = User.getUserIdByEmail(config.getProperty("testrail.User"));
			String runLabel = config.getProperty("testrail.RunLabel");
			if(runLabel == null || runLabel.trim().length() == 0)
			{
				String runPrefix = config.getProperty("testrail.RunPrefix");
				DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_HHmmss");
				Calendar cal = Calendar.getInstance();
				runLabel = runPrefix + dateFormat.format(cal.getTime());
			}
			try {
				Run.createRun(projectId, suiteId, milestoneId, runLabel, "Automated test run", userId);
			}
			catch (Exception e) {
				logger.error("Error creating testrail test run", e);
			}
		}
	}

	@Override
	public void onFinish(ISuite suite) {
		// Do nothing - nothing needs to be done here
	}

}
