package com.tibco.cep.bemm.common.handler.impl;

import java.util.Properties;

import com.tibco.cep.bemm.common.handler.ApplicationDataFeedHandler;
import com.tibco.cep.bemm.model.Application;
import com.tibco.cep.bemm.common.service.logging.LogManagerFactory;
import com.tibco.cep.bemm.common.service.logging.Logger;

/**
 * @author dijadhav
 *
 */
public class CoherenceDataFeedHandlerImpl implements ApplicationDataFeedHandler<Application> {
	private Logger logger = LogManagerFactory.getLogManager().getLogger(this.getClass());
	private Properties propertyParams;

	@Override
	public Application getTopologyData(Application application) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void init(Properties properties) {
		// TODO Auto-generated method stub

	}

	@Override
	public void connect(Application application) {
		//No-op
	}

	@Override
	public void disconnect(Application monitoredEntity) {
		// TODO Auto-generated method stub
		
	}

}
