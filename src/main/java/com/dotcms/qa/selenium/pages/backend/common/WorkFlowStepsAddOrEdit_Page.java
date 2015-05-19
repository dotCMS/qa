package com.dotcms.qa.selenium.pages.backend.common;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.dotcms.qa.selenium.pages.backend.IStructureAddOrEdit_FieldsPage;
import com.dotcms.qa.selenium.pages.backend.IWorkFlowStepsAddOrEdit_Page;
import com.dotcms.qa.selenium.pages.backend.IWorkflowActionAddOrEdit_Page;
import com.dotcms.qa.selenium.pages.common.BasePage;
import com.dotcms.qa.selenium.util.SeleniumPageManager;
import com.thoughtworks.selenium.webdriven.commands.Click;

/**
 * This class implements the methods defined in the IWorkFlowStepsAddOrEdit_Page interface
 * @author Oswaldo Gallango
 * @since 05/04/2015
 * @version 1.0
 * 
 */
public class WorkFlowStepsAddOrEdit_Page extends BasePage implements IWorkFlowStepsAddOrEdit_Page{

	public WorkFlowStepsAddOrEdit_Page(WebDriver driver) {
		super(driver);
	}

	/**
	 * Add a new step i the workflow scheme
	 * @param stepName Name of the step
	 * @throws Exception
	 */
	public void addWorkflowStep(String stepName) throws Exception{
		WebElement button = getWebElement(By.id("dropdownButtonContainer")).findElement(By.id("dijit_form_DropDownButton_0_label"));
		button.click();
		WebElement step =getWebElement(By.id("stepName"));
		step.clear();
		step.sendKeys(stepName);
		getWebElement(By.id("Save-new-step_label")).click();
	}

	/**
	 * Edit workflow step properties
	 * @param stepName     Current stepName
	 * @param newStepName  New Step Name
	 * @param stepOrder        Step order 
	 * @param resolveTask  if step is resolved task
	 * @param scheduleEnable if step is schedule enable
	 * @param scheduleAction scheduled action
	 * @param scheduleIn     schedule in value
	 * @throws Exception
	 */
	public void editWorkflowStep(String stepName, String newStepName, String stepOrder, boolean resolveTask, boolean scheduleEnable, String scheduleAction, String scheduleIn) throws Exception{
		WebElement step = findStep(newStepName);
		step.findElement(By.cssSelector("div[class='showPointer wfStepTitleDivs']")).click();
		WebElement stepDialog = getWebElement(By.id("stepEditDia"));

		WebElement name = stepDialog.findElement(By.name("stepName"));
		name.clear();
		name.sendKeys(newStepName);

		WebElement order = stepDialog.findElement(By.name("stepOrder"));
		order.clear();
		order.sendKeys(stepOrder);

		WebElement stepResolved = stepDialog.findElement(By.name("stepResolved"));
		if(resolveTask){
			if(!stepResolved.isSelected()){
				stepResolved.click();
			}
		}else{
			if(stepResolved.isSelected()){
				stepResolved.click();
			}
		}

		WebElement enableEscalation = stepDialog.findElement(By.name("enableEscalation"));
		if(scheduleEnable){
			if(!enableEscalation.isSelected()){
				enableEscalation.click();
				sleep(2);
				if(scheduleAction != null && !scheduleAction.equals("")){
					WebElement escalationAction = stepDialog.findElement(By.id("escalationAction"));
					escalationAction.clear();
					escalationAction.sendKeys(scheduleAction);
					getWebElement(By.id("escalationAction_popup0")).click();
				}
				if(scheduleIn != null && !scheduleIn.equals("")){
					WebElement escalationTime = stepDialog.findElement(By.id("escalationTime"));
					escalationTime.clear();
					escalationTime.sendKeys(scheduleIn);					
				}
			}
		}else{
			if(enableEscalation.isSelected()){
				enableEscalation.click();
			}
		}
		stepDialog.findElement(By.id("editStepBtn_label")).click();
	}

	/**
	 * Get the step WebElement
	 * @param stepName Name of the step 
	 * @return WebElement
	 * @throws Exception
	 */
	private WebElement findStep(String stepName) throws Exception{
		WebElement step = null;
		List<WebElement> steps = getWebElements(By.className("wfStepBoundingBox"));
		for(WebElement stepElem : steps){
			WebElement title = stepElem.findElement(By.className("wfStepTitle ")).findElement(By.cssSelector("span[style='border-bottom:dotted 1px gray;']"));
			if(title.getText().trim().equals(stepName)){
				step=stepElem;
				break;
			}
		}
		return step;
	}

	/**
	 * Add a new action in the specified step
	 * @param stepName Name of the step
	 * @return IWorkflowActionAddOrEdit_Page
	 * @throws Exception
	 */
	public IWorkflowActionAddOrEdit_Page addActionToStep(String stepName) throws Exception{
		WebElement step = findStep(stepName);
		step.findElement(By.cssSelector("span[class='dijitReset dijitInline dijitIcon addIcon']")).click();
		return SeleniumPageManager.getBackEndPageManager().getPageObject(IWorkflowActionAddOrEdit_Page.class);
	}

	/**
	 * Validates if a step exist in the workflow scheme
	 * @param stepName Name of the step
	 * @return boolean
	 * @throws Exception
	 */
	public boolean doesWorkflowStepExist(String stepName) throws Exception{
		boolean exist = false;
		try{
			WebElement step = findStep(stepName);
			if(step != null){
				exist = true;
			}
		}catch(Exception e){

		}

		return exist;
	}

	/**
	 *  Delete step on Workflow scheme 
	 * @param stepName	Name of the step
	 * @throws Exception
	 */
	public void deleteStep(String stepName) throws Exception{
		WebElement step = findStep(stepName);
		step.findElement(By.className("wfStepTitle ")).findElement(By.cssSelector("span[class='deleteIcon']")).click();
		switchToAlert().accept();
	}
	
	/**
	 * Edit the specified action under the requested step
	 * @param stepName Step Name
	 * @param actionName Action Name
	 * @return IWorkflowActionAddOrEdit_Page
	 * @throws Exception
	 */
	public IWorkflowActionAddOrEdit_Page editWorkflowAction(String stepName, String actionName) throws Exception{
		WebElement step = findStep(stepName);
		List<WebElement> actions = step.findElements(By.cssSelector("td[class='showPointer']"));
		for(WebElement action : actions){
			if(action.getText().contains(actionName)){
				action.click();
				break;
			}
		}		
		return SeleniumPageManager.getBackEndPageManager().getPageObject(IWorkflowActionAddOrEdit_Page.class);
	}
	
}
