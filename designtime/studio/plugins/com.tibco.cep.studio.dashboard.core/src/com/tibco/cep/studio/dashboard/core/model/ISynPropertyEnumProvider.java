package com.tibco.cep.studio.dashboard.core.model;

import java.util.List;

/**
 * @author ssrinivasan
 * 
 */
public interface ISynPropertyEnumProvider {
	
	List<Object> getEnumerations(String propertyName);

	void addEnumeration(String propertyName, Object o);

	void setEnumerations(String propertyName, List<Object> l);

	void removeEnumeration(String propertyName, Object o);

	public Object cloneThis() throws Exception;
}