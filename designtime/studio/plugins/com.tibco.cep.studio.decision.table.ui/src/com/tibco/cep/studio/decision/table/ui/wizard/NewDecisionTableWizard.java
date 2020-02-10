/**
 * 
 */
package com.tibco.cep.studio.decision.table.ui.wizard;

import static com.tibco.cep.studio.ui.navigator.util.ProjectExplorerUtils.refreshView;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.WorkspaceModifyOperation;
import org.eclipse.ui.part.ISetSelectionTarget;

import com.tibco.cep.decision.table.model.dtmodel.Table;
import com.tibco.cep.decisionproject.ontology.RuleFunction;
import com.tibco.cep.studio.core.index.utils.IndexUtils;
import com.tibco.cep.studio.core.utils.ModelUtils;
import com.tibco.cep.studio.decision.table.ui.DecisionTableUIPlugin;
import com.tibco.cep.studio.decision.table.ui.editors.DefaultDecisionTableEditorInput;
import com.tibco.cep.studio.ui.StudioUIPlugin;
import com.tibco.cep.studio.ui.util.Messages;
import com.tibco.cep.studio.ui.util.StudioResourceUtils;
import com.tibco.cep.studio.ui.util.StudioWorkbenchConstants;
import com.tibco.cep.studio.ui.wizards.AbstractNewEntityWizard;


// This is the changed class similar to other new entity wizards
public class NewDecisionTableWizard extends AbstractNewEntityWizard<NewDecisionTableWizardPage<NewDecisionTableWizard>> implements IVRFSelectionWizard {

	private String virtualRFPath = null;
	
	private IFile vrfFile;
	
	private String projectPath = "";
	
	private String folderPath = "";
	/**
	 * The {@link RuleFunction} for which to create this table
	 */
	private RuleFunction baseResource;

	public NewDecisionTableWizard() {
		setWindowTitle(Messages.getString("new.decision.table.wizard.title"));
	}

	protected void createEntity(String filename, String baseURI,
			IProgressMonitor monitor) throws Exception {
	}

	protected ImageDescriptor getDefaultPageImageDescriptor() {
		return DecisionTableUIPlugin.getImageDescriptor("icons/new_dt_wizard.gif");
	}

	protected String getEntityType() {
		return StudioWorkbenchConstants._WIZARD_TYPE_NAME_DT;
	}

	protected String getWizardDescription() {
		return Messages.getString("new.decision.table.wizard.desc");
	}

	protected String getWizardTitle() {
		return Messages.getString("new.decision.table.wizard.title");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tibco.cep.studio.ui.wizards.AbstractNewEntityWizard#addPages()
	 */
	public void addPages() {
		try {
			if (_selection != null && !_selection.isEmpty()) {
				project = StudioResourceUtils.getProjectForWizard(_selection);
				if (_selection.getFirstElement() instanceof IFile) {
					IFile file = (IFile) _selection.getFirstElement();
					virtualRFPath = IndexUtils.getFullPath(file);
				}
			}
			String currentProjectName = (project != null) ? project.getName() : "";
			page = new NewDecisionTableWizardPage<NewDecisionTableWizard>(
					getWizardTitle(), _selection, getEntityType(),
					virtualRFPath, currentProjectName, false);
			page.setDescription(getWizardDescription());
			page.setTitle(getWizardTitle());
			addPage(page);

		} catch (Exception e) {
			DecisionTableUIPlugin.log(e);
		}
	}

	private IEditorPart openEditor(IWorkbenchPage workbenchPage,
			String fileName, String tableName, String folderPath) {
		// Create the appropriate Editor Input
		IProject project = StudioResourceUtils.getCurrentProject(_selection);
		if (project == null) {
			if (page.getContainerFullPath() != null) {
				IResource resource = StudioResourceUtils.getResourcePathFromContainerPath(page.getContainerFullPath());
				if (resource.exists()) {
					if (resource instanceof IProject) {
						project = (IProject) resource;
					} else {
						project = (IProject) resource.getProject();
					}
				}
			}
		}
		String projectName = project.getName();
		final LegacyDecisionTableEditorHelper helper = new LegacyDecisionTableEditorHelper(
				projectName, tableName, (RuleFunction) page.getTemplate(), folderPath);

		final String implPath = "/"
				+ folderPath
				+ "/"
				+ tableName
				+ StudioResourceUtils
						.getExtensionFor(StudioWorkbenchConstants._WIZARD_TYPE_NAME_DT);

		final IProject proj = project;
		// create the new entity creation operation
		WorkspaceModifyOperation op = new WorkspaceModifyOperation() {
			protected void execute(IProgressMonitor monitor) throws CoreException {
				try{
					final String baseURI = StudioResourceUtils.getCurrentProjectBaseURI(proj);
					IFile file = proj.getFile(implPath);
					DefaultDecisionTableEditorInput editorInput = helper.buildEditorInput(file);
					Table table = helper.buildModelDesiderata();
					editorInput.setTableEModel(table);
					StudioResourceUtils.persistEntity(table, table.getName(), table.getFolder(), IndexUtils.RULE_FN_IMPL_EXTENSION, baseURI, proj, monitor, ModelUtils.getPersistenceOptions());
					file.getParent().refreshLocal(IResource.DEPTH_ONE, monitor);
				} catch(final Exception e){
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
		} catch (InvocationTargetException e) {
			isEntityCreationError = true;
			page.setErrorMessage(Messages.getString("new.resource.creation.error", page.getContainerFullPath().toPortableString(),getEntityType()));
		}

		if(isEntityCreationError){
			return null;
		}
		
		try {
			if (PlatformUI.getWorkbench().getEditorRegistry().getDefaultEditor(implPath) != null) {
				return workbenchPage.openEditor(getEditorInput(),
						PlatformUI.getWorkbench().getEditorRegistry().getDefaultEditor(implPath).getId());
			}
			else {
				StudioUIPlugin.logErrorMessage("No default editor for: " + implPath);
			}
		}
		catch (PartInitException exception) {

		}
		return null;
	}

	@Override
	public boolean performFinish() {

		final String dTname = page.getResourceContainer().getResourceName();
		try {
			IPath containerPath = page.getContainerFullPath();
			if (containerPath != null) {
				IResource resource = StudioResourceUtils.getResourcePathFromContainerPath(containerPath);
				if (resource instanceof IFolder) {
					IFolder folder = (IFolder) resource;
					folderPath = folder.getFullPath().removeFirstSegments(1)
							.toString();
					projectPath = folder.getLocation().toString();
				} else if (resource instanceof IProject) {
					IProject resourceProject = (IProject) resource;
					folderPath = resourceProject.getFullPath()
							.removeFirstSegments(1).toString();
					projectPath = resourceProject.getLocation().toString();
				} else if (resource instanceof IFile) {
					IResource resourceParent = (IResource) resource.getParent();
					folderPath = resourceParent.getFullPath()
							.removeFirstSegments(1).toString();
					projectPath = resourceParent.getLocation().toString();
				}
			}

			// Full Path of the file
			String fname = new StringBuilder(projectPath).append("/").append(
					dTname).append(StudioWorkbenchConstants._DT_EXTENSION)
					.toString();

			final IWorkbenchWindow workbenchWindow = PlatformUI.getWorkbench()
					.getActiveWorkbenchWindow();
			final IWorkbenchPage workbenchPage = workbenchWindow
					.getActivePage();
			final IWorkbenchPart activePart = workbenchPage.getActivePart();
			if (activePart instanceof ISetSelectionTarget) {
				getShell().getDisplay().asyncExec(new Runnable() {
					public void run() {
						((ISetSelectionTarget) activePart)
								.selectReveal(_selection);
					}
				});
			}

			IEditorPart part = openEditor(workbenchPage, fname, dTname,
					folderPath);

			if (_selection != null) {
				IProject project = StudioResourceUtils
						.getCurrentProject(_selection);
				if (_selection.isEmpty()) {
					if (page.getContainerFullPath() != null) {
						IResource resource = StudioResourceUtils.getResourcePathFromContainerPath(page
								.getContainerFullPath());
						if (resource.exists()) {
							if (resource instanceof IProject) {
								project = (IProject) resource;
							} else {
								project = (IProject) resource.getProject();
							}
						}
					}
					project.refreshLocal(IProject.DEPTH_INFINITE, null);
				} else if (!(_selection.isEmpty())) {
					if (_selection.getFirstElement() instanceof IProject) {
						((IProject) _selection.getFirstElement()).refreshLocal(
								IProject.DEPTH_INFINITE, null);
					} else {
						project.refreshLocal(IProject.DEPTH_INFINITE, null);
					}
				}
				refreshView(workbenchPage, vrfFile);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public void init(IWorkbench workbench, IStructuredSelection selection) {
		try {
			this._selection = selection;
			if ((selection != null) && (selection.size() == 1)) {
				// If Import Decision Table from Excel on VRF file selection
				if (selection.size() == 1
						&& selection.getFirstElement() instanceof IFile) {
					IFile file = (IFile) selection.getFirstElement();
					vrfFile = file;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public IFile getVRFFile() {
		return vrfFile;
	}
}
