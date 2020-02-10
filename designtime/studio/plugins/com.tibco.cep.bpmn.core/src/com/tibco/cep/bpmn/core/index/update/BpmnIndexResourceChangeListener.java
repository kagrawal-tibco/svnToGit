package com.tibco.cep.bpmn.core.index.update;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.IJobChangeEvent;
import org.eclipse.core.runtime.jobs.IJobChangeListener;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.emf.ecore.EObject;

import com.tibco.cep.bpmn.core.BpmnCorePlugin;
import com.tibco.cep.bpmn.core.index.jobs.BpmnProjectCacheLock;
import com.tibco.cep.bpmn.core.index.visitor.BpmnIndexUpdateVisitor;
import com.tibco.cep.bpmn.model.designtime.BpmnModelCache;
import com.tibco.cep.designtime.model.IndexCache;
import com.tibco.cep.studio.common.configuration.IStudioProjectConfigurationChangeListener;
import com.tibco.cep.studio.common.configuration.StudioProjectConfiguration;
import com.tibco.cep.studio.common.configuration.StudioProjectConfigurationChangeEvent;
import com.tibco.cep.studio.common.configuration.update.IStudioProjectConfigurationDelta;
import com.tibco.cep.studio.core.index.jobs.IndexJob;
import com.tibco.cep.studio.core.index.jobs.IndexJobResult;
import com.tibco.cep.studio.core.index.update.IndexResourceChangeListener.UpdateReferencedIndexJob;

