package com.tibco.cep.dashboard.psvr.alerts;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

import com.tibco.cep.dashboard.psvr.common.NonFatalException;
import com.tibco.cep.dashboard.psvr.mal.model.MALComponent;
import com.tibco.cep.dashboard.psvr.mal.model.MALSeriesConfig;
import com.tibco.cep.dashboard.psvr.mal.model.MALVisualization;
import com.tibco.cep.dashboard.psvr.plugin.PluginException;
import com.tibco.cep.dashboard.security.SecurityToken;

public final class AlertEvalutorsIndex {
	
	private SecurityToken token;
	
	@SuppressWarnings("unused")
	private Principal preferredPrincipal;
	
	private Map<String,Map<String,AlertEvaluator>> alertEvaluators;

	private AlertInterpreter alertInterpreter;

	public AlertEvalutorsIndex(SecurityToken token) {
		super();
		this.token = token;
		this.preferredPrincipal = this.token.getPreferredPrincipal();
		alertEvaluators = new HashMap<String, Map<String,AlertEvaluator>>();
		alertInterpreter = AlertInterpreter.getInstance();
	}
	
	public void indexEvaluators(MALComponent component) throws PluginException {
		Map<String,AlertEvaluator> seriesLvlEvaluators = new HashMap<String, AlertEvaluator>();
		for (MALVisualization visualization : component.getVisualization()) {
			for (MALSeriesConfig seriesConfig : visualization.getSeriesConfig()) {
				AlertEvaluator alertEvaluator = alertInterpreter.interpret(seriesConfig);
				if (alertEvaluator != null){
					seriesLvlEvaluators.put(seriesConfig.getId(), alertEvaluator);
				}
			}
		}
		alertEvaluators.put(component.getId(),seriesLvlEvaluators);
	}	
	
	public boolean deindexEvaluators(MALComponent component) {
		boolean success = true;
		Map<String,AlertEvaluator> seriesLvlEvaluators = alertEvaluators.remove(component.getId());
		if (seriesLvlEvaluators != null) {
			for (MALVisualization visualization : component.getVisualization()) {
				for (MALSeriesConfig seriesConfig : visualization.getSeriesConfig()) {
					AlertEvaluator alertEvaluator = seriesLvlEvaluators.remove(seriesConfig.getId());
					if (alertEvaluator != null){
						try {
							alertEvaluator.shutdown();
						} catch (NonFatalException e) {
							success = false;
						}
					}
				}
			}
		}
		return success;
	}
	
	public AlertEvaluator getEvaluator(MALSeriesConfig seriesConfig){
		MALComponent component = (MALComponent) seriesConfig.getParent().getParent();
		Map<String, AlertEvaluator> seriesLvlEvaluators = alertEvaluators.get(component.getId());
		if (seriesLvlEvaluators != null){
			return seriesLvlEvaluators.get(seriesConfig.getId());
		}
		return null;
	}

	public boolean shutdown(){
		boolean success = true;
		for (Map<String, AlertEvaluator> seriesLvlEvaluators : alertEvaluators.values()) {
			for (AlertEvaluator evaluator : seriesLvlEvaluators.values()) {
				try {
					evaluator.shutdown();
				} catch (NonFatalException e) {
					success = false;
				}
			}
			seriesLvlEvaluators.clear();
		}
		alertEvaluators.clear();
		return success;
	}
}
