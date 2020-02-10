package com.tibco.cep.bpmn.runtime.activity.tasks;

import java.io.StringWriter;
import java.util.GregorianCalendar;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;

import com.tibco.be.util.XiSupport;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnMetaModelExtensionConstants;
import com.tibco.cep.bpmn.model.designtime.utils.EObjectWrapper;
import com.tibco.cep.bpmn.model.designtime.utils.ROEObjectWrapper;
import com.tibco.cep.bpmn.runtime.activity.Activity;
import com.tibco.cep.bpmn.runtime.activity.Task;
import com.tibco.cep.bpmn.runtime.activity.Transition;
import com.tibco.cep.bpmn.runtime.agent.Job;
import com.tibco.cep.bpmn.runtime.agent.Job.PendingEvent;
import com.tibco.cep.bpmn.runtime.agent.ProcessAgent;
import com.tibco.cep.bpmn.runtime.model.JobContext;
import com.tibco.cep.bpmn.runtime.templates.ProcessName;
import com.tibco.cep.bpmn.runtime.utils.Constants;
import com.tibco.cep.bpmn.runtime.utils.Variables;
import com.tibco.cep.kernel.model.entity.Event;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.LogManagerFactory;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.runtime.channel.ChannelManager;
import com.tibco.cep.runtime.model.element.Concept;
import com.tibco.cep.runtime.model.element.Property;
import com.tibco.cep.runtime.model.element.PropertyArray;
import com.tibco.cep.runtime.model.element.PropertyAtom;
import com.tibco.cep.runtime.model.element.PropertyAtomConcept;
import com.tibco.cep.runtime.model.event.EventPayload;
import com.tibco.cep.runtime.model.event.SimpleEvent;
import com.tibco.cep.runtime.session.RuleServiceProvider;
import com.tibco.xml.ImportRegistryEntry;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.datamodel.XiFactory;
import com.tibco.xml.datamodel.XiNode;
import com.tibco.xml.datamodel.helpers.XiSerializer;

public class MonitoringEventHelper {
	
	private static final String TASKID2 = "taskid";
	private static final String PENDINGEVENT = "pendingevent";
	private static final String PENDINGEVENTS = "pendingevents";
	private static final String INDEX2 = "index";
	private static final String ARRAY = "array";
	private static final String TYPE = "type";
	private static final String VALUE = "value";
	private static final String NAME = "name";
	private static final String VARIABLE = "variable";
	private static final String VARIABLES = "variables";
	private static final String VARIABLE_XML_SCHEMA = "http://www.tibco.com/be/bpmn/VariableXMLSchema";
	private static final String JOBDATA = "jobdata";
	private static final String TIME_STAMP = "time_stamp";
	private static final String NEXT_TASK_LABEL = "nextTaskLabel";
	private static final String NEXT_TASK = "nextTask";
	private static final String NEXT_TASK_TYPE = "nextTaskType";
	private static final String PREV_TASK_LABEL = "prevTaskLabel";
	private static final String PREV_TASK = "prevTask";
	private static final String PREV_TASK_TYPE = "prevTaskType";
	private static final String CURR_TASK_LABEL = "currTaskLabel";
	private static final String CURR_TASK = "currTask";
	private static final String CURR_TASK_TYPE = "currTaskType";
	private static final String PROCESS_NAME = "process_name";
	private static final String PROCESS_SIMPLE_NAME = "process_simple_name";
	private static final String BACKING_STORE_NAME = "backing_store_name";
	private static final String FULLY_QUALIFIED_NAME = "fully_qualified_name";
	private static final String JAVA_CLASS_NAME = "java_class_name";
	private static final String PROCESS_DAO_NAME = "process_dao_name";
	private static final String POST_TASK = "POST_TASK";
	private static final String PRE_TASK = "PRE_TASK";
	protected static Logger logger = LogManagerFactory.getLogManager().getLogger(MonitoringEventHelper.class);
    protected static Level LEVEL = Constants.DEBUG ? Level.WARN : Level.DEBUG;
    
    private static final String PROCESS_EXPANDED_NAME = "process_expanded_name";
	private static final String MONITOR_TYPE = "monitorType";
	private static final String PARENT_PROCESS_ID = "parentProcessId";
	private static final String PROCESS_ID = "processId";
	private static final String BE_PROCESS_MONITOR_EVENT = "be.process.monitor.event";
	
	
	public static void monitorTask(Task currentTask, Job job, Variables vars,boolean isPreTask) {
		ProcessAgent pa = job.getProcessAgent();
		RuleServiceProvider rsp = pa.getRuleServiceProvider();
		
		final long processId = job.getJobContext().getId();
		final String processExtId = job.getJobContext().getExtId();
		final ExpandedName processExpandedName = job.getJobContext().getExpandedName();
		
		final Activity task = job.getCurrentTask();
		final JobContext jobContext = job.getJobContext();
		final ProcessName procName = jobContext.getProcessTemplate().getProcessName();
		try {
			com.tibco.cep.designtime.model.event.Event event = getMonitorEvent(rsp);

			if (event != null) {
				SimpleEvent pme = (SimpleEvent) rsp.getTypeManager().createEntity(event.getFullPath());
				if(event.getPropertyDefinition(PROCESS_ID, true) != null ){
					
					pme.setPropertyValue(PROCESS_ID,jobContext.getId());
				}
				if(event.getPropertyDefinition(PARENT_PROCESS_ID, true) != null && jobContext.getParent() != null ){
					
					pme.setPropertyValue(PARENT_PROCESS_ID,((JobContext)jobContext.getParent()).getId());
				}
				if(event.getPropertyDefinition(MONITOR_TYPE, true) != null ){
					
					pme.setPropertyValue(MONITOR_TYPE,isPreTask ?PRE_TASK:POST_TASK);
				}
				if(event.getPropertyDefinition(PROCESS_EXPANDED_NAME, true) != null ){
					
					pme.setPropertyValue(PROCESS_EXPANDED_NAME, procName.getExpandedName().toString());
				}
				if(event.getPropertyDefinition(PROCESS_DAO_NAME, true) != null ){
					
					pme.setPropertyValue(PROCESS_DAO_NAME, procName.getEntityDaoName());
				}
				if(event.getPropertyDefinition(JAVA_CLASS_NAME, true) != null ){
					
					pme.setPropertyValue(JAVA_CLASS_NAME, procName.getJavaClassName());
				}
				if(event.getPropertyDefinition(FULLY_QUALIFIED_NAME, true) != null ){
					
					pme.setPropertyValue(FULLY_QUALIFIED_NAME, procName.getModeledFQN());
				}
				if(event.getPropertyDefinition(BACKING_STORE_NAME, true) != null ){
					
					pme.setPropertyValue(BACKING_STORE_NAME, procName.getBackingStoreName());
				}
				if(event.getPropertyDefinition(PROCESS_SIMPLE_NAME, true) != null ){
					
					pme.setPropertyValue(PROCESS_SIMPLE_NAME, procName.getSimpleName());
				}

				if(event.getPropertyDefinition(PROCESS_NAME, true) != null ){
					
					pme.setPropertyValue(PROCESS_NAME, procName.getSimpleName());
				}
				

				if(event.getPropertyDefinition(CURR_TASK_TYPE, true) != null ){
					
					pme.setPropertyValue(CURR_TASK_TYPE, currentTask.getType());
				}

				if(event.getPropertyDefinition(CURR_TASK, true) != null ){
					
					pme.setPropertyValue(CURR_TASK, currentTask.getName());
				}
				if(event.getPropertyDefinition(CURR_TASK_LABEL, true) != null ){
					ROEObjectWrapper<EClass, EObject>taskModel = task.getInitContext().getProcessModel().getTaskElement(currentTask.getName(),com.tibco.cep.bpmn.model.designtime.utils.ROEObjectWrapper.class);
					EObjectWrapper<EClass, EObject> extValueWrapper =	 EObjectWrapper.wrap(taskModel.getEInstance());;
			    	String label = (String) extValueWrapper.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_NAME);
					pme.setPropertyValue(CURR_TASK_LABEL, label);
				}
				Task prevTask = jobContext.getLastTaskExecuted();

				if (prevTask != null) {

					if(event.getPropertyDefinition(PREV_TASK_TYPE, true) != null ){
						
						pme.setPropertyValue(PREV_TASK_TYPE, prevTask.getType());
					}

					if(event.getPropertyDefinition(PREV_TASK, true) != null ){
						
						pme.setPropertyValue(PREV_TASK, prevTask.getName());
					}
					if(event.getPropertyDefinition(PREV_TASK_LABEL, true) != null ){
						ROEObjectWrapper<EClass, EObject>taskModel = prevTask.getInitContext().getProcessModel().getTaskElement(currentTask.getName(),com.tibco.cep.bpmn.model.designtime.utils.ROEObjectWrapper.class);
						EObjectWrapper<EClass, EObject> extValueWrapper =	 EObjectWrapper.wrap(taskModel.getEInstance());;
				    	String label = (String) extValueWrapper.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_NAME);
						pme.setPropertyValue(PREV_TASK_LABEL, label);
					}
				}

				Task nextTask = jobContext.getNextTask();
				if(nextTask == null) {
					 if (job.getLastTask() != null) {
				            Transition[] outgoing = job.getLastTask().getOutgoingTransitions();

				            if(outgoing.length > 0 & outgoing[0] != null) {
				            	Transition transition = outgoing[0];
				            	nextTask =  transition.toTask();
				            }
				        }
				}
				if (nextTask != null) {

					if(event.getPropertyDefinition(NEXT_TASK_TYPE, true) != null ){
						
						pme.setPropertyValue(NEXT_TASK_TYPE, nextTask.getType());
					}

					if(event.getPropertyDefinition(NEXT_TASK, true) != null ){
						
						pme.setPropertyValue(NEXT_TASK, nextTask.getName());
					}
					if(event.getPropertyDefinition(NEXT_TASK_LABEL, true) != null ){
						ROEObjectWrapper<EClass, EObject>taskModel = nextTask.getInitContext().getProcessModel().getTaskElement(currentTask.getName(),com.tibco.cep.bpmn.model.designtime.utils.ROEObjectWrapper.class);
						EObjectWrapper<EClass, EObject> extValueWrapper =	 EObjectWrapper.wrap(taskModel.getEInstance());;
				    	String label = (String) extValueWrapper.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_NAME);
						pme.setPropertyValue(NEXT_TASK_LABEL, label);
					}

				}

