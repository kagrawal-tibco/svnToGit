package com.tibco.rta.common.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.tibco.rta.Fact;
import com.tibco.rta.Key;
import com.tibco.rta.MetricKey;
import com.tibco.rta.log.Level;
import com.tibco.rta.log.LogManagerFactory;
import com.tibco.rta.log.Logger;
import com.tibco.rta.log.impl.LoggerCategory;
import com.tibco.rta.model.DimensionHierarchy;
import com.tibco.rta.query.MetricEventType;
import com.tibco.rta.runtime.model.MetricNode;
import com.tibco.rta.runtime.model.MetricNodeEvent;
import com.tibco.rta.runtime.model.impl.MetricNodeEventImpl;
import com.tibco.rta.runtime.model.rule.AlertNodeState;
import com.tibco.rta.runtime.model.rule.RuleMetricNodeState;
import com.tibco.rta.runtime.model.rule.impl.RuleMetricNodeStateKey;
import com.tibco.rta.service.om.ObjectManager;

public class RtaTransaction implements Transaction {

    private static final Logger LOGGER = LogManagerFactory.getLogManager().getLogger(LoggerCategory.RTA_SERVICES_METRIC.getCategory());

    private int clientBatchSize;


    public enum TxnType {
        NEW, UPDATE, DELETE
    }


    private Map<Key, RtaTransactionElement<?>> transactionList = new TreeMap<Key, RtaTransactionElement<?>>();

    private ObjectManager om;

    static final ThreadLocal<Transaction> INSTANCE = new ThreadLocal<Transaction>() {
        public Transaction initialValue() {
            return new RtaTransaction();
        }
    };


    public static Transaction get() {
        return INSTANCE.get();
    }

    public RtaTransaction() {
        try {
            this.om = ServiceProviderManager.getInstance().getObjectManager();
        } catch (Exception e) {

        }
    }

    @Override
    public Map<Key, RtaTransactionElement<?>> getTxnList() {
        return transactionList;
    }

    @Override
    public MetricNode getNode(Key key) {
        RtaTransactionElement<?> txElem = transactionList.get(key);
        if (txElem == null) {
            return null;
        } else if (txElem.getTxnType() == TxnType.DELETE) {
            return null;
        } else {
            return (MetricNode) txElem.getNode();
        }
    }

    @Override
    public Fact getFact(Key key) {
        RtaTransactionElement<?> txElem = transactionList.get(key);
        if (txElem == null) {
            return null;
        } else if (txElem.getTxnType() == TxnType.DELETE) {
            return null;
        } else {
            return (Fact) txElem.getNode();
        }
    }

    @Override
    public void recordNewMetricNode(MetricNode metricNode) {
        Key key = metricNode.getKey();
//		RtaTransactionElement<MetricNode> txElem = transactionList
//				.get(metricNode.getKey());
//		LOGGER.log(Level.DEBUG, "Putting (new) node to TXN: %s", key);
//		System.out.println(String.format("Putting (new) node to TXN: %s", key));
        transactionList.put(key,
                new RtaTransactionElement<MetricNode>(TxnType.NEW,
                        metricNode));
//        if (e != null) {
//        	LOGGER.log(Level.DEBUG, "Overwrite %s with (new) node to TXN: %s", e.getTxnType(), key);
//        } else {
//        	LOGGER.log(Level.DEBUG, "First time %s with (new) node to TXN: %s","NEW", key);
//        }

    }

    public void recordUpdateMetricNode(MetricNode metricNode) {
        Key key = metricNode.getKey();
        RtaTransactionElement<?> txElem = transactionList.get(metricNode.getKey());
        if (txElem != null && txElem.getTxnType().equals(TxnType.NEW)) {
//				LOGGER.log(Level.DEBUG, "Updating a new node..(update) node to TXN: %s", key);
//			System.out.println(String.format("Treating update as insert (new) node to TXN: %s", key));

        } else {
//			System.out.println(String.format("UPPPpdate as insert (new) node to TXN: %s", key));

            transactionList.put(key,
                    new RtaTransactionElement<MetricNode>(TxnType.UPDATE,
                            metricNode));
//            if (e != null) {
//            	LOGGER.log(Level.DEBUG, "Overwrite %s with (update) node to TXN: %s", e.getTxnType(), key);
//            } else {
//            	LOGGER.log(Level.DEBUG, "First time %s with (update) node to TXN: %s", "UPDATE", key);
//            }
            
        }
    }

    public void recordDeleteMetricNode(MetricNode metricNode) {
        Key key = metricNode.getKey();
//		RtaTransactionElement<MetricNode> txElem = transactionList.get(key);
        transactionList.put(key,
                new RtaTransactionElement<MetricNode>(TxnType.DELETE,
                        metricNode));
//        if (e != null) {
//        	LOGGER.log(Level.DEBUG, "Overwrite %s with (delete) node to TXN: %s", e.getTxnType(), key);
//        } else {
//        	LOGGER.log(Level.DEBUG, "First time %s with (delete) node to TXN: %s", "DELETE", key);
//
//        }
        
    }

    @Override
    public void recordNewFact(Fact fact) {
        RtaTransaction transaction = (RtaTransaction) INSTANCE.get();
        Key key = fact.getKey();
        transaction.transactionList.put(key, new RtaTransactionElement<Fact>(
                TxnType.NEW, fact));
    }

    public void recordUpdateFact(Fact fact) {
        RtaTransaction transaction = (RtaTransaction) INSTANCE.get();
        Key key = fact.getKey();
//		RtaTransactionElement<Fact> txElem = transaction.transactionList.get(fact
//				.getKey());
        transaction.transactionList.put(key, new RtaTransactionElement<Fact>(
                TxnType.UPDATE, fact));
//		facts.put(key,  fact);

    }

    public void recordDeleteFact(Fact fact) {
        Key key = fact.getKey();
//		RtaTransactionElement<Fact> txElem = transactionList.get(fact
//				.getKey());
        transactionList.put(key, new RtaTransactionElement<Fact>(
                TxnType.DELETE, fact));

    }

    public void recordMetricNodeChild(MetricNode metricNode, Fact fact) {
        RtaTransaction transaction = (RtaTransaction) INSTANCE.get();
        MetricFact mf = new MetricFact(metricNode, fact);
        Key key = mf.getKey();
        RtaTransactionElement<?> txElem = transaction.transactionList.get(key);

        if (txElem == null) {
            transaction.transactionList.put(key, new RtaTransactionElement<MetricFact>(
                    TxnType.UPDATE, mf));
        } else if (txElem.getTxnType() == TxnType.DELETE) {
            // delete, then insert case, overwrite
            transaction.transactionList.put(key, new RtaTransactionElement<MetricFact>(
                    TxnType.UPDATE, mf));
        }
    }


    @Override
    public void commit() throws Exception {
    	//Bala: Normal cases, always retry infinitely.
        commit(true);
    }


    @Override
    public void rollback() throws Exception {
        om.rollback();
        clear();
    }


    @Override
    public void clear() {
        transactionList.clear();
    }

    @Override
    public void remove() {
        INSTANCE.remove();
    }

    @Override
    public List<MetricNodeEvent> getMetricNodeChanges() {
        List<MetricNodeEvent> metricNodeEvents = new ArrayList<MetricNodeEvent>();
        for (RtaTransactionElement e : transactionList.values()) {
            if (e.getNode() instanceof MetricNode) {
                MetricNode metricNode = (MetricNode) e.getNode();
                if (e.getTxnType().equals(TxnType.NEW)) {
                    metricNodeEvents.add(new MetricNodeEventImpl(metricNode, MetricEventType.NEW));
                } else if (e.getTxnType().equals(TxnType.UPDATE)) {
                    metricNodeEvents.add(new MetricNodeEventImpl(metricNode, MetricEventType.UPDATE));
                } else if (e.getTxnType().equals(TxnType.DELETE)) {
                    metricNodeEvents.add(new MetricNodeEventImpl(metricNode, MetricEventType.DELETE));
                }
            }
        }
        return metricNodeEvents;
    }

    @Override
    public void beginTransaction() throws Exception {
        om.beginTransaction();

    }

    @Override
    public void mergeTransaction(Transaction txn) {
        if (txn instanceof RtaTransaction) {
            RtaTransaction rtaTxn = (RtaTransaction) txn;

            transactionList.putAll(rtaTxn.transactionList);
        }
    }

