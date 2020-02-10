package com.tibco.cep.dashboard.psvr.context;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.Logger;

/**
 * @author apatil
 * 
 */
public class ContextCache {
	
	private static ContextCache instance = null;

	public static final synchronized ContextCache getInstance() {
		if (instance == null) {
			instance = new ContextCache();
		}
		return instance;
	}
	
	private Logger logger;

	private Map<String,Context> nonGlobalCtxNameToCtxMap;

	private Context defaultContext;

	private ContextCache() {
		this.nonGlobalCtxNameToCtxMap = new HashMap<String,Context>();
	}

	void init(Logger logger, Properties properties) {
		this.logger = logger;
		Context context = new Context("System", properties);
		defaultContext = context;
		logger.log(Level.INFO, "Registered " + nonGlobalCtxNameToCtxMap.size() + " contexts...");
		logger.log(Level.INFO, "Registered " + defaultContext + " as the global context...");
	}

	boolean shutdown() {
		boolean success = true;
		logger.log(Level.INFO, "Shutting down " + nonGlobalCtxNameToCtxMap.size() + " contexts...");
		Iterator<Context> ctxIter = nonGlobalCtxNameToCtxMap.values().iterator();
		while (ctxIter.hasNext()) {
			Context context = ctxIter.next();
			logger.log(Level.DEBUG, "Shutting down " + context + "...");
			boolean ctxShutDwn = context.shutdown();
			if (ctxShutDwn == false){
				logger.log(Level.WARN, context + "did not shutdown properly...");
			}
			success = success && ctxShutDwn;
		}
		nonGlobalCtxNameToCtxMap.clear();
		if (defaultContext != null) {
			logger.log(Level.INFO, "Shutting down " + defaultContext + "...");
			boolean ctxShutDwn = defaultContext.shutdown();
			success = success && ctxShutDwn;
			if (ctxShutDwn == false){
				logger.log(Level.WARN, defaultContext + "did not shutdown properly...");
			}			
			defaultContext = null;
		}
		return success;
	}

	public Context getContext(String name) throws ContextException {
		Context ctx = nonGlobalCtxNameToCtxMap.get(name);
		if (ctx == null) {
			throw new ContextException("could not find a context for " + name);
		}
		return ctx;
	}

	public Context getGlobalContext() {
		return defaultContext;
	}

	public Iterator<String> getContextNames() {
		return nonGlobalCtxNameToCtxMap.keySet().iterator();
	}

	public Iterator<Context> getNonGlobalContexts() {
		return nonGlobalCtxNameToCtxMap.values().iterator();
	}

	public int getNonGlobalContextCount() {
		return nonGlobalCtxNameToCtxMap.size();
	}

}