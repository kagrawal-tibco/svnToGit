package com.tibco.cep.studio.core.repo;

import java.util.HashMap;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.common.util.EList;

import com.tibco.cep.studio.core.adapters.AdapterCache;
import com.tibco.cep.studio.core.adapters.CoreAdapterFactory;
import com.tibco.cep.studio.core.index.model.DesignerProject;
import com.tibco.cep.studio.core.index.update.IStudioElementDelta;
import com.tibco.cep.studio.core.index.update.IStudioModelChangedListener;
import com.tibco.cep.studio.core.index.update.StudioModelChangedEvent;
import com.tibco.cep.studio.core.index.update.StudioModelDelta;
import com.tibco.cep.studio.core.index.update.StudioProjectDelta;

public class AdapterCacheManager implements IResourceChangeListener, IStudioModelChangedListener {

	private static HashMap<String, AdapterCacheUpdater> projectToUpdater = new HashMap<String, AdapterCacheUpdater>();

	public AdapterCacheManager() {
	}

	@Override
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
			if (!((IProject)delta.getResource()).exists()) {
				return;
			}
		}
		if (delta.getFlags() == IResourceDelta.MARKERS
				|| delta.getFlags() == IResourceDelta.SYNC) {
			// don't process the file if only the markers
			// or synchronization status have changed
			return;
		}
		IResource resource = delta.getResource();
		if (resource instanceof IWorkspaceRoot) {
			IResourceDelta[] affectedChildren = delta.getAffectedChildren();
			for (IResourceDelta resourceDelta : affectedChildren) {
				processDelta(resourceDelta);
			}
			return;
		}
		processDelta(delta);
	}

	private void processDelta(IResourceDelta delta) {
		IProject project = delta.getResource().getProject();
		AdapterCache adapterCache = CoreAdapterFactory.fProjectAdapterCache.get(project.getName());
		if (adapterCache == null) {
			// we don't care about this change.  The cache will be created on demand, rather than creating it now
			return;
		}
		AdapterCacheUpdater adapterCacheUpdater = projectToUpdater.get(project.getName());
		if (adapterCacheUpdater == null) {
			adapterCacheUpdater = new AdapterCacheUpdater();
			projectToUpdater.put(project.getName(), adapterCacheUpdater);
		}
		try {
			delta.accept(adapterCacheUpdater);
		} catch (CoreException e) {
			e.printStackTrace();
		}
		
	}

//	public synchronized AdapterCache getCache(String projectName) {
//		if (projectName == null) {
//			return null;
//		}
//		AdapterCache cache;
//		
//		if (CoreAdapterFactory.fProjectAdapterCache.containsKey(projectName)) {
//			cache = CoreAdapterFactory.fProjectAdapterCache.get(projectName);
//		}
//		else {
//			cache = new AdapterCache();
//			CoreAdapterFactory.fProjectAdapterCache.put(projectName, cache);
//			initializeCache(projectName, cache);
//		}
//		return cache;
//	}
//
//	private String getProjectURI(String projectName) {
//		IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(projectName);
//		if (project.getLocation() != null) {
//			return project.getLocation().toString();
//		}
//		return "";
//	}

//	private void initializeCache(String projectName, AdapterCache cache) {
//		Date start = new Date();
//		AdapterCacheUpdater updater = new AdapterCacheUpdater(cache);
//		projectToUpdater.put(projectName, updater);
//		
//		IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(projectName);
//		try {
//			project.accept(updater);
//		} catch (CoreException e) {
//			e.printStackTrace();
//		}
//		Date end = new Date();
//		System.out.println("Cache initialization took "+(end.getTime() - start.getTime()));
//	}

	public void clearCaches() {
		CoreAdapterFactory.fProjectAdapterCache.clear();
		projectToUpdater.clear();
	}

	public void removeCache(String projectName) {
		CoreAdapterFactory.fProjectAdapterCache.remove(projectName);
		projectToUpdater.remove(projectName);
	}

	@Override
	public void modelChanged(StudioModelChangedEvent event) {
		StudioModelDelta delta = event.getDelta();
		List<StudioProjectDelta> changedProjects = delta.getChangedProjects();
		for (int j = 0; j < changedProjects.size(); j++) {
			StudioProjectDelta projectDelta = changedProjects.get(j);
			int type = projectDelta.getType();
			DesignerProject changedProject = projectDelta.getChangedProject();
			if (changedProject == null) {
				return;
			}
			String projectName = changedProject.getName();
			AdapterCacheUpdater adapterCacheUpdater = projectToUpdater.get(projectName);
			if (adapterCacheUpdater == null) {
				// we don't care about this change.  The cache will be created on demand, rather than creating it now
				return;
			}
			if (type == IStudioElementDelta.REMOVED) {
				// cache will be deleted elsewhere
			} else {
				IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(projectName);
				EList<DesignerProject> referencedProjects = changedProject.getReferencedProjects();
				adapterCacheUpdater.updateProjectReferences(project, referencedProjects);
			}
		}
		
	}
	
}
