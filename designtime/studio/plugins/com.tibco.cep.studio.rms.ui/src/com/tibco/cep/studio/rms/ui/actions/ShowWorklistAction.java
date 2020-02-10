package com.tibco.cep.studio.rms.ui.actions;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.ui.IEditorReference;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;

import com.tibco.cep.studio.rms.client.ui.RMSClientWorklistEditor;
import com.tibco.cep.studio.rms.client.ui.RMSClientWorklistEditorInput;
import com.tibco.cep.studio.rms.ui.utils.ActionConstants;
import com.tibco.cep.studio.rms.ui.utils.RMSUIUtils;
import com.tibco.cep.studio.ui.StudioUIManager;

public class ShowWorklistAction extends AbstractRMSAction {

	@Override
	public void init(IAction action) {
		StudioUIManager.getInstance().addAction(ActionConstants.WORKLIST_ACTION, action);
		action.setEnabled(false);
	}
	
	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.rms.actions.AbstractRMSAction#selectionChanged(org.eclipse.jface.action.IAction, org.eclipse.jface.viewers.ISelection)
	 */
	@Override
	public void selectionChanged(IAction action, ISelection selection) {
		super.selectionChanged(action, selection);
			if (RMSUIUtils.islogged()) {
				action.setEnabled(true);
			} else {
				action.setEnabled(false);
			}
	}
	
	@Override
	public void runWithEvent(IAction action, Event event) {
		final IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
		boolean isWorkListEditorOpen = false;
		IEditorReference[] editorReferences = page.getEditorReferences();
		IEditorReference activeEditorReference = null;
		if (editorReferences != null) {
			for (IEditorReference editorReference : editorReferences) {
				if (editorReference.getId().equals(RMSClientWorklistEditor.ID)) {
					isWorkListEditorOpen = true;
					activeEditorReference = editorReference;
					break;
				}
			}
		}
		if (!isWorkListEditorOpen) {
			final ProgressMonitorDialog pdialog = new ProgressMonitorDialog(
					PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell()) {
				@Override
				protected void createDialogAndButtonArea(Composite parent) {
					dialogArea = createDialogArea(parent);
					applyDialogFont(parent);
				}
			};
			pdialog.setCancelable(false);
			IRunnableWithProgress runnable = new IRunnableWithProgress() {
				public void run(IProgressMonitor monitor) {
					RMSClientWorklistEditorInput input = 
						new RMSClientWorklistEditorInput("Show Worklist");
					try {
						page.openEditor(input, RMSClientWorklistEditor.ID);
					} catch (PartInitException e) {
						e.printStackTrace();
					}
				}
			};
			try {
				pdialog.run(false, true, runnable);
			} catch (InvocationTargetException e) {
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
			pdialog.close();
		} else {
			try {
				page.openEditor(activeEditorReference.getEditorInput(), RMSClientWorklistEditor.ID);
			} catch (PartInitException e) {
				e.printStackTrace();
			}
		}
	}
}