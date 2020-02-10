package com.tibco.cep.dashboard.psvr.biz.streaming;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.tibco.cep.dashboard.common.utils.StringUtil;
import com.tibco.cep.dashboard.psvr.biz.BizRequest;
import com.tibco.cep.dashboard.psvr.biz.BizResponse;
import com.tibco.cep.dashboard.psvr.streaming.Channel;
import com.tibco.cep.dashboard.psvr.streaming.Streamer;
import com.tibco.cep.dashboard.psvr.streaming.StreamerRegistry;
import com.tibco.cep.dashboard.security.SecurityToken;

public class StartStreamingChannelAction extends AbstractChannelAction {

	private String STREAMER_PARAM_NAME = Streamer.class.getName();

	@Override
	protected BizResponse doChannelAction(SecurityToken token, Channel channel, BizRequest request) {
		String streamerID = request.getParameter(STREAMER_PARAM_NAME);
		if (StringUtil.isEmptyOrBlank(streamerID) == true){
			return handleError(getMessage("streaming.invalid.streamerid", getMessageGeneratorArgs(token, channel.getName())));
		}
		//get streamer from streamer registry 
		Streamer streamer = StreamerRegistry.getInstance().getRegisteredStreamer(streamerID);
		if (streamer == null){
			return handleError(getMessage("streaming.nonexistent.streamerid", getMessageGeneratorArgs(token, streamerID, channel.getName())));
		}
		Map<String,String> map = new HashMap<String, String>();
		Iterator<String> parameterNames = request.getParameterNames();
		while (parameterNames.hasNext()) {
			String parameterName = (String) parameterNames.next();
			map.put(parameterName, request.getParameter(parameterName));
		}
		channel.startStreaming(streamer, map);
		return handleSuccessWithCommandAck();
	}

}
