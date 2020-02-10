package com.tibco.cep.dashboard.plugin.beviews.drilldown.query;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.tibco.cep.dashboard.common.data.FieldValue;
import com.tibco.cep.dashboard.common.data.Tuple;
import com.tibco.cep.dashboard.common.data.TupleSchema;
import com.tibco.cep.dashboard.common.data.TupleSchemaField;
import com.tibco.cep.dashboard.common.utils.StringUtil;
import com.tibco.cep.dashboard.plugin.beviews.data.TupleSchemaFactory;
import com.tibco.cep.dashboard.plugin.beviews.drilldown.model.NextInLine;
import com.tibco.cep.dashboard.plugin.beviews.drilldown.query.model.QueryBinaryTerm;
import com.tibco.cep.dashboard.plugin.beviews.drilldown.query.model.QueryCondition;
import com.tibco.cep.dashboard.plugin.beviews.drilldown.query.model.QueryPredicate;
import com.tibco.cep.dashboard.plugin.beviews.drilldown.query.model.QuerySpec;
import com.tibco.cep.dashboard.plugin.beviews.drilldown.visengine.EntityVisualizerProvider;
import com.tibco.cep.dashboard.plugin.beviews.mal.ReferenceDataType;
import com.tibco.cep.dashboard.psvr.common.query.QueryException;
import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.kernel.service.logging.Logger;

class ConceptDrilldownProvider implements TypeSpecificDrilldownProvider {

	@Override
	public QuerySpec getDrillDownQuery(Logger logger, Tuple tuple, String typeId) {
		if (tuple == null) {
			return null;
		}
		if (StringUtil.isEmptyOrBlank(typeId) == true) {
			// we have a null or blank source type id , skip it
			return null;
		}
		int fieldCount = tuple.getFieldCount();
		for (int i = 0; i < fieldCount; i++) {
			TupleSchemaField schemaField = tuple.getSchema().getFieldByPosition(i);
			if (schemaField.getFieldDataType() instanceof ReferenceDataType){
				ReferenceDataType dataType = (ReferenceDataType) schemaField.getFieldDataType();
				Entity referencedType = dataType.getReferenceType();
				if (referencedType.getGUID().equals(typeId) == true) {
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
	public List<NextInLine> getNextInLines(Logger logger, Tuple tuple, Map<String, String> additionalParameters, DrilldownProvider drilldownProvider) throws QueryException {
		List<NextInLine> nextInLines = new ArrayList<NextInLine>();
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
					NextInLine nextInLine = new NextInLine(tuple.getTypeId(),tuple.getId(),EntityVisualizerProvider.getInstance().getEntityVisualizer(referencedType).getName(),referencedType.getGUID(),count);
					nextInLines.add(nextInLine);
				}
			}
		}
		return nextInLines;
	}

}
