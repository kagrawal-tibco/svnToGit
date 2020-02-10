package com.tibco.rta.query.filter.impl;

import static com.tibco.rta.model.serialize.impl.ModelSerializationConstants.ELEM_FILTER_VALUE;
import static com.tibco.rta.model.serialize.impl.ModelSerializationConstants.ELEM_KEY_NAME;

import java.sql.Timestamp;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.tibco.rta.model.serialize.json.FilterValueTypeResolver;
import com.tibco.rta.query.filter.BaseRelationalFilter;

@XmlAccessorType(XmlAccessType.NONE)
public class BaseRelationalFilterImpl extends FilterImpl implements BaseRelationalFilter {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3839601875639613716L;
	
	protected String key;
	
	
	protected Object value;
	
	public BaseRelationalFilterImpl() {
		
	}
	
	public BaseRelationalFilterImpl(String key, Object value) {
		this.key = key;
		this.value = value;
	}
	
	@XmlElement(name=ELEM_KEY_NAME)
	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}
	
	
	@JsonDeserialize(using=FilterValueTypeResolver.class)
	@XmlElement(name=ELEM_FILTER_VALUE)
	public Object getValue() {
		if (value instanceof Timestamp) {
			/*return ((Timestamp)value).toString();*/
			return ((Timestamp)value);
		}
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	public String toString() {
		return "BRF:" ;
	}

}

