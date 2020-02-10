package com.tibco.cep.dashboard.psvr.streaming;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.tibco.cep.dashboard.common.utils.StringUtil;
import com.tibco.cep.dashboard.psvr.data.DataException;
import com.tibco.cep.dashboard.psvr.mal.model.MALComponent;
import com.tibco.cep.dashboard.psvr.ogl.model.VisualizationData;
import com.tibco.cep.dashboard.psvr.plugin.PluginException;
import com.tibco.cep.dashboard.psvr.runtime.PresentationContext;

class ComponentTypeSubscriptionHandler extends SubscriptionHandler {

	protected Set<DataSourceUpdateHandler> updateHandlers;
	protected SubscriptionHandlerIndex index;
	private String componentType;
	private Collection<MALComponent> components;

	protected ComponentTypeSubscriptionHandler(){
		updateHandlers = new HashSet<DataSourceUpdateHandler>();
		index = new SubscriptionHandlerIndex();
	}

	@Override
	protected void doProcess(Map<String, String> subscription, PresentationContext ctx) throws StreamingException, DataException, PluginException {
		componentType = subscription.get("componenttype");
		if (StringUtil.isEmptyOrBlank(componentType) == false){
			components = tokenRoleProfile.getViewsConfigHelper().getComponentsByType(componentType);
			for (MALComponent component : components) {
				ComponentSubscriptionHandler handler = new ComponentSubscriptionHandler();
				handler.token = token;
				handler.preferredRole = preferredRole;
				handler.tokenRoleProfile = tokenRoleProfile;
				handler.process(component, ctx);
				updateHandlers.addAll(handler.updateHandlers);
				index.addHandler(handler);
			}
		}
	}

	@Override
	protected DataSourceUpdateHandler[] getUpdateHandlers() {
		return updateHandlers.toArray(new DataSourceUpdateHandler[updateHandlers.size()]);
	}

	@Override
	protected VisualizationData[] processUpdate(String subscriptionName) throws StreamingException {
		List<VisualizationData> data = new LinkedList<VisualizationData>();
		Collection<SubscriptionHandler> handlers = index.getHandlers(subscriptionName);
		for (SubscriptionHandler componentSubscriptionHandler : handlers) {
			VisualizationData[] individualData = componentSubscriptionHandler.processUpdate(subscriptionName);
			data.addAll(Arrays.asList(individualData));
		}
		return data.toArray(new VisualizationData[data.size()]);
	}

	@Override
	protected void shutdown() {
		this.updateHandlers.clear();
		Collection<SubscriptionHandler> handlers = index.getHandlers();
		for (SubscriptionHandler subscriptionHandler : handlers) {
			subscriptionHandler.shutdown();
		}
	}

	@Override
	protected boolean isEquivalentTo(Map<String, String> request) {
		return componentType.equals(request.get("componenttype"));
	}

	@Override
	protected String getSubscribedTarget() {
		return componentType;
	}

	@Override
	public MALComponent[] getComponents() {
		return components.toArray(new MALComponent[components.size()]);
	}

}
