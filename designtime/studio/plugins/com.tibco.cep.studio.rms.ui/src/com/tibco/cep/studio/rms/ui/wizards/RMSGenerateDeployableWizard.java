package com.tibco.cep.studio.rms.ui.wizards;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubProgressMonitor;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.IWorkbenchWindow;

import com.tibco.cep.studio.rms.client.ArtifactsManagerClient;
import com.tibco.cep.studio.rms.model.Error;
import com.tibco.cep.studio.rms.ui.RMSUIPlugin;
import com.tibco.cep.studio.rms.ui.actions.GenerateDeployableAction;
import com.tibco.cep.studio.ui.StudioUIPlugin;
/**
 * @author hitesh
 *
 */
public class RMSGenerateDeployableWizard extends Wizard  {

	private IWorkbenchWindow window;
	
	private String[] projectList;
	
	private String title;
	
	private RMSGenerateDeployableWizardPage rmsPage;

	private String projectName;
	private double version;
	private boolean genDebugInfo;
	private boolean includeServiceVars;
	private boolean generateClassesOnly;
	private String url;
	
	private GenerateDeployableAction genDepAction;
	
	/**
	 * @param window
	 * @param title
	 * @param selection 
	 */
	public RMSGenerateDeployableWizard(IWorkbenchWindow window, 
			                           GenerateDeployableAction genDepAction, 
			                           String title, 
			                           String[] projectList) {
		setWindowTitle(title);
		setDefaultPageImageDescriptor(StudioUIPlugin.getImageDescriptor("icons/buildEARWizard.png"));
		this.window = window;
		this.title = title;
		this.projectList = projectList;
		this.genDepAction =  genDepAction;
	}
	
	@Override
	public boolean performFinish() {
		try {
			projectName = rmsPage.getSelectedProject();
			version = rmsPage.getVersion();
			genDebugInfo = rmsPage.isGenDebugInfo();
			includeServiceVars = rmsPage.isIncludeServiceVars();
			generateClassesOnly = rmsPage.isGenerateClassesOnly();
			url = rmsPage.getURL();
			
			getContainer().run(true, true, new IRunnableWithProgress() {
				public void run(final IProgressMonitor monitor) throws InvocationTargetException, InterruptedException {
					runGenerateDeployable(monitor);
				}
			});
		} catch (InvocationTargetException e) {
			RMSUIPlugin.debug(e.getMessage());
		} catch (InterruptedException e) {
			RMSUIPlugin.debug(e.getMessage());
		}

		return true;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.wizard.Wizard#addPages()
	 */
	@Override
	public void addPages() {
		rmsPage = new RMSGenerateDeployableWizardPage(this.title, this.projectList);
		addPage(rmsPage);
	}
	
	/**
	 * @param monitor
	 * @return
	 */
	private IStatus runGenerateDeployable(IProgressMonitor monitor) {

		try {
			monitor.beginTask("Fetching RMS user deployable details..", IProgressMonitor.UNKNOWN);

			monitor.worked(10);
			if (monitor.isCanceled()) {
				throw new CoreException(Status.CANCEL_STATUS);
			}
			Object response = ArtifactsManagerClient.generateDeployable(projectName, version, genDebugInfo, includeServiceVars, 
					generateClassesOnly, url, new SubProgressMonitor(monitor, 90));
			monitor.worked(90);
			if (monitor.isCanceled()) {
				throw new CoreException(Status.CANCEL_STATUS);
			}
			if (response == null) {
				monitor.done();
				genDepAction.setSuccess(false);
				return new Status(Status.ERROR, StudioUIPlugin.getUniqueIdentifier(), "Deployable generated failed.");
			}
			if (response instanceof Error) {
				Error error = (Error)response;
				monitor.setTaskName(error.getErrorString());
				monitor.done();
				genDepAction.setSuccess(false);
				genDepAction.setError(error.getErrorString());
				return new Status(Status.ERROR, StudioUIPlugin.getUniqueIdentifier(), error.getErrorString());
			} else {
				monitor.setTaskName("Deployable generated successfully");
				monitor.done();
				genDepAction.setSuccess(true);
				return new Status(Status.OK, StudioUIPlugin.getUniqueIdentifier(),"Deployable generated successfully.");
			}
		} catch (Exception e) {
			RMSUIPlugin.log(e);
			if (e instanceof CoreException
					&& ((CoreException) e).getStatus().getSeverity() == Status.CANCEL) {
				return new Status(Status.ERROR, StudioUIPlugin.getUniqueIdentifier(),"Generate Deployable cancelled.");
			}
		}
		finally {
			monitor.done();
		}
		return null;
	}

	@Override
	public boolean performCancel() {
		genDepAction.setCancelled(true);
		return super.performCancel();
	}

	public IWorkbenchWindow getWindow() {
		return window;
	}
}