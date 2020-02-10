package com.tibco.cep.dashboard.psvr.biz.streaming;

import java.util.Map;
import java.util.Properties;

import com.tibco.cep.dashboard.psvr.biz.BaseAction;
import com.tibco.cep.dashboard.psvr.biz.BizRequest;
import com.tibco.cep.dashboard.psvr.biz.BizResponse;
import com.tibco.cep.dashboard.psvr.biz.RawBizResponseImpl;
import com.tibco.cep.dashboard.psvr.streaming.StreamingProperties;

public class GetPolicyFileAction extends BaseAction {
	
	private String policyXML;
	
	@Override
	protected void init(String command, Properties properties, Map<String, String> configuration) throws Exception {
		super.init(command, properties, configuration);
		StringBuilder sb = new StringBuilder();
        sb.append("<cross-domain-policy>");
        int portNumber = (Integer)StreamingProperties.STREAMING_PORT.getValue(properties);
        sb.append("<allow-access-from to-ports=\"");
        sb.append(portNumber);
        sb.append("\" secure=\"false\" domain=\"*\"/>");
        sb.append("</cross-domain-policy>");
        policyXML = sb.toString();
	}

	@Override
	protected BizResponse doExecute(BizRequest request) {
		return new RawBizResponseImpl(policyXML);
	}

}
