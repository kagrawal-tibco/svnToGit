package com.tibco.cep.bpmn.runtime.activity.tasks;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnumLiteral;
import org.eclipse.emf.ecore.EObject;

import com.tibco.be.util.XSTemplateSerializer;
import com.tibco.cep.bpmn.model.designtime.extension.ExtensionHelper;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnMetaModelConstants;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnMetaModelExtensionConstants;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnModelClass;
import com.tibco.cep.bpmn.model.designtime.utils.EObjectWrapper;
import com.tibco.cep.bpmn.model.designtime.utils.ROEObjectWrapper;
import com.tibco.cep.bpmn.runtime.activity.InitContext;
import com.tibco.cep.bpmn.runtime.activity.Task;
import com.tibco.cep.bpmn.runtime.activity.TaskFactory;
import com.tibco.cep.bpmn.runtime.activity.TaskRegistry;
import com.tibco.cep.bpmn.runtime.activity.TaskResult;
import com.tibco.cep.bpmn.runtime.activity.Transition;
import com.tibco.cep.bpmn.runtime.activity.mapper.Mapper;
import com.tibco.cep.bpmn.runtime.agent.Job;
import com.tibco.cep.bpmn.runtime.agent.ProcessException;
import com.tibco.cep.bpmn.runtime.model.JobContext;
import com.tibco.cep.bpmn.runtime.utils.Constants;
import com.tibco.cep.bpmn.runtime.utils.Variables;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.LogManagerFactory;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.xml.datamodel.XiNode;

/*
 * Author: Suresh Subramani / Date: 12/11/11 / Time: 11:47 AM
 */
public abstract class AbstractTask implements Task {

	protected static Logger logger = LogManagerFactory.getLogManager().getLogger(Task.class);
    protected static Level LEVEL = Constants.DEBUG ? Level.WARN : Level.DEBUG;

    protected InitContext initContext;
    protected String type;
    protected String name;
    protected Transition[] incomingTransitions;

    protected Transition[] outgoingTranisitions;
    private ROEObjectWrapper<EClass,EObject> taskModel;
    protected String inputXslt;
    protected String outputXslt;
    protected boolean isTimeoutEnabled;
    protected String timeoutEvent;
    protected String timeoutExpression;
    protected String timeoutUnit;

    private AtomicReference<Mapper> mapper = new AtomicReference<Mapper>();
	private boolean forCompensation;
	private boolean asyncExec;
	private boolean checkPoint;
    private short index;
	private boolean monitoring;
	private int uniqueId;
    protected static ThreadLocal<Object> lockContext = new ThreadLocal<Object>();
    
    

    public AbstractTask() {
//        lockContext = new ThreadLocal<Object>();
//        lockContext.set(null);
    }

    @Override
    public void init(InitContext context, Object... args) throws Exception{
        
    	setContext(context,args);
        if(args.length > 3)
        	 this.uniqueId = (Integer) args[3];


        taskModel = (ROEObjectWrapper<EClass, EObject>) initContext.getProcessModel().getTaskElement(this.name, ROEObjectWrapper.class);

        logger.log(Level.INFO, String.format("Initializing task : %s", name));
        if(taskModel.isInstanceOf(BpmnModelClass.ACTIVITY)) {
        	initTask();
        	initTimeoutProperties();
        }
        initTransformations();

    }
    
    protected void setContext(InitContext context,Object ... args) {
    	this.initContext = context;
        this.type = (String) args[0];
        this.name = (String) args[1];
        this.index = (Short) args[2];
    }
    
    protected void initTimeoutProperties() throws Exception{
    	if(BpmnModelClass.ACTIVITY.isSuperTypeOf(taskModel.getEClassType())){
    		EObjectWrapper<EClass, EObject> valueWrapper = ExtensionHelper.getAddDataExtensionValueWrapper(taskModel.getEInstance());
        	if(valueWrapper!= null && valueWrapper.containsAttribute(BpmnMetaModelExtensionConstants.E_ATTR_TIMER_ENABLED)){
        		isTimeoutEnabled = (Boolean)valueWrapper.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_TIMER_ENABLED);
        		if(isTimeoutEnabled){
        			EObject timeoutData = valueWrapper.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_TIMER_DATA);
        			if(timeoutData != null){
        				EObjectWrapper<EClass, EObject> wrap = EObjectWrapper.wrap(timeoutData);
        				String xpathExpr = wrap.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_EXPRESSION);
        				if (xpathExpr != null && !xpathExpr.trim().isEmpty()) {
        		            XiNode xpathNode = XSTemplateSerializer.deSerializeXPathString(xpathExpr);
        		            this.timeoutExpression = XSTemplateSerializer.getXPathExpressionAsStringValue(xpathNode);
        		        }
        				timeoutEvent = wrap.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_EVENT);
        				EEnumLiteral unitEnum = wrap.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_UNIT);
        				timeoutUnit = unitEnum.getLiteral();
        			}
        		}
        	}
    	}
    	
    }



    public  void initTransitions() throws Exception
    {

        List<ROEObjectWrapper<EClass,EObject>> outgoings = taskModel.getWrappedEObjectList(BpmnMetaModelConstants.E_ATTR_OUTGOING);

        LinkedList<Transition> outTransitions = new LinkedList<Transition>();

        for (ROEObjectWrapper<EClass,EObject> outgoing : outgoings) {

            String transitionId = (String) outgoing.getAttribute(BpmnMetaModelConstants.E_ATTR_ID);
            Transition transition = TaskRegistry.getInstance().getTransition(transitionId);
            if (transition == null) {
                logger.log(Level.INFO, String.format("Initializing outbound transition %s", transitionId));
                transition = TaskFactory.getInstance().newTransition(transitionId);
                transition.init(this.initContext, new Object[] {this, outgoing});


            }
            outTransitions.add(transition);
        }

        List<ROEObjectWrapper<EClass,EObject>> incomings = taskModel.getWrappedEObjectList(BpmnMetaModelConstants.E_ATTR_INCOMING);
        LinkedList<Transition> inTransitions = new LinkedList<Transition>();

        for (ROEObjectWrapper<EClass,EObject> incoming : incomings) {

            String transitionId = (String) incoming.getAttribute(BpmnMetaModelConstants.E_ATTR_ID);
            Transition transition = TaskRegistry.getInstance().getTransition(transitionId);
            if (transition == null) {
                logger.log(Level.INFO, String.format("Initializing inbound transition %s", transitionId));
                transition = TaskFactory.getInstance().newTransition(transitionId);
                transition.init(this.initContext, new Object[] {this, incoming});
            }
            inTransitions.add(transition);
        }

        incomingTransitions = inTransitions.toArray(new Transition[0]);
        outgoingTranisitions = outTransitions.toArray(new Transition[0]);



    }

    protected void initTransformations() throws Exception
    {
        initInputTransforms();
        initOutputTransforms();

    }

    protected void initOutputTransforms() {
        List<?> dataOutputAssocs = taskModel.getListAttribute(BpmnMetaModelConstants.E_ATTR_DATA_OUTPUT_ASSOCIATIONS);
        if (!dataOutputAssocs.isEmpty()) {
            ROEObjectWrapper<EClass,EObject> doAssocWrap = ROEObjectWrapper.wrap((EObject)dataOutputAssocs.get(0));

            EObject transform = (EObject) doAssocWrap.getAttribute(BpmnMetaModelConstants.E_ATTR_TRANSFORMATION);
            ROEObjectWrapper<EClass,EObject> transformWrap = ROEObjectWrapper.wrap(transform);
            outputXslt = (String) transformWrap.getAttribute(BpmnMetaModelConstants.E_ATTR_BODY);
        }
    }

    protected void initInputTransforms() {
        List<?> dataInputAssocs = taskModel.getListAttribute(BpmnMetaModelConstants.E_ATTR_DATA_INPUT_ASSOCIATIONS);
        if (!dataInputAssocs.isEmpty()) {
            ROEObjectWrapper<EClass,EObject> doAssocWrap = ROEObjectWrapper.wrap((EObject)dataInputAssocs.get(0));

            EObject transform = (EObject) doAssocWrap.getAttribute(BpmnMetaModelConstants.E_ATTR_TRANSFORMATION);
            ROEObjectWrapper<EClass,EObject> transformWrap = ROEObjectWrapper.wrap(transform);
            inputXslt = (String) transformWrap.getAttribute(BpmnMetaModelConstants.E_ATTR_BODY);
        }
    }

    protected boolean isTimeoutEnabled(){
    	return isTimeoutEnabled;
    }
    
    

    public String getTimeoutEvent() {
		return timeoutEvent;
	}

	public String getTimeoutExpression() {
		return timeoutExpression;
	}

	public String getTimeoutUnit() {
		return timeoutUnit;
	}

	@Override
    public Mapper getMapper() {
        Mapper m = mapper.get();
        if (m == null) {
            mapper.compareAndSet(null, m=newMapper());
        }
        return m;
    }

    @SuppressWarnings("unchecked")
	private Mapper newMapper()
    {

        String qualifiedName = this.getName();
        qualifiedName = qualifiedName.substring(qualifiedName.lastIndexOf(".")+1).replaceAll("\\.", "_");
        String className = String.format("%s$%s_Mapper", initContext.getProcessTemplate().getProcessName().getJavaClassName(), qualifiedName) ;

        ClassLoader classLoader = initContext.getProcessAgent().getRuleServiceProvider().getClassLoader();

        try {
            Class<Mapper> klazz = (Class<Mapper>) Class.forName(className, true, classLoader);
            return klazz.newInstance();
        }
        catch (Throwable t) {
            logger.log(Level.ERROR, t, "Class not found");
            throw new RuntimeException(t);
        }
    }

    @Override
    public Transition[] getOutgoingTransitions() {
        return this.outgoingTranisitions;
    }

    @Override
    public Transition[] getIncomingTransitions() {
        return this.incomingTransitions;
    }


    @Override
    public String getName() {
        return this.name;
    }
    
	@Override
	public int getUniqueId() {
		// TODO Auto-generated method stub
		return this.uniqueId;
	}

    @Override
    public String getType() {
        return this.type;
    }

    public ROEObjectWrapper<EClass,EObject> getTaskModel()
    {
        return this.taskModel;
    }
    
    protected void setTaskModel(ROEObjectWrapper<EClass, EObject> tm) {
    	this.taskModel = ROEObjectWrapper.wrap(tm.getEInstance());
    }

    @Override
    public String getInputMapperString() {
        return this.inputXslt;
    }

    @Override
    public String getOutputMapperString() {
        return this.outputXslt;
    }

    @Override
    public boolean isCheckpointEnabled() {
        return this.asyncExec || this.checkPoint;
    }

    public boolean isAsyncExec() {
    	return this.asyncExec;
    }
    
    
    

    public boolean isMonitoring() {
		return monitoring;
	}

	@Override
    public InitContext getInitContext() {
        return this.initContext;
    }
    
    boolean isForCompensation() {
    	return this.forCompensation;
    }

    @Override
    public short getIndex() {
        return index;
    }

    void initTask() {
    	EObjectWrapper<EClass, EObject> valueWrapper =	ExtensionHelper.getAddDataExtensionValueWrapper(taskModel.getEInstance());

    	this.asyncExec = (Boolean) valueWrapper.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_ASYNC);
    	this.checkPoint = (Boolean) valueWrapper.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_CHECK_POINT);
    	this.monitoring = ( MonitoringEventHelper.getMonitorEvent(initContext.getProcessAgent().getRuleServiceProvider()) != null);
    	
    	
    }
    
    
    
    @Override
    public TaskResult exec(Job context, Variables vars, Task loopTask) {
    	TaskResult result = null;
    	try {
	    	preExec(context,vars);
	    	if(logger.isEnabledFor(LEVEL))
	    	logger.log(LEVEL,String.format("Executing task:%s",getName()));
	    	result  =  execute( context,  vars, loopTask);
	    	if(result == null) {
	    		throw new ProcessException("Null task result");
	    	}
    	}
    	catch(Throwable e) {
    		logger.log(Level.ERROR,String.format("Task Execution Exception at:%s",getName()),e);
    	}
    	finally {
    		Job resultJob = context;
    		if(result.getResult() instanceof Job) {
    			resultJob = (Job) result.getResult();
    		}
    		postExec(resultJob,vars);
    	}
    	return result;
    }

	public abstract TaskResult execute(Job context, Variables vars, Task loopTask);
	

	protected void postExec(Job job, Variables vars) {
		if(isMonitoring()){
			MonitoringEventHelper.monitorTask(this,job, vars,false);
		}
	}

	protected void preExec(Job job, Variables vars) {
		if(isMonitoring()){
			MonitoringEventHelper.monitorTask(this,job, vars,true);
		}
	}

	@Override
	public Object getLockContext(JobContext job) {
		return lockContext.get();
	}
	
	@Override 
	public void setLockContext(Object val) {
		lockContext.set(val);
	}
	
}
