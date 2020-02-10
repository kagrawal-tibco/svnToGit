package com.tibco.cep.runtime.service.cluster;

import java.util.Properties;

import com.tibco.cep.common.exception.LifecycleException;

public interface LifeCycleService {
	
	void init (Properties props, Object ...objects) throws Exception;
	
	void start() throws LifecycleException;
	
	void stop() throws LifecycleException;
}
