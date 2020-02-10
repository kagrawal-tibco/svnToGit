package com.tibco.cep.dashboard.psvr.biz.streaming;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.tibco.cep.dashboard.psvr.biz.BizRequest;
import com.tibco.cep.dashboard.psvr.biz.BizResponse;
import com.tibco.cep.dashboard.psvr.mal.ElementNotFoundException;
import com.tibco.cep.dashboard.psvr.mal.MALException;
import com.tibco.cep.dashboard.psvr.mal.ViewsConfigHelper;
import com.tibco.cep.dashboard.psvr.mal.model.MALComponent;
import com.tibco.cep.dashboard.psvr.mal.model.MALDashboardPage;
import com.tibco.cep.dashboard.psvr.mal.model.MALPanel;
import com.tibco.cep.dashboard.psvr.mal.model.MALPartition;
import com.tibco.cep.dashboard.psvr.runtime.PresentationContext;
import com.tibco.cep.dashboard.psvr.streaming.Channel;
import com.tibco.cep.dashboard.psvr.streaming.StreamingException;
import com.tibco.cep.dashboard.security.SecurityToken;

public class SubscribeAllAction extends AbstractChannelAction {

	@Override
	protected BizResponse doChannelAction(SecurityToken token,Channel channel, BizRequest request) {
		PresentationContext ctx = null;
		try {
			if (channel != null){
				ctx = new PresentationContext(token);
				Map<String,String> map = new HashMap<String, String>();
				Iterator<String> parameterNames = request.getParameterNames();
				while (parameterNames.hasNext()) {
					String parameterName = (String) parameterNames.next();
					map.put(parameterName,request.getParameter(parameterName));
				}
				ViewsConfigHelper viewsConfigHelper = ctx.getViewsConfigHelper();
				MALDashboardPage page = (MALDashboardPage) viewsConfigHelper.getCurrentPage();
				for (MALPartition partition : page.getPartition()) {
					for (MALPanel panel : partition.getPanel()) {
						for (MALComponent component : panel.getComponent()) {
							map.put("componentid", component.getId());
							channel.subscribe(map, ctx);
						}
					}
				}
				
			}
			return handleSuccess("");
		} catch (StreamingException e) {
			return handleError(getMessage("streaming.subscribe.failure", getMessageGeneratorArgs(token, e)), e);
		} catch (MALException e) {
			return handleError(getMessage("bizaction.view.retrieval.failure", getMessageGeneratorArgs(token, e)), e);
		} catch (ElementNotFoundException e) {
			return handleError(getMessage("bizaction.view.notfound.failure", getMessageGeneratorArgs(token, e)), e);
		} finally {
			if (ctx != null){
				ctx.close();
			}
		}
	}

}
