package com.tibco.cep.dashboard.psvr.context;

import com.tibco.cep.dashboard.config.PropertyKey;
import com.tibco.cep.dashboard.config.PropertyKeys;

interface ContextProperties extends PropertyKeys {
	
	static final PropertyKey ROLLOVER_TIME_KEY = new PropertyKey("rollover.time",null,PropertyKey.DATA_TYPE.String,null);

}
