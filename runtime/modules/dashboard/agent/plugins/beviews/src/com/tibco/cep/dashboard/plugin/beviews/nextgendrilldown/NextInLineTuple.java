package com.tibco.cep.dashboard.plugin.beviews.nextgendrilldown;

import com.tibco.cep.dashboard.common.data.BuiltInTypes;
import com.tibco.cep.dashboard.common.data.FieldValue;
import com.tibco.cep.dashboard.common.data.Tuple;
import com.tibco.cep.dashboard.common.data.TupleSchema;
import com.tibco.cep.dashboard.plugin.beviews.data.TupleSchemaFactory;

public class NextInLineTuple extends Tuple {
	
	public static final String FIELD_NAME_COUNT = "count";

	private static final long serialVersionUID = -1951788384867165944L;

	public NextInLineTuple(/* Tuple parent, */String typeId, int count) {
		// assign the next in line tuple schema
		super(TupleSchemaFactory.getInstance().getDynamicBaseTupleSchema(typeId));
		// update the recently created schema
		updateSchema(getSchema());
		// populate the fields
		fieldIdToFieldValueMap.put("0", new FieldValue(BuiltInTypes.LONG, count));
	}

	private void updateSchema(TupleSchema schema) {
		// add count field
		schema.addField(0, FIELD_NAME_COUNT, "0", BuiltInTypes.LONG, false, Long.MAX_VALUE);	
	}

}