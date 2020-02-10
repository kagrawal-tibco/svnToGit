package com.tibco.cep.studio.debug.smap;

import java.util.Map;

import com.tibco.be.util.BidiMap;

public interface TaskRegistryMapper extends BidiMap<String,String> {

	void init(Map<String, String> maps);

}
