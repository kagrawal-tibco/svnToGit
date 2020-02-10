package com.tibco.cep.dashboard.psvr.common.query;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.tibco.cep.dashboard.common.data.DataType;

/**
 * @author anpatil
 *
 */
public final class QueryParams implements Cloneable {

	public static final QueryParams NO_QUERY_PARAMS = new QueryParams(false);

	private Map<String, String> valueMap;

	private Map<String, DataType> typeMap;

	private boolean editable;

	private QueryParams(boolean noneditable) {
		valueMap = new TreeMap<String, String>();
		typeMap = new TreeMap<String, DataType>();
		this.editable = noneditable;
	}

	public QueryParams() {
		this(true);
	}

	public void addParameter(String name, DataType type, String value) {
		if (editable == false) {
			throw new UnsupportedOperationException("Cannot add parameter to a non editable query params");
		}
		valueMap.put(name, value);
		typeMap.put(name, type);
	}

	public void clear() {
		if (editable == false) {
			throw new UnsupportedOperationException("Cannot clear a non editable query params");
		}
		valueMap.clear();
		typeMap.clear();
	}

	public String getParameter(String name) {
		return valueMap.get(name);
	}

	public DataType getParameterType(String name) {
		return typeMap.get(name);
	}

	public List<String> getParameterNames() {
		return new LinkedList<String>(valueMap.keySet());
	}

	public String removeParameter(String name) {
		String removed = valueMap.remove(name);
		if (removed != null){
			typeMap.remove(name);
		}
		return removed;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		QueryParams other = (QueryParams) obj;
		if (editable != other.editable)
			return false;
		if (typeMap == null) {
			if (other.typeMap != null)
				return false;
		} else if (!typeMap.equals(other.typeMap))
			return false;
		if (valueMap == null) {
			if (other.valueMap != null)
				return false;
		} else if (!valueMap.equals(other.valueMap))
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (editable ? 1231 : 1237);
		result = prime * result + ((typeMap == null) ? 0 : typeMap.hashCode());
		result = prime * result + ((valueMap == null) ? 0 : valueMap.hashCode());
		return result;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder("QueryParams[");
		builder.append("editable=");
		builder.append(editable);
		builder.append(",types=");
		builder.append(typeMap);
		builder.append(",values=");
		builder.append(valueMap);
		builder.append("]");
		return builder.toString();
	}

	public Map<String,String> getMap(){
		return new HashMap<String, String>(valueMap);
	}

	public Object clone() {
		QueryParams clone = new QueryParams();
		clone.editable = this.editable;
		clone.typeMap = new TreeMap<String, DataType>(this.typeMap);
		clone.valueMap = new TreeMap<String, String>(this.valueMap);
		return clone;
	}
}