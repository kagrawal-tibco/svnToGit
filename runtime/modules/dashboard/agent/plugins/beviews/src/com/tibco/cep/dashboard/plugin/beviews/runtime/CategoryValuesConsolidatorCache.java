package com.tibco.cep.dashboard.plugin.beviews.runtime;

import java.util.HashMap;
import java.util.Map;

import javax.naming.NamingException;

import com.tibco.cep.dashboard.logging.LoggingService;
import com.tibco.cep.dashboard.management.ManagementUtils;
import com.tibco.cep.dashboard.psvr.data.DataException;
import com.tibco.cep.dashboard.psvr.mal.ElementChangeListener;
import com.tibco.cep.dashboard.psvr.mal.ViewsConfigHelper;
import com.tibco.cep.dashboard.psvr.mal.model.MALComponent;
import com.tibco.cep.dashboard.psvr.mal.model.MALElement;
import com.tibco.cep.dashboard.psvr.plugin.PluginException;
import com.tibco.cep.dashboard.psvr.runtime.PresentationContext;
import com.tibco.cep.dashboard.psvr.vizengine.VisualizationException;
import com.tibco.cep.dashboard.security.SecurityClient;
import com.tibco.cep.dashboard.security.SecurityToken;
import com.tibco.cep.dashboard.security.SecurityTokenListener;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.Logger;

/**
 * @deprecated
 */
public class CategoryValuesConsolidatorCache {

	private static CategoryValuesConsolidatorCache instance;

	public static final synchronized CategoryValuesConsolidatorCache getInstance() {
		if (instance == null) {
			instance = new CategoryValuesConsolidatorCache();
		}
		return instance;
	}

	private Map<SecurityToken, Map<String,TokenRoleSet>> cache;

	private SecurityClient securityClient;

	private SecurityTokenListener listener;

	private Logger logger;

	private boolean initialized;

	private CategoryValuesConsolidatorCache() {
		cache = new HashMap<SecurityToken, Map<String,TokenRoleSet>>();
	}

	public synchronized void init(Logger logger) throws NamingException {
		if (initialized == false) {
			this.logger = LoggingService.getChildLogger(logger, "categoryvaluesconsolidator");
			securityClient = (SecurityClient) ManagementUtils.getContext().lookup("security");
			listener = new CVConsolidatorCacheSTokenListenerImpl();
			securityClient.addSecurityTokenListener(listener);
			initialized = true;
		}
	}

	public synchronized CategoryValuesConsolidator getConsolidator(SecurityToken token, MALComponent component, PresentationContext pCtx) throws DataException, PluginException, VisualizationException {
		if (initialized == false){
			throw new IllegalStateException();
		}
		Map<String, TokenRoleSet> tokenMap = cache.get(token);
		if (tokenMap == null){
			tokenMap = new HashMap<String, TokenRoleSet>();
			cache.put(token, tokenMap);
		}
		String role = token.getPreferredPrincipal().toString();
		TokenRoleSet tokenRoleSet = tokenMap.get(role);
		if (tokenRoleSet == null){
			tokenRoleSet = new TokenRoleSet(new CVConsolidatorCacheElemChangeListenerImpl(token,role), pCtx);
			tokenMap.put(role, tokenRoleSet);
		}
		CategoryValuesConsolidator consolidator = tokenRoleSet.consolidators.get(component.getId());
		if (consolidator == null){
			consolidator = new CategoryValuesConsolidator(logger,component,pCtx);
			tokenRoleSet.consolidators.put(component.getId(), consolidator);
		}
		return consolidator;
	}

	private synchronized void removeConsolidators(SecurityToken token){
		if (logger.isEnabledFor(Level.DEBUG) == true){
			logger.log(Level.DEBUG,"Removing category value consolidator(s) for "+token);
		}
		Map<String, TokenRoleSet> tokenMap = cache.remove(token);
		if (tokenMap != null){
			for (TokenRoleSet tokenRoleSet : tokenMap.values()) {
				tokenRoleSet.viewsConfigHelper.removeElementChangeListener(tokenRoleSet.elementChangeListener);
				for (CategoryValuesConsolidator consolidator : tokenRoleSet.consolidators.values()) {
					consolidator.destroy();
				}
				tokenRoleSet.consolidators.clear();
			}
			tokenMap.clear();
		}
	}

	class TokenRoleSet {

		ViewsConfigHelper viewsConfigHelper;

		CVConsolidatorCacheElemChangeListenerImpl elementChangeListener;

		Map<String,CategoryValuesConsolidator> consolidators;

		TokenRoleSet(CVConsolidatorCacheElemChangeListenerImpl elementChangeListener, PresentationContext pCtx){
			this.elementChangeListener = elementChangeListener;
			this.consolidators = new HashMap<String, CategoryValuesConsolidator>();
			viewsConfigHelper = pCtx.getViewsConfigHelper();
			viewsConfigHelper.addElementChangeListener(elementChangeListener);
		}
	}


	class CVConsolidatorCacheSTokenListenerImpl implements SecurityTokenListener {

		@Override
		public void tokenCreated(SecurityToken token) {
		}

		@Override
		public void tokenDeleted(SecurityToken token) {
			removeConsolidators(token);
		}

		@Override
		public void tokenExpired(SecurityToken token) {
			removeConsolidators(token);
		}

	}

	class CVConsolidatorCacheElemChangeListenerImpl implements ElementChangeListener {

		private SecurityToken token;
		private String role;

		CVConsolidatorCacheElemChangeListenerImpl(SecurityToken token, String role) {
			this.token = token;
			this.role = role;
		}

		@Override
		public void prepareForChange(MALElement element) {
			if (element instanceof MALComponent){
				CategoryValuesConsolidator consolidator = getConsolidator(element);
				if (consolidator != null) {
					consolidator.unregisterDataChangeListeners();
				}
			}
		}

		private CategoryValuesConsolidator getConsolidator(MALElement element) {
			Map<String, TokenRoleSet> map = cache.get(token);
			if (map != null){
				TokenRoleSet tokenRoleSet = map.get(role);
				if (tokenRoleSet != null){
					return tokenRoleSet.consolidators.get(element.getId());
				}
			}
			return null;
		}

		@Override
		public void changeAborted(MALElement element) {
			if (element instanceof MALComponent){
				try {
					CategoryValuesConsolidator consolidator = getConsolidator(element);
					if (consolidator != null) {
						consolidator.registerDataChangeListeners((MALComponent) element, new PresentationContext(token));
					}
				} catch (Exception e) {
					throw new RuntimeException("could not register data change listeners on "+element,e);
				}
			}
		}

		@Override
		public void changeComplete(MALElement element) {
			if (element instanceof MALComponent){
				try {
					CategoryValuesConsolidator consolidator = getConsolidator(element);
					if (consolidator != null) {
						consolidator.registerDataChangeListeners((MALComponent) element, new PresentationContext(token));
					}
				} catch (Exception e) {
					throw new RuntimeException("could not register data change listeners on "+element,e);
				}
			}
		}

		@Override
		public void postOp(String parentPath, MALElement child, MALElement replacement, OPERATION operation) {
		}

		@Override
		public void preOp(String parentPath, MALElement child, MALElement replacement, OPERATION operation) {
		}


	}

}
