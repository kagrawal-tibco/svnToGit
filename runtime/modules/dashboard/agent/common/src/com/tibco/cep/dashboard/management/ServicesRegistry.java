package com.tibco.cep.dashboard.management;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import java.util.TreeMap;

import com.tibco.cep.dashboard.common.utils.StringUtil;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.Logger;

public final class ServicesRegistry {

	private Logger logger;
	
	private String fileName;
	
	private HashMap<String,TreeMap<Integer,String>> parsedRegistry;

	public ServicesRegistry(Logger logger,String fileName) throws ManagementException{
		this.logger = logger;
		this.fileName = fileName;
		this.parsedRegistry = new HashMap<String, TreeMap<Integer,String>>();
		Properties registry = loadRegistry();
		parseRegistry(registry);
	}
	
	private Properties loadRegistry() throws ManagementException{
        URL registryURL = ServicesRegistry.class.getResource("/"+fileName);
        if (registryURL == null){
            throw new ManagementException("could not load "+fileName);
        }
        if (logger.isEnabledFor(Level.DEBUG)) {
            logger.log(Level.DEBUG,"Translated "+fileName+" to "+registryURL);
        }
        
        Properties tempProps = new Properties();
        try {
            tempProps.load(registryURL.openStream());
        } catch (IOException ex) {
            throw new ManagementException("could not load "+fileName);
        }
		return tempProps;
	}
	
	private void parseRegistry(Properties registry) throws ManagementException{
        Enumeration<?> propertyNames = registry.propertyNames();
        while (propertyNames.hasMoreElements()) {
            String propertyName = (String) propertyNames.nextElement();
            String[] splits = propertyName.split("\\.");
            if (splits.length != 2){
            	throw new ManagementException("Incorrect service registry key "+propertyName+" in "+fileName);
            }
            String mode = splits[0];
            String sequenceAsStr = splits[1];
            TreeMap<Integer, String> modeSpecificServices = parsedRegistry.get(mode);
            if (modeSpecificServices == null){
            	modeSpecificServices = new TreeMap<Integer, String>();
            	parsedRegistry.put(mode,modeSpecificServices);
            }
            try {
				Integer sequence = new Integer(sequenceAsStr);
				String className = registry.getProperty(propertyName);
				if (StringUtil.isEmptyOrBlank(className) == true){
					logger.log(Level.WARN, "Skipping "+propertyName+" with no value in "+fileName);
				}
				else {
                    if (logger.isEnabledFor(Level.DEBUG)) {
                        logger.log(Level.DEBUG,"Adding "+className+" to controllable services list for "+mode+"...");
                    }					
					modeSpecificServices.put(sequence, className);
				}
			} catch (NumberFormatException e) {
				throw new ManagementException("Incorrect service registry key "+propertyName+" in "+fileName);
			}
        }
	}
	
	public List<String> getServices(String mode){
		TreeMap<Integer, String> modeSpecificServices = parsedRegistry.get(mode.toLowerCase());
		if (modeSpecificServices != null){
			return new ArrayList<String>(modeSpecificServices.values());
		}
		return Collections.emptyList();
	}

}
