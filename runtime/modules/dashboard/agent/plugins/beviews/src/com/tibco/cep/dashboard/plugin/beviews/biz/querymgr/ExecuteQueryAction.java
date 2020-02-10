package com.tibco.cep.dashboard.plugin.beviews.biz.querymgr;

import java.util.ArrayList;
import java.util.Arrays;

import com.tibco.cep.dashboard.plugin.beviews.biz.BaseSessionCheckerAction;
import com.tibco.cep.dashboard.plugin.beviews.drilldown.query.DrilldownProvider;
import com.tibco.cep.dashboard.plugin.beviews.drilldown.query.model.QuerySpec;
import com.tibco.cep.dashboard.plugin.beviews.search.BizSessionSearchStore;
import com.tibco.cep.dashboard.plugin.beviews.search.QuerySpecModel;
import com.tibco.cep.dashboard.psvr.biz.BizResponse;
import com.tibco.cep.dashboard.psvr.biz.BizSession;
import com.tibco.cep.dashboard.psvr.biz.BizSessionRequest;
import com.tibco.cep.dashboard.psvr.common.query.QueryException;
import com.tibco.cep.dashboard.security.SecurityToken;

public class ExecuteQueryAction extends BaseSessionCheckerAction {

	@Override
	protected BizResponse doExecute(SecurityToken token, BizSessionRequest request) {
		BizSession session = request.getSession();
		BizSessionSearchStore sessionSearchStore = BizSessionSearchStore.getInstance(session);
		QuerySpecModel queryManagerModel = sessionSearchStore.getQueryManagerModel();
		if (queryManagerModel == null){
			return handleError("No Query Found");
		}
		QuerySpec newQuerySpec = queryManagerModel.getQuerySpec();
		QuerySpec existingQuerySpec = null;
		if (sessionSearchStore.getQuerySpecs() != null && sessionSearchStore.getQuerySpecs().isEmpty() != true) {
			existingQuerySpec = sessionSearchStore.getQuerySpecs().get(0);
		}
		try {
			sessionSearchStore.setQuerySpecs(Arrays.asList(newQuerySpec));
			DrilldownProvider.getInstance(session).clear();
		} catch (QueryException e) {
			sessionSearchStore.setQuerySpecs(existingQuerySpec == null ? new ArrayList<QuerySpec>(0) : Arrays.asList(existingQuerySpec));
			return handleError("Could not execute the query");
		}
		return handleSuccess("");
	}

	@Override
	protected BizResponse handleInvalidSession(SecurityToken token, String sessionId, BizSessionRequest request) {
		return handleError("Your session has timed out");
	}

}
