package com.tibco.rta.common.service;

/**
 * 
 * @author bgokhale
 *
 * A basic Idgenerator interface
 */
@Deprecated
public interface IdGenerator extends StartStopService {
	
	long getId();

}
