package com.tibco.cep.query.stream.partition;

import com.tibco.cep.query.stream.context.Context;
import com.tibco.cep.query.stream.core.SubStreamOwner;

/*
 * Author: Ashwin Jayaprakash Date: Nov 12, 2007 Time: 2:14:49 PM
 */

public interface WindowOwner extends SubStreamOwner<Window> {
    /**
     * @param context
     * @param window
     * @param timestamp Use {@link com.tibco.cep.query.stream.misc.Clock#roundToNearestSecondInMillis(long)}.
     */
    public void scheduleWindowAt(Context context, Window window, Long timestamp);
}
