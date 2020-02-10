package com.tibco.cep.bemm.common.service.impl;

import java.util.regex.Pattern;

import com.tibco.cep.bemm.common.exception.ObjectCreationException;
import com.tibco.cep.bemm.common.message.impl.MessageKey;
import com.tibco.cep.bemm.common.service.BEMMServiceProviderManager;
import com.tibco.cep.bemm.common.service.MessageService;
import com.tibco.cep.bemm.common.service.ValidationService;
import com.tibco.cep.bemm.management.exception.BEValidationException;
import com.tibco.cep.bemm.model.Application;
import com.tibco.cep.bemm.model.Host;
import com.tibco.cep.bemm.model.ServiceInstance;
import com.tibco.rta.common.service.impl.AbstractStartStopServiceImpl;

/**
 * Implements the functionality defined in ValidationService interface.
 * 
 * @author dijadhav
 *
 */
public class ValidationServiceImpl extends AbstractStartStopServiceImpl implements ValidationService {
	private MessageService messageService;
	public static final String _255 = "(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)";
	public static final Pattern pattern = Pattern
			.compile("^(?:" + _255 + "\\.){3}" + _255 + "$|((l|L)(o|O)(c|C)(a|A)(l|L)(h|H)(o|O)(s|S)(t|T))");

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.tibco.tea.agent.be.services.ValidationService#isRemoteFileExist(java
	 * .lang.String)
	 */
	@Override
	public boolean isNotNullAndEmpty(String str) {
		return null != str && !str.trim().isEmpty() ? true : false;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.tibco.tea.agent.be.services.ValidationService#isIPAddressValid(java
	 * .lang.String)
	 */
	@Override
	public boolean isIPAddressValid(String ipAddress) {
		return Pattern.matches(_255, ipAddress);
	}

	@Override
	public void vaidateServiceInstance(ServiceInstance serviceInstance) throws BEValidationException {
		try {
			messageService = BEMMServiceProviderManager.getInstance().getMessageService();
			if (null == serviceInstance)
				throw new BEValidationException(messageService.getMessage(MessageKey.SERVICE_INSTANCE_NULL_ERROR_MESSAGE));

			Host host = serviceInstance.getHost();

			if (null == host)
				throw new BEValidationException(messageService.getMessage(MessageKey.HOST_NULL_SERVICE_INSTANCE_OBJECT_ERROR_MESSAGE));

			Application application = host.getApplication();
			if (null == application)
				throw new BEValidationException(messageService.getMessage(MessageKey.APPLICATION_NULL_SERVICE_INSTANCE_HOST_OBJECT_MESSAGE));
		} catch (ObjectCreationException e) {
			e.printStackTrace();
		}
		
	}

}
