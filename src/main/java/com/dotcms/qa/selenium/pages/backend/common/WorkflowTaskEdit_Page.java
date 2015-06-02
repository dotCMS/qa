package com.dotcms.qa.selenium.pages.backend.common;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.dotcms.qa.selenium.pages.backend.IWorkflowTaskEdit_Page;
import com.dotcms.qa.selenium.pages.common.BasePage;

/**
 * This class implements the methods defined in the IWorkflowTaskEdit_Page interface
 * @author Oswaldo Gallango
 * @since 05/22/2015
 * @version 1.0
 * 
 */
public class WorkflowTaskEdit_Page extends BasePage implements IWorkflowTaskEdit_Page{

	public WorkflowTaskEdit_Page(WebDriver driver) {
		super(driver);
	}
	
	/**
	 * Select the specified action if is available
	 * @param actionName Action name
	 * @throws Exception
	 */
	public void selectAction(String actionName) throws Exception{
		List<WebElement> actions = getWebElement(By.className("callOutBox2")).findElements(By.className("workflowActionLink"));
		for(WebElement action : actions){
			if(action.getText().trim().contains(actionName)){
				action.click();
				break;
			}
		}
	}

}
