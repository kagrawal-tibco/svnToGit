package com.tibco.cep.dashboard.plugin.beviews.drilldown.query.model;

import com.tibco.cep.dashboard.common.data.BuiltInTypes;
import com.tibco.cep.dashboard.common.data.FieldValue;
import com.tibco.cep.dashboard.common.data.TupleSchema;
import com.tibco.cep.dashboard.common.utils.ObjectUtil;

/**
 * A single projection field. e.g. field1 SUM(field1) field1 AS label
 */
public class QueryProjectionField extends QueryExpression {

	static final long serialVersionUID = 340166438179266958L;

	public final static String ALL_FIELDS = "*";

	public final static String AVG = "avg";
	public final static String COUNT = "count";
	public final static String FIRST = "first";
	public final static String LAST = "last";
	public final static String MAX = "max";
	public final static String MIN = "min";
	public final static String SUM = "sum";

	public static String[] sFunctions = new String[] { AVG, COUNT, FIRST, LAST, MAX, MIN, SUM };

	private String mFromFieldName;
	private String mFunction;
	private String mDisplayLabel;
	private String mFieldName;
	private String mFieldTypeId;

	public QueryProjectionField(TupleSchema schema, String fromFieldName) {
		this(schema, fromFieldName, null, null);
	}

	public QueryProjectionField(TupleSchema schema, String fromFieldName, String function) {
		this(schema, fromFieldName, function, null);
	}

	public QueryProjectionField(TupleSchema schema, String fromFieldName, String function, String displayLabel) {
		super(schema);
		validateArgs(schema, fromFieldName, function, displayLabel);
		mFromFieldName = fromFieldName;
		mFunction = function;
		mDisplayLabel = displayLabel;
		if (mFunction == null) {
			// Based on from field's name and type.
			mFieldTypeId = mSchema.getFieldIDByName(mFromFieldName);
		} else {
			if (mFunction.equals(FIRST) || mFunction.equals(LAST)) {
				// Based on from field's type.
				mFieldTypeId = mSchema.getFieldIDByName(mFromFieldName);
			} else {
				// It's just double.
				mFieldTypeId = BuiltInTypes.DOUBLE.getDataTypeID();
			}
		}
		// Figure out new field name.
		if (mDisplayLabel != null) {
			mFieldName = mDisplayLabel;
		} else {
			// New field name is constructed from the aggregration function name
			// if there is a function.
			if (mFromFieldName.equals(ALL_FIELDS) == true) {
				mFieldName = (mFunction == null) ? fromFieldName : "all" + "_" + mFunction;
			} else {
				mFieldName = (mFunction == null) ? fromFieldName : mFromFieldName + "_" + mFunction;
			}
		}
	}

	public void setAlias(String alias) {
		this.mAlias = alias;
	}

	private void validateArgs(TupleSchema schema, String fromFieldName, String function, String displayLabel) {
		if (schema == null) {
			throw new IllegalArgumentException("Invalid Schema");
		}
		if (fromFieldName == null || fromFieldName.trim().length() == 0) {
			throw new IllegalArgumentException("Invalid field [" + fromFieldName + "]");
		}
		if (fromFieldName.equals(ALL_FIELDS) == false && schema.isDynamic() == false && schema.hasField(fromFieldName) == false) {
			throw new IllegalArgumentException("Field [" + fromFieldName + "] is not part of schema");
		}

		if (function != null) {
			boolean invalidFunction = true;
			for (int i = 0; i < sFunctions.length; i++) {
				if (function.equals(sFunctions[i])) {
					invalidFunction = false;
					break;
				}
			}
			if (invalidFunction)
				throw new IllegalArgumentException("Invalid function " + function);
		}
	}

	@Override
	protected FieldValue eval() {
		throw new UnsupportedOperationException("eval");
	}

	@Override
	public void validate() throws IllegalArgumentException {
		validateArgs(mSchema, mFromFieldName, mFunction, mDisplayLabel);
	}

	public TupleSchema getSchema() {
		return this.mSchema;
	}

	public void setSchema(TupleSchema aSchema) {
		this.mSchema = aSchema;
	}

	public String getFromFieldName() {
		return this.mFromFieldName;
	}

	public String getFunction() {
		return this.mFunction;
	}

	public String getDisplayLabel() {
		return this.mDisplayLabel;
	}

	public String getFieldName() {
		return this.mFieldName;
	}

	public String getFieldTypeId() {
		return mFieldTypeId;
	}

	void formatProjectionField(StringBuilder output) throws Exception {
		//TODO: Check what to do here
		String srcTable = mSchema.getTypeName();//SchemaDBName.formatFullTableName(mSchema);
		String fromColumn = mFromFieldName;//SchemaDBName.formatColumnName(mSchema, mFromFieldName);

		if (mFunction != null) {
			output.append(mFunction).append("(").append(srcTable).append(".").append(fromColumn).append(")");
		} else {
			output.append(srcTable).append(".").append(fromColumn);
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj == this) {
			return true;
		}
		if ((obj instanceof QueryProjectionField) == false) {
			return false;
		}

		QueryProjectionField castedObj = (QueryProjectionField) obj;

		if (this.mSchema.equals(castedObj.mSchema) && this.mFromFieldName.equals(castedObj.mFromFieldName) && ObjectUtil.equalWithNull(this.mFunction, castedObj.mFunction)
				&& ObjectUtil.equalWithNull(this.mDisplayLabel, castedObj.mDisplayLabel)) {
			return true;
		}

		return false;
	}

	// toString() is used for hashcode calculation.
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		String fieldName = mFromFieldName;
		if (fieldName.equals(ALL_FIELDS) == true) {
			if (mAlias != null) {
				fieldName = mAlias;
			}
		}
		else {
//			fieldName = KeywordEscaper.escapeKeyword(mFromFieldName);
//			if (mAlias != null) {
//				if (mFromFieldName.startsWith("@") == true) {
//					fieldName = mAlias+fieldName;
//				}
//				else {
//					fieldName = mAlias+"."+fieldName;
//				}
//			}
			fieldName = FieldToStringConvertor.convert(mAlias, mFromFieldName);
		}
//		if (fieldName.equals(ALL_FIELDS) == false && mAlias != null) {
//			fieldName = mAlias+"."+mFromFieldName;
//		}
//		else if (fieldName.equals(ALL_FIELDS) == true && mAlias != null) {
//			fieldName = mAlias;
//		}
		if (mFunction != null) {
			sb.append(mFunction).append("(").append(fieldName).append(")");
		} else {
			sb.append(fieldName);
		}
		//sb.append(" AS ").append(mFieldName);
		return sb.toString();
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		QueryProjectionField queryProjectionField = new QueryProjectionField(mSchema, mFromFieldName, mFunction, mDisplayLabel);
		queryProjectionField.setAlias(mAlias);
		return queryProjectionField;
	}

//	public static String getAggregation(MDAggregationFuncEnum mdAggr) {
//		if (mdAggr.equals(MDAggregationFuncEnum.Avg)) {
//			return AVG;
//		} else if (mdAggr.equals(MDAggregationFuncEnum.Count)) {
//			return COUNT;
//		} else if (mdAggr.equals(MDAggregationFuncEnum.Max)) {
//			return MAX;
//		} else if (mdAggr.equals(MDAggregationFuncEnum.Min)) {
//			return MIN;
//		} else if (mdAggr.equals(MDAggregationFuncEnum.Sum)) {
//			return SUM;
//		}
//
//		return null;
//	}

}
