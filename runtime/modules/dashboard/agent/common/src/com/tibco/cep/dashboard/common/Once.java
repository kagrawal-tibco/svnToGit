package com.tibco.cep.dashboard.common;

import java.util.HashMap;
import java.util.Map;

public final class Once {
	
	private Once(){
		
	}

	private final static Map<String,Boolean> KEY_USAGE_MAP = new HashMap<String, Boolean>(); 

	public static final boolean firstTime(String key) {
		Boolean used = KEY_USAGE_MAP.get(key);
		if (used != null){
			return used;
		}
		KEY_USAGE_MAP.put(key, Boolean.FALSE);
		return true;
	}

}