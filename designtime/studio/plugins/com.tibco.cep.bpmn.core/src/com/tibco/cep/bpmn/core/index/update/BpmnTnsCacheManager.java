package com.tibco.cep.bpmn.core.index.update;

import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.emf.ecore.EObject;

import com.tibco.cep.bpmn.core.index.jobs.LoadTnsCacheJob;
import com.tibco.cep.bpmn.core.index.jobs.TnsCacheRule;
import com.tibco.cep.bpmn.core.index.jobs.UpdateTnsCacheJob;
import com.tibco.cep.bpmn.model.designtime.BpmnModelCache;
import com.tibco.cep.bpmn.model.designtime.utils.BpmnCommonIndexUtils;
import com.tibco.cep.studio.core.StudioCorePlugin;
import com.tibco.cep.studio.core.index.utils.CommonIndexUtils;
import com.tibco.cep.studio.core.repo.EMFTnsCache;
import com.tibco.cep.studio.core.repo.TnsCacheManager;

/**
 * 
 * @author majha
 *
 */
public class BpmnTnsCacheManager implements IResourceChangeListener {

    private final static String TYPE_PROCESS 	="beprocess";
	private static Map<String, BpmnTnsCacheUpdater> projectToUpdater = new ConcurrentHashMap<String, BpmnTnsCacheUpdater>();

    public static String[] extensions = new String[] {
    	TYPE_PROCESS
    };
    
    static {
    	Arrays.sort(extensions);
    }
    
	public BpmnTnsCacheManager() {
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
		if (delta.getResource() instanceof IProject
                && delta.getKind() == IResourceDelta.REMOVED) {
			removeCache(project.getName());
        }

		if (!project.isOpen())
			return;
		// IResource r = delta.getResource();
		if (!isValidResource(delta))
			return;
		/**
		 * TnsCache update should happen only if the changed resource is an entity whose schema's
 		 * are stored in the TnsCache . This depends on the index being loaded first.
		 */
//		if(CommonIndexUtils.isEntityType(r.getFileExtension())||
//		   CommonIndexUtils.isProcessType(r.getFileExtension())){
			BpmnTnsCacheUpdater tnsCacheUpdater = projectToUpdater.get(project.getName());
			if (tnsCacheUpdater == null) {
				EObject index = BpmnModelCache.getInstance().getIndex(delta.getResource().getProject().getName());// wait if index not generated
				if(index == null)
					return;
				getCache(project.getName(), false);
				tnsCacheUpdater = projectToUpdater.get(project.getName());
			}
			
			Job j = new UpdateTnsCacheJob(project, delta, tnsCacheUpdater);
			j.setRule(new TnsCacheRule(getCache(project.getName(), false)));
			j.schedule();
//		}
	}
	
	private boolean isValidResource(IResourceDelta delta) {
		IResource r = delta.getResource();
		if (r instanceof IProject || r instanceof IFolder) {
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
			}else{
				if ((delta.getMarkerDeltas() == null)
						|| (delta.getMarkerDeltas().length == 0)) {
					return ((CommonIndexUtils.isEntityType(r.getFileExtension()) || CommonIndexUtils
							.isProcessType(r.getFileExtension()) || r.getFileExtension().equals(CommonIndexUtils.WSDL_EXTENSION)) && !r.getFileExtension().equalsIgnoreCase(CommonIndexUtils.CHANNEL_EXTENSION) );
				}else if(delta.getKind() == IResourceDelta.REMOVED){
					return (r.getFileExtension().equals(CommonIndexUtils.WSDL_EXTENSION) || CommonIndexUtils
							.isProcessType(r.getFileExtension()) || TnsCacheManager.isCacheResource((IFile)r));
				}
			}
			
		}
		return false;
	}

	public synchronized EMFTnsCache getCache(String projectName, boolean waitForProcessLoad) {
		EMFTnsCache cache = StudioCorePlugin.getCache(projectName);
		if (cache == null) {
			return null;
		}
		if (!projectToUpdater.containsKey(projectName)) {
			initializeCache(projectName, cache,waitForProcessLoad);
		}
		
		return cache;
	}

	private void initializeCache(String projectName, EMFTnsCache cache, boolean join) {
		EObject index = BpmnCommonIndexUtils.getIndex(projectName);
		if (index != null) {
			BpmnTnsCacheUpdater updater = new BpmnTnsCacheUpdater(cache);
			projectToUpdater.put(projectName, updater);
//			EObjectWrapper<EClass, EObject> indexWrapper = EObjectWrapper.wrap(index);
//			ECoreHelper.loadImports(indexWrapper, cache, projectName);
			IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(projectName);
			LoadTnsCacheJob tnsCacheJob = new LoadTnsCacheJob(project, updater);
			tnsCacheJob.setProperty(tnsCacheJob.getKey(), index);
			tnsCacheJob.setRule(new TnsCacheRule(cache));
			tnsCacheJob.schedule();
			if(join){
				try {
					Job.getJobManager().resume();
					tnsCacheJob.join();
				} catch (InterruptedException e) {
					
				}
			}
		}
		
	}
	
	public void removeCache(String projectName) {
		projectToUpdater.remove(projectName);
	}

	public static boolean isCacheResource(IFile file) {
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
	
}
