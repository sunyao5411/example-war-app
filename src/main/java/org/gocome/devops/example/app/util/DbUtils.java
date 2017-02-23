/*******************************************************************************
 *
 * Copyright (c) 2001-2016 Primeton Technologies, Ltd.
 * All rights reserved.
 * 
 * Created on Feb 23, 2017 9:28:19 AM
 *******************************************************************************/

package org.gocome.devops.example.app.util;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.reflect.MethodUtils;

/**
 * DbUtils.
 *
 * @author ZhongWen Li (mailto:lizw@primeton.com)
 */
public class DbUtils {
	
	private static final String DRIVER = "com.mysql.jdbc.Driver";
	private static final String URL = "jdbc:mysql://127.0.0.1:3306/test?useUnicode=true&characterEncoding=utf8&autoReconnect=true&autoReconnectForPools=true&failOverReadOnly=false";
	private static final String USER = "root";
	private static final String PASS = "root";
	
	private static int WAY_DEFAULT = 0;
	private static int WAY_OS_ENV = 1;
	private static int WAY_JVM_ENV = 2;
	private static int WAY_FILE = 3;
	
	private static final Properties DB_INFO = new Properties();
	
	static {
		try {
			Class.forName(DRIVER);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	protected static int getConfigWay() {
		InputStream input = DbUtils.class.getResourceAsStream("/META-INF/jdbc.properties");
		if (null != input) {
			try {
				DB_INFO.load(input);
				return WAY_FILE;
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				IOUtils.closeQuietly(input);
			}
		}
		if (StringUtils.isNotBlank(System.getProperty("jdbc.url"))) {
			DB_INFO.setProperty("jdbc.url", System.getProperty("jdbc.url"));
			DB_INFO.setProperty("jdbc.user", System.getProperty("jdbc.user"));
			DB_INFO.setProperty("jdbc.password", System.getProperty("jdbc.password"));
			DB_INFO.setProperty("jdbc.driver", System.getProperty("jdbc.driver"));
			return WAY_JVM_ENV;
		}
		if (StringUtils.isNotBlank(System.getenv("jdbc.url"))) {
			DB_INFO.setProperty("jdbc.url", System.getenv("jdbc.url"));
			DB_INFO.setProperty("jdbc.user", System.getenv("jdbc.user"));
			DB_INFO.setProperty("jdbc.password", System.getenv("jdbc.password"));
			DB_INFO.setProperty("jdbc.driver", System.getenv("jdbc.driver"));
			return WAY_OS_ENV;
		}
		return WAY_DEFAULT;
	}
	
	/**
	 * 
	 * @return
	 * @throws SQLException
	 */
	public static Connection getConnection() throws SQLException {
		if (WAY_DEFAULT == getConfigWay()) {
			return DriverManager.getConnection(URL, USER, PASS);
		}
		return DriverManager.getConnection(
				DB_INFO.getProperty("jdbc.url"), 
				DB_INFO.getProperty("jdbc.user"), 
				DB_INFO.getProperty("jdbc.password"));
	}
	
	/**
	 * 
	 * @param closeables
	 */
	public static void closeQuietly(Object ... closeables) {
		if (null == closeables || 0 == closeables.length) {
			return;
		}
		for (Object closeable : closeables) {
			if (null == closeable) {
				continue;
			}
			if (closeable instanceof Closeable) {
				IOUtils.closeQuietly((Closeable)closeable);
			} else {
				try {
					MethodUtils.invokeMethod(closeable, "close", null); //$NON-NLS-1$
				} catch (NoSuchMethodException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
}
