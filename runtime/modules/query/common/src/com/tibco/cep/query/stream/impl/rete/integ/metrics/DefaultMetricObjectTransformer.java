/*
 * Copyright (c) TIBCO Software Inc 2010.
 * All rights reserved.
 *
 * This software is confidential and proprietary information of
 * TIBCO Software Inc.
 *
 * Author: Suresh Subramani (suresh.subramani@tibco.com)
 * Date  : 13/8/2010
 */

package com.tibco.cep.query.stream.impl.rete.integ.metrics;

import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

import com.tibco.cep.kernel.model.entity.Entity;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.LogManagerFactory;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.runtime.management.CacheTable;
import com.tibco.cep.runtime.management.ManagementTable;
import com.tibco.cep.runtime.management.MetricDef;
import com.tibco.cep.runtime.management.MetricTable;
import com.tibco.cep.runtime.management.impl.metrics.Constants;
import com.tibco.cep.runtime.metrics.Data;
import com.tibco.cep.runtime.metrics.DataDef.ColumnDef;
import com.tibco.cep.runtime.model.event.SimpleEvent;
import com.tibco.cep.runtime.service.basic.AsyncWorkerServiceWatcher.AsyncWorkerService;
import com.tibco.cep.runtime.service.cluster.agent.InferenceAgent;
import com.tibco.cep.runtime.service.loader.BEClassLoader;
import com.tibco.cep.runtime.session.locks.LockManager;
import com.tibco.cep.runtime.util.FQName;

/*
* Author: Ashwin Jayaprakash Date: Mar 3, 2009 Time: 5:05:21 PM
*/
public class DefaultMetricObjectTransformer implements MetricObjectTransformer {

    protected BEClassLoader classLoader;

    protected ManagementTable managementTable;

    protected MetricTable metricTable;

    protected CacheTable cacheTable;

    protected List<EventURILookupHelper> eventLookupList;

    private Logger logger;

    public void init(Properties properties,
                     BEClassLoader classLoader,
                     ManagementTable managementTable,
                     MetricTable metricTable,
                     CacheTable cacheTable) {
        this.classLoader = classLoader;
        this.managementTable = managementTable;
        this.metricTable = metricTable;
        this.cacheTable = cacheTable;
        eventLookupList = new LinkedList<EventURILookupHelper>();

        FQName templateFQName = new FQName(Constants.KEY_TEMPLATE_CLUSTER_NAME,
                Constants.KEY_TEMPLATE_PROCESS_ID,
                LockManager.class.getSimpleName());
        eventLookupList.add(new EventURILookupHelper(templateFQName, 2, LockManager.class.getSimpleName(), "/Monitoring/Events/bemetrics/ConcurrentLockMetricEvent"));

        templateFQName = new FQName(Constants.KEY_TEMPLATE_CLUSTER_NAME,
                Constants.KEY_TEMPLATE_PROCESS_ID,
                Constants.KEY_TEMPLATE_AGENT_NAME,
                Constants.KEY_TEMPLATE_AGENT_ID,
                AsyncWorkerService.class.getSimpleName(),
                "%asyncWorkerName%");
        eventLookupList.add(new EventURILookupHelper(templateFQName, 4, AsyncWorkerService.class.getSimpleName(), "/Monitoring/Events/bemetrics/ThreadAndJobPoolMetricEvent"));

        templateFQName = new FQName(Constants.KEY_TEMPLATE_CLUSTER_NAME,
                Constants.KEY_TEMPLATE_PROCESS_ID,
                Constants.KEY_TEMPLATE_AGENT_NAME,
                Constants.KEY_TEMPLATE_AGENT_ID,
                InferenceAgent.class.getSimpleName(),
                "%inReteCallType%", "%inReteCallIndentifier");
        eventLookupList.add(new EventURILookupHelper(templateFQName, 5, "Rule", "/Monitoring/Events/bemetrics/RuleOrRuleFuncMetricEvent"));

        templateFQName = new FQName(Constants.KEY_TEMPLATE_CLUSTER_NAME,
                Constants.KEY_TEMPLATE_PROCESS_ID,
                Constants.KEY_TEMPLATE_AGENT_NAME,
                Constants.KEY_TEMPLATE_AGENT_ID,
                InferenceAgent.class.getSimpleName(),
                "%inReteCallType%", "%inReteCallIndentifier");
        eventLookupList.add(new EventURILookupHelper(templateFQName, 6, "Rete", "/Monitoring/Events/bemetrics/PostRTCMetricEvent"));

        logger = LogManagerFactory.getLogManager().getLogger(getClass());
    }

