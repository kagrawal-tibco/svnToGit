package com.tibco.cep.studio.ui.wizards;

import static com.tibco.cep.studio.ui.util.StudioResourceUtils.getExtensionFor;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.IWizardContainer;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.ui.IEditorInput;
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

import com.tibco.be.util.GUIDGenerator;
import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.studio.ui.StudioUIPlugin;
import com.tibco.cep.studio.ui.util.Messages;
import com.tibco.cep.studio.ui.util.StudioResourceUtils;

public abstract class AbstractNewEntityWizard<W extends EntityFileCreationWizard> extends Wizard implements INewWizard {

	protected W page = null;
	protected IStructuredSelection _selection;
	protected IProject project;
	protected String currentProjectName;
	protected String defaultEntityName = null;
	protected boolean openEditor = true; // This is for not to open editor when entity creation from diagram 
    protected IDiagramEntitySelection diagramEntitySelect;// This is for when entity creation from diagram
    
	public IProject getProject() {
		return project;
	}
	protected abstract String getEntityType();
	protected abstract String getWizardDescription();
	protected abstract String getWizardTitle();
	protected abstract ImageDescriptor getDefaultPageImageDescriptor();
	protected abstract void createEntity(final String filename,
			final String baseURI, IProgressMonitor monitor) throws Exception;

	protected void populateEntity(String filename, Entity entity) {
		entity.setName(filename);
		entity.setDescription(page.getTypeDesc());
		entity.setFolder(StudioResourceUtils.getFolder(getModelFile()));
		entity.setNamespace(StudioResourceUtils.getFolder(getModelFile()));
		entity.setGUID(GUIDGenerator.getGUID());
		entity.setOwnerProjectName(project.getName());
	}
	
	public void setDefaultEntityName(String defaultEntityName) {
		this.defaultEntityName = defaultEntityName;
	}

	@SuppressWarnings("unchecked")
	public void addPages() {
		try {
			if (_selection != null) {
				project = StudioResourceUtils.getProjectForWizard(_selection);
			}
			page = (W)new EntityFileCreationWizard(getWizardTitle(),_selection, getEntityType(), currentProjectName);
			page.setDescription(getWizardDescription());
			page.setTitle(getWizardTitle());
			if (defaultEntityName != null) {
				page.setFileName(defaultEntityName);
			}
			addPage(page);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void init(IWorkbench workbench, IStructuredSelection selection) {
		_selection = selection;
		if(getDefaultPageImageDescriptor()!= null)
			setDefaultPageImageDescriptor(getDefaultPageImageDescriptor());
	}

	public IFile getModelFile() {
		return page.getModelFile();
	}
    
	protected String fName;
    protected boolean isEntityCreationError = false;	
    
	public boolean performFinish() {
		isEntityCreationError = false;
		final String filename = page.getFileName();
		try {
			project = page.getProject();
			
			final String baseURI = StudioResourceUtils.getCurrentProjectBaseURI(project);
			fName = baseURI + "/"
				+ page.getContainerFullPath().toPortableString() + "/"
				+ page.getFileName()
				+ getExtensionFor(getEntityType());

			// create the new entity creation operation
			WorkspaceModifyOperation op = new WorkspaceModifyOperation() {
				protected void execute(IProgressMonitor monitor) throws CoreException {
					try{
					createEntity(filename, baseURI, monitor);
					}catch(final Exception e){
						isEntityCreationError = true;
						page.setErrorMessage(Messages.getString("new.resource.creation.error", page.getContainerFullPath().toPortableString(),getEntityType()));
					}
				}
			};
			// run the new entity creation operation
			try {
				getContainer().run(false, true, op);
			} catch (InterruptedException e) {
				isEntityCreationError = true;
				page.setErrorMessage(Messages.getString("new.resource.creation.error", page.getContainerFullPath().toPortableString(),getEntityType()));
//				e.printStackTrace();
			} catch (InvocationTargetException e) {
				isEntityCreationError = true;
				page.setErrorMessage(Messages.getString("new.resource.creation.error", page.getContainerFullPath().toPortableString(),getEntityType()));
//				e.printStackTrace();
			}

			if(isEntityCreationError){
				return false;
			}
			
			if(project != null){
				project.refreshLocal(IProject.DEPTH_INFINITE, null);
			}
//			if(_selection != null){
//				if(_selection.getFirstElement() instanceof IProject){
//					((IProject)_selection.getFirstElement()).refreshLocal(IProject.DEPTH_INFINITE, null);
//				}
//				else{
//					getCurrentProject(_selection).refreshLocal(IProject.DEPTH_INFINITE, null);
//				}
//			}

			IWorkbenchWindow workbenchWindow = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
			IWorkbenchPage workbenchPage = workbenchWindow.getActivePage();
			final IWorkbenchPart activePart = workbenchPage.getActivePart();
			if (activePart instanceof ISetSelectionTarget) {
				getShell().getDisplay().asyncExec
				(new Runnable() {
					public void run() {
						((ISetSelectionTarget)activePart).selectReveal(_selection);
					}
				});
			}
			
			if(diagramEntitySelect != null){
				diagramEntitySelect.setEntityFile(getModelFile());
			}
			
			// set the charset explicitly
//			modelFile.setCharset(ModelUtils.DEFAULT_ENCODING, new NullProgressMonitor());

			if(isOpenEditor()){
				// Open an editor on the new file.
				try {
					if (PlatformUI.getWorkbench().getEditorRegistry().getDefaultEditor(fName) != null) {
						workbenchPage.openEditor(getEditorInput(),
								PlatformUI.getWorkbench().getEditorRegistry().getDefaultEditor(fName).getId());
					}
					else {
						StudioUIPlugin.logErrorMessage("No default editor for: " + fName);
					}
				}
				catch (PartInitException exception) {

				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

		return true;
	}
	
	protected IEditorInput getEditorInput(){
		FileEditorInput fileEditorInput = new FileEditorInput(getModelFile());
		return fileEditorInput;
	}
	
	public boolean isOpenEditor() {
		return openEditor;
	}
	public void setOpenEditor(boolean openEditor) {
		this.openEditor = openEditor;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.wizard.Wizard#setContainer(org.eclipse.jface.wizard.IWizardContainer)
	 */
	public void setContainer(IWizardContainer wizardContainer) {
		if (wizardContainer != null) {
			((WizardDialog) wizardContainer).setHelpAvailable(false);
		}
		super.setContainer(wizardContainer);
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.jface.wizard.Wizard#isHelpAvailable()
	 */
	public boolean isHelpAvailable() {
		return false;
	}
}