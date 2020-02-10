package com.tibco.rta.service.persistence.db;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.tibco.rta.common.service.RtaTransaction;

public class BatchedNodePerSchema {

	public enum NodeType {
		METRIC, FACT, FACTHR, RULE_METRIC
	};

	private String identifier;
	private NodeType type;
	Map<RtaTransaction.TxnType, List<Object>> nodesByTxnType = new HashMap<RtaTransaction.TxnType, List<Object>>();

	public BatchedNodePerSchema(String identifier, NodeType type) {
		this.identifier = identifier;
		this.type = type;
		nodesByTxnType.put(RtaTransaction.TxnType.DELETE, new ArrayList<Object>());
		nodesByTxnType.put(RtaTransaction.TxnType.UPDATE, new ArrayList<Object>());
		nodesByTxnType.put(RtaTransaction.TxnType.NEW, new ArrayList<Object>());
	}

	public void addNode(RtaTransaction.TxnType type, Object node) {
		nodesByTxnType.get(type).add(node);
	}

	public List<Object> getNodeList(RtaTransaction.TxnType type) {
		return nodesByTxnType.get(type);
	}

	public String getIdentifier() {
		return this.identifier;
	}

	public NodeType getNodeType() {
		return this.type;
	}

	@Override
	public String toString() {
		return "BatchedNodePerSchema [identifier=" + identifier + " , type=" + type + " ]";
	}
}
