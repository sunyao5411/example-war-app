/*******************************************************************************
 *
 * Copyright (c) 2001-2016 Primeton Technologies, Ltd.
 * All rights reserved.
 * 
 * Created on Jan 22, 2017 4:51:34 PM
 *******************************************************************************/

package org.gocome.devops.example.app.resteasy;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.core.Application;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

/**
 * RestEasyApplication.
 *
 * @author ZhongWen Li (mailto:lizw@primeton.com)
 */
public class RestEasyApplication extends Application {
	
	private static Logger logger = Logger.getLogger(RestEasyApplication.class);
	
	private Set<Class<?>> application = new HashSet<>();

	/* (non-Javadoc)
	 * @see javax.ws.rs.core.Application#getClasses()
	 */
	@Override
	public Set<Class<?>> getClasses() {
		if (null == application || application.isEmpty()) {
			init();
		}
		return application;
	}
	
	protected void init() {
		application = new HashSet<>();
		InputStream input = null;
		try {
			input = RestEasyApplication.class.getResourceAsStream("/META-INF/resteasy.txt");
			if (null == input) {
				return;
			}
			String content = IOUtils.toString(input, "utf-8");
			if (StringUtils.isNotBlank(content)) {
				String[] classes = content.split(",");
				for (String clazz : classes) {
					if (StringUtils.isNotEmpty(clazz)) {
						clazz = clazz.trim();
						try {
							application.add(Class.forName(clazz));
							System.err.println(String.format("Load RESTful api implements %s.", clazz));
						} catch (ClassNotFoundException e) {
							logger.error(e);
						}
					}
				}
			}
		} catch (IOException e) {
			logger.error(e);
		} finally {
			IOUtils.closeQuietly(input);
		}
		
	}

}
