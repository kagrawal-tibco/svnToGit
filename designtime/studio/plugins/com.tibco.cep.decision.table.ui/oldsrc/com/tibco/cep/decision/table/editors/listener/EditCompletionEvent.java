package com.tibco.cep.decision.table.editors.listener;

import java.util.EventObject;

import com.jidesoft.decision.cell.editors.DefaultRuleVariableCellEditor;
import com.tibco.cep.decision.table.model.dtmodel.TableRuleVariable;

public class EditCompletionEvent extends EventObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5916153784504762844L;
	
	private TableRuleVariable data;
	
	/**
	 * 
	 * @param source -> The cell editor 
	 * @see DefaultRuleVariableCellEditor
	 * @param data
	 */
	public EditCompletionEvent(Object source, TableRuleVariable data) {
		super(source);
		this.data = data;
	}

	public TableRuleVariable getData() {
		return data;
	}
}
