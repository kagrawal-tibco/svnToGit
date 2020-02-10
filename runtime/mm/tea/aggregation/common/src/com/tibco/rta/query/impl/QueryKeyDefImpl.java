package com.tibco.rta.query.impl;

import static com.tibco.rta.model.serialize.impl.ModelSerializationConstants.ELEM_CUBE_NAME;
import static com.tibco.rta.model.serialize.impl.ModelSerializationConstants.ELEM_HIERARCHY_NAME;
import static com.tibco.rta.model.serialize.impl.ModelSerializationConstants.ELEM_KEY_NAME;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.tibco.rta.MetricKey;
import com.tibco.rta.impl.MetricKeyImpl;
import com.tibco.rta.query.FilterKeyQualifier;
import com.tibco.rta.query.MetricFieldTuple;
import com.tibco.rta.query.MetricQualifier;
import com.tibco.rta.query.QueryByKeyDef;

@XmlRootElement
@XmlAccessorType(XmlAccessType.NONE)
public class QueryKeyDefImpl extends QueryDefImpl implements QueryByKeyDef {

	private static final long serialVersionUID = -767864446926820837L;
	
	protected MetricKey key;

	public QueryKeyDefImpl() {
		super();
	}


	@XmlElement(name=ELEM_KEY_NAME, type=MetricKeyImpl.class)	
	@Override
	public MetricKey getQueryKey() {
		return key;
	}

	@Override
	public void setQueryKey(MetricKey key) {
		this.key = key;
	}


	@Override
	public String getSchemaName() {
		return key.getSchemaName();
	}

	
	@Override
	public String getCubeName() {
		return key.getCubeName();
	}
	
	@Override
	public String getHierarchyName() {
		return key.getDimensionHierarchyName();
	}
	
	@Override
	public String toString() {
		StringBuilder strBldr = new StringBuilder("KEY = ");
		strBldr.append(key.toString());
		strBldr.append(getOrderByQuerySubStr(getOrderByTuples()));
		return strBldr.toString();
	}
	
	private String getOrderByQuerySubStr(List<MetricFieldTuple> metricTuples) {
		if (metricTuples == null || metricTuples.isEmpty()) {
			return "";
		}
		StringBuilder query = new StringBuilder();
		query.append(" ORDER BY ");
		boolean isFirstTuple = true;
		for (MetricFieldTuple tuple : metricTuples) {
			if (!isFirstTuple) {
				query.append(", ");
			}
			isFirstTuple = false;
			if (tuple.getMetricQualifier() != null
					&& tuple.getMetricQualifier().equals(MetricQualifier.DIMENSION_LEVEL)) {
				query.append("dim_devel_name");
			} else if (tuple.getKeyQualifier() != null
					&& tuple.getKeyQualifier().equals(FilterKeyQualifier.DIMENSION_NAME)) {
				query.append(tuple.getKey());
			}
		}
		return query.toString();
	}

}
