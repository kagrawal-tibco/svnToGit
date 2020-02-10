package com.tibco.cep.studio.ui.actions;

import static com.tibco.cep.studio.ui.util.StudioUIUtils.saveAllEditors;

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
import org.eclipse.ui.IPartListener2;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchPartReference;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;
import org.eclipse.ui.PlatformUI;

import com.tibco.cep.studio.core.nature.StudioProjectNature;
import com.tibco.cep.studio.ui.StudioUIPlugin;
import com.tibco.cep.studio.ui.util.Messages;
import com.tibco.cep.studio.ui.util.StudioResourceUtils;
import com.tibco.cep.studio.ui.widgets.StudioWizardDialog;
import com.tibco.cep.studio.ui.wizards.GeneratePOMWizard;
import com.tomsawyer.graph.TSGraphObject;

/**
 * 
 * @author pdeokar
 * 
 */
public class GeneratePOMAction implements
		IWorkbenchWindowActionDelegate, IObjectActionDelegate, IPartListener2 {
	private IWorkbenchPart fPart = null;
	private IWorkbenchWindow fWindow;
	private IProject fProject;
	private Shell fShell;
	private ISelection fSelection;
	public final String CDD_EXTENSION = "cdd";
	public final String SITE_TOPOLOGY_EXTENSION = "st";

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.IWorkbenchWindowActionDelegate#dispose()
	 */
	public void dispose() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.eclipse.ui.IWorkbenchWindowActionDelegate#init(org.eclipse.ui.
	 * IWorkbenchWindow)
	 */
	public void init(IWorkbenchWindow window) {
		this.fWindow = window;
		this.fShell = fWindow.getShell();
		fWindow.getPartService().addPartListener(this);
	}
	

	@Override
	public void partActivated(IWorkbenchPartReference partRef) {
		fPart = partRef.getPart(false);
		
	}

	@Override
	public void partBroughtToTop(IWorkbenchPartReference partRef) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void partClosed(IWorkbenchPartReference partRef) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void partDeactivated(IWorkbenchPartReference partRef) {
		
		IWorkbenchPart pPart = partRef.getPart(false);
		if(pPart == fPart) {
			fPart = null;
		}
		
	}

	@Override
	public void partHidden(IWorkbenchPartReference partRef) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void partInputChanged(IWorkbenchPartReference partRef) {
		fPart = partRef.getPart(false);
	}

	@Override
	public void partOpened(IWorkbenchPartReference partRef) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void partVisible(IWorkbenchPartReference partRef) {
		// TODO Auto-generated method stub
		
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.IActionDelegate#run(org.eclipse.jface.action.IAction)
	 */
	public void run(IAction action) {
		try {
			if (fWindow == null) {
				fWindow = PlatformUI.getWorkbench().getActiveWorkbenchWindow(); 
				this.fShell = fWindow.getShell();
				fWindow.getPartService().addPartListener(this);
			}
			if (fProject != null) {
				// Save all editors if dirty for the project selected
				if (saveAllEditors(fWindow.getActivePage(), fProject.getName(),
						true)) {
					boolean status = MessageDialog.openQuestion(this.fShell,
							Messages.getString("Build.EAR.save.editors.title"),
							Messages.getString("Build.EAR.save.editors.desc",
									fProject.getName()));
					if (status) {
						saveAllEditors(fWindow.getActivePage(), fProject
								.getName(), false);
					} else {
						return;
					}
				}
				GeneratePOMWizard wizard = new GeneratePOMWizard(
						this.fWindow, Messages.getString("Generate.POM.task"),
						this.fProject);
				wizard.setNeedsProgressMonitor(true);
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
					
				}
			}
		} catch (Exception e) {
			
		}
	}

	

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.ui.IActionDelegate#selectionChanged(org.eclipse.jface.action
	 * .IAction, org.eclipse.jface.viewers.ISelection)
	 */

	@Override
	public void setActivePart(IAction action, IWorkbenchPart targetPart) {
		fPart = targetPart;

	}

	@Override
	public void selectionChanged(IAction action, ISelection selection) {
		this.fSelection = selection;
		try {
			this.fProject = getProjectFromSelection(fSelection);
			// if project is not null then project is open and is a valid studio
			// project
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
			StudioUIPlugin.log("Failed to set pom generation enablement.");
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

}