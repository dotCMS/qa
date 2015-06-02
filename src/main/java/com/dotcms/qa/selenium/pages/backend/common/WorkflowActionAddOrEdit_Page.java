package com.dotcms.qa.selenium.pages.backend.common;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.dotcms.qa.selenium.pages.backend.IWorkflowActionAddOrEdit_Page;
import com.dotcms.qa.selenium.pages.common.BasePage;

/**
 * This class implements the methods defined in the IWorkflowActionAddOrEdit_Page interface
 * @author Oswaldo Gallango
 * @since 05/05/2015
 * @version 1.0
 * 
 */
public class WorkflowActionAddOrEdit_Page extends BasePage implements IWorkflowActionAddOrEdit_Page{

	private WebElement actionName;
	private WebElement actionRequiresCheckout;
	private WebElement whoCanUseSelect;
	private WebElement whoCanUseSelect_popup0;
	private WebElement whoCanUseTbl;
	private WebElement actionCommentable;
	private WebElement actionAssignable;
	private WebElement actionAssignToSelect;
	private WebElement actionAssignToSelect_popup0;
	private WebElement actionNextStep;
	private WebElement actionNextStep_popup0;
	private WebElement actionIconSelect;
	private WebElement actionIconSelect_popup0;
	private WebElement actionCondition; 
	private WebElement wfActionlets;
	private WebElement wfActionlets_popup0;


	public WorkflowActionAddOrEdit_Page(WebDriver driver) {
		super(driver);
	}

	/**
	 * Set the action name
	 * @param workflowActionName Action name
	 * @throws Exception
	 */
	public void setActionName(String workflowActionName) throws Exception{
		actionName.clear();
		actionName.sendKeys(workflowActionName);		
	}

	/**
	 * Get the action Name
	 * @return String
	 * @throws Exception
	 */
	public String getActionName() throws Exception{
		return actionName.getAttribute("value");
	}

	/**
	 * Set if the teh user can save or not content
	 * @param saveContent Boolean
	 * @throws Exception
	 */
	public void setSaveContent(boolean saveContent) throws Exception{
		if(saveContent){
			if(!actionRequiresCheckout.isSelected()){
				actionRequiresCheckout.click();
			}
		}else{
			if(actionRequiresCheckout.isSelected()){
				actionRequiresCheckout.click();
			}

		}
	}

	/**
	 * Indicate if the save content checkbox is set
	 * @return boolean
	 * @throws Exception
	 */
	public boolean isSaveContent() throws Exception{
		return actionRequiresCheckout.isSelected();
	}

	/**
	 * Add a user or roles to the list of who can use
	 * @param userRole Name of the role or user
	 * @throws Exception
	 */
	public void setWhoCanUse(String userRole) throws Exception{
		whoCanUseSelect.clear();
		whoCanUseSelect.sendKeys(userRole);
		if(whoCanUseSelect_popup0.isDisplayed()){
			whoCanUseSelect_popup0.click();
		}else{
			getWebElement(By.id("widget_whoCanUseSelect")).findElement(By.cssSelector("div[class='dijitReset dijitRight dijitButtonNode dijitArrowButton dijitDownArrowButton dijitArrowButtonContainer']")).click();
			sleep(2);
			List<WebElement> options = getWebElement(By.id("whoCanUseSelect_popup")).findElements(By.cssSelector("div[class='dijitReset dijitMenuItem']"));
			for(WebElement option :  options){
				if(option.getText().trim().contains(userRole)){
					option.click();
					break;
				}
			}
		}
		sleep(2);
		List<WebElement> buttons = getWebElements(By.cssSelector("span[class='dijitReset dijitInline dijitButtonText']"));
		for(WebElement button : buttons){
			if(button.getText().trim().equals(getLocalizedString("Add"))){
				button.click();
				break;
			}
		}
	}

	/**
	 * Get the list of user or roles that can use the action
	 * @return List<String>
	 * @throws Exception
	 */
	public List<String> getWhocanUse() throws Exception{
		List<String> whocanUseList = new ArrayList<String>();
		List<WebElement> rows = whoCanUseTbl.findElements(By.tagName("tr"));
		for(WebElement row: rows){
			List<WebElement> columns = row.findElements(By.tagName("td"));
			whocanUseList.add(columns.get(1).getText());
		}		
		return whocanUseList;
	}

	/**
	 * Set if the action allows comments
	 * @param allowsComment Boolean
	 * @throws Exception
	 */
	public void setAllowComment(boolean allowsComment) throws Exception{
		if(allowsComment){
			if(!actionCommentable.isSelected()){
				actionCommentable.click();
			}
		}else{
			if(actionCommentable.isSelected()){
				actionCommentable.click();
			}

		}
	}

