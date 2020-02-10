package com.tibco.rta.impl;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.tibco.rta.RtaAsyncMessage;
import com.tibco.rta.model.DataType;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class RtaAsyncMessageImpl implements RtaAsyncMessage {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3451561638707945084L;

    @JsonTypeInfo(use=JsonTypeInfo.Id.CLASS, include= JsonTypeInfo.As.PROPERTY, property="@class")
	protected Map<String, Datum> propertyMap = new ConcurrentHashMap<String, Datum>();

	@Override
	public List<String> getProperties() {
		return new ArrayList<String>(propertyMap.keySet());
	}

    public Map<String, Datum> getPropertyMap() {
        return propertyMap;
    }

    @Override
	public Object getValue(String propertyName) {
		Datum datum = propertyMap.get(propertyName);
		if (datum != null) {
			return datum.value;
		}
		return null;
	}

	@Override
	public DataType getDataType(String propertyName) {
		Datum datum = propertyMap.get(propertyName);
		if (datum != null) {
			return datum.dataType;
		}
		return null;
	}
	
	public void setProperty(String key, Object value) {
		DataType datatype;
		if (value instanceof Integer) {
			datatype = DataType.INTEGER;
		} else if (value instanceof Float) {
			datatype = DataType.DOUBLE;
		} else if (value instanceof Double) {
			datatype = DataType.DOUBLE;
		} else if (value instanceof String) {
			datatype = DataType.STRING;
		} else {
			datatype = DataType.OBJECT;
		}
        propertyMap.put(key, new Datum(key, value, datatype));
	}


	@Override
	public String toString() {
		StringBuilder str = new StringBuilder();
		
		for (Map.Entry<String, Datum> entry : propertyMap.entrySet()) {
            str.append("{key=").append(entry.getKey()).append(", datum=").append("}");
		}
		return str.toString();
	}

}