    public static class RtaTransactionElement<T> {
        private RtaTransaction.TxnType txnType;
        private T node;

        public RtaTransactionElement(RtaTransaction.TxnType txnType, T node) {
            this.txnType = txnType;
            this.node = node;
        }

        public TxnType getTxnType() {
            return txnType;
        }

        public T getNode() {
            return node;
        }
    }

    public static class MetricFact {
        MetricNode metricNode;
        Fact fact;
        Key key;

        public MetricFact(MetricNode metricNode, Fact fact) {
            this.metricNode = metricNode;
            this.fact = fact;
            key = new MFKey(metricNode.getKey(), fact.getKey());
        }

        public Key getKey() {
            // TODO Auto-generated method stub
            return key;
        }

        public MetricNode getMetricNode() {
            return metricNode;
        }

        public Fact getFact() {
            return fact;
        }

        public static class MFKey implements Key {

            private static final long serialVersionUID = 2578457404990164867L;

            Key mKey;

            Key fKey;

            public MFKey(Key mKey, Key fKey) {
                this.mKey = mKey;
                this.fKey = fKey;
            }

            public boolean equals(Object object) {

                if (!(object instanceof MFKey)) {
                    return false;
                }

                MFKey mfKey2 = (MFKey) object;

                if (mKey != null) {
                    if (!mKey.equals(mfKey2.mKey)) {
                        return false;
                    }
                } else if (mfKey2.mKey == null) {
                    return false;
                }

                if (fKey != null) {
                    if (!fKey.equals(mfKey2.fKey)) {
                        return false;
                    }
                } else if (mfKey2.fKey == null) {
                    return false;
                }
                return true;
            }
            
            @Override
            public String toString() {
            	String mKeyStr = mKey == null? "" : mKey.toString();
            	String fKeyStr = fKey == null? "" : fKey.toString();
            	return mKeyStr + fKeyStr;
            }

        	@Override
        	public int compareTo(Object other) {
        		return toString().compareTo(other.toString());
        	}
        }
    }

    public static class FactHr implements Key {

        private static final long serialVersionUID = 2578457404990164867L;

        Fact fact;
        String schemaName;
        String cubeName;
        String hierarchyName;

        public FactHr(Fact fact, DimensionHierarchy hr) {
            this.fact = fact;
            this.schemaName = hr.getOwnerSchema().getName();
            this.cubeName = hr.getOwnerCube().getName();
            this.hierarchyName = hr.getName();
        }

        public Fact getFact() {
            return fact;
        }

        public String getSchemaName() {
            return this.schemaName;
        }

        public String getCubeName() {
            return this.cubeName;
        }

        public String getHierarchyName() {
            return hierarchyName;
        }

        public boolean equals(Object object) {
            if (!(object instanceof FactHr)) {
                return false;
            }

            FactHr other = (FactHr) object;

            return (fact.getKey().equals(other.fact.getKey())
                    && schemaName.equals(other.schemaName)
                    && cubeName.equals(other.cubeName) && hierarchyName
                    .equals(other.hierarchyName));
        }
        
        public String toString() {
        	return fact.getKey().toString() + "_" + schemaName + "_" + cubeName + "_" + hierarchyName;
        }

    	@Override
    	public int compareTo(Object other) {
    		return toString().compareTo(other.toString());
    	}

    }

    @Override
    public void recordfactProcessedForHr(Fact fact, DimensionHierarchy hr) {
        FactHr factHr = new FactHr(fact, hr);
        transactionList.put(factHr, new RtaTransactionElement<FactHr>(
                TxnType.NEW, factHr));

    }

    public int getClientBatchSize() {
        return clientBatchSize;
    }

    public void setClientBatchSize(int clientBatchSize) {
        this.clientBatchSize = clientBatchSize;
    }


    @Override
    public RuleMetricNodeState getRuleMetricNode(String ruleName,
                                                 String actionName, MetricKey key) throws Exception {

        RuleMetricNodeStateKey rKey = new RuleMetricNodeStateKey(ruleName, actionName, key);
        RtaTransactionElement<?> txElem = transactionList.get(rKey);
        if (txElem == null) {
            return null;
        } else if (txElem.getTxnType() == TxnType.DELETE) {
            return null;
        } else {
            return (RuleMetricNodeState) txElem.getNode();
        }
    }

    @Override
    public void recordUpdateRuleMetricNode(RuleMetricNodeState actionState) {
        RtaTransactionElement<?> txElem = transactionList.get(actionState.getKey());
        if (txElem != null && txElem.getTxnType().equals(TxnType.NEW)) {
//				LOGGER.log(Level.DEBUG, "Updating a new node..(update) node to TXN: %s", key);
//			System.out.println(String.format("Treating update as insert (new) node to TXN: %s", key));

        } else {
//			System.out.println(String.format("UPPPpdate as insert (new) node to TXN: %s", key));

            transactionList.put(actionState.getKey(),
                    new RtaTransactionElement<RuleMetricNodeState>(TxnType.UPDATE,
                            actionState));
        }

    }

    @Override
    public void recordNewRuleMetricNode(RuleMetricNodeState actionState) {
        Key key = actionState.getKey();
//		RtaTransactionElement<MetricNode> txElem = transactionList
//				.get(metricNode.getKey());
//		LOGGER.log(Level.DEBUG, "Putting (new) node to TXN: %s", key);
//		System.out.println(String.format("Putting (new) node to TXN: %s", key));
        transactionList.put(key,
                new RtaTransactionElement<RuleMetricNodeState>(TxnType.NEW,
                        actionState));
    }

    @Override
    public void recordDeleteRuleMetricNode(RuleMetricNodeState actionState) {
//		RtaTransactionElement<MetricNode> txElem = transactionList.get(key);
        if (LOGGER.isEnabledFor(Level.DEBUG)) {
            LOGGER.log(Level.DEBUG, "Recording Delete RuleMetricNodeState [%s]", actionState.getKey());
        }
        transactionList.put(actionState.getKey(),
                new RtaTransactionElement<RuleMetricNodeState>(TxnType.DELETE,
                        actionState));
    }


    public String toString() {
        StringBuilder b = new StringBuilder();
        b.append("*********************Txn: ").append(hashCode()).append("\n)");

        for (Map.Entry<Key, RtaTransactionElement<?>> entry : transactionList.entrySet()) {
            Key key = entry.getKey();
            RtaTransactionElement e = entry.getValue();
            b.append("** Key = ").append(key.toString()).append(", Type : ").append(e.getTxnType().name()).append("\n");
        }
        b.append("*********************Txn: over").append(hashCode()).append("\n");
        return b.toString();
    }


	@Override
	public void recordUpdateAlertNodeState(AlertNodeState alertNode) {
        RtaTransactionElement<?> txElem = transactionList.get(alertNode.getKey());
        if (txElem != null && txElem.getTxnType().equals(TxnType.NEW)) {
//				LOGGER.log(Level.DEBUG, "Updating a new node..(update) node to TXN: %s", key);
//			System.out.println(String.format("Treating update as insert (new) node to TXN: %s", key));

        } else {
//			System.out.println(String.format("UPPPpdate as insert (new) node to TXN: %s", key));

            transactionList.put(alertNode.getKey(),
                    new RtaTransactionElement<AlertNodeState>(TxnType.UPDATE,
                            alertNode));
        }
	}

	@Override
	public void recordNewAlertNodeState(AlertNodeState alertNode) {
        transactionList.put(alertNode.getKey(),
                new RtaTransactionElement<AlertNodeState>(TxnType.NEW,
                        alertNode));
		
	}
	
    @Override
    public void recordDeleteAlertNodeState(AlertNodeState alertNode) {
//		RtaTransactionElement<MetricNode> txElem = transactionList.get(key);
        if (LOGGER.isEnabledFor(Level.DEBUG)) {
            LOGGER.log(Level.DEBUG, "Recording Delete RuleMetricNodeState [%s]", alertNode.getKey());
        }
        transactionList.put(alertNode.getKey(),
                new RtaTransactionElement<AlertNodeState>(TxnType.DELETE,
                        alertNode));
    }

	@Override
	public void commit(boolean retryInfinte) throws Exception {
		om.commit(retryInfinte);
		
	}
}
