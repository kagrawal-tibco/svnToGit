package com.tibco.cep.dashboard.plugin.beviews.nextgendrilldown.path;

import com.tibco.cep.dashboard.common.data.DataType;
import com.tibco.cep.dashboard.common.data.FieldValue;
import com.tibco.cep.designtime.core.model.Entity;

public class FieldValueDrillDownTreePathElement extends TypeIDDrillDownTreePathElement {

	private String typeid;

	private String fieldName;

	private DataType fieldDataType;

	private FieldValue value;

	public FieldValueDrillDownTreePathElement(String token, Entity entity, String fieldName, DataType fieldDataType, FieldValue value) {
		super(token, entity);
		this.fieldName = fieldName;
		this.fieldDataType = fieldDataType;
		this.value = value;
		this.typeid = entity.getGUID();
	}

	public String getFieldName() {
		return fieldName;
	}

	public DataType getFieldDataType() {
		return fieldDataType;
	}

	public FieldValue getValue() {
		return value;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((token == null) ? 0 : token.hashCode());
		result = prime * result + ((typeid == null) ? 0 : typeid.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		FieldValueDrillDownTreePathElement other = (FieldValueDrillDownTreePathElement) obj;
		if (token == null) {
			if (other.token != null)
				return false;
		} else if (!token.equals(other.token))
			return false;
		if (typeid == null) {
			if (other.typeid != null)
				return false;
		} else if (!typeid.equals(other.typeid))
			return false;
		return true;
	}

}