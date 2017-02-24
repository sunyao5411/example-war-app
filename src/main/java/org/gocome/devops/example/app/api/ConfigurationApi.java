/*******************************************************************************
 *
 * Copyright (c) 2001-2016 Primeton Technologies, Ltd.
 * All rights reserved.
 * 
 * Created on Feb 23, 2017 5:13:11 PM
 *******************************************************************************/

package org.gocome.devops.example.app.api;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;
import java.util.Properties;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.commons.io.IOUtils;
import org.gocome.devops.example.app.exception.ApiException;
import org.gocome.devops.example.app.model.JdbcConfiguration;
import org.gocome.devops.example.app.util.DbInitUtils;
import org.gocome.devops.example.app.util.DbUtils;

/**
 * ConfigurationApi.
 *
 * @author ZhongWen Li (mailto:lizw@primeton.com)
 */
@Path(URL.PREFIX + "/configurations")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ConfigurationApi {
	
	@Path("/jdbc")
	@GET
	public JdbcConfiguration getJdbcConfiguration() throws ApiException {
		if (DbUtils.WAY == DbUtils.WAY_DEFAULT) {
			return new JdbcConfiguration(DbUtils.URL, DbUtils.USER, DbUtils.PASS);
		}
		return new JdbcConfiguration(DbUtils.DB_INFO.getProperty("jdbc_url"), DbUtils.DB_INFO.getProperty("jdbc_user"),
				DbUtils.DB_INFO.getProperty("jdbc_password"));
	}
	
	@Path("/jdbc")
	@PUT
	public boolean setJdbcConfiguration(JdbcConfiguration configuration) throws ApiException {
		DbUtils.DB_INFO.setProperty("jdbc_url", configuration.getUrl());
		DbUtils.DB_INFO.setProperty("jdbc_user", configuration.getUser());
		DbUtils.DB_INFO.setProperty("jdbc_password", configuration.getPassword());
		
		OutputStream out = null;
		File f = new File ("/tmp/jdbc.properties");
		try {
			if (f.exists()) {
				f.delete();
			}
			f.createNewFile();
			out = new FileOutputStream(f);
			DbUtils.DB_INFO.store(out, "# Auto save");
			DbUtils.WAY = DbUtils.WAY_LOCAL_FILE;
		} catch (IOException e) {
			throw new ApiException(e);
		} finally {
			IOUtils.closeQuietly(out);
		}
		return true;
	}
	
	@Path("/jdbc/init")
	@PUT
	public boolean initialize() throws ApiException {
		DbInitUtils.initialize();
		return true;
	}
	
	@Path("/systems")
	@GET
	public Properties getSystemProperties() {
		return System.getProperties();
	}
	
	@Path("/environments")
	@GET
	public Map<String, String> getEnvironments() {
		return System.getenv();
	}

}
