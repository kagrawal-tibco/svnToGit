package com.tibco.cep.bemm.monitoring.metric.service;

import java.util.Properties;

import com.tibco.cep.bemm.management.service.exception.ServiceInitializationException;
import com.tibco.cep.bemm.monitoring.metric.probe.ProbeContainer;
import com.tibco.rta.Fact;
import com.tibco.rta.RtaSession;
import com.tibco.rta.model.RtaSchema;

/**
 * @author vdhumal
 *
 */
public interface AggregationService {

	/**
	 * @param configuration
	 * @throws ServiceInitializationException
	 */
	void init(Properties configuration) throws ServiceInitializationException;
	
	/**
	 * @throws Exception
	 */
	void start() throws Exception;

	/**
	 * @throws Exception
	 */
	RtaSession getSession() throws Exception;

	/**
	 * @param schema
	 * @return
	 * @throws Exception
	 */
	Fact createFact() throws Exception;
	
	/**
	 * @param schema
	 * @return
	 * @throws Exception
	 */
	Fact createFact(String schemaName) throws Exception;
	
	/**
	 * @throws Exception
	 */
	void stop() throws Exception;
	
	/**
	 * @return ProbeContainer
	 */
	ProbeContainer getProbeContainer();
	
	RtaSchema getDefaultSchema();


}
