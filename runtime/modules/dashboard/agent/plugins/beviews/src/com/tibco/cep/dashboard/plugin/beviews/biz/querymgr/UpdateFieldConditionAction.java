package com.tibco.cep.dashboard.plugin.beviews.biz.querymgr;

import com.tibco.cep.dashboard.common.data.TupleSchema;
import com.tibco.cep.dashboard.common.data.TupleSchemaField;
import com.tibco.cep.dashboard.common.utils.StringUtil;
import com.tibco.cep.dashboard.plugin.beviews.data.TupleSchemaFactory;
import com.tibco.cep.dashboard.plugin.beviews.drilldown.query.model.QueryCondition;
import com.tibco.cep.dashboard.plugin.beviews.querymgr.DataTypeHandler;
import com.tibco.cep.dashboard.plugin.beviews.querymgr.DataTypeHandlerFactory;
import com.tibco.cep.dashboard.plugin.beviews.search.BizSessionSearchStore;
import com.tibco.cep.dashboard.plugin.beviews.search.QuerySpecModel;
import com.tibco.cep.dashboard.psvr.biz.BizResponse;
import com.tibco.cep.dashboard.psvr.biz.BizSession;
import com.tibco.cep.dashboard.psvr.biz.BizSessionRequest;
import com.tibco.cep.dashboard.psvr.biz.RawBizResponseImpl;
import com.tibco.cep.dashboard.psvr.common.query.QueryException;
import com.tibco.cep.dashboard.security.SecurityToken;
import com.tibco.cep.kernel.service.logging.Level;

public class UpdateFieldConditionAction extends BaseEntityFieldsManagmentAction {

	@Override
	protected BizResponse doExecute(SecurityToken token, BizSessionRequest request) {
		String fieldName = request.getParameter("fieldname");
		if (StringUtil.isEmptyOrBlank(fieldName) == true) {
			return new RawBizResponseImpl("Invalid field name");
		}
		BizSession session = request.getSession();
		QuerySpecModel queryMgrMdl = BizSessionSearchStore.getInstance(session).getQueryManagerModel();
		if (queryMgrMdl == null) {
			return new RawBizResponseImpl("Invalid session");
		}
		String operator = request.getParameter("operator");
//		if (StringUtil.isEmptyOrBlank(operator) == true) {
//			return new RawBizResponseImpl("Invalid operator");
//		}
		String[] values = request.getParameterValues("value");
//		if (values == null || values.length == 0) {
//			return new RawBizResponseImpl("Invalid values");
//		}
		TupleSchema tupleSchema = TupleSchemaFactory.getInstance().getTupleSchema(queryMgrMdl.getTypeID());
		if (tupleSchema != null) {
			TupleSchemaField schemaField = tupleSchema.getFieldByName(fieldName);
			if (schemaField != null && schemaField.isSystemField() == false) {
				try {
					DataTypeHandler dataTypeHandler = DataTypeHandlerFactory.getInstance().getDataTypeHandler(schemaField.getFieldDataType());
					QueryCondition condition = dataTypeHandler.createQueryCondition(logger, queryMgrMdl.getTupleSchema(), fieldName, operator, values);
					logger.log(Level.INFO, "Added ["+condition+"] for ["+request.toString()+"]");
					queryMgrMdl.setCondition(fieldName, condition);
					return new RawBizResponseImpl(getConditionAsString(condition, schemaField));
				} catch (QueryException e) {
					exceptionHandler.handleException(e, Level.ERROR);
					return new RawBizResponseImpl(e.getMessage());
				}
			} else {
				return new RawBizResponseImpl("Invalid field name");
			}
		} else {
			return new RawBizResponseImpl("Could not find requested entity");
		}

	}

}
