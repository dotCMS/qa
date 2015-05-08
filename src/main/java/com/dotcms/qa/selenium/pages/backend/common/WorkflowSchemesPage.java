package com.dotcms.qa.selenium.pages.backend.common;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.dotcms.qa.selenium.pages.backend.IWorkFlowStepsAddOrEdit_Page;
import com.dotcms.qa.selenium.pages.backend.IWorkflowSchemeAddOrEditPage;
import com.dotcms.qa.selenium.pages.backend.IWorkflowSchemesPage;
import com.dotcms.qa.selenium.pages.common.BasePage;
import com.dotcms.qa.selenium.util.SeleniumPageManager;

/**
 * This class implements the methods defined in the IWorkflowSchemesPage interface
 * to do the Users TestRail validations
 * @author Oswaldo Gallango
 * @since 04/30/2015
 * @version 1.0
 * 
 */
public class WorkflowSchemesPage extends BasePage implements IWorkflowSchemesPage{

	public WorkflowSchemesPage(WebDriver driver) {
		super(driver);
	}

	/**
	 * Returns the add a new Scheme page
	 * @return IWorkflowSchemeAddOrEditPage
	 * @throws Exception
	 */
	public IWorkflowSchemeAddOrEditPage getAddSchemePage() throws Exception{

		List<WebElement> spans = getWebElement(By.id("workflowSchemeMain")).findElement(By.cssSelector("div[class='yui-u']")).findElements(By.cssSelector("span[class='dijitReset dijitInline dijitButtonText']"));
		for(WebElement span : spans){
			if(span.getText().equals(getLocalizedString("Add-Workflow-Scheme"))){
				span.click();
				break;
			}
		}
		return SeleniumPageManager.getBackEndPageManager().getPageObject(IWorkflowSchemeAddOrEditPage.class);
	}

	/**
	 * Returns edit scheme page
	 * @param workflowName Name of the workflow
	 * @return IWorkFlowStepsAddOrEdit_Page
	 * @throws Exception
	 */
	public IWorkFlowStepsAddOrEdit_Page getEditSchemeStepsPage(String workflowName) throws Exception{
		WebElement workflow = finWorkflowScheme(workflowName);
		List<WebElement> header = workflow.findElements(By.tagName("th"));
		header.get(0).click();
		return SeleniumPageManager.getBackEndPageManager().getPageObject(IWorkFlowStepsAddOrEdit_Page.class);
	}
	
	/**
	 * SearchFor the workfloeRow
	 * @param worflowName NAme of the workflow
	 * @return WebElement
	 * @throws Exception
	 */
	private WebElement finWorkflowScheme(String worflowName) throws Exception{
		WebElement workflow = null;
		List<WebElement> rows = getWebElement(By.id("workflowSchemeMain")).findElements(By.cssSelector("div[class='editRow showPointer']"));
		for(WebElement row : rows){
			List<WebElement> header = row.findElements(By.tagName("th"));
			if(header.get(0).getText().trim().contains(worflowName)){
				workflow=row;
				break;
			}
		}
		return workflow;
	}
	
	/**
	 * Validate if a Workflow Scheme exist
	 * @param workflowName Name of the workflow scheme
	 * @return boolean
	 * @throws Exception
	 */
	public boolean doesWorkflowSchemeExist(String workflowName) throws Exception{
		boolean exist = false;
		try{
			WebElement scheme = finWorkflowScheme(workflowName);
			if(scheme != null){
				exist=true;
			}
		}catch(Exception e){}
		return exist;
	}
}
