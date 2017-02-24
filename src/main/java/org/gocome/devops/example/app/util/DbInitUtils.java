/*******************************************************************************
 *
 * Copyright (c) 2001-2016 Primeton Technologies, Ltd.
 * All rights reserved.
 * 
 * Created on Feb 24, 2017 4:25:56 PM
 *******************************************************************************/

package org.gocome.devops.example.app.util;

import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import org.apache.commons.io.IOUtils;
import org.gocome.devops.example.app.web.DbInitValidator;

/**
 * DbInitUtils.
 *
 * @author ZhongWen Li (mailto:lizw@primeton.com)
 */
public class DbInitUtils {

	private static String SQL = "SELECT COUNT(*) FROM `employee`";
	
	public static boolean hasInitialized() {
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
			//e.printStackTrace();
			System.err.println("[" + new Date() + "] [WARN] Db not initialized yet.");
			return false;
		} finally {
			DbUtils.closeQuietly(rs, stmt, connection);
		}
	}
	
	public static void initialize() {
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
	
}
