package com.tibco.be.ws.decisiontable.constraint;

import com.tibco.cep.decision.table.model.dtmodel.Table;

/**
 * Abstract Table Analyzer Action class
 * @author vdhumal
 */
public abstract class AbstractTableAnalyzerAction {
	
	protected Table table;
	protected DecisionTable fCurrentOptimizedTable;
	
	/**
	 * @param fCurrentEditor
	 * @param fCurrentOptimizedTable
	 * @param fComponents
	 * @param fRangeColumnValues
	 */
	public AbstractTableAnalyzerAction(Table table, 
            DecisionTable fCurrentOptimizedTable) {
		this.table = table;
		this.fCurrentOptimizedTable = fCurrentOptimizedTable;
	}
	
	public abstract void run();

}
