package com.tibco.cep.dashboard.plugin.beviews.biz.querymgr.nextgen;

import java.util.List;
import java.util.Map;

import com.tibco.cep.dashboard.common.data.TupleSchema;
import com.tibco.cep.dashboard.common.data.TupleSchemaField;
import com.tibco.cep.dashboard.plugin.beviews.biz.BaseSessionCheckerAction;
import com.tibco.cep.dashboard.plugin.beviews.data.TupleSchemaFactory;
import com.tibco.cep.dashboard.plugin.beviews.drilldown.query.model.QueryCondition;
import com.tibco.cep.dashboard.plugin.beviews.drilldown.visengine.EntityVisualizer;
import com.tibco.cep.dashboard.plugin.beviews.querymgr.DataTypeHandler;
import com.tibco.cep.dashboard.plugin.beviews.querymgr.DataTypeHandlerFactory;
import com.tibco.cep.dashboard.plugin.beviews.search.QuerySpecModel;
import com.tibco.cep.dashboard.psvr.biz.BizResponse;
import com.tibco.cep.dashboard.psvr.biz.BizSessionRequest;
import com.tibco.cep.dashboard.psvr.common.query.QueryException;
import com.tibco.cep.dashboard.psvr.ogl.model.edm.QueryManagerEntity;
import com.tibco.cep.dashboard.psvr.ogl.model.edm.QueryManagerEntityField;
import com.tibco.cep.dashboard.psvr.ogl.model.edm.QueryManagerEntityFieldCondition;
import com.tibco.cep.dashboard.psvr.ogl.model.types.DataType;
import com.tibco.cep.dashboard.security.SecurityToken;
import com.tibco.cep.kernel.service.logging.Level;

