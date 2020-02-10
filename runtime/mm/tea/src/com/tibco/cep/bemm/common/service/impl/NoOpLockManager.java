package com.tibco.cep.bemm.common.service.impl;

import java.util.List;
import java.util.Map;
import java.util.Properties;

import com.tibco.cep.bemm.common.service.LockManager;
import com.tibco.cep.bemm.common.service.logging.Level;
import com.tibco.cep.bemm.common.service.logging.LogManagerFactory;
import com.tibco.cep.bemm.common.service.logging.Logger;
import com.tibco.cep.bemm.model.Versionable;

public class NoOpLockManager implements LockManager {

	private static final Logger LOGGER = LogManagerFactory.getLogManager().getLogger(NoOpLockManager.class);
	
	@Override
	public void init(Properties configuration) {
		//No-op
		LOGGER.log(Level.INFO, "Initialized");
	}

	@Override
	public void acquireWriteLock(String key) {
		//No-op
	}

	@Override
	public void acquireReadLock(String key) {
		//No-op
	}

	@Override
	public void releaseLock(String key) {
		//No-op
	}

	@Override
	public void checkVersion(Versionable versionable, Long userEntityVersion) {
		//No-op
	}

	@Override
	public void checkVersions(List<Versionable> versionables, Map<String, Long> userEntityVersions) {
		//No-op
	}

	@Override
	public Long incrementVersion(Versionable versionable) {
		return null;
	}

	@Override
	public void start() throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void stop() throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void suspend() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isStarted() {
		// TODO Auto-generated method stub
		return false;
	}

}
