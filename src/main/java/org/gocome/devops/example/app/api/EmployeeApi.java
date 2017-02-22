/*******************************************************************************
 *
 * Copyright (c) 2001-2017 Primeton Technologies, Ltd.
 * All rights reserved.
 * 
 * Created on Jan 23, 2017 2:12:37 PM
 *******************************************************************************/

package org.gocome.devops.example.app.api;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.gocome.devops.example.app.exception.ApiException;
import org.gocome.devops.example.app.model.Employee;

/**
 * EmployeeApi. <br>
 *
 * @author ZhongWen Li (mailto: lizw@primeton.com)
 */
@Path(URL.PREFIX + "/employees")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class EmployeeApi {

	@POST
	public Employee add(Employee employee) throws ApiException {
		return employee;
	}
	
	@PUT
	public Employee update(Employee employee) throws ApiException {
		return employee;
	}
	
	@Path("/{id}")
	@GET
	public Employee get(@PathParam("id") String id) throws ApiException {
		return null;
	}
	
	@Path("/{id}")
	@DELETE
	public Employee remove(@PathParam("id") String id) throws ApiException {
		return null;
	}

	@Path("/signin")
	@PUT
	public Employee signin(Employee employee) throws ApiException {
		if (null != employee && "lizw@primeton.com".equals(employee.getMail())
				&& "000000".equals(employee.getPassword())) {
			Employee e = new Employee();
			e.setId("0848");
			e.setMail(employee.getMail());
			e.setName("ZhongWen Li");
			return e;
		}
		return null;
	}
	
}
