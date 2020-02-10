package com.tibco.rta.service.transport;

import com.tibco.rta.common.service.MessageContext;
import com.tibco.rta.common.service.StartStopService;

/**
 * 
 * @author bgokhale
 * 
 * This service provides transport layer to the engine to communicate with channels etc.
 * 
 */


public interface TransportService extends StartStopService {

	void handleError(MessageContext context);
}
