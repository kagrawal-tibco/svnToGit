package com.tibco.cep.studio.dashboard.core.util;

import java.util.Iterator;
import java.util.Map;

/**
 *
 */
public class EnumHelper {
	
	/**
	 * Return a local name corr. to an MD name. The map keys should be MD names and the values local names.
	 * @param mdName
	 * @param map
	 * @return the local name if found, the MD name otherwise.
	 */
	public static String getLocalName(String mdName, Map<String,String> map) {
		return (String) lookUpByValue(mdName, map);
	}
	
	/**
	 * Return an MD name corr. to a local name. The map keys should be local names and the values md names.
	 * @param mdName
	 * @param map
	 * @return the MD name if found, the local name otherwise.
	 */
	public static String getMdName(String localName, Map<String,String> map) {
		return (String) lookUpByKey(localName, map);
	}
	
	public static String[] getLocalNames(Map<String,String> map) {
		return (String[]) map.keySet().toArray(new String[0]);
	}
	
	public static String[] getMdNames(Map<String,String> map) {
		return (String[]) map.values().toArray(new String[0]);
	}
	
	private static Object lookUpByKey(Object key, Map<?,?> map) {
		Object v = map.get(key);
		return (v != null) ? v : key;
	}
	
	private static Object lookUpByValue(Object value, Map<?,?> map) {
		Iterator<?> iter = map.keySet().iterator();
		while (iter.hasNext()) {
	        Object mapKey = iter.next();
	        Object mapObject = map.get(mapKey);
		    if (value.equals(mapObject))
		    	return mapKey;
		}
		return value;
	}
}