public abstract class BaseQueryManagerAction extends BaseSessionCheckerAction {

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
		return handleError(errorMsg);
	}

	/**
	 * @deprecated
	 * @param tupleSchema
	 * @param queryMgrMdl
	 * @param qmEntity
	 */
	protected void populateQueryManagerEntity(TupleSchema tupleSchema, QuerySpecModel queryMgrMdl, QueryManagerEntity qmEntity){
		int fieldCount = tupleSchema.getFieldCount();
		for (int i = 0; i < fieldCount; i++) {
			TupleSchemaField schemaField = tupleSchema.getFieldByPosition(i);
			QueryManagerEntityField qmEntityField = new QueryManagerEntityField();
			if (schemaField.isSystemField() == false && schemaField.isArray() == false) {
				qmEntityField.setId(schemaField.getFieldID());
				qmEntityField.setName(schemaField.getFieldName());
				try {
					qmEntityField.setDataType(DataType.valueOf(schemaField.getFieldDataType().getDataTypeID()));
				} catch (IllegalArgumentException e) {
					qmEntityField.setDataType(DataType.STRING);
				}
				QueryManagerEntityFieldCondition condition = new QueryManagerEntityFieldCondition();

				QueryCondition qCondition = queryMgrMdl.getCondition(schemaField.getFieldName());
				if(qCondition != null) {
					try {
						DataTypeHandler dataTypeHandler = DataTypeHandlerFactory.getInstance().getDataTypeHandler(schemaField.getFieldDataType());
						String operator = dataTypeHandler.getHighLevelOperator(logger, qCondition);
						condition.setOperator(operator);
						List<Map<String,Object>> valuesList = dataTypeHandler.getRawValues(logger, qCondition);
						if(valuesList != null && valuesList.size()>0){
							String [] valuesArray = new String[valuesList.size()];
							int valCounter = 0;
							for(Map<String, Object> valueMap:valuesList){
								String value = (String)valueMap.get("value");
								valuesArray[valCounter++] = value;
							}
							condition.setValue(valuesArray);
						}

					} catch (QueryException e) {
						e.printStackTrace();
					}
				}
				else {
					// put default condition as 'Any'
					condition.setValue(new String[]{"Any"});
				}
				qmEntityField.setDefaultCondition(condition);
				qmEntity.addQueryManagerEntityField(qmEntityField);
			}
		}
	}

	protected void addFieldConditions(SecurityToken token, QueryManagerEntity queryManagerEntity, EntityVisualizer entityVisualizer, QuerySpecModel querySpecModel) {
		//MALSourceElement sourceElement = MALSourceElementCache.getInstance().getMALSourceElement(entityVisualizer.getTypeId());
		TupleSchema schema = querySpecModel != null ? querySpecModel.getTupleSchema() : TupleSchemaFactory.getInstance().getTupleSchema(entityVisualizer.getTypeId());
		//get the field to be shown
		Map<String, String> displayableFields = entityVisualizer.getDisplayableFields();
		for (Map.Entry<String, String> displayableFieldsEntry : displayableFields.entrySet()) {
			String name = displayableFieldsEntry.getKey();
			String displayName = displayableFieldsEntry.getValue();
			TupleSchemaField field = schema.getFieldByName(name);
			QueryManagerEntityField queryManagerEntityField = new QueryManagerEntityField();
			//set the id
			queryManagerEntityField.setId(field.getFieldID());
			//set the name as the display name
			queryManagerEntityField.setName(displayName);
			//set the data type
			try {
				queryManagerEntityField.setDataType(DataType.valueOf(field.getFieldDataType().getDataTypeID()));
			} catch (IllegalArgumentException e) {
				queryManagerEntityField.setDataType(DataType.STRING);
			}
			DataTypeHandler dataTypeHandler = DataTypeHandlerFactory.getInstance().getDataTypeHandler(field.getFieldDataType());
			//the actual condition
			QueryManagerEntityFieldCondition queryManagerEntityFieldCondition = new QueryManagerEntityFieldCondition();
			//set the operator to the default operation
			queryManagerEntityFieldCondition.setOperator(dataTypeHandler.getDefaultHighLevelOperator());
			//set the values to the default values
			//TODO decide if we should set the default value to Any.
			List<Map<String,Object>> rawValues = dataTypeHandler.getDefaultRawValues();
			for (Map<String, Object> rawValue : rawValues) {
				String value = (String)rawValue.get("value");
				queryManagerEntityFieldCondition.addValue(value);
			}
			//get the actual condition on the field
			if (querySpecModel != null) {
				//we use the name here , since the low level query spec understand only the names NOT the display name
				QueryCondition condition = querySpecModel.getCondition(name);
				if (condition != null) {
					try {
						//we have a condition, set it as the condition
						String highLevelOperator = dataTypeHandler.getHighLevelOperator(logger, condition);
						//add the values
						rawValues = dataTypeHandler.getRawValues(logger, condition);
						//set the operator
						queryManagerEntityFieldCondition.setOperator(highLevelOperator);
						//clear the value set
						queryManagerEntityFieldCondition.clearValue();
						for (Map<String, Object> rawValue : rawValues) {
							if (rawValue.get("value") != null) {
								String value = (String)(rawValue.get("value").toString());
								queryManagerEntityFieldCondition.addValue(value);
							}
						}
						//set the condition as the condition
						queryManagerEntityField.setCondition(queryManagerEntityFieldCondition);
					} catch (QueryException e) {
						String message = getMessage("querymanagermodel.condition.parsing.failure", getMessageGeneratorArgs(token, schema.getScopeName(), name));
						exceptionHandler.handleException(message, e, Level.WARN);
						//revert to default condition
						queryManagerEntityField.setDefaultCondition(queryManagerEntityFieldCondition);
					}
				}
			}
			else {
				//we do not have a condition, set a default condition
				queryManagerEntityField.setDefaultCondition(queryManagerEntityFieldCondition);
			}
			queryManagerEntity.addQueryManagerEntityField(queryManagerEntityField);
		}
	}
}
