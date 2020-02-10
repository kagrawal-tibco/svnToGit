package com.tibco.cep.decision.table.actions;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.compare.CompareUI;
import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;

import com.tibco.cep.decision.table.model.dtmodel.Table;
import com.tibco.cep.decision.table.ui.DecisionTableUIPlugin;
import com.tibco.cep.decision.table.ui.utils.Messages;
import com.tibco.cep.decision.table.utils.DecisionTableUtil;
import com.tibco.cep.decisionproject.ontology.Implementation;

/**
 * Action to compare two Decision Tables, both structurally as well as textually
 * (i.e. comparing the persisted xmi files)
 * 
 * @author rhollom
 * 
 */
public class CompareTableAction extends AbstractHandler implements IObjectActionDelegate {

	private ISelection selection;
	private String projectName;
	private Implementation implementation;
	
	/**
	 * @param implementation
	 * @param file
	 */
	public CompareTableAction(Implementation implementation, IFile file) {
		this.selection = new StructuredSelection(new Object[] {file });
		this.implementation = implementation;
	}

	/**
	 * the command has been executed, so extract extract the needed information
	 * from the application context.
	 */
	public Object execute(ExecutionEvent event) throws ExecutionException {
		if (selection instanceof StructuredSelection) {
			StructuredSelection ss = (StructuredSelection) selection;
			try {
				implementation = processTable((Table)implementation);
			} catch (OperationCanceledException e) {
				return null;
			}
			TableCompareEditorInput input = new TableCompareEditorInput(implementation);
			input.setSelection(ss);
			CompareUI.openCompareEditor(input);
		}
		return null;
	}

	/**
	 * @param ss
	 * @return
	 */
	protected StructuredSelection reloadDirtyTables(StructuredSelection ss) {
		Object[] selections = ss.toArray();
		List<Table> processedTables = new ArrayList<Table>();
		for (Object object : selections) {
			if (object instanceof Table) {
				Table table = (Table) object;
				if (table.isDirty()) {
					Table tbl = processTable(table);
					// tbl is the table that we actually want to use
					processedTables.add(tbl);
				} else {
					processedTables.add(table);
				}
			}
		}
		return new StructuredSelection(processedTables);
	}
	
	private Table processTable(Table table) throws OperationCanceledException {
		// if the table is open in an editor, prompt to save. If the user opts
		// not
		// to save, then use a temporary representation of the table to compare,
		// rather
		// than reloading the table. Otherwise, the existing table in the editor
		// will
		// be overwritten with the reloaded one, and will cause problems when/if
		// the table
		// is saved.
		IEditorPart openEditor = DecisionTableUtil.findOpenEditor(table);
		if (openEditor != null && openEditor.isDirty()) {
			if (!openEditor.getSite().getPage().saveEditor(openEditor, true)) {
				// canceled, do not do the compare
				throw new OperationCanceledException();
			}
			if (openEditor.isDirty()) {
				DecisionTableUIPlugin.debug(getClass().getName(),
						Messages.getString("History_LoadTable"));
				return DecisionTableUtil.loadTemporaryTable(table, projectName);
			}
		}
		if (table.isDirty()) {
			DecisionTableUIPlugin.debug(getClass().getName(),
					Messages.getString("History_ReloadTable"));
			return DecisionTableUtil.reloadTable(table, projectName);
		}
		return table;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.IActionDelegate#run(org.eclipse.jface.action.IAction)
	 */
	public void run(IAction action) {
		try {
			execute(null);
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.IActionDelegate#selectionChanged(org.eclipse.jface.action.IAction, org.eclipse.jface.viewers.ISelection)
	 */
	public void selectionChanged(IAction action, ISelection selection) {
		this.selection = selection;
	}

	@Override
	public void setActivePart(IAction action, IWorkbenchPart targetPart) {
		// TODO Auto-generated method stub
	}
}