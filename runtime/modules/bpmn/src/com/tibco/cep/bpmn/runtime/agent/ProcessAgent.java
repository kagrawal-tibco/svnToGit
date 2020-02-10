package com.tibco.cep.bpmn.runtime.agent;

import java.util.List;

import com.tibco.cep.bpmn.runtime.config.ProcessAgentConfiguration;
import com.tibco.cep.bpmn.runtime.templates.ProcessTemplate;
import com.tibco.cep.bpmn.runtime.templates.ProcessTemplateTuple;
import com.tibco.cep.runtime.model.process.LoopTuple;
import com.tibco.cep.runtime.model.process.MergeTuple;
import com.tibco.cep.runtime.service.cluster.Cluster;
import com.tibco.cep.runtime.service.om.api.ControlDao;
import com.tibco.cep.runtime.session.RuleServiceProvider;
import com.tibco.cep.runtime.session.RuleSession;

/*
* Author: Suresh Subramani / Date: 12/10/11 / Time: 9:35 AM
*/
public interface ProcessAgent {

    RuleServiceProvider getRuleServiceProvider();

    ProcessAgentConfiguration getProcessAgentConfiguration();
    
    RuleSession getRuleSession();

    List<ProcessTemplate> getDeployedProcessTemplates();

    ProcessExecutor getProcessExecutor();

    ControlDao<String, MergeTuple> getMergeTableControl();
    
    ControlDao<String, LoopTuple> getLoopCounterTuple();

    ControlDao<String, ProcessTemplateTuple> getProcessTemplateTableControl();

    ControlDao getClusterLocks();

    Cluster getCluster();
    
    void releaseAllLocks();

    interface ProcessExecutor {
        void submitJob(Job job);


    }
}
