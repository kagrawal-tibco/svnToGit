package com.tibco.cep.studio.rms.history.actions;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.ui.IEditorReference;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;

import com.tibco.cep.studio.rms.artifacts.RMSRepo;
import com.tibco.cep.studio.rms.artifacts.manager.impl.RMSArtifactsSummaryManager;
import com.tibco.cep.studio.rms.history.RMSHistoryEditor;
import com.tibco.cep.studio.rms.history.RMSHistoryEditorInput;
import com.tibco.cep.studio.rms.ui.RMSUIPlugin;
import com.tibco.cep.studio.rms.ui.actions.AbstractRMSAction;
import com.tibco.cep.studio.rms.ui.utils.ActionConstants;
import com.tibco.cep.studio.rms.ui.utils.RMSUIUtils;
import com.tibco.cep.studio.ui.StudioUIManager;

/**
 * 
 * @author sasahoo
 *
 */
public class ShowHistoryAction extends AbstractRMSAction {

	private boolean isHistoryEditorOpen = false;
	private RMSHistoryEditor rmsHistoryEditor = null;

	/* (non-Javadoc)
	 * @see org.eclipse.ui.IActionDelegate2#init(org.eclipse.jface.action.IAction)
	 */
	@Override
	public void init(IAction action) {
		StudioUIManager.getInstance().addAction(ActionConstants.SHOW_HISTORY_ACTION, action);
		action.setEnabled(false);
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.rms.actions.AbstractRMSAction#selectionChanged(org.eclipse.jface.action.IAction, org.eclipse.jface.viewers.ISelection)
	 */
	@Override
	public void selectionChanged(IAction action, ISelection selection) {
		//Clean out old reference if any.
		m_SelectedResource = null;
		if (selection instanceof IStructuredSelection) {
			IStructuredSelection structuredSelection = (IStructuredSelection)selection;
			Object firstElement = structuredSelection.getFirstElement();
			if (firstElement instanceof IFile) {
				m_SelectedResource = (IResource)firstElement;
			}
		}
		if (RMSUIUtils.islogged() && m_SelectedResource != null) {
			action.setEnabled(true);
		} else {
			action.setEnabled(false);
		}
	}

	@Override
	public void runWithEvent(IAction action, Event event) {
		final IWorkbenchPage page = 
			PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
		IProject project = m_SelectedResource.getProject();
		final String projectName = project.getName();
		//Get URL same as the one with checkout
		RMSRepo rmsRepo = RMSArtifactsSummaryManager.getInstance().getRMSRepoInfo(projectName);
		if (rmsRepo == null) {
			//TODO error dialog
			return;
		}
		final String repoURL = rmsRepo.getRepoURL();
		final String repoProject = rmsRepo.getRmsProject();

		IEditorReference[] editorReferences = page.getEditorReferences();
		if (editorReferences != null) {
			for (IEditorReference editorReference : editorReferences) {
				if (editorReference.getId().equals(RMSHistoryEditor.ID)) {
					isHistoryEditorOpen = true;
					rmsHistoryEditor = (RMSHistoryEditor)editorReference.getEditor(false);
					break;
				}
			}
		}
		openEditor(page, repoURL, repoProject);

	}

	/**
	 * @param page
	 * @param repoURL
	 * @param repoProject
	 */
	private void openEditor(final IWorkbenchPage page, 
			                final String repoURL, 
			                final String repoProject) {
		final ProgressMonitorDialog pdialog = new ProgressMonitorDialog(page.getWorkbenchWindow().getShell()) {
			@Override
			protected void createDialogAndButtonArea(Composite parent) {
				dialogArea = createDialogArea(parent);
				applyDialogFont(parent);
			}
		};
		pdialog.setCancelable(false);
		IRunnableWithProgress runnable = new IRunnableWithProgress() {
			public void run(IProgressMonitor monitor) {
				if (m_SelectedResource != null) {
					final String resourcePath ="/" + m_SelectedResource.getFullPath().
					removeFileExtension().
					removeFirstSegments(1).
					toString();
					if (!isHistoryEditorOpen) {
						RMSHistoryEditorInput input = new RMSHistoryEditorInput(repoURL,
								"Show History", resourcePath, repoProject, m_SelectedResource.getProject().getName());
						try {
							page.openEditor(input, RMSHistoryEditor.ID);
						} catch (PartInitException e) {
							RMSUIPlugin.log(e);
						}
					} else {
						rmsHistoryEditor.refresh(repoURL, resourcePath, repoProject);
						if (page.getActiveEditor() != rmsHistoryEditor) {
							page.activate(rmsHistoryEditor);
						}
					}
				}
			}
		};
		try {
			pdialog.run(false, true, runnable);
		} catch (InvocationTargetException e) {
		} catch (InterruptedException e) {
			RMSUIPlugin.log(e);
		} catch (Exception e) {
			RMSUIPlugin.log(e);
		}
		pdialog.close();
	}
}