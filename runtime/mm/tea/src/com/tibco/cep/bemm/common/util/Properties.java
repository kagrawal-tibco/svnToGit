package com.tibco.cep.bemm.common.util;

import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA. User: mgharat Date: 5/29/14 Time: 2:01 PM To
 * change this template use File | Settings | File Templates.
 */
public class Properties {

	Map<String, String> propertyParams;

	public Properties() {
		propertyParams = new HashMap<>();
	}

	public void put(String key, String value) {
		propertyParams.put(key, value);
	}

	public String get(String key) {
		return propertyParams.get(key);
	}
}
