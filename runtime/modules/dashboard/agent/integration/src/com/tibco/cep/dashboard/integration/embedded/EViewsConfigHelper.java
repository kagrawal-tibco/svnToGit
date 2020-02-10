package com.tibco.cep.dashboard.integration.embedded;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import com.tibco.cep.dashboard.psvr.alerts.AlertEvalutorsIndex;
import com.tibco.cep.dashboard.psvr.mal.ElementChangeListener;
import com.tibco.cep.dashboard.psvr.mal.MALSkinIndexer;
import com.tibco.cep.dashboard.psvr.mal.ViewsConfigHelper;
import com.tibco.cep.dashboard.psvr.mal.model.MALComponent;
import com.tibco.cep.dashboard.psvr.mal.model.MALPage;
import com.tibco.cep.dashboard.psvr.plugin.PluginException;
import com.tibco.cep.dashboard.security.SecurityToken;

public class EViewsConfigHelper extends ViewsConfigHelper {

	private MALComponent component;

	EViewsConfigHelper(SecurityToken token, MALSkinIndexer skinIndexer, MALComponent malComponent) throws PluginException {
		this.token = token;
		this.skinIndexer = skinIndexer;
		this.component = malComponent;
		this.listeners = new ArrayList<ElementChangeListener>();
		
		evalutorsIndex = new AlertEvalutorsIndex(token);
		evalutorsIndex.indexEvaluators(component);
	}

	@Override
	public MALComponent getComponentById(String componentID) {
		return component;
	}

	@Override
	public MALComponent getComponentByName(String componentName) {
		return component;
	}

	@Override
	public Collection<MALComponent> getComponentsByType(String componentType) {
		return Arrays.asList(component);
	}
	
	@Override
	public MALPage[] getPagesByType(String pageType) {
		return new MALPage[0];
	}
	
	AlertEvalutorsIndex getAlertEvaluatorIndex(){
		return evalutorsIndex;
	}

}
