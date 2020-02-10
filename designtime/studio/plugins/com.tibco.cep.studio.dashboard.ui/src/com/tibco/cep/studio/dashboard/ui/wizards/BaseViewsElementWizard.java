package com.tibco.cep.studio.dashboard.ui.wizards;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.IEditorDescriptor;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.WorkspaceModifyOperation;
import org.eclipse.ui.part.FileEditorInput;
import org.eclipse.ui.part.ISetSelectionTarget;

import com.tibco.cep.studio.dashboard.core.insight.model.LocalConfig;
import com.tibco.cep.studio.dashboard.core.model.impl.LocalECoreFactory;
import com.tibco.cep.studio.dashboard.ui.DashboardUIPlugin;
import com.tibco.cep.studio.dashboard.ui.utils.DashboardResourceUtils;
import com.tibco.cep.studio.dashboard.utils.BEViewsElementNames;
import com.tibco.cep.studio.ui.wizards.EntityFileCreationWizard;

public abstract class BaseViewsElementWizard extends Wizard implements INewWizard {

	protected EntityFileCreationWizard entityCreatingWizardPage = null;
	protected IStructuredSelection _selection;
	// protected IProject project;

	protected String elementType;
	protected String elementTypeName;

	protected String wizardTitle;

	protected String pageName;
	protected String pageTitle;
	protected String pageDesc;


	public BaseViewsElementWizard(String elementType, String elementTypeName, String wizardTitle, String pageName, String pageTitle, String pageDesc) {
		this.elementType = elementType;
		this.elementTypeName = elementTypeName;
		this.wizardTitle = wizardTitle;
		this.pageName = pageName;
		this.pageTitle = pageTitle;
		this.pageDesc = pageDesc;
		setWindowTitle(this.wizardTitle);
	}

	public void addPages() {
		try {
			// if(_selection != null){
			// project = getProjectForWizard(_selection);
			// }
			entityCreatingWizardPage = createPage();
			addPage(entityCreatingWizardPage);
		} catch (Exception e) {
			// TODO log the exception and show it to user ?
			e.printStackTrace();
		}
	}

	protected EntityFileCreationWizard createPage() {
		EntityFileCreationWizard page = new DashboardEntityFileCreationWizard(pageName, _selection, elementType, elementTypeName);
		page.setDescription(pageDesc);
		page.setTitle(pageTitle);
		return page;
	}

	/***************************************************************************
	 * Initializes this creation wizard using the passed workbench and object selection.
	 * <p>
	 * This method is called after the no argument constructor and before other methods are called.
	 * </p>
	 *
	 * @param workbench
	 *            the current workbench
	 * @param selection
	 *            the current object selection
	 *
	 * @see org.eclipse.ui.IWorkbenchWizard#init(IWorkbench, IStructuredSelection)
	 */
	public void init(IWorkbench workbench, IStructuredSelection selection) {
		_selection = selection;
	}

	public IFile getModelFile() {
		return entityCreatingWizardPage.getModelFile();
	}

	/***************************************************************************
	 * Creates a project with a <code>CustomNature</code> association.
	 *
	 * @return <code>true</code> to indicate the finish request was accepted, and <code>false</code> to indicate that the finish request was refused
	 *
	 * @see org.eclipse.jface.wizard.IWizard#performFinish()
	 */
	public boolean performFinish() {
		IProject project = entityCreatingWizardPage.getProject();
		if (project == null) {
			return false;
		}
		final IFile modelFile = getModelFile();
		final String elementName = entityCreatingWizardPage.getFileName();
		final String baseURI = DashboardResourceUtils.getCurrentProjectBaseURI(project);
		String fname = baseURI + entityCreatingWizardPage.getContainerFullPath().toPortableString() + "/" + elementName + "." + BEViewsElementNames.getExtension(elementType);
		// create the new entity creation operation
		WorkspaceModifyOperation op = new WorkspaceModifyOperation() {
			protected void execute(IProgressMonitor monitor) throws CoreException {
				try {
					persistFirstPage(elementName, entityCreatingWizardPage.getTypeDesc(), baseURI, monitor);
				} catch (Exception e) {
					throw new CoreException(new Status(IStatus.ERROR,DashboardUIPlugin.PLUGIN_ID,"could not persist "+elementName,e));
				}
			}
		};
		// run the new entity creation operation
		try {
			getContainer().run(false, true, op);
			//since we run without forking, we can call this code right after the run(...)
			if (project != null) {
				project.refreshLocal(IProject.DEPTH_INFINITE, null);
			}
			IWorkbenchWindow workbenchWindow = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
			IWorkbenchPage workbenchPage = workbenchWindow.getActivePage();
			final IWorkbenchPart activePart = workbenchPage.getActivePart();
			if (activePart instanceof ISetSelectionTarget) {
				getShell().getDisplay().asyncExec(new Runnable() {
					public void run() {
						((ISetSelectionTarget) activePart).selectReveal(_selection);
					}
				});
			}
			// Open an editor on the new file.
			IEditorDescriptor defaultEditor = PlatformUI.getWorkbench().getEditorRegistry().getDefaultEditor(fname);
			if (defaultEditor != null) {
				try {
					workbenchPage.openEditor(new FileEditorInput(modelFile), defaultEditor.getId());
				} catch (PartInitException e) {
					DashboardUIPlugin.getInstance().getLog().log(new Status(IStatus.ERROR,DashboardUIPlugin.PLUGIN_ID,"could not open "+modelFile,e));
					MessageDialog.openError(getShell(), getWindowTitle(), "could not open "+elementName);
					return true;
				}
			}
		} catch (InterruptedException e) {
			DashboardUIPlugin.getInstance().getLog().log(new Status(IStatus.ERROR,DashboardUIPlugin.PLUGIN_ID,"could not create "+modelFile,e));
			MessageDialog.openError(getShell(), getWindowTitle(), "could not create "+elementName);
		} catch (InvocationTargetException e) {
			DashboardUIPlugin.getInstance().getLog().log(new Status(IStatus.ERROR,DashboardUIPlugin.PLUGIN_ID,"could not create "+modelFile,e));
			MessageDialog.openError(getShell(), getWindowTitle(), "could not create "+elementName);
		} catch (CoreException e){
			DashboardUIPlugin.getInstance().getLog().log(new Status(IStatus.WARNING,DashboardUIPlugin.PLUGIN_ID,"could not refresh "+project.getName(),e));
			MessageDialog.openWarning(getShell(), getWindowTitle(), "could not refresh "+project);
		}
		return true;
	}

	protected void persistFirstPage(String elementName, String elementDesc, String baseURI, IProgressMonitor monitor) throws Exception {
		IProject project = entityCreatingWizardPage.getProject();
		// create element, set the properties and call synchronize
		LocalConfig localConfig = (LocalConfig) LocalECoreFactory.getInstance(project).createLocalElement(elementType);
		localConfig.getID();
		localConfig.setName(elementName);
		localConfig.setDisplayName(elementName);
		localConfig.setDescription(elementDesc);
		localConfig.setFolder(DashboardResourceUtils.getFolder(getModelFile()));
		localConfig.setNamespace(DashboardResourceUtils.getFolder(getModelFile()));
		localConfig.setOwnerProject(project.getName());
		// create element, set the properties and call synchronize
		persistFirstPage(localConfig, elementName, elementDesc, baseURI, monitor);
	}

	protected void persistFirstPage(LocalConfig localConfig, String elementName, String elementDesc, String baseURI, IProgressMonitor monitor) throws Exception {
		localConfig.synchronize();
		// Persist the element
		DashboardResourceUtils.persistEntity(localConfig.getEObject(), baseURI, entityCreatingWizardPage.getProject(), monitor);
		// LocalECoreFactory.getInstance(project).refresh(localConfig.getInsightType());
		// String superType = BEViewsElementNames.getSuperType(localConfig.getInsightType());
		// if (superType != null) {
		// LocalECoreFactory.getInstance(project).refresh(superType);
		// }
	}
}