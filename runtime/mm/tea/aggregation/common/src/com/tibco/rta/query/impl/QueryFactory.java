package com.tibco.rta.query.impl;

import com.tibco.rta.query.FilterKeyQualifier;
import com.tibco.rta.query.MetricQualifier;
import com.tibco.rta.query.QueryByFilterDef;
import com.tibco.rta.query.QueryByKeyDef;
import com.tibco.rta.query.filter.EqFilter;
import com.tibco.rta.query.filter.GEFilter;
import com.tibco.rta.query.filter.GtFilter;
import com.tibco.rta.query.filter.InFilter;
import com.tibco.rta.query.filter.LEFilter;
import com.tibco.rta.query.filter.LikeFilter;
import com.tibco.rta.query.filter.LtFilter;
import com.tibco.rta.query.filter.NEqFilter;
import com.tibco.rta.query.filter.impl.EqFilterImpl;
import com.tibco.rta.query.filter.impl.GEFilterImpl;
import com.tibco.rta.query.filter.impl.GtFilterImpl;
import com.tibco.rta.query.filter.impl.InFilterImpl;
import com.tibco.rta.query.filter.impl.LEFilterImpl;
import com.tibco.rta.query.filter.impl.LikeFilterImpl;
import com.tibco.rta.query.filter.impl.LtFilterImpl;
import com.tibco.rta.query.filter.impl.NEqFilterImpl;

public class QueryFactory extends BaseQueryFactory {

    public static final QueryFactory INSTANCE = new QueryFactory();
	
	public QueryFactory () {
		
	}
	
	public EqFilter newEqFilter(MetricQualifier qualifier,Object value) {
		return new EqFilterImpl(qualifier, value);
	}
	
	public EqFilter newEqFilter(FilterKeyQualifier qualifier, String name, Object value) {
		return new EqFilterImpl(qualifier, name, value);
	}
	
	public NEqFilter newNEqFilter(MetricQualifier qualifier,Object value) {
		return new NEqFilterImpl(qualifier, value);
	}
	
	public NEqFilter newNEqFilter(FilterKeyQualifier qualifier, String name, Object value) {
		return new NEqFilterImpl(qualifier, name, value);
	}

	public GEFilter newGEFilter(MetricQualifier qualifier, Object value) throws Exception {
		if (qualifier == MetricQualifier.DIMENSION_LEVEL) {
			throw new Exception("NA");
		}
		return new GEFilterImpl(qualifier, value);
	}
	
	public GtFilter newGtFilter(MetricQualifier qualifier, Object value) throws Exception {
		if (qualifier == MetricQualifier.DIMENSION_LEVEL) {
			throw new Exception("NA");
		}
		return new GtFilterImpl(qualifier, value);
	}
	
	public LEFilter newLEFilter(MetricQualifier qualifier,Object value) throws Exception {
		if (qualifier == MetricQualifier.DIMENSION_LEVEL) {
			throw new Exception("NA");
		}
		return new LEFilterImpl(qualifier,value);
	}
	
	public LtFilter newLtFilter(MetricQualifier qualifier, Object value) throws Exception {
		if (qualifier == MetricQualifier.DIMENSION_LEVEL) {
			throw new Exception("NA");
		}
		return new LtFilterImpl(qualifier, value);
	}
	
	public GEFilter newGEFilter(FilterKeyQualifier qualifier, String name, Object value) {
		return new GEFilterImpl(qualifier, name, value);
	}
	
	public GtFilter newGtFilter(FilterKeyQualifier qualifier, String name, Object value) {
		return new GtFilterImpl(qualifier, name, value);
	}
	
	public LEFilter newLEFilter(FilterKeyQualifier qualifier, String name, Object value) {
		return new LEFilterImpl(qualifier, name, value);
	}
	
	public LtFilter newLtFilter(FilterKeyQualifier qualifier, String name, Object value) {
		return new LtFilterImpl(qualifier, name, value);
	}
	
	public LikeFilter newLikeFilter(FilterKeyQualifier qualifier, String name, String valueRegEx) {
		return new LikeFilterImpl(qualifier, name, valueRegEx);
	}

	
	public LikeFilter newLikeFilter(MetricQualifier qualifier, String valueRegEx) {
		return new LikeFilterImpl(qualifier ,valueRegEx);
	}
	
	public InFilter newInFilter(FilterKeyQualifier qualifier, String name) {
		return new InFilterImpl(qualifier, name);
	}	
	
	public InFilter newInFilter(MetricQualifier qualifier) {
		return new InFilterImpl(qualifier);
	}	

	public QueryByFilterDef newQueryByFilterDef(String schemaName, String cubeName, String hierarchyName, String measurementName) {
		return new QueryFilterDefImpl(schemaName, cubeName, hierarchyName, measurementName);
	}
	
	public QueryByKeyDef newQueryByKeyDef() {
		return new QueryKeyDefImpl();
	}

}
