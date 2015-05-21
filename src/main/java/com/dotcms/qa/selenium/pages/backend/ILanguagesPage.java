package com.dotcms.qa.selenium.pages.backend;

/**
 * Languages Page Interface
 * @author Oswaldo Gallango
 * @since 05/21/2015
 * @version 1.0
 *
 */
public interface ILanguagesPage {
	
	/**
	 * Add a new language to the dotcms
	 * @param langCode    Language code (2 character)
	 * @param countryCode Country code (2 character)
	 * @param language   Language name
	 * @param country    Country name
	 * @throws Exception
	 */
	public void addLanguage(String langCode, String countryCode, String language, String country) throws Exception;

	/**
	 * Validates if the language exist
	 * @param language  Language name
	 * @param countryCode Two digits country code
	 * @return true if exist, false if not
	 * @throws Exception
	 */
	public boolean doesLanguageExist(String language,String countryCode) throws Exception;
	
	/**
	 * Delete the specified language
	 * @param language Language name
	 * @param countryCode Two digits country code
	 * @throws Exception
	 */
	public void deleteLanguage(String language,String countryCode) throws Exception;
}
