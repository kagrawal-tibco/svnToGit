package com.tibco.cep.bemm.common.handler.impl;

import java.util.Properties;

import com.tibco.cep.bemm.common.exception.ObjectCreationException;
import com.tibco.cep.bemm.common.handler.ApplicationDataFeedHandler;
import com.tibco.cep.bemm.common.service.BEMMServiceProviderManager;
import com.tibco.cep.bemm.common.service.MBeanService;
import com.tibco.cep.bemm.common.service.logging.LogManagerFactory;
import com.tibco.cep.bemm.common.service.logging.Logger;
import com.tibco.cep.bemm.model.ServiceInstance;

/**
 * JMX Data feed handler
 * 
 * @author dijadhav
 *
 */
public class JMXDataFeedHandlerImpl implements ApplicationDataFeedHandler<ServiceInstance> {

	private static Logger LOGGER = LogManagerFactory.getLogManager().getLogger(JMXDataFeedHandlerImpl.class);
	/**
	 * Mbean Service Instance
	 */
	private MBeanService mbeanService;

	/**
	 * @param mbeanService
	 */
	public JMXDataFeedHandlerImpl() {
		super();
		try {
			this.mbeanService = BEMMServiceProviderManager.getInstance().getBEMBeanService();
		} catch (ObjectCreationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void init(Properties properties) {
		// TODO Auto-generated method stub
	}

	@Override
	public void connect(ServiceInstance serviceInstance) {
		// No-op
	}

	@Override
	public ServiceInstance getTopologyData(ServiceInstance serviceInstance) throws Exception {
		return mbeanService.getServiceInstanceTopologyData(serviceInstance);
	}

	@Override
	public void disconnect(ServiceInstance monitoredEntity) {
		// TODO
	}
}
