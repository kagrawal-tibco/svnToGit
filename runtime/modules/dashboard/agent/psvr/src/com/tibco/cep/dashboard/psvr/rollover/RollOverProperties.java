package com.tibco.cep.dashboard.psvr.rollover;

import java.util.TimeZone;

import com.tibco.cep.dashboard.common.utils.DateTimeUtils;
import com.tibco.cep.dashboard.config.PropertyKey;
import com.tibco.cep.dashboard.config.PropertyKeys;

public interface RollOverProperties extends PropertyKeys {
	
	static final PropertyKey ENABLED = new PropertyKey("rollover.enabled","Enables rollover of dashboards",PropertyKey.DATA_TYPE.Boolean, Boolean.TRUE);
	
	static final PropertyKey TIME_ZONE = new PropertyKey("rollover.timezone","The time zone in which the rollover should occur",PropertyKey.DATA_TYPE.String, TimeZone.getDefault().getID());
	
	static final PropertyKey TIME = new PropertyKey("rollover.time","The time at which the rollover should occur",PropertyKey.DATA_TYPE.String, "00:00");
	
	static final PropertyKey POLICY = new PropertyKey("rollover.policy","The policy to use during rollover",PropertyKey.DATA_TYPE.String, ROLLOVER_POLICY.RESET.toString().toLowerCase());
	
	static final PropertyKey OFFSET = new PropertyKey("rollover.offset","The time in seconds by which the rollover should be triggered earlier then the rollover time",PropertyKey.DATA_TYPE.Long, new Long(15*DateTimeUtils.SECOND));
	
}
