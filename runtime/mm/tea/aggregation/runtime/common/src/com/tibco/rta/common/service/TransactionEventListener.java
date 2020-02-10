package com.tibco.rta.common.service;

public interface TransactionEventListener {
	
	void onTransactionEvent(TransactionEvent context);

}
