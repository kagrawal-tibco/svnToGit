package com.tibco.cep.decision.table.editors.listener;

import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import org.eclipse.emf.common.util.EList;
import org.eclipse.swt.widgets.Display;

import com.tibco.cep.decision.table.editors.DecisionTableDesignViewer;
import com.tibco.cep.decision.table.editors.DecisionTableEditor;
import com.tibco.cep.decision.table.model.DecisionTableModelManager;
import com.tibco.cep.decision.table.model.dtmodel.Argument;
import com.tibco.cep.decision.table.model.dtmodel.Table;

/**
 * 
 * @author sasahoo
 * 
 */
public class DTDeclTableModelListener implements TableModelListener {

	private DecisionTableEditor editor;
	
	private DecisionTableDesignViewer decisionTableDesignViewer;

	public DTDeclTableModelListener(DecisionTableEditor editor) {
		this.editor = editor;
		this.decisionTableDesignViewer = editor.getDecisionTableDesignViewer();
	}

	public void tableChanged(TableModelEvent e) {
		Display.getDefault().asyncExec(new Runnable() {
			public void run() {
				((DecisionTableEditor) editor).editorContentModified();
			}
		});
//		TableModel dtDeclTableModel = decisionTableDesignViewer
//				.getDeclTableModel();
		DecisionTableModelManager decisionTableModelManager = ((DecisionTableEditor) editor)
				.getDecisionTableModelManager();
		if (decisionTableModelManager != null) {
			Table tableEModel = decisionTableModelManager.getTabelEModel();
			if (tableEModel != null) {
//				int rowIndex = e.getLastRow();
				EList<Argument> argList = tableEModel.getArgument();
				// if
				// (decisionTableDesignViewer.getDecltable().getSelectedRows().length
				// == 1) {
				// if (e.getFirstRow() == e.getLastRow()) {
				// if (e.getType() == TableModelEvent.UPDATE) {
				// if (decisionTableDesignViewer.getDecltable()
				// .getSelectedRows().length == 1) {
				// int columnNo = e.getColumn();
				// Argument arg = argList.get(e.getLastRow());
				// ArgumentProperty argProperty = arg
				// .getProperty();
				// String cellValue = "";
				// if (columnNo == 3) {
				// cellValue = ((ImageIcon) dtDeclTableModel
				// .getValueAt(rowIndex, columnNo))
				// .getDescription();
				// } else {
				// cellValue = (String) dtDeclTableModel
				// .getValueAt(rowIndex, columnNo);
				// }
				// cellValue = cellValue != null ? cellValue
				// .trim() : null;
				// switch (columnNo) {
				// case 0:
				// argProperty.setPath(cellValue);
				// break;
				// case 1:
				// argProperty.setAlias(cellValue);
				// break;
				// case 2:
				// arg.setDirection(cellValue);
				// break;
				// case 3:
				// argProperty.setGraphicalPath(cellValue);
				// break;
				// default:
				// break;
				// }
				// }
				// }
				// }// End Of if (e.getFirstRow() == e.getLastRow()) {
				// }// End Of if
				// (decisionTableDesignViewer.getDecltable().getSelectedRows().length
				// == 1) {
				if (e.getType() == TableModelEvent.DELETE) {
					int[] rowIndices = decisionTableDesignViewer.getDecltable()
							.getSelectedRows();
					for (int row = rowIndices.length - 1; row >= 0; row--) {
						argList.remove(rowIndices[row]);
					}
					decisionTableDesignViewer.getDecltable().clearSelection();
				}// End Of if (e.getType() == TableModelEvent.DELETE &&
				// deleteDone == false) {
				// End Of if (decisionTableEModel != null) {

			}// End Of if (decisionTableEModelWrapper != null) {
		}// End of if (decisionTableModelManager != null) {
	}// End Of public void tableChanged(TableModelEvent e) {
}
