package com.tibco.cep.studio.rms.history.actions;

import org.eclipse.compare.CompareConfiguration;
import org.eclipse.compare.CompareUI;
import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.handlers.HandlerUtil;

import com.tibco.cep.studio.ui.StudioUIPlugin;

public class DiffAction extends AbstractHandler implements IObjectActionDelegate {

	private ISelection selection;

	/**
	 * the command has been executed, so extract extract the needed information
	 * from the application context.
	 */
	public Object execute(ExecutionEvent event) throws ExecutionException {
		try {
			if (selection == null && event != null) {
				IWorkbenchWindow window = HandlerUtil
				.getActiveWorkbenchWindowChecked(event);
				selection = window.getSelectionService().getSelection();
				if (selection == null) {
					return null;
				}
			}
			if (selection instanceof StructuredSelection) {
				StructuredSelection s = (StructuredSelection) selection;
				CompareConfiguration cc = new CompareConfiguration(StudioUIPlugin.getDefault().getPreferenceStore());
				RMSHistoryCompareEditorInput input = new RMSHistoryCompareEditorInput(cc);
				input.setSelection(s);
				CompareUI.openCompareDialog(input);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
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

	public void selectionChanged(IAction action, ISelection selection) {
		this.selection = selection;
	}

	@Override
	public void setActivePart(IAction action, IWorkbenchPart targetPart) {
		// TODO Auto-generated method stub
	}
}