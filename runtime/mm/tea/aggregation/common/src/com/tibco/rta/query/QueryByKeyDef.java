package com.tibco.rta.query;

import com.tibco.rta.MetricKey;

/**
 * Define query using partial or full keys
 */
public interface QueryByKeyDef extends QueryDef {
	
	/**
	 * Get the key associated with this query
	 * @return
	 */
	MetricKey getQueryKey ();
	
	/**
	 * Set the key associated with this query.
	 * @param key
	 */
	void setQueryKey(MetricKey key);

}
