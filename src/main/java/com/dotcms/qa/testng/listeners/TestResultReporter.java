package com.dotcms.qa.testng.listeners;

import org.apache.log4j.Logger;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

import com.dotcms.qa.selenium.util.SeleniumConfig;
import com.dotcms.qa.selenium.util.SeleniumPageManager;
import com.dotcms.qa.testrail.Run;
import com.dotcms.qa.testrail.TestCase;

public class TestResultReporter extends TestListenerAdapter implements ITestListener
{
    private static final Logger logger = Logger.getLogger(TestResultReporter.class);

    public void onTestStart(ITestResult result) {
		super.onTestStart(result);
		logger.info("***** Test " + result.getName() + " started *****");
	}

	public void onTestFailure(ITestResult result) {
		super.onTestFailure(result);
		SeleniumPageManager.takeSnapshots(result.getName());
		recordTestCaseResult(result, TestCase.Status.FAILED);
	}

	public void onTestSkipped(ITestResult result) {
		super.onTestSkipped(result);
	}
	
	public void onTestSuccess(ITestResult result) {
		super.onTestSuccess(result);
		recordTestCaseResult(result, TestCase.Status.PASSED);
	}
	
	private void recordTestCaseResult(ITestResult result, TestCase.Status status) {
		SeleniumConfig config = SeleniumConfig.getConfig();
		String recordResultsInTestRail = config.getProperty("reportResultsInTestrail");
		if(recordResultsInTestRail != null && recordResultsInTestRail.trim().toLowerCase().equalsIgnoreCase("true")) {
			String caseId = null;
			String name = result.getName();
			if(name.startsWith("tc") && name.contains("_")) {
				caseId = name.substring(2, name.indexOf("_"));
			}
			else
			{
				logger.error("ERROR parsing method name.  Unable to record test case result in testrail for method:" + name);
			}	
			
			if(caseId != null) {
				try {
					TestCase.addResult(Run.currentRunId, caseId, status);
				}
				catch(Exception e) {
					logger.error("Error recording test case result in testrail for caseId:" + caseId, e);
				}
			}		
		}
	}
}
