package com.tibco.cep.bemm.management.pinger.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import com.tibco.cep.bemm.common.exception.ObjectCreationException;
import com.tibco.cep.bemm.common.service.logging.Level;
import com.tibco.cep.bemm.common.service.logging.LogManagerFactory;
import com.tibco.cep.bemm.common.service.logging.Logger;
import com.tibco.cep.bemm.management.util.ManagementUtil;
import com.tibco.cep.bemm.model.Application;

/**
 * @author vdhumal
 * Singleton Registry for the PingerServices
 */
public class PingerServiceRegistry {

	private static Logger LOGGER = LogManagerFactory.getLogManager().getLogger(PingerServiceRegistry.class);
	
	private static PingerServiceRegistry pingerServiceRegistry = null;
	private static String DEFAULT_TRANSPORT_TYPE = "JMX";
	
	private Map<Application, PingerService> pingerServices = null;
	private Properties properties = null;
	
	private PingerServiceRegistry() {
		this.pingerServices = new HashMap<>();
	}
	
	public static synchronized PingerServiceRegistry getInstance() throws Exception {
		if (pingerServiceRegistry == null) {
			if (LOGGER.isEnabledFor(Level.DEBUG)) {
				LOGGER.log(Level.DEBUG, "Instantiating the Pinger service registry.");
			}
			pingerServiceRegistry = new PingerServiceRegistry();
		}
		return pingerServiceRegistry;
	}
		
	public void init(Properties properties) throws Exception {
		this.properties = properties;
	}
	
	public synchronized PingerService createPingerService(Application application, String transportType) throws Exception {
		//Create a new instance
		PingerService pingerService = createPingerServiceInstance(transportType);

		//If existing, stop and remove it
		PingerService origPingerService = pingerServices.get(application);
		if (origPingerService != null) {
			origPingerService.stop();
			pingerServices.remove(application);
		}
		pingerService.init(properties);
		pingerServices.put(application, pingerService);
		return pingerService;
	}

	public synchronized PingerService getPingerService(Application application) throws Exception {
		PingerService pingerService = pingerServices.get(application);
		if (pingerService == null) {
			pingerService = createPingerService(application, DEFAULT_TRANSPORT_TYPE);
		}
		return pingerService;
	}
	
	private PingerService createPingerServiceInstance(String transportType) throws Exception {
		PingerService pingerService = null;
		try {
			switch(transportType) {
			case "JMX" :
				pingerService = (PingerService) ManagementUtil.getInstance("com.tibco.cep.bemm.management.pinger.service.impl.JMXPingerService");
				break;
			case "HAWK" :
				pingerService = (PingerService) ManagementUtil.getInstance("com.tibco.cep.bemm.management.pinger.service.impl.HAWKPingerService");
				break;
			default :
				pingerService = (PingerService) ManagementUtil.getInstance("com.tibco.cep.bemm.management.pinger.service.impl.JMXPingerService");
				break;
			}			
		} catch (ObjectCreationException ex) {
			throw new Exception(ex);
		}
		return pingerService;
	}		
}
