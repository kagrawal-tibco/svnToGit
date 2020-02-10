package com.tibco.cep.studio.ui.wizards;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.IImportWizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.actions.WorkspaceModifyOperation;

import com.tibco.cep.studio.common.configuration.XPATH_VERSION;
import com.tibco.cep.studio.core.StudioCorePlugin;
import com.tibco.cep.studio.core.converter.IImportProjectListener;
import com.tibco.cep.studio.ui.actions.CreateStudioProjectOperation;
import com.tibco.cep.studio.ui.util.Messages;
import com.tibco.cep.studio.ui.util.StudioResourceUtils;

public class ImportDesignerProjectWizard extends Wizard implements IImportWizard {
	
	private ImportDesignerProjectWizardPage mainPage;
	private IImportProjectListener[] fImportProjectListeners;
	private XPATH_VERSION xpathVersion;

	public ImportDesignerProjectWizard() {
		super();
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.wizard.Wizard#performFinish()
	 */
	public boolean performFinish() {
		
		if(!mainPage.getLocationPath().toFile().exists()){
			resultError();
			return false;
		}
		
		IProject newProj = mainPage.getProjectHandle();
        if (newProj == null)
            return false;
        if (!newProj.exists()) {
        	createImportedDesignerProject(newProj);
        }
        return true;
	}
	 
	private void createImportedDesignerProject(IProject newProj) {

		File fileToImport = mainPage.getExistingDesignerProjectArchive();
		File targetLocation = new File(mainPage.getLocationPath().toOSString() + Path.SEPARATOR + mainPage.getProjectName()); 
		WorkspaceModifyOperation op = new CreateStudioProjectOperation(newProj, fileToImport, targetLocation, null, getXpathVersion(), mainPage.useDefaults());
		try {
			getContainer().run(true, true, op);
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		// fire project imported notification
		fireProjectImportedNotification(newProj, new NullProgressMonitor());
		
		//Switching to Designer perspective if current one is any other perspective
		StudioResourceUtils.switchStudioPerspective();
		//System.out.println("done");
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.IWorkbenchWizard#init(org.eclipse.ui.IWorkbench, org.eclipse.jface.viewers.IStructuredSelection)
	 */
	public void init(IWorkbench workbench, IStructuredSelection selection) {
		setWindowTitle(Messages.getString("DesignerProjectImportWizard.Title")); //NON-NLS-1 //$NON-NLS-1$
		setNeedsProgressMonitor(true);
		mainPage = new ImportDesignerProjectWizardPage(Messages.getString("DesignerProjectImportWizard.ImportDesc")); //NON-NLS-1 //$NON-NLS-1$
	}
	
	/* (non-Javadoc)
     * @see org.eclipse.jface.wizard.IWizard#addPages()
     */
    public void addPages() {
        super.addPages(); 
        addPage(mainPage);        
    }

	private void fireProjectImportedNotification(IProject project, IProgressMonitor monitor) {
		monitor.subTask("Notifying project import listeners");
		IImportProjectListener[] listeners = getImportProjectListeners();
		for (IImportProjectListener importProjectListener : listeners) {
			importProjectListener.projectImported(project, monitor);
		}
	}

	private IImportProjectListener[] getImportProjectListeners() {
		if (fImportProjectListeners == null) {
			List<IImportProjectListener> listeners = new ArrayList<IImportProjectListener>();
			IConfigurationElement[] configurationElementsFor = Platform.getExtensionRegistry().getConfigurationElementsFor(StudioCorePlugin.PLUGIN_ID, "importProjectNotification");
			for (IConfigurationElement configurationElement : configurationElementsFor) {
				String attribute = configurationElement.getAttribute("importListener");
				if (attribute != null) {
					try {
						Object executableExtension = configurationElement.createExecutableExtension("importListener");
						if (executableExtension instanceof IImportProjectListener) {
							listeners.add((IImportProjectListener) executableExtension);
						}
					} catch (CoreException e) {
						e.printStackTrace();
					}
				}
			}
			fImportProjectListeners = new IImportProjectListener[listeners.size()];
			return listeners.toArray(fImportProjectListeners);
		}
		return fImportProjectListeners;
	}

	protected void resultError() {
		mainPage.setErrorMessage(Messages.getString("new_project_creation_fail"));
	}
	
	public XPATH_VERSION getXpathVersion() {
		return xpathVersion;
	}

	public void setXpathVersion(XPATH_VERSION xpathVersion) {
		this.xpathVersion = xpathVersion;
	}
	
}
