package com.tibco.cep.dashboard.plugin.beviews.drilldown.query.model;

import com.tibco.cep.dashboard.common.data.BuiltInTypes;
import com.tibco.cep.dashboard.common.data.FieldValue;
import com.tibco.cep.dashboard.common.data.TupleSchema;

/**
 * Represents a bind-able parameter
 */
public class QueryFieldName extends QueryExpression {

	private static final long serialVersionUID = 2709874639667275483L;

	private String fieldName;

	public QueryFieldName(TupleSchema schema, String fieldName) {
		super(schema);
		this.fieldName = fieldName;
	}

	public String getFieldName() {
		return fieldName;
	}

	@Override
	protected FieldValue eval() {
		return new FieldValue(BuiltInTypes.STRING, fieldName);
	}

	@Override
	public void validate() throws IllegalArgumentException {
		if (fieldName == null || fieldName.trim().length() == 0) {
			throw new IllegalArgumentException("Invalid field [" + fieldName + "]");
		}
		if (mSchema.isDynamic() == false && mSchema.hasField(fieldName) == false) {
			throw new IllegalArgumentException("Field [" + fieldName + "] is not part of schema");
		}
	}

	/**
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		return toString().equals(obj == null ? null : obj.toString());
	}

	@Override
	public String toString() {
//		String fieldName = KeywordEscaper.escapeKeyword(this.fieldName);
//		if (mAlias != null) {
//			if (fieldName.startsWith("@") == true) {
//				fieldName = mAlias+fieldName;
//			}
//			else {
//				fieldName = mAlias+"."+fieldName;
//			}
//		}
		return FieldToStringConvertor.convert(mAlias, this.fieldName);
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		QueryFieldName queryFieldName = new QueryFieldName(super.getSchema(), fieldName);
		queryFieldName.setAlias(mAlias);
		return queryFieldName;
	}

}
