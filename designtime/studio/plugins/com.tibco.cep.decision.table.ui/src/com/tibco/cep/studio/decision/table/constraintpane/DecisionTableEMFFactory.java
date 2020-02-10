package com.tibco.cep.studio.decision.table.constraintpane;

import java.text.DateFormat;

import org.eclipse.core.runtime.SubMonitor;

import com.tibco.cep.decision.table.model.dtmodel.Table;

public class DecisionTableEMFFactory {

	public static DecisionTable createDecisionTable(Table table, DateFormat dateFormat,
	                                                SubMonitor progressMonitor) {
		DecisionTableCreator creator = new DecisionTableCreator(table, dateFormat);
		return creator.createDecisionTable(progressMonitor);
	}

	public static DecisionTable createOptimizedDecisionTable(Table table, DateFormat dateFormat,
	                                                         SubMonitor progressMonitor) {
		DecisionTable decisionTable = createDecisionTable(table, dateFormat, progressMonitor);
		progressMonitor.setTaskName("Optimizing Table Analyzer Matrix");
		decisionTable.optimize(progressMonitor);
		return decisionTable;
	}
	
}