    public Entity transform(FQName name, Data data) {
        logger.log(Level.DEBUG, "Received "+name+"...");
        EventURILookupHelper eventURILookupHelper = getEventURI(name);
        if (eventURILookupHelper != null) {
            SimpleEvent event;
            try {
                event = (SimpleEvent) classLoader.createEntity(eventURILookupHelper.eventURI);
            } catch (Exception e) {
                logger.log(Level.ERROR, "could not create an instance of " + eventURILookupHelper.eventURI);
                return null;
            }
            String propertyName = "hostName";
            try {
                logger.log(Level.TRACE, "Setting '"+propertyName+"' on an instance of "+eventURILookupHelper.eventURI+" to "+eventURILookupHelper.getHostingEntity(name));
                event.setProperty(propertyName, eventURILookupHelper.getHostingEntity(name));
                propertyName = "metricName";
                logger.log(Level.TRACE, "Setting '"+propertyName+"' on an instance of "+eventURILookupHelper.eventURI+" to "+eventURILookupHelper.getMetricName(name));
                event.setProperty(propertyName, eventURILookupHelper.getMetricName(name));
            } catch (Exception e) {
                logger.log(Level.ERROR, e, "could not set '" + propertyName + "' on an instance of " + eventURILookupHelper.eventURI);
                return null;
            }
            ColumnDef[] columnDefs = eventURILookupHelper.getColumnDefinitions(metricTable);
            Object[] columnData = data.getColumns();
            for (int i = 0; i < columnDefs.length; i++) {
                ColumnDef columnDef = columnDefs[i];
                String columnName = columnDef.getName();
                if (columnName.equalsIgnoreCase("count") == true) {
                    columnName = "cnt";
                }
                if (columnDef.getNestedTypes() == null) {
                    try {
                        logger.log(Level.TRACE, "Setting '"+columnName+"' on an instance of "+eventURILookupHelper.eventURI+" to "+columnData[i]);
                        event.setProperty(columnName, columnData[i]);
                    } catch (Exception e) {
                        logger.log(Level.ERROR, e, "could not set '" + columnName + "' on an instance of " + eventURILookupHelper.eventURI);
                    }
                } else {
                    //deal with nested types later
                    logger.log(Level.TRACE, "cannot handle nested type of '" + columnName + "' in data definition for " + eventURILookupHelper.templateFQName);
                }
            }
            logger.log(Level.DEBUG, "Firing "+event);
            return event;
        }
        return null;
    }

    protected EventURILookupHelper getEventURI(FQName name) {
        String[] nameComponents = name.getComponentNames();
        for (EventURILookupHelper lookupHelper : eventLookupList) {
            if (lookupHelper.matches(nameComponents) == true) {
                return lookupHelper;
            }
        }
        return null;
    }

    public void discard() {
        eventLookupList.clear();
    }

    class EventURILookupHelper {

        private int location;
        private String locationMatch;
        private String eventURI;
        private FQName templateFQName;
        private ColumnDef[] columnDefs;

        EventURILookupHelper(FQName templateFQName, int location, String locationMatch, String eventURI) {
            if (location >= templateFQName.getComponentNames().length) {
                throw new IllegalArgumentException(String.format("location[%d] is not valid with the provided template FQName[%s]", location, templateFQName.toString()));
            }
            this.templateFQName = templateFQName;
            this.location = location;
            this.locationMatch = locationMatch;
            this.eventURI = eventURI;
            this.columnDefs = null;
        }

        boolean matches(String[] nameComponents) {
            if (location >= nameComponents.length) {
                return false;
            }
            return nameComponents[location].equals(locationMatch);
        }

        ColumnDef[] getColumnDefinitions(MetricTable definitionTable) {
            if(columnDefs == null){
                MetricDef metricDef = definitionTable.getMetricDef(templateFQName);
                columnDefs = metricDef.getDataDef().getColumnDefs();
            }
            if (columnDefs.length == 1) {
                return columnDefs[0].getNestedTypes();
            }
            return columnDefs;
        }

        String getHostingEntity(FQName name) {
            String[] cNames = name.getComponentNames();
            FQName monitoredFQName = null;
            for (int i = 0; i < location; i++) {
                if (monitoredFQName == null) {
                    monitoredFQName = new FQName(cNames[i]);
                } else {
                    monitoredFQName = new FQName(monitoredFQName, cNames[i]);
                }
            }
            return (monitoredFQName == null) ? "" : monitoredFQName.toString();
        }

        String getMetricName(FQName name) {
            String[] cNames = name.getComponentNames();
            FQName metricName = null;
            if (location + 1 < cNames.length) {
                for (int i = location + 1; i < cNames.length; i++) {
                    if (metricName == null) {
                        metricName = new FQName(cNames[i]);
                    } else {
                        metricName = new FQName(metricName, cNames[i]);
                    }
                }
            } else {
                metricName = new FQName(cNames[location]);
            }
            return (metricName == null) ? "" : metricName.toString();
        }

    }
}
