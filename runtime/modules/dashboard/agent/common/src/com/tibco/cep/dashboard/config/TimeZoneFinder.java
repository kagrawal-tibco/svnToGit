package com.tibco.cep.dashboard.config;

import java.util.TimeZone;

import com.tibco.cep.dashboard.common.utils.StringUtil;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.Logger;

public final class TimeZoneFinder {

	private String timezoneid;

	private TimeZone timeZone;

	private TimeZone defaultTimeZone;

	private Logger logger;

	public TimeZoneFinder(Logger logger, String timezoneid) {
		this(logger, timezoneid, TimeZone.getDefault());
	}

	public TimeZoneFinder(Logger logger, String timezoneid, TimeZone defaultTimeZone) {
		super();
		this.logger = logger;
		this.timezoneid = timezoneid;
		this.defaultTimeZone = defaultTimeZone;
		parse();
	}

	private void parse() {
		// check if there is a time zone id defined
		if (StringUtil.isEmptyOrBlank(timezoneid) == true) {
			// yes it is, log and use system's default time zone
			timeZone = defaultTimeZone;
			logger.log(Level.INFO, "No timezone id specified, defaulting to " + timeZone.getDisplayName());
		} else {
			timeZone = TimeZone.getTimeZone(timezoneid);
			// check if the time zone created matches the ID requested by user
			if (timeZone.getID().equals(timezoneid) == false) {
				timeZone = defaultTimeZone;
				// no it does not match so again log and use system's default
				// time zone
				logger.log(Level.WARN, "Invalid timezone id specified, defaulting to " + timeZone.getDisplayName());
			}
		}
	}

	public TimeZone getTimeZone() {
		return timeZone;
	}

}
