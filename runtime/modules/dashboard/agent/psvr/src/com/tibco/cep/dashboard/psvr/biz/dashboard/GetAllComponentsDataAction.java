package com.tibco.cep.dashboard.psvr.biz.dashboard;

import java.io.IOException;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import com.tibco.cep.dashboard.common.utils.StringUtil;
import com.tibco.cep.dashboard.psvr.biz.BaseAuthenticatedAction;
import com.tibco.cep.dashboard.psvr.biz.BizRequest;
import com.tibco.cep.dashboard.psvr.biz.BizResponse;
import com.tibco.cep.dashboard.psvr.data.DataException;
import com.tibco.cep.dashboard.psvr.mal.ElementNotFoundException;
import com.tibco.cep.dashboard.psvr.mal.MALException;
import com.tibco.cep.dashboard.psvr.mal.model.MALComponent;
import com.tibco.cep.dashboard.psvr.mal.model.MALPage;
import com.tibco.cep.dashboard.psvr.mal.model.MALPanel;
import com.tibco.cep.dashboard.psvr.mal.model.MALPartition;
import com.tibco.cep.dashboard.psvr.ogl.OGLException;
import com.tibco.cep.dashboard.psvr.ogl.OGLMarshaller;
import com.tibco.cep.dashboard.psvr.ogl.model.UpdatePacket;
import com.tibco.cep.dashboard.psvr.ogl.model.UpdatePackets;
import com.tibco.cep.dashboard.psvr.ogl.model.VisualizationData;
import com.tibco.cep.dashboard.psvr.plugin.PluginException;
import com.tibco.cep.dashboard.psvr.runtime.PresentationContext;
import com.tibco.cep.dashboard.psvr.streaming.Channel;
import com.tibco.cep.dashboard.psvr.streaming.ChannelGroup;
import com.tibco.cep.dashboard.psvr.streaming.SubscriptionHandler;
import com.tibco.cep.dashboard.psvr.user.TokenRoleProfile;
import com.tibco.cep.dashboard.psvr.vizengine.VisualizationEngine;
import com.tibco.cep.dashboard.psvr.vizengine.VisualizationException;
import com.tibco.cep.dashboard.security.SecurityToken;

public class GetAllComponentsDataAction extends BaseAuthenticatedAction {

	@Override
	protected BizResponse doAuthenticatedExecute(SecurityToken token, BizRequest request) {
		List<MALComponent> components = null;
		PresentationContext ctx = null;
		String pageid = request.getParameter("pageid");
		String pagetype = request.getParameter("pagetype");
        try {
    		if (StringUtil.isEmptyOrBlank(pageid) == false){
    			components = getComponentsByPageID(token, pageid);
    		}
    		else if (StringUtil.isEmptyOrBlank(pagetype) == false){
    			components = getComponentsByPageType(token, pagetype);
    		}
    		else {
    			components = getComponents(token);
    		}
			ctx = new PresentationContext(token, true);
			UpdatePackets packets = new UpdatePackets();
			for (MALComponent component : components) {
				VisualizationData visualizationData = VisualizationEngine.getInstance().getComponentVisualizationData(component, null, ctx);
				UpdatePacket packet = new UpdatePacket();
				packet.setVisualizationData(visualizationData);
				packets.addUpdatePacket(packet);
			}
			return handleSuccess(OGLMarshaller.getInstance().marshall(token, packets));
		} catch (DataException e) {
			return handleError(getMessage("getcomponentdata.config.generation.failure", getMessageGeneratorArgs(token, e)), e);
		} catch (MALException e) {
			return handleError(getMessage("bizaction.view.loading.failure", getMessageGeneratorArgs(token, e)), e);
		} catch (ElementNotFoundException e) {
			return handleError(getMessage("bizaction.view.notfound.failure", getMessageGeneratorArgs(token, e)), e);
		} catch (VisualizationException e) {
			return handleError(getMessage("getcomponentdata.data.generation.failure", getMessageGeneratorArgs(token, e)), e);
		} catch (IOException e) {
			return handleError(getMessage("getcomponentdata.data.generation.failure", getMessageGeneratorArgs(token, e)), e);
		} catch (PluginException e) {
			return handleError(getMessage("getcomponentdata.data.generation.failure", getMessageGeneratorArgs(token, e)), e);
		} catch (OGLException e) {
			return handleError(getMessage("bizaction.marshalling.failure", getMessageGeneratorArgs(token, e, "component data")), e);
		} finally {
			if (ctx != null){
				ctx.close();
			}
		}
	}

	private List<MALComponent> getComponentsByPageID(SecurityToken token, String pageID) throws MALException, ElementNotFoundException{
		TokenRoleProfile profile = TokenRoleProfile.getInstance(token);
		MALPage page = profile.getViewsConfigHelper().getPageByID(pageID);
		List<MALComponent> components = new LinkedList<MALComponent>();
		if (page != null) {
			for (MALPartition partition : page.getPartition()) {
				for (MALPanel panel : partition.getPanel()) {
					for (MALComponent component : panel.getComponent()) {
						components.add(component);
					}
				}
			}
		}
		return components;
	}

	private List<MALComponent> getComponentsByPageType(SecurityToken token, String pageType) throws MALException, ElementNotFoundException{
		TokenRoleProfile profile = TokenRoleProfile.getInstance(token);
		MALPage[] pages = profile.getViewsConfigHelper().getPagesByType(pageType);
		List<MALComponent> components = new LinkedList<MALComponent>();
		if (pages != null) {
			for (MALPage page : pages) {
				for (MALPartition partition : page.getPartition()) {
					for (MALPanel panel : partition.getPanel()) {
						for (MALComponent component : panel.getComponent()) {
							components.add(component);
						}
					}
				}
			}
		}
		return components;
	}

	private List<MALComponent> getComponents(SecurityToken token) throws MALException, ElementNotFoundException {
		Channel channel = ChannelGroup.getInstance().getChannel(token);
		Collection<SubscriptionHandler> subscriptions = channel.getSubscriptions();
		List<MALComponent> components = new LinkedList<MALComponent>();
		for (SubscriptionHandler subscriptionHandler : subscriptions) {
			MALComponent[] subscribedComponents = subscriptionHandler.getComponents();
			if (subscribedComponents != null) {
				for (MALComponent subscribedComponent : subscribedComponents) {
					components.add(subscribedComponent);
				}
			}
		}
		return components;
	}

}
