/**
 * 
 */
package com.tibco.cep.studio.core;

import java.io.File;
import java.util.LinkedHashSet;
import java.util.Set;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IResourceDeltaVisitor;
import org.eclipse.core.resources.WorkspaceJob;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Status;

import com.tibco.cep.studio.core.tools.ChannelMigrator;

/**
 * Listener which gets notified when existing project gets imported.
 * @author aathalye
 *
 */
public class ExternalProjectsImportResourceChangeListener implements IResourceChangeListener {
	
	private ResourceDeltaVisitor visitor = new ResourceDeltaVisitor();
	
	private Set<IProject> transformedProjects = new LinkedHashSet<IProject>();
	
	/* (non-Javadoc)
	 * @see org.eclipse.core.resources.IResourceChangeListener#resourceChanged(org.eclipse.core.resources.IResourceChangeEvent)
	 */
	@Override
	public void resourceChanged(IResourceChangeEvent event) {
		IResourceDelta delta = event.getDelta();
		try {
			if (IResourceChangeEvent.POST_CHANGE == event.getType()) {
				delta.accept(visitor);
			} 
			if (IResourceChangeEvent.PRE_DELETE == event.getType()) {
				IResource cachedResource = event.getResource();
				if (cachedResource instanceof IProject) {
					IProject project = (IProject)cachedResource;
					//Remove it
					transformedProjects.remove(project);
				}
			}
		} catch (CoreException e) {
			e.printStackTrace();
		}
	}
	
	class ResourceDeltaVisitor implements IResourceDeltaVisitor {

		/* (non-Javadoc)
		 * @see org.eclipse.core.resources.IResourceDeltaVisitor#visit(org.eclipse.core.resources.IResourceDelta)
		 */
		@Override
		public boolean visit(IResourceDelta delta) throws CoreException {
			IResource cachedResource = delta.getResource();
			
			if (cachedResource instanceof IProject) {
				IProject project = (IProject)cachedResource;
				//Check if transformation has already been run
				if (!transformedProjects.contains(project)) {
					//Do this only if the project exists
					if (project.exists()) {
						File locationFile = project.getLocation().toFile();
						try {
							ChannelMigrator channelMigrator = new ChannelMigrator();
							channelMigrator.migrateChannels(locationFile, "channel", true);
							transformedProjects.add(project);
							
							RefreshProjectJob job = new RefreshProjectJob(project);
							job.setRule(project);
							job.schedule();
						} catch (Exception e) {
							throw new RuntimeException(e);
						}
						return false;
					}
				}
			}
			return true;
		}
	}
	
	class RefreshProjectJob extends WorkspaceJob {
		private IProject project;
		
		public RefreshProjectJob(IProject project) {
			super("Project Refresh");
			this.project = project;
		}
		
		public IStatus runInWorkspace(IProgressMonitor monitor) {
			try {
				if (!project.isSynchronized(IResource.DEPTH_INFINITE)) {
					project.refreshLocal(IResource.DEPTH_INFINITE, new NullProgressMonitor());
				}
			} catch (CoreException e) {
				StudioCorePlugin.log(e);
			}
			return Status.OK_STATUS;
		}
	}
}
