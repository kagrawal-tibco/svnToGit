package com.tibco.cep.bpmn.ui.wizards;
	
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubProgressMonitor;
import org.eclipse.emf.common.util.EList;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.actions.WorkspaceModifyOperation;

import com.tibco.cep.bpmn.core.BpmnCorePlugin;
import com.tibco.cep.bpmn.ui.BpmnUIPlugin;
import com.tibco.cep.bpmn.ui.Messages;
import com.tibco.cep.repo.GlobalVariables;
import com.tibco.cep.studio.common.configuration.BpmnProcessPathEntry;
import com.tibco.cep.studio.common.configuration.BpmnProcessSettings;
import com.tibco.cep.studio.common.configuration.StudioProjectConfiguration;
import com.tibco.cep.studio.core.configuration.manager.StudioProjectConfigurationManager;
import com.tibco.cep.studio.core.repo.emf.StudioEMFProject;
import com.tibco.cep.studio.core.validation.IResourceValidator;
import com.tibco.cep.studio.ui.StudioUIPlugin;
import com.tibco.cep.studio.ui.actions.ValidateProjectAction;
import com.tibco.cep.studio.ui.actions.ValidateProjectAction.ResourceVisitor;

public class GenerateProcessWizard extends Wizard {

	private GenerateProcessWizardPage fProjectPage;
	private IWorkbenchWindow fWindow;
	private IProject fProject;

