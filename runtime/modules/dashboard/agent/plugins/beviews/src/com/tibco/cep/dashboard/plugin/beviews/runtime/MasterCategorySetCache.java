package com.tibco.cep.dashboard.plugin.beviews.runtime;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import javax.naming.NamingException;

import com.tibco.cep.dashboard.management.ManagementUtils;
import com.tibco.cep.dashboard.psvr.mal.model.MALComponent;
import com.tibco.cep.dashboard.psvr.runtime.PresentationContext;
import com.tibco.cep.dashboard.psvr.vizengine.VisualizationException;
import com.tibco.cep.dashboard.security.SecurityClient;
import com.tibco.cep.dashboard.security.SecurityToken;
import com.tibco.cep.dashboard.security.SecurityTokenListener;

public class MasterCategorySetCache {

	private static MasterCategorySetCache instance;

	public static final synchronized MasterCategorySetCache getInstance() {
		if (instance == null) {
			instance = new MasterCategorySetCache();
		}
		return instance;
	}

	private Map<SecurityToken, Map<String,TokenRoleComponentSet>> cache;

	private SecurityClient securityClient;

	private SecurityTokenListener listener;

	private boolean initialized;

	private MasterCategorySetCache() {
		try {
			cache = new HashMap<SecurityToken, Map<String,TokenRoleComponentSet>>();
			securityClient = (SecurityClient) ManagementUtils.getContext().lookup("security");
			listener = new SecurityTokenListenerImpl();
			securityClient.addSecurityTokenListener(listener);
			initialized = true;
		} catch (NamingException e) {
			throw new RuntimeException(e);
		}
	}

	public synchronized MasterCategorySet getMasterCategorySet(SecurityToken token, MALComponent component, PresentationContext pCtx) throws VisualizationException {
		if (initialized == false){
			throw new IllegalStateException();
		}
		Map<String, TokenRoleComponentSet> tokenMap = cache.get(token);
		if (tokenMap == null){
			tokenMap = new HashMap<String, TokenRoleComponentSet>();
			cache.put(token, tokenMap);
		}
		String role = token.getPreferredPrincipal().toString();
		TokenRoleComponentSet tokenRoleComponentSet = tokenMap.get(role);
		if (tokenRoleComponentSet == null){
			tokenRoleComponentSet = new TokenRoleComponentSet(token, pCtx.getViewsConfigHelper());
			tokenMap.put(role, tokenRoleComponentSet);
		}
		return tokenRoleComponentSet.getMasterCategorySet(component, pCtx);
	}

	public synchronized void removeMasterCategorySets(SecurityToken token) {
		Map<String, TokenRoleComponentSet> tokenMap = cache.remove(token);
		if (tokenMap != null){
			for (TokenRoleComponentSet tokenRoleComponentSet : tokenMap.values()) {
				tokenRoleComponentSet.clear();
			}
			tokenMap.clear();
		}
	}

	public synchronized void clear(){
		LinkedList<SecurityToken> tokens = new LinkedList<SecurityToken>(cache.keySet());
		for (SecurityToken token : tokens) {
			removeMasterCategorySets(token);
		}
	}

	class SecurityTokenListenerImpl implements SecurityTokenListener {

		@Override
		public void tokenCreated(SecurityToken token) {
			//do nothing
		}

		@Override
		public void tokenDeleted(SecurityToken token) {
			removeMasterCategorySets(token);
		}

		@Override
		public void tokenExpired(SecurityToken token) {
			removeMasterCategorySets(token);
		}

	}
}
