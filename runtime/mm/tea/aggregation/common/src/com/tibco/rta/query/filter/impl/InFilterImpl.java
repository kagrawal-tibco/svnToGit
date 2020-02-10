package com.tibco.rta.query.filter.impl;


import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;

import com.tibco.rta.query.FilterKeyQualifier;
import com.tibco.rta.query.MetricQualifier;
import com.tibco.rta.query.filter.InFilter;

/**
 * The Class InFilterImpl.
 */
public class InFilterImpl extends RelationalFilterImpl implements InFilter {
	
	private static final long serialVersionUID = 9160128275847230960L;
	
	@XmlElement
	protected List<Object> inSet = new ArrayList<Object>();

	/**
	 * Instantiates a new in filter.
	 */
	public InFilterImpl() {}
	
	/**
	 * Instantiates a new in filter.
	 *
	 * @param qualifier the qualifier
	 * @param value the value
	 */
	public InFilterImpl(MetricQualifier qualifier) {
		// in this case, the single value "value" of the parent class is not used.
		super(qualifier, null);
	}
	
	public InFilterImpl(FilterKeyQualifier qualifier, String key) {
		// in this case, the single value "value" of the parent class is not used.
		super(qualifier,key, null);
	}
	
	public String toString() {
		return "IN:";
	}

	@Override
	public void addToSet(Object... values) {		
		for (Object value : values) {
			if (value instanceof Timestamp) {
				inSet.add(((Timestamp)value).toString());
				continue;
			}
			inSet.add(value);			
	    }
	}

	@Override
	public List<Object> getInSet() {
		return inSet;
	}		
}