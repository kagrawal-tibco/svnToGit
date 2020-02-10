package com.tibco.cep.query.stream.util;

/*
 * Author: Ashwin Jayaprakash Date: Feb 21, 2008 Time: 3:54:58 PM
 */

public class Helper {
    /**
     * Works for positive integers.
     *
     * @param x
     * @return <code>-1</code> if x <= 0.
     */
    public static int floorLogBase2(int x) {
        if (x > 0) {
            // See Integer Javadocs for "floor() of log-base2(x)".
            return 31 - Integer.numberOfLeadingZeros(x);
        }

        return -1;
    }
}
