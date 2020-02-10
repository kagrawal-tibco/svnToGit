package com.tibco.cep.studio.decision.table.ui.properties;

import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.nebula.widgets.nattable.NatTable;
import org.eclipse.nebula.widgets.nattable.grid.layer.GridLayer;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.views.properties.tabbed.AbstractPropertySection;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;

import com.tibco.cep.decision.table.model.dtmodel.Table;
import com.tibco.cep.decision.table.model.dtmodel.TableRule;
import com.tibco.cep.decision.table.model.dtmodel.TableRuleSet;
import com.tibco.cep.decision.table.model.dtmodel.TableRuleVariable;
import com.tibco.cep.decision.table.model.dtmodel.TableTypes;
import com.tibco.cep.decisionproject.util.DTConstants;
import com.tibco.cep.studio.decision.table.editor.DTBodyLayerStack;
import com.tibco.cep.studio.decision.table.editor.IDecisionTableEditor;
import com.tibco.cep.studio.decision.table.ui.DecisionTableUIPlugin;

/**
 * 
 * @author smahajan
 *
 */
public abstract class AbstractDTPropertySection extends AbstractPropertySection{
	protected TabbedPropertySheetPage propertySheetPage;
	
	protected String project;
	
	protected IDecisionTableEditor editor;
	
	protected Table tableEModel;
	
	protected TableRuleVariable tableRuleVariable;
	
	protected int tType = -1;
	
	protected int selectedRow = -1;
	
	/**
	 * The column selected.
	 */
	protected int selectedColumn = -1;
	
	/**
	 * The table type we are working with
	 */
	protected TableTypes tableType;
	
	protected TableRuleSet decisionTableRuleSet;
	
	public abstract void disablePropertyPage();
	
	/**
	 * @see org.eclipse.ui.views.properties.tabbed.ITabbedPropertySection#createControls(org.eclipse.swt.widgets.Composite,
	 *      org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage)
	 */
	public void createControls(Composite parent, TabbedPropertySheetPage tabbedPropertySheetPage) {
		super.createControls(parent, tabbedPropertySheetPage);
		this.propertySheetPage = tabbedPropertySheetPage;
	}
	
	/**
	 * @see org.eclipse.ui.views.properties.tabbed.ISection#setInput(org.eclipse.ui.IWorkbenchPart,
	 *      org.eclipse.jface.viewers.ISelection)
	 */
	public void setInput(IWorkbenchPart part, ISelection selection) {
		super.setInput(part, selection);
		if (!(selection instanceof IStructuredSelection)) {
			return;
		}
		try {
			tableEModel = null;
			tableRuleVariable = null;
			decisionTableRuleSet = null;
			
			editor = (IDecisionTableEditor) getPart();
			
			tableEModel = editor.getTable();
			project = tableEModel.getOwnerProjectName();
			
			Object firstElement = ((IStructuredSelection) selection).getFirstElement();
			if (firstElement instanceof TableRuleVariable) {
				setPaneType();
				if (DTConstants.DECISION_TABLE == tType) {
					decisionTableRuleSet = tableEModel.getDecisionTable();
					tableType = TableTypes.DECISION_TABLE;
				} else if (DTConstants.EXCEPTION_TABLE == tType) {
					decisionTableRuleSet = tableEModel.getExceptionTable();
					tableType = TableTypes.EXCEPTION_TABLE;
				}
				tableRuleVariable = (TableRuleVariable)firstElement;
			}
			if (!editor.isEnabled()) disablePropertyPage();
		} catch (Exception e) {
			DecisionTableUIPlugin.log(e);
		}
	}
	
	protected void setPaneType() {
		selectedRow = -1;
		tType = -1;
		//For Decision Table

		NatTable DTNatTable = editor.getDtDesignViewer().getDecisionTable();
		DTBodyLayerStack<TableRule> targetDTStack = (DTBodyLayerStack<TableRule>) ((GridLayer)DTNatTable.getLayer()).getBodyLayer();
		Integer[] selectedRows;
		int[] selectedCols;
		selectedRows = editor.getDtDesignViewer().getSelectedRows(targetDTStack);
		selectedCols = targetDTStack.getSelectionLayer().getSelectedColumnPositions();
		if (selectedRows.length == 1) {
			tType = DTConstants.DECISION_TABLE;
			selectedRow = selectedRows[0];
			selectedColumn = selectedCols[0];
			return;
		}
		//For Exception Table Pane
		NatTable ETNatTable = editor.getDtDesignViewer().getExceptionTable();
		DTBodyLayerStack<TableRule> targetETStack = (DTBodyLayerStack<TableRule>) ((GridLayer)ETNatTable.getLayer()).getBodyLayer();
		selectedRows = editor.getDtDesignViewer().getSelectedRows(targetETStack);
		selectedCols = targetETStack.getSelectionLayer().getSelectedColumnPositions();
		if (selectedRows.length == 1) {
			tType = DTConstants.DECISION_TABLE;
			selectedRow = selectedRows[0];
			selectedColumn = selectedCols[0];
			return;
		}
	}
}
