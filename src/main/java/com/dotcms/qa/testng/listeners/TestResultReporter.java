package com.dotcms.qa.testng.listeners;

import java.util.Set;

import org.apache.log4j.Logger;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

import com.dotcms.qa.testrail.Run;
import com.dotcms.qa.testrail.TestCase;

public class TestResultReporter extends TestListenerAdapter implements ITestListener
{
    private static final Logger logger = Logger.getLogger(TestResultReporter.class);

    public void onTestStart(ITestResult result) {
		super.onTestStart(result);
	}

	public void onTestFailure(ITestResult result) {
		super.onTestFailure(result);
		System.out.println("***** - on Test Failure - *****");
		System.out.println("    name=" + result.getName());
		System.out.println("    testname=" + result.getTestName());
		System.out.println("    instancename=" + result.getInstanceName());
		System.out.println("    status=" + result.getStatus());
		System.out.println("    host=" + result.getHost());
		System.out.println("    attributes:");
		Set<String> attribs = result.getAttributeNames();
		for(String attrib : attribs) {
			System.out.println("        " + attrib +":" + result.getAttribute(attrib));
		}
		recordTestCaseResult(result, TestCase.Status.FAILED);
	}

	public void onTestSkipped(ITestResult result) {
		super.onTestSkipped(result);
	}
	
	public void onTestSuccess(ITestResult result) {
		super.onTestSuccess(result);
		System.out.println("***** - on Test Success - *****");
		recordTestCaseResult(result, TestCase.Status.PASSED);
	}
	
	private void recordTestCaseResult(ITestResult result, TestCase.Status status) {
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
