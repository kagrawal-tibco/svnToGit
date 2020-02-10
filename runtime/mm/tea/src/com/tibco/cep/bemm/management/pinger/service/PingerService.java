package com.tibco.cep.bemm.management.pinger.service;

import com.tibco.cep.bemm.common.service.StartStopService;
import com.tibco.cep.bemm.model.Monitorable;

/**
 * @author vdhumal
 * PingerService for MonitorableEntity (ServiceInstance/Host)
 */
public interface PingerService extends StartStopService {

	/**
	 * @param monitorableEntity - register
	 * @throws Exception
	 */
	void register(Monitorable monitorableEntity) throws Exception;
	
	/**
	 * @param monitorableEntity - unregister
	 * @throws Exception
	 */
	void unregister(Monitorable monitorableEntity) throws Exception;
	 
	/**
	 * Set the pinger response handler
	 */
	void setResponseCallbackHandler(PingerResponseCallbackHandler pingerResponseCallbackHandler);
	
	/**
	 * Response handler
	 */
	interface PingerResponseCallbackHandler {		
		void handle(Monitorable monitorableEntity, boolean reachable);
	}
}
