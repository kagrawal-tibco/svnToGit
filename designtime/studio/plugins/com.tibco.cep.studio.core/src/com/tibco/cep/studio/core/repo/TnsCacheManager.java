package com.tibco.cep.studio.core.repo;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Queue;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.common.util.EList;

import com.tibco.cep.studio.core.StudioCorePlugin;
import com.tibco.cep.studio.core.index.model.DesignerProject;
import com.tibco.cep.studio.core.index.update.IStudioElementDelta;
import com.tibco.cep.studio.core.index.update.IStudioModelChangedListener;
import com.tibco.cep.studio.core.index.update.StudioModelChangedEvent;
import com.tibco.cep.studio.core.index.update.StudioModelDelta;
import com.tibco.cep.studio.core.index.update.StudioProjectDelta;

public class TnsCacheManager implements IResourceChangeListener, IStudioModelChangedListener {

    private final static String TYPE_CONCEPT 	="concept";
    private final static String TYPE_EVENT  	="event";
    private final static String TYPE_TIMEEVENT  ="time";
    private final static String TYPE_SCORECARD  ="scorecard";
    private final static String TYPE_RULEFUNCTION  ="rulefunction";
    private final static String TYPE_AESCHEMA 	="aeschema";
    private final static String TYPE_XSD 		="xsd";
    private final static String TYPE_DTD 		="dtd";
    private final static String TYPE_WSDL		="wsdl";
    private final static String TYPE_METRIC		="metric";

    private static HashMap<String, EMFTnsCache> projectTnsCache = new HashMap<String, EMFTnsCache>();
	private static HashMap<String, TnsCacheUpdater> projectToUpdater = new HashMap<String, TnsCacheUpdater>();

    public static String[] extensions = new String[] {
    	TYPE_CONCEPT,
    	TYPE_EVENT,
    	TYPE_TIMEEVENT,
    	TYPE_SCORECARD,
    	TYPE_RULEFUNCTION,
    	TYPE_AESCHEMA,
    	TYPE_XSD,
    	TYPE_DTD,
    	TYPE_WSDL,
    	TYPE_METRIC
    };
    
    static {
    	Arrays.sort(extensions);
    }
    
	public TnsCacheManager() {
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
		TnsCacheUpdater tnsCacheUpdater = projectToUpdater.get(project.getName());
		if (tnsCacheUpdater == null) {
			// we don't care about this change.  The cache will be created on demand, rather than creating it now
//			System.out.println("Cache not yet created");
			return;
		}
		try {
			delta.accept(tnsCacheUpdater);
		} catch (CoreException e) {
			StudioCorePlugin.log(e);
		}
		Queue<Throwable> q = tnsCacheUpdater.getErrors();
		Throwable t = null;
		do {
			// drain the error queue
			t = q.poll();
			if(t != null)
				StudioCorePlugin.log(t);
		} while (t != null);
		
	}

	public synchronized EMFTnsCache getCache(String projectName) {
		if (projectName == null) {
			return null;
		}
		EMFTnsCache cache;
		
		if (projectTnsCache.containsKey(projectName)) {
			cache = projectTnsCache.get(projectName);
		}
		else {
			ClassLoader ccl = Thread.currentThread().getContextClassLoader();
			if (ccl == null) {
				// BE-22240 : For OS X, the context classloader is null on the UI thread.  Need to set it so that the cache init can find DelegatingTnsFlavor (see BootstrapTnsCache)
				Thread.currentThread().setContextClassLoader(getClass().getClassLoader());
			}
			cache = new EMFTnsCache(getProjectURI(projectName), projectName);
			projectTnsCache.put(projectName, cache);
			initializeCache(projectName, cache);
			if (ccl == null) {
				// Set back to null to avoid potential classloader issues elsewhere
				Thread.currentThread().setContextClassLoader(null);
			}
		}
		return cache;
	}

	private String getProjectURI(String projectName) {
		IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(projectName);
		if (project.getLocation() != null) {
			return project.getLocation().toString();
		}
		return "";
	}

	private void initializeCache(String projectName, EMFTnsCache cache) {
		Date start = new Date();
		TnsCacheUpdater updater = new TnsCacheUpdater(cache);
		projectToUpdater.put(projectName, updater);
		
		IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(projectName);
		try {
			project.accept(updater);
		} catch (CoreException e) {
			StudioCorePlugin.log(e);
		}
		cache.resolveAndCheck();
		Date end = new Date();
		// System.out.println("Cache initialization took "+(end.getTime() - start.getTime()));
		Queue<Throwable> q = updater.getErrors();
		Throwable t = null;
		do {
			// drain the error queue
			t = q.poll();
			if(t != null)
				StudioCorePlugin.log(t);
		} while (t != null);
		
	}

	public void clearCaches() {
		projectTnsCache.clear();
		projectToUpdater.clear();
	}

	public static boolean isCacheResource(IFile file) {
		if (file.getName().charAt(0) == '.') {
			// would this ever be valid?
			return false;
		}
		String extension = file.getFileExtension();
		return isCacheResource(extension);
	}

	public static boolean isCacheResource(String extension) {
		// needs to be case insensitive...
		if (extension == null) {
			return false;
		}
		if (Arrays.binarySearch(extensions, extension) >= 0) {
			return true;
		}
		// do a case insensitive search.  Slow, but necessary
		for (String ext : extensions) {
			if (ext.equalsIgnoreCase(extension)) {
				return true;
			}
		}
		return false;
	}

	public void removeCache(String projectName) {
		projectTnsCache.remove(projectName);
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
			TnsCacheUpdater tnsCacheUpdater = projectToUpdater.get(projectName);
			if (tnsCacheUpdater == null) {
				// we don't care about this change.  The cache will be created on demand, rather than creating it now
				return;
			}
			if (type == IStudioElementDelta.REMOVED) {
				// cache will be deleted elsewhere
			} else {
				// WIP - check whether this flag is sufficient to avoid reloading here
//				if (!event.getDelta().hasReferenceChanges()) {
//					return;
//				}
				IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(projectName);
				EList<DesignerProject> referencedProjects = changedProject.getReferencedProjects();
				tnsCacheUpdater.updateProjectReferences(project, referencedProjects);
				Queue<Throwable> q = tnsCacheUpdater.getErrors();
				Throwable t = null;
				do {
					// drain the error queue
					t = q.poll();
					if(t != null)
						StudioCorePlugin.log(t);
				} while (t != null);
			}
		}
		
	}
	
}
