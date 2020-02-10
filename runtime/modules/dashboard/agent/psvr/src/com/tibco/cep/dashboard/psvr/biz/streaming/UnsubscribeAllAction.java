package com.tibco.cep.dashboard.psvr.biz.streaming;

import com.tibco.cep.dashboard.psvr.biz.BizRequest;
import com.tibco.cep.dashboard.psvr.biz.BizResponse;
import com.tibco.cep.dashboard.psvr.streaming.Channel;
import com.tibco.cep.dashboard.security.SecurityToken;

public class UnsubscribeAllAction extends AbstractChannelAction {

	@Override
	protected BizResponse doChannelAction(SecurityToken token, Channel channel, BizRequest request) {
//		try {
//			if (channel != null) {
//				Map<String, String> map = new HashMap<String, String>();
//				Iterator<String> parameterNames = request.getParameterNames();
//				while (parameterNames.hasNext()) {
//					String parameterName = (String) parameterNames.next();
//					map.put(parameterName, request.getParameter(parameterName));
//				}
//				ViewsConfigHelper viewsConfigHelper = getTokenRoleProfile(token).getViewsConfigHelper();
//				MALDashboardPage page = viewsConfigHelper.getCurrentDashBoardPage();
//				for (MALPartition partition : page.getPartition()) {
//					for (MALPanel panel : partition.getPanel()) {
//						for (MALComponent component : panel.getComponent()) {
//							map.put("componentid", component.getId());
//							channel.unsubscribe(map);
//						}
//					}
//				}
//
//			}
//			return handleSuccess("");
//		} catch (RequestProcessingException e) {
//			return handleError(e.getMessage(), e);
//		}
		if (channel != null){
			channel.unsubscribeAll();
		}
		return handleSuccess("");
	}

}
