package com.tibco.cep.bpmn.runtime.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.tibco.cep.bpmn.runtime.activity.Task;
import com.tibco.cep.bpmn.runtime.agent.ProcessStatus;
import com.tibco.cep.bpmn.runtime.templates.ProcessTemplate;
import com.tibco.cep.kernel.service.logging.LogManagerFactory;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.runtime.model.element.Concept;
import com.tibco.cep.runtime.model.element.Property;
import com.tibco.cep.runtime.model.element.PropertyArrayString;
import com.tibco.cep.runtime.model.element.impl.GeneratedConceptImpl;
import com.tibco.cep.runtime.model.element.impl.property.raw.PropertyArrayStringRaw;
import com.tibco.cep.runtime.model.element.impl.property.raw.PropertyAtomConceptReferenceRaw;
import com.tibco.cep.runtime.model.element.impl.property.raw.PropertyAtomIntRaw;

/**
 * Created by IntelliJ IDEA.
 * User: suresh
 * Date: Nov 19, 2011
 * Time: 7:57:45 AM
 *
 */

/**
 * This class represent the type information for a BPMN process.
 * Every BPMN process that is defined in the Studio, is converted into
 * java class of the form
 * <ProcessName>_<VersionInfo> extends AbstractProcess. example of this
 * public class CreateOrder_V1_1107 extends AbstractJobContext {
 *     Property[] variables = {}
 *     getter/setter
 * }
 *
 * The class name is the key to get to the ProcessTemplate.
 * A ProcessGraph is the definition/Type of the AbstractProcess, and an Instance of AbstractProcess points to the ProcessGraph
 * A ProcessGraph has a Nodes (Task/Activity) and Edges (Transition/SequenceFlows). Additionally it will have
 * Gateways which are specialized task.
 *
 * Every state variable that needs to be capture for the JobImpl is defined here
 * $1zprocessStatus (int) : ProcessStatus [0]
 * $1zlastTaskExecuted(short) : Last TaskId executed by the process. [1]
 * $1zparentProcessId(APV) : The parent process for this process [2]
 */

public abstract class AbstractJobContext extends GeneratedConceptImpl implements JobContext {

	static Logger logger = LogManagerFactory.getLogManager().getLogger(JobContext.class);
    transient private List<JobContext> forkedChildrens;

    //private AtomicInteger version = new AtomicInteger(0);
//    private ProcessStatus processStatus;
//
//    private AbstractJobContext parentProcess;
//    private Task lastTaskExecuted;

    transient private Task nextTask;

    PropertyAtomIntRaw processStatus = new PropertyAtomIntRaw(this) {
    	public String getName() {
    		return "$1zprocessStatus";
    	}
    };
    PropertyAtomIntRaw lastTaskExecuted = new PropertyAtomIntRaw(this) {
    	public String getName() {
    		return "$1zlastTaskExecuted";
    	}
    };
    PropertyAtomConceptReferenceRaw parentProcess = new PropertyAtomConceptReferenceRaw(this) {
        @Override
		public Class getType() {
			// Change this when setParent changes its instanceof clause
//			if (m_value != null) {
//				Job job = JobImpl.getCurrentJob();
//				if (job != null) {
//					try {
//						Cluster cluster = job.getProcessAgent().getCluster();
//						int typeId = cluster.getObjectTableCache().getById(getConceptId()).getTypeId();
//						return cluster.getMetadataCache().getClass(typeId);
//					} catch (Exception e) {
//						logger.log(Level.ERROR, String.format("Failed to get ObjectTable tuple for id:%d",getConceptId()),e);
//					}
//				}
//			}
			return JobContext.class;
		}

        @Override
    	public String getName() {
    		return "$1zparentProcess";
    	}
    };
    
    
    PropertyArrayStringRaw pendingEventMap = new PropertyArrayStringRaw(this,0) {
    	public String getName() {
    		return "$1zpendingEventMap";//<key:encoded(eventState,event_id)>=<value:event Task>
    	}
    };


    public AbstractJobContext(long _id) {
		super(_id);
	}

	public AbstractJobContext() {
	}

	public AbstractJobContext(long _id, String _extId) {
		super(_id,_extId);
	}
	
	


    @Override
    public void addChild(JobContext child) {
        if (forkedChildrens == null) {
            forkedChildrens = new ArrayList<JobContext>();
        }

        forkedChildrens.add(child);
        ((AbstractJobContext)child).setParent((Concept)this);
        setPersistenceModified();
    }
    
    
    public List<JobContext> getJobChildren() {
    	if(forkedChildrens == null){
    		forkedChildrens = new ArrayList<JobContext>();
    	}
    	return Collections.unmodifiableList(forkedChildrens);
    }



    public void setParent(Concept instance) {
        //Change getType for parentProcess inner class when JobContext changes to a different type.
        if (!(instance instanceof JobContext)) {
            throw new RuntimeException(String.format("Can't set %d as a parent, as its type is :%s, expected it to be %s", instance.getId(), instance.getClass(), this.getClass()));
        }
        parentProcess.setConcept(instance);
        setPersistenceModified();
    }

    public Concept getParent() {
        return parentProcess.getConcept();
    }
    
    public long getParentProcessId() {
    	if(parentProcess != null)
    		return parentProcess.getConceptId();
    	else 
    		return -1;
    }



    @Override
    public ProcessStatus getProcessStatus() {
        return ProcessStatus.valueOf(processStatus.getInt());


    }
    
    @Override 
    public PropertyArrayString getPendingEventMap() {
    	return pendingEventMap;
    }

    @Override
    public void setProcessStatus(ProcessStatus status) {
        this.processStatus.setInt(status.getStatusId());
        setPersistenceModified();
    }

    @Override
    public Task getLastTaskExecuted() {
        return getTask(lastTaskExecuted.getInt());
    }

    @Override
    public void setLastTaskExecuted(Task task) {

        this.lastTaskExecuted.setInt(task == null ? -1 : task.getIndex());
        setPersistenceModified();
    }

    @Override
    public void setNextTask(Task next) {
        this.nextTask = next;
        setPersistenceModified();
    }

    @Override
    public Task getNextTask() {
        return nextTask;
    }

    public Task getTask(int taskId) {
        if (taskId == -1) return null;
        ProcessTemplate template = getProcessTemplate();
        return template.getTask((short)taskId);

    }




    public abstract String getDesignTimeProcessUri() ;
    
    
    public abstract int getNumTasks() ;








    @Override
    public abstract String getType();


    @Override
    public Property getProperty(String name) {
        if ("$1zprocessStatus".equals(name)) return processStatus;
        if ("$1zlastTaskExecuted".equals(name)) return lastTaskExecuted;
        if ("$1zparentProcess".equals(name))  return parentProcess;
        if ("$1zpendingEventMap".equals(name)) return pendingEventMap;
        return super.getProperty(name);

    }

	public void setExtId(String extId, boolean init) {
		if(init) {
			this.extId = extId;
		} else {
			super.setExtId(extId);
		}
		
	}

//    @Override
//    public void setPropertyValue(String name, Object value) throws Exception {
//        if ("$1zprocessStatus".equals(name)) {
//            this.setProcessStatus(ProcessStatus.valueOf(((PropertyAtomInt) value).getInt()));
//        } else  if ("$1zlastTaskExecuted".equals(name)) {
//            this.setLastTaskExecuted(getTask(((PropertyAtomInt) value).getInt()));
//        } else if ("$1zparentProcess".equals(name)) {
//            if (value == null) return;
//            long pid = ((PropertyAtomLong)value).getLong();
//            this.setParent(deReference_Orig(pid, null, null, true));
//
//            //todo
//        } else {
//        	super.setPropertyValue(name, value);
//        }

//    }
}