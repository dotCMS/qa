package com.dotcms.qa.selenium.pages.backend.common;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.dotcms.qa.selenium.pages.backend.IWorkflowTasksPage;
import com.dotcms.qa.selenium.pages.common.BasePage;

public class WorkflowTasksPage extends BasePage implements IWorkflowTasksPage{

	public WorkflowTasksPage(WebDriver driver) {
		super(driver);
	}

	/**
	 * Get the current step of the specified workflow taks
	 * @param title   Title of the content
	 * @param workflowScheme Scheme
	 * @return String
	 * @throws Exception
	 */
	public String getWorflowTaskCurrentStep(String title, String workflowScheme) throws Exception{
		String value ="";
		try{
			WebElement task = findWorkflowTask(title, workflowScheme);
			List<WebElement> columns = task.findElements(By.tagName("td"));
			value=columns.get(3).getText();
		}catch(Exception e){}
		
		return value;
	}

	/**
	 * return the row the workflow task
	 * @param title Title of the content
	 * @param workflowScheme Scheme
	 * @return
	 * @throws Exception
	 */
	private WebElement findWorkflowTask(String title, String workflowScheme) throws Exception{
		WebElement workflowTask=null;
		WebElement keywords = getWebElement(By.id("filterTasksFrm")).findElement(By.id("keywords"));
		keywords.clear();
		keywords.sendKeys(title);

		WebElement scheme = getWebElement(By.id("filterTasksFrm")).findElement(By.id("schemeId"));
		scheme.clear();
		scheme.sendKeys(workflowScheme);
		getWebElement(By.id("schemeId_popup0")).click();

		List<WebElement> buttons = getWebElement(By.cssSelector("div[class='buttonRow']")).findElements(By.cssSelector("span[class='dijitReset dijitInline dijitButtonText']"));
		for(WebElement button : buttons){
			if(button.getText().trim().equals(getLocalizedString("Search"))){
				button.click();
				sleep(2);
				break;				
			}
		}

		List<WebElement> rows = getWebElement(By.id("workflowTaskListCp")).findElements(By.tagName("tr"));
		for(WebElement row : rows){
			List<WebElement> columns = row.findElements(By.tagName("td"));
			if(columns.size() > 2){
				if(columns.get(1).getText().trim().equals(title)){
					workflowTask = row;
					break;
				}
			}
		}
		return workflowTask;
	}
}
