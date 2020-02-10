package com.tibco.rta.query;

/**
 * 
 * An enumeration to specify the type of query
 *
 */
public enum QueryType {
	/**
	 * Snapshot query.
	 */
	SNAPSHOT,
	/**
	 * Streaming query.
	 */
	STREAMING,
	
	/**
	 * Both snapshot and streaming query.
	 */
	SNAPSHOT_AND_STREAMING,

}