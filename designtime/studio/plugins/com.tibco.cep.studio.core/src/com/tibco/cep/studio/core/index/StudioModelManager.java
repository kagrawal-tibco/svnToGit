package com.tibco.cep.studio.core.index;

import java.io.File;
import java.util.Collection;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ISaveContext;
import org.eclipse.core.resources.ISaveParticipant;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.IJobChangeEvent;
import org.eclipse.core.runtime.jobs.IJobChangeListener;
import org.eclipse.core.runtime.jobs.IJobManager;
import org.eclipse.core.runtime.jobs.Job;

import com.tibco.cep.rt.LoadAllIndexSchedulingRule;
import com.tibco.cep.studio.common.StudioProjectCache;
import com.tibco.cep.studio.core.StudioCorePlugin;
import com.tibco.cep.studio.core.index.jobs.CreateIndexJob;
import com.tibco.cep.studio.core.index.jobs.IndexJob;
import com.tibco.cep.studio.core.index.jobs.IndexJobResult;
import com.tibco.cep.studio.core.index.jobs.LoadAllIndexesJob;
import com.tibco.cep.studio.core.index.jobs.LoadIndexJob;
import com.tibco.cep.studio.core.index.jobs.PersistIndexJob;
import com.tibco.cep.studio.core.index.jobs.StudioIndexRule;
import com.tibco.cep.studio.core.index.jobs.StudioProjectCacheLock;
import com.tibco.cep.studio.core.index.model.DesignerProject;
import com.tibco.cep.studio.core.index.utils.IndexUtils;
import com.tibco.cep.studio.core.util.CommonUtil;

public class StudioModelManager implements ISaveParticipant {

	class IndexJobChangeListener implements IJobChangeListener {
		
		public void sleeping(IJobChangeEvent event) {
		}
		public void scheduled(IJobChangeEvent event) {
		}
		public void running(IJobChangeEvent event) {
		}
		public void done(IJobChangeEvent event) {
			Job job = event.getJob();
			StudioCorePlugin.debug("finished job "+job);
			if (job instanceof LoadAllIndexesJob && job.getResult().isOK()) {
				fLoaded  = true;
				StudioProjectCache.getInstance().allJobFinished();
				return;
			} else if (job instanceof CreateIndexJob) {
				CreateIndexJob createJob = (CreateIndexJob) job;
				IStatus result = event.getResult();
				if (result.getSeverity() == Status.OK && result instanceof IndexJobResult) {
					IndexJobResult<DesignerProject> status = (IndexJobResult<DesignerProject>) result;
					Object jobData = status.getJobData();
					if (jobData instanceof DesignerProject) {
						insertIndex(createJob.getProject(), (DesignerProject)jobData, true);
						return;
					}
				}
				// something went wrong with the create job, still need to remove the job from the
				// index cache to avoid deadlocks
				StudioProjectCache.getInstance().removeIndexJob(createJob.getProject().getName());
			} else if (job instanceof LoadIndexJob) {
				LoadIndexJob loadJob = (LoadIndexJob) job;
				IStatus result = event.getResult();
				if (result.getSeverity() == Status.OK && result instanceof IndexJobResult) {
					IndexJobResult<DesignerProject> status = (IndexJobResult<DesignerProject>) result;
					Object jobData = status.getJobData();
					if (jobData instanceof DesignerProject) {
						insertIndex(loadJob.getProject(), (DesignerProject)jobData, true);
						return;
					}
				}
				// job was not properly loaded, create a new one.  
				createIndex(loadJob.getProject(), true, new NullProgressMonitor());
			} else {
				// something went wrong
				System.out.println("Error in finished index job");
			}
		}
		public void awake(IJobChangeEvent event) {
		}
		public void aboutToRun(IJobChangeEvent event) {
		}
	}
	
	public static final String LOAD_ALL_INDEX_JOB = "loadAllIndexesFamily";

	private static final String BUILD_EAR_APP_NAME = "com.tibco.cep.studio.cli.buildear";
	private static final String CLI_APP_NAME = "com.tibco.cep.studio.cli.studio-tools";
	private static final String APP_ARG = "-application";

	// whether or not all indexes have been loaded
	private boolean fLoaded = false;
	private StudioProjectCache fIndexCache = StudioProjectCache.getInstance();
	
	private IJobChangeListener fJobListener = new IndexJobChangeListener();

	public Collection<DesignerProject> getAllLoadedIndexes() {
		Job loadAllJob = getExistingLoadAllJob();
		if (loadAllJob != null) {
			Job.getJobManager().resume();
			try {
				loadAllJob.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		return fIndexCache.values();
	}
	
	public Collection<DesignerProject> getAllIndexes() {
		if (!fLoaded) {
			loadAllIndexes();
		}
		Job loadAllJob = getExistingLoadAllJob();
		if (loadAllJob != null) {
			Job.getJobManager().resume();
			try {
				loadAllJob.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		return fIndexCache.values();
	}
	
	public DesignerProject getIndex(String projectName) {
		try {
			IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(projectName);
			DesignerProject index = getIndex(project);
			if (index == null && isCLIMode()) {
				index = StudioProjectCache.getInstance().getIndex(projectName);
			}
			return index;
		}catch (IllegalStateException e) {
			// this is for commandline invocation when workspace is not available
			return StudioProjectCache.getInstance().getIndex(projectName);
		}
	}
	
	public DesignerProject getIndex(final IProject project) {
		if (!project.isAccessible() || !CommonUtil.isStudioProject(project)) {
			if (!isCLIMode()) {
				// if we're in command-line mode, then the project will not be accessible, but the index should still be available
				return null;
			}
		}
		if (Job.getJobManager().isSuspended()) {
			Job.getJobManager().resume();
		}
		if (!fIndexCache.isIndexCached(project.getName())) {
			StudioCorePlugin.debug("getting index for "+project.getName());
			Job indexJob = getExistingIndexJob(project);
			if (indexJob != null) {
				try {
					Job.getJobManager().resume();
					StudioCorePlugin.debug("joining index job for "+project.getName());
					indexJob.join();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			
			DesignerProject index = loadIndex(project, true);
			if (index != null) {
				return index;
			}
			
			// the index was not loaded, either due to an error
			// or because the index has not yet been created
			// Either way, create a new index
			createIndex(project, true, new NullProgressMonitor());
		}
		return fIndexCache.getIndex(project.getName());
	}

	public static boolean isCLIMode() {
		String[] commandLineArgs = Platform.getCommandLineArgs();
		if (commandLineArgs == null) {
			return false;
		}
		for (int i=0; i<commandLineArgs.length; i++) {
			if (APP_ARG.equalsIgnoreCase(commandLineArgs[i]) && i<commandLineArgs.length-1) {
				String appName = commandLineArgs[i+1];
				if (CLI_APP_NAME.equals(appName) || BUILD_EAR_APP_NAME.equals(appName)) {
					return true;
				}
			}
		}

		return false;
	}

	protected void insertIndex(IProject project, DesignerProject index,
			boolean overwrite) {
		synchronized (fIndexCache) {
			fIndexCache.putIndex(project.getName(), index);
			return;
		}
	}

	public boolean removeIndex(IProject project, boolean removeIndexFile) {
		if (fIndexCache.containsKey(project.getName())) {
			DesignerProject index = fIndexCache.remove(project.getName());
			index.eAdapters().clear();
		}
		if (removeIndexFile) {
			removeIndexFile(project);
		}
		
		return true;
	}
	
	public void rebuildAllIndexes() {
		IProject[] projects = CommonUtil.getAllStudioProjects();
		for (int i = 0; i < projects.length; i++) {
			removeIndex(projects[i]);
		}
		fLoaded = false;
		loadAllIndexes();
	}
	
	public void rebuildIndex(IProject project) {
		Job job = getExistingIndexJob(project);
		if (job != null) {
			try {
				job.join(); // wait for this job to complete before removing it
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		removeIndex(project);
		loadIndex(project);
	}
	
	private boolean removeIndexFile(IProject project) {
		File indexFile = IndexUtils.getIndexFile(project.getName());
		if (indexFile.exists()) {
			return indexFile.delete();
		}
		return true;
	}
	

	public boolean removeIndex(IProject project) {
		return removeIndex(project, true);
	}
	
	public IStatus saveIndex(DesignerProject index) {
		return saveIndex(index, false);
	}
	
	public void saveAllIndexes() {
		Collection<DesignerProject> allIndexes = getAllLoadedIndexes();
		Object[] array = allIndexes.toArray();
		for (int i=0; i<array.length; i++) {
			saveIndex((DesignerProject) array[i], true);
		}
	}
	
	public IStatus saveIndex(DesignerProject index, boolean runDirectly) {
		if (index.getLastModified() != null && index.getLastPersisted() != null 
				&& index.getLastModified().getTime() <= index.getLastPersisted().getTime()) {
			// no need to persist this index
			return Status.OK_STATUS;
		}
		if (getExistingPersistJob(index) != null) {
			StudioCorePlugin.debug("persist job already scheduled, returning");
			return Status.OK_STATUS;
		}
		PersistIndexJob persistIndexJob = new PersistIndexJob(index);
		StudioCorePlugin.debug("scheduling persist job");
		if (runDirectly) {
			persistIndexJob.runNow(new NullProgressMonitor());
		} else {
			persistIndexJob.schedule();
			Job.getJobManager().resume();
			try {
				persistIndexJob.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		return Status.OK_STATUS;
	}
	
	public void loadAllIndexes() {
		StudioCorePlugin.debug("loading all jobs");
		if (fLoaded) {
			return;
		}
		IndexJob loadAllJob = (IndexJob) getExistingLoadAllJob();
		if (loadAllJob != null) {
			Job.getJobManager().resume();
			try {
				StudioCorePlugin.debug("joining the existing load all job");
				loadAllJob.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		} else {
			loadAllJob = new LoadAllIndexesJob();
			loadAllJob.addJobChangeListener(fJobListener);
			StudioCorePlugin.debug("scheduling load all job");
			StudioProjectCache.getInstance().addIndexJob("#ALL",new StudioProjectCacheLock(loadAllJob));
			loadAllJob.setRule(new LoadAllIndexSchedulingRule());
			loadAllJob.schedule();
			Job.getJobManager().resume();
		}

	}
	
	public DesignerProject loadIndex(IProject project) {
		return loadIndex(project, false);
	}
	
	public DesignerProject loadIndex(final IProject project, boolean wait) {
		if (fIndexCache.isIndexCached(project.getName())) {
			return fIndexCache.getIndex(project.getName());
		}
		
		try {
			if (!project.isOpen() || !CommonUtil.isStudioProject(project)) {
				StudioCorePlugin.debug("Project "+project.getName()+" is either not open or does not have the studio nature, skipping load");
				return null;
			}
		} catch (Exception e) {
			return null;
		}

		// need to schedule and run a LoadIndexJob
		LoadIndexJob job = new LoadIndexJob(project);
		job.setRule(new StudioIndexRule(project.getName()));
		job.addJobChangeListener(fJobListener);
		StudioCorePlugin.debug("scheduling load job for "+project.getName());
//		IResourceRuleFactory ruleFactory = ResourcesPlugin.getWorkspace().getRuleFactory();
//		ISchedulingRule rule = ruleFactory.createRule(project);
//		job.setRule(rule);
		StudioProjectCache.getInstance().addIndexJob(project.getName(),new StudioProjectCacheLock(job));
		job.schedule();
		try {
			if (wait) {
				StudioCorePlugin.debug("joining load job for "+project.getName());
				Job.getJobManager().resume();
				job.join();
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return fIndexCache.getIndex(project.getName());
	}

	public void doneSaving(ISaveContext context) {
	}

	public void prepareToSave(ISaveContext context) throws CoreException {
	}

	public void rollback(ISaveContext context) {
	}

	public void saving(ISaveContext context) throws CoreException {
		context.needDelta(); // mark that we need resource deltas in case this participant is not registered before first delta on startup

		// persist all indexes
		saveAllIndexes();
	}

	public boolean isIndexCached(IProject project) {
		return fIndexCache.isIndexCached(project.getName());
	}

	public boolean createIsScheduled(IProject project) {
		return getExistingCreateJob(project) != null;
	}
	
	public PersistIndexJob getExistingPersistJob(DesignerProject index) {
		Job[] jobs = Job.getJobManager().find(index);
		for (int i = 0; i < jobs.length; i++) {
			if (jobs[i] instanceof PersistIndexJob) {
				return (PersistIndexJob) jobs[i];
			}
		}
		return null;
	}
	
	private CreateIndexJob getExistingCreateJob(IProject project) {
        // make sure we don't schedule a create job if the
        // project is already scheduled to be refreshed
        IJobManager jobManager = Job.getJobManager();
        Job[] jobsOnProject = jobManager.find(project);
        for (int i = 0; i < jobsOnProject.length; i++) {
            if (jobsOnProject[i] instanceof CreateIndexJob) {
                return (CreateIndexJob) jobsOnProject[i];
            }
        }
        return null;
    }

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
            if (jobsOnProject[i] instanceof CreateIndexJob || jobsOnProject[i] instanceof LoadIndexJob) {
                return jobsOnProject[i];
            }
        }
        return null;
	}

	private Job getExistingLoadAllJob() {

        IJobManager jobManager = Job.getJobManager();

        Job[] loadAllIndexesJobs = jobManager.find(StudioModelManager.LOAD_ALL_INDEX_JOB);
        for (int i = 0; i < loadAllIndexesJobs.length; i++) {
            if (loadAllIndexesJobs[i] instanceof LoadAllIndexesJob) {
                return loadAllIndexesJobs[i];
            }
        }
        return null;
    
	}

	public void createIndex(IProject project, boolean wait,
			IProgressMonitor progressMonitor) {
		
		CreateIndexJob job = getExistingCreateJob(project);
		if (job != null) {
			try {
				if (wait) {
					Job.getJobManager().resume();
					job.join();
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		} else {
			if (!project.isOpen() || !CommonUtil.isStudioProject(project)) {
				StudioCorePlugin.debug("Project "+project.getName()+" is either not open or does not have the studio nature, skipping create");
				return;
			}
			job = new CreateIndexJob(project);
			job.setRule(new StudioIndexRule(project.getName()));
//			IResourceRuleFactory ruleFactory = ResourcesPlugin.getWorkspace().getRuleFactory();
//			ISchedulingRule rule = ruleFactory.createRule(project);
//			job.setRule(rule);
			job.addJobChangeListener(fJobListener);
			StudioCorePlugin.debug("scheduling create job for "+project.getName());
			StudioProjectCache.getInstance().addIndexJob(project.getName(),new StudioProjectCacheLock(job));
			job.schedule();
			if (wait) {
				Job.getJobManager().resume();
				try {
					StudioCorePlugin.debug("joining create job for "+project.getName());
					job.join();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		
	}

	

}

