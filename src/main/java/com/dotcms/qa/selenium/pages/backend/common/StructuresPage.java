package com.dotcms.qa.selenium.pages.backend.common;

import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

import com.dotcms.qa.selenium.pages.backend.*;
import com.dotcms.qa.selenium.pages.common.BasePage;
import com.dotcms.qa.selenium.util.SeleniumPageManager;
import com.dotcms.qa.util.language.LanguageManager;

public class StructuresPage extends BasePage implements IStructuresPage {
    private static final Logger logger = Logger.getLogger(StructuresPage.class);

	private WebElement dijit_form_ComboButton_0_label;

	@FindBy(how = How.CLASS_NAME, using = "listingTable")
    private WebElement tableOfStructures;
	
	public StructuresPage(WebDriver driver) {
		super(driver);
	}

	public void deleteStructureAndContent(String structureName, boolean confirm) throws Exception {
		WebElement structureElement = null;
		List<WebElement> rows = tableOfStructures.findElements(By.tagName("tr"));
		for(WebElement row : rows) {
			try {
				WebElement col = row.findElement(By.tagName("td"));
				if(col.getText().trim().equals(structureName)) {
					structureElement = col;
					break;
				}
			}
			catch(NoSuchElementException e) {
				logger.trace("Row does not include td element", e);
				// Move on to next row and keep going
			}
			catch(Exception e) {
				logger.error("Unexpected error attempting to iterate over structures - structureName =" + structureName, e);
				// Move on to next row and keep going
			}
		}
		
		if(structureElement == null) {
			throw new Exception("Unable to locate structure by name - structureName = " + structureName);
		}
		
		rightClickElement(structureElement);
		String menuString = LanguageManager.getValue("Delete-Structure-and-Content");
		WebElement popup = this.getWebElement(By.className("dijitMenuPopup"));
		List<WebElement> menuRows = popup.findElements(By.tagName("tr"));
		boolean done = false;
		for(WebElement menuRow : menuRows) {
			List<WebElement> menuCols = menuRow.findElements(By.tagName("td"));
			for(WebElement menuCol : menuCols) {
				if(menuCol.getText().trim().equals(menuString)) {
					menuCol.click();
					Alert alert = this.switchToAlert();
					if(confirm) {
						alert.accept();
					}
					else {
						alert.dismiss();
					}
					done = true;
					break;
				}
			}
			if(done)
				break;
		}
	}

	public boolean doesStructureExist(String structureName) {
		boolean retValue = false;
		return retValue;
	}

	public IStructureAddOrEdit_PropertiesPage getAddNewStructurePage() throws Exception {
		dijit_form_ComboButton_0_label.click();	// Add new structure button
		return SeleniumPageManager.getPageManager().getPageObject(IStructureAddOrEdit_PropertiesPage.class);
	}
}