	/**
	 * @param window
	 * @param title
	 * @param selection 
	 */
	public GenerateProcessWizard(IWorkbenchWindow window, String title, IProject project) {
		setWindowTitle(title);
		setDefaultPageImageDescriptor(BpmnUIPlugin.getDefault().getImageDescriptor("icons/processgenwiz.png"));
		this.fWindow = window;
		this.fProject = project;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.wizard.Wizard#addPages()
	 */
	@Override
	public void addPages() {
		String projectName = this.fProject.getName();
		if(projectName != null) {
			this.fProjectPage = new GenerateProcessWizardPage(projectName);
			addPage(this.fProjectPage);
		}
	}
	

	


	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.wizard.Wizard#performCancel()
	 */
	@Override
	public boolean performCancel() {
		return true;
	}


	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.wizard.Wizard#performFinish()
	 */
	@Override
	public boolean performFinish() {
		try {
			String projectName = this.fProject.getName();
			StudioProjectConfiguration pc = StudioProjectConfigurationManager
					.getInstance().getProjectConfiguration(projectName);

			StudioProjectConfigurationManager.getInstance().saveConfiguration(
					projectName, pc);
			
			if(fProject != null) {
				BpmnProcessSettings bpc = BpmnCorePlugin.getBpmnProjectConfiguration(projectName);
				StudioEMFProject sproject = new StudioEMFProject(projectName);
				sproject.load();
				GlobalVariables gvars = sproject.getGlobalVariables();
				String fpath = new StringBuffer(gvars.substituteVariables(bpc.getBuildFolder())).toString();
				IPath path = new Path(fpath);
				IFolder folder = this.fProject.getFolder(path);
				IPath[] selectedProcesses = getSelectedProcesses(bpc);
				boolean overwrite = false;
				if (folder != null && folder.exists()) {
					overwrite = MessageDialog.openQuestion(fWindow
							.getShell(), 
							Messages.getString("bpmn.build.wizard.title"), 
							Messages.getString("bpmn.build.folder.exists"));
				} else {
					overwrite = true;
				}
				GenerateProcessesOperation op = new GenerateProcessesOperation(fProject,overwrite,selectedProcesses);
				getContainer().run(true, true, op);
				
			}
		} catch (Exception e1) {
			BpmnUIPlugin.log(e1);
			StudioUIPlugin.errorDialog(this.fWindow.getShell(), Messages
					.getString("bpmn.build.wizard.title"), Messages
					.getString("bpmn.build.codegen.failed"), e1);
		}
		return true;
	}

	/**
	 * @return
	 */
	public GenerateProcessWizardPage getProjectPage() {
		return this.fProjectPage;
	}
	
	
	private IPath[] getSelectedProcesses(BpmnProcessSettings pc) {
		EList<BpmnProcessPathEntry> paths = pc.getSelectedProcessPaths();
		List<IPath> spaths = new LinkedList<IPath>();
		for(BpmnProcessPathEntry path : paths) {
			IPath p = new Path(path.getPath());
			spaths.add(p);
		}
		
		return spaths.toArray(new IPath[spaths.size()]);
	}
	
	class GenerateProcessesOperation extends WorkspaceModifyOperation {
		private boolean overwrite;
		private IProject project;
		private List<IPath> processes;

		public GenerateProcessesOperation(IProject project, boolean overwrite, IPath[] selectedProcesses) {
			this.project = project;
			this.overwrite = overwrite;
			this.processes = Arrays.asList(selectedProcesses);
		}

		
		@Override
		protected void execute(IProgressMonitor monitor) throws CoreException,
				InvocationTargetException, InterruptedException {
			IStatus status = runGeneration(monitor,overwrite, project,processes);
			if(!status.isOK()) {
				BpmnCorePlugin.log(status);
			}
			
		}

	};

	private IStatus runGeneration(IProgressMonitor monitor, boolean overwrite, IProject project, List<IPath> processes) {
		try {
			monitor.beginTask(Messages.getString("bpmn.build.codegen.task"),
					IProgressMonitor.UNKNOWN);
			
			// validate Project
//			if(true) { //TODO:Manish -> remove this line when validation is implemented
			if (isValid(project, new SubProgressMonitor(monitor, 10))) {
				// Build EAR
				
				if (overwrite) {
					
//					final BpmnCodeGenerator cg = new BpmnCodeGenerator(monitor,project,processes,overwrite);
//					// codegen life cycle
//					cg.init();
//					cg.generate();
					monitor.worked(70);
					Display.getDefault().asyncExec(new Runnable() {
					
						@Override
						public void run() {
							// TODO Auto-generated method stub
							MessageDialog.openInformation(StudioUIPlugin.getShell(), 
									Messages.getString("bpmn.build.codegen.task"), //$NON-NLS-1$
									Messages.getString("bpmn.build.codegen.success"));//NON-NLS-1$
					
						}
					});


					return new Status(Status.OK, StudioUIPlugin
							.getUniqueIdentifier(), Messages
							.getString("bpmn.build.codegen.success"));//$NON-NLS-1$
				} else {
					monitor.worked(100);
					return Status.OK_STATUS;
				}
			} 
			else {
				monitor.worked(100);
				return new Status(Status.ERROR, StudioUIPlugin
						.getUniqueIdentifier(), Messages.getString(
						"bpmn.build.validation.task.failed", fProject.getName(), //$NON-NLS-1$
						getValidationCount(this.fProject, true), getValidationCount(
								this.fProject, false)));
			}

		} catch (Exception e1) {
			monitor.worked(100);
			return new Status(Status.ERROR, StudioUIPlugin
					.getUniqueIdentifier(),  Messages
					.getString("bpmn.build.codegen.failed"),e1); //$NON-NLS-1$
		} finally {
			monitor.done();
		}
		
	}
	
	/**
	 * @param project
	 * @param error
	 * @return
	 */
	public static int getValidationCount(IProject project,boolean error) {
		int errorCount = 0;
		int warningCount = 0;
		try {
			IMarker[] markers = project.findMarkers(IResourceValidator.VALIDATION_MARKER_TYPE, true, IResource.DEPTH_INFINITE);
			if (markers.length > 0) {
				for (int i=0; i<markers.length; i++) {
					if ((Integer) markers[i].getAttribute(IMarker.SEVERITY) == IMarker.SEVERITY_ERROR) {
						errorCount++;
					} else {
						warningCount++;
					}
				}
			}
			if(error) {
				return errorCount;
			} else {
				return warningCount;
			}
		} catch (CoreException e) {
			StudioUIPlugin.log(e);
		}
		return 0;
	}
	
	/**
	 * @param project
	 * @param monitor
	 * @return
	 */
	public static boolean isValid(final IProject project,IProgressMonitor monitor) {
		monitor.beginTask(Messages.getString("bpmn.build.validation.task",project.getName()), 1); //NON-NLS-1$
		if (monitor.isCanceled()) {
			return false;
		}
		SubProgressMonitor subMon = new SubProgressMonitor(monitor, 1);
		ValidateProjectAction.ResourceVisitor visitor = new ResourceVisitor(subMon);
		try {
			// clear all existing validation errors
			project.deleteMarkers(IResourceValidator.VALIDATION_MARKER_TYPE, true, IResource.DEPTH_INFINITE);
			visitor.setCountOnly(true);
			project.accept(visitor);
			subMon.beginTask(Messages.getString("bpmn.build.validation.task",project.getName()),1); //$NON-NLS-1$
			visitor.setCountOnly(false);
			project.accept(visitor);
		} catch (CoreException e) {
			if (e.getStatus().getSeverity() == Status.CANCEL) {
				return false;
			} else {
				StudioUIPlugin.log(e);
			}
		}
		return getValidationCount(project, true) < 1;
	}
	
	/**
	 * @param project
	 */
	public static void displayValidationMessage(final IProject project) {
		// report any errors found
		final boolean showWarning = getValidationCount(project, true) > 0;
		Display.getDefault().asyncExec(new Runnable() {
			public void run() {
				if (showWarning) {
					MessageDialog.openWarning(new Shell(), 
							Messages.getString("bpmn.build.validation.title"), //$NON-NLS-1$
							Messages.getString("bpmn.build.validation.task.failed",//$NON-NLS-!$
									project.getName(),
									getValidationCount(project, true),
									getValidationCount(project, false)));
				} else {
					MessageDialog.openWarning(new Shell(), 
							Messages.getString("bpmn.build.validation.title"),  //$NON-NLS-1$
							Messages.getString("bpmn.build.validation.task.success"));//$NON-NLS-1$
				}
			}
			
		});
	}
	
	

}