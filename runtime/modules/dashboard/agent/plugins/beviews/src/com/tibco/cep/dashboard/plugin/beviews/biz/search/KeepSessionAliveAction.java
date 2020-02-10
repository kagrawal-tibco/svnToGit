package com.tibco.cep.dashboard.plugin.beviews.biz.search;

import java.util.Date;

import com.tibco.cep.dashboard.common.data.BuiltInTypes;
import com.tibco.cep.dashboard.psvr.biz.BaseSessionedAction;
import com.tibco.cep.dashboard.psvr.biz.BizResponse;
import com.tibco.cep.dashboard.psvr.biz.BizSession;
import com.tibco.cep.dashboard.psvr.biz.BizSessionRequest;
import com.tibco.cep.dashboard.security.SecurityToken;
import com.tibco.cep.kernel.service.logging.Level;

public class KeepSessionAliveAction extends BaseSessionedAction {

	private static final String EMPTY_RESPONSE = "";

	@Override
	protected BizResponse doSessionedExecute(SecurityToken token, BizSessionRequest request) {
		BizSession session = request.getSession();
		if (session != null){
			if (logger.isEnabledFor(Level.DEBUG) == true){
				logger.log(Level.DEBUG,"Keeping "+session+" alive...");
			}
			session.setAttribute(this.getClass().getName(),BuiltInTypes.DATETIME.toString(new Date()));
		}
		return handleSuccess(EMPTY_RESPONSE);
	}

}