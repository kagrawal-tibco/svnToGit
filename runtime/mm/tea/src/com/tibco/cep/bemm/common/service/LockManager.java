package com.tibco.cep.bemm.common.service;

import java.util.List;
import java.util.Map;
import java.util.Properties;

import com.tibco.cep.bemm.common.exception.StaleEntityVersionException;
import com.tibco.cep.bemm.model.Versionable;

/**
 * @author vdhumal
 *
 */
public interface LockManager extends StartStopService{
	
	/**
	 * @param configuration
	 */
	void init(Properties configuration);
	
	/**
	 * @param key
	 */
	void acquireWriteLock(String key);
	
	/**
	 * @param key
	 */
	void acquireReadLock(String key);
		
	/**
	 * @param key
	 */
	void releaseLock(String key);
	
	/**
	 * @param versionable
	 * @param userEntityVersion
	 * @throws StaleEntityVersionException
	 */
	void checkVersion(Versionable versionable, Long userEntityVersion) throws StaleEntityVersionException;
	
	/**
	 * @param versionables
	 * @param userEntityVersions
	 * @throws StaleEntityVersionException
	 */
	void checkVersions(List<Versionable> versionables, Map<String, Long> userEntityVersions) throws StaleEntityVersionException;

	
	/**
	 * @param versionable
	 * @return
	 */
	Long incrementVersion(Versionable versionable);
}
