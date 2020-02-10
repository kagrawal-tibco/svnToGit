package com.tibco.cep.studio.ui.actions;

import static com.tibco.cep.studio.ui.util.StudioUIUtils.saveAllEditors;

import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubProgressMonitor;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.jdt.core.IJavaModelMarker;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
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

import com.tibco.cep.repo.GlobalVariables;
import com.tibco.cep.studio.common.configuration.EnterpriseArchiveEntry;
import com.tibco.cep.studio.common.configuration.StudioProjectConfiguration;
import com.tibco.cep.studio.core.StudioCore;
import com.tibco.cep.studio.core.StudioCorePlugin;
import com.tibco.cep.studio.core.configuration.manager.StudioProjectConfigurationManager;
import com.tibco.cep.studio.core.nature.StudioProjectNature;
import com.tibco.cep.studio.core.preferences.StudioCorePreferenceConstants;
import com.tibco.cep.studio.core.repo.emf.StudioEMFProject;
import com.tibco.cep.studio.core.util.CommonUtil;
import com.tibco.cep.studio.core.util.packaging.impl.DefaultRuntimeClassesPackager;
import com.tibco.cep.studio.core.util.packaging.impl.EMFEarPackager;
import com.tibco.cep.studio.core.validation.ClasspathValidator;
import com.tibco.cep.studio.core.validation.IResourceValidator;
import com.tibco.cep.studio.ui.StudioUIPlugin;
import com.tibco.cep.studio.ui.actions.ValidateProjectAction.ResourceVisitor;
import com.tibco.cep.studio.ui.dialog.OverwriteMessageDialog;
import com.tibco.cep.studio.ui.preferences.StudioUIPreferenceConstants;
import com.tibco.cep.studio.ui.util.Messages;
import com.tibco.cep.studio.ui.util.StudioResourceUtils;
import com.tibco.cep.studio.ui.widgets.StudioWizardDialog;
import com.tibco.cep.studio.ui.wizards.EnterpriseArchiveBuildWizard;
import com.tomsawyer.graph.TSGraphObject;
import com.tibco.cep.studio.core.index.utils.IndexUtils;

/**
 * 
 * @author sasahoo
 * 
 */
