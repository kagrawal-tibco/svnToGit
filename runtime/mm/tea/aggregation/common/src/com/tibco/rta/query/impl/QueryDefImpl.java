package com.tibco.rta.query.impl;

import static com.tibco.rta.model.serialize.impl.ModelSerializationConstants.ELEM_ORDERBY_TUPLE;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

import com.tibco.rta.query.FilterKeyQualifier;
import com.tibco.rta.query.MetricFieldTuple;
import com.tibco.rta.query.MetricQualifier;
import com.tibco.rta.query.QueryDef;

@XmlAccessorType(XmlAccessType.NONE)
abstract public class QueryDefImpl extends BaseQueryDefImpl implements QueryDef {

	private static final long serialVersionUID = 8504618934155070441L;

	protected List<MetricFieldTuple> orderBy = new ArrayList<MetricFieldTuple>();
	protected Map<String,List<String>> metricNames = new LinkedHashMap<String, List<String>>();
	

	public QueryDefImpl() {
		super();
	}

	@XmlElement(name=ELEM_ORDERBY_TUPLE)
	@Override
	public List<MetricFieldTuple> getOrderByTuples() {
		return orderBy;
	}
	
	public void addOrderByTuple(MetricQualifier qualifier) {
		orderBy.add(new MetricFieldTuple(qualifier));
	}
	
	public void addOrderByTuple(FilterKeyQualifier qualifier, String key) {
		orderBy.add(new MetricFieldTuple(qualifier, key));
	}


	@Override
	public void addMetricName(String measurementName, String metricName) {
		List<String> metricNameList = metricNames.get(measurementName);
		if (metricNameList == null) {
			metricNameList = new ArrayList<String>();
			metricNames.put(measurementName, metricNameList);
		}
		metricNameList.add(metricName);
	}
	
	@Override
	public String getUserName() {
		return userName;
	}

	@Override
	public void setUserName(String userName) {
		this.userName = userName;
	}

}
