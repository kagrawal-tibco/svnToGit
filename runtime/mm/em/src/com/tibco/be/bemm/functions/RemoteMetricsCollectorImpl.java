package com.tibco.be.bemm.functions;

import java.io.IOException;
import java.lang.reflect.UndeclaredThrowableException;
import java.rmi.ConnectException;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.runtime.channel.ChannelManager;
import com.tibco.cep.runtime.model.event.SimpleEvent;
import com.tibco.cep.runtime.service.loader.BEClassLoader;
import com.tibco.cep.runtime.session.RuleServiceProvider;
import com.tibco.cep.runtime.session.RuleSessionManager;

/**
 *
 */
public abstract class RemoteMetricsCollectorImpl implements RemoteMetricsCollector {
    protected static Timer timer = new Timer("remotemetricscollector", true);
    protected String monitoredEntityName;
    protected COLLECTOR_TYPE collectorType;
    protected String[] properties;
    protected MetricTypeHandlerFactory typeHandlerFactory;
    protected BEClassLoader classLoader;
    protected Map<String, MetricCollectingTimerTask> taskMap;
    protected Logger logger;
    protected ChannelManager channelManager;
    protected long frequency;
    protected TopologyEntityProperties entityProperties;
    protected STATUS status;
    protected String[] defaultHawkProps;

    public RemoteMetricsCollectorImpl(COLLECTOR_TYPE type, String[] properties, String monitoredEntityName) {
        this.collectorType = type;
        this.properties = properties;
        this.monitoredEntityName = monitoredEntityName;
        taskMap = new HashMap<String, MetricCollectingTimerTask>();
        typeHandlerFactory = MetricTypeHandlerFactory.getInstance();
        status = STATUS.NOT_INITIALIZED;
        RuleServiceProvider currRuleServiceProvider = RuleSessionManager.getCurrentRuleSession().getRuleServiceProvider();
		classLoader = (BEClassLoader) currRuleServiceProvider.getClassLoader();
		logger = currRuleServiceProvider.getLogger(this.getClass());
		frequency = currRuleServiceProvider.getGlobalVariables().getVariableAsLong("JMXUpdateFreq",30000);
		channelManager = currRuleServiceProvider.getChannelManager();
        entityProperties = new TopologyEntityProperties(logger,monitoredEntityName,this.properties);
		boolean success = entityProperties.parse(collectorType);
		if (success == true){
			success = initConnection();
			if (success == true){
				status = STATUS.OK;
			}
			else {
				status = STATUS.IO_ERROR;
			}
		}
		else {
			status = STATUS.PROPERTIES_PARSE_ERROR;
		}
    }

    public void subscribe(String name,String type, String receivingEventURI) {
        if (status == STATUS.DISCONNECTED){
            synchronized (this) {
                if (status == STATUS.DISCONNECTED) {
                    boolean success = initConnection();
                    if (success == true){
                        status = STATUS.OK;
                    }
                    else {
                        status = STATUS.IO_ERROR;
                    }
                }
            }
        }
        if (status != STATUS.OK){
            return;
        }
        MetricCollectingTimerTask task = taskMap.get(type);
        if (task == null) {
            MetricTypeHandler handler = getHandler(type);
            if (handler == null){
                status = STATUS.CONFIG_ERROR;
                return;
            }
            task = new MetricCollectingTimerTask(name, handler, receivingEventURI);
            taskMap.put(type, task);
        }
        if (task.running == false) {
            logger.log(Level.DEBUG, "Scheduled timer task for " + type + " with " + receivingEventURI + "...");
            timer.schedule(task, 100, frequency);
            task.running = true;
        }
        logger.log(Level.DEBUG, "Subscribed for " + type + " with " + receivingEventURI + "...");
    }

    public void unsubscribe(String type) {
        if (status != STATUS.OK){
            return;
        }
        MetricCollectingTimerTask task = taskMap.get(type);
        if (task != null){
            task.cancel();
            taskMap.remove(type);
            logger.log(Level.DEBUG, "Unsubscribed "+type+"...");
            if (taskMap.isEmpty() == true){
                closeConnection();
                status = STATUS.DISCONNECTED;
            }
        }
    }

    public Object snapshot(String name,String type, String receivingEventURI) {
        if (status == STATUS.DISCONNECTED){
            synchronized (this) {
                if (status == STATUS.DISCONNECTED) {
                    boolean success = initConnection();
                    if (success == true){
                        status = STATUS.OK;
                    }
                    else {
                        status = STATUS.IO_ERROR;
                    }
                }
            }
        }
        if (status != STATUS.OK){
            return null;
        }
        try {
            MetricCollectingTimerTask task = taskMap.get(type);
            if (task == null) {
                MetricTypeHandler handler = getHandler(type);
                task = new MetricCollectingTimerTask(name,handler, receivingEventURI);
                taskMap.put(type, task);
            }
            return task.getPopulatedEvents();
        } catch (Exception e) {
            if (logger.isEnabledFor(Level.DEBUG)){
                logger.log(Level.DEBUG, "could not create events for "+type+" using "+collectorType+" agent(s) for "+monitoredEntityName+"...",e);
            }
            else {
                logger.log(Level.WARN, "could not create events for "+type+" using "+collectorType+" agent(s) for "+monitoredEntityName+"...");
            }
            return null;
        }
    }
    
