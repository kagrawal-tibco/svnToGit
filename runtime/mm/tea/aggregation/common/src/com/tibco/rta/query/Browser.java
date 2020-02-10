package com.tibco.rta.query;

import java.util.Iterator;


/**
 * The browser interface used by snapshot/drill down queries.
 * 
 */
public interface Browser<T> extends Iterator<T> {

	void stop();
	
}