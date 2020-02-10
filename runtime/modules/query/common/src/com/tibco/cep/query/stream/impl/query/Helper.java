package com.tibco.cep.query.stream.impl.query;

import com.tibco.cep.query.stream.query.continuous.ContinuousQuery;

/*
 * Author: Ashwin Jayaprakash Date: Mar 19, 2008 Time: 3:32:16 PM
 */

public class Helper {
    /**
     * {@value}
     */
    public static final long QUERY_COMPLETION_RECHECK_TIME_MILLIS = 1000;

    public static void waitForQueryCompletion(ContinuousQuery query, long recheckTimeMillis)
            throws InterruptedException {
        int rechecks = 0;

        while (true) {
            long timeForQueryToFinish = query.calcEstimatedFinishTime();

            if (timeForQueryToFinish > -1) {
                rechecks = 0;
            }
            else {
                // Make another check just to make sure that it has stopped.
                if (rechecks == 1) {
                    break;
                }

                rechecks++;
            }

            Thread.sleep(recheckTimeMillis);
        }
    }

    /**
     * Same as {@link #waitForQueryCompletion(ContinuousQuery, long)} with
     * {@link #QUERY_COMPLETION_RECHECK_TIME_MILLIS}
     * 
     * @param query
     * @throws InterruptedException
     */
    public static void waitForQueryCompletion(ContinuousQuery query)
            throws InterruptedException {
        waitForQueryCompletion(query, QUERY_COMPLETION_RECHECK_TIME_MILLIS);
    }
}
