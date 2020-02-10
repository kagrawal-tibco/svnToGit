package com.tibco.rta.service.persistence.memory.adapter.impl;

import static com.tibco.rta.service.persistence.memory.InMemoryConstant.FACT_TABLE_PREFIX;
import static com.tibco.rta.service.persistence.memory.InMemoryConstant.METRIC_TABLE_PREFIX;
import static com.tibco.rta.service.persistence.memory.InMemoryConstant.SEP;

import com.tibco.rta.Fact;
import com.tibco.rta.Key;
import com.tibco.rta.MetricKey;
import com.tibco.rta.impl.FactKeyImpl;
import com.tibco.rta.runtime.model.MetricNode;
import com.tibco.rta.runtime.model.rule.RuleMetricNodeState;
import com.tibco.rta.runtime.model.rule.impl.RuleMetricNodeStateKey;
import com.tibco.store.persistence.model.MemoryKey;
import com.tibco.store.persistence.model.MemoryTuple;

public class RuntimeModelConvertor {

	public static MemoryKey getMemoryKey(Key key) {

		if (key instanceof MetricKey) {
			MemoryKey mKey = new MetricMemoryKey((MetricKey) key);
			return mKey;
		} else if (key instanceof FactKeyImpl) {
			// TODO
			// mKey = new MemoryKeyImpl();

		}
		return null;
	}

	public static MetricNode getMetricNode(MemoryTuple mTuple) throws Exception {
		if (mTuple instanceof MetricMemoryTuple) {
			return (MetricNode) mTuple.getWrappedObject();
		}
		return null;
	}

	public static Fact getFact(MemoryTuple mTuple) throws Exception {
		if (mTuple instanceof Fact) {
			return (Fact) mTuple.getWrappedObject();
		}
		return null;
	}

	public static RuleMetricNodeState getRuleMetricNode(MemoryTuple mTuple) throws Exception {
		if (mTuple instanceof RuleStateMemoryTuple) {
			return (RuleMetricNodeState) mTuple.getWrappedObject();
		}
		return null;
	}

	public static MemoryTuple getMemoryTuple(MetricNode mNode) {
		return new MetricMemoryTuple(mNode);
	}

	public static MemoryTuple getMemoryTuple(Fact fact) {
		return new FactMemoryTuple(fact);
	}

	public static MemoryKey getMemoryKeyForRuleNode(RuleMetricNodeStateKey key) {
		return new RuleStateMemoryKey(key);
	}

	public static MemoryKey getMemoryKeyForFact(FactKeyImpl key) {
		return new FactMemoryKey(key);
	}

	public static String getMemoryTableName(Key key) {
		if (key instanceof MetricKey) {
			String schemaName = ((MetricKey) key).getSchemaName();
			String cubeName = ((MetricKey) key).getCubeName();
			String dh = ((MetricKey) key).getDimensionHierarchyName();
			String uniqueIdentifier = TableGenerationUtility.getMemoryTableName(METRIC_TABLE_PREFIX, schemaName, cubeName, dh);
			
			return uniqueIdentifier;
		} else if (key instanceof FactKeyImpl) {
			String schemaName = ((FactKeyImpl) key).getSchemaName();
			String uniqueIdentifier = TableGenerationUtility.getUniqueIdentifier(FACT_TABLE_PREFIX, SEP, schemaName);

			return uniqueIdentifier;
		}
		return null;
	}

	public static MemoryTuple getMemoryTuple(RuleMetricNodeState rmNode, RuleStateMemoryKey memoryKey) {
		return new RuleStateMemoryTuple(rmNode, memoryKey);
	}

}