public class BuildEnterpriseArchiveAction implements
		IWorkbenchWindowActionDelegate, IObjectActionDelegate, IPartListener2 {
	private IWorkbenchPart fPart = null;
	private IWorkbenchWindow fWindow;
	private IProject fProject;
	private Shell fShell;
	private ISelection fSelection;
	private EnterpriseArchiveEntry eaconfig;
	public final String CDD_EXTENSION = "cdd";
	public final String SITE_TOPOLOGY_EXTENSION = "st";
	private List<String> fexcludeResources = new ArrayList<String>();
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

				EnterpriseArchiveBuildWizard wizard = new EnterpriseArchiveBuildWizard(
						this.fWindow, Messages.getString("Build.EAR.Building.task"),
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
					IProject project = this.fProject;
					String projectName = project.getName();
					StudioProjectConfiguration pc = StudioProjectConfigurationManager
							.getInstance().getProjectConfiguration(projectName);
					eaconfig = pc.getEnterpriseArchiveConfiguration();
					StudioEMFProject sproject = new StudioEMFProject(projectName);
					sproject.load();
					GlobalVariables gvars = sproject.getGlobalVariables();
					String fpath = new StringBuffer(gvars
							.substituteVariables(eaconfig.getPath()))
							.toString();
					File file = new File(fpath);
					if (CommonUtil.traverseReadOnlyDirectory(file
							.getParentFile(), fpath)) {
						MessageDialog.openError(fWindow.getShell(), Messages
								.getString("Build.EAR.title"), Messages
								.getString("Build.EAR.file.parent.readOnly"));
						return;
					}
					boolean overwrite = preBuildFileOps(file);
					IndexUtils.waitForUpdateAll();
					if (!overwrite) {
						run(action);// invoking the Build EAR action
						return;
					}
					BuildArchiveJob job = 
						new BuildArchiveJob(project.getName(), overwrite/*, shouldBuildClassesOnly*/);
					job.setPriority(Job.BUILD);
					job.setUser(true);
					job.schedule();
				}
			}
		} catch (Exception e) {
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			e.printStackTrace(pw);
			if(sw.getBuffer().length() > 0){
				StudioUIPlugin.log(sw.getBuffer().toString());
			}
			
			MessageDialog.openError(fWindow.getShell(), Messages.getString("Build.EAR.title"), e.getMessage());
			run(action);// invoking the Build EAR action
		}
	}

	class BuildArchiveJob extends Job {
		private boolean overwrite;
		
		private String projectName;

		/**
		 * @param projectName
		 * @param overwrite
		 */
		public BuildArchiveJob(String projectName, boolean overwrite) {
			super("Build Enterprise Archive");
			this.projectName = projectName;
			this.overwrite = overwrite;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @seeorg.eclipse.core.runtime.jobs.Job#run(org.eclipse.core.runtime.
		 * IProgressMonitor)
		 */
		public IStatus run(IProgressMonitor monitor) {
			buildInProgress = true;
			return runBuildArchive(monitor, projectName, overwrite);
		}
	};

	/**
	 * @param monitor
	 * @param projectName
	 * @param overwrite
	 * @param buildClassesOnly
	 * @return
	 */
	private IStatus runBuildArchive(IProgressMonitor monitor,
			                        String projectName, 
			                        boolean overwrite) {
		try {
			monitor.beginTask(Messages.getString("Build.EAR.Building.task"),
					IProgressMonitor.UNKNOWN);

			fexcludeResources.clear();
			fexcludeResources.add(CDD_EXTENSION);
			fexcludeResources.add(SITE_TOPOLOGY_EXTENSION);

			// validate Project
			if (isValid(fProject, new SubProgressMonitor(monitor, 10))) {
				//Build EAR
				if (overwrite) {
					monitor.setTaskName(Messages
							.getString("Build.EAR.LoadProjectConfig.task"));
					final StudioEMFProject sproject = new StudioEMFProject(fProject.getName());
					sproject.load();
					monitor.worked(20);
			    	boolean useLegacyCompiler = !StudioCore.getDefaultCompiler().equals(DefaultRuntimeClassesPackager.IN_MEMORY_COMPILER);
					EMFEarPackager packager = new EMFEarPackager(sproject,
							new SubProgressMonitor(monitor, 70), false, useLegacyCompiler);
					monitor.setTaskName(Messages
							.getString("Build.EAR.Packaging.task"));
					packager.close();
					monitor.worked(70);
					showAsyncMessage(Messages
							.getString("Build.EAR.Building.task"), Messages
							.getString("Build.EAR.success"));
					// Increment version after ear is built successfully
					StudioProjectConfiguration pc = StudioProjectConfigurationManager
					.getInstance().getProjectConfiguration(
							fProject.getName());
					EnterpriseArchiveEntry eaconfig = pc
					.getEnterpriseArchiveConfiguration();
					eaconfig.setVersion(eaconfig.getVersion() + 1);
					StudioProjectConfigurationManager.getInstance()
					.saveConfiguration(fProject.getName(), pc);

					return new Status(Status.OK, StudioUIPlugin
							.getUniqueIdentifier(), Messages
							.getString("Build.EAR.success"));
				} else {
					monitor.worked(90);
					return Status.OK_STATUS;
				}
			} else {
				monitor.worked(90);
				return new Status(Status.ERROR, StudioUIPlugin
						.getUniqueIdentifier(), Messages.getString(
								"Build.EAR.Validation.task.failed", fProject.getName(),
								getValidationCount(this.fProject, true),
								getValidationCount(this.fProject, false)));
			}

		} catch (Exception e1) {
			monitor.worked(100);

			if (e1 instanceof CoreException
					&& ((CoreException) e1).getStatus().getSeverity() == Status.CANCEL) {
				if (eaconfig != null && eaconfig.getTempOutputPath() != null) {
					File file = new File(eaconfig.getTempOutputPath());
					if (file.exists() && file.isDirectory()) {
						CommonUtil.deleteDirContent(file);
					}
				}
				showAsyncMessage(Messages.getString("Build.EAR.Building.task"),
						Messages.getString("Build.EAR.cancelled"));
				return new Status(Status.OK, StudioUIPlugin
						.getUniqueIdentifier(), Messages
						.getString("Build.EAR.cancelled"));
			} else {

				return new Status(Status.ERROR, StudioUIPlugin
						.getUniqueIdentifier(), Messages
						.getString("Build.EAR.failed"), e1);
			}
		} finally {
			buildInProgress = false;
			monitor.done();
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
			action.setEnabled(fProject != null && fProject.isAccessible() && fProject.hasNature(StudioProjectNature.STUDIO_NATURE_ID));
		} catch (Exception e) {
			StudioUIPlugin.log("Failed to set build enterprise archive enablement.");
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
	public void setActivePart(IAction action, IWorkbenchPart targetPart) {
		fPart = targetPart;

	}

	/**
	 * @param project
	 * @param monitor
	 * @return
	 * @throws CoreException
	 */
	private boolean isValid(final IProject project, IProgressMonitor monitor)
			throws CoreException {
		monitor.beginTask(Messages.getString("Build.EAR.Validation.task",
				project.getName()), 1);
		if (monitor.isCanceled()) {
			throw new CoreException(Status.CANCEL_STATUS);
		}
		SubProgressMonitor subMon = new SubProgressMonitor(monitor, 1);
		ValidateProjectAction.ResourceVisitor visitor = new ResourceVisitor(
				subMon);
		try {
			// clear all existing validation errors
			project.deleteMarkers(IResourceValidator.BUILD_PATH_MARKER_TYPE, 
					true, IResource.DEPTH_ZERO);
			project.deleteMarkers(IResourceValidator.VALIDATION_MARKER_TYPE,
					true, IResource.DEPTH_INFINITE);
			if (isClasspathValid(project)) {
				visitor.setCountOnly(true);
				project.accept(visitor);
				subMon.beginTask(Messages.getString(
						"Build.EAR.Validation.task", project.getName()), 1);
				visitor.setCountOnly(false);
				project.accept(visitor);
			}
		} catch (CoreException e) {
			if (e.getStatus().getSeverity() == Status.CANCEL) {
				throw new CoreException(Status.CANCEL_STATUS);
			} else {
				StudioUIPlugin.log(e);
			}
		}
		return getValidationCount(project, true) < 1;
	}
	
	private boolean isClasspathValid(IProject project){
		ClasspathValidator classpathValidator = new ClasspathValidator(project);
		boolean validated = classpathValidator.validate();
		return validated;
	}
	


	/**
	 * @param project
	 * @param error
	 * @return
	 */
	private int getValidationCount(IProject project, boolean error) {
		int errorCount = 0;
		int warningCount = 0;
		try {
			IMarker[] buildPathMarkers = project.findMarkers(
					IResourceValidator.BUILD_PATH_MARKER_TYPE, true,
					IResource.DEPTH_INFINITE);
			IMarker[] markers = project.findMarkers(
					IResourceValidator.VALIDATION_MARKER_TYPE, true,
					IResource.DEPTH_INFINITE);
			IMarker[] javaBuildPathMarkers = project.findMarkers(IJavaModelMarker.BUILDPATH_PROBLEM_MARKER, true, IResource.DEPTH_INFINITE);
			IMarker[] gvMarkers = project.findMarkers(StudioCorePlugin.PLUGIN_ID + ".gvproblem", true, IResource.DEPTH_INFINITE);
			List<IMarker> buildPathMarkersList = Arrays.asList(buildPathMarkers);
			List<IMarker> validationMarkersList = Arrays.asList(markers);
			List<IMarker> javaMarkersList = Arrays.asList(javaBuildPathMarkers);
			List<IMarker> gvMarkersList = Arrays.asList(gvMarkers);
			
			List<IMarker> markersList = new ArrayList<IMarker>();
			markersList.addAll(buildPathMarkersList);
			markersList.addAll(validationMarkersList);
			markersList.addAll(javaMarkersList);
			markersList.addAll(gvMarkersList);
			
			if (markersList.size() > 0) {
				for (IMarker marker : markersList) {
					if (fexcludeResources.contains(marker.getResource()
							.getFileExtension())) {
						continue;
					}
					if ((Integer) marker.getAttribute(IMarker.SEVERITY) == IMarker.SEVERITY_ERROR) {
						errorCount++;
					} else {
						warningCount++;
					}
				}
			}
			if (error) {
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
	 * @param file
	 * @return
	 * @throws Exception
	 */
	private boolean preBuildFileOps(File file) throws Exception {
		boolean overwrite = StudioUIPlugin.getDefault().getPreferenceStore().getBoolean(StudioUIPreferenceConstants.STUDIO_OVERWRITE_EAR);
		if (overwrite) {
			if (!file.canWrite()) {
				file.setWritable(true);	
			} else if (CommonUtil.isLocked(file)) {
				MessageDialog.openError(fWindow.getShell(), Messages
						.getString("Build.EAR.title"), Messages.getString(
						"Build.EAR.file.locked", file.getAbsolutePath()));
				return false;
			}
			return true;
		}
		if (file.exists()) {
			if (!file.canWrite()) {
				overwrite = OverwriteMessageDialog.openQuestion(fWindow.getShell(),
						Messages.getString("Build.EAR.title"), Messages
								.getString("Build.EAR.file.readonly.exists"), StudioUIPlugin.getDefault().getPreferenceStore(), StudioUIPreferenceConstants.STUDIO_OVERWRITE_EAR,  Messages
								.getString("Build.EAR.file.always.overwrite"));
				if (overwrite) {
					file.setWritable(true);
				}
			} else if (CommonUtil.isLocked(file)) {
				MessageDialog.openError(fWindow.getShell(), Messages
						.getString("Build.EAR.title"), Messages.getString(
						"Build.EAR.file.locked", file.getAbsolutePath()));
				overwrite = false;
			} else {
				overwrite = OverwriteMessageDialog.openQuestion(fWindow.getShell(),
						Messages.getString("Build.EAR.title"), Messages
								.getString("Build.EAR.file.exists"), StudioUIPlugin.getDefault().getPreferenceStore(), StudioUIPreferenceConstants.STUDIO_OVERWRITE_EAR,  Messages
								.getString("Build.EAR.file.always.overwrite"));
			}
			eaconfig.setOverwrite(overwrite);
		} else {
			overwrite = true;
		}
		return overwrite;
	}

	/**
	 * @param title
	 * @param desc
	 */
	private void showAsyncMessage(final String title, final String desc) {
		Display.getDefault().asyncExec(new Runnable() {

			@Override
			public void run() {
				MessageDialog.openInformation(StudioUIPlugin.getShell(), title,
						desc);

			}
		});
	}
}