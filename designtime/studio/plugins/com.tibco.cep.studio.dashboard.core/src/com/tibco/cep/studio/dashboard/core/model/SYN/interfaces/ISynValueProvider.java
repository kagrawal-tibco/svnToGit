package com.tibco.cep.studio.dashboard.core.model.SYN.interfaces;

import java.util.List;

/**
 * An interface for elements that provide support for primitive values
 * 
 */
public interface ISynValueProvider {

	public abstract String getValue() throws Exception;

	public abstract List<String> getValues() throws Exception;

	public abstract void setValue(String value) throws Exception;

	public abstract void addValue(String value) throws Exception;

	public abstract void removeValue(String value) throws Exception;
}
