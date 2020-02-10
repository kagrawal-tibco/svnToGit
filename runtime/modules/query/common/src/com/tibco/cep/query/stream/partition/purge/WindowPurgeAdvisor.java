package com.tibco.cep.query.stream.partition.purge;

import com.tibco.cep.query.stream.context.DefaultQueryContext;
import com.tibco.cep.query.stream.context.GlobalContext;

/*
 * Author: Ashwin Jayaprakash Date: Nov 21, 2007 Time: 12:00:04 PM
 */

public interface WindowPurgeAdvisor {
    /**
     * <p>
     * Note the spellings - Verb: advise, Noun: advice.
     * </p>
     * <p>
     * Invoked at the beginning and end of the Window's processing cycle. The
     * advice obtained at the end of the cycle (if any) will be used only in the
     * next cycle.
     * </p>
     * <p>
     * Advice can be of 2 types - {@link ImmediateWindowPurgeAdvice} or
     * {@link ScheduledWindowPurgeAdvice}. If the
     * {@link ScheduledWindowPurgeAdvice} is returned and the time has not come
     * yet to apply the advice, i.e
     * {@link ScheduledWindowPurgeAdvice#adviseNow(WindowStats)} is not invoked
     * on the instance yet, then the advisor will not be consulted until after
     * that.
     * </p>
     * 
     * @param globalContext
     * @param queryContext
     * @param stats
     * @return <b>Has</b> be <code>null</code> if there is no advice.
     */
    public WindowPurgeAdvice advise(GlobalContext globalContext, DefaultQueryContext queryContext,
            WindowStats stats);
}
