package com.tibco.rta.query.filter.impl;

import static com.tibco.rta.model.serialize.impl.ModelSerializationConstants.*;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.tibco.rta.query.FilterKeyQualifier;
import com.tibco.rta.query.MetricQualifier;
import com.tibco.rta.query.filter.RelationalFilter;

@XmlRootElement
@XmlAccessorType(XmlAccessType.NONE)
public class RelationalFilterImpl extends BaseRelationalFilterImpl implements RelationalFilter {
	
	private static final long serialVersionUID = -4091928712830031732L;

	protected MetricQualifier metricQualifier;
	protected FilterKeyQualifier keyQualifier;

	public RelationalFilterImpl() {
		
	}
	
	public RelationalFilterImpl(MetricQualifier metricQualifier, Object value) {
		super(null, value);
		this.metricQualifier = metricQualifier;
	}
	
	public RelationalFilterImpl(FilterKeyQualifier keyQualifier, String key, Object value) {
		super(key, value);
		this.keyQualifier = keyQualifier;
	}
	
	@XmlElement(name=ELEM_METRIC_QUALIFIER)
	public MetricQualifier getMetricQualifier() {
		return metricQualifier;
	}

	public void setMetricQualifier(MetricQualifier qualifier) {
		this.metricQualifier = qualifier;
	}

	@XmlElement(name=ELEM_FILTER_QUALIFIER)
	public FilterKeyQualifier getKeyQualifier() {
		return keyQualifier;
	}

	public void setKeyQualifier(FilterKeyQualifier keyQualifier) {
		this.keyQualifier = keyQualifier;
	}
	
	public String toString() {
		return "RF:" ;
	}

}

