package com.tibco.cep.dashboard.psvr.ogl;

import com.tibco.cep.dashboard.management.ManagementException;
import com.tibco.cep.dashboard.management.Service;

/**
 * @author apatil
 * 
 */
public class OGLService extends Service {

	public OGLService() {
		super("ogl", "Output Generation Layer");
	}

	@Override
	protected void doInit() throws ManagementException {
		addDependent(OGLMarshaller.getInstance());
		addDependent(OGLUnmarshaller.getInstance());
	}

}