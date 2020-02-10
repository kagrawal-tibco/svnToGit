package com.tibco.cep.decision.table.codegen;

import java.util.ArrayList;

public class DTRow extends BaseDTRow<DTCell, DTCell>{

	public DTRow(ArrayList<DTCell> conditions, ArrayList<DTCell> actions,
			int priority, String rowId) {
		super(conditions, actions, priority, rowId);
	}

	public DTRow(DTCell[] conditions, DTCell[] actions, int priority, String rowId) {
		super(conditions, actions, priority, rowId);
	}

}
