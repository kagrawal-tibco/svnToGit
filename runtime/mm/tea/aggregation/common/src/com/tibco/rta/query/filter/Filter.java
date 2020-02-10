package com.tibco.rta.query.filter;

import java.io.Serializable;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.annotation.JsonTypeIdResolver;
import com.tibco.rta.model.serialize.jaxb.adapter.FilterAdapter;
import com.tibco.rta.model.serialize.json.FilterTypeIdResolver;

/**
 * A generic filter representation.
 * 
 */
@XmlJavaTypeAdapter(FilterAdapter.class)
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME,include = JsonTypeInfo.As.PROPERTY,property = "type")
@JsonTypeIdResolver(FilterTypeIdResolver.class)
public interface Filter extends Serializable {
	
	Filter getParent();
	
	void setParent(Filter parent);
	
}