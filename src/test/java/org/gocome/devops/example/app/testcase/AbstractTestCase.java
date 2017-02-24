/*******************************************************************************
 *
 * Copyright (c) 2001-2016 Primeton Technologies, Ltd.
 * All rights reserved.
 * 
 * Created on Feb 24, 2017 3:11:34 PM
 *******************************************************************************/

package org.gocome.devops.example.app.testcase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * AbstractTestCase.
 *
 * @author ZhongWen Li (mailto:lizw@primeton.com)
 */
public abstract class AbstractTestCase {
	
	@Before
	public abstract void before() throws Exception;
	
	@Test
	public abstract void test() throws Exception;
	
	@After
	public abstract void after() throws Exception;
	
}
