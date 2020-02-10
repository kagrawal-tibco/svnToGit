package com.tibco.cep.studio.debug.smap;

import java.util.Map;

import com.tibco.be.util.DualHashBidiMap;

public class TaskRegistryMapperImpl extends DualHashBidiMap<String, String> implements TaskRegistryMapper {

	@Override
	public void init(Map<String, String> maps) {
		putAll(maps);
	}

}
