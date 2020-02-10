package com.tibco.cep.query.stream._join_.node.join;

import com.tibco.cep.query.stream.context.GlobalContext;
import com.tibco.cep.query.stream.context.QueryContext;
import com.tibco.cep.query.stream.tuple.Tuple;

/*
 * Author: Ashwin Jayaprakash Date: Sep 15, 2008 Time: 3:01:33 PM
 */

public interface StopFilter {
    /**
     * Has to be idempotent.
     *
     * @param globalContext
     * @param queryContext
     */
    void startSession(GlobalContext globalContext, QueryContext queryContext);

    /**
     * Has to be idempotent.
     *
     * @param globalContext
     * @param queryContext
     * @param currentJoinResult
     * @return
     */
    boolean continueJoins(GlobalContext globalContext, QueryContext queryContext,
                          Tuple currentJoinResult);

    /**
     * Has to be idempotent.
     *
     * @param globalContext
     * @param queryContext
     */
    void endSession(GlobalContext globalContext, QueryContext queryContext);

    /**
     * Has to be idempotent. Invoked outside the "session".
     *
     * @param globalContext
     * @param queryContext
     * @return
     */
    boolean continueJoins(GlobalContext globalContext, QueryContext queryContext);
}
