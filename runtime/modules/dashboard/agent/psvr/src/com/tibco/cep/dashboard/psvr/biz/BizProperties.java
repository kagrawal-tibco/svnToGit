package com.tibco.cep.dashboard.psvr.biz;

import com.tibco.cep.dashboard.common.utils.DateTimeUtils;
import com.tibco.cep.dashboard.config.PropertyKey;
import com.tibco.cep.dashboard.config.PropertyKeys;

public interface BizProperties extends PropertyKeys {
	
	public final static PropertyKey SESSION_TIMEOUT_KEY = new PropertyKey("session.timeout", "Defines the amount of inactive period before a session is invalidated", PropertyKey.DATA_TYPE.Long, DateTimeUtils.MINUTE);
	
	public final static PropertyKey ENABLE_SESSION_KEEPING = new PropertyKey("session.keeping.enabled", "Enables session timeout prevention", PropertyKey.DATA_TYPE.Boolean, Boolean.TRUE);

}
