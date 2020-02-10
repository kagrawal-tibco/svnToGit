package com.tibco.cep.bemm.common.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import org.xml.sax.InputSource;

import com.tibco.cep.bemm.common.operations.model.Agent;
import com.tibco.cep.bemm.common.operations.model.Method;
import com.tibco.cep.bemm.common.operations.model.Methodgroup;
import com.tibco.cep.bemm.common.operations.model.Methods;
import com.tibco.cep.bemm.common.operations.model.ProcessMethods;
import com.tibco.cep.bemm.common.service.EntityMethodsDescriptorService;
import com.tibco.cep.bemm.common.service.logging.Level;
import com.tibco.cep.bemm.common.service.logging.LogManagerFactory;
import com.tibco.cep.bemm.common.service.logging.Logger;

/**
 * Implementation of methods defined in EntityMethodsDescriptorService
 * interface.
 * 
 * @author dijadhav
 *
 */
public class EntityMethodsDescriptorServiceImpl implements EntityMethodsDescriptorService {

	private static final String JAXB_PROCESS_METHODS_PACKAGE = "com.tibco.cep.bemm.common.operations.model";
	
	// Logger
	private Logger logger = LogManagerFactory.getLogManager().getLogger(EntityMethodsDescriptorServiceImpl.class);
	private Map<String, Methods> typeMethodsMap = new HashMap<String, Methods>();

	/**
	 * Default constructor which loads the process and agent level methods
	 */
	public EntityMethodsDescriptorServiceImpl() {
		loadProcessMethods();
		loadAgentMethods();
	}

	private void loadAgentMethods() {
		InputStream fileStream = this.getClass().getResourceAsStream("/InferenceAgent_MethodsDescriptor.xml");
		try {
			Agent agent = (Agent) deserialize(fileStream);
			typeMethodsMap.put(agent.getType(), agent.getMethods());
		} catch (IOException e) {
			logger.log(Level.ERROR, e.getLocalizedMessage(), e);
		} catch (Exception e) {
			logger.log(Level.ERROR, e.getLocalizedMessage(), e);
		}
	}

	private void loadProcessMethods() {
		InputStream fileStream = this.getClass().getResourceAsStream("/Process_MethodsDescriptor.xml");
		try {
			ProcessMethods processMethods = (ProcessMethods) deserialize(fileStream);
			typeMethodsMap.put(processMethods.getType(), processMethods.getMethods());
		} catch (IOException e) {
			logger.log(Level.ERROR, e.getLocalizedMessage(), e);
		} catch (Exception e) {
			logger.log(Level.ERROR, e.getLocalizedMessage(), e);
		}
	}

	@Override
	public Map<String, Methods> getEnityMethods() {
		return typeMethodsMap;
	}

	@Override
	public Method getEnityMethod(String entityType, String methodGroup, String methodName) {
		Methods methods = typeMethodsMap.get(entityType);
		if (null != methods) {
			for (Methodgroup methodgroup : methods.getMethodgroup()) {
				if (null != methodgroup && methodgroup.getGroup().equals(methodGroup)) {
					for (Method existingMethod : methodgroup.getMethod()) {
						if (null != existingMethod && existingMethod.getName().equals(methodName)) {
							return existingMethod;
						}
					}
				}
			}
		}
		return null;
	}
	
	private Object deserialize(InputStream fileStream) throws Exception {
		JAXBContext jaxbContext = JAXBContext.newInstance(JAXB_PROCESS_METHODS_PACKAGE);
		Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
		Object obj = unmarshaller.unmarshal(new InputSource(fileStream));
		return obj;
	}
	
}
