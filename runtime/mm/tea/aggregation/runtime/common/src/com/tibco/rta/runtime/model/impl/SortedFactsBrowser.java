package com.tibco.rta.runtime.model.impl;

import com.tibco.rta.Fact;
import com.tibco.rta.query.Browser;

/**
 * 
 * @author ntamhank
 * Decorator for Metric All facts browser. This sorts the facts based on key's natural ordering
 * 
 */
public abstract class SortedFactsBrowser<T extends Fact> implements Browser<T> {
	protected Browser<T> mFactsBrowser;
	
	public SortedFactsBrowser(Browser<T> factsBrowser) {
		mFactsBrowser = factsBrowser;
	}

}
