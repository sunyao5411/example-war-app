/*******************************************************************************
 *
 * Copyright (c) 2001-2017 Primeton Technologies, Ltd.
 * All rights reserved.
 * 
 * Created on Jan 23, 2017 2:12:37 PM
 *******************************************************************************/

package org.gocome.devops.example.app.api;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.apache.commons.lang.StringUtils;
import org.gocome.devops.example.app.exception.ApiException;
import org.gocome.devops.example.app.model.Employee;
import org.gocome.devops.example.app.util.DbUtils;

/**
 * EmployeeApi. <br>
 *
 * @author ZhongWen Li (mailto: lizw@primeton.com)
 */
@Path(URL.PREFIX + "/employees")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class EmployeeApi {
	
	private static String SQL_INSERT = "INSERT INTO `employee` (`no`, `name`, `mail`, `password`, `phone`, `gender`) VALUES (?, ?, ?, ?, ?, ?)";
	
	private static String SQL_UPDATE = "UPDATE `employee` SET `no` = ?, `name` = ?, `password` = ?, `phone` = ?, `gender` = ? WHERE `mail` = ?";

	private static String SQL_DELETE = "DELETE FROM `employee` WHERE `mail` = ?";
	
	private static String SQL_SELECT = "SELECT `id`, `no`, `name`, `mail`, `password`, `phone`, `gender` FROM `employee` WHERE `mail` = ?";
	
	private static String SQL_QUERY = "SELECT `id`, `no`, `name`, `mail`, `password`, `phone`, `gender` FROM `employee`";
	
	@POST
	public Employee add(Employee employee) throws ApiException {
		if (null == employee || StringUtils.isBlank(employee.getMail())) {
			return null;
		}
		if (null != get(employee.getMail())) {
			throw new ApiException("Email {0} already in use.", employee.getMail());
		}
		Connection connection = null;
		PreparedStatement stmt = null;
		try {
			connection = DbUtils.getConnection();
			stmt = connection.prepareStatement(SQL_INSERT);
			stmt.setString(1, employee.getNo());
			stmt.setString(2, employee.getName());
			stmt.setString(3, employee.getMail());
			stmt.setString(4, employee.getPassword());
			stmt.setString(5, employee.getPhone());
			stmt.setString(6, employee.getGender());
			return stmt.execute() ? employee : null;
		} catch (SQLException e) {
			throw new ApiException(e);
		} finally {
			DbUtils.closeQuietly(stmt, connection);
		}
	}
	
	@PUT
	public Employee update(Employee employee) throws ApiException {
		if (null == employee || StringUtils.isBlank(employee.getMail())) {
			return null;
		}
		Connection connection = null;
		PreparedStatement stmt = null;
		try {
			connection = DbUtils.getConnection();
			stmt = connection.prepareStatement(SQL_UPDATE);
			stmt.setString(1, employee.getNo());
			stmt.setString(2, employee.getName());
			stmt.setString(3, employee.getPassword());
			stmt.setString(4, employee.getPhone());
			stmt.setString(5, employee.getGender());
			stmt.setString(6, employee.getMail());
			return stmt.execute() ? employee : null;
		} catch (SQLException e) {
			throw new ApiException(e);
		} finally {
			DbUtils.closeQuietly(stmt, connection);
		}
	}
	
	@Path("/{mail}")
	@GET
	public Employee get(@PathParam("mail") String mail) throws ApiException {
		if (StringUtils.isBlank(mail)) {
			return null;
		}
		Connection connection = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			connection = DbUtils.getConnection();
			stmt = connection.prepareStatement(SQL_SELECT);
			stmt.setString(1, mail);
			rs = stmt.executeQuery();
			if (rs.next()) {
				Employee employee = new Employee();
				employee.setId(rs.getString(1));
				employee.setNo(rs.getString(2));
				employee.setName(rs.getString(3));
				employee.setMail(rs.getString(4));
				employee.setPassword(rs.getString(5));
				employee.setPhone(rs.getString(6));
				employee.setGender(rs.getString(7));
				return employee;
			}
		} catch (SQLException e) {
			throw new ApiException(e);
		} finally {
			DbUtils.closeQuietly(rs, stmt, connection);
		}
		return null;
	}
	
	@GET
	public List<Employee> query() throws ApiException {
		List<Employee> results = new ArrayList<>();
		Connection connection = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			connection = DbUtils.getConnection();
			stmt = connection.prepareStatement(SQL_QUERY);
			rs = stmt.executeQuery();
			while (rs.next()) {
				Employee employee = new Employee();
				employee.setId(rs.getString(1));
				employee.setNo(rs.getString(2));
				employee.setName(rs.getString(3));
				employee.setMail(rs.getString(4));
				employee.setPassword(rs.getString(5));
				employee.setPhone(rs.getString(6));
				employee.setGender(rs.getString(7));
				
				results.add(employee);
			}
		} catch (SQLException e) {
			throw new ApiException(e);
		} finally {
			DbUtils.closeQuietly(rs, stmt, connection);
		}
		return results;
	}
	
	@Path("/{mail}")
	@DELETE
	public Employee remove(@PathParam("mail") String mail) throws ApiException {
		if (StringUtils.isBlank(mail)) {
			return null;
		}
		Employee employee = get(mail);
		if (null == employee) {
			return null;
		}
		Connection connection = null;
		PreparedStatement stmt = null;
		try {
			connection = DbUtils.getConnection();
			stmt = connection.prepareStatement(SQL_DELETE);
			stmt.setString(1, employee.getMail());
			return stmt.execute() ? employee : null;
		} catch (SQLException e) {
			throw new ApiException(e);
		} finally {
			DbUtils.closeQuietly(stmt, connection);
		}
	}

	@Path("/signin")
	@PUT
	public boolean signin(Employee employee, @Context HttpServletRequest request) throws ApiException {
		if (null == request.getSession().getAttribute(Employee.class.getSimpleName())) {
			Employee e = get(employee.getMail());
			if (null == e) {
				return false;
			}
			if (null != employee && StringUtils.equals(employee.getMail(), e.getMail())
					&& StringUtils.equals(employee.getPassword(), e.getPassword())) {
				request.getSession().setAttribute(Employee.class.getSimpleName(), employee);
				return true;
			}
		}
		return true;
	}
	
	@Path("/signout")
	@PUT
	public boolean signout(@Context HttpServletRequest request) throws ApiException {
		request.getSession().removeAttribute(Employee.class.getSimpleName());
		return true;
	}
	
}
