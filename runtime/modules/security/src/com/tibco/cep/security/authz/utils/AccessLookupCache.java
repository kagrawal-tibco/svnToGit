/**
 * 
 */
package com.tibco.cep.security.authz.utils;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import com.tibco.cep.security.authz.core.impl.ACLContext;
import com.tibco.cep.security.authz.permissions.actions.Permit;

/**
 * @author aathalye
 *
 */
public class AccessLookupCache {
	
	private ConcurrentMap<ACLContext, Permit> cache = new ConcurrentHashMap<ACLContext, Permit>();
	
	private volatile byte status;
	
	public AccessLookupCache() {
		status = CACHE_IN_USE;
	}
	
	private static final byte CACHE_IN_USE = 0x1;
	private static final byte CACHE_INVALID = 0x2;
	/**
	 * @param aclContext
	 * @param permit
	 */
	public void putCacheEntry(final ACLContext aclContext,
			                  final Permit permit) throws Exception {
		if (aclContext == null) {
			throw new IllegalArgumentException("Input parameter for a cache entry cannot be null");
		}
		if ((status & CACHE_IN_USE) == CACHE_IN_USE) {
			//Add a new entry atomically
			cache.putIfAbsent(aclContext, permit);
		} else {
			throw new Exception("The cache is not valid");
		}
	}
	
	/**
	 * @param aclContext
	 * @return
	 */
	public Permit get(final ACLContext aclContext) throws Exception {
		Permit permit = null;
		if (aclContext == null) {
			return null;
		}
		if ((status & CACHE_IN_USE) == CACHE_IN_USE) {
			if (!cache.isEmpty()) {
				permit = cache.get(aclContext);
			}
		} else {
			throw new Exception("Cannot query an invalid cache");
		}
		return permit;
	}
	
	/**
	 * @throws Exception
	 */
	public void invalidate() throws Exception {
		if (cache != null &&
				(status & CACHE_IN_USE) == CACHE_IN_USE) {
			//Clear the cache
			cache.clear();
			//Commented for the time being.
//			status = CACHE_INVALID;
		} else {
			throw new Exception("Cannot invalidate an invalid cache");
		}
	}
	
	/**
	 * @return
	 */
	public boolean isInUse() {
		return (status & CACHE_IN_USE) != 0;
	}
	
	/**
	 * @param context
	 * @return
	 */
	public boolean contains(final ACLContext context) {
		if (context == null) {
			return false;
		}
		return cache.containsKey(context);
	}
}
