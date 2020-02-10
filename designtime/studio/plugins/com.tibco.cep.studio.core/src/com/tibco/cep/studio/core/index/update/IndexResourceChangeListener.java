package com.tibco.cep.studio.core.index.update;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.jar.JarFile;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IResourceDeltaVisitor;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.IJobChangeEvent;
import org.eclipse.core.runtime.jobs.IJobChangeListener;
import org.eclipse.core.runtime.jobs.IJobManager;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.emf.common.util.EList;

import com.tibco.cep.studio.common.StudioProjectCache;
import com.tibco.cep.studio.common.configuration.BuildPathEntry;
import com.tibco.cep.studio.common.configuration.IStudioProjectConfigurationChangeListener;
import com.tibco.cep.studio.common.configuration.ProjectLibraryEntry;
import com.tibco.cep.studio.common.configuration.StudioProjectConfiguration;
import com.tibco.cep.studio.common.configuration.StudioProjectConfigurationChangeEvent;
import com.tibco.cep.studio.common.configuration.update.IStudioProjectConfigurationDelta;
import com.tibco.cep.studio.core.StudioCorePlugin;
import com.tibco.cep.studio.core.index.jobs.BinaryStorageIndexCreator;
import com.tibco.cep.studio.core.index.jobs.IndexJob;
import com.tibco.cep.studio.core.index.jobs.StudioProjectCacheLock;
import com.tibco.cep.studio.core.index.model.DesignerProject;
import com.tibco.cep.studio.core.index.model.IndexFactory;
import com.tibco.cep.studio.core.index.utils.IndexUtils;
import com.tibco.cep.studio.core.projlib.ProjectLibraryConfigurationChangeListener.UpdateProjectLibraryLinkedResourcesJob;
import com.tibco.cep.studio.core.util.CommonUtil;

public class IndexResourceChangeListener implements
		IResourceChangeListener, IStudioProjectConfigurationChangeListener {

	private class UpdateJobChangeListener implements IJobChangeListener {
		
		public void done(IJobChangeEvent event) {
			Job job = event.getJob();
			
			if (job instanceof UpdateIndexJob) {
				IStatus result = event.getResult();
				if (result.getSeverity() == Status.OK) {
					StudioProjectCache.getInstance().removeIndexJob(StudioProjectCache.UPDATE_KEY);
				}
			}
			if (job instanceof UpdateReferencedIndexJob) {
				IStatus result = event.getResult();
				if (result.getSeverity() == Status.OK) {
					StudioProjectCache.getInstance().removeIndexJob(((UpdateReferencedIndexJob) job).getOwnerProjectName());
				}
			}
		}
		
		public void sleeping(IJobChangeEvent event) {}
		public void scheduled(IJobChangeEvent event) {}
		public void running(IJobChangeEvent event) {}
		public void awake(IJobChangeEvent event) {}
		public void aboutToRun(IJobChangeEvent event) {}
	}
	
	public class UpdateIndexJob extends IndexJob<DesignerProject> {

		private IResourceDelta fDelta;

		public UpdateIndexJob(IResourceDelta delta) {
			super("Updating index");
			this.fDelta = delta;
		}

		@Override
		public IStatus runJob(IProgressMonitor monitor) {
			IndexUpdateVisitor updateVisitor = new IndexUpdateVisitor(null);
			IResourceDelta[] deltas = null;
			if (fDelta.getResource() instanceof IWorkspaceRoot) {
				deltas = fDelta.getAffectedChildren();
			} else {
				if (fDelta != null) {
					deltas = new IResourceDelta[] { fDelta };
				}
			}
			try {
				if (deltas != null && deltas.length > 0) {
					for (IResourceDelta delta : deltas) {
						if (delta.getResource() != null) {
							DesignerProject index = IndexUtils.getIndex(delta.getResource().getProject());
							if (index == null) {
								continue; // non-Studio project, or the index will be created lazily
							}
							synchronized (index) {
								delta.accept(updateVisitor);
								// fire delta
								fireDesignerModelChangedEvent(updateVisitor.getAffectedProjects(), updateVisitor.hasReferenceChanges());
							}
						} else {
							delta.accept(updateVisitor);
							// fire delta
							fireDesignerModelChangedEvent(updateVisitor.getAffectedProjects(), updateVisitor.hasReferenceChanges());
						}
					}
				}
			} catch (CoreException e) {
				e.printStackTrace();
			}

			return Status.OK_STATUS;
		}

		@Override
		public boolean belongsTo(Object family) {
			return UPDATE_INDEX_FAMILY.equals(family);
		}
		
	}
	
	public class UpdateReferencedIndexJob extends IndexJob<DesignerProject> {
		
		private String ownerProjectName;
		private StudioProjectConfiguration fConfiguration;

		public UpdateReferencedIndexJob(String ownerProjectName, StudioProjectConfiguration config) {
			super("Updating index");
			this.ownerProjectName = ownerProjectName;
			this.fConfiguration = config;
		}
		
		public String getOwnerProjectName() {
			return ownerProjectName;
		}

		@Override
		public IStatus runJob(IProgressMonitor monitor) {			
			IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(ownerProjectName);
			if (!project.isAccessible() || !CommonUtil.isStudioProject(project)) {
				return Status.OK_STATUS;
			}

			DesignerProject parentIndex = StudioCorePlugin.getDesignerModelManager().getIndex(project);
			EList<DesignerProject> referencedProjects = parentIndex.getReferencedProjects();
			
			// first, find added config entries
			for (ProjectLibraryEntry bpe: fConfiguration.getProjectLibEntries()) {
				DesignerProject proj = findProject(bpe, referencedProjects);
				if (proj == null) {
					processReferencedProject(bpe, null, parentIndex, IStudioProjectConfigurationDelta.ADDED, monitor);
				}
			}
			// now find removed ones
			List<DesignerProject> removedProjects = new ArrayList<DesignerProject>();
			for (int i=0; i<referencedProjects.size(); i++) {
				DesignerProject proj = referencedProjects.get(i);
				BuildPathEntry bpe = findBuildPathEntry(proj, fConfiguration.getProjectLibEntries());
				if (bpe == null) {
					removedProjects.add(proj);
				}
			}
			for (DesignerProject removedProj : removedProjects) {
				processReferencedProject(null, removedProj, parentIndex, IStudioProjectConfigurationDelta.REMOVED, monitor);
			}

			List<StudioProjectDelta> affectedProjects = new ArrayList<StudioProjectDelta>();
			affectedProjects.add(new StudioProjectDelta(parentIndex, IStudioElementDelta.CHANGED));
			
			// fire delta
			fireDesignerModelChangedEvent(affectedProjects, true);
			return Status.OK_STATUS;
		}
		
		private BuildPathEntry findBuildPathEntry(DesignerProject proj,
				EList<ProjectLibraryEntry> list) {
			IPath archivePath = new Path(proj.getArchivePath());
			for (ProjectLibraryEntry bpe : list) {
				IPath libEntryPath = new Path(bpe.getPath(bpe.isVar()));
				if (libEntryPath.equals(archivePath)) {
					return bpe;
				}
			}
			return null;
		}

		private void processReferencedProject(BuildPathEntry entry,
				DesignerProject referencedIndex, DesignerProject parentIndex, int type, IProgressMonitor monitor) {
			if (type == IStudioProjectConfigurationDelta.ADDED) {
//				// remove old one (just in case), add new one
				removeReferencedProj(parentIndex, entry.getPath(entry.isVar()));
				String path =entry.getPath(entry.isVar());
				createAndAdd(path, parentIndex, monitor);
			} else if (type == IStudioProjectConfigurationDelta.CHANGED) {
				// remove old one, add new one
				removeReferencedProj(referencedIndex, referencedIndex.getName());
				createAndAdd(referencedIndex.getName(), parentIndex, monitor);
			} else if (type == IStudioProjectConfigurationDelta.REMOVED) {
				// remove old one
				removeReferencedProj(parentIndex, referencedIndex.getName());
			}
		}

		private DesignerProject findProject(BuildPathEntry entry,
				EList<DesignerProject> referencedProjects) {
			for (DesignerProject designerProject : referencedProjects) {
				if (designerProject.getName().equals(entry.getPath(entry.isVar()))) {
					return designerProject;
				}
			}
			return null;
		}

		private void removeReferencedProj(DesignerProject index, String libRef) {
			EList<DesignerProject> referencedProjects = index.getReferencedProjects();
			DesignerProject toBeRemoved = null;
			IPath libRefPath = new Path(libRef);
			for (DesignerProject designerProject : referencedProjects) {
				IPath archivePath = new Path(designerProject.getArchivePath());
				if (archivePath.equals(libRefPath)) {
					toBeRemoved = designerProject;
					break;
				}
			}
			if (toBeRemoved != null) {
				index.getReferencedProjects().remove(toBeRemoved);
			}
		}

		@Override
		public boolean belongsTo(Object family) {
			return UPDATE_INDEX_FAMILY.equals(family);
		}
		
		private void createAndAdd(String libPath, DesignerProject index, IProgressMonitor monitor) {
			JarFile file;
			try {
				file = new JarFile(libPath);
				DesignerProject project = IndexFactory.eINSTANCE.createDesignerProject();				
				project.setName(file.getName());
				project.setRootPath(libPath);
				// set Archive Resource Path also
				project.setArchivePath(libPath);
				BinaryStorageIndexCreator creator = new BinaryStorageIndexCreator(project, file, monitor, index.getName());
				creator.index();
				index.getReferencedProjects().add(project);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	public static Object UPDATE_INDEX_FAMILY = new Object();
	private IJobChangeListener fUpdateListener = new UpdateJobChangeListener();

	public IndexResourceChangeListener() {
		super();
	}

	public void resourceChanged(IResourceChangeEvent event) {
		if (!(event.getType() == IResourceChangeEvent.POST_CHANGE
				|| event.getType() == IResourceChangeEvent.PRE_DELETE)) {
			return;
		}
		IResourceDelta delta = event.getDelta();
		if (event.getBuildKind() != 0) {
			return; // just a build, no need to update
		}
		if (delta == null) {
			return;
		}
		if (delta.getResource() instanceof IProject) {
			IProject proj = (IProject)delta.getResource();
			if (!proj.exists()) {
				return;
			}
			if (!proj.isAccessible() || !CommonUtil.isStudioProject(proj)) {
				return;
			}
		}
		if (delta.getFlags() == IResourceDelta.MARKERS
				|| delta.getFlags() == IResourceDelta.SYNC) {
			// don't process the file if only the markers
			// or synchronization status have changed
			return;
		}
		UpdateIndexJob updateJob = new UpdateIndexJob(delta);
		updateJob.setUser(false);
		updateJob.addJobChangeListener(fUpdateListener);
		StudioCorePlugin.debug("Scheduling update job for "+delta.getResource());
		StudioProjectCache.getInstance().addIndexJob(StudioProjectCache.UPDATE_KEY,new StudioProjectCacheLock(updateJob));
		updateJob.schedule();
	}

	private void fireDesignerModelChangedEvent(
			List<StudioProjectDelta> affectedProjects, boolean hasReferenceChanges) {
		if (affectedProjects.size() == 0) {
			return; // optimization, do not fire if nothing has changed
		}
		StudioModelDelta delta = new StudioModelDelta(affectedProjects, hasReferenceChanges);
		StudioCorePlugin.getDefault().fireModelChangedEvent(delta);
	}

	@Override
	public void configurationChanged(StudioProjectConfigurationChangeEvent event) {
		int type = event.getDelta().getType();
		if (type == IStudioProjectConfigurationDelta.REMOVED) {
			// do nothing.  The index is already deleted
			return;
		}
		StudioProjectConfiguration config = event.getDelta().getAffectedChild();
		String projectName = config.getName();//StudioProjectConfigurationManager.getInstance().getProjectName(config);
		/**
		 * Moved from {@link ProjectLibraryConfigurationChangedListener} configurationChanged()
		 * Linked resources should be linked before any indexing is done.
		 */
//		UpdateProjectLibraryLinkedResourcesJob updateLinkJob = new UpdateProjectLibraryLinkedResourcesJob(event.getDelta());
//		updateLinkJob.setRule(ResourcesPlugin.getWorkspace().getRoot().getProject(config.getName()));
//		updateLinkJob.schedule();
	
		UpdateReferencedIndexJob updateJob = new UpdateReferencedIndexJob(projectName, config);
//		updateJob.setRule(ResourcesPlugin.getWorkspace().getRoot().getProject(config.getName()));
		updateJob.addJobChangeListener(fUpdateListener);
		updateJob.setUser(false);
		StudioProjectCache.getInstance().addIndexJob(projectName,new StudioProjectCacheLock(updateJob));
		updateJob.schedule();
	}

}
