/**
 * 
 */
package com.tibco.cep.decision.table.language;

import com.tibco.cep.decision.table.model.dtmodel.Table;
import com.tibco.cep.decision.table.model.dtmodel.TableRule;
import com.tibco.cep.decision.table.model.dtmodel.TableRuleSet;
import com.tibco.cep.decision.table.model.dtmodel.TableRuleVariable;

public class TableErrorSource {
	private final Table table;
	private final TableRuleSet trs;
	private TableRule row;
	private TableRuleVariable col;
	
	public TableErrorSource(Table table, TableRuleSet trs, TableRule row, TableRuleVariable col) {
		this.table = table;
		this.trs = trs;
		this.row = row;
		this.col = col;
	}
	
	public Table getTable() {
		return table;
	}

	public TableRuleSet getTrs() {
		return trs;
	}
	
	public TableRule getRow() {
		return row;
	}

	public TableRuleVariable getCol() {
		return col;
	}
}