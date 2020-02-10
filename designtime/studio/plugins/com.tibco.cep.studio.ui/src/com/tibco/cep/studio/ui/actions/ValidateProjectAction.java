package com.tibco.cep.studio.ui.actions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IResourceVisitor;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubProgressMonitor;
import org.eclipse.core.runtime.jobs.IJobChangeEvent;
import org.eclipse.core.runtime.jobs.IJobChangeListener;
import org.eclipse.core.runtime.jobs.IJobManager;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.emf.common.util.EList;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;

import com.tibco.cep.studio.core.index.model.DesignerElement;
import com.tibco.cep.studio.core.index.model.DesignerProject;
import com.tibco.cep.studio.core.index.model.ElementContainer;
import com.tibco.cep.studio.core.index.model.SharedElement;
import com.tibco.cep.studio.core.index.utils.IndexUtils;
import com.tibco.cep.studio.core.util.CommonUtil;
import com.tibco.cep.studio.core.validation.IResourceValidator;
import com.tibco.cep.studio.core.validation.IResourceValidatorExtension;
import com.tibco.cep.studio.core.validation.SharedElementValidationContext;
import com.tibco.cep.studio.core.validation.ValidationContext;
import com.tibco.cep.studio.core.validation.ValidationUtils;
import com.tibco.cep.studio.core.validation.ValidatorInfo;
import com.tibco.cep.studio.ui.StudioUIPlugin;

public class ValidateProjectAction extends ValidateResourceAction {

	private StringBuffer buffer;
	private Map<String, Map<String, Integer>> validateProjectsMap = new HashMap<String, Map<String, Integer>>();
	
	public static Object VALIDATE_PROJECT_FAMILY = new Object();

	public static class ResourceVisitor implements IResourceVisitor {

		private SubProgressMonitor fMonitor;
		private int fCount = 0;
		private boolean fCountOnly;

		/**
		 * @param countOnly
		 */
		public void setCountOnly(boolean countOnly) {
			fCountOnly = countOnly;
		}

		/**
		 * @param subProgressMonitor
		 */
		public ResourceVisitor(SubProgressMonitor subProgressMonitor) {
			this.fMonitor = subProgressMonitor;
		}


		/* (non-Javadoc)
		 * @see org.eclipse.core.resources.IResourceVisitor#visit(org.eclipse.core.resources.IResource)
		 */
		public boolean visit(IResource resource) throws CoreException {
			if (fCountOnly) {
				fCount++;
				return true;
			}
			if (fMonitor.isCanceled()) {
				throw new CoreException(Status.CANCEL_STATUS);
			}
			ValidatorInfo[] projectResourceValidators = ValidationUtils.getProjectResourceValidators();
			if (projectResourceValidators == null) {
				fMonitor.worked(1);
				return false;
			}
			fMonitor.subTask(resource.getName());
			validateResource(resource, projectResourceValidators);
			if (resource instanceof IProject) {
				IProject proj = (IProject) resource;
				DesignerProject index = IndexUtils.getIndex(proj.getName());
				EList<DesignerProject> referencedProjects = index.getReferencedProjects();
				for (DesignerProject designerProject : referencedProjects) {
					processReferencedProject(proj, designerProject, projectResourceValidators);
				}
			}
			fMonitor.worked(1);
			return true;
		}
		
		private void processReferencedProject(IProject proj,
				ElementContainer container,
				ValidatorInfo[] projectResourceValidators) throws CoreException {
			EList<DesignerElement> entries = container.getEntries();
			for (DesignerElement designerElement : entries) {
				if (designerElement instanceof ElementContainer) {
					processReferencedProject(proj, (ElementContainer) designerElement, projectResourceValidators);
				} else if (designerElement instanceof SharedElement) {				
					validateSharedResource(proj, (SharedElement) designerElement, projectResourceValidators);
				}
			}
		}