    public void destroy(){
    	for(String key:taskMap.keySet()){
            MetricCollectingTimerTask task = taskMap.get(key);
            if (task != null){
                task.cancel();
            }   		
    	}
    	taskMap.clear();
        closeConnection();        
    }

    protected abstract boolean initConnection();

    protected abstract void closeConnection();

    protected abstract MetricTypeHandler getHandler(String type);

    class MetricCollectingTimerTask extends TimerTask {

        private MetricTypeHandler handler;
        private String receivingEventURI;

        private boolean running;

        private EventCreator eventCreator;

        private String name;

        MetricCollectingTimerTask(String name, MetricTypeHandler handler, String receivingEventURI) {
            this.name = name;
            this.handler = handler;
            this.receivingEventURI = receivingEventURI;
            running = false;
            this.eventCreator = new EventCreator(logger,classLoader,receivingEventURI);
        }

        @Override
        public void run() {
            logger.log(Level.DEBUG, "Firing population of " + receivingEventURI + " for " + handler.getType() + "...");
            SimpleEvent[] events = null;
            try {
                events = getPopulatedEvents();
            } catch (UndeclaredThrowableException e) {
                if (e.getUndeclaredThrowable() instanceof ConnectException) {
                    if (logger.isEnabledFor(Level.DEBUG)) {
                        logger.log(Level.DEBUG, "EM Remote Metrics Collector", "could not connect to "+monitoredEntityName+" using "+entityProperties+" while acquiring "+handler.getType()+" stats...", e);
                    }
                    cancel();
                    running = false;
                    unsubscribe(handler.getType());
                }
                return;
            } catch (IOException e) {
                logger.log(Level.DEBUG, "EM Remote Metrics Collector", "could not connect to "+monitoredEntityName+" using "+entityProperties+" while acquiring "+handler.getType()+" stats...", e);
                cancel();
                running = false;
                unsubscribe(handler.getType());
                return;
            }

            if (events != null) {
                int noOfEvents = events.length;
                if (noOfEvents == 1) {
                    try {
                        channelManager.sendEvent(events[0], events[0].getDestinationURI(), null);
                    } catch (Exception e) {
                        logger.log(Level.WARN, "could not send an instance of " + receivingEventURI + "...", e);
                    }
                } else {
                    String batchID = UUID.randomUUID().toString();
                    String propertyName = null;
                    try {
                        for (int i = 0; i < noOfEvents; i++) {
                            SimpleEvent event = events[i];
                            propertyName = "index";
                            event.setProperty(propertyName, i);
                            propertyName = "isBatchStart";
                            event.setProperty(propertyName, i == 0);
                            propertyName = "isBatchEnd";
                            event.setProperty(propertyName, i+1 == noOfEvents);
                            propertyName = "batchSize";
                            event.setProperty(propertyName, noOfEvents);
                            propertyName = "batchId";
                            event.setProperty(propertyName, batchID);
                            channelManager.sendEvent(event, event.getDestinationURI(), null);
                        }
                    } catch (NoSuchFieldException e) {
                        logger.log(Level.WARN, "could not find field named " + propertyName + " in " + receivingEventURI, e);
                    } catch (Exception e) {
                        if (propertyName != null) {
                            logger.log(Level.WARN, "could not set property named " + propertyName + " on " + receivingEventURI, e);
                        }
                        logger.log(Level.WARN, "could not send an instance of " + receivingEventURI + "...", e);
                    }
                }
            }
        }

        private SimpleEvent[] getPopulatedEvents() throws IOException {
        	if(handler == null){
        		return null;
        	}
            logger.log(Level.DEBUG, "Populating " + receivingEventURI + " for " + handler.getType() + "...");
            SimpleEvent[] events = handler.populate(eventCreator);
            if (events != null) {
                String propertyName = null;
                try {
                    for (SimpleEvent event : events) {
                        propertyName = "name";
                        event.setProperty(propertyName, name);
                        propertyName = "snapshottime";
                        event.setProperty(propertyName, System.currentTimeMillis());
                        propertyName = "type";
                        event.setProperty(propertyName,handler.getType());
                    }
                } catch (NoSuchFieldException e) {
                    logger.log(Level.WARN, "could not find field named " + propertyName + " in " + receivingEventURI, e);
                    events = null;
                } catch (Exception e) {
                    logger.log(Level.WARN, "could not set property named " + propertyName + " on " + receivingEventURI, e);
                    events = null;
                }
            }
            return events;
        }
    }
}
