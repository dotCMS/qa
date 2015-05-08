package com.dotcms.plugin.rest.qaautomation;

import java.net.URISyntaxException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.dotcms.repackage.javax.ws.rs.GET;
import com.dotcms.repackage.javax.ws.rs.POST;
import com.dotcms.repackage.javax.ws.rs.Path;
import com.dotcms.repackage.javax.ws.rs.PathParam;
import com.dotcms.repackage.javax.ws.rs.Produces;
import com.dotcms.repackage.javax.ws.rs.core.Context;
import com.dotcms.repackage.javax.ws.rs.core.Response;
import com.dotcms.repackage.javax.ws.rs.core.Response.ResponseBuilder;
import com.dotcms.rest.InitDataObject;
import com.dotcms.rest.WebResource;
import com.dotmarketing.common.db.DotConnect;
import com.dotmarketing.util.Logger;

/**
 * This class control the delete methods used by the qa automation test
 * (https://github.com/dotCMS/qa) for dotCMS 3.0
 * @author Oswaldo Gallango
 *
 */
@Path("/qa_automation")
public class QAAutomationResource extends WebResource {

	/**
	 * Delete the specified user from db
	 * @param request
	 * @param params
	 * @return Response
	 * @throws URISyntaxException
	 */
	@GET
	@Path("/user/delete/{params:.*}")
	@Produces("application/json")
	public Response deleteUser(@Context HttpServletRequest request, @PathParam("params") String params) throws URISyntaxException {
		InitDataObject initData = init(params, true, request, true);
		Map<String, String> paramsMap = initData.getParamsMap();
		String userId = paramsMap.get("userid");
		DotConnect db = new DotConnect();
		Logger.info( this.getClass(), "Received request to delete user:"+userId );
		boolean deleted= false;
		try{
			db.executeStatement("update inode set owner = 'dotcms.org.1' where owner = '"+userId+"'");
			db.executeStatement("update contentlet set mod_user =  'dotcms.org.1' where mod_user  = '"+userId+"'");
			db.executeStatement("update file_asset set mod_user =  'dotcms.org.1' where mod_user  = '"+userId+"'");
			db.executeStatement("update containers set mod_user =  'dotcms.org.1' where mod_user  = '"+userId+"'");
			db.executeStatement("update template set mod_user =  'dotcms.org.1' where mod_user  = '"+userId+"'");
			db.executeStatement("update htmlpage set mod_user =  'dotcms.org.1' where mod_user  = '"+userId+"'");
			db.executeStatement("update links set mod_user =  'dotcms.org.1' where mod_user  = '"+userId+"'");
			db.executeStatement("update contentlet_version_info set locked_by='dotcms.org.1' where locked_by  = '"+userId+"'");
			db.executeStatement("update htmlpage_version_info set locked_by='dotcms.org.1' where locked_by  = '"+userId+"'");
			db.executeStatement("update container_version_info set locked_by='dotcms.org.1' where locked_by  = '"+userId+"'");
			db.executeStatement("update fileasset_version_info set locked_by='dotcms.org.1' where locked_by  = '"+userId+"'");
			db.executeStatement("update template_version_info set locked_by='dotcms.org.1' where locked_by  = '"+userId+"'");
			db.executeStatement("update link_version_info set locked_by='dotcms.org.1' where locked_by  = '"+userId+"'");
			db.executeStatement("update workflow_task set created_by='dotcms.org.1' where created_by  = '"+userId+"'");
			db.executeStatement("update workflow_task set assigned_to='dotcms.org.1' where assigned_to  = '"+userId+"'");
			db.executeStatement("update workflow_comment set posted_by='dotcms.org.1' where posted_by  = '"+userId+"'");
			db.executeStatement("delete from permission where roleid in (select id from cms_role where role_key='"+userId+"')");
			db.executeStatement("delete from users_cms_roles where user_id= '"+userId+"'");
			db.executeStatement("delete from cms_role where role_key='"+userId+"'");
			db.executeStatement("delete from user_ where userid = '"+userId+"'");
			deleted=true;
		} catch(Exception e){
			Logger.error(QAAutomationResource.class,"ERROR - User could not be deleted. UserId:"+userId,e);
		}
		ResponseBuilder builder = Response.ok("{\"result\":\"" + deleted + " POST!\"}", "application/json");
		return builder.build();
	}

	/**
	 * Remove the specified tag from db
	 * @param request
	 * @param params
	 * @return
	 * @throws URISyntaxException
	 */
	@GET
	@Path("/tag/delete/{params:.*}")
	@Produces("application/json")
	public Response deleteTag(@Context HttpServletRequest request, @PathParam("params") String params) throws URISyntaxException {
		InitDataObject initData = init(params, true, request, true);
		Map<String, String> paramsMap = initData.getParamsMap();
		String tagName = paramsMap.get("tagname");
		DotConnect db = new DotConnect();
		Logger.info( this.getClass(), "Received request to delete tag:"+tagName );
		boolean deleted= false;
		try{
			db.executeStatement("delete from tag_inode where tag_id in (select tag_id from tag where tagname like '"+tagName+"%')");
			db.executeStatement("delete from tag where tagname like '"+tagName+"%'");
			deleted= true;
		} catch(Exception e){
			Logger.error(QAAutomationResource.class,"ERROR - Tag could not be deleted. Tag:"+tagName,e);

		}

		ResponseBuilder builder = Response.ok("{\"result\":\"" + deleted + " POST!\"}", "application/json");
		return builder.build();
	}
	
	/**
	 * Delete the specified workflow scheme from db
	 * @param request
	 * @param params
	 * @return Response
	 * @throws URISyntaxException
	 */
	@GET
	@Path("/workflows/delete/{params:.*}")
	@Produces("application/json")
	public Response deleteWorkflows(@Context HttpServletRequest request, @PathParam("params") String params) throws URISyntaxException {
		InitDataObject initData = init(params, true, request, true);
		Map<String, String> paramsMap = initData.getParamsMap();
		String workflowName = paramsMap.get("name");
		DotConnect db = new DotConnect();
		Logger.info( this.getClass(), "Received request to delete workflow:"+workflowName );
		boolean deleted= false;
		try{
			db.executeStatement("delete from workflow_scheme where name='"+workflowName+"'");
			deleted=true;
		} catch(Exception e){
			Logger.error(QAAutomationResource.class,"ERROR - Workflow Scheme could not be deleted. Workflow name:"+workflowName,e);
		}
		ResponseBuilder builder = Response.ok("{\"result\":\"" + deleted + " POST!\"}", "application/json");
		return builder.build();
	}

}