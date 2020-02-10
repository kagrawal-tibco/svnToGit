/**
 * 
 */
package com.tibco.cep.studio.rms.core.utils;

/**
 * @author aathalye
 *
 */
public class LocationStore {
	
	private static final LocationStore store = new LocationStore();
	
	private String baseCheckoutPath;
	
	private String cachedLoginURL;
	
	private LocationStore() {}
	
	public static LocationStore newInstance() {
		return store;
	}

	/**
	 * @return the baseCheckoutPath
	 */
	public final String getBaseCheckoutPath() {
		return baseCheckoutPath;
	}

	/**
	 * @param baseCheckoutPath the baseCheckoutPath to set
	 */
	public final void setBaseCheckoutPath(String baseCheckoutPath) {
		this.baseCheckoutPath = baseCheckoutPath;
	}

	/**
	 * @return the cachedLoginURL
	 */
	public final String getCachedLoginURL() {
		return cachedLoginURL;
	}

	/**
	 * @param cachedLoginURL the cachedLoginURL to set
	 */
	public final void setCachedLoginURL(String cachedLoginURL) {
		this.cachedLoginURL = cachedLoginURL;
	}
	
	
}