				if(event.getPropertyDefinition(TIME_STAMP, true) != null ){
					
					pme.setProperty(TIME_STAMP, new GregorianCalendar());
				}
			
				
				if(hasVariableSchema(event)){
					String payload = processVars(vars,job);
					//payload = StringEscapeUtils.escapeXml(payload);
					EventPayload epl = rsp.getTypeManager().getPayloadFactory().createPayload(pme.getExpandedName(), payload);
					pme.setPayload(epl);
				}

				ChannelManager manager = job.getProcessAgent().getRuleServiceProvider().getChannelManager();

				if (event.getDestinationName() != null) {
					String destURI = String.format("%s/%s", event.getChannelURI(), event.getDestinationName());
					manager.sendEventImmediate(pme, destURI, null);
				}

			}
		} catch (Exception e) {
			String type = isPreTask ? "Pre":"Post";
			logger.log(Level.ERROR, "Failed to send %s-exec monitor event for process:%s and task: %s", e,type, processId, processExpandedName);
		}
	}
	
	private static boolean hasVariableSchema(com.tibco.cep.designtime.model.event.Event event) {
		ImportRegistryEntry[] imports = event.getPayloadImportRegistry().getImports();
		for(ImportRegistryEntry imp:imports) {
			if(imp.getNamespaceURI().equals(VARIABLE_XML_SCHEMA))
				return true;
		}
		return false;
	}

	/**
	 * @param job
	 * @param vars
	 */
	

	public static String processVars(Variables vars,Job job) throws Exception {
		final String namespace = VARIABLE_XML_SCHEMA;
		XiFactory xiFactory = XiSupport.getXiFactory();
		XiNode docRoot = xiFactory.createDocument();
		XiNode xjobdata = xiFactory.createElement(ExpandedName.makeName(namespace, JOBDATA));
		docRoot.appendChild(xjobdata);
		
		XiNode xvars = xiFactory.createElement(ExpandedName.makeName(namespace, VARIABLES));
		xjobdata.appendChild(xvars);
		if(job.getJobContext() instanceof Concept) {
			Concept instance = ((Concept)job.getJobContext());
			Property[] properties = instance.getProperties();
			for(Property prop:properties) {
				if(prop  instanceof PropertyAtom) {
					PropertyAtom pa = (PropertyAtom) prop;
					if(pa instanceof PropertyAtomConcept) {
						XiNode jvar = xiFactory.createElement(ExpandedName.makeName(namespace, VARIABLE));
						XiNode jname = xiFactory.createAttribute(ExpandedName.makeName(namespace,NAME), prop.getName());
						jvar.setAttribute(jname);
						XiNode jval = xiFactory.createElement(ExpandedName.makeName(namespace, VALUE));
						jvar.appendChild(jval);
						Concept c = ((PropertyAtomConcept)pa).getConcept();
						if (c != null) {
							XiNode node = XiSupport.getXiFactory().createElement(c.getExpandedName());
							c.toXiNode(node, false);
							jval.appendChild(node);
							String jtype = c.getExpandedName().toString();
							XiNode xtype = xiFactory.createAttribute(ExpandedName.makeName(namespace, TYPE), jtype);
							jvar.setAttribute(xtype);
						}
						xvars.appendChild(jvar);
					} else {
						XiNode jvar = xiFactory.createElement(ExpandedName.makeName(namespace, VARIABLE));
						XiNode jname = xiFactory.createAttribute(ExpandedName.makeName(namespace,NAME), prop.getName());
						jvar.setAttribute(jname);
						XiNode jval = xiFactory.createElement(ExpandedName.makeName(namespace, VALUE));
						jvar.appendChild(jval);
						Object c = ((PropertyAtom)pa).getValue();
						jval.setStringValue(c.toString());
						String jtype = c.getClass().getName();
						XiNode xtype = xiFactory.createAttribute(ExpandedName.makeName(namespace,TYPE),jtype);
						jvar.setAttribute(xtype);
						xvars.appendChild(jvar);
					}
				} else if (prop instanceof PropertyArray){
					PropertyArray parr = (PropertyArray) prop;
					XiNode jvar = xiFactory.createElement(ExpandedName.makeName(namespace, VARIABLE));
					XiNode jname = xiFactory.createAttribute(ExpandedName.makeName(namespace,NAME), prop.getName());
					jvar.setAttribute(jname);
					XiNode isArray = xiFactory.createAttribute(ExpandedName.makeName(namespace,ARRAY), "true");
					jvar.setAttribute(isArray);
					for(int i=0; i < parr.length();i++) {
						PropertyAtom pa = parr.get(i);
						XiNode jval = xiFactory.createElement(ExpandedName.makeName(namespace, VALUE));
						jvar.appendChild(jval);
						XiNode index = xiFactory.createAttribute(ExpandedName.makeName(namespace,INDEX2), ""+i);
						jval.setAttribute(index);
						if(pa instanceof PropertyAtomConcept) {
							Concept c = ((PropertyAtomConcept)pa).getConcept();
							XiNode node = XiSupport.getXiFactory().createElement(c.getExpandedName());
							c.toXiNode(node, false);
							jval.appendChild(node);
							String jtype = c.getExpandedName().toString();
							XiNode xtype = xiFactory.createAttribute(ExpandedName.makeName(namespace,TYPE),jtype);
							jvar.setAttribute(xtype);
							xvars.appendChild(jvar);
						} else {
							Object c = ((PropertyAtom)pa).getValue();
							jval.setStringValue(c.toString());
							String jtype = c.getClass().getName();
							XiNode xtype = xiFactory.createAttribute(ExpandedName.makeName(namespace,TYPE),jtype);
							jvar.setAttribute(xtype);
							xvars.appendChild(jvar);
						}
					}
					
				}
			}
			/*
			XiNode jvar = xiFactory.createElement(ExpandedName.makeName(namespace, "variable"));
			XiNode jname = xiFactory.createAttribute(ExpandedName.makeName(namespace,"name"), "job");
			jvar.setAttribute(jname);
			XiNode jval = xiFactory.createElement(ExpandedName.makeName(namespace, "value"));
			jvar.appendChild(jval);
			ExpandedName rootNm = instance.getExpandedName();
			if(rootNm.getLocalName().contains("$9")){
				rootNm = ExpandedName.makeName(rootNm.getNamespaceURI(), rootNm.getLocalName().replace("$9","XX9"));
			}
            XiNode node = XiSupport.getXiFactory().createElement(rootNm);
            instance.toXiNode(node, false);
			jval.appendChild(node);
			String jtype = rootNm.toString();
			XiNode xtype = xiFactory.createAttribute(ExpandedName.makeName(namespace,"type"),jtype);
			jvar.setAttribute(xtype);
			xvars.appendChild(jvar);
//			XiSerializer.serialize(jvar);
			 * 
			 */
		}
		
		Map vmap = vars.asMap();
		for(Object v:vmap.keySet()){
			Object val = vmap.get(v);
			XiNode xvar = xiFactory.createElement(ExpandedName.makeName(namespace, VARIABLE));
			
			XiNode xname = xiFactory.createAttribute(ExpandedName.makeName(namespace,NAME), v.toString());
			xvar.setAttribute(xname);
			
			XiNode xval = xiFactory.createElement(ExpandedName.makeName(namespace, VALUE));
			xvar.appendChild(xval);
			String vtype = "";
			if(val instanceof Concept) {
				Concept instance = ((Concept)val);
				ExpandedName rootNm = instance.getExpandedName();
				if(rootNm.getLocalName().contains("$9")){
					rootNm = ExpandedName.makeName(rootNm.getNamespaceURI(), rootNm.getLocalName().replace("$9","XX9"));
				}
	            XiNode node = XiSupport.getXiFactory().createElement(rootNm);
	            instance.toXiNode(node, false);
				xval.appendChild(node);
				vtype = rootNm.toString();
			} else if (val instanceof SimpleEvent) {
				SimpleEvent se = (SimpleEvent) val;
				ExpandedName rootNm = se.getExpandedName();
				if(rootNm.getLocalName().contains("$9")){
					rootNm = ExpandedName.makeName(rootNm.getNamespaceURI(), rootNm.getLocalName().replace("$9","XX9z"));
				}
	            XiNode node = XiSupport.getXiFactory().createElement(rootNm);
	            se.toXiNode(node);
				xval.appendChild(node);
				vtype = rootNm.toString();
			} else {
				xval.setStringValue(val.toString());
				vtype = val.getClass().getName();
			} 
			
			XiNode xtype = xiFactory.createAttribute(ExpandedName.makeName(namespace,TYPE),vtype);
			xvar.setAttribute(xtype);
			xvars.appendChild(xvar);
//			XiSerializer.serialize(xvar);
		}
		XiNode xpevents = xiFactory.createElement(ExpandedName.makeName(namespace, PENDINGEVENTS));
		xjobdata.appendChild(xpevents);
		for(Entry<String, Set<PendingEvent>> pentry:job.getPendingEvents().entrySet()) {
			String taskid = pentry.getKey();
			for(PendingEvent pevent:pentry.getValue()) {
				XiNode xpevent = xiFactory.createElement(ExpandedName.makeName(namespace, PENDINGEVENT));
				Event pe = pevent.getEvent();
				XiNode xtaskid = xiFactory.createAttribute(ExpandedName.makeName(namespace, TASKID2), taskid);
				xpevent.setAttribute(xtaskid);
				xpevents.appendChild(xpevent);
				if(pe instanceof SimpleEvent) {
					SimpleEvent se = (SimpleEvent) pe;
					ExpandedName rootNm =se.getExpandedName();
					XiNode node = XiSupport.getXiFactory().createElement(rootNm);
					se.toXiNode(node);
					xpevent.appendChild(node);
				}
			}
		}
		StringWriter sw = new StringWriter();
		XiSerializer.serialize(docRoot,sw);
		return sw.getBuffer().toString().replace("$1z", "XX1z");
	}
	
	public static com.tibco.cep.designtime.model.event.Event getMonitorEvent(RuleServiceProvider rsp) {
		String eventURI =  null;
		if(rsp == null || rsp.getProperties() == null) {
			eventURI = System.getProperty(BE_PROCESS_MONITOR_EVENT);
		} else {
		    eventURI = rsp.getProperties().getProperty(BE_PROCESS_MONITOR_EVENT);
		}
		if(eventURI != null) {
			eventURI = eventURI.trim();
			return rsp.getProject().getOntology().getEvent(eventURI);
		}
		return null;
	}

}
