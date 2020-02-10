package com.tibco.cep.bemm.management.service.impl;

import com.tibco.cep.bemm.common.exception.ObjectCreationException;
import com.tibco.cep.bemm.common.message.impl.MessageKey;
import com.tibco.cep.bemm.common.service.BEMMServiceProviderManager;
import com.tibco.cep.bemm.management.service.TransportService;
import com.tibco.cep.bemm.model.ServiceInstance;

/**
 * This transport service uses ssh to perform the operations defined in
 * TransportService
 * 
 * @author dijadhav
 *
 */
public class SshTransportServiceImpl implements TransportService {

	@Override
	public String deploy(ServiceInstance serviceInstance, String loggedInUser) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String undeploy(ServiceInstance serviceInstance, String loggedInUser) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String start(ServiceInstance serviceInstance, String loggedInUser) {
		return null;
	}

	@Override
	public String stop(ServiceInstance instance, String loggedInUser) throws ObjectCreationException {
		throw new UnsupportedOperationException(BEMMServiceProviderManager.getInstance().getMessageService().getMessage(MessageKey.OPERATION_NOT_SUPORTED_SSH_TRANSPORT));
	}

}
