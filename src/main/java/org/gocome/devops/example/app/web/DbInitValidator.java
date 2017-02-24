/*******************************************************************************
 *
 * Copyright (c) 2001-2016 Primeton Technologies, Ltd.
 * All rights reserved.
 * 
 * Created on Feb 23, 2017 10:42:10 AM
 *******************************************************************************/

package org.gocome.devops.example.app.web;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.gocome.devops.example.app.util.DbInitUtils;

/**
 * DbInitValidator.
 *
 * @author ZhongWen Li (mailto:lizw@primeton.com)
 */
public class DbInitValidator implements ServletContextListener {
	
	

	/* (non-Javadoc)
	 * @see javax.servlet.ServletContextListener#contextInitialized(javax.servlet.ServletContextEvent)
	 */
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		// Auto-generated method stub
		if (!DbInitUtils.hasInitialized()) {
			DbInitUtils.initialize();
		}
	}

	/* (non-Javadoc)
	 * @see javax.servlet.ServletContextListener#contextDestroyed(javax.servlet.ServletContextEvent)
	 */
	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		// Auto-generated method stub

	}

}