	/**
	 * Get if the action allows comments
	 * @return boolean
	 * @throws Exception
	 */
	public boolean areAllowedComments() throws Exception{
		return actionCommentable.isSelected();
	}

	/**
	 * Set If a user can assign
	 * @param userCanAssign
	 * @throws Exception
	 */
	public void setUserCanAssign(boolean userCanAssign) throws Exception{
		if(userCanAssign){
			if(!actionAssignable.isSelected()){
				actionAssignable.click();
			}
		}else{
			if(actionAssignable.isSelected()){
				actionAssignable.click();
			}

		}
	}

	/**
	 * Get if a user can assign
	 * @param userCanAssign
	 * @return
	 * @throws Exception
	 */
	public boolean userCanAssign(boolean userCanAssign) throws Exception{
		return actionAssignable.isSelected();
	}

	/**
	 * Set the user or role that can assign this action
	 * @param userRole   Name of the user or role
	 * @throws Exception
	 */
	public void setAssignTo(String userRole) throws Exception{
		actionAssignToSelect.clear();
		actionAssignToSelect.sendKeys(userRole);
		actionAssignToSelect_popup0.click();
	}

	/**
	 * Get the name of the user or role that can assign
	 * @return String
	 * @throws Exception
	 */
	public String getAssignTo() throws Exception{
		return actionAssignToSelect.getAttribute("value");
	}

	/**
	 * Set the next step
	 * @param stepName Name of the next step
	 * @throws Exception
	 */
	public void setNextStep(String stepName) throws Exception{
		actionNextStep.clear();
		actionNextStep.sendKeys(stepName);
		actionNextStep_popup0.click();
	}

	/**
	 * Get the next step name
	 * @return String
	 * @throws Exception
	 */
	public String getNextStep() throws Exception{
		return actionNextStep.getAttribute("value");
	}

	/**
	 * Set the icon for this action
	 * @param iconName Name of the icon
	 * @throws Exception
	 */
	public void setIcon(String iconName) throws Exception{
		actionIconSelect.clear();
		actionIconSelect.sendKeys(iconName);
		actionIconSelect_popup0.click();
	}

	/**
	 * Get the name of the icon assigned to this action
	 * @return String
	 * @throws Exception
	 */
	public String getIcon() throws Exception{
		return actionIconSelect.getAttribute("value");
	}

	/**
	 * Set the custom code field
	 * @param code    The custom code
	 * @throws Exception
	 */
	public void setCustomCode(String code) throws Exception{
		actionCondition.clear();
		actionCondition.sendKeys(code);
	}

	/**
	 * Get the custom field
	 * @return String
	 * @throws Exception
	 */
	public String getCustomCode() throws Exception{
		return actionCondition.getText();
	}

	/**
	 * Add the save button
	 * @throws Exception
	 */
	public void save() throws Exception{
		List<WebElement> buttons = getWebElement(By.className("wfAddActionButtonRow")).findElement(By.id("saveButtonDiv")).findElements(By.cssSelector("span[class='dijitReset dijitInline dijitButtonText']"));
		for(WebElement button : buttons){
			if(button.getText().trim().equals(getLocalizedString("Save"))){
				button.click();
				break;
			}
		}
	}

	/**
	 * Click the cancel button
	 * @throws Exception
	 */
	public void cancel() throws Exception{
		List<WebElement> buttons = getWebElement(By.className("wfAddActionButtonRow")).findElements(By.cssSelector("span[class='dijitReset dijitInline dijitButtonText']"));
		for(WebElement button : buttons){
			if(button.getText().trim().equals(getLocalizedString("cancel"))){
				button.click();
				break;
			}
		}
	}

	/**
	 * Click the delete button
	 * @throws Exception
	 */
	public void delete() throws Exception{
		List<WebElement> buttons = getWebElement(By.className("wfAddActionButtonRow")).findElement(By.id("deleteButtonDiv")).findElements(By.cssSelector("span[class='dijitReset dijitInline dijitButtonText']"));
		for(WebElement button : buttons){
			if(button.getText().trim().equals(getLocalizedString("delete"))){
				button.click();
				switchToAlert().accept();
				break;
			}
		}
	}

	/**
	 * Add a subaction 
	 * @param worflowSubaction Name of the subaction
	 * @throws Exception
	 */
	public void addSubAction(String worflowSubaction) throws Exception{
		wfActionlets.clear();
		wfActionlets.sendKeys(worflowSubaction);
		wfActionlets_popup0.click();

		List<WebElement> buttons = getWebElements(By.cssSelector("div[class='wfAddActionButtonRow']")).get(1).findElements(By.cssSelector("span[class='dijitReset dijitInline dijitButtonText']"));
		for(WebElement button : buttons){
			if(button.getText().trim().equals(getLocalizedString("Add-Workflow-SubAction"))){
				button.click();
				break;
			}
		}
	}
}
