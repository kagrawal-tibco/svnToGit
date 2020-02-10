package com.tibco.cep.dashboard.plugin.beviews.biz.querymgr;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.tibco.cep.dashboard.common.data.TupleSchema;
import com.tibco.cep.dashboard.common.data.TupleSchemaField;
import com.tibco.cep.dashboard.common.utils.StringUtil;
import com.tibco.cep.dashboard.plugin.beviews.data.TupleSchemaFactory;
import com.tibco.cep.dashboard.plugin.beviews.drilldown.query.model.QuerySpec;
import com.tibco.cep.dashboard.plugin.beviews.runtime.VelocityViewHelper;
import com.tibco.cep.dashboard.plugin.beviews.search.BizSessionSearchStore;
import com.tibco.cep.dashboard.plugin.beviews.search.IncompatibleQueryException;
import com.tibco.cep.dashboard.plugin.beviews.search.QuerySpecModel;
import com.tibco.cep.dashboard.psvr.biz.BizResponse;
import com.tibco.cep.dashboard.psvr.biz.BizSession;
import com.tibco.cep.dashboard.psvr.biz.BizSessionRequest;
import com.tibco.cep.dashboard.security.SecurityToken;

public class GetEntityFieldsAction extends BaseEntityFieldsManagmentAction {

	@Override
	protected BizResponse doExecute(SecurityToken token, BizSessionRequest request) {
		try {
			String viewId = "success";
			BizSession session = request.getSession();
			BizSessionSearchStore searchStore = BizSessionSearchStore.getInstance(session);
			QuerySpecModel queryMgrMdl = searchStore.getQueryManagerModel();
			String typeid = request.getParameter("typeid");
			if (StringUtil.isEmptyOrBlank(typeid) == false) {
				// we have a type id from the front-end
				if (queryMgrMdl == null) {
					//we don't have a query manager model spec, create a new one
					searchStore.setQuerySpecs(Arrays.asList(new QuerySpec(TupleSchemaFactory.getInstance().getTupleSchema(typeid))));
					searchStore.createQueryManagerModel();
					queryMgrMdl = searchStore.getQueryManagerModel();
				}
				else if (queryMgrMdl.getTypeID().equals(typeid) == false) {
					// the type id does not match the query manager query spec, reset the query manager model
					queryMgrMdl.setQuerySpec(new QuerySpec(TupleSchemaFactory.getInstance().getTupleSchema(typeid)));
				}
				String typeID = queryMgrMdl.getTypeID();
				TupleSchema tupleSchema = TupleSchemaFactory.getInstance().getTupleSchema(typeID);
				if (tupleSchema != null) {
					List<Map<String, String>> fields = new LinkedList<Map<String, String>>();
					int fieldCount = tupleSchema.getFieldCount();
					for (int i = 0; i < fieldCount; i++) {
						TupleSchemaField schemaField = tupleSchema.getFieldByPosition(i);
						if (schemaField.isSystemField() == false && schemaField.isArray() == false) {
							Map<String, String> fieldInfo = new HashMap<String, String>();
							fieldInfo.put("name", schemaField.getFieldName());
							fieldInfo.put("displayname", schemaField.getFieldName());
							fieldInfo.put("datatype", schemaField.getFieldDataType().getDataTypeID());
							fieldInfo.put("condition", getConditionAsString(queryMgrMdl.getCondition(schemaField.getFieldName()),schemaField));
							fieldInfo.put("multiple", Boolean.toString(schemaField.isArray()));
							fields.add(fieldInfo);
						}
					}
					request.setAttribute("fields", fields);
				} else {
					return handleError(request, "Could not find requested entity");
				}
			}
			return VelocityViewHelper.getInstance().prepareRespone(request, viewId);
		} catch (IncompatibleQueryException e) {
			return handleError(request, e.getMessage());
		}
	}

}