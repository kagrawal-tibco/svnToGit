package com.tibco.cep.dashboard.psvr.rollover;

import java.util.TimeZone;

import com.tibco.cep.dashboard.management.ManagementException;
import com.tibco.cep.dashboard.management.Service;

public class RollOverService extends Service {

	private boolean enabled;

	private TimeZone timeZone;

	private String rollOverTime;

	private long offset;

	private ROLLOVER_POLICY policy;

	public RollOverService() {
		super("rollover", "Day Rollover Service");
	}

	@Override
	protected void doInit() throws ManagementException {
		enabled = (Boolean) RollOverProperties.ENABLED.getValue(this.properties);
		if (enabled == true) {
			timeZone = TimeZone.getTimeZone((String) RollOverProperties.TIME_ZONE.getValue(properties));
			rollOverTime = (String) RollOverProperties.TIME.getValue(properties);
			offset = (Long) RollOverProperties.OFFSET.getValue(properties);
			policy = ROLLOVER_POLICY.valueOf(RollOverProperties.POLICY.getValue(properties).toString().toUpperCase());
			addDependent(new RollOverTask(timeZone, rollOverTime, offset, policy));
		}
	}

}