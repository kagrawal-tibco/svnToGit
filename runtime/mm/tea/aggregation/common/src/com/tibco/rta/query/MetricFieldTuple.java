package com.tibco.rta.query;

import static com.tibco.rta.model.serialize.impl.ModelSerializationConstants.ATTR_TYPE_NAME;
import static com.tibco.rta.model.serialize.impl.ModelSerializationConstants.ATTR_VALUE_NAME;
import static com.tibco.rta.model.serialize.impl.ModelSerializationConstants.ELEM_METRIC_QUALIFIER;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlElement;

/**
 * 
 * Allows to create "order by" for queries
 *
 */
public class MetricFieldTuple implements Serializable {

	private static final long serialVersionUID = -874446784067427797L;
	
	@XmlElement(name=ELEM_METRIC_QUALIFIER)
	protected MetricQualifier metricQualifier;
	
	@XmlElement(name=ATTR_TYPE_NAME)
	protected FilterKeyQualifier keyQualifier;
	
	@XmlElement(name=ATTR_VALUE_NAME)
	protected String key;
	
	protected MetricFieldTuple() {
		
	}
	
	public MetricFieldTuple (MetricQualifier metricQualifier) {
		this.metricQualifier = metricQualifier;
	}
	
	public MetricFieldTuple (FilterKeyQualifier keyQualifier, String key) {
		this.keyQualifier = keyQualifier;
		this.key = key;
	}	
	
	public MetricQualifier getMetricQualifier() {
		return metricQualifier;
	}	
	
	public FilterKeyQualifier getKeyQualifier() {
		return keyQualifier;
	}	
	
	public String getKey() {
		return key;
	}

}
