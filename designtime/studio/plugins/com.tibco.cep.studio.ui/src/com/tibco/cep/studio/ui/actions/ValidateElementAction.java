package com.tibco.cep.studio.ui.actions;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IResourceVisitor;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubProgressMonitor;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.WorkspaceModifyOperation;

import com.tibco.cep.studio.core.util.CommonUtil;
import com.tibco.cep.studio.core.validation.IResourceValidator;
import com.tibco.cep.studio.core.validation.ValidationContext;
import com.tibco.cep.studio.core.validation.ValidationUtils;
import com.tibco.cep.studio.core.validation.ValidatorInfo;
import com.tibco.cep.studio.ui.StudioUIPlugin;
import com.tibco.cep.studio.ui.util.StudioResourceUtils;

public class ValidateElementAction extends ValidateProjectAction {

	private boolean showError;
	private boolean showWarning;
	private StringBuffer buffer;
	public static ValidatorInfo[] projectResourceValidators;
	private static boolean cancelled = false;
	
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
				cancelled = true;
				return false;
			}
			if (projectResourceValidators == null) {
				fMonitor.worked(1);
				return false;
			}
			fMonitor.subTask(resource.getName());
			validate(resource);
			fMonitor.worked(1);
			return true;
		}
	}
	
	/**
	 * @param resource
	 * @throws CoreException
	 */
	public static void validate(IResource resource) throws CoreException {
		for (ValidatorInfo validatorInfo : projectResourceValidators) {
			if (validatorInfo.enablesFor(resource)) {
				ValidationContext vldContext = new ValidationContext(resource , IResourceDelta.CHANGED , IncrementalProjectBuilder.FULL_BUILD);
				if (!validatorInfo.fValidator.validate(vldContext)) {
					if (!validatorInfo.fValidator.canContinue()) {
						cancelled = true;
						// something happened (for example, an unrecoverable error), and we cannot continue
						throw new CoreException(Status.CANCEL_STATUS);
					}
				}
			}
		}
	}
	

	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.ui.actions.ValidateResourceAction#run(org.eclipse.jface.action.IAction)
	 */
	public void run(IAction action) {
		cancelled = false;
		if (performValidate()) {
			if (!cancelled) {
				Display.getDefault().asyncExec(new Runnable() {
					public void run() {
						if (showError) {
							MessageDialog.openError(Display.getDefault().getActiveShell(), "Resource Validation Error", buffer.toString());
							return;
						} else if (showWarning) {
							MessageDialog.openWarning(Display.getDefault().getActiveShell(), "Resource Validation Warning", buffer.toString());
							return;
						} else {
							MessageDialog.openInformation(Display.getDefault().getActiveShell(), "Resource Validation", "Validation was successful.");
							return;
						} 
					}
				});
			}
		} else {
			Display.getDefault().asyncExec(new Runnable() {
				public void run() {
					MessageDialog.openError(new Shell(), "Resource Validation failed!", "Exception occurred.");
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
		final List<IResource> resourceToValidate = new ArrayList<IResource>();
		Iterator<?> iterator = selection.iterator();
		projectResourceValidators = ValidationUtils.getProjectResourceValidators();
		final List<IProject> projectsToValidate = new ArrayList<IProject>();
		while (iterator.hasNext()) {
			Object next = iterator.next();
			if (next instanceof IResource) {
				IResource resource = (IResource)next;
				if (resource instanceof IProject) {
					
					IProject project = (IProject) resource;
					if (!projectsToValidate.contains(project)) {
						projectsToValidate.add(project);
					}
					
				} else if (resource instanceof IFolder) {
			
					List<IResource> resources = new ArrayList<IResource>();
					StudioResourceUtils.traverseResources((IFolder)resource, resources);
					for (IResource res: resources) {
						StudioResourceUtils.listAllDependentResources(res, resourceToValidate);
					}

				} else {
					StudioResourceUtils.listAllDependentResources(resource, resourceToValidate);
				}
			}
		}
		return performValidate(resourceToValidate, projectsToValidate);
	}
	
	/**
	 * @param resourceToValidate
	 * @return
	 */
	public boolean performValidate(final List<IResource> resourceToValidate, final List<IProject> projectsToValidate) {

		WorkspaceModifyOperation op = new WorkspaceModifyOperation() {
			/* (non-Javadoc)
			 * @see org.eclipse.ui.actions.WorkspaceModifyOperation#execute(org.eclipse.core.runtime.IProgressMonitor)
			 */
			public void execute(IProgressMonitor monitor) {
				int size = resourceToValidate.size() + projectsToValidate.size();
				monitor.beginTask("Validating", size);
				try {
					for (IResource resource : resourceToValidate) {
						if (monitor.isCanceled()) {
							cancelled = true;
							return;
						}
						SubProgressMonitor subMon = new SubProgressMonitor(monitor, 1);
						if (resource ==  null) {
							continue;
						}
						resource.deleteMarkers(IResourceValidator.VALIDATION_MARKER_TYPE, true, IResource.DEPTH_INFINITE);
						if (projectResourceValidators == null) {
							monitor.worked(1);
							return;
						}
						subMon.subTask(resource.getName());
						validate(resource);
						subMon.worked(1);
					}
					for (IProject project : projectsToValidate) {
						if (monitor.isCanceled()) {
							cancelled = true;
							return;
						}
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

					}
				} catch (CoreException e) {
					if (e.getStatus().getSeverity() == Status.CANCEL) {
						cancelled = true;
					} else {
						e.printStackTrace();
					}
				}
			}
		};

		try {
			PlatformUI.getWorkbench().getProgressService().run(true, true, op);
		} catch (InterruptedException e) {
			return false;
		} catch (InvocationTargetException exception) {
			exception.printStackTrace();
			return false;
		}
		// report any errors found
		buffer = new StringBuffer();
		buffer.append("Problems found in the following resource(s):\n");
		boolean hasErrors = false;
		boolean hasWarnings = false;
		
		int errorCount = 0;
		int warningCount = 0;
		
		for (IResource resource : resourceToValidate) {
			try {
				if (resource == null) {
					continue;
				}
				IMarker[] markers = resource.findMarkers(IResourceValidator.VALIDATION_MARKER_TYPE, true, IResource.DEPTH_INFINITE);
				if (markers.length > 0) {
					for (int i = 0; i < markers.length; i++) {
						if ((Integer) markers[i].getAttribute(IMarker.SEVERITY) == IMarker.SEVERITY_ERROR) {
							errorCount++;
						} else {
							warningCount++;
						}
					}
					hasErrors = errorCount > 0;
					hasWarnings = warningCount > 0;
					buffer.append('\t');
					buffer.append(resource.getName());
					buffer.append(" (");
					buffer.append(errorCount);
					buffer.append(" errors, ");
					buffer.append(warningCount);
					buffer.append(" warnings)");
					buffer.append('\n');
				}
			} catch (Exception e) {
				StudioUIPlugin.debug(this.getClass().getName(), e.getMessage(), e);
			}
		}
		
		for (IProject project : projectsToValidate) {
			try {
				if (project == null) {
					continue;
				}
				IMarker[] markers = project.findMarkers(IResourceValidator.VALIDATION_MARKER_TYPE, true, IResource.DEPTH_INFINITE);
				if (markers.length > 0) {
					for (int i = 0; i < markers.length; i++) {
						if ((Integer) markers[i].getAttribute(IMarker.SEVERITY) == IMarker.SEVERITY_ERROR) {
							errorCount++;
						} else {
							warningCount++;
						}
					}
					hasErrors = errorCount > 0;
					hasWarnings = warningCount > 0;
					buffer.append('\t');
					buffer.append(project.getName());
					buffer.append(" (");
					buffer.append(errorCount);
					buffer.append(" errors, ");
					buffer.append(warningCount);
					buffer.append(" warnings)");
					buffer.append('\n');
				}
			} catch (Exception e) {
				StudioUIPlugin.debug(this.getClass().getName(), e.getMessage(), e);
			}
		}
		
		buffer.append("\nPlease see the Problems view for more details.");
		
		showError = hasErrors;
		showWarning = hasWarnings;
		
		return true;
	}

	public boolean hasError(){
		return showError;
	}

	public StringBuffer getError() {
		return buffer;
	}

}