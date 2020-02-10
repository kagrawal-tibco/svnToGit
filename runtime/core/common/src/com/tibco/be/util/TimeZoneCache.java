package com.tibco.be.util;

import java.util.HashMap;
import java.util.TimeZone;

public class TimeZoneCache 
{
	private static final ThreadLocal<HashMap<String, TimeZone>> timezones = new ThreadLocal<HashMap<String, TimeZone>>();
	
    public static TimeZone getCachedTz(String tz) {
        HashMap<String, TimeZone> tzs = timezones.get();
        if (tzs == null) {
            tzs = new HashMap<String, TimeZone>();
            timezones.set(tzs);
        }

        TimeZone timeZone = tzs.get(tz);
        if (timeZone == null) {
            timeZone = TimeZone.getTimeZone(tz);
            tzs.put(tz, timeZone);
        }

        return timeZone;
    }

}
