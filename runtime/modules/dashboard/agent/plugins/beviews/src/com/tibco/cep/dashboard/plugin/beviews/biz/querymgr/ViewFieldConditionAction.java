package com.tibco.cep.dashboard.plugin.beviews.biz.querymgr;

import com.tibco.cep.dashboard.common.data.TupleSchema;
import com.tibco.cep.dashboard.common.data.TupleSchemaField;
import com.tibco.cep.dashboard.common.utils.StringUtil;
import com.tibco.cep.dashboard.plugin.beviews.data.TupleSchemaFactory;
import com.tibco.cep.dashboard.plugin.beviews.drilldown.query.model.QueryCondition;
import com.tibco.cep.dashboard.plugin.beviews.querymgr.DataTypeHandler;
import com.tibco.cep.dashboard.plugin.beviews.querymgr.DataTypeHandlerFactory;
import com.tibco.cep.dashboard.plugin.beviews.runtime.VelocityViewHelper;
import com.tibco.cep.dashboard.plugin.beviews.search.BizSessionSearchStore;
import com.tibco.cep.dashboard.plugin.beviews.search.QuerySpecModel;
import com.tibco.cep.dashboard.psvr.biz.BizResponse;
import com.tibco.cep.dashboard.psvr.biz.BizSession;
import com.tibco.cep.dashboard.psvr.biz.BizSessionRequest;
import com.tibco.cep.dashboard.psvr.common.query.QueryException;
import com.tibco.cep.dashboard.security.SecurityToken;
import com.tibco.cep.kernel.service.logging.Level;

public class ViewFieldConditionAction extends BaseEntityFieldsManagmentAction {

	@Override
	protected BizResponse doExecute(SecurityToken token, BizSessionRequest request) {
		String fieldName = request.getParameter("fieldname");
		if (StringUtil.isEmptyOrBlank(fieldName) == true) {
			return handleError(request, "Invalid field name");
		}
		BizSession session = request.getSession();
		QuerySpecModel queryMgrMdl = BizSessionSearchStore.getInstance(session).getQueryManagerModel();
		if (queryMgrMdl == null) {
			return handleError("Invalid session");
		}

		try {
			TupleSchema tupleSchema = TupleSchemaFactory.getInstance().getTupleSchema(queryMgrMdl.getTypeID());
			if (tupleSchema != null) {
				TupleSchemaField schemaField = tupleSchema.getFieldByName(fieldName);			
				if (schemaField != null && schemaField.isSystemField() == false) {
					DataTypeHandler dataTypeHandler = DataTypeHandlerFactory.getInstance().getDataTypeHandler(schemaField.getFieldDataType());
					QueryCondition condition = queryMgrMdl.getCondition(fieldName);
					request.setAttribute("fieldname", fieldName);
					request.setAttribute("supportedoperators", dataTypeHandler.getHighLevelOperators());
					request.setAttribute("currentconditionasstring", getConditionAsString(condition, schemaField));
					if (condition == null) {
						request.setAttribute("operatortoselect", dataTypeHandler.getDefaultHighLevelOperator());
						request.setAttribute("values", dataTypeHandler.getDefaultValues());
					} else {
						request.setAttribute("operatortoselect", dataTypeHandler.getHighLevelOperator(logger, condition));
						request.setAttribute("values", dataTypeHandler.getValues(logger, condition));
					}
					return VelocityViewHelper.getInstance().prepareRespone(request, dataTypeHandler.getDataTypeName() + "conditionview");
				}
				else {
					return handleError(request, "Invalid field name");
				}
			}
			else {
				return handleError(request, "Could not find requested entity");
			}
		} catch (QueryException e) {
			exceptionHandler.handleException(e, Level.ERROR);
			return handleError(request, e.getMessage());
		}
	}

}
