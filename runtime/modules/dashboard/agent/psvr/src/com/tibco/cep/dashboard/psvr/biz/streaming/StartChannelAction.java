package com.tibco.cep.dashboard.psvr.biz.streaming;

import com.tibco.cep.dashboard.psvr.biz.BizRequest;
import com.tibco.cep.dashboard.psvr.biz.BizResponse;
import com.tibco.cep.dashboard.psvr.streaming.Channel;
import com.tibco.cep.dashboard.security.SecurityToken;

public class StartChannelAction extends AbstractChannelAction {

	@Override
	protected BizResponse doChannelAction(SecurityToken token,Channel channel, BizRequest request) {
		if (channel != null){
			channel.start();
		}
		return handleSuccess("");
	}
}
