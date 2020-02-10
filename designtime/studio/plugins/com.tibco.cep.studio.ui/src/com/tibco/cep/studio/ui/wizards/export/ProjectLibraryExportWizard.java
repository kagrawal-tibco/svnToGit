package com.tibco.cep.studio.ui.wizards.export;

import static com.tibco.cep.studio.ui.util.StudioUIUtils.isValidProject;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IPath;
import org.eclipse.emf.common.util.EList;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.wizard.IWizardContainer;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.ui.IExportWizard;
import org.eclipse.ui.IWorkbench;

import com.tibco.cep.studio.common.configuration.ProjectLibraryEntry;
import com.tibco.cep.studio.common.configuration.StudioProjectConfiguration;
import com.tibco.cep.studio.common.util.ModelNameUtil;
import com.tibco.cep.studio.core.configuration.manager.StudioProjectConfigurationManager;
import com.tibco.cep.studio.core.util.DependencyUtils;
import com.tibco.cep.studio.ui.StudioUIPlugin;
import com.tibco.cep.studio.ui.util.Messages;

public class ProjectLibraryExportWizard extends Wizard implements IExportWizard {

	private IStructuredSelection fSelection = null;
	private final String PROJ_LIB_EXTENSION = "projlib";
	private ProjectLibraryExportWizardPage fResourcePage;
	private ProjectLibraryExportDependencyPage fDependencyPage;
	private boolean success = false;
	private boolean error = false;
	private String EXPORT_PROJECT_LIBRARY_DIALOG_SETTINGS = "ExportProjectLibrary"; //$NON-NLS-1$

	@Override
	public void addPages() {
		fResourcePage = new ProjectLibraryExportWizardPage("Export stuff", fSelection);
		addPage(fResourcePage);
		fDependencyPage = new ProjectLibraryExportDependencyPage("Dependencies");
		addPage(fDependencyPage);
	}

	public ProjectLibraryExportWizard() {
		setWindowTitle(Messages.getString("project.library.export.window.title"));
		setHelpAvailable(false);
		StudioUIPlugin plugin = StudioUIPlugin.getDefault();
		IDialogSettings workbenchSettings = plugin.getDialogSettings();
		IDialogSettings settings = workbenchSettings.getSection(EXPORT_PROJECT_LIBRARY_DIALOG_SETTINGS);
		if (settings == null) {
			settings = workbenchSettings.addNewSection(EXPORT_PROJECT_LIBRARY_DIALOG_SETTINGS);
		}
		setDialogSettings(settings);
	}

	@Override
	public IWizardPage getNextPage(IWizardPage page) {
		IWizardPage wizPage = super.getNextPage(page);
		if (wizPage instanceof ProjectLibraryExportDependencyPage) {
			((ProjectLibraryExportDependencyPage) wizPage).setDependentResources(fResourcePage.getSelectedResources(), fResourcePage.getDependentResources());
			int size = ((ProjectLibraryExportDependencyPage) wizPage).getFilteredDependentResourcesSize();
			if (size == 0) {
				return null;
			}
		}
		return wizPage;
	}

