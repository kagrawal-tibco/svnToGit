package com.tibco.cep.dashboard.plugin.beviews.nextgendrilldown;

import java.util.ArrayList;
import java.util.List;

import com.tibco.cep.dashboard.common.data.FieldValue;
import com.tibco.cep.dashboard.common.data.Tuple;
import com.tibco.cep.dashboard.common.data.TupleSchema;
import com.tibco.cep.dashboard.common.data.TupleSchemaField;
import com.tibco.cep.dashboard.common.utils.StringUtil;
import com.tibco.cep.dashboard.plugin.beviews.common.query.ViewsQuery;
import com.tibco.cep.dashboard.plugin.beviews.data.TupleSchemaFactory;
import com.tibco.cep.dashboard.plugin.beviews.drilldown.query.model.QueryBinaryTerm;
import com.tibco.cep.dashboard.plugin.beviews.drilldown.query.model.QueryCondition;
import com.tibco.cep.dashboard.plugin.beviews.drilldown.query.model.QueryPredicate;
import com.tibco.cep.dashboard.plugin.beviews.drilldown.query.model.QuerySpec;
import com.tibco.cep.dashboard.plugin.beviews.mal.ReferenceDataType;
import com.tibco.cep.dashboard.psvr.common.query.QueryException;
import com.tibco.cep.dashboard.psvr.common.query.QueryExecutor;
import com.tibco.cep.dashboard.psvr.common.query.ResultSet;
import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.kernel.service.logging.Logger;

class ConceptDrilldownProvider implements TypeSpecificDrilldownProvider {

	@Override
	public QuerySpec getDrillDownQuery(Logger logger, Entity entity, String entityInstanceId, String nextInLineTypeId, QueryExecutor queryExecutor) throws QueryException {
		if (entity == null || StringUtil.isEmptyOrBlank(entityInstanceId) == true) {
			return null;
		}
		if (StringUtil.isEmptyOrBlank(nextInLineTypeId) == true) {
			// we have a null or blank source type id , skip it
			return null;
		}
		Tuple tuple = getTuple(entity, entityInstanceId, queryExecutor);
		if (tuple == null) {
			return null;
		}
		int fieldCount = tuple.getFieldCount();
		for (int i = 0; i < fieldCount; i++) {
			TupleSchemaField schemaField = tuple.getSchema().getFieldByPosition(i);
			if (schemaField.getFieldDataType() instanceof ReferenceDataType){
				ReferenceDataType dataType = (ReferenceDataType) schemaField.getFieldDataType();
				Entity referencedType = dataType.getReferenceType();
				if (referencedType.getGUID().equals(nextInLineTypeId) == true) {
					TupleSchema tupleSchema = TupleSchemaFactory.getInstance().getTupleSchema(referencedType);
					QuerySpec querySpec = new QuerySpec(tupleSchema);
					TupleSchemaField idField = tupleSchema.getIDField();
					if (schemaField.isArray() == true){
						Comparable<?>[] values = tuple.getFieldArrayValueByName(schemaField.getFieldName()).getValues();
						QueryCondition queryCondition = null;
						for (Comparable<?> value : values) {
							QueryPredicate valueCondition = new QueryPredicate(tupleSchema, idField.getFieldName(), QueryPredicate.EQ, new FieldValue(dataType.getPrimitiveDataType(),value));
							if (queryCondition == null){
								queryCondition = valueCondition;
							}
							else {
								queryCondition = new QueryBinaryTerm(queryCondition,QueryBinaryTerm.OR,valueCondition);
							}
						}
						querySpec.addAndCondition(queryCondition);
					}
					else {
						FieldValue idValue = new FieldValue(dataType.getPrimitiveDataType(),tuple.getFieldValueByName(schemaField.getFieldName()).getValue());
						querySpec.addAndCondition(new QueryPredicate(tupleSchema, idField.getFieldName(), QueryPredicate.EQ, idValue));
					}
					return querySpec;
				}
			}
		}
		return null;
	}

	@Override
	public List<Tuple> getNextInLines(Logger logger, Entity entity, String entityInstanceId, QueryExecutor queryExecutor) throws QueryException {
		List<Tuple> nextInLines = new ArrayList<Tuple>();
		Tuple tuple = getTuple(entity, entityInstanceId, queryExecutor);
		int fieldCount = tuple.getFieldCount();
		for (int i = 0; i < fieldCount; i++) {
			TupleSchemaField schemaField = tuple.getSchema().getFieldByPosition(i);
			if (schemaField.getFieldDataType() instanceof ReferenceDataType){
				if (tuple.isFieldNullByName(schemaField.getFieldName()) == false) {
					ReferenceDataType dataType = (ReferenceDataType) schemaField.getFieldDataType();
					Entity referencedType = dataType.getReferenceType();
					int count = 1;
					if (schemaField.isArray() == true){
						count = tuple.getFieldArrayValueByName(schemaField.getFieldName()).getValues().length;
					}
					nextInLines.add(new NextInLineTuple(referencedType.getGUID(), count));
				}
			}
		}
		return nextInLines;
	}

	private Tuple getTuple(Entity entity, String entityInstanceId, QueryExecutor queryExecutor) throws QueryException {
		TupleSchema tupleSchema = TupleSchemaFactory.getInstance().getTupleSchema(entity);
		TupleSchemaField idField = tupleSchema.getIDField();

		QuerySpec query = new QuerySpec(tupleSchema);
		query.setCondition(new QueryPredicate(tupleSchema, idField.getFieldName(), QueryPredicate.EQ, new FieldValue(idField.getFieldDataType(), idField.getFieldDataType().valueOf(entityInstanceId))));

		ResultSet resultSet = null;
		try {
			resultSet = queryExecutor.executeQuery(new ViewsQuery(query));
			if (resultSet.next() == true) {
				return resultSet.getTuple();
			}
			return null;
		} finally {
			if (resultSet != null) {
				resultSet.close();
			}
		}
	}



}
