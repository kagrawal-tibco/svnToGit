package com.tibco.cep.runtime.perf.stats;

import java.util.LinkedHashMap;
import java.util.Map.Entry;
import java.text.MessageFormat;
import java.util.concurrent.TimeUnit;

public final class TimeUnitUtil {

    private static transient final LinkedHashMap<Double, String> timeUnitsMap = new LinkedHashMap<Double, String>();

    static {
        timeUnitsMap.put((double) TimeUnit.DAYS.toMillis(365), "year(s) ago");
        timeUnitsMap.put((double) TimeUnit.DAYS.toMillis(30), "months(s) ago");
        timeUnitsMap.put((double) TimeUnit.DAYS.toMillis(7), "week(s) ago");
        timeUnitsMap.put((double) TimeUnit.DAYS.toMillis(1), "day(s) ago");
        timeUnitsMap.put((double) TimeUnit.HOURS.toMillis(1), "hour(s) ago");
        timeUnitsMap.put((double) TimeUnit.MINUTES.toMillis(1), "minute(s) ago");
        timeUnitsMap.put((double) TimeUnit.SECONDS.toMillis(1), "second(s) ago");
        timeUnitsMap.put((double) TimeUnit.MILLISECONDS.toMillis(1), "msec(s) ago");
    }

    public static final String convert(long time){
        for (Entry<Double, String> entry : timeUnitsMap.entrySet()) {
            double d = time / entry.getKey();
            if (d > 1) {
                return MessageFormat.format("{0,number,#.##} {1} [{2}]", new Object[]{d, entry.getValue(), Long.toString(time)});
            }
        }
        return Long.toString(time);
    }

}