public class BpmnIndexResourceChangeListener implements
		IResourceChangeListener/*, IStudioProjectConfigurationChangeListener */ {

	private class UpdateJobChangeListener implements IJobChangeListener {
		
		public void done(IJobChangeEvent event) {
			Job job = event.getJob();
			
			if (job instanceof UpdateBpmnIndexJob) {
				IStatus result = event.getResult();
				if (result.getSeverity() == Status.OK) {
					BpmnModelCache.getInstance().removeIndexJob(IndexCache.UPDATE_KEY);
				}
			}
			if (job instanceof UpdateReferencedBpmnIndexJob) {
				IStatus result = event.getResult();
				if (result.getSeverity() == Status.OK) {
					BpmnModelCache.getInstance().removeIndexJob(((UpdateReferencedIndexJob) job).getOwnerProjectName());
				}
			}
		}
		
		public void sleeping(IJobChangeEvent event) {}
		public void scheduled(IJobChangeEvent event) {}
		public void running(IJobChangeEvent event) {}
		public void awake(IJobChangeEvent event) {}
		public void aboutToRun(IJobChangeEvent event) {}
	}
	
	public class UpdateBpmnIndexJob extends IndexJob<EObject> {

		private IResourceDelta fDelta;

		public UpdateBpmnIndexJob(IResourceDelta delta) {
			super("Updating index");
			this.fDelta = delta;
		}

		@Override
		public IStatus runJob(IProgressMonitor monitor) {
			BpmnIndexUpdateVisitor updateVisitor = new BpmnIndexUpdateVisitor();
			try {
				if (fDelta != null) {
					fDelta.accept(updateVisitor);
				}
			} catch (CoreException e) {
				BpmnCorePlugin.log(e);
			}

			// fire delta
			fireBpmnModelChangedEvent(updateVisitor.getAffectedProjects(), false);
			return Status.OK_STATUS;
		}

		@Override
		public boolean belongsTo(Object family) {
			return UPDATE_INDEX_FAMILY.equals(family);
		}
		
	}
	
	public class UpdateReferencedBpmnIndexJob extends IndexJob<EObject> {
		
		private String ownerProjectName;
		@SuppressWarnings("unused")
		private StudioProjectConfiguration fConfiguration;

		public UpdateReferencedBpmnIndexJob(String ownerProjectName, StudioProjectConfiguration config) {
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
			if (!project.isAccessible() 
					//|| !BpmnProjectNatureManager.getInstance().isBpmnProject(project)
					) {
				return new IndexJobResult<EObject>(Status.INFO, 
						BpmnCorePlugin.PLUGIN_ID,
						"Failed to update bpmn index of "+ ownerProjectName+ ": project is not bpmn type"
						,null,new Exception());
			}

			EObject parentIndex = BpmnCorePlugin.getDefault().getBpmnModelManager().getBpmnIndex(project);
//			EList<DesignerProject> referencedProjects = parentIndex.getReferencedProjects();
//			
//			// first, find added config entries
//			for (ProjectLibraryEntry bpe: fConfiguration.getProjectLibEntries()) {
//				DesignerProject proj = findProject(bpe, referencedProjects);
//				if (proj == null) {
//					processReferencedProject(bpe, null, parentIndex, StudioProjectConfigurationChangeEvent.ADDED, monitor);
//				}
//			}
//			// now find removed ones
//			List<DesignerProject> removedProjects = new ArrayList<DesignerProject>();
//			for (int i=0; i<referencedProjects.size(); i++) {
//				DesignerProject proj = referencedProjects.get(i);
//				BuildPathEntry bpe = findBuildPathEntry(proj, fConfiguration.getProjectLibEntries());
//				if (bpe == null) {
//					removedProjects.add(proj);
//				}
//			}
//			for (DesignerProject removedProj : removedProjects) {
//				processReferencedProject(null, removedProj, parentIndex, StudioProjectConfigurationChangeEvent.REMOVED, monitor);
//			}
//
			List<BpmnProjectDelta> affectedProjects = new ArrayList<BpmnProjectDelta>();
			affectedProjects.add(new BpmnProjectDelta(parentIndex, IBpmnElementDelta.CHANGED));
			
			// fire delta
			fireBpmnModelChangedEvent(affectedProjects, true);
			return Status.OK_STATUS;
		}
		
//		private BuildPathEntry findBuildPathEntry(DesignerProject proj,
//				EList<ProjectLibraryEntry> list) {
//			for (ProjectLibraryEntry bpe : list) {				
//				if (bpe.getPath().equals(proj.getName())) {
//					return bpe;
//				}
//			}
//			return null;
//		}

//		private void processReferencedProject(BuildPathEntry entry,
//				DesignerProject referencedIndex, DesignerProject parentIndex, int type, IProgressMonitor monitor) {
//			if (type == StudioProjectConfigurationChangeEvent.ADDED) {
////				// remove old one (just in case), add new one
//				removeReferencedProj(parentIndex, entry.getPath());
//				createAndAdd(entry.getPath(), parentIndex, monitor);
//			} else if (type == StudioProjectConfigurationChangeEvent.CHANGED) {
//				// remove old one, add new one
//				removeReferencedProj(referencedIndex, referencedIndex.getName());
//				createAndAdd(referencedIndex.getName(), parentIndex, monitor);
//			} else if (type == StudioProjectConfigurationChangeEvent.REMOVED) {
//				// remove old one
//				removeReferencedProj(parentIndex, referencedIndex.getName());
//			}
//		}

//		private DesignerProject findProject(BuildPathEntry entry,
//				EList<DesignerProject> referencedProjects) {
//			for (DesignerProject designerProject : referencedProjects) {
//				if (designerProject.getName().equals(entry.getPath())) {
//					return designerProject;
//				}
//			}
//			return null;
//		}

//		private void removeReferencedProj(DesignerProject index, String libRef) {
//			EList<DesignerProject> referencedProjects = index.getReferencedProjects();
//			DesignerProject toBeRemoved = null;
//			for (DesignerProject designerProject : referencedProjects) {
//				if (designerProject.getName().equals(libRef)) {
//					toBeRemoved = designerProject;
//					break;
//				}
//			}
//			if (toBeRemoved != null) {
//				index.getReferencedProjects().remove(toBeRemoved);
//			}
//		}

		@Override
		public boolean belongsTo(Object family) {
			return UPDATE_INDEX_FAMILY.equals(family);
		}
		
//		private void createAndAdd(String libPath, DesignerProject index, IProgressMonitor monitor) {
//			JarFile file;
//			try {
//				file = new JarFile(libPath);
//				DesignerProject project = IndexFactory.eINSTANCE.createDesignerProject();				
//				project.setName(file.getName());
//				project.setRootPath(libPath);
//				// set Archive Resource Path also
//				project.setArchivePath(libPath);
//				BinaryStorageIndexCreator creator = new BinaryStorageIndexCreator(project, file, monitor, index.getName());
//				creator.index();
//				index.getReferencedProjects().add(project);
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//		}
		
	}
	
	public static Object UPDATE_INDEX_FAMILY = new Object();
	private IJobChangeListener fUpdateListener = new UpdateJobChangeListener();

	public BpmnIndexResourceChangeListener() {
		super();
	}

	public void resourceChanged(IResourceChangeEvent event) {
		if (!(event.getType() == IResourceChangeEvent.POST_CHANGE
				|| event.getType() == IResourceChangeEvent.PRE_DELETE)) {
			return;
		}
		IResourceDelta delta = event.getDelta();
		if (delta == null) {
			return;
		}
		if (delta.getResource() instanceof IProject) {
			IProject proj = (IProject)delta.getResource();
			if (!proj.exists()) {
				return;
			}
			if (!proj.isAccessible() 
					//|| !BpmnProjectNatureManager.getInstance().isBpmnProject(proj)
					) {
				return;
			}
		}
		if (delta.getFlags() == IResourceDelta.MARKERS
				|| delta.getFlags() == IResourceDelta.SYNC) {
			// don't process the file if only the markers
			// or synchronization status have changed
			return;
		}
		if(isValidResource(delta)){
			UpdateBpmnIndexJob updateJob = new UpdateBpmnIndexJob(delta);
			updateJob.setUser(false);
			updateJob.addJobChangeListener(fUpdateListener);
			BpmnCorePlugin.debug("Scheduling update job for "+delta.getResource());
			BpmnModelCache.getInstance().addIndexJob(IndexCache.UPDATE_KEY,new BpmnProjectCacheLock(updateJob));
			updateJob.schedule();
		}
		
		
		processDelta(delta);
	}
	
	private boolean isValidResource(IResourceDelta delta) {
		IResource r = delta.getResource();
		if (r instanceof IWorkspaceRoot ||r instanceof IProject || r instanceof IFolder) {
			IResourceDelta[] affectedChildren = delta.getAffectedChildren();
			for (IResourceDelta iResourceDelta : affectedChildren) {
				boolean validResource = isValidResource(iResourceDelta);
				if (validResource)
					return true;
			}
		} else {  
			if(r.getFileExtension() == null
					|| r.getFileExtension().isEmpty()){
				
				return false;
			}else if (delta.getFlags() == IResourceDelta.MARKERS
					|| delta.getFlags() == IResourceDelta.SYNC)
				return false;
			else
				return true;
		}
		return false;
	}
	
	private void processDelta(final IResourceDelta delta) {
		IResource resource = delta.getResource();
		if (resource instanceof IWorkspaceRoot) {
			IResourceDelta[] affectedChildren = delta.getAffectedChildren();
			for (IResourceDelta resourceDelta : affectedChildren) {
				processDelta(resourceDelta);
			}
			return;
		}
		final IProject project = resource.getProject();
		if(project == null)
			return;

//		if (project.exists() && project.isOpen()) {
//			Job j = new UpdateTnsCacheJob(project, delta);
//			j.setRule(new TnsCacheRule(BpmnCorePlugin.getCache(project.getName())));
//			j.schedule();
//		}
	}

	private void fireBpmnModelChangedEvent(
			List<BpmnProjectDelta> affectedProjects, boolean referencesOnly) {
		if (affectedProjects.size() == 0) {
			return; // optimization, do not fire if nothing has changed
		}
		BpmnModelDelta delta = new BpmnModelDelta(affectedProjects, referencesOnly);
		BpmnCorePlugin.getDefault().fireModelChangedEvent(delta);
	}

//	@Override
//	public void configurationChanged(StudioProjectConfigurationChangeEvent event) {
//		int type = event.getDelta().getType();
//		if (type == IStudioProjectConfigurationDelta.REMOVED) {
//			// do nothing.  The index is already deleted
//			return;
//		}
//		StudioProjectConfiguration config = event.getDelta().getAffectedChild();
//		String projectName = config.getName();//StudioProjectConfigurationManager.getInstance().getProjectName(config);
//		UpdateReferencedBpmnIndexJob updateJob = new UpdateReferencedBpmnIndexJob(projectName, config);
//		updateJob.addJobChangeListener(fUpdateListener);
//		updateJob.setUser(false);
//		BpmnModelCache.getInstance().addIndexJob(projectName,new BpmnProjectCacheLock(updateJob));
//		updateJob.schedule();
//	}

}
