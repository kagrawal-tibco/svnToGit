package com.tibco.cep.bemm.common.service.impl;

import java.util.List;
import java.util.Map;
import java.util.Properties;

import com.tibco.cep.bemm.common.exception.ObjectCreationException;
import com.tibco.cep.bemm.common.exception.StaleEntityVersionException;
import com.tibco.cep.bemm.common.message.impl.MessageKey;
import com.tibco.cep.bemm.common.service.BEMMServiceProviderManager;
import com.tibco.cep.bemm.common.service.LockManager;
import com.tibco.cep.bemm.common.service.MessageService;
import com.tibco.cep.bemm.common.service.logging.Level;
import com.tibco.cep.bemm.common.service.logging.LogManagerFactory;
import com.tibco.cep.bemm.common.service.logging.Logger;
import com.tibco.cep.bemm.common.util.ConfigProperty;
import com.tibco.cep.bemm.model.Versionable;
import com.tibco.rta.common.service.LockService;
import com.tibco.rta.common.service.ServiceProviderManager;

/**
 * @author vdhumal
 *
 */
public class LockManagerImpl implements LockManager {

	private static final Logger LOGGER = LogManagerFactory.getLogManager().getLogger(LockManagerImpl.class);
	
	private LockService lockService = null;
	private boolean checkVersion = true;
	private MessageService messageService;

	
	public LockManagerImpl() throws Exception {
		lockService = ServiceProviderManager.getInstance().getLockService();
	}
	
	@Override
	public void init(Properties configuration) {
		try {
			String checkVersionStr = (String) ConfigProperty.BE_TEA_AGENT_BE_LOCKING_VERSION_CHECK_ENABLE.getValue(configuration);
			checkVersion = Boolean.parseBoolean(checkVersionStr);
			messageService = BEMMServiceProviderManager.getInstance().getMessageService();
		} catch (ObjectCreationException e) {
			e.printStackTrace();
		}
		LOGGER.log(Level.INFO, messageService.getMessage(MessageKey.INITIALIZED_VERSION_CHECK, checkVersion));
	}
	
	@Override
	public void acquireReadLock(String lockKey) {
		LOGGER.log(Level.DEBUG, messageService.getMessage(MessageKey.AQUIRED_READ_LOCK_KEY, lockKey));
		lockService.acquireReadLock(lockKey, -1);		
	}

	@Override
	public void acquireWriteLock(String lockKey) {
		LOGGER.log(Level.DEBUG, messageService.getMessage(MessageKey.AQUIRED_WRITE_LOCK_KEY , lockKey));
		lockService.acquireWriteLock(lockKey, -1);
		
	}

	@Override
	public void releaseLock(String lockKey) {
		LOGGER.log(Level.DEBUG, messageService.getMessage(MessageKey.RELEASED_LOCK_KEY, lockKey));
		lockService.unlock(lockKey);		
	}

	@Override
	public void checkVersion(Versionable versionable, Long userEntityVersion) 
			throws StaleEntityVersionException {
		LOGGER.log(Level.DEBUG, messageService.getMessage(MessageKey.CHECK_ENTITY_STALE));
		if (checkVersion && (versionable != null && versionable.getVersion() != userEntityVersion)) {
			throw new StaleEntityVersionException(versionable);
		}
	}

	@Override
	public void checkVersions(List<Versionable> versionables, Map<String, Long> userEntityVersions) 
			throws StaleEntityVersionException{
		if (checkVersion && userEntityVersions != null) {
			for (Versionable versionable : versionables) {
				Long userVersion = userEntityVersions.get(versionable.getKey());
				LOGGER.log(Level.DEBUG, messageService.getMessage(MessageKey.SERVER_USER_VERSION, versionable.getClass().getSimpleName(), 
						versionable.getKey(), versionable.getVersion(), userVersion));
				if (versionable.getVersion() != userVersion) {
					throw new StaleEntityVersionException(versionable);
				}
			}
		}	
	}

	@Override
	public Long incrementVersion(Versionable versionable) {
		if (versionable != null)
			return versionable.incrementVersion();
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
