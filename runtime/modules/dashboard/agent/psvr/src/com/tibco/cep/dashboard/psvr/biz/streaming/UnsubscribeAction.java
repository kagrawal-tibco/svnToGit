package com.tibco.cep.dashboard.psvr.biz.streaming;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.tibco.cep.dashboard.psvr.biz.BizRequest;
import com.tibco.cep.dashboard.psvr.biz.BizResponse;
import com.tibco.cep.dashboard.psvr.streaming.Channel;
import com.tibco.cep.dashboard.security.SecurityToken;

public class UnsubscribeAction extends AbstractChannelAction {

	@Override
	protected BizResponse doChannelAction(SecurityToken token, Channel channel, BizRequest request) {
		if (channel != null) {
			Map<String, String> map = new HashMap<String, String>();
			Iterator<String> parameterNames = request.getParameterNames();
			while (parameterNames.hasNext()) {
				String parameterName = (String) parameterNames.next();
				map.put(parameterName, request.getParameter(parameterName));
			}
			channel.unsubscribe(map);
		}
		return handleSuccess("");
	}

}
