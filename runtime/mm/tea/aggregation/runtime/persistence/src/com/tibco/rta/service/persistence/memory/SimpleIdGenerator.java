package com.tibco.rta.service.persistence.memory;

import com.tibco.rta.common.service.IdGenerator;
import com.tibco.rta.common.service.impl.AbstractStartStopServiceImpl;

import java.util.Properties;

@Deprecated
public class SimpleIdGenerator extends AbstractStartStopServiceImpl implements IdGenerator {
	
	long id = 1;

	@Override
	public void init(Properties configuration) throws Exception {
	}

	@Override
	public void start() throws Exception {
	}

	@Override
	public void stop() throws Exception {
	}
	
	@Override
	synchronized public long getId() {
		return id++;
	}

}
