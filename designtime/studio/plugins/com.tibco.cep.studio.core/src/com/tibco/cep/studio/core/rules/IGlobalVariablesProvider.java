package com.tibco.cep.studio.core.rules;

/**
 * Interface to provide method to check for global variables.
 * @author apsharma
 *
 */
public interface IGlobalVariablesProvider {

	public boolean isGlobalVariable(String variable);
}
