/*******************************************************************************
 *
 * Copyright (c) 2001-2016 Primeton Technologies, Ltd.
 * All rights reserved.
 * 
 * Created on Feb 23, 2017 10:42:10 AM
 *******************************************************************************/

package org.gocome.devops.example.app.web;

import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.commons.io.IOUtils;
import org.gocome.devops.example.app.util.DbUtils;
import org.gocome.devops.example.app.util.ScriptRunner;

/**
 * DbInitValidator.
 *
 * @author ZhongWen Li (mailto:lizw@primeton.com)
 */
public class DbInitValidator implements ServletContextListener {
	
	private static String SQL = "SELECT COUNT(*) FROM `employee`";
	
	protected boolean hasInitialized() {
		Connection connection = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			connection = DbUtils.getConnection();
			stmt = connection.prepareStatement(SQL);
			rs = stmt.executeQuery();
			System.out.println("[" + new Date() + "] [INFO] Db initialized.");
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			System.err.println("[" + new Date() + "] [WARN] Db not initialized yet.");
			return false;
		} finally {
			DbUtils.closeQuietly(rs, stmt, connection);
		}
	}
	
	protected void initialize() {
		ScriptRunner runner = null;
		Reader reader = null;
		try {
			runner = new ScriptRunner(DbUtils.getConnection());
			runner.setAutoCommit(true);
			reader = new InputStreamReader(DbInitValidator.class.getResourceAsStream("/META-INF/mysql.sql"), "utf-8"); //$NON-NLS-1$

			runner.runScript(reader);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			IOUtils.closeQuietly(reader);
			if (null != runner) {
				runner.closeConnection();
			}
		}
	}

	/* (non-Javadoc)
	 * @see javax.servlet.ServletContextListener#contextInitialized(javax.servlet.ServletContextEvent)
	 */
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		// Auto-generated method stub
		if (!hasInitialized()) {
			initialize();
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
