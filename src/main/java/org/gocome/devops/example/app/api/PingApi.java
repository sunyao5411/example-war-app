/*******************************************************************************
 *
 * Copyright (c) 2001-2016 Primeton Technologies, Ltd.
 * All rights reserved.
 * 
 * Created on Jan 22, 2017 5:11:23 PM
 *******************************************************************************/

package org.gocome.devops.example.app.api;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

/**
 * PingApi.
 *
 * @author ZhongWen Li (mailto:lizw@primeton.com)
 */
@Path(URL.PREFIX)
public class PingApi {
	
	@GET
	@Path("/ping")
	public String ping() {
		return "pong"; //$NON-NLS-1$
	}
}
