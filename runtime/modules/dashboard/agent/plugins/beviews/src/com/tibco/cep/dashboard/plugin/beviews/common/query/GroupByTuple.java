package com.tibco.cep.dashboard.plugin.beviews.common.query;

import com.tibco.cep.dashboard.common.data.BuiltInTypes;
import com.tibco.cep.dashboard.common.data.DataType;
import com.tibco.cep.dashboard.common.data.FieldValue;
import com.tibco.cep.dashboard.common.data.Tuple;
import com.tibco.cep.dashboard.common.data.TupleSchema;
import com.tibco.cep.dashboard.plugin.beviews.data.TupleSchemaFactory;

public class GroupByTuple extends Tuple {

	public static final String FIELD_NAME_FIELDNAME = "fieldname";

	public static final String FIELD_NAME_FIELDVALUE = "fieldvalue";

	public static final String FIELD_NAME_GROUP_BY_AGGR = "count";

	private static final long serialVersionUID = -87888607001755484L;

	public GroupByTuple(String typeId, String fieldName, FieldValue fieldValue, long aggregateValue) {
		// assign the next in line tuple schema
		super(TupleSchemaFactory.getInstance().getDynamicBaseTupleSchema(typeId));
		// update the recently created schema
		updateSchema(getSchema(), fieldValue.getDataType());
		// populate the fields
		// field name
		fieldIdToFieldValueMap.put("0", new FieldValue(BuiltInTypes.STRING, fieldName));
		// field value
		fieldIdToFieldValueMap.put("1", fieldValue);
		// field value count
		fieldIdToFieldValueMap.put("2", new FieldValue(BuiltInTypes.LONG, aggregateValue));
	}

	private void updateSchema(TupleSchema tupleSchema, DataType fieldValueDataType) {
		// add field name
		tupleSchema.addField(0, FIELD_NAME_FIELDNAME, "0", BuiltInTypes.STRING, false, Long.MAX_VALUE);
		// add field value
		tupleSchema.addField(1, FIELD_NAME_FIELDVALUE, "1", fieldValueDataType, false, Long.MAX_VALUE);
		// add count field
		tupleSchema.addField(2, FIELD_NAME_GROUP_BY_AGGR, "2", BuiltInTypes.LONG, false, Long.MAX_VALUE);
	}

}