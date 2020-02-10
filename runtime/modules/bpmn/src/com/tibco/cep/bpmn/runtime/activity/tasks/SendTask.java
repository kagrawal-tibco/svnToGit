package com.tibco.cep.bpmn.runtime.activity.tasks;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;

import com.tibco.be.functions.event.EventHelper;
import com.tibco.cep.bpmn.model.designtime.extension.ExtensionHelper;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnMetaModelConstants;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnMetaModelExtensionConstants;
import com.tibco.cep.bpmn.model.designtime.utils.EObjectWrapper;
import com.tibco.cep.bpmn.model.designtime.utils.ROEObjectWrapper;
import com.tibco.cep.bpmn.runtime.activity.EventApplicable;
import com.tibco.cep.bpmn.runtime.activity.InitContext;
import com.tibco.cep.bpmn.runtime.activity.Task;
import com.tibco.cep.bpmn.runtime.activity.TaskResult;
import com.tibco.cep.bpmn.runtime.activity.results.DefaultResult;
import com.tibco.cep.bpmn.runtime.activity.results.ExceptionResult;
import com.tibco.cep.bpmn.runtime.agent.Job;
import com.tibco.cep.bpmn.runtime.templates.ProcessTemplate;
import com.tibco.cep.bpmn.runtime.utils.Variables;
import com.tibco.cep.designtime.model.Ontology;
import com.tibco.cep.designtime.model.event.Event;
import com.tibco.cep.driver.jms.JMSDestination;
import com.tibco.cep.driver.tibrv.TibRvDestination;
import com.tibco.cep.runtime.channel.Channel.Destination;
import com.tibco.cep.runtime.channel.ChannelManager;
import com.tibco.cep.runtime.model.event.SimpleEvent;

/**
 * @author pdhar
 *
 */
public class SendTask extends AbstractTask implements EventApplicable {

    protected String destination;
    protected Event event;
    protected String destinationProperties;

    static int REPLY_TO_MASK = 1;
    static int CONSUME_MASK  = 2;

    protected Map<String, Integer> task2ConsumeAndReplyTable = new HashMap<String, Integer>();

    public SendTask() {
    }

    @Override
    public void init(InitContext context, Object... args) throws Exception {
        super.init(context, args);
        EObjectWrapper<EClass, EObject> flowNodeWrapper = EObjectWrapper.wrap(getTaskModel().getEInstance());
        EObjectWrapper<EClass, EObject> valueWrapper =	ExtensionHelper.getAddDataExtensionValueWrapper(flowNodeWrapper);
        this.destination = (String) valueWrapper.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_DESTINATION);
        String eventURI = (String) valueWrapper.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_EVENT);
        Ontology ontology = getInitContext().getProcessModel().getOntology();
        this.event = ontology.getEvent(eventURI);
        this.destinationProperties = getDestinationProperties(context);

        initializeReplyConsumeTasks(context, flowNodeWrapper);
    }

    private void initializeReplyConsumeTasks(InitContext context,
                                             EObjectWrapper<EClass, EObject> flowNodeWrapper) {

        EObjectWrapper<EClass, EObject> addDataExtensionValueWrapper = ExtensionHelper
                .getAddDataExtensionValueWrapper(flowNodeWrapper);
        if (addDataExtensionValueWrapper
                .containsAttribute(BpmnMetaModelExtensionConstants.E_ATTR_MESSAGE_STARTERS)) {

            List<EObject> listAttribute = addDataExtensionValueWrapper
                    .getListAttribute(BpmnMetaModelExtensionConstants.E_ATTR_MESSAGE_STARTERS);
            if (listAttribute != null && listAttribute.size() > 0) {
                // collect all the triggerable task in process
                Map<String, Task> triggerableTaskMap = new HashMap<String, Task>();
                ProcessTemplate processTemplate = initContext
                        .getProcessTemplate();
                List<Task> triggerableTask = processTemplate
                        .getTriggerableTask();
                for (Task task : triggerableTask) {
                    ROEObjectWrapper<EClass, EObject> tmodel = task
                            .getTaskModel();
                    String id = tmodel
                            .getAttribute(BpmnMetaModelConstants.E_ATTR_ID);
                    triggerableTaskMap.put(id, task);
                }

                for (EObject eObject : listAttribute) {
                    ROEObjectWrapper<EClass, EObject> wrap = ROEObjectWrapper
                            .wrap(eObject);
                    // here id is start event/receive task flow node
                    String id = wrap
                            .getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_MESSAGE_STARTER);
                    boolean replyTo =(Boolean) wrap
                            .getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_REPLY_TO);
                    boolean consume =(Boolean) wrap
                            .getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_CONSUME);

                    Task task = triggerableTaskMap.get(id);
                    if (task != null) {
                        int mask = 0;

                        mask = replyTo ? mask | REPLY_TO_MASK : mask;
                        mask = consume ? mask | CONSUME_MASK : mask;

                        if (mask > 0) task2ConsumeAndReplyTable.put(task.getName(), mask);
                    }
                }
            }
        }
    }

    /* (non-Javadoc)
      * @see com.tibco.cep.bpmn.runtime.activity.Task#execute(com.tibco.cep.bpmn.runtime.agent.Job)
      */
    @Override
    public TaskResult execute(Job job, Variables vars, Task loopTask) {
    	TaskResult result = null;

        try {
            // The mapper has already created the event instance and put in the
            // vars using the event name
            Object var = vars.getVariable(event.getName());
            if (var != null && var instanceof com.tibco.cep.runtime.model.event.SimpleEvent) {

                
                //consumeEvents(job, true); // consume will only be done in the end task

                if (task2ConsumeAndReplyTable.size() == 0) {
                	sendMessage(job, (SimpleEvent)var);
                	
                } else {
                	sendReplies(job, (SimpleEvent)var);
                }
                result = new DefaultResult(TaskResult.Status.OK, var);
            } else {
                Throwable t = new Exception(MessageFormat.format("Event instance for event type \"{0}\" is null",this.event.getFullPath()));
                result = new ExceptionResult(t);
            }

        } catch (Throwable throwable) {
            result = new ExceptionResult(throwable);
        }
        
        return result; // needed for debugger exit task notification
    }

    protected void consumeEvents(Job job, boolean isFiltered) throws Exception {

        Map<String, Set<Job.PendingEvent>> pendingEvents = job.getPendingEvents();
        for (Entry<String, Set<Job.PendingEvent>> entry : pendingEvents.entrySet()) {
            String taskName = entry.getKey();
            // check if the task from the pending event belongs to this process or sub process
            // if it does not then continue.
            Task t = job.getJobContext().getProcessTemplate().getTask(taskName);
            if(t == null) 
            	continue;
            int mask = !(isFiltered) ? CONSUME_MASK: task2ConsumeAndReplyTable.get(taskName);
            if ((mask & CONSUME_MASK) == CONSUME_MASK) {
                    Set<Job.PendingEvent> peSet = entry.getValue();
                    for (Job.PendingEvent pe : peSet) {
                    	pe.consume();
                    }
            }
        }
    }

    protected void sendReplies(Job job, SimpleEvent evt) throws Exception {
        for(Entry<String, Integer> entry : task2ConsumeAndReplyTable.entrySet()){
            int mask = entry.getValue();
            String task = entry.getKey();

            if ((mask & REPLY_TO_MASK) == REPLY_TO_MASK) {
                Set<Job.PendingEvent> events = job.getPendingEvents(task);
                if(events == null) {
                	sendMessage(job,evt);
                } else {
                	for(Job.PendingEvent e : events) {
                		if(((SimpleEvent)e.getEvent()).getContext() != null) {
                			EventHelper.replyEvent_Impl((SimpleEvent) e.getEvent(),evt, true);
                		} else {
                			sendMessage(job,evt);
                		}
                    }
                }
                
            }
        }
    }

    protected void sendMessage(Job job, SimpleEvent evt) throws Exception {

        String dest = getDestination();
        ChannelManager manager = job.getProcessAgent().getRuleServiceProvider().getChannelManager();
        if(dest == null || dest.trim().isEmpty()) {
            dest = event.getChannelURI() + "/" + event.getDestinationName();
        }
        manager.sendEvent(evt, dest, null);

    }




    public String getDestination() {
        return destination;
    }

    public Event getInputEvent() {
        return event;
    }

    public Event getOutputEvent() {
        return null;
    }




    protected String getDestinationProperties(InitContext context) {
        String destinationProps = null;
        ChannelManager manager = context.getProcessAgent()
                .getRuleServiceProvider().getChannelManager();
        if (manager != null) {
            Destination dest = manager.getDestination(destination);
            if (dest != null) {
                EObjectWrapper<EClass, EObject> flowNodeWrapper = EObjectWrapper
                        .wrap(getTaskModel().getEInstance());
                EObjectWrapper<EClass, EObject> valueWrapper = ExtensionHelper
                        .getAddDataExtensionValueWrapper(flowNodeWrapper);
                if (valueWrapper
                        .containsAttribute(BpmnMetaModelExtensionConstants.E_ATTR_DESTINATION_CONFIG)) {
                    EObject config = valueWrapper
                            .getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_DESTINATION_CONFIG);
                    if (config != null) {
                        EObjectWrapper<EClass, EObject> configWrapper = EObjectWrapper
                                .wrap(config);
                        Map<String, Object> props = new HashMap<String, Object>();
                        if (dest instanceof JMSDestination) {
                            String destName = configWrapper
                                    .getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_DESTINATION_NAME);
                            if (destName != null && !destName.trim().isEmpty())
                                props.put("name", destName);

                            Boolean queue = configWrapper
                                    .getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_QUEUE);
                            //if (queue != null && !queue.trim().isEmpty())
                                props.put(
                                        BpmnMetaModelExtensionConstants.E_ATTR_QUEUE,
                                        queue);

                            Object attribute = configWrapper
                                    .getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_JMSEXPIRATION);
                            if (attribute != null)
                                props.put(
                                        BpmnMetaModelExtensionConstants.E_ATTR_JMSEXPIRATION,
                                        attribute);

                            attribute = configWrapper
                                    .getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_JMSDELIVERYMODE);
                            if (attribute != null)
                                props.put(
                                        BpmnMetaModelExtensionConstants.E_ATTR_JMSDELIVERYMODE,
                                        attribute);

                            attribute = configWrapper
                                    .getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_JMSPRIORITY);
                            if (attribute != null)
                                props.put(
                                        BpmnMetaModelExtensionConstants.E_ATTR_JMSPRIORITY,
                                        attribute);

                            String corrId = configWrapper
                                    .getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_JMSCORRELATIONID);
                            if (corrId != null && !corrId.trim().isEmpty())
                                props.put(
                                        BpmnMetaModelExtensionConstants.E_ATTR_JMSCORRELATIONID,
                                        corrId);

                        } else if (dest instanceof TibRvDestination) {
                            String attribute = configWrapper
                                    .getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_SUBJECT);
                            if (attribute != null && !attribute.trim().isEmpty())
                                props.put(
                                        BpmnMetaModelExtensionConstants.E_ATTR_SUBJECT,
                                        attribute);

                        }

                        destinationProps = convertToString(props);
                        if (destinationProps != null
                                && destinationProps.trim().isEmpty())
                            destinationProps = null;
                    }
                }

            }
        }

        return destinationProps;
    }

    private String convertToString(Map<String, Object> map) {
        if(map.size() == 0)
            return "";
        Iterator<Entry<String, Object>> iterator = map.entrySet().iterator();
        StringBuilder sb = new StringBuilder();
        for (;;) {
            Entry<String, Object> e = iterator.next();
            String key = e.getKey();
            Object value = e.getValue();
            sb.append(key);
            sb.append('=');
            sb.append(value);
            if (!iterator.hasNext())
                return sb.toString();

            sb.append(";");
        }
    }


}
