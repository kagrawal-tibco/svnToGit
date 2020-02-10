package com.tibco.cep.dashboard.naming;

import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.spi.InitialContextFactory;

/**
 * @author anpatil
 * 
 */
public class NamingContextFactory implements InitialContextFactory {
	
	private static final String PROPERTIES_FILE_NAME = "services.properties"; 

	private Map<String,Map<String,String>> transportToServiceImplsMap;
	
	public NamingContextFactory(){
		try {
			transportToServiceImplsMap = new HashMap<String, Map<String,String>>();
			Properties props = new Properties();
			props.load(this.getClass().getResourceAsStream(PROPERTIES_FILE_NAME));
			Enumeration<Object> keys = props.keys();
			while (keys.hasMoreElements()) {
				String key = (String) keys.nextElement();
				String[] splits = key.split("\\.");
				String transportType = splits[0];
				String serviceName = splits[1];
				Map<String, String> serviceImpls = transportToServiceImplsMap.get(transportType);
				if (serviceImpls == null){
					serviceImpls = new HashMap<String, String>();
					transportToServiceImplsMap.put(transportType, serviceImpls);
				}
				serviceImpls.put(serviceName, props.getProperty(key));
			}
		} catch (IOException e) {
			throw new RuntimeException("could not initialize NamingContextFactory",e);
		}
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * javax.naming.spi.InitialContextFactory#getInitialContext(java.util.Hashtable
	 * )
	 */
	public Context getInitialContext(Hashtable<?, ?> envoirenment) throws NamingException {
		String transport = (String) envoirenment.get(NamingContext.TRANSPORT);
		if (transport == null || transport.trim().length() == 0) {
			transport = "local";
		}
		if (transport.equalsIgnoreCase("local") == true) {
			// this is going to be local lookup
			return new LocalSynNamingContext(envoirenment, transportToServiceImplsMap.get("local"));
		}
		throw new NamingException("Unknown transport type[" + transport + "]");
	}

}