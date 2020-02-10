package com.tibco.cep.dashboard.psvr.text.url;

import java.util.Properties;

import com.tibco.cep.dashboard.common.data.Tuple;
import com.tibco.cep.dashboard.security.SecurityToken;
import com.tibco.cep.kernel.service.logging.Logger;

/**
 * @author anpatil
 * 
 */
public final class SubstitutionContext {

	public static final String REF_OPEN = "${";
	public static final String REF_DIVIDE = ":";
	public static final String REF_CLOSE = "}";

	private Properties properties;
	private Logger logger;
	private SecurityToken token;
	private Tuple tuple;
	
	public SubstitutionContext(Logger logger, SecurityToken token, Properties properties,Tuple tuple) {
		super();
		this.logger = logger;
		this.token = token;
		this.properties = properties;
		this.tuple = tuple;
	}
	
	public SecurityToken getToken() {
		return this.token;
	}

	public Tuple getTuple() {
		return this.tuple;
	}

	public Logger getLogger() {
		return logger;
	}
	
	public Properties getProperties() {
		return properties;
	}
	
}
