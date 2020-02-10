package com.tibco.rta.query.filter.impl;


import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import com.tibco.rta.query.filter.AndFilter;
import com.tibco.rta.query.filter.Filter;

import static com.tibco.rta.model.serialize.impl.ModelSerializationConstants.*;

// TODO: Auto-generated Javadoc
/**
 * The Class AndFilter.
 */
@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement(name=ELEM_AND_FILTER)
public class AndFilterImpl extends LogicalFilterImpl implements AndFilter {

	private static final long serialVersionUID = 6332517856714020267L;

	/**
	 * Instantiates a new and filter.
	 */
	public AndFilterImpl(){
		
	}
	
	/**
	 * Instantiates a new and filter.
	 */
	public AndFilterImpl(Filter... filter) {
		super(filter);
	}
	/**
	 * Adds the filter.
	 *
	 * @param f the f
	 */
	public void addFilter (Filter... filters){
		super.addFilter(filters);
	}
	
	public static void main(String[] args) {
	}
	
		
	public String toString() {
		String s =  "AND:";
		for (Filter f : filters) {
			s += f;
		}
		return s;
	}

}