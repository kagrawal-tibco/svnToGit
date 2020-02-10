package com.tibco.cep.bpmn.runtime.activity.tasks;

import java.util.List;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.InternalEObject;

import com.tibco.be.model.util.ModelNameUtil;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnMetaModelConstants;
import com.tibco.cep.bpmn.model.designtime.utils.EObjectWrapper;
import com.tibco.cep.bpmn.runtime.activity.InitContext;
import com.tibco.cep.bpmn.runtime.activity.Task;
import com.tibco.cep.bpmn.runtime.activity.TaskResult;
import com.tibco.cep.bpmn.runtime.activity.results.CallActivityResult;
import com.tibco.cep.bpmn.runtime.activity.results.ExceptionResult;
import com.tibco.cep.bpmn.runtime.agent.Job;
import com.tibco.cep.bpmn.runtime.agent.JobImpl;
import com.tibco.cep.bpmn.runtime.agent.ProcessAgent;
import com.tibco.cep.bpmn.runtime.model.JobContext;
import com.tibco.cep.bpmn.runtime.templates.MapperConstants;
import com.tibco.cep.bpmn.runtime.templates.ProcessTemplate;
import com.tibco.cep.bpmn.runtime.templates.ProcessTemplateRegistry;
import com.tibco.cep.bpmn.runtime.utils.FQName;
import com.tibco.cep.bpmn.runtime.utils.Variables;
import com.tibco.cep.designtime.model.Ontology;
import com.tibco.cep.designtime.model.process.ProcessModel;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.runtime.service.om.api.EntityDao;
import com.tibco.cep.studio.core.index.utils.CommonIndexUtils;

/**
 * @author pdhar
 *
 */
public class CallActivityTask extends AbstractTask {

    protected ProcessModel calledProcessModel;

    public CallActivityTask() {
        // TODO Auto-generated constructor stub
    }

    @Override
    public void init(InitContext context, Object... args) throws Exception {
        super.init(context, args);
        initCalledProcessModel();
    }
    
	protected void initCalledProcessModel() {
		EObjectWrapper<EClass, EObject> flowNodeWrapper = EObjectWrapper.wrap(getTaskModel().getEInstance());
		EObjectWrapper<EClass, EObject> callableElementWrapper = flowNodeWrapper.getWrappedEObjectAttribute(BpmnMetaModelConstants.E_ATTR_CALLED_ELEMENT);
		String elementFolder = callableElementWrapper.getAttribute(BpmnMetaModelConstants.E_ATTR_FOLDER);
		String elementName = callableElementWrapper.getAttribute(BpmnMetaModelConstants.E_ATTR_NAME);
		Ontology ontology = getInitContext().getProcessModel().getOntology();
		String path = elementFolder + "/" + elementName;
		path = path.replace("//", "/");
		this.calledProcessModel = (ProcessModel) ontology.getEntity(path);

		
		// Temporary defensive check to cover for Admin deployment case for Call Activity
        if (null == this.calledProcessModel) {
               EObject eObject = callableElementWrapper.getEInstance();
              
               String uri = null;
               URI proxyURI = null;
			if (eObject != null && eObject instanceof InternalEObject) {
				proxyURI = ((InternalEObject) eObject).eProxyURI();
				if (proxyURI == null) {
					return;
				}
				if (proxyURI.toFileString() != null) {
					uri = proxyURI.toFileString();
				} else if (proxyURI.devicePath() != null) {
					uri = proxyURI.devicePath();
				} else if (proxyURI.toString() != null) {
					uri = proxyURI.toString();
					if(uri.contains("#"))
						uri = uri.substring(0, uri.lastIndexOf("#"));
				}

			}
              
               if (uri != null && !uri.isEmpty()) {
                     ProcessModel processModel = getCalledProcessByUri(ontology, uri);
                     if (processModel != null) {
                            this.calledProcessModel = processModel;
                     }
               }
        }

	}

    /**
     * @return
     */
    public ProcessModel getCalledProcess() {
        return calledProcessModel;
    }

  
    protected ProcessModel getCalledProcessByUri(Ontology ontology, String uri) {
        if (uri.endsWith(CommonIndexUtils.PROCESS_EXTENSION)) {
            int index = uri.indexOf(CommonIndexUtils.PROCESS_EXTENSION);
            uri = uri.substring(0, index - 1);
        }
        ProcessModel procModel = (ProcessModel) ontology.getEntity(uri);
        return procModel;
    }

    protected Task getStartTask(ProcessModel pmodel) {
        final ProcessTemplate processTemplate = getProcessTemplateFromModel(pmodel);
        List<Task> starterTasks = processTemplate.getStarterTask();
        if (!starterTasks.isEmpty()) {
            return starterTasks.get(0);
        }
        return null;
    }

    /**
     * @param pmodel
     * @return
     */
    protected ProcessTemplate getProcessTemplateFromModel(ProcessModel pmodel) {
        final FQName fqName = FQName.makeName(pmodel.getFullPath(), pmodel.getRevision());
        final String processName = ModelNameUtil.modelPathToGeneratedClassName(fqName.getName());
        final ProcessTemplate processTemplate = ProcessTemplateRegistry.getInstance().getProcessTemplate(processName);
        return processTemplate;
    }

    /*
      * (non-Javadoc)
      *
      * @see
      * com.tibco.cep.bpmn.runtime.activity.Task#execute(com.tibco.cep.bpmn.runtime
      * .agent.Job)
      */
    @Override
    public TaskResult execute(Job parentJob, Variables vars, Task loopTask) {
    	
    	TaskResult result = null;
        final ProcessAgent pac = getInitContext().getProcessAgent();
        final Ontology ontology = pac.getRuleServiceProvider().getProject().getOntology();
        String calledProcessUri = (String) vars.getVariable(MapperConstants.PROCESS_URI);
        ProcessModel overideProcessModel = null;
        try {


            overideProcessModel = calledProcessUri == null ? this.calledProcessModel : getCalledProcessByUri(ontology, calledProcessUri);
            ProcessTemplate template = ProcessTemplateRegistry.getInstance().getProcessTemplateFromURI(ontology, overideProcessModel.getFullPath());

            // call activity input transform should create process variables
            JobContext childContext = vars.containsKey(MapperConstants.PROCESS) ?
                    (JobContext) vars.getVariable(MapperConstants.PROCESS)
                    : template.newProcessData();



            Task task = getStartTask(overideProcessModel);
            if (task == null) {
            	result = new ExceptionResult(String.format("Process definition[%s] does not have a Start Task. Check your model", overideProcessModel.getFullPath()));
            } else {
            	/**
            	 * The parentJob does not go further after the child job is launched due to result mode CONTINUE_ASYNC_ANDSTOP_CURRENT
            	 * therefore this job needs to be cleaned up at checkpoint
            	 */
            	parentJob.getJobContext().addChild(childContext);
            	parentJob.getJobContext().setLastTaskExecuted(this);
				
            	// save the parent job for later(push to cache) when the child job finishes it will wake up the parent
            	// the cleanup order is child cleans up itself on completion and transfers control back to parent
            	// which then cleans up itself on completion and transfers control back in a stacked pop fashion
                JobImpl childJob = new JobImpl(childContext, pac);
                if(loopTask != null) {
                	// in case of a looped call activity/sub process
                	// pending events needs to be transferred to the LoopTaskContext once
                	// and transferred back to the job once the loop ends.
                	if(((DefaultLoopTask)loopTask).getPendingEvents() == null) {
                		((DefaultLoopTask)loopTask).setPendingEvents(parentJob.getPendingEvents());
                	}
                	
                } else {
                	
                	// copy pending events when changing jobs
                	// this is possible because the execution order is parent->child->parent
                	// and the context can be transferred back
                	childJob.setPendingEvents(parentJob.getPendingEvents());
                }

                EntityDao dao = pac.getCluster().getDaoProvider().getEntityDao(childContext.getClass());
                dao.lock(childContext.getId(), -1);
                
                childJob.setCurrentTask(this);

                // jump to the start task
                childJob.setLastTask(task);
                result = new CallActivityResult(childJob);
            }            

        } catch (Throwable e) {
            logger.log(Level.ERROR, "Exception processing the CallActivity ", e);
            result =  new ExceptionResult(e);
        }

        return result;  // needed for debugger exit task notification
    }



}
