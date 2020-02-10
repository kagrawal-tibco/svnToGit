package com.tibco.cep.dashboard.psvr.mal;

import javax.naming.NamingException;

import com.tibco.cep.dashboard.logging.LoggingService;
import com.tibco.cep.dashboard.management.ExceptionHandler;
import com.tibco.cep.dashboard.management.ManagementException;
import com.tibco.cep.dashboard.management.ManagementUtils;
import com.tibco.cep.dashboard.management.MessageGenerator;
import com.tibco.cep.dashboard.management.Service;
import com.tibco.cep.dashboard.psvr.mal.store.PersistentStoreFactory;
import com.tibco.cep.dashboard.security.SecurityClient;
import com.tibco.cep.dashboard.security.SecurityToken;
import com.tibco.cep.kernel.service.logging.Level;

public class MALService extends Service {

	private SecurityClient securityClient;
	private SecurityToken sysToken;
	private MALSession sysMALSession;
	
	private MALGlobalElementsCache globalElementsCache;
	private MALSkinCache skinCache;
	
	public MALService() {
		super("mal", "Metadata Abstracton Layer");
	}

	@Override
	protected void doInit() throws ManagementException {
		ViewsConfigHelper.LOGGER = LoggingService.getChildLogger(logger, "viewsconfighelper");
		ViewsConfigHelper.EXCEPTION_HANDLER = new ExceptionHandler(ViewsConfigHelper.LOGGER);
		ViewsConfigHelper.MESSAGE_GENERATOR = new MessageGenerator(name, ViewsConfigHelper.EXCEPTION_HANDLER);
		
		MALComponentGallery.LOGGER = LoggingService.getChildLogger(logger, "gallery");
		MALComponentGallery.EXCEPTION_HANDLER = new ExceptionHandler(MALComponentGallery.LOGGER);;
		MALComponentGallery.MESSAGE_GENERATOR = new MessageGenerator(name, MALComponentGallery.EXCEPTION_HANDLER);;
		
		MALSession.LOGGER = LoggingService.getChildLogger(logger, "session");
		MALTransaction.LOGGER = LoggingService.getChildLogger(logger, "transaction");
		
		addDependent(PersistentStoreFactory.getInstance());
		addDependent(MALElementManagerFactory.getInstance());
		globalElementsCache = MALGlobalElementsCache.getInstance();
		addDependent(globalElementsCache);
		skinCache = MALSkinCache.getInstance();
		addDependent(skinCache);
		
		URIHelper.LOGGER = logger;
	}

	@Override
	protected void doStart() throws ManagementException {
		if (logger.isEnabledFor(Level.DEBUG) == true){
			logger.log(Level.DEBUG,"Acquiring system token");
		}
		initSysToken();
		if (logger.isEnabledFor(Level.DEBUG) == true){
			logger.log(Level.DEBUG,"Creating a system level MAL Session");
		}
		try {
			sysMALSession = new MALSession(sysToken);
		} catch (Exception e) {
			String msg = messageGenerator.getMessage("malservice.systemmalsession.failure");
			throw new ManagementException(msg, e);
		}
		if (logger.isEnabledFor(Level.DEBUG) == true){
			logger.log(Level.DEBUG,"Loading global elements cache");
		}
		globalElementsCache.setMALSession(sysMALSession);
		skinCache.setMALSession(sysMALSession);
	}
	
	private void initSysToken() throws ManagementException{
		try {
			securityClient = (SecurityClient) ManagementUtils.getContext().lookup("security");
		} catch (NamingException ex) {
			String msg = messageGenerator.getMessage("malservice.security.lookup.failure");
			throw new ManagementException(msg,ex);
		}
		//PATCH find a better way to get system level token
		try {
			sysToken = securityClient.login(null, null);
		} catch (Exception e) {
			String msg = messageGenerator.getMessage("malservice.security.syslogin.failure");
			throw new ManagementException(msg,e);
		}
	}
	
}