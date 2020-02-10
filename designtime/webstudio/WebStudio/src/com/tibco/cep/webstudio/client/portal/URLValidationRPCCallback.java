package com.tibco.cep.webstudio.client.portal;

import java.util.Map;

import com.smartgwt.client.rpc.RPCCallback;
import com.smartgwt.client.rpc.RPCRequest;
import com.smartgwt.client.rpc.RPCResponse;
import com.tibco.cep.webstudio.client.panels.CustomSC;

public class URLValidationRPCCallback implements RPCCallback {

	@Override
	public void execute(RPCResponse response, Object rawData, RPCRequest request) {
		Map httpHeaders = response.getHttpHeaders();
		response.getHttpResponseCode();
		if (httpHeaders != null) {
			String xFrameOptions = (String) httpHeaders.get("x-frame-options");
			if (xFrameOptions == null) {
				xFrameOptions = (String) httpHeaders.get("X-Frame-Options");
			}
			if (xFrameOptions != null && WebPagePortlet.SAME_ORIGIN.equalsIgnoreCase(xFrameOptions)) {
				validateEvent(false);
				CustomSC.say("Invalid URL", "The specified URL does not allow embedding inside of a portlet");
				return;
			}
			validateEvent(true);
		}
	}

	protected void validateEvent(boolean isValid) {
		// do nothing by default
	}
	
}
