package com.tibco.cep.dashboard.psvr.mal;

import java.util.HashMap;
import java.util.Map;

import com.tibco.cep.dashboard.common.data.BuiltInTypes;
import com.tibco.cep.dashboard.common.data.DataType;
import com.tibco.cep.dashboard.common.utils.StringUtil;
import com.tibco.cep.designtime.core.model.PROPERTY_TYPES;
import com.tibco.cep.designtime.core.model.element.PropertyDefinition;

/**
 * @author apatil
 * 
 */
public class MALFieldMetaInfo {

	protected String id;

	protected String name;

	protected DataType dataType;

	protected boolean groupBy;
	
	protected boolean multi;

	protected Map<String, Object> attributes;

	protected Object source;

	/**
	 * @deprecated
	 * @param object
	 */
	protected MALFieldMetaInfo(Object object) {
		if (object instanceof PropertyDefinition) {
			PropertyDefinition property = (PropertyDefinition) object;
			this.id = property.getGUID();
			this.name = property.getName();
			PROPERTY_TYPES type = property.getType();
			dataType = BuiltInTypes.resolve(type.toString());
			if (dataType == null) {
				throw new IllegalArgumentException("Unknown datatype [" + type + "]");
			}
			groupBy = property.isGroupByField();
			multi = property.isArray();
			this.attributes = new HashMap<String, Object>();
		} else {
			throw new IllegalArgumentException("Unknown object type[" + object.getClass().getName() + "]");
		}
	}

	/**
	 * @param id
	 * @param name
	 * @param dataType
	 * @param groupBy
	 */
	public MALFieldMetaInfo(Object source, String id, String name, DataType dataType, boolean groupBy, boolean multi) {
		this.source = source;
		this.id = id;
		this.name = name;
		this.dataType = dataType;
		this.groupBy = groupBy;
		this.multi = multi;
		this.attributes = new HashMap<String, Object>();
		if (StringUtil.isEmptyOrBlank(this.id) == true){
			this.id = this.name;
		}
		if (StringUtil.isEmptyOrBlank(this.id) == true){
			throw new IllegalArgumentException("id and name cannot be null");
		}
	}

	public DataType getDataType() {
		return dataType;
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public boolean isDate() {
		if (dataType == BuiltInTypes.DATETIME) {
			return true;
		}
		return false;
	}

	public boolean isNumeric() {
		if (dataType == BuiltInTypes.INTEGER) {
			return true;
		}
		if (dataType == BuiltInTypes.FLOAT) {
			return true;
		}
		if (dataType == BuiltInTypes.LONG) {
			return true;
		}
		if (dataType == BuiltInTypes.DOUBLE) {
			return true;
		}
		if (dataType == BuiltInTypes.SHORT) {
			return true;
		}
		return false;
	}

	public String toString() {
		StringBuffer buffer = new StringBuffer("com.tibco.cep.dashboard.core.mal.MALFieldMetaInfo[");
		buffer.append("id=" + id);
		buffer.append(",name=" + name);
		buffer.append(",dataType=" + dataType);
		buffer.append("]");
		return buffer.toString();
	}

	public boolean equals(Object obj) {
		if (obj == this) {
			return true;
		}
		if ((obj instanceof MALFieldMetaInfo) == false) {
			return false;
		}
		MALFieldMetaInfo castedObj = (MALFieldMetaInfo) obj;
		if (castedObj.id.equals(this.id) == false) {
			return false;
		}
		if (castedObj.name.equals(this.name) == false) {
			return false;
		}
		if (castedObj.dataType.equals(this.dataType) == false) {
			return false;
		}
		return true;
	}

	public Object getAttribute(String attributeName) {
		return this.attributes.get(attributeName);
	}

	public void addAttribute(String attributeName, Object attributeValue) {
		this.attributes.put(attributeName, attributeValue);
	}

	public Object removeAttribute(String attributeName) {
		return this.attributes.remove(attributeName);
	}

	public boolean isGroupBy() {
		return groupBy;
	}
	
	public boolean isMulti() {
		return multi;
	}
	
	public Object getSource(){
		return source;
	}

}