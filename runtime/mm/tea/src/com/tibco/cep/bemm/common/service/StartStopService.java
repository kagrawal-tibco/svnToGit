package com.tibco.cep.bemm.common.service;

import java.util.Properties;

/**
 * @author vdhumal
 *
 */
public interface StartStopService {
	public abstract void init(Properties properties) throws Exception;

	public abstract void start() throws Exception;

	public abstract void stop() throws Exception;

	public abstract void suspend();

	public abstract void resume();

	public abstract boolean isStarted();
}
