package com.dotcms.qa.selenium.pages.backend.common;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.dotcms.qa.selenium.pages.backend.IWorkflowSchemeAddOrEditPage;
import com.dotcms.qa.selenium.pages.common.BasePage;

/**
 * This class implements the methods defined in the IWorkflowSchemeAddOrEditPage interface
 * @author Oswaldo Gallango
 * @since 04/30/2015
 * @version 1.0
 * 
 */
public class WorkflowSchemeAddOrEditPage extends BasePage implements IWorkflowSchemeAddOrEditPage{

	private WebElement addEditSchemeForm;
	private WebElement schemeName;
	private WebElement schemeDescription;
	private WebElement schemeArchived;
	
	public WorkflowSchemeAddOrEditPage(WebDriver driver) {
		super(driver);
	} 
	
	/**
	 * Set the scheme name
	 * @param name Scheme name
	 * @throws Exception
	 */
	public void setName(String name) throws Exception{
		schemeName.clear();
		schemeName.sendKeys(name);
	}
	
	/**
	 * Get the scheme name
	 * @return String
	 * @throws Exception
	 */
	public String getName() throws Exception{
		return schemeName.getAttribute("value");
	}
	
	/**
	 * Set the scheme description
	 * @param description Scheme description
	 * @throws Exception
	 */
	public void setDescription(String description) throws Exception{
		schemeDescription.clear();
		schemeDescription.sendKeys(description);
	}
	
	/**
	 * Get the scheme description
	 * @return String
	 * @throws Exception
	 */
	public String getDescription() throws Exception{
		return schemeDescription.getAttribute("value");
	}
	
	/**
	 * Set the scheme as archived or not
	 * @param archive archive status
	 * @throws Exception
	 */
	public void setArchive(boolean archive) throws Exception{
		if(schemeArchived.isSelected() && !archive){
			schemeArchived.click();
		}else{
			schemeArchived.click();
		}
	}
	
	/**
	 * Return if the scheme is marked as archived
	 * @return boolean
	 * @throws Exception
	 */
	public boolean isArchived() throws Exception{
		return schemeArchived.isSelected();
	}
	
	/**
	 * Click the save button
	 * @param archive archive status
	 * @throws Exception
	 */
	public void save() throws Exception{
		List<WebElement> buttons = addEditSchemeForm.findElements(By.cssSelector("span[class='dijitReset dijitInline dijitButtonText']"));
		for(WebElement button : buttons){
			if(button.getText().trim().equals(getLocalizedString("save"))){
				button.click();
				break;
			}
		}
	}
	
	/**
	 * Click the cancel button
	 * @param archive archive status
	 * @throws Exception
	 */
	public void cancel() throws Exception{
		List<WebElement> buttons = addEditSchemeForm.findElements(By.cssSelector("span[class='dijitReset dijitInline dijitButtonText']"));
		for(WebElement button : buttons){
			if(button.getText().trim().equals(getLocalizedString("cancel"))){
				button.click();
				break;
			}
		}
	}
	
	
	

}
