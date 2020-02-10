package com.tibco.rta.service.metric;

/**	
 * This service handles the aggregations
 *
 */

import java.util.List;

import com.tibco.rta.Fact;
import com.tibco.rta.common.service.FactMessageContext;
import com.tibco.rta.common.service.StartStopService;
import com.tibco.rta.common.service.StickyThreadPool;
import com.tibco.rta.common.service.Transaction;
import com.tibco.rta.common.service.TransactionEvent;
import com.tibco.rta.common.service.TransactionEventListener;
import com.tibco.rta.common.service.WorkItemService;
import com.tibco.rta.runtime.model.MetricNodeEvent;

public interface MetricService extends StartStopService {

    /**
     * At runtime, a fact instance is asserted into the engine. This triggers the metric computation
     * Presently do not return anything.
     *
     * @param fact
     * @throws Exception
     */

//    <C extends FactMessageContext> TransactionEvent assertFact(Fact fact, boolean isRecovery) throws Exception;

    <C extends FactMessageContext> TransactionEvent assertFact(Fact fact) throws Exception;

    /**
     * At runtime, a fact instance is asserted into the engine. This triggers the metric computation
     * Presently do not return anything.
     *
     * @param messageContext
     * @param fact
     * @throws Exception
     */

    <C extends FactMessageContext> TransactionEvent assertFact(C messageContext, Fact fact) throws Exception;
    
//    <C extends FactMessageContext> TransactionEvent assertFact(C messageContext, Fact fact, boolean isRecovery) throws Exception;
    
    <C extends FactMessageContext> TransactionEvent assertFact(C messageContext, List<Fact> fact) throws Exception;
    
    /**
     * A way to propagate metric value change events to other modules such as query/rules
     * 
     * @param listener The listers whose onValueChangeEvent will be called.
     */
    void addMetricValueChangeListener (MetricEventListener listener);
    
    /**
     * Remove a listener
     * 
     * @param listener The listers to remove.
     */
    void removeMetricValueChangeListener (MetricEventListener listener);

	void removeTransactionContextListener(TransactionEventListener listener);

	void addTransactionContextListener(TransactionEventListener listener);
	
    void addFactListener(FactListener<?> fact) throws Exception;
    
    void removeFactListener(FactListener factListener);

	StickyThreadPool getStickyPool();

	void publishTxn(Transaction txn);

	int getTxnRetryCount();
	
	int getTxnBatchSize();
	
	int getClientTxnBatchSize();

	WorkItemService getScatterThreadPool();
	
	boolean useAssetLocking();
	
	boolean useSingleLock();
	
	public void publishSnapshotRuleJob(MetricNodeEvent evt);
}
