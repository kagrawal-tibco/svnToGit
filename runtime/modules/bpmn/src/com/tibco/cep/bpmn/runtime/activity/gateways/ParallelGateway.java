package com.tibco.cep.bpmn.runtime.activity.gateways;


import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;

import com.tibco.be.functions.object.ObjectHelper;
import com.tibco.be.functions.xpath.JXPathHelper;
import com.tibco.cep.bpmn.model.designtime.extension.ExtensionHelper;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnMetaModelExtensionConstants;
import com.tibco.cep.bpmn.model.designtime.utils.EObjectWrapper;
import com.tibco.cep.bpmn.runtime.activity.InitContext;
import com.tibco.cep.bpmn.runtime.activity.Task;
import com.tibco.cep.bpmn.runtime.activity.TaskResult;
import com.tibco.cep.bpmn.runtime.activity.Transition;
import com.tibco.cep.bpmn.runtime.activity.results.DefaultResult;
import com.tibco.cep.bpmn.runtime.activity.results.ExceptionResult;
import com.tibco.cep.bpmn.runtime.activity.results.ForkResult;
import com.tibco.cep.bpmn.runtime.activity.results.MergeCompleteResult;
import com.tibco.cep.bpmn.runtime.agent.Job;
import com.tibco.cep.bpmn.runtime.agent.JobImpl;
import com.tibco.cep.bpmn.runtime.agent.ProcessAgent;
import com.tibco.cep.bpmn.runtime.agent.ProcessStatus;
import com.tibco.cep.bpmn.runtime.model.AbstractJobContext;
import com.tibco.cep.bpmn.runtime.model.JobContext;
import com.tibco.cep.bpmn.runtime.templates.ProcessTemplate;
import com.tibco.cep.bpmn.runtime.utils.Variables;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.repo.GlobalVariables;
import com.tibco.cep.runtime.model.element.impl.BaseGeneratedConceptImpl;
import com.tibco.cep.runtime.model.process.MergeTuple;
import com.tibco.cep.runtime.model.process.MergeTuple.MergeEntry;
import com.tibco.cep.runtime.service.cluster.Cluster;
import com.tibco.cep.runtime.service.cluster.system.ClusterIdGenerator;
import com.tibco.cep.runtime.service.om.api.ControlDao;
import com.tibco.cep.runtime.service.om.api.DaoProvider;
import com.tibco.cep.runtime.service.om.api.EntityDao;
import com.tibco.cep.runtime.service.om.api.Filter;
import com.tibco.cep.runtime.service.om.api.FilterContext;
import com.tibco.cep.runtime.session.RuleSession;
import com.tibco.cep.runtime.session.RuleSessionManager;
import com.tibco.cep.runtime.session.impl.locks.AbstractConcurrentLockManager;
import com.tibco.cep.runtime.session.locks.LockManager.LockLevel;

/**
 * ({@link com.tibco.cep.bpmn.runtime.model.JobContext} is treated as Object/job)
 * <p/>
 * 1. Join RuleFunction:
 * <p/>
 * Parameters - (Object[] jobs, String[] transitionNames, String mergeKey)
 * <p/>
 * Return value - Object/job. Cannot be null.
 * <p/>
 * 2. Split RuleFunction:
 * <p/>
 * Parameters - (Object job, String transitionName).
 * <p/>
 * Return value - Object/job. Can be null.
 * <p/>
 * <p/>
 *
 * @author pdhar
 */
public class ParallelGateway extends AbstractGateway {
    final LocalLockManager localLockManager;

    public ParallelGateway() {
        this.localLockManager = new LocalLockManager();
    }

    @Override
    public void init(InitContext context, Object... args) throws Exception {
        super.init(context, args);

        EObjectWrapper<EClass, EObject> flowNodeWrapper = EObjectWrapper.wrap(getTaskModel().getEInstance());
        EObjectWrapper<EClass, EObject> valueWrapper = ExtensionHelper.getAddDataExtensionValueWrapper(flowNodeWrapper);

        forkFunctionURI = valueWrapper.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_FORK_RULEFUNCTION);
        if (forkFunctionURI != null && forkFunctionURI.length() == 0) {
            forkFunctionURI = null;
        }
        joinFunctionURI = valueWrapper.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_JOIN_RULEFUNCTION);
        if (joinFunctionURI != null && joinFunctionURI.length() == 0) {
            joinFunctionURI = null;
        }
    }

    @Override
    protected void initForkTransforms() throws Exception {
        forkCopy = true; // using blind copy of the  job
    }

    @Override
    protected void initJoinTransforms() throws Exception {
        joinCopy = false; // using a merge function
    }

    @Override
    public TaskResult execute(Job job, Variables vars, Task loopTask) {
        //synchronized(ParallelGateway.class) {
        if (logger.isEnabledFor(LEVEL)) {
            logger.log(LEVEL, "Inside " + this + " : " + vars
                    + (job == null ? "" : " : " + job.getTransitionName() + " : " + job));
        }

        try {
            TaskResult result = join(job, vars,false);

            if (result.getStatus() == TaskResult.Status.MERGECOMPLETE && this.getOutputTokenCount() > 1) {

                result = split(((MergeCompleteResult) result).getMergedJob());

            }

            if (logger.isEnabledFor(LEVEL)) {
                logger.log(LEVEL, "Leaving " + this + " : " + vars
                        + (job == null ? "" : " : " + job.getTransitionName()) + " with " + result + " : " + job);
            }

            return result;
        }
        catch (Throwable e) {
            if (logger.isEnabledFor(LEVEL)) {
                logger.log(LEVEL, "Leaving " + this + " : " + vars
                        + (job == null ? "" : " : " + job.getTransitionName()) + " with " + e + " : " + job);
            }

            return new ExceptionResult(e);
        }
        //}

    }

    public TaskResult join(Job job, Variables vars,boolean isError) throws Exception {

        int activationCount = getActivationCount();
        if (activationCount == 0) {
            throw new Exception(String.format("Invalid Gateway specified : %s", getName()));
        }
        if (activationCount == 1) {
            if (logger.isEnabledFor(LEVEL)) {
                logger.log(LEVEL, "Leaving " + this + " : " + vars
                        + (job == null ? "" : " : " + job.getTransitionName()) + " join with activation count 1");
            }

            return new MergeCompleteResult(job);
        }

        ProcessAgent pac = this.getInitContext().getProcessAgent();
        ControlDao<String, MergeTuple> mergeTable = pac.getMergeTableControl();

        String mergeKey = makeJoinKey(job);

        if (logger.isEnabledFor(LEVEL)) {
            logger.log(LEVEL, "Calling " + this + " : " + vars
                    + (job == null ? "" : " : " + job.getTransitionName()) + " join with merge key : " + mergeKey);
        }

        MergeTuple mergeTuple = null;
//        while (!mergeTable.lock(mergeKey, 500)) {
//        }

        localLockManager.lock(mergeKey, Integer.MAX_VALUE, LockLevel.LEVEL1);
        ControlDao clusterLocks = pac.getClusterLocks();
        clusterLocks.lock(mergeKey, Long.MAX_VALUE);

        try {
            mergeTuple = mergeTable.get(mergeKey);
            if (mergeTuple == null) {
                mergeTuple = new DefaultMergeTuple(mergeKey, this.getActivationCount());
                ((JobImpl)job).recordControlDaoTuple(mergeTuple);
            }

            mergeTuple.merge(job.getJobContext().getId(), job.getTransitionName(),isError);

            if (mergeTuple.isComplete()) {
            	mergeTuple.setComplete();
                JobContext mergedChild = doJoin(job, vars, mergeKey, mergeTuple);
                boolean isNewJobContext = true;
                Collection<JobContext> parentJobs = new HashSet<JobContext>();
                // record for cleanup Job contexts that are not going forward
                for (MergeEntry mergeEntry : mergeTuple.getMergeEntries()) {
                    JobContext mc = (JobContext) ObjectHelper.getById(mergeEntry.getProcessId());
                    if(!mc.equals(mergedChild)){
                    	((Job)job).recordCompletedJob(this, mc);
                    } else {
                    	if(isNewJobContext) {
                    		isNewJobContext = false;
                    	}
                    }
                    parentJobs.add((JobContext) mc.getParent());
                    
                }
				for (JobContext pjc : parentJobs) {
					if (pjc != null && pjc.getId() != mergedChild.getId()) {
						((Job) job).recordCompletedJob(this, pjc);
					}
				}
                
                
                ((JobImpl)job).recordControlDaoTuple(mergeTuple);
                mergeTable.remove(mergeKey);

                if (logger.isEnabledFor(LEVEL)) {
                    logger.log(LEVEL, "Called " + this + " : " + vars
                            + (job == null ? "" : " : " + job.getTransitionName())
                            + " join after completing merge : " + mergeKey + " : " + mergedChild);
                }
                EntityDao dao = pac.getCluster().getDaoProvider().getEntityDao(mergedChild.getClass());
                dao.lock(mergedChild.getId(), -1);

                JobImpl jobImpl = new JobImpl(mergedChild, pac);
                jobImpl.setCurrentTask(this);
                jobImpl.setLastTask(this);
                if(isNewJobContext) {
                	jobImpl.checkpoint(false); // checkpoint a new merged job context
                }

                return new MergeCompleteResult(jobImpl);

            }
            else {
                mergeTable.put(mergeKey, mergeTuple);
                ((JobImpl)job).recordControlDaoTuple(mergeTuple);
            }
        }
        finally {
        	// Need to checkpoint to save the merge tuple to cache and backing store
        	//job.checkpoint(false);
            //mergeTable.unlock(mergeKey);
            clusterLocks.unlock(mergeKey);
            //logger.log(Level.INFO, String.format("Unlock for key %s", mergeKey));

            localLockManager.unlock(mergeKey);
        }

        if (logger.isEnabledFor(LEVEL)) {
            logger.log(LEVEL, "Called " + this + " : " + vars
                    + (job == null ? "" : " : " + job.getTransitionName())
                    + " waiting for join : " + mergeKey + " : " + mergeTuple);
        }

        return new DefaultResult(TaskResult.Status.WAITFORJOIN, null);
    }

    String makeJoinKey(Job context) {
        JobContext job = context.getJobContext();
        GlobalVariables globalVariables = context.getProcessAgent().getRuleServiceProvider().getGlobalVariables();

        return JXPathHelper.evalXPathAsString(getJoinExpression(), new String[]{"job", "globalVariables"},
                new Object[]{job, globalVariables});
    }

    JobContext doJoin(Job context, Variables vars, String mergeKey, MergeTuple mergeTuple) {
        Object[] jobs = new Object[mergeTuple.getTokenCount()];
        String[] transitionNames = new String[jobs.length];
        int i = 0;

        RuleSession processRS = context.getProcessAgent().getRuleSession();
        RuleSession origRS = RuleSessionManager.getCurrentRuleSession();
        RuleSessionManager.currentRuleSessions.set(processRS);
        try {
            for (MergeEntry mergeEntry : mergeTuple.getMergeEntries()) {
            	jobs[i] = ObjectHelper.getById(mergeEntry.getProcessId());
            	transitionNames[i] = mergeEntry.getTransitionName();
            	if(mergeEntry.hasError()) {
            		// set the jobContext process status propertyAtomInt to ERROR 
            		// which can be checked by Reflection Property API
            		((AbstractJobContext)jobs[i]).setProcessStatus(ProcessStatus.ERROR);
            	}
            	i++;
            }
        }
        finally {
            RuleSessionManager.currentRuleSessions.set(origRS);
        }

        return invokeJoinFunction(context, vars, mergeKey, jobs, transitionNames);
    }

    JobContext invokeJoinFunction(Job context, Variables vars, String mergeKey,
                                  Object[] jobs, String[] transitionNames) {
        RuleSession ruleSession = context.getProcessAgent().getRuleSession();

        if (logger.isEnabledFor(LEVEL)) {
            logger.log(LEVEL, "Calling " + this + " : " + vars + " : "
                    + context.getTransitionName() + " join function : " + mergeKey + " : " + joinFunctionURI);
        }

        Map transtionNames2Job = new HashMap();
        for (int i = 0; i < jobs.length; i++) {
            transtionNames2Job.put(transitionNames[i], jobs[i]);
        }

        JobContext pv = (JobContext) ruleSession
                .invokeFunction(joinFunctionURI, new Object[]{transtionNames2Job, mergeKey}, true);

        if (logger.isEnabledFor(LEVEL)) {
            logger.log(LEVEL, "Called " + this + " : " + vars + " : "
                    + context.getTransitionName() + " join function : " + mergeKey + " : " + pv);
        }

        return pv;
    }

    /**
     * @param mergedJob
     * @return
     * @throws Exception
     */
    TaskResult split(Job mergedJob) throws Exception {

        Transition transitions[] = this.getOutgoingTransitions();


        Cluster cluster = getInitContext().getProcessAgent().getCluster();
        ClusterIdGenerator idGenerator = cluster.getIdGenerator();
        if(hasChildSplits(mergedJob)){
        	return new ForkResult(java.util.Collections.EMPTY_LIST);
        }
        List<Job> processes = new ArrayList<Job>();
        for (Transition t : transitions) {
            JobContext processData = invokeSplitFunction(mergedJob, t.getName());
            if (processData == null) {
            	long id = idGenerator.nextEntityId(mergedJob.getJobContext().getClass());
                processData = (JobContext) ((BaseGeneratedConceptImpl)mergedJob.getJobContext()).duplicateThis(id,null);

            }

            //((AbstractJobContext) processData).setId(idGenerator.nextEntityId(processData.getClass()));
            /**
        	 * The parentJob does not go further after the child job is launched due to result mode CONTINUE_ASYNC_ANDSTOP_CURRENT
        	 * therefore this job needs to be cleaned up at checkpoint
        	 */
            /**
             * Do not maintain parent child relationship in parallel gw because during merge the parent could be a simple jobcontext instead of a
             * split jobcontext and tracing backwards to clean up finished jobs does not work.
             */
        	//mergedJob.getJobContext().addChild(processData);
        	//mergedJob.getJobContext().setLastTaskExecuted(this);
			
            ((AbstractJobContext) processData).setParent(mergedJob.getJobContext());
            EntityDao dao = cluster.getDaoProvider().getEntityDao(processData.getClass());
            dao.lock(processData.getId(), -1);

            JobImpl p = new JobImpl(processData, this.getInitContext().getProcessAgent());
            p.setLastTask(null);
            p.setNextTask(t.toTask());
            p.setTransitionName(t.getName());

            processes.add(p);
        }

        if (logger.isEnabledFor(LEVEL)) {
            logger.log(LEVEL, "Called " + this + " : " + mergedJob.getTransitionName()
                    + " split : " + processes);
        }
        boolean reusingMergedJob = false;
        for(Job p:processes){
           JobContext jc = p.getJobContext();	
           // check if the split resulted in returning merged job data
           if(jc.equals(mergedJob)){
        	   reusingMergedJob=true;
        	   break;
           }
        }
//        if(!reusingMergedJob) {
//        	 merged job is not being re-used. it ends here
//        	if(JobImpl.getCurrentJob().getJobContext().equals(mergedJob.getJobContext())){
//        		mergedJob.getJobContext().setProcessStatus(ProcessStatus.COMPLETE);
//        	} else {
//        		mergedJob.recordCompletedJob(this, mergedJob.getJobContext());
//        	}
//        	mergedJob.checkpoint(true);
//        }
        mergedJob.checkpoint(true);
        for (Job process : processes) {
            process.checkpoint(true);  //Suresh TODO
        }

        return new ForkResult(processes);
    }

    /**
     * @param mergedJob
     * @param transitionName
     * @return Can be null.
     */
    JobContext invokeSplitFunction(Job mergedJob, String transitionName) {
        if (forkFunctionURI == null) {
            return mergedJob.getJobContext();
        }

        RuleSession ruleSession = mergedJob.getProcessAgent().getRuleSession();
        JobContext context = mergedJob.getJobContext();

        if (logger.isEnabledFor(LEVEL)) {
            logger.log(LEVEL, "Calling " + this + " : "
                    + mergedJob.getTransitionName() + " split function : " + forkFunctionURI + " : " + transitionName);
        }

        JobContext pv =
                (JobContext) ruleSession.invokeFunction(forkFunctionURI, new Object[]{context, transitionName}, true);

        if (logger.isEnabledFor(LEVEL)) {
            logger.log(LEVEL, "Called " + this + " : "
                    + mergedJob.getTransitionName() + " split function : " + forkFunctionURI + " : " + pv);
        }

        return pv;
    }
    //-------------- Child split finder
	boolean hasChildSplits(Job job) throws Exception {
		ProcessTemplate processTemplate = job.getJobContext().getProcessTemplate();
		// Get the Event entity Dao
		DaoProvider daoProvider = job.getProcessAgent().getCluster().getDaoProvider();

		EntityDao dao = daoProvider.getEntityDao(processTemplate.getTemplateClass());

		Set entrySet = dao.entrySet(new SplitJobFinder(job.getJobContext().getId()), Integer.MAX_VALUE);
		logger.log(Level.INFO, "Found [%d] child split instances of [%s] process",entrySet.size(), processTemplate.getName());
		return !entrySet.isEmpty();
		
	}
	
	static class SplitJobFinder implements Filter{
		 private long parentId;

		public SplitJobFinder(long parentId) {
			 SplitJobFinder.this.parentId = parentId;
	        }

	        @Override
	        public boolean evaluate(Object o, FilterContext context) {
	            AbstractJobContext jc = (AbstractJobContext) o;
	            if(jc.getParentProcessId() != -1) {
	            	long pid = jc.getParentProcessId();
	                return pid == parentId;
	            } 

	            return false;
	        }
	}

    //---------------

    static class LocalLockManager extends AbstractConcurrentLockManager<String, Object> {
        LocalLockManager() {
            super(false, new DummyLockKeeper<String, Object>());
        }

        @Override
        protected InvalidatableLock handleAbsentLock(String key) {
            //Create a dummy lock.
            InvalidatableLock lock = new InvalidatableLock(LockLevel.LEVEL1);

            //Pretend that it is locked.
            try {
                lock.acquire();
            }
            catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            return lock;
        }
    }
}
