package com.tibco.cep.dashboard.psvr.biz.streaming;

import com.tibco.cep.dashboard.common.utils.StringUtil;
import com.tibco.cep.dashboard.psvr.biz.BizRequest;
import com.tibco.cep.dashboard.psvr.biz.BizResponse;
import com.tibco.cep.dashboard.psvr.streaming.Channel;
import com.tibco.cep.dashboard.security.SecurityToken;

public class CreateChannelAction extends AbstractChannelAction {

	@Override
	protected BizResponse doAuthenticatedExecute(SecurityToken token, BizRequest request) {
		String name = request.getParameter("name");
		if (StringUtil.isEmptyOrBlank(name) == true){
			return handleError(getMessage("streaming.invalid.channelname"));
		}
		return super.doAuthenticatedExecute(token, request);
	}

	@Override
	protected BizResponse doChannelAction(SecurityToken token,Channel channel, BizRequest request) {
		if (channel.getPrefferredPrincipal().equals(token.getPreferredPrincipal()) == false){
			return handleError(getMessage("streaming.preexisting.channel.failure", getMessageGeneratorArgs(token, channel.getPrefferredPrincipal())));
		}
		channel.setName(request.getParameter("name"));
		return handleSuccess("");
	}

}
