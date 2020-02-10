package com.tibco.cep.studio.decision.table.listeners;
import org.eclipse.nebula.widgets.nattable.NatTable;
import org.eclipse.nebula.widgets.nattable.grid.layer.GridLayer;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.PlatformUI;

import com.tibco.cep.decision.table.model.dtmodel.TableRule;
import com.tibco.cep.decision.table.model.dtmodel.TableRuleVariable;
import com.tibco.cep.studio.decision.table.editor.DTBodyLayerStack;
import com.tibco.cep.studio.decision.table.editor.IDecisionTableEditor;
import com.tibco.cep.studio.decision.table.utils.DecisionTableUtil;
public class DTCellSelectionListener extends MouseAdapter{

	private NatTable nTable;
	private IDecisionTableEditor editor;
	DTBodyLayerStack<TableRule> targetDTStack;
	
	public DTCellSelectionListener(NatTable table, IDecisionTableEditor editor) {
		this.nTable = table;
		this.editor = editor;
		targetDTStack = (DTBodyLayerStack<TableRule>) ((GridLayer)nTable.getLayer()).getBodyLayer();
	}
	@Override
	public void mouseDown(MouseEvent e) {
		Object source = e.getSource();
		if (source instanceof NatTable) {
			Integer[] selectedRows;
			int[] selectedCols;
			int row,column;
			selectedRows = editor.getDtDesignViewer().getSelectedRows(targetDTStack);
			selectedCols = targetDTStack.getSelectionLayer().getSelectedColumnPositions();
			if (selectedRows.length == 1) {
				row = selectedRows[0];
				column = selectedCols[0];
				//TODO check for row column bounds
				setWorkbenchSelectionforCell(row,column);
			}
			
		}
		
	}
	
	private void setWorkbenchSelectionforCell(int row, int column) {
		final Object value = editor.getModelDataByPosition(column, row, nTable);
		if (value != null && value instanceof TableRuleVariable) {
			final TableRuleVariable drc = (TableRuleVariable) value;
			Display.getDefault().asyncExec(new Runnable(){
				@Override
				public void run() {
					IEditorPart editor = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getActiveEditor();
					DecisionTableUtil.setWorkbenchSelection(drc, editor);
				}});
		} 
		else if(value != null && value instanceof Integer){
			Display.getDefault().asyncExec(new Runnable(){
				@Override
				public void run() {
					IEditorPart editor = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getActiveEditor();
					DecisionTableUtil.setWorkbenchSelection(value, editor);
				}});
		}
		else {
			Display.getDefault().asyncExec(new Runnable(){
				@Override
				public void run() {
					IEditorPart editor = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getActiveEditor();
					DecisionTableUtil.setWorkbenchSelection(new Object(), editor);
				}});
		}
		
	}
}
