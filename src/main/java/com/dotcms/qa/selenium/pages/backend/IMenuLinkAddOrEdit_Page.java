package com.dotcms.qa.selenium.pages.backend;

import com.dotcms.qa.selenium.pages.IBasePage;

/**
 * Menu Link Add or Edit Interface
 * @author Oswaldo Gallango
 * @since 04/14/2015
 * @version 1.0
 */
public interface IMenuLinkAddOrEdit_Page extends IBasePage{

	public static final String INTERNAL_LINK ="internal"; 
	public static final String EXTERNAL_LINK ="external"; 
	public static final String CODE_LINK ="code"; 

	public static final String SAME_TARGET="Same Window";
	public static final String NEW_TARGET="New Window";
	public static final String PARENT_TARGET="Parent Window";

	public static final String HTTP_PROTOCOL="http://";
	public static final String HTTPS_PROTOCOL="https://";
	public static final String FTP_TARGET="ftp://";
	public static final String MAILTO_TARGET="mailto:";
	public static final String JAVASCRIPT_TARGET="javascript:";
	
	/**
	 * Set Menu link title
	 * @param title Title
	 * @throws Exception
	 */
	public void setLinkTitle(String title) throws Exception;
	
	/**
	 * Get Menu Link Title
	 */
	public String getLinkTitle() throws Exception;
	
	/**
	 * Set Menu Link folder
	 * @param folderName
	 * @throws Exception
	 */
	public void setLinkFolder(String folderName) throws Exception;
	
	/**
	 * Get Menu Link Folder
	 * @return String
	 * @throws Exception
	 */
	public String getLinkFolder() throws Exception;
	
	/**
	 * Set Menu Link type: internal,external or code link
	 * @param type (accepted values: internal,external or code)
	 * @throws Exception
	 */
	public void  setLinkType(String type) throws Exception;
	
	/**
	 * Get Menu Link Type
	 * @return String
	 * @throws Exception
	 */
	public String getLinkType() throws Exception;
	
	/**
	 * Set Menu Link order
	 * @param position
	 * @throws Exception
	 */
	public void  setLinkOrder(int position) throws Exception;
	
	/**
	 * Get Menu Link order
	 * @return String
	 * @throws Exception
	 */
	public String getLinkOrder() throws Exception;
	
	/**
	 * Set Menu Link show on menu
	 * @param position
	 * @throws Exception
	 */
	public void  setLinkShowOnMenu(boolean showOnMenu) throws Exception;
	
	/**
	 * Get if Menu Link is show on menu
	 * @return String
	 * @throws Exception
	 */
	public boolean isLinkShowOnMenu() throws Exception;
	
	/**
	 * Set Menu Link target: same, new or parent window
	 * @param type (accepted values: same, new or parent)
	 * @throws Exception
	 */
	public void  setLinkTarget(String type) throws Exception;
	
	/**
	 * Get Menu Link target
	 * @return String
	 * @throws Exception
	 */
	public String getLinkTarget() throws Exception;
	
	/**
	 * Set the link if the menu link type is a code link
	 * @param code
	 * @throws Exception
	 */
	public void setLinkCode(String code) throws Exception;
	
	/**
	 * Get the link if the link type is code link
	 * @return String
	 * @throws Exception
	 */
	public String getLinkCode() throws Exception;
	
	/**
	 * Set the link if the menu link type is external link
	 * @param protocol valids protocol http://, https://, ftp://, mailto: or javascript:
	 * @param url  Url path
	 * @throws Exception
	 */
	public void setLinkExternalCode(String protocol, String url) throws Exception;
	
	/**
	 * Get the link  if the link type is code link
	 * @return String
	 * @throws Exception
	 */
	public String getLinkExternalCode() throws Exception;
	
	/**
	 * Set the link if the menu link type is internal link
	 * @param host  Host name
	 * @param folder Folder name
	 * @param url Url path
	 * @throws Exception
	 */
	public void setLinkInternalCode(String host, String folder, String url) throws Exception;
	
	/**
	 * Get the link  if the link type is internal link
	 * @return String
	 * @throws Exception
	 */
	public String getLinkInternalCode() throws Exception;
	
	/**
	 * Click the save and publish button
	 */
	public void saveAndPublish() throws Exception;
	
	/**
	 * Click the save button
	 */
	public void save() throws Exception;
	
	/**
	 * Click the cancel button
	 */
	public void cancel() throws Exception;
}