		private void validateSharedResource(IProject project, SharedElement sharedElement, 
				ValidatorInfo[] projectResourceValidators) throws CoreException {
			for (ValidatorInfo validatorInfo : projectResourceValidators) {
				if (!(validatorInfo.fValidator instanceof IResourceValidatorExtension)) {
					continue;
				}
				IResourceValidatorExtension validator = (IResourceValidatorExtension) validatorInfo.fValidator;
				if (validatorInfo.enablesFor(sharedElement)) {
					SharedElementValidationContext vldContext = new SharedElementValidationContext(project, sharedElement, IResourceDelta.CHANGED, IncrementalProjectBuilder.FULL_BUILD);
					if (!validator.validate(vldContext)) {
						if (!validatorInfo.fValidator.canContinue()) {
							// something happened (for example, an unrecoverable error), and we cannot continue
							throw new CoreException(Status.CANCEL_STATUS);
						}
					}
				}
			}
		}

		private void validateResource(IResource resource,
				ValidatorInfo[] projectResourceValidators) throws CoreException {
			for (ValidatorInfo validatorInfo : projectResourceValidators) {
				if (validatorInfo.enablesFor(resource)) {
					ValidationContext vldContext = new ValidationContext(resource , IResourceDelta.CHANGED , IncrementalProjectBuilder.FULL_BUILD);
					if (!validatorInfo.fValidator.validate(vldContext)) {
						if (!validatorInfo.fValidator.canContinue()) {
							// something happened (for example, an unrecoverable error), and we cannot continue
							throw new CoreException(Status.CANCEL_STATUS);
						}
					}
				}
			}
		}

	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.ui.actions.ValidateResourceAction#run(org.eclipse.jface.action.IAction)
	 */
	public void run(IAction action) {
		if (!performValidate()) {
			Display.getDefault().asyncExec(new Runnable() {
				public void run() {
					MessageDialog.openError(new Shell(), "Project Validation failed!", "Exception occurred.");
				}
			});
		}
	}

	public boolean performValidate() {
		if (!(_selection instanceof StructuredSelection)) {
			return false;
		}
		StructuredSelection selection = (StructuredSelection) _selection;
		if (selection.isEmpty()) {
			return false;
		}
		final List<IProject> projectsToValidate = new ArrayList<IProject>();
		Iterator<?> iterator = selection.iterator();
		while (iterator.hasNext()) {
			Object next = iterator.next();
			if (next instanceof IResource) {
				IProject project = ((IResource)next).getProject();
				if (!projectsToValidate.contains(project)) {
					projectsToValidate.add(project);
				}
			}
		}
		return performValidate(projectsToValidate);
	}
	
	/**
	 * @param projectsToValidate
	 * @return
	 */
	public boolean performValidate(List<IProject> projectsToValidate) {
		ValidateProjectJob job = new ValidateProjectJob("Validating...", projectsToValidate);
		job.addJobChangeListener(new ValidateProjectJobChangeListener());
		job.setPriority(Job.BUILD);
		job.setUser(true);
		job.schedule();
		return true;
	}

	protected void showMessage(final boolean hasErrors, final boolean hasWarnings) {
		Display.getDefault().asyncExec(new Runnable() {
			public void run() {
				if (hasErrors) {
					showError();
					return;
				} else if (hasWarnings) {
					showWarning();
					return;
				} else {
//					MessageDialog.openInformation(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), "Project Validation", "Validation was successful.");
					return ;
				} 
			}
		});
	}
	
	protected void showError() {
		MessageDialog.openError(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), "Project Validation Error", buffer.toString());
	}
	
