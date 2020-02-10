package com.tibco.cep.dashboard.psvr.biz.bootstrap;

import com.tibco.cep.dashboard.config.ClientConfiguration;
import com.tibco.cep.dashboard.psvr.biz.BaseAction;
import com.tibco.cep.dashboard.psvr.biz.BizRequest;
import com.tibco.cep.dashboard.psvr.biz.BizResponse;
import com.tibco.cep.dashboard.psvr.biz.RawBizResponseImpl;

public class GetConfigurationAction extends BaseAction {

	@Override
	protected BizResponse doExecute(BizRequest request) {
		String configurationXML = ClientConfiguration.getInstance().toXML(false);
		return new RawBizResponseImpl(configurationXML);
	}

}
