package com.tibco.cep.query.stream.misc;

/*
 * Author: Ashwin Jayaprakash Date: Nov 13, 2007 Time: 5:25:22 PM
 */

public class Clock {
    public static long getCurrentTimeMillis() {
        return System.currentTimeMillis();
    }

    public static long roundToNearestSecondInMillis(long timestampMillis) {
        long quotient = timestampMillis / 1000;
        long roundedTS = quotient * 1000;
        long c = (timestampMillis - roundedTS) > 500 ? (roundedTS + 1000) : roundedTS;

        return c;
    }
}
