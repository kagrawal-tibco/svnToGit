package com.tibco.cep.kernel.core.rete;

/*
* Author: Ashwin Jayaprakash / Date: 10/28/11 / Time: 4:34 PM
*/
public interface BeTransactionHook {
    /**
     * @param ruleSession Can be null.
     */
    void cleanupTxnWorkArea(Object ruleSession);

    /**
     * @return If true, then call {@link #begin()}, {@link #commit()}/{@link #rollback()}. If false, call {@link #txnWork(Runnable)}.
     */
    boolean isComposable();

    //-------------

    void txnWork(Runnable work);

    //-------------

    void begin();

    void commit();

    void rollback();
}
