package com.dotcms.qa.selenium.pages.backend.common;

import java.io.File;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.dotcms.qa.selenium.pages.backend.IContentImport_ContentPage;
import com.dotcms.qa.selenium.pages.common.BasePage;
import com.dotcms.qa.util.Evaluator;
import com.dotcms.qa.util.WebKeys;

public class ContentImport_ContentPage extends BasePage implements IContentImport_ContentPage{

	private WebElement structuresSelect;
	private WebElement structuresSelect_popup0;
	private WebElement goToPreviewButton;
	
	public ContentImport_ContentPage(WebDriver driver) {
		super(driver);
	}
	
	/**
	 * set the structure of the content to be imported
	 * @param structureName Structure Name
	 * @throws Exception
	 */
	public void setStructure(String structureName) throws Exception{
		structuresSelect.clear();
		structuresSelect.sendKeys(structureName);
		structuresSelect_popup0.click();		
	}
	
	/**
	 * set the csv file to import
	 * @param usersFilePath  Path to csv file
	 * @throws Exception
	 */
	public void setFile(String usersFilePath) throws Exception{
		String path = System.getProperty("user.dir");
		File file = new File(path+usersFilePath);
		if(getBrowserName().equals(WebKeys.SAFARI_BROWSER_NAME)){
			WebElement elem = getWebElement(By.xpath("//input[@type='file']"));
			elem.sendKeys(file.getAbsolutePath());
		}else{
			getWebElement(By.cssSelector("input[type='file'][name='file']")).sendKeys(file.getAbsolutePath());
		}
	}
	
	/**
	 * Click the go to preview button
	 */
	public void gotToPreview() throws Exception{
		goToPreviewButton.click();
	}
	
	/**
	 * Click the import content button
	 * @throws Exception
	 */
	public void importContent() throws Exception{
		getWebElement(By.id("proceedButton_label")).click();
		switchToAlert().accept();
		Evaluator eval = new Evaluator() {
			public boolean evaluate() throws Exception {  // returns true if the button is present
				boolean isButtonLoaded =false;
				try{
					WebElement button = getWebElement(By.id("goBackButton_label"));
					isButtonLoaded = button!= null;
				}catch(Exception e){
					//buton is not loaded yet
				}
				return isButtonLoaded;
			}
		};
		//wait just until 5 minutes
		if(pollForValue(eval, true, 5000, 60)){ 
			getWebElement(By.id("goBackButton_label")).click();
		}
	}

}
