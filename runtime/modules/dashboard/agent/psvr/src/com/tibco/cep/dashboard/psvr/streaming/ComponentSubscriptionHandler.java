package com.tibco.cep.dashboard.psvr.streaming;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.tibco.cep.dashboard.common.utils.StringUtil;
import com.tibco.cep.dashboard.psvr.data.DataException;
import com.tibco.cep.dashboard.psvr.mal.model.MALComponent;
import com.tibco.cep.dashboard.psvr.mal.model.MALSeriesConfig;
import com.tibco.cep.dashboard.psvr.mal.model.MALVisualization;
import com.tibco.cep.dashboard.psvr.ogl.model.VisualizationData;
import com.tibco.cep.dashboard.psvr.plugin.PluginException;
import com.tibco.cep.dashboard.psvr.runtime.PresentationContext;
import com.tibco.cep.dashboard.psvr.vizengine.VisualizationEngine;

class ComponentSubscriptionHandler extends SubscriptionHandler {

	protected Set<DataSourceUpdateHandler> updateHandlers;
	protected Map<String,List<String>> subscriptionNameToSeriesCfgMap;
	private MALComponent component;

	protected ComponentSubscriptionHandler(){
		updateHandlers = new HashSet<DataSourceUpdateHandler>();
		subscriptionNameToSeriesCfgMap = new HashMap<String, List<String>>();
	}

	@Override
	protected void doProcess(Map<String, String> subscription, PresentationContext ctx) throws StreamingException, DataException, PluginException {
		component = findComponent(subscription);
		process(component, ctx);
	}

	protected void process(MALComponent component, PresentationContext ctx) throws DataException, PluginException {
		DataSourceUpdateHandlerFactory updateHandlerFactory = DataSourceUpdateHandlerFactory.getInstance();
		for (MALVisualization visualization : component.getVisualization()) {
			for (MALSeriesConfig seriesConfig : visualization.getSeriesConfig()) {
				DataSourceUpdateHandler dataSourceUpdateHandler = updateHandlerFactory.getDataSourceUpdateHandler(seriesConfig, ctx);
				updateHandlers.add(dataSourceUpdateHandler);
				List<String> seriesCfgNames = subscriptionNameToSeriesCfgMap.get(dataSourceUpdateHandler.getSubscriptionName());
				if (seriesCfgNames == null){
					seriesCfgNames = new LinkedList<String>();
					subscriptionNameToSeriesCfgMap.put(dataSourceUpdateHandler.getSubscriptionName(),seriesCfgNames);
				}
				seriesCfgNames.add(seriesConfig.getName());
			}
		}
	}

	private MALComponent findComponent(Map<String, String> subscription) throws StreamingException {
		MALComponent searchResult = null;
		String componentId = subscription.get("componentid");
		if (StringUtil.isEmptyOrBlank(componentId) == false){
			searchResult = tokenRoleProfile.getViewsConfigHelper().getComponentById(componentId);
			if (searchResult == null){
				searchResult = tokenRoleProfile.getComponentGallery().searchComponent(componentId);
			}
			if (searchResult == null){
				throw new StreamingException("No component found with id as "+componentId);
			}
			return searchResult;
		}
		String componentName = subscription.get("componentname");
		if (StringUtil.isEmptyOrBlank(componentName) == false){
			searchResult = tokenRoleProfile.getViewsConfigHelper().getComponentByName(componentName);
			if (searchResult == null){
				searchResult = tokenRoleProfile.getComponentGallery().searchComponentByName(componentName);
			}
		}
		if (searchResult == null){
			throw new StreamingException("No component found with name as "+componentId);
		}
		return searchResult;
	}

	@Override
	protected DataSourceUpdateHandler[] getUpdateHandlers() {
		return updateHandlers.toArray(new DataSourceUpdateHandler[updateHandlers.size()]);
	}

	@Override
	protected VisualizationData[] processUpdate(String subscriptionName) throws StreamingException {
		PresentationContext ctx = null;
		try {
			ctx = new PresentationContext(token);
			List<String> seriesCfgNames = subscriptionNameToSeriesCfgMap.get(subscriptionName);
			VisualizationData visualizationData = VisualizationEngine.getInstance().getComponentVisualizationData(component, seriesCfgNames, ctx);
			return new VisualizationData[]{visualizationData};
		} catch (Exception e) {
			throw new StreamingException("could not process update for "+subscriptionName+" for "+component,e);
		} finally {
			if (ctx != null){
				ctx.close();
			}
		}
	}

	@Override
	protected void shutdown() {
		this.component = null;
		this.updateHandlers.clear();
		this.updateHandlers = null;
		this.subscriptionNameToSeriesCfgMap.clear();
		this.subscriptionNameToSeriesCfgMap = null;
	}

	@Override
	protected boolean isEquivalentTo(Map<String, String> request) {
		try {
			return findComponent(request).getId().equals(component.getId());
		} catch (StreamingException e) {
			return false;
		}
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder(ComponentSubscriptionHandler.class.getSimpleName());
		sb.append("[");
		sb.append("component="+component);
		sb.append(",updatehandlerscnt="+((updateHandlers == null) ? "--" : updateHandlers.size()));
		sb.append("]");
		return sb.toString();
	}

	@Override
	protected String getSubscribedTarget() {
		return component.toString();
	}

	@Override
	public MALComponent[] getComponents() {
		return new MALComponent[] { component };

	}

}
