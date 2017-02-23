/*******************************************************************************
 *
 * Copyright (c) 2001-2016 Primeton Technologies, Ltd.
 * All rights reserved.
 * 
 * Created on Feb 23, 2017 9:28:19 AM
 *******************************************************************************/

package org.gocome.devops.example.app.util;

import java.io.Closeable;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.apache.commons.io.IOUtils;
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
	
	static {
		try {
			Class.forName(DRIVER);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 * @return
	 * @throws SQLException
	 */
	public static Connection getConnection() throws SQLException {
		return DriverManager.getConnection(URL, USER, PASS);
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
