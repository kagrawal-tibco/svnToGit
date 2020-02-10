package com.tibco.cep.studio.ui.actions;

import static com.tibco.cep.studio.ui.util.StudioUIUtils.saveAllEditors;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
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
import com.tibco.cep.studio.core.util.StudioProjectUtil;
import com.tibco.cep.studio.ui.StudioUIPlugin;
import com.tibco.cep.studio.ui.util.Messages;
import com.tibco.cep.studio.ui.util.StudioResourceUtils;

/**
 * 
 * @author sasahoo
 *
 */
public class ConvertToStudioProjectAction implements IWorkbenchWindowActionDelegate, 
                                        	   IObjectActionDelegate, 
											   IPartListener2 {
	private IWorkbenchPart fPart = null;
	private IWorkbenchWindow fWindow;
	private IProject fProject;
	private Shell fShell;
	private ISelection fSelection;
	private boolean buildInProgress = false;

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
				//default build in progress flag set false
				buildInProgress = false; 
				// by this time the project is a valid studio project and is
				// open
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

				StudioProjectUtil.addStudioJavaProjectNature(fProject);

			}
		} catch (Exception e) {
			// StudioUIPlugin.log(e);
			MessageDialog.openError(fWindow.getShell(), "Convert to Studio Project", e.getMessage());
			run(action);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.ui.IActionDelegate#selectionChanged(org.eclipse.jface.action
	 * .IAction, org.eclipse.jface.viewers.ISelection)
	 */
	public void selectionChanged(IAction action, ISelection selection) {

		//If build archive is in progress, there will be no selection change
		if (buildInProgress) {
			return;
		}

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
			action.setEnabled(fProject != null && fProject.isAccessible() && !fProject.hasNature(StudioProjectNature.STUDIO_NATURE_ID));
		} catch (Exception e) {
			StudioUIPlugin.log("Failed." + e);
		}
	}

	protected IProject getProjectFromSelection(ISelection selection)
			throws Exception {
		if ((selection != null) && (selection instanceof IStructuredSelection)) {
			IStructuredSelection structuredSelection = (IStructuredSelection) selection;

			if (structuredSelection.size() > 1)
				return null;
			if (!selection.isEmpty()) {
				IProject project = StudioResourceUtils.getCurrentProject(structuredSelection);
				if (project != null) {
					return project;
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
	public void setActivePart(IAction action, IWorkbenchPart targetPart) {
		fPart = targetPart;

	}

}