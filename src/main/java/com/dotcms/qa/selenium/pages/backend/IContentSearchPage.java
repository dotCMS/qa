package com.dotcms.qa.selenium.pages.backend;

import com.dotcms.qa.selenium.pages.IBasePage;

public interface IContentSearchPage extends IBasePage {
	public IContentAddOrEdit_ContentPage getAddContentPage(String structureName) throws Exception;
	
	/**
	 * Click the add new content button
	 * @param Structure Structure Name
	 * @return IContentAddOrEdit_ContentPage
	 * @throws Exception
	 */
	public IContentAddOrEdit_ContentPage addContent(String structure) throws Exception;
	
	/**
	 * Edit a content
	 * @param contentName Contentlet name
	 * @param structure Contentlet structure
	 * @return IContentAddOrEdit_ContentPage
	 * @throws Exception
	 */
	public IContentAddOrEdit_ContentPage editContent(String contentName, String structure) throws Exception;
	
	/**
	 * Push the specified contentlet
	 * @param contentName Contentlet name
	 * @param structure Contentlet structure
	 * @throws Exception
	 */
	public void pushContent(String contentName, String structure) throws Exception;
	
	/**
	 * validate if the content exist
	 * @param contentName Contentlet name
	 * @param structure Contentlet structure
	 * @return true if exist, false if not
	 * @throws Exception
	 */
	public boolean doesContentExist(String contentName, String structure) throws Exception;
	
	/**
	 * unpublish a content 
	 * @param contentName Contentlet name
	 * @param structure Contentlet structure
	 * @throws Exception
	 */
	public void unpublish(String contentName, String structure) throws Exception;
	
	/**
	 * archive a content 
	 * @param contentName Contentlet name
	 * @param structure Contentlet structure
	 * @throws Exception
	 */
	public void archive(String contentName, String structure) throws Exception;
	
	/**
	 * delete a content 
	 * @param contentName Contentlet name
	 * @param structure Contentlet structure
	 * @throws Exception
	 */
	public void delete(String contentName, String structure) throws Exception;
	
	/**
	 * publish a content 
	 * @param contentName Contentlet name
	 * @param structure Contentlet structure
	 * @throws Exception
	 */
	public void publish(String contentName, String structure) throws Exception;
	
	/**
	 * unarchive a content 
	 * @param contentName Contentlet name
	 * @param structure Contentlet structure
	 * @throws Exception
	 */
	public void unArchive(String contentName, String structure) throws Exception;
	
	/**
	 * Validate is the content is archived 
	 * @param contentName Contentlet name
	 * @param structure Contentlet structure
	 * @return true if exist, false if not
	 * @throws Exception
	 */
	public boolean isArchive(String contentName, String structure) throws Exception;
	
	/**
	 * Validate is the content is unpublished 
	 * @param contentName Contentlet name
	 * @param structure Contentlet structure
	 * @return true if exist, false if not
	 * @throws Exception
	 */
	public boolean isUnpublish(String contentName, String structure) throws Exception;
	
	/**
	 * Validate is the content is published 
	 * @param contentName Contentlet name
	 * @param structure Contentlet structure
	 * @return true if exist, false if not
	 * @throws Exception
	 */
	public boolean isPublish(String contentName, String structure) throws Exception;
}
