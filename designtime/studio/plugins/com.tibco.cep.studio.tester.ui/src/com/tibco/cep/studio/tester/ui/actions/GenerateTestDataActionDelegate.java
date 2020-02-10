package com.tibco.cep.studio.tester.ui.actions;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IPartListener;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;
import org.eclipse.ui.PlatformUI;

import com.tibco.cep.studio.core.nature.StudioProjectNature;
import com.tibco.cep.studio.ui.StudioUIPlugin;
import com.tibco.cep.studio.ui.util.Messages;
import com.tibco.cep.studio.ui.util.StudioResourceUtils;
import com.tibco.cep.studio.ui.widgets.StudioWizardDialog;
import com.tibco.cep.studio.ui.wizards.NewTestDataWizard;
import com.tomsawyer.graph.TSGraphObject;

public class GenerateTestDataActionDelegate implements IObjectActionDelegate,IWorkbenchWindowActionDelegate,IPartListener {

	private ISelection _selection;
	private IWorkbenchWindow fWindow;
	private IProject fProject;
	private Shell fShell;
	private IWorkbenchPart fPart;
	


	/* (non-Javadoc)
	 * @see org.eclipse.ui.IActionDelegate#run(org.eclipse.jface.action.IAction)
	 */
	public void run(IAction action) {
		try {
			if (fWindow == null) {
				fWindow = PlatformUI.getWorkbench().getActiveWorkbenchWindow(); 
				this.fShell = fWindow.getShell();
				fWindow.getPartService().addPartListener(this);
			}
			NewTestDataWizard wizard = new NewTestDataWizard(this.fWindow,  Messages.getString("create.testdata"),this.fProject,(IStructuredSelection)_selection);
			
			/*	wizard.setNeedsProgressMonitor(true);
				StudioWizardDialog dialog = new StudioWizardDialog(this.fShell, wizard) {
					@Override
					protected void createButtonsForButtonBar(Composite parent) {
						super.createButtonsForButtonBar(parent);
						Button finishButton = getButton(IDialogConstants.FINISH_ID);
						finishButton.setText(IDialogConstants.OK_LABEL);
					}
				};
				dialog.create();
				int returnCode = dialog.open();
				if (returnCode == Dialog.OK) {
				
				}*/
		} catch (Exception e) {
			// StudioUIPlugin.log(e);
			MessageDialog.openError(fWindow.getShell(), Messages.getString("create.testdata"), e.getMessage());
			//run(action);// invoking the Build EAR action
		}
	}

	
	@Override
	public void init(IWorkbenchWindow window) {
		// TODO Auto-generated method stub
		this.fWindow = window;
		this.fShell = fWindow.getShell();
		fWindow.getPartService().addPartListener(this);
	}

	@Override
	public void partActivated(IWorkbenchPart part) {
		// TODO Auto-generated method stub
		fPart = part.getSite().getPart();
	}
	
	public void selectionChanged(IAction action, ISelection selection) {
		this._selection = selection;
		try {
			this.fProject = getProjectFromSelection(_selection);
			if(this.fProject == null && this.fPart != null && fPart instanceof IEditorPart) {
				IEditorPart p = (IEditorPart) fPart;
				this.fProject = (IProject) p.getAdapter(IProject.class);
				if(this.fProject == null) {
					IEditorInput edInput = p.getEditorInput();
					if(edInput != null && edInput instanceof IFileEditorInput) {
						IFileEditorInput finput = (IFileEditorInput) edInput;
						IFile file = finput.getFile();
						if(file != null) {
							fProject = file.getProject();
						}
					}
				}
			}
			action.setEnabled(fProject != null && fProject.isAccessible() && fProject.hasNature(StudioProjectNature.STUDIO_NATURE_ID));
		} catch (Exception e) {
			StudioUIPlugin.log("Failed to create test data");
			StudioUIPlugin.log(e);
		}
	}

	protected IProject getProjectFromSelection(ISelection selection)
			throws Exception {
		if ((selection != null) && (selection instanceof IStructuredSelection)) {
			IStructuredSelection structuredSelection = (IStructuredSelection) selection;

			if (structuredSelection.size() > 1)
				return null;
			if (!selection.isEmpty()) {
				if (StudioResourceUtils.isStudioProject(structuredSelection)) {
					return StudioResourceUtils
							.getCurrentProject(structuredSelection);
				}
				Object obj = structuredSelection.getFirstElement();
				if (obj instanceof TSGraphObject) {
					IFile file = (IFile) ((TSGraphObject) obj)
							.getAttributeValue(IFile.class.getName());
					if (file != null) {
						return file.getProject();
					}
				}
			}

			// if selection is empty then check active editor

			if (fWindow != null) {
				IWorkbenchPage activePage = fWindow.getActivePage();
				if (activePage != null) {
					IEditorPart editor = activePage.getActiveEditor();
					if (editor != null) {
						return (IProject) editor.getAdapter(IProject.class);
					}
				}
			}
		}
		return null;
	}
	


	@Override
	public void partBroughtToTop(IWorkbenchPart part) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void partClosed(IWorkbenchPart part) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void partDeactivated(IWorkbenchPart part) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void partOpened(IWorkbenchPart part) {
		// TODO Auto-generated method stub
		
	}
	/* (non-Javadoc)
	 * @see org.eclipse.ui.IObjectActionDelegate#setActivePart(org.eclipse.jface.action.IAction, org.eclipse.ui.IWorkbenchPart)
	 */
	public void setActivePart(IAction action, IWorkbenchPart targetPart) {
		// TODO Auto-generated method stub
	}


	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

}