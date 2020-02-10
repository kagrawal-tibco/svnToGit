package com.tibco.cep.decision.table.handler;

import static com.tibco.cep.decision.table.utils.DecisionTableUtil.initPopups;
import static com.tibco.cep.studio.ui.util.StudioUIUtils.setWorkbenchSelection;

import javax.swing.JTable;

import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.IPartListener;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart;

import com.jidesoft.grid.TableScrollPane;
import com.tibco.cep.decision.table.editors.DecisionTableContentEditorInput;
import com.tibco.cep.decision.table.editors.DecisionTableDesignViewer;
import com.tibco.cep.decision.table.editors.DecisionTableEditor;
import com.tibco.cep.decision.table.editors.IDecisionTableEditorInput;
import com.tibco.cep.decision.table.model.dtmodel.Table;
import com.tibco.cep.decision.table.model.dtmodel.TableRuleVariable;
import com.tibco.cep.decision.table.utils.DecisionTableUtil;
import com.tibco.cep.studio.ui.overview.CommonOverview;
import com.tibco.cep.studio.ui.overview.DecisionTableOverviewUtils;
import com.tibco.cep.studio.ui.resources.JarEntryEditorInput;

/**
 * 
 * @author sasahoo
 * @author aathalye
 * @author rmishra
 * 
 */
public class DecisionTableEditorPartListener implements IPartListener {
	
	private DecisionTableEditor editor;

	/**
	 * @param editor
	 */
	public DecisionTableEditorPartListener(DecisionTableEditor editor) {
		this.editor = editor;
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.ui.IPartListener#partActivated(org.eclipse.ui.IWorkbenchPart)
	 */
	public void partActivated(IWorkbenchPart part) {

		if (part instanceof DecisionTableEditor) {
			if (editor.isInvalidPart()) {
				return;
			}
			
			// Ensure that only the listener for the attached editor executes
			if (part.getSite().getPage().getActiveEditor() != editor) {
				return;
			}
			setPartSelection(editor);
			
			//Also initialize cell editors
			DecisionTableDesignViewer designViewer = editor.getDecisionTableDesignViewer();
			if (designViewer != null) {
				Table eModel = editor.getDecisionTableModelManager().getTabelEModel();
				if (eModel != null) {
					designViewer.initConvertersEditors(eModel);
				}
			}
			
			IEditorSite site = (IEditorSite) editor.getSite();
			final IWorkbenchPage activePage = site.getPage();
			if (activePage == null) {
				return;
			}
			editor.openEditorPerspective(site);

			Table tableEModel = editor.getDecisionTableModelManager().getTabelEModel();

			if (tableEModel != null) {
				//Initialize static popup variables
				initPopups(); //TODO: Fix it later with Table Editor level variables
				editor.setFocus();
			}
			if (activePage.getActiveEditor() instanceof DecisionTableEditor) { 
				DecisionTableOverviewUtils.updateOverview(activePage, editor);
			}
		}
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.IPartListener#partClosed(org.eclipse.ui.IWorkbenchPart)
	 */
	public void partClosed(IWorkbenchPart part) {
		DecisionTableUtil.hideAllEditorPopups(part, false);
		IViewPart view = editor.getSite().getPage().findView(CommonOverview.ID);
		if (view instanceof CommonOverview) {
			CommonOverview commonOverview = (CommonOverview)view;
			if (part instanceof DecisionTableEditor) {
				DecisionTableOverviewUtils.refreshOverview(commonOverview, (DecisionTableEditor)part, false, false);
			}
		}
		
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.IPartListener#partDeactivated(org.eclipse.ui.IWorkbenchPart)
	 */
	public void partDeactivated(final IWorkbenchPart part) {
		DecisionTableUtil.hideAllEditorPopups(part, false);
	}

	public void partOpened(IWorkbenchPart part) {
		// TODO Auto-generated method stub
	}

	@Override
	public void partBroughtToTop(IWorkbenchPart part) {
		// TODO Auto-generated method stub
	}
	
	/**
	 * @param editor
	 */
	private void setPartSelection(DecisionTableEditor editor) {
		Table tableEModel = null;
		
		TableScrollPane decisionTableScrollPane = editor.getDecisionTableDesignViewer().getDecisionTablePane().getTableScrollPane();
		JTable mainDecisionTable = decisionTableScrollPane.getMainTable();

		TableScrollPane expTableScrollPane = editor.getDecisionTableDesignViewer().getExceptionTablePane().getTableScrollPane();
		JTable expDecisionTable = expTableScrollPane.getMainTable();
		
		if (mainDecisionTable.getSelectedRowCount() > 0) {
			setSelection(editor, mainDecisionTable);
		} else if (expDecisionTable.getSelectedRowCount() > 0) {
			setSelection(editor, expDecisionTable);
		} else {
			IDecisionTableEditorInput dtEditorInput= null;
			if (editor.isJar()) {
				dtEditorInput = new DecisionTableContentEditorInput((JarEntryEditorInput)editor.getEditorInput());
			} else {
				dtEditorInput = (IDecisionTableEditorInput)editor.getEditorInput();
			}
			tableEModel = dtEditorInput.getTableEModel();
			setWorkbenchSelection(tableEModel, editor);
		}
	}
	
	/**
	 * @param editor
	 * @param table
	 */
	private void setSelection(DecisionTableEditor editor, JTable table){
		int firstRow = table.getSelectedRow();
		int column = table.getSelectedColumn();
		TableRuleVariable tableRuleVariable =  null;
		if (firstRow != -1 && column != -1) {
			Object value = table.getValueAt(firstRow, column);
			if (value != null && value instanceof TableRuleVariable) {
				tableRuleVariable = (TableRuleVariable) value;
				setWorkbenchSelection(tableRuleVariable, editor);
			}
		}
	}

}
