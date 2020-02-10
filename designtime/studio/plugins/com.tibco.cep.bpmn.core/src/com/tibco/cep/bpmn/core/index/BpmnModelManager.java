package com.tibco.cep.bpmn.core.index;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ISaveContext;
import org.eclipse.core.resources.ISaveParticipant;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.IJobChangeEvent;
import org.eclipse.core.runtime.jobs.IJobManager;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.core.runtime.jobs.JobChangeAdapter;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.util.EcoreUtil;

import com.tibco.be.util.BidiMap;
import com.tibco.be.util.DualHashBidiMap;
import com.tibco.cep.bpmn.core.BpmnCoreConstants;
import com.tibco.cep.bpmn.core.BpmnCorePlugin;
import com.tibco.cep.bpmn.core.index.jobs.BpmnIndexRule;
import com.tibco.cep.bpmn.core.index.jobs.BpmnProjectCacheLock;
import com.tibco.cep.bpmn.core.index.jobs.CreateBpmnIndexJob;
import com.tibco.cep.bpmn.core.index.jobs.LoadAllBpmnIndexesJob;
import com.tibco.cep.bpmn.core.index.jobs.LoadBpmnIndexJob;
import com.tibco.cep.bpmn.core.index.jobs.PersistBpmnIndexJob;
import com.tibco.cep.bpmn.core.nature.BpmnProjectNatureManager;
import com.tibco.cep.bpmn.core.utils.ECoreHelper;
import com.tibco.cep.bpmn.model.designtime.BpmnModelCache;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnMetaModelConstants;
import com.tibco.cep.bpmn.model.designtime.ontology.BpmnIndex;
import com.tibco.cep.bpmn.model.designtime.ontology.impl.DefaultBpmnIndex;
import com.tibco.cep.bpmn.model.designtime.utils.EObjectWrapper;
import com.tibco.cep.rt.LoadAllIndexSchedulingRule;
import com.tibco.cep.studio.core.index.jobs.IndexJob;
import com.tibco.cep.studio.core.index.jobs.IndexJobResult;

public class BpmnModelManager extends JobChangeAdapter implements ISaveParticipant {
	
	public static final String LOAD_ALL_BPMN_INDEX_JOB = "loadAllIndexesFamily";
	
	private BpmnModelCache fIndexCache = BpmnModelCache.getInstance();
	
	// whether or not all indexes have been loaded
	private boolean fLoaded = false;

	private BidiMap<String, URI> fIndexURICache = new DualHashBidiMap<String,URI>();
	private static Map<String, ResourceSet> fProjectResourceSetMap = Collections.synchronizedMap(new HashMap<String,ResourceSet>());
//	private static Map<String,RootSymbolMap> fProjectRootSymbolMap = Collections.synchronizedMap(new HashMap<String,RootSymbolMap>());
	private static Map<String,BpmnNameGenerator> fProjectNameGenerator = Collections.synchronizedMap(new HashMap<String,BpmnNameGenerator>());

	/**
	 * @param project
	 * @return
	 */
	public EObject loadIndex(IProject project) {
		return loadIndex(project, false);
	}
	
	/**
	 * @param project
	 * @param wait, wait for load index job to complete and return valid index
	 * @return
	 */
	synchronized public EObject loadIndex(final IProject project, boolean wait) {
		if (fIndexCache.isIndexCached(project.getName())) {
			return fIndexCache.getIndex(project.getName());
		}
		
		LoadBpmnIndexJob runningLoadIndexJob = getExistingLoadIndexJob(project);
		if(runningLoadIndexJob != null){
			try {
				// check if load index job already in queue, if yes just join the job to finish
				BpmnCorePlugin.debug("joining load job for "+ project.getName());
				Job.getJobManager().resume();
				runningLoadIndexJob.join();
				if (fIndexCache.isIndexCached(project.getName())) {
					return fIndexCache.getIndex(project.getName());
				}
			} catch (InterruptedException e) {
				BpmnCorePlugin.log(e);
			}
		}
		
		
//		try {
//			if (!project.isOpen() || !BpmnProjectNatureManager.getInstance().isBpmnProject(project)) {
//				BpmnCorePlugin.debug("Project "+project.getName()+" is either not open or does not have the bpmn nature, skipping load");
//				return null;
//			}
//		} catch (Exception e) {
//			BpmnCorePlugin.log(e);
//			return null;
//		}

		// need to schedule and run a LoadIndexJob
		LoadBpmnIndexJob job = new LoadBpmnIndexJob(project);
		job.setRule(new BpmnIndexRule(project.getName()));
		job.addJobChangeListener(this);
		BpmnCorePlugin.debug("scheduling load job for "+project.getName());

		BpmnModelCache.getInstance().addIndexJob(project.getName(),new BpmnProjectCacheLock(job));
		job.schedule();
		EObject eObject = null;
		try {
			if (wait) {
				BpmnCorePlugin.debug("joining load job for "+project.getName());
				Job.getJobManager().resume();
				job.join();
				eObject = fIndexCache.getIndex(project.getName());
			}
		} catch (InterruptedException e) {
			BpmnCorePlugin.log(e);
		}
		return eObject;
	}
	
	
	private LoadBpmnIndexJob getExistingLoadIndexJob(IProject project){
		LoadBpmnIndexJob loadBpmnIndexJob= null;
		// make sure we don't schedule a create job if the
        // project is already scheduled to be refreshed
        IJobManager jobManager = Job.getJobManager();
        Job[] jobsOnProject = jobManager.find(project);
        for (int i = 0; i < jobsOnProject.length; i++) {
            if (jobsOnProject[i] instanceof LoadBpmnIndexJob) {
            	loadBpmnIndexJob = (LoadBpmnIndexJob) jobsOnProject[i];
            	break;
            }
        }
		
		return loadBpmnIndexJob;
	}
	
	
	/**
	 * @param projectName
	 * @return
	 */
	public EObject getBpmnIndex(String projectName) {
		if (Job.getJobManager().isSuspended()) {
			Job.getJobManager().resume();
		}
		IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(projectName);
		return getBpmnIndex(project);
	}
	
	/**
	 * @param project
	 * @return
	 */
	public EObject getBpmnIndex(IProject project) {
//		if (project == null || !project.isAccessible() || !BpmnProjectNatureManager.getInstance().isBpmnProject(project)) {
//			return null;
//		}
		if (Job.getJobManager().isSuspended()) {
			Job.getJobManager().resume();
		}
		EObject index = fIndexCache.getIndex(project.getName());
		if (index == null) {
			BpmnCorePlugin.debug("getting index for "+project.getName());
			index = loadIndex(project, true);
			if (index == null) {
				// the index was not loaded, either due to an error
				// or because the index has not yet been created
				// Either way, create a new index
				createIndex(project, true, new NullProgressMonitor());
				index = fIndexCache.getIndex(project.getName());
			}
		}
		return index;
	}
	
//	/**
//	 * @param file
//	 * @return
//	 */
//	public EObject getBpmnIndex(IFile file){
//		if (Job.getJobManager().isSuspended()) {
//			Job.getJobManager().resume();
//		}
//		BpmnIndexJob loadAllJob = (BpmnIndexJob) getExistingLoadAllJob();
//		if(loadAllJob != null) {
//			try {
//				loadAllJob.join();
//			} catch (InterruptedException e) {
//				BpmnCorePlugin.log(e);
//				return null;
//			}
//		}
//		return fIndexCache.get(file.getProject().getName());
//	}
	
	

	@Override
	public void done(IJobChangeEvent event) {
		Job job = event.getJob();
		BpmnCorePlugin.debug("finished job "+job);
		if (job instanceof LoadAllBpmnIndexesJob && job.getResult().isOK()) {
			fLoaded  = true;
//			resolveAllResources();
			BpmnModelCache.getInstance().allJobFinished();
			return;
		} else if (job instanceof CreateBpmnIndexJob) {
			CreateBpmnIndexJob createJob = (CreateBpmnIndexJob) job;
			IStatus result = event.getResult();
			if (result.getSeverity() == Status.OK && result instanceof IndexJobResult) {
				@SuppressWarnings("unchecked")
				IndexJobResult<EObject> status = (IndexJobResult<EObject>) result;
				Object jobData = status.getJobData();
				if (jobData instanceof EObject) {
					EObject eObj = (EObject) jobData;
					EcoreUtil.resolveAll(eObj);
					insertIndex(createJob.getProject(), eObj, true);
					
					// send an index update notification
					try {
						IExtensionRegistry reg = Platform.getExtensionRegistry();
						IConfigurationElement[] extensions = reg
						.getConfigurationElementsFor(BpmnCoreConstants.EXTENSION_POINT_INDEX_UPDATE);
						for (int i = 0; i < extensions.length; i++) {
							IConfigurationElement element = extensions[i];
							final Object o = element.createExecutableExtension(BpmnCoreConstants.EXTENSION_POINT_ATTR_INDEX);
							if (o instanceof IBpmnIndexUpdate) {
								((IBpmnIndexUpdate) o).onIndexUpdate(createJob.getProject(), eObj);
							}
						}
					} catch (Exception e) {
						// TODO: handle exception
					}
						
//					BpmnCorePlugin.getCache(createJob.getProject().getName());
//					LoadTnsCacheJob tnsCacheJob = new LoadTnsCacheJob(createJob.getProject());
//					tnsCacheJob.setProperty(tnsCacheJob.getKey(), eObj);
//					tnsCacheJob.setRule(new TnsCacheRule(BpmnCorePlugin.getCache(createJob.getProject().getName())));
//					tnsCacheJob.schedule();
//					Job.getJobManager().resume();
					return;
				}
			} else {
				BpmnCorePlugin.log(result);
			}
			// something went wrong with the create job, still need to remove the job from the
			// index cache to avoid deadlocks
			BpmnModelCache.getInstance().removeIndexJob(createJob.getProject().getName());
		} else if (job instanceof LoadBpmnIndexJob) {
			LoadBpmnIndexJob loadJob = (LoadBpmnIndexJob) job;
			IStatus result = event.getResult();
			if (result.getSeverity() == Status.OK && result instanceof IndexJobResult) {
				@SuppressWarnings("unchecked")
				IndexJobResult<EObject> status = (IndexJobResult<EObject>) result;
				Object jobData = status.getJobData();
				if (jobData instanceof EObject) {
					EObject eObj = (EObject) jobData;
					EcoreUtil.resolveAll(eObj);
					insertIndex(loadJob.getProject(),eObj, true);
//					LoadTnsCacheJob tnsCacheJob = new LoadTnsCacheJob(loadJob.getProject());
//					tnsCacheJob.setProperty(tnsCacheJob.getKey(), eObj);
//					tnsCacheJob.setRule(new TnsCacheRule(StudioCorePlugin.getCache(loadJob.getProject().getName())));
//					tnsCacheJob.schedule();
//					Job.getJobManager().resume();
					return;
				}else{
					// in case project doesn't contain any bpmn process
					BpmnModelCache.getInstance().removeIndexJob(loadJob.getProject().getName());
				}
			} else {
				BpmnCorePlugin.log(result);
			}
			// job was not properly loaded, create a new one.  
			createIndex(loadJob.getProject(), true, new NullProgressMonitor());
		} else {
			// something went wrong
			BpmnCorePlugin.log("Error in finished index job");
		}		
		
	}
	
	
	@SuppressWarnings("unused")
	private void resolveAllResources() {
		synchronized (fProjectResourceSetMap) {
			for(ResourceSet rset:fProjectResourceSetMap.values()){
				EcoreUtil.resolveAll(rset);
			}
		}
		
	}
	
	public void resolveAll(ResourceSet rest){
		if(rest != null){
			synchronized (rest) {
				List<Resource> resources = new ArrayList<Resource>(rest.getResources());
				for (Resource resource : resources) {
					if (resource != null)
						EcoreUtil.resolveAll(resource);
				}
			}
		}
	}

	protected void insertIndex(IProject project, EObject index,
			boolean overwrite) {
		synchronized (fIndexURICache) {
			fIndexURICache.put(project.getName(),URI.createFileURI(BpmnIndexUtils.getIndexLocation(project.getName())));	
			synchronized (fIndexCache) {
				fIndexCache.putIndex(project.getName(), index);	
//				synchronized (fProjectRootSymbolMap) {
//					RootSymbolMap rootSymbolMap = getRootSymbolMap(project.getName());
//					BpmnModelUtils.initRootSymbolMap(project.getName());
//				}
				synchronized(fProjectNameGenerator) {
//					BpmnNameGenerator nameGenerator = 
						getNameGenerator(project.getName());
				}
				return;
			}
		}
	}
	
	public void createIndex(IProject project, boolean wait,
			IProgressMonitor progressMonitor) {
		
		CreateBpmnIndexJob job = getExistingCreateJob(project);
		if (job != null) {
			try {
				if (wait) {
					Job.getJobManager().resume();
					job.join();
				}
			} catch (InterruptedException e) {
				BpmnCorePlugin.log(e);
			}
		} else {
//			if (!project.isOpen() || !BpmnProjectNatureManager.getInstance().isBpmnProject(project)) {
//				BpmnCorePlugin.debug("Project "+project.getName()+" is either not open or does not have the bpmn nature, skipping create");
//				return;
//			}

			job = new CreateBpmnIndexJob(project);
			job.setRule(new BpmnIndexRule(project.getName()));
			job.addJobChangeListener(this);
			BpmnCorePlugin.debug("scheduling create job for "+project.getName());
			BpmnModelCache.getInstance().addIndexJob(project.getName(),new BpmnProjectCacheLock(job));
			job.schedule();
			if (wait) {
				Job.getJobManager().resume();
				try {
					BpmnCorePlugin.debug("joining create job for "+project.getName());
					job.join();
				} catch (InterruptedException e) {
					BpmnCorePlugin.log(e);
				}
			}
		}
		
	}
	

	
	
	public boolean createIsScheduled(IProject project) {
		return getExistingCreateJob(project) != null;
	}
	
	
	public PersistBpmnIndexJob getExistingPersistJob(EObject index) {
		Job[] jobs = Job.getJobManager().find(index);
		for (int i = 0; i < jobs.length; i++) {
			if (jobs[i] instanceof PersistBpmnIndexJob) {
				return (PersistBpmnIndexJob) jobs[i];
			}
		}
		return null;
	}
	
	private CreateBpmnIndexJob getExistingCreateJob(IProject project) {
        // make sure we don't schedule a create job if the
        // project is already scheduled to be refreshed
        IJobManager jobManager = Job.getJobManager();
        Job[] jobsOnProject = jobManager.find(project);
        for (int i = 0; i < jobsOnProject.length; i++) {
            if (jobsOnProject[i] instanceof CreateBpmnIndexJob) {
                return (CreateBpmnIndexJob) jobsOnProject[i];
            }
        }
        return null;
    }

	/**
	 * @param project
	 * @return
	 */
	private Job getExistingIndexJob(IProject project) {
		// first, check for a scheduled LoadAllIndexesJob
		Job job = getExistingLoadAllJob();
		if (job != null) {
			return job;
		}
		
		if (!fLoaded) {
			loadAllIndexes();
		}
	
		// next, check for a LoadIndexJob or CreateIndexJob for this project
		// also check for a CreateIndexJob for this project in same loop
        IJobManager jobManager = Job.getJobManager();
        Job[] jobsOnProject = jobManager.find(project);
        for (int i = 0; i < jobsOnProject.length; i++) {
            if (jobsOnProject[i] instanceof CreateBpmnIndexJob || jobsOnProject[i] instanceof LoadBpmnIndexJob) {
                return jobsOnProject[i];
            }
        }
        return null;
	}

	/**
	 * @return
	 */
	private Job getExistingLoadAllJob() {

        IJobManager jobManager = Job.getJobManager();

        Job[] loadAllIndexesJobs = jobManager.find(BpmnModelManager.LOAD_ALL_BPMN_INDEX_JOB);
        for (int i = 0; i < loadAllIndexesJobs.length; i++) {
            if (loadAllIndexesJobs[i] instanceof LoadAllBpmnIndexesJob) {
                return loadAllIndexesJobs[i];
            }
        }
        return null;
    
	}
	
	
	/**
	 * 
	 */
	@SuppressWarnings("unchecked")
	public void loadAllIndexes() {
		BpmnCorePlugin.debug("loading all jobs");
		if (fLoaded) {
			return;
		}
		initializeIndexURIMap();
		IndexJob<EObject> loadAllJob = (IndexJob<EObject>) getExistingLoadAllJob();
		if (loadAllJob != null) {
			Job.getJobManager().resume();
			try {
				BpmnCorePlugin.debug("joining the existing load all job");
				loadAllJob.join();
			} catch (InterruptedException e) {
				BpmnCorePlugin.log(e);
			}
		} else {
			loadAllJob = new LoadAllBpmnIndexesJob();
			loadAllJob.addJobChangeListener(this);
			BpmnCorePlugin.debug("scheduling load all job");
			BpmnModelCache.getInstance().addIndexJob(BpmnModelCache.ALL_KEY,new BpmnProjectCacheLock(loadAllJob));
			loadAllJob.setRule(new LoadAllIndexSchedulingRule());
			loadAllJob.schedule();
			Job.getJobManager().resume();
		}

	}

	

	private void initializeIndexURIMap() {
		IProject[] projects = BpmnProjectNatureManager.getInstance().getAllBpmnProjects();
		synchronized (fIndexURICache) {
			for(IProject project:projects) {
					fIndexURICache.put(project.getName(),URI.createFileURI(BpmnIndexUtils.getIndexLocation(project.getName())));
			}
		}
		
	}

	/**
	 * @param project
	 * @return
	 */
	public boolean isIndexCached(IProject project) {
		return fIndexCache.isIndexCached(project.getName());
	}

	/* (non-Javadoc)
	 * @see org.eclipse.core.resources.ISaveParticipant#doneSaving(org.eclipse.core.resources.ISaveContext)
	 */
	@Override
	public void doneSaving(ISaveContext context) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see org.eclipse.core.resources.ISaveParticipant#prepareToSave(org.eclipse.core.resources.ISaveContext)
	 */
	@Override
	public void prepareToSave(ISaveContext context) throws CoreException {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see org.eclipse.core.resources.ISaveParticipant#rollback(org.eclipse.core.resources.ISaveContext)
	 */
	@Override
	public void rollback(ISaveContext context) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see org.eclipse.core.resources.ISaveParticipant#saving(org.eclipse.core.resources.ISaveContext)
	 */
	@Override
	public void saving(ISaveContext context) throws CoreException {
		context.needDelta(); // mark that we need resource deltas in case this participant is not registered before first delta on startup

		// persist all indexes
		saveAllIndexes();
		
	}
	
	/**
	 * 
	 */
	public void saveAllIndexes() {
		Collection<EObject> allIndexes = getAllLoadedIndexes();
		Object[] array = allIndexes.toArray();
		for (int i=0; i<array.length; i++) {
			saveIndex((EObject) array[i], true);
		}
	}
	
	
	/**
	 * @return
	 */
	public Collection<EObject> getAllLoadedIndexes() {
		Job loadAllJob = getExistingLoadAllJob();
		if (loadAllJob != null) {
			Job.getJobManager().resume();
			try {
				loadAllJob.join();
			} catch (InterruptedException e) {
				BpmnCorePlugin.log(e);
			}
		}
		return fIndexCache.values();
	}
	
	
	/**
	 * @param index
	 * @return
	 */
	public IStatus saveIndex(EObject index) {
		return saveIndex(index, false);
	}
	
	/**
	 * @param index
	 * @param runDirectly
	 * @return
	 */
	public IStatus saveIndex(EObject index, boolean runDirectly) {
		EObjectWrapper<EClass, EObject> indexWrapper = EObjectWrapper.wrap(index);
		
		if (indexWrapper.getAttribute(BpmnMetaModelConstants.E_ATTR_LAST_MODIFIED) != null && 
				indexWrapper.getAttribute(BpmnMetaModelConstants.E_ATTR_LAST_PERSISTED) != null 
				&& ((Date)indexWrapper.getAttribute(BpmnMetaModelConstants.E_ATTR_LAST_MODIFIED)).getTime() 
				   <= ((Date)indexWrapper.getAttribute(BpmnMetaModelConstants.E_ATTR_LAST_PERSISTED)).getTime()) {
			// no need to persist this index
			return Status.OK_STATUS;
		}
		if (getExistingPersistJob(index) != null) {
			BpmnCorePlugin.debug("persist job already scheduled, returning");
			return Status.OK_STATUS;
		}
		PersistBpmnIndexJob persistIndexJob = new PersistBpmnIndexJob(index);
		BpmnCorePlugin.debug("scheduling persist job");
		if (runDirectly) {
			persistIndexJob.runJob(new NullProgressMonitor());
		} else {
			persistIndexJob.schedule();
			Job.getJobManager().resume();
			try {
				persistIndexJob.join();
			} catch (InterruptedException e) {
				BpmnCorePlugin.log(e);
			}
		}
		return Status.OK_STATUS;
	}

	/**
	 * @param project
	 * @param removeIndexFile
	 * @param removeEntry TODO
	 * @return
	 */
	public boolean removeIndex(IProject project, boolean removeIndexFile, boolean removeEntry) {
		if (fIndexCache.containsKey(project.getName())) {
			EObject index = fIndexCache.remove(project.getName());
			index.eAdapters().clear();
			EObjectWrapper<EClass, EObject> iwrapper = EObjectWrapper.wrap(index);
			iwrapper.clearListAttribute(BpmnMetaModelConstants.E_ATTR_EXTENSIONS);
		}

		if (removeIndexFile) {
			removeIndexFile(project);
		}
		if(removeEntry) {
			synchronized (fIndexURICache) {
				fIndexURICache.remove(project.getName());
			}
			synchronized(fProjectResourceSetMap) {
				fProjectResourceSetMap.remove(project.getName());
			}
//			synchronized(fProjectRootSymbolMap){
//				fProjectRootSymbolMap.remove(project.getName());
//			}
			synchronized(fProjectNameGenerator) {
				fProjectNameGenerator.remove(project.getName());
			}
		}
		
		return true;
		
	}
	
	/**
	 * @param project
	 * @return
	 */
	private boolean removeIndexFile(IProject project) {
		 File indexFile = BpmnIndexUtils.getIndexFile(project.getName());
		if (indexFile.exists()) {
			return indexFile.delete();
		}
		return true;
	}
	
	/**
	 * @param project
	 * @return
	 */
	public boolean removeIndex(IProject project) {
		return removeIndex(project, true, false);
	}
	
	
	/**
	 * 
	 */
	public void rebuildAllIndexes() {
		IProject[] projects = ResourcesPlugin.getWorkspace().getRoot().getProjects();
		for (int i = 0; i < projects.length; i++) {
			removeIndex(projects[i]);
		}
		fLoaded = false;
		loadAllIndexes();
	}
	
	/**
	 * @param project
	 */
	public void rebuildIndex(IProject project) {
		fIndexCache.getIndex(project.getName());
		removeIndex(project);
//		EObject index = 
		loadIndex(project);
	}

	/**
	 * @param project
	 */
	public void waitOnIndexJobs(IProject project) {
		Job job = getExistingIndexJob(project);
		if (job != null) {
			try {
				job.join(); // wait for this job to complete before removing it
			} catch (InterruptedException e) {
				BpmnCorePlugin.log(e);
			}
		}
	}
	
	/**
	 * @param projectName
	 * @return
	 */
//	public RootSymbolMap getRootSymbolMap(String projectName) {
//		RootSymbolMap rootSymbolMap = null;
//		synchronized(fProjectRootSymbolMap) {
//			rootSymbolMap = fProjectRootSymbolMap.get(projectName);
//		}
//		
//		if(rootSymbolMap == null){
//			EObject index = getBpmnIndex(projectName);
//			rootSymbolMap = new ProjectSymbolMap(index);
//			synchronized(fProjectRootSymbolMap) {
//				fProjectRootSymbolMap.put(projectName, rootSymbolMap);
//			}
//		}
//		return rootSymbolMap;
//	}
//	
	/**
	 * @param project
	 * @return
	 */
	public BpmnNameGenerator getNameGenerator(IProject project) {
		return getNameGenerator(project.getName());
	}
	
	/**
	 * @param projectName
	 * @return
	 */
	public BpmnNameGenerator getNameGenerator(String projectName) {
		BpmnNameGenerator bpmnNameGenerator = null;
		bpmnNameGenerator = fProjectNameGenerator.get(projectName);			
		if(bpmnNameGenerator == null){
			EObject index = getBpmnIndex(projectName);
			bpmnNameGenerator = new BpmnNameGenerator(index);
			if(index !=null)
				fProjectNameGenerator.put(projectName, bpmnNameGenerator);
		}
		return bpmnNameGenerator;
	}
	
	
	
	/**
	 * returns 
	 * @param projectName
	 * @return
	 */
	public BpmnIndex getBpmnOntology(String projectName) {
		return new DefaultBpmnIndex(getBpmnIndex(projectName));
	}

	/**
	 * @deprecated use BpmnIndexUtils.getIndexLocationMap()
	 * @return project name to index file Location URI bidirectional map
	 */
	public BidiMap<String,URI> getProjectLocationMap() {
		return fIndexURICache;
	}
	
	public ResourceSet getModelResourceSet(IProject project) throws CoreException {
		ResourceSet rset = fProjectResourceSetMap.get(project.getName());
		if(rset == null){
			rset = ECoreHelper.createModelResourceSet(project);
			
			fProjectResourceSetMap.put(project.getName(), rset);
		}else{
			synchronized (rset) {
				ECoreHelper.refreshResourceSet(project, rset);
			}
		}
		return rset;
	}

	

}
