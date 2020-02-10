package com.tibco.cep.ws.util.wsdlimport.context.impl;

import java.util.Properties;

import com.tibco.cep.ws.util.wsdlimport.context.ImportContext;


public abstract class AbstractImportContext
		implements ImportContext {

	
	private final Properties properties = new Properties();


	@Override
	public Properties getProperties()
	{
		return this.properties;
	}


}