	@Override
	public boolean performFinish() {
		
		String targetFilePath = fResourcePage.getTargetPath();	
		String name = fResourcePage.getProjLibName();

		// remove the extension if present
		if (name.indexOf('.') != -1 && name.endsWith("." + PROJ_LIB_EXTENSION))	{
			name = name.replace("." + PROJ_LIB_EXTENSION, "");
		}
		File targetFile = new File(targetFilePath + "/" + name + "." + PROJ_LIB_EXTENSION);
		boolean create = true;
		if (targetFile.exists()) {
			create = MessageDialog.openQuestion(getShell(), "Replace file", Messages.getString("project.library.export.duplicate.name", targetFile.getAbsolutePath()));
		}
		if(create){
			ProjectLibraryExportWizardPage exportPage = fResourcePage;

			List<IResource> selectedResources = exportPage.getSelectedResources();
			List<IResource> dependentResources = fResourcePage.getDependentResources();
			for (IResource resource : dependentResources) {
				if (!selectedResources.contains(resource)) {
					selectedResources.add(resource);
				}
			}
			if(selectedResources.size() > 0){
				Map<IPath, IResource> resourcePaths = new HashMap<IPath, IResource>();
				
				// filter duplicate resources from proj libs
				for (IResource resource : selectedResources) {
					if (resource.isLinked(IResource.CHECK_ANCESTORS)) {
						// resource if from a project library, need to check whether it has already been added from the local project
						IPath plPath = resource.getFullPath().removeFirstSegments(2);
						if (resourcePaths.containsKey(plPath)) {
							// already added locally
							continue;
						}
						resourcePaths.put(plPath, resource);
					} else {
						IResource res = resourcePaths.get(resource.getFullPath().removeFirstSegments(1));
						if (res != null && res.isLinked(IResource.CHECK_ANCESTORS)) {
							// already added from project library, replace that with local resource
						}
						resourcePaths.put(resource.getFullPath().removeFirstSegments(1), resource);
					}
				}
				selectedResources.clear();
				selectedResources.addAll(resourcePaths.values());
				IProject project = null;
				if(selectedResources.get(0) instanceof IProject){
					project = (IProject)selectedResources.get(0);
				}else{
					project = selectedResources.get(0).getProject();
				}
				boolean exportFailed = isValidProject(project, getShell(), Messages.getString("project.library.export.fail.desc"));
				if(!exportFailed){ 
					return false;
				}
				StudioProjectConfiguration projectConfig = StudioProjectConfigurationManager
						.getInstance().getProjectConfiguration(project.getName());
				EList<ProjectLibraryEntry> projectLibEntries = projectConfig.getProjectLibEntries();

				int projLibCheckedCount=0;

				for(ProjectLibraryEntry entry:projectLibEntries)
				{	
					String projLibPath=entry.getPath();
					String projLibName=projLibPath.substring((projLibPath.lastIndexOf(File.separator)+1));

					for(IResource resource :selectedResources )
					{
						if((resource.getFullPath().toOSString()).indexOf(projLibName)!=-1)
						{
							projLibCheckedCount++;
							break;
						}
					}
				}

				if(projectLibEntries!=null && projLibCheckedCount!=projectLibEntries.size())
				{
					MessageDialog.openInformation(getShell(), Messages.getString("project.library.export.window.title") , Messages.getString("project.library.export.projlib.unchecked"));
				}
			}

			try{

				DependencyUtils.buildProjectLibrary(targetFile, selectedResources);

			} catch(Exception e) {
				StudioUIPlugin.log(e);
				error = true;
				return false;
			}
			success = true;
			return true;
		}
		return false;
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.jface.wizard.Wizard#dispose()
	 */
	public void dispose() {
		if (success) {
			MessageDialog.openConfirm(getShell(), Messages.getString("project.library.export.window.title"), Messages.getString("project.library.export.success.desc"));
		} 
		if (error) {
			MessageDialog.openError(getShell(), Messages.getString("project.library.export.window.title") , Messages.getString("project.library.export.fail.desc"));
		}
	}

	@Override
	public void init(IWorkbench workbench, IStructuredSelection selection) {
		if(selection.getFirstElement() instanceof IJavaProject) {
			this.fSelection = new StructuredSelection(((IJavaProject)selection.getFirstElement()).getProject());
		} else {
			this.fSelection  = selection;
		}
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
	
	private boolean validateName(){
		File outputLoc = new File(fResourcePage.getOutputLocationText());
		boolean extValid = fResourcePage.getProjLibName().indexOf('.') == -1 || fResourcePage.getProjLibName().endsWith("." + PROJ_LIB_EXTENSION);
		if (!extValid)	{
			fResourcePage.setErrorMessage(Messages.getString("project.library.export.name.extension.error", PROJ_LIB_EXTENSION));
			fResourcePage.setPageComplete(false);
			return false;
		} 
		if (fResourcePage.getProjLibName().equals(""))	{
			fResourcePage.setErrorMessage((Messages.getString("project.library.export.name.error")));
			fResourcePage.setPageComplete(false);
			return false;
		}
		if (fResourcePage.getProjLibName().length() > 0) {
			if(!ModelNameUtil.isValidSharedResourceIdentifier(fResourcePage.getProjLibName())){
				String problemMessage = Messages.getString("invalidFilename", fResourcePage.getProjLibName());
				fResourcePage.setPageComplete(false);
				fResourcePage.setErrorMessage(problemMessage);
				return false;
			} else if (!fResourcePage.getOutputLocationText().equals("") && 
//					!projLibNameText.getText().equals("") && 
					fResourcePage.getSelectedResources().size() > 0 && 
					//isPageComplete &&
					extValid &&outputLoc.isDirectory()){
					fResourcePage.setErrorMessage(null);
					File targetFile = new File(fResourcePage.getTargetPath() + "/" +  fResourcePage.getProjLibName() + "." + PROJ_LIB_EXTENSION);
//					boolean depsize = fResourcePage.getDependentResources().size() > 0 ;
//					if (targetFile.exists()) {
						
//						String str =  depsize ? "\n" + " "
//								+ Messages.getString("project.library.export.complete.description") : "";
//						fResourcePage.setMessage(Messages.getString("project.library.export.duplicate.name") + str , DialogPage.WARNING);
//					} else {
//						String str = depsize ? Messages.getString("project.library.export.complete.description") : "";
//						fResourcePage.setMessage( str , Status.OK);
//					}
//					fResourcePage.setPageComplete(true);
					return true;
				}
		}
		return false;

	}	
}