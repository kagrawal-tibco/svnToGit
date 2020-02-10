package com.tibco.cep.dashboard.psvr.biz.streaming;

import com.tibco.cep.dashboard.psvr.biz.BaseAuthenticatedAction;
import com.tibco.cep.dashboard.psvr.biz.BizRequest;
import com.tibco.cep.dashboard.psvr.biz.BizResponse;
import com.tibco.cep.dashboard.psvr.mal.ElementNotFoundException;
import com.tibco.cep.dashboard.psvr.mal.MALException;
import com.tibco.cep.dashboard.psvr.streaming.Channel;
import com.tibco.cep.dashboard.psvr.streaming.ChannelGroup;
import com.tibco.cep.dashboard.security.SecurityToken;

public abstract class AbstractChannelAction extends BaseAuthenticatedAction {

	@Override
	protected BizResponse doAuthenticatedExecute(SecurityToken token, BizRequest request) {
		try {
			Channel channel = ChannelGroup.getInstance().getChannel(token);
			return doChannelAction(token,channel,request);
		} catch (MALException e) {
			return handleError(getMessage("streaming.channel.element.retrieval.failure", getMessageGeneratorArgs(token, e)), e);
		} catch (ElementNotFoundException e){
			return handleError(getMessage("streaming.channel.element.notfound.failure", getMessageGeneratorArgs(token, e)), e);
		}
	}

	protected abstract BizResponse doChannelAction(SecurityToken token,Channel channel, BizRequest request);

}