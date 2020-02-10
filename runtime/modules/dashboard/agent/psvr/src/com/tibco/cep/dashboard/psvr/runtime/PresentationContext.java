package com.tibco.cep.dashboard.psvr.runtime;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import com.tibco.cep.dashboard.config.GlobalConfiguration;
import com.tibco.cep.dashboard.config.SynchronizedSimpleDateFormat;
import com.tibco.cep.dashboard.management.MessageGeneratorArgs;
import com.tibco.cep.dashboard.psvr.alerts.AlertEvaluator;
import com.tibco.cep.dashboard.psvr.data.DataException;
import com.tibco.cep.dashboard.psvr.data.DataSourceHandler;
import com.tibco.cep.dashboard.psvr.data.DataSourceHandlerCache;
import com.tibco.cep.dashboard.psvr.mal.ElementNotFoundException;
import com.tibco.cep.dashboard.psvr.mal.MALException;
import com.tibco.cep.dashboard.psvr.mal.MALExternalReferenceResolver;
import com.tibco.cep.dashboard.psvr.mal.MALFieldMetaInfo;
import com.tibco.cep.dashboard.psvr.mal.ViewsConfigHelper;
import com.tibco.cep.dashboard.psvr.mal.model.MALSeriesConfig;
import com.tibco.cep.dashboard.psvr.plugin.PluginException;
import com.tibco.cep.dashboard.psvr.user.TokenRoleProfile;
import com.tibco.cep.dashboard.psvr.vizengine.VisualizationException;
import com.tibco.cep.dashboard.security.SecurityToken;

//PORT clean up the constructor
public class PresentationContext {

	protected SecurityToken token;
	protected TokenRoleProfile profile;
	protected ViewsConfigHelper viewsConfigHelper;
	protected boolean generateAdditionalOutputs;
	protected Map<String, Object> attributes;

	// optimization caches
	protected Map<Object, Object> resolvedReferences;
	protected Map<Object, MALFieldMetaInfo> resolvedFieldReferences;
	protected Map<MALSeriesConfig, DataSourceHandler> dataSourceHandlers;
	protected Map<MALSeriesConfig, AlertEvaluator> alertEvaluators;

	protected PresentationContext() throws MALException, ElementNotFoundException {
		attributes = new HashMap<String, Object>();
		resolvedReferences = new HashMap<Object, Object>();
		resolvedFieldReferences = new HashMap<Object, MALFieldMetaInfo>();
		dataSourceHandlers = new HashMap<MALSeriesConfig, DataSourceHandler>();
		alertEvaluators = new HashMap<MALSeriesConfig, AlertEvaluator>();
	}

	public PresentationContext(SecurityToken token) throws MALException, ElementNotFoundException {
		this(token, true);
	}

	public PresentationContext(SecurityToken token, boolean generateAdditionalOutputs) throws MALException, ElementNotFoundException {
		this();
		this.token = token;
		profile = TokenRoleProfile.getInstance(this.token);
		viewsConfigHelper = profile.getViewsConfigHelper();
		this.generateAdditionalOutputs = generateAdditionalOutputs;
	}

//	public PresentationContext(SecurityToken token, TokenRoleProfile profile) throws MALException, ElementNotFoundException {
//		this.token = token;
//		this.profile = profile;
//		viewsConfigHelper = this.profile.getViewsConfigHelper();
//		this.generateAdditionalOutputs = false;
//	}
//

	public SecurityToken getSecurityToken() {
		return token;
	}

	public boolean generateAdditionalOutputs() {
		return generateAdditionalOutputs;
	}

	public Locale getLocale() {
		return viewsConfigHelper.getLocale();
	}

	public Object resolveRef(Object reference) throws MALException, ElementNotFoundException {
		Object resolvedRef = resolvedReferences.get(reference);
		if (resolvedRef == null) {
//			resolvedRef = MALReferenceResolver.resolveReference(token, reference);
			try {
				resolvedRef = MALExternalReferenceResolver.resolveReference(token, reference);
				if (resolvedRef == null) {
					throw new ElementNotFoundException("");
				}
				resolvedReferences.put(reference, resolvedRef);
			} catch (PluginException e) {
				throw new MALException(e);
			}
		}
		return resolvedRef;
	}

	public MALFieldMetaInfo resolveFieldRef(Object sourceField) throws MALException, ElementNotFoundException {
		MALFieldMetaInfo fieldMetaInfo = resolvedFieldReferences.get(sourceField);
		if (fieldMetaInfo == null) {
//			fieldMetaInfo = MALReferenceResolver.resolveFieldReference(token, sourceField);
			try {
				fieldMetaInfo = MALExternalReferenceResolver.resolveFieldReference(token, sourceField);
				if (fieldMetaInfo == null) {
					throw new ElementNotFoundException("");
				}
				resolvedFieldReferences.put(sourceField, fieldMetaInfo);
			} catch (PluginException e) {
				throw new MALException(e);
			}
		}
		return fieldMetaInfo;
	}

	public DataSourceHandler getDataSourceHandler(MALSeriesConfig seriesCfg) throws VisualizationException {
		DataSourceHandler dataSourceHandler = dataSourceHandlers.get(seriesCfg);
		if (dataSourceHandler == null) {
			try {
				dataSourceHandler = DataSourceHandlerCache.getInstance().getDataSourceHandler(seriesCfg, this);
				dataSourceHandlers.put(seriesCfg, dataSourceHandler);
			} catch (DataException e) {
				throw new VisualizationException(e);
			} catch (PluginException e) {
				throw new VisualizationException(e);
			}
		}
		return dataSourceHandler;
	}

	public SynchronizedSimpleDateFormat getCustomDateFormat() {
		return GlobalConfiguration.getInstance().getDateFormat();
	}

	public AlertEvaluator getVisualEvaluator(MALSeriesConfig seriesConfig) {
		AlertEvaluator evaluator = alertEvaluators.get(seriesConfig);
		if (evaluator == null) {
			evaluator = profile.getAlertEvalutorsIndex().getEvaluator(seriesConfig);
			alertEvaluators.put(seriesConfig, evaluator);
		}
		return evaluator;
	}

	public void addAttribute(String name, Object value) {
		attributes.put(name, value);
	}

	public Object getAttribute(String name) {
		return attributes.get(name);
	}

	public void removeAttribute(String name) {
		attributes.remove(name);
	}

	public ViewsConfigHelper getViewsConfigHelper() {
		return viewsConfigHelper;
	}

	public TokenRoleProfile getTokenRoleProfile() {
		return profile;
	}

	public MessageGeneratorArgs getMessageGeneratorArgs(Throwable t, Object... args) {
		SecurityToken token = getSecurityToken();
		return new MessageGeneratorArgs(token.toString(), token.getUserID(), token.getPreferredPrincipal(), token.getPrincipals(), t, args);
	}

	public void close() {
		alertEvaluators.clear();
		alertEvaluators = null;
		dataSourceHandlers.clear();
		dataSourceHandlers = null;
		resolvedFieldReferences.clear();
		resolvedFieldReferences = null;
		resolvedReferences.clear();
		resolvedReferences = null;
		attributes.clear();
		attributes = null;
		profile = null;
		viewsConfigHelper = null;
		token = null;
	}
}
