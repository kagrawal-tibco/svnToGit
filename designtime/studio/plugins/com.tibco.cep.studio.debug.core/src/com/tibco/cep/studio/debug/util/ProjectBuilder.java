package com.tibco.cep.studio.debug.util;

import java.io.File;
import java.text.MessageFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IResourceVisitor;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubProgressMonitor;

import com.tibco.cep.studio.core.repo.emf.StudioEMFProject;
import com.tibco.cep.studio.core.util.packaging.impl.EMFEarPackager;
import com.tibco.cep.studio.core.validation.IResourceValidator;
import com.tibco.cep.studio.core.validation.ValidationContext;
import com.tibco.cep.studio.core.validation.ValidationUtils;
import com.tibco.cep.studio.core.validation.ValidatorInfo;
import com.tibco.cep.studio.debug.core.StudioDebugCorePlugin;

public class ProjectBuilder {
	
	public static File generateTempEarFile(String projectName, IProgressMonitor submonitor) {
		File file = null;
		IProgressMonitor subMonitor = new SubProgressMonitor(submonitor, 100);
		try {
			subMonitor.beginTask("Building project EAR file",
					IProgressMonitor.UNKNOWN);
			subMonitor.worked(20);
			IProject project = ResourcesPlugin.getWorkspace().getRoot()
					.getProject(projectName);
			// validate Project
			if (isValid(project, new SubProgressMonitor(subMonitor, 30))) {
				// Build EAR
				subMonitor.setTaskName("Loading project...");
				subMonitor.worked(20);
				File tmpDir = new File(System.getProperty("java.io.tmpdir"));
				file = File.createTempFile(projectName, ".ear", tmpDir);
				final StudioEMFProject sproject = new StudioEMFProject(
						projectName);
				sproject.load();
				EMFEarPackager packager = new EMFEarPackager(sproject, false,"", false);
				packager.setOverWrite(true);
				packager.setEarFile(file);
				subMonitor.setTaskName(MessageFormat.format("Packaging project...{0}",projectName));
				subMonitor.worked(30);
				packager.close();
				subMonitor.done();
				StudioDebugCorePlugin.log(MessageFormat.format(
						"EAR built successfully...{0}", projectName));
			} else {
				StudioDebugCorePlugin
						.log(MessageFormat
								.format(
										"Problems found in the following project:{0}\n\n\t Errors:{1} Warnings:{2}\n\nPlease see the Problems view for more details",
										projectName, getValidationCount(
												project, true),
										getValidationCount(project, false)));
				return null;
			}

		} catch (Exception e1) {
			StudioDebugCorePlugin.log("Build EAR failed", e1);
			return null;
		}

		return file;
	}


	private static boolean isValid(final IProject project, IProgressMonitor monitor) {
		monitor.beginTask(MessageFormat.format("Validating project {0}",
				project.getName()), 1);
		if (monitor.isCanceled()) {
			return false;
		}
		SubProgressMonitor subMon = new SubProgressMonitor(monitor, 1);
		ResourceVisitor visitor = new ResourceVisitor(subMon);
		try {
			// clear all existing validation errors
			project.deleteMarkers(IResourceValidator.VALIDATION_MARKER_TYPE,
					true, IResource.DEPTH_INFINITE);
			visitor.setCountOnly(true);
			project.accept(visitor);
			subMon.beginTask(MessageFormat.format("Validating project {0}",
					project.getName()), 1);
			visitor.setCountOnly(false);
			project.accept(visitor);
		} catch (CoreException e) {
			if (e.getStatus().getSeverity() == Status.CANCEL) {
				return false;
			} else {
				StudioDebugCorePlugin.log(e);
			}
		}
		return getValidationCount(project, true) < 1;
	}

	public static int getValidationCount(IProject project, boolean error) {
		int errorCount = 0;
		int warningCount = 0;
		try {
			IMarker[] markers = project.findMarkers(
					IResourceValidator.VALIDATION_MARKER_TYPE, true,
					IResource.DEPTH_INFINITE);
			if (markers.length > 0) {
				for (int i = 0; i < markers.length; i++) {
					if ((Integer) markers[i].getAttribute(IMarker.SEVERITY) == IMarker.SEVERITY_ERROR) {
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
			StudioDebugCorePlugin.log(e);
		}
		return 0;
	}
	
	public static class ResourceVisitor implements IResourceVisitor {

		private SubProgressMonitor fMonitor;
		@SuppressWarnings("unused")
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
				return false;
			}
			ValidatorInfo[] projectResourceValidators = ValidationUtils.getProjectResourceValidators();
			if (projectResourceValidators == null) {
				fMonitor.worked(1);
				return false;
			}
			fMonitor.subTask(resource.getName());
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
			fMonitor.worked(1);
			return true;
		}
		
	}
	
	/**
	 * @param path
	 * @return
	 */
	public static String updatePathforWhiteSpace(String path) {
		Pattern pattern = Pattern.compile("\\s");
		Matcher matcher = pattern.matcher(path);
		boolean found = matcher.find();
		if (found) {
			path = "\"" + path + "\"";
		}
		return path;
	}

}
