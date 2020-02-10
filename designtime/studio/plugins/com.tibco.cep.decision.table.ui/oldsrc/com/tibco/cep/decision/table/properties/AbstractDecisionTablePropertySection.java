package com.tibco.cep.decision.table.properties;

import javax.swing.JTable;

import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.views.properties.tabbed.AbstractPropertySection;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;

import com.jidesoft.grid.TableScrollPane;
import com.tibco.cep.decision.table.editors.DecisionTableEditor;
import com.tibco.cep.decision.table.editors.IDecisionTableEditorInput;
import com.tibco.cep.decision.table.model.dtmodel.Table;
import com.tibco.cep.decision.table.model.dtmodel.TableRuleSet;
import com.tibco.cep.decision.table.model.dtmodel.TableRuleVariable;
import com.tibco.cep.decision.table.model.dtmodel.TableTypes;
import com.tibco.cep.decision.table.ui.DecisionTableUIPlugin;
import com.tibco.cep.decisionproject.util.DTConstants;

/**
 * 
 * @author sasahoo
 *
 */
public class AbstractDecisionTablePropertySection extends AbstractPropertySection {

	protected DecisionTablePropertySheetPage propertySheetPage;
	
	protected String project;
	
	protected DecisionTableEditor editor;
	
	protected JTable table;
	
	protected Table tableEModel;
	
	protected TableRuleVariable tableRuleVariable;
	
	protected int paneType = -1;
	
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
	
	/**
	 * @see org.eclipse.ui.views.properties.tabbed.ITabbedPropertySection#createControls(org.eclipse.swt.widgets.Composite,
	 *      org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage)
	 */
	public void createControls(Composite parent, TabbedPropertySheetPage tabbedPropertySheetPage) {
		super.createControls(parent, tabbedPropertySheetPage);
		this.propertySheetPage = (DecisionTablePropertySheetPage) tabbedPropertySheetPage;
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
			
			editor = (DecisionTableEditor) getPart();
			IDecisionTableEditorInput decisionTableEditorInput = (IDecisionTableEditorInput)editor.getEditorInput();
			project = decisionTableEditorInput.getProjectName();

			tableEModel = decisionTableEditorInput.getTableEModel();
			Object firstElement = ((IStructuredSelection) selection).getFirstElement();
			if (firstElement instanceof TableRuleVariable) {
				setPaneType();
				if (DTConstants.DECISION_TABLE == paneType) {
					decisionTableRuleSet = tableEModel.getDecisionTable();
					tableType = TableTypes.DECISION_TABLE;
				} else if (DTConstants.EXCEPTION_TABLE == paneType) {
					decisionTableRuleSet = tableEModel.getExceptionTable();
					tableType = TableTypes.EXCEPTION_TABLE;
				}
				tableRuleVariable = (TableRuleVariable)firstElement;
			}
			
		} catch (Exception e) {
			DecisionTableUIPlugin.log(e);
		}
	}
	
	protected void setPaneType() {
		selectedRow = -1;
		paneType = -1;
		//For Decision Table Pane
		TableScrollPane tableScrollPane = 
			editor.getDecisionTableDesignViewer().getDecisionTablePane().getTableScrollPane();
		table = tableScrollPane.getMainTable();
		if (table.getSelectedRow() >= 0) {
			paneType = DTConstants.DECISION_TABLE;
			selectedRow = table.getSelectedRow();
			selectedColumn = table.getSelectedColumn();
			return;
		}
		//For Exception Table Pane
		tableScrollPane = 
			editor.getDecisionTableDesignViewer().getExceptionTablePane().getTableScrollPane();
		table = tableScrollPane.getMainTable();
		if (table.getSelectedRow() >= 0) {
			paneType = DTConstants.EXCEPTION_TABLE;
			selectedRow = table.getSelectedRow();
			selectedColumn = table.getSelectedColumn();
		}
	}
}