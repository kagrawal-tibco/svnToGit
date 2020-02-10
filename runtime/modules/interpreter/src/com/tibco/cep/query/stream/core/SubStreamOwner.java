package com.tibco.cep.query.stream.core;

import com.tibco.cep.query.stream.context.Context;

/*
 * Author: Ashwin Jayaprakash Date: Jan 10, 2008 Time: 4:56:48 PM
 */

public interface SubStreamOwner<SS extends SubStream> {
    public void scheduleSubStreamForNextCycle(Context context, SS subStream);

    /**
     * Can send deletes if the {@link SubStream} asks for it.
     * 
     * @return
     */
    public abstract boolean canSendDeletesToSubStream();
}
