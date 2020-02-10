package com.tibco.cep.decision.table.editors.listener;

import java.util.ArrayList;
import java.util.List;

import javax.swing.event.ChangeEvent;

import com.jidesoft.decision.cell.editors.DefaultRuleVariableCellEditor;
import com.jidesoft.grid.JideCellEditor;
import com.jidesoft.grid.JideCellEditorListener;
import com.tibco.cep.decision.table.model.dtmodel.TableRuleVariable;

public class DecisionTableCellEditorListener implements JideCellEditorListener {
	
	private List<ICellEditCompletionListener> cellEditCompletionListeners;
	
	public DecisionTableCellEditorListener() {
		cellEditCompletionListeners = new ArrayList<ICellEditCompletionListener>();
	}
	public void editingStarted(ChangeEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public boolean editingStarting(ChangeEvent arg0) {
		// TODO Auto-generated method stub
		return true;
	}

	public boolean editingStopping(ChangeEvent arg0) {
		// TODO Auto-generated method stub
		return true;
	}

	public void editingCanceled(ChangeEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
	public boolean addCellEditCompletionListener(ICellEditCompletionListener cellEditCompletionListener) {
		return cellEditCompletionListeners.add(cellEditCompletionListener);
	}

	public void editingStopped(ChangeEvent arg0) {
		JideCellEditor editor = (JideCellEditor)arg0.getSource();
		if (editor instanceof DefaultRuleVariableCellEditor) {
			DefaultRuleVariableCellEditor rvc = (DefaultRuleVariableCellEditor)editor;
			TableRuleVariable TRV = (TableRuleVariable)rvc.getEditorValue();
			if (TRV != null) {
				fireCellEditCompletionEvent(rvc, TRV);
			}
		}
	}
	
	private void fireCellEditCompletionEvent(DefaultRuleVariableCellEditor rvc,
			                                 TableRuleVariable TRV) {
		EditCompletionEvent editCompletionEvent = new EditCompletionEvent(rvc, TRV);
		for (ICellEditCompletionListener completionListener : cellEditCompletionListeners) {
			completionListener.editingComplete(editCompletionEvent);
		}
	}
}
