package com.tibco.cep.bemm.model;

/**
 * Interface which provides the functionality to for version
 * 
 * @author dijadhav
 *
 */
public interface Versionable {
	/**
	 * Get Version
	 * 
	 * @return Version
	 */
	Long getVersion();

	/**
	 * Get key
	 * 
	 * @return Get Key
	 */
	Object getKey();

	/**
	 * Increment Version
	 * 
	 * @return Get incremented version
	 */
	Long incrementVersion();

}