	protected void showWarning() {
		MessageDialog.openWarning(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), "Project Validation Warning", buffer.toString());
	}
	
	public boolean hasError(String projectName) {
		return validateProjectsMap.containsKey(projectName) && validateProjectsMap.get(projectName).get("Error") > 0;
	}

	public StringBuffer getError() {
		return buffer;
	}
	
	private class ValidateProjectJob extends Job {

		private List<IProject> projectsToValidate;

		public ValidateProjectJob(String name, List<IProject> projectsToValidate) {
			super(name);
			this.projectsToValidate = projectsToValidate;
		}

		@Override
		protected IStatus run(IProgressMonitor monitor) {
			monitor.beginTask("Validating Project", projectsToValidate.size());
			for (IProject project : projectsToValidate) {
				if (monitor.isCanceled()) {
					return new Status(Status.OK, StudioUIPlugin
							.getUniqueIdentifier(), "Validation has been cancelled");
				}
				try {
					if (project != null && CommonUtil.isStudioProject(project) && project.isOpen()) {
						SubProgressMonitor subMon = new SubProgressMonitor(monitor, 1);
						ResourceVisitor visitor = new ResourceVisitor(subMon);
						// clear all existing validation errors
						project.deleteMarkers(IResourceValidator.VALIDATION_MARKER_TYPE, true, IResource.DEPTH_INFINITE);
						visitor.setCountOnly(true);
						project.accept(visitor);
						subMon.beginTask("Validating "+project.getName(), visitor.fCount);
						visitor.setCountOnly(false);
						project.accept(visitor);
					}
				} catch (CoreException e) {
					if (e.getStatus().getSeverity() == Status.CANCEL) {
						return new Status(Status.OK, StudioUIPlugin
								.getUniqueIdentifier(), "Validation has been cancelled");
					} else {
						return new Status(Status.ERROR, StudioUIPlugin
								.getUniqueIdentifier(), "Validate Project has failed", e);
					}
				}
			}

			validateProjectsMap.clear();
			// report any errors found
			buffer = new StringBuffer();
			buffer.append("Problems found in the following project(s):\n");
			boolean hasErrors = false;
			boolean hasWarnings = false;
			for (IProject project : projectsToValidate) {
				try {
					Map<String, Integer> ewmap = new HashMap<String, Integer>();
					IMarker[] markers = project.findMarkers(IResourceValidator.VALIDATION_MARKER_TYPE, true, IResource.DEPTH_INFINITE);
					int errorCount = 0;
					int warningCount = 0;
					if (markers.length > 0) {
						for (int i = 0; i < markers.length; i++) {
							if ((Integer) markers[i].getAttribute(IMarker.SEVERITY) == IMarker.SEVERITY_ERROR) {
								errorCount++;
							} else {
								warningCount++;
							}
						}

						if (!hasErrors) {
							hasErrors = errorCount > 0;
						}
						if (!hasWarnings) {
							hasWarnings = warningCount > 0;
						}

						ewmap.put("Error", errorCount);
						ewmap.put("Warning", warningCount);

						validateProjectsMap.put(project.getName(), ewmap);

						buffer.append('\t');
						buffer.append(project.getName());
						buffer.append(" (");
						buffer.append(errorCount);
						buffer.append(" errors, ");
						buffer.append(warningCount);
						buffer.append(" warnings)");
						buffer.append('\n');
					}
				} catch (CoreException e) {
					e.printStackTrace();
				}
			}
			buffer.append("\nPlease see the Problems view for more details.");
			showMessage(hasErrors, hasWarnings);
			return Status.OK_STATUS;
		}

		@Override
		public boolean belongsTo(Object family) {
			return VALIDATE_PROJECT_FAMILY.equals(family);
		}

	}
	
  private class ValidateProjectJobChangeListener implements IJobChangeListener {
		
		public void done(IJobChangeEvent event) {
			Job job = event.getJob();
			
			if (job instanceof ValidateProjectJob) {
				IStatus result = event.getResult();
				if (result.getSeverity() == Status.OK) {
					//TODO
				}
			}
		}
		
		public void sleeping(IJobChangeEvent event) {}
		public void scheduled(IJobChangeEvent event) {}
		public void running(IJobChangeEvent event) {}
		public void awake(IJobChangeEvent event) {}
		public void aboutToRun(IJobChangeEvent event) {}
	}
  
  public void waitForValidateProjects() {
	  IJobManager jobManager = Job.getJobManager();
	  Job[] jobsOnProject = jobManager.find(VALIDATE_PROJECT_FAMILY);        
	  for (int i = 0; i < jobsOnProject.length; i++) {
		  if (jobsOnProject[i] instanceof ValidateProjectJob) {
			  ValidateProjectJob updateJob = (ValidateProjectJob) jobsOnProject[i];
			  try {
				  updateJob.join();
			  } catch (InterruptedException e) {
				  e.printStackTrace();
			  }
		  }
	  }
  }

}