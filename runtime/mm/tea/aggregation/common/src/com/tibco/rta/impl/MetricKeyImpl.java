package com.tibco.rta.impl;

import static com.tibco.rta.model.serialize.impl.ModelSerializationConstants.ELEM_CUBE_NAME;
import static com.tibco.rta.model.serialize.impl.ModelSerializationConstants.ELEM_DIMENSION_LEVEL;
import static com.tibco.rta.model.serialize.impl.ModelSerializationConstants.ELEM_DIMENSION_MAP;
import static com.tibco.rta.model.serialize.impl.ModelSerializationConstants.ELEM_HIERARCHY_NAME;
import static com.tibco.rta.model.serialize.impl.ModelSerializationConstants.ELEM_SCHEMA_NAME;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import com.tibco.rta.MetricKey;
import com.tibco.rta.model.serialize.jaxb.adapter.KeyDimensionMapAdapter;

@XmlAccessorType(XmlAccessType.NONE)
public class MetricKeyImpl implements MetricKey {
	
	private static final long serialVersionUID = 1L;

	protected String schemaName;
	protected String cubeName;
	protected String dimensionHierarchyName;
	protected String dimensionLevelName;
//	protected String measurementName;
	
	protected String stringKey;

	protected Map<String, Object> dimensionValuesMap = new LinkedHashMap<String, Object>();
	


	public MetricKeyImpl (String schemaName, String cubeName, String hierarchyName, String dimensionLevelName) {
		this.schemaName = schemaName;
		this.cubeName = cubeName;
		this.dimensionHierarchyName = hierarchyName;
		this.dimensionLevelName = dimensionLevelName;
//		this.measurementName = measurementName;
	}
	
	public MetricKeyImpl (MetricKey metricKey) {
		this.schemaName = metricKey.getSchemaName();
		this.cubeName = metricKey.getCubeName();
		this.dimensionHierarchyName = metricKey.getDimensionHierarchyName();
		this.dimensionLevelName = metricKey.getDimensionLevelName();
//		this.measurementName = metricKey.getMeasurementName();
		for (String dimNm : metricKey.getDimensionNames()) {
			dimensionValuesMap.put(dimNm, metricKey.getDimensionValue(dimNm));
		}
	}

    public MetricKeyImpl() {}
	
	public void addDimensionValueToKey(String dimensionName, Object dimensionValue) {
		dimensionValuesMap.put(dimensionName, dimensionValue);
	}
	
	@XmlElement(name=ELEM_SCHEMA_NAME)
	public String getSchemaName() {
		return schemaName;
	}

	@XmlElement(name=ELEM_CUBE_NAME)
	public String getCubeName() {
		return cubeName;
	}

	@XmlElement(name=ELEM_HIERARCHY_NAME)
	public String getDimensionHierarchyName() {
		return dimensionHierarchyName;
	}
	
	@XmlAttribute(name=ELEM_DIMENSION_LEVEL)
	public String getDimensionLevelName() {
		return dimensionLevelName;
	}
	
	
	public void setSchemaName(String schemaName) {
		this.schemaName = schemaName;
	}
	
	
	public void setCubeName(String cubeName) {
		this.cubeName = cubeName;
	}

	public void setDimensionHierarchyName(String dimensionHierarchyName) {
		this.dimensionHierarchyName = dimensionHierarchyName;
	}

	public void setDimensionLevelName(String dimensionLevelName) {
		this.dimensionLevelName = dimensionLevelName;
	}

//	public String getMeasurementName() {
//		return measurementName;
//	}
//
//	public void setMeasurementName(String measurementName) {
//		this.measurementName = measurementName;
//	}
	
	@XmlElement(name=ELEM_DIMENSION_MAP)
	@XmlJavaTypeAdapter(KeyDimensionMapAdapter.class)
	public void setDimensionValuesMap(Map<String, Object> dimensionValuesMap) {
			this.dimensionValuesMap = dimensionValuesMap;
	}
		
	public Map<String, Object> getDimensionValuesMap() {
		return dimensionValuesMap;
	}
	
	public List<String> getDimensionNames() {
		return new ArrayList<String>(dimensionValuesMap.keySet());
	}
	public Object getDimensionValue(String dimensionName) {
		return dimensionValuesMap.get(dimensionName);
	}

	@Override
	public boolean equals (Object keyObj) {
		if (! (keyObj instanceof MetricKeyImpl)) {
			return false;
		}
		MetricKeyImpl key = (MetricKeyImpl) keyObj;
		
		return strEql (schemaName, key.schemaName) &&
			strEql (cubeName, key.cubeName) &&
			strEql (dimensionHierarchyName, key.dimensionHierarchyName) &&
			strEql (dimensionLevelName, key.dimensionLevelName) &&
//			strEql (measurementName, key.measurementName) &&
			dimensionValuesMap.equals(key.dimensionValuesMap);
	}
	
	@Override
	public int hashCode() {
		int hashCode = super.hashCode();
	
		if (schemaName != null) {
			hashCode = schemaName.hashCode();
		}
		if (cubeName != null) {
			hashCode = hashCode + cubeName.hashCode();
		}
		if (dimensionHierarchyName != null) {
			hashCode = hashCode + dimensionHierarchyName.hashCode();
		}
		if (dimensionLevelName != null) {
			hashCode = hashCode + dimensionLevelName.hashCode();
		}
//		if (measurementName != null) {
//			hashCode = hashCode + measurementName.hashCode();
//		}
		return hashCode + dimensionValuesMap.hashCode();
	}
	
	@Override
	public String toString() {
		if (stringKey == null) {
//		StringBuilder strBldr = new StringBuilder(schemaName + "/" + cubeName + "/" + dimensionHierarchyName + "/" + dimensionLevelName);
		StringBuilder strBldr = new StringBuilder(dimensionLevelName);
		for (Map.Entry<String, Object> e : dimensionValuesMap.entrySet()) {
			// strBldr.append("/" + e.getKey() + "=" + e.getValue());
			strBldr.append("/" + e.getValue());
			// exclude dimensions below current level, which are null
			if(e.getKey().equals(dimensionLevelName)){
				break;
			}			
		}
		stringKey = strBldr.toString();
		}
		return stringKey;
	}
	
	boolean strEql (String str1, String str2) {
		if (str1 == str2) {
			return true;
		} else if (str1 != null && str1.equals(str2)) {
			return true;
		}
		return false;
	}

	@Override
	public int compareTo(Object other) {
		return toString().compareTo(other.toString());
	}
	
}