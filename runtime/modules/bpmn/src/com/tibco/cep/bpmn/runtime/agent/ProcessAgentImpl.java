package com.tibco.cep.bpmn.runtime.agent;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.tibco.cep.bpmn.runtime.agent.Job.PendingEvent;
import com.tibco.cep.bpmn.runtime.config.ProcessAgentConfiguration;
import com.tibco.cep.bpmn.runtime.model.JobContext;
import com.tibco.cep.bpmn.runtime.templates.CodegenTemplate;
import com.tibco.cep.bpmn.runtime.templates.ProcessTemplate;
import com.tibco.cep.bpmn.runtime.templates.ProcessTemplateManager;
import com.tibco.cep.bpmn.runtime.templates.ProcessTemplateTuple;
import com.tibco.cep.bpmn.runtime.utils.PendingEventHelper;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.runtime.model.TypeManager;
import com.tibco.cep.runtime.model.element.PropertyArrayString;
import com.tibco.cep.runtime.model.process.LoopTuple;
import com.tibco.cep.runtime.model.process.MergeTuple;
import com.tibco.cep.runtime.service.cluster.agent.AgentConfiguration;
import com.tibco.cep.runtime.service.cluster.agent.NewInferenceAgent;
import com.tibco.cep.runtime.service.cluster.om.DistributedCacheBasedStore;
import com.tibco.cep.runtime.service.om.api.ControlDao;
import com.tibco.cep.runtime.service.om.api.ControlDaoType;
import com.tibco.cep.runtime.service.om.api.DaoProvider;
import com.tibco.cep.runtime.service.om.api.EntityDao;
import com.tibco.cep.runtime.service.om.api.Filter;
import com.tibco.cep.runtime.service.om.api.FilterContext;
import com.tibco.cep.runtime.session.RuleServiceProvider;
import com.tibco.cep.runtime.session.locks.LockManager;

/*
* Author: Suresh Subramani / Date: 11/25/11 / Time: 7:41 PM
*
*/
public class ProcessAgentImpl extends NewInferenceAgent implements ProcessAgent {




    private ProcessTemplateManager processTemplateManager;
    private DefaultProcessExecutor processExecutor;
    private ControlDao<String, MergeTuple> mergeTable;
    private ControlDao<String, ProcessTemplateTuple> processTemplateTable;
    private ControlDao lockCache;
	private ControlDao<String, LoopTuple> loopCounterTable;



    public ProcessAgentImpl(AgentConfiguration config, RuleServiceProvider rsp, Type agentType) throws Exception {

        super(config, rsp, agentType);
        processTemplateManager = new ProcessTemplateManager();

        if (isCacheServer()) return;

        processExecutor = new DefaultProcessExecutor(rsp,(ProcessAgentConfiguration) config);
        rsp.getProperties().setProperty("be.ontology.concept.session.check", "false");
    }

    @Override
    protected void initActionManager() {
        actionMgr = new ProcessAgentActionManager();
    }

    @Override
    protected void onInit() throws Exception {

        super.onInit();


        mergeTable = this.cluster.getDaoProvider().createControlDao(String.class, MergeTuple.class, ControlDaoType.BPMN$MergeGatewayTable);
        processTemplateTable = this.cluster.getDaoProvider().createControlDao(String.class, ProcessTemplateTuple.class, ControlDaoType.BPMN$ProcessTemplateTable);
        this.lockCache = this.cluster.getDaoProvider().createControlDao(Object.class, Collection.class, ControlDaoType.ClusterLocks);
        this.loopCounterTable = this.cluster.getDaoProvider().createControlDao(String.class, LoopTuple.class, ControlDaoType.BPMN$LoopCounterTable);
        this.processTemplateManager.init(this, isCacheServer());

        if (isCacheServer()) return;

        logger.log(Level.INFO, String.format("Initializing ProcessAgentImpl:%s", this.name));
        ProcessRuleSession processRuleSession = (ProcessRuleSession) ruleSession;
        processRuleSession.init(this);

        processExecutor.init();
        getCluster().getLockManager().setContextResolver(new ProcessVariablesLockContextResolver());
        logger.log(Level.INFO, String.format("Initialized ProcessAgentImpl:%s", this.name));
    }

    @Override
    protected void onPrepareToActivate(boolean reactivate) throws Exception {

        super.onPrepareToActivate(reactivate);
    }

    @Override
    public void onRegister() throws Exception {

        mergeTable.start();
        processTemplateTable.start();
        this.lockCache.start();

        super.onRegister();

    }

    @Override
    protected void onPrepareToDeActivate() throws Exception {

    }

    @Override
    protected void onActivate(boolean reactivate) throws Exception {

        if (isCacheServer()) return;

        processExecutor.start();

        super.onActivate(reactivate);



        this.logger.log(Level.INFO, "Agent %s - %s: Resuming active jobs", this.getAgentName(), this.getAgentId());

        this.recover();

        this.logger.log(Level.INFO, "Agent %s - %s: Activated", this.getAgentName(), this.getAgentId());

    }


    @Override
    protected void onSuspend() throws Exception {
        super.onSuspend();
    }

    @Override
    protected void onResume() throws Exception {
        super.onResume();
    }

    @Override
    protected void onShutdown() throws Exception {
        super.onShutdown();
    }

    @Override
    protected void onDeactivate() throws Exception {
        super.onDeactivate();
    }



    public  void recover() throws Exception {

        if (this.getAgentState() != AgentState.PREPARETOACTIVATE) return;

        DaoProvider daoProvider = getCluster().getDaoProvider();
        TypeManager tm = getCluster().getRuleServiceProvider().getTypeManager();

        for (CodegenTemplate processTemplate : getDeployedProcessTemplates()) {


            EntityDao dao = daoProvider.getEntityDao(processTemplate.getTemplateClass());

            logger.log(Level.INFO, "Attempting to recover instances of [%s] process", processTemplate.getName());

            Set entrySet = dao.entrySet(new RunningProcessFinder(), Integer.MAX_VALUE);
            List<JobImpl> jobs = new ArrayList<JobImpl>();

            Map<Long,String> peMap = new HashMap<Long,String>();
            final DistributedCacheBasedStore objectStore = (DistributedCacheBasedStore) getRuleSession().getObjectManager();
            for (Iterator iterator = entrySet.iterator(); iterator.hasNext(); ) {
                Map.Entry entry = (Map.Entry) iterator.next();

                JobContext jobContext = (JobContext) entry.getValue();
                if (dao.lock(jobContext.getId(), 1)) {  //Try if you can lock, and if successfull, take the ownership of the jobcontext.
                	PropertyArrayString peMapArr= jobContext.getPendingEventMap();
                	peMap.clear();// reuse the map
    				PendingEventHelper.convertPropertyArrayToMap(peMapArr,peMap);
    				Map<String, Set<PendingEvent>> pendingEvents = PendingEventHelper.convertMapToPendingEvents(peMap,objectStore);
                	
                	JobImpl job = new JobImpl(jobContext,this);
                	job.setPendingEvents(pendingEvents);
                	jobs.add(job);
                }
            }

            logger.log(Level.INFO, "Identified [%d] instances of [%s] process to recover",
                    entrySet.size(), processTemplate.getName());

            for (JobImpl job : jobs) {
                processExecutor.submitJob(job);
            }

            logger.log(Level.INFO, "Recovered and scheduled [%d] instances of [%s] process",
                    entrySet.size(), processTemplate.getName());
        }
    }

	



    public ProcessAgentConfiguration getProcessAgentConfiguration() {
        return (ProcessAgentConfiguration) getAgentConfig();
    }

    @Override
    public List<ProcessTemplate> getDeployedProcessTemplates() {
        return processTemplateManager.getProcessTemplates();
    }

    @Override
    public ProcessExecutor getProcessExecutor() {
        return processExecutor;
    }

    @Override
    public ControlDao<String, MergeTuple> getMergeTableControl() {
        return mergeTable;
    }

    @Override
	public ControlDao<String, LoopTuple> getLoopCounterTuple() {
		return loopCounterTable;
	}

	@Override
    public ControlDao<String, ProcessTemplateTuple> getProcessTemplateTableControl() {
        return processTemplateTable;
    }

    @Override
    public ControlDao getClusterLocks() {
        return this.lockCache;
    }

    @Override
    public RuleServiceProvider getRuleServiceProvider() {
        return ruleSession.getRuleServiceProvider();
    }
    
    @Override
    public void releaseAllLocks() {
    	LockManager lockMgr = cluster.getLockManager();
        Collection<LockManager.LockData> locks = null;
        locks = lockMgr.takeLockDataStuckToThread();
    }

    //------------

    public static class RunningProcessFinder implements Filter {
        public RunningProcessFinder() {
        }

        @Override
        public boolean evaluate(Object o, FilterContext context) {
            JobContext jc = (JobContext) o;
            ProcessStatus status = jc.getProcessStatus();

            return status ==  ProcessStatus.RUNNING;
        }
    }
}
