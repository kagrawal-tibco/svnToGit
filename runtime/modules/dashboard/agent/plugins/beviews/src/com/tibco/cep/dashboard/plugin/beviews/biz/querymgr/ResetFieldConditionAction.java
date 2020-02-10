package com.tibco.cep.dashboard.plugin.beviews.biz.querymgr;

import com.tibco.cep.dashboard.common.utils.StringUtil;
import com.tibco.cep.dashboard.plugin.beviews.search.BizSessionSearchStore;
import com.tibco.cep.dashboard.plugin.beviews.search.QuerySpecModel;
import com.tibco.cep.dashboard.psvr.biz.BizResponse;
import com.tibco.cep.dashboard.psvr.biz.BizSession;
import com.tibco.cep.dashboard.psvr.biz.BizSessionRequest;
import com.tibco.cep.dashboard.psvr.biz.RawBizResponseImpl;
import com.tibco.cep.dashboard.security.SecurityToken;

public class ResetFieldConditionAction extends BaseEntityFieldsManagmentAction {

	@Override
	protected BizResponse doExecute(SecurityToken token, BizSessionRequest request) {
		String fieldName = request.getParameter("fieldname");
		if (StringUtil.isEmptyOrBlank(fieldName) == true){
			handleError(request, "Invalid field name");
		}
		BizSession session = request.getSession();
		QuerySpecModel queryMgrMdl = BizSessionSearchStore.getInstance(session).getQueryManagerModel();
		if (queryMgrMdl == null){
			handleError(request, "Invalid session");
		}
		queryMgrMdl.removeCondition(fieldName);
		return new RawBizResponseImpl("Any");
	}

}