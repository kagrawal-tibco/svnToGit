package com.tibco.rta.engine;

import com.tibco.rta.engine.RtaEngine;

/**
 * @author vdhumal
 * 
 */
public class RtaEngineExFactory {
	
	private static RtaEngineExFactory instance;
	
	private RtaEngine engine;
	
	private RtaEngineExFactory(){}
	
	/**
	 * @return RtaEngineExFactory instance
	 */
	synchronized public static RtaEngineExFactory getInstance() {
		if (instance == null) {
			instance = new RtaEngineExFactory();
		}
		return instance;
	}
	
	/**
	 * @return RtaEngineEx instance
	 */
	synchronized public RtaEngine getEngine() {
		if (engine == null)
			engine = new RtaEngineEx();
		return engine;
	}
	
}
