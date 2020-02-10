package com.tibco.rta.query.filter.impl;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.tibco.rta.query.filter.Filter;
import com.tibco.rta.query.filter.LogicalFilter;

import static com.tibco.rta.model.serialize.impl.ModelSerializationConstants.*;

@XmlRootElement
@XmlAccessorType(XmlAccessType.NONE)
public class LogicalFilterImpl extends FilterImpl implements LogicalFilter {

	private static final long serialVersionUID = -569464680835182340L;

	protected List<Filter> filters = new ArrayList<Filter>();
	
	/**
	 * Instantiates a new or filter.
	 */
	public LogicalFilterImpl() {
		
	}
	
	public LogicalFilterImpl(Filter filter) {
		filters.add(filter);
	}
	
	/**
	 * Instantiates a new or filter.
	 *
	 * @param f the f
	 */
	public LogicalFilterImpl (Filter... filters) {
		for (Filter filter : filters ) {
			this.filters.add(filter);
			filter.setParent(this);
		}
	}
	
	/**
	 * Adds the filter.
	 *
	 * @param f the f
	 */
	public void addFilter (Filter... filters){
		for (Filter filter : filters ) {
			this.filters.add(filter);
			filter.setParent(this);
		}
	}
	
	@XmlElement(name=ELEM_FILTER_NAME)
	@Override
	public Filter[] getFilters() {
		// TODO Auto-generated method stub
		return filters.toArray(new Filter[]{});
	}
	
	public String toString() {
		String s = "LF:";
		for (int i=0; i<filters.size(); i++) {
			s += filters.get(i);
		}
		return s;
	}
}
