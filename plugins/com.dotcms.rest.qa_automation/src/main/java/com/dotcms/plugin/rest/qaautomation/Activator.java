package com.dotcms.plugin.rest.qaautomation;

import com.dotcms.plugin.rest.qaautomation.QAAutomationResource;

import com.dotcms.repackage.org.osgi.framework.BundleContext;
import com.dotcms.rest.config.RestServiceUtil;
import com.dotmarketing.osgi.GenericBundleActivator;
import com.dotmarketing.util.Logger;

/**
 * Rest Api for QA automation delete deletions for dotcms 3.0
 * @author Oswaldo Gallango
 *
 */
public class Activator extends GenericBundleActivator {

	Class clazz = QAAutomationResource.class;


	public void start(BundleContext context) throws Exception {

		Logger.info(this.getClass(), "Adding new Restful Service:" + clazz.getSimpleName());
		RestServiceUtil.addResource(clazz);

	}

	public void stop(BundleContext context) throws Exception {

		Logger.info(this.getClass(), "Removing new Restful Service:" + clazz.getSimpleName());
		RestServiceUtil.removeResource(clazz);

	}

}