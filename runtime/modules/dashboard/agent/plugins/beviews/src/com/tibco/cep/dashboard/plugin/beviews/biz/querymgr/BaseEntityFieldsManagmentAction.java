package com.tibco.cep.dashboard.plugin.beviews.biz.querymgr;

import com.tibco.cep.dashboard.common.data.TupleSchemaField;
import com.tibco.cep.dashboard.plugin.beviews.biz.BaseSessionCheckerAction;
import com.tibco.cep.dashboard.plugin.beviews.drilldown.query.model.QueryCondition;
import com.tibco.cep.dashboard.plugin.beviews.querymgr.DataTypeHandlerFactory;
import com.tibco.cep.dashboard.plugin.beviews.runtime.VelocityViewHelper;
import com.tibco.cep.dashboard.psvr.biz.BizResponse;
import com.tibco.cep.dashboard.psvr.biz.BizSessionRequest;
import com.tibco.cep.dashboard.psvr.common.query.QueryException;
import com.tibco.cep.dashboard.security.SecurityToken;
import com.tibco.cep.kernel.service.logging.Level;

public abstract class BaseEntityFieldsManagmentAction extends BaseSessionCheckerAction {

	@Override
	protected BizResponse handleInvalidSession(SecurityToken token, String sessionId, BizSessionRequest request) {
		return handleError("Your session has timed out");
	}

	protected String getConditionAsString(QueryCondition condition, TupleSchemaField field) {
		if (condition == null) {
			return "Any";
		}
		try {
			return DataTypeHandlerFactory.getInstance().getDataTypeHandler(field.getFieldDataType()).getQueryConditionAsString(logger, condition);
		} catch (QueryException e) {
			exceptionHandler.handleException("could not convert " + condition + " to a string format ", e, Level.WARN);
			return "Unknown";
		}
	}
	
	protected BizResponse handleError(BizSessionRequest request, String errorMsg){
		request.setAttribute("errormessage", errorMsg);
		return VelocityViewHelper.getInstance().prepareRespone(request, "error");		
	}

}
