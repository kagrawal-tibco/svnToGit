/**
 * 
 */
package com.tibco.cep.studio.core.utils;

import com.tibco.cep.decision.table.model.dtmodel.TableTypes;

/**
 * @author rmishra
 *
 */
public class DecisionTableColumnIdGenerator {
	private int dtColumnIdCounter = 0;
	private int etColumnIdCounter = 0;
	private final int INTERVAL = 1;
	
	public String getCoulmnId(TableTypes tableType) {
		if (tableType == TableTypes.DECISION_TABLE) {
			dtColumnIdCounter = dtColumnIdCounter + INTERVAL;
			return "" + dtColumnIdCounter;
		}
		else {
			etColumnIdCounter = etColumnIdCounter + INTERVAL;
			return "" + etColumnIdCounter;
		}
	}
	public void initializeCounter(int dtColumnCount , int etColumnCount){
		this.dtColumnIdCounter = dtColumnCount;
		this.etColumnIdCounter = etColumnCount;
		
	}
}
