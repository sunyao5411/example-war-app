/*******************************************************************************
 *
 * Copyright (c) 2001-2017 Primeton Technologies, Ltd.
 * All rights reserved.
 * 
 * Created on Jan 23, 2017 2:09:40 PM
 *******************************************************************************/

package org.gocome.devops.example.app.model;

/**
 * User. <br>
 *
 * @author ZhongWen Li (mailto: lizw@primeton.com)
 */
public class Employee {
	
	private String id;
	private String name;
	private String mail;
	private String password;
	private String gender;
	
	public Employee() {
		super();
		// Auto-generated constructor stub
	}

	public Employee(String id, String name, String mail, String password, String gender) {
		super();
		this.id = id;
		this.name = name;
		this.mail = mail;
		this.password = password;
		this.gender = gender;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}
	
}
