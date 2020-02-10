package com.tibco.cep.decision.table.constraintpane;

import org.eclipse.core.runtime.SubMonitor;

import com.tibco.cep.decision.table.model.dtmodel.Table;

public class DecisionTableEMFFactory {

	public static DecisionTable createDecisionTable(Table table, SubMonitor progressMonitor) {
		DecisionTableCreator creator = new DecisionTableCreator(table);
		return creator.createDecisionTable(progressMonitor);
	}

	public static DecisionTable createOptimizedDecisionTable(Table table, SubMonitor progressMonitor) {
		DecisionTable decisionTable = createDecisionTable(table, progressMonitor);
		progressMonitor.setTaskName("Optimizing Table Analyzer Matrix");
		decisionTable.optimize(progressMonitor);
		return decisionTable;
	}
	
}
