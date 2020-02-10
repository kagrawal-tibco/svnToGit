package com.tibco.cep.dashboard.integration.embedded;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.tibco.cep.dashboard.common.data.BuiltInTypes;
import com.tibco.cep.dashboard.common.data.FieldValue;
import com.tibco.cep.dashboard.common.data.Tuple;
import com.tibco.cep.dashboard.common.data.TupleSchema;
import com.tibco.cep.dashboard.common.data.TupleSchemaField;
import com.tibco.cep.dashboard.common.data.TupleSchemaSource;
import com.tibco.cep.dashboard.psvr.common.NonFatalException;
import com.tibco.cep.dashboard.psvr.data.DataException;
import com.tibco.cep.dashboard.psvr.data.DataSourceHandler;
import com.tibco.cep.dashboard.psvr.data.Threshold;
import com.tibco.cep.dashboard.psvr.mal.MALFieldMetaInfo;
import com.tibco.cep.dashboard.psvr.mal.model.MALActionRule;
import com.tibco.cep.dashboard.psvr.mal.model.MALSeriesConfig;
import com.tibco.cep.dashboard.psvr.mal.model.types.ThresholdUnitEnum;
import com.tibco.cep.dashboard.psvr.runtime.PresentationContext;
import com.tibco.cep.studio.dashboard.preview.SeriesDataSet;

public class EDataSourceHandler extends DataSourceHandler {

	static final String VALUE_FIELD_NAME = "value_field";
	static final String CATEGORY_FIELD_NAME = "category_field";

	private static final MALFieldMetaInfo VALUE_FIELD_INFO = new MALFieldMetaInfo(null, "value_fld_id", VALUE_FIELD_NAME, BuiltInTypes.DOUBLE, false, false);

	private TupleSchema tupleSchema;
	private List<Tuple> data;
	private MALFieldMetaInfo categoryField;
	private MALFieldMetaInfo[] outputFields;

	EDataSourceHandler(MALActionRule actionRule, SeriesDataSet seriesData) {
		if (seriesData == null || seriesData.getDataCount() == 0) {
			data = Collections.emptyList();
			categoryField = new MALFieldMetaInfo(null, "category_fld_id", CATEGORY_FIELD_NAME, BuiltInTypes.STRING, false, false);
			outputFields = new MALFieldMetaInfo[] { categoryField, VALUE_FIELD_INFO };
			this.tupleSchema = new ETupleSchema(outputFields);
		} else {
			data = new LinkedList<Tuple>();
			Map<String, FieldValue> tupleFieldValues = new HashMap<String, FieldValue>();
			Iterator<Comparable<?>> categoryValuesIter = seriesData.getCategoryValues();
			while (categoryValuesIter.hasNext()) {
				Comparable<?> categoryValue = (Comparable<?>) categoryValuesIter.next();
				if (tupleSchema == null) {
					categoryField = new MALFieldMetaInfo(null, "category_fld_id", CATEGORY_FIELD_NAME, (categoryValue instanceof Date ? BuiltInTypes.DATETIME : BuiltInTypes.STRING), false, false);
					outputFields = new MALFieldMetaInfo[] { categoryField, VALUE_FIELD_INFO };
					this.tupleSchema = new ETupleSchema(outputFields);
				}

				FieldValue categoryFieldValue = new FieldValue(categoryField.getDataType(), false);
				categoryFieldValue.setValue(categoryValue);
				tupleFieldValues.put(categoryField.getId(), categoryFieldValue);

				Number value = seriesData.getValue(categoryValue);
				FieldValue valueFieldValue = new FieldValue(VALUE_FIELD_INFO.getDataType(), false);
				valueFieldValue.setValue(value.doubleValue());
				tupleFieldValues.put(VALUE_FIELD_INFO.getId(), valueFieldValue);

				data.add(new Tuple(tupleSchema, tupleFieldValues));
			}
		}
		if (actionRule != null) {
			this.name = actionRule.getName();
			this.threshold = new Threshold(0, ThresholdUnitEnum.COUNT);
		} else {
			this.name = "";
			this.threshold = new Threshold(0, ThresholdUnitEnum.COUNT);
		}
	}

	@Override
	protected void configure(MALSeriesConfig seriesConfig, PresentationContext ctx) throws DataException {

	}

	@Override
	public List<Tuple> getData(PresentationContext ctx) throws DataException {
		return data;
	}

	@Override
	protected void shutdown() throws NonFatalException {

	}

	private class ETupleSchemaSource extends TupleSchemaSource {

		@Override
		public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {

		}

		@Override
		public void writeExternal(ObjectOutput out) throws IOException {

		}

	}

	private class ETupleSchema extends TupleSchema {

		private static final long serialVersionUID = 1L;

		ETupleSchema(MALFieldMetaInfo[] fields) {
			super(new ETupleSchemaSource(), false);
			int position = 0;
			for (MALFieldMetaInfo field : fields) {
				addField(position, new TupleSchemaField(field.getName(), field.getId(), field.getDataType(), Long.MAX_VALUE, false, null, null, false, false));
			}
		}
	}

	public MALFieldMetaInfo getCategoryField() {
		return categoryField;
	}

	public MALFieldMetaInfo getValueField() {
		return VALUE_FIELD_INFO;
	}

}
