package com.tibco.cep.runtime.service.cluster.util;

import java.util.Collection;
import java.util.Properties;

import com.tibco.cep.designtime.model.Ontology;
import com.tibco.cep.designtime.model.event.Event;
import com.tibco.cep.repo.GlobalVariables;
import com.tibco.cep.runtime.model.TypeManager;
import com.tibco.cep.runtime.model.TypeManager.TypeDescriptor;
import com.tibco.cep.runtime.model.event.SimpleEvent;
import com.tibco.cep.runtime.model.event.TimeEvent;
import com.tibco.cep.runtime.service.cluster.Cluster;
import com.tibco.cep.runtime.session.RuleServiceProvider;

/*
* Author: Ashwin Jayaprakash / Date: 8/11/11 / Time: 10:40 AM
*/
public class TypeHelper {
    /**
     * @param cluster
     * @param eventClassName
     * @return null if not found.
     */
    public static Class $mapNameToClass(Cluster cluster, String eventClassName) {
        RuleServiceProvider rsp = cluster.getRuleServiceProvider();
        Ontology ontology = rsp.getProject().getOntology();
        TypeManager typeManager = rsp.getTypeManager();

        Collection<Event> events = ontology.getEvents();
        for (Event event : events) {
            String uri = event.getFullPath();
            TypeDescriptor typeDescriptor = typeManager.getTypeDescriptor(uri);
            Class clazz = typeDescriptor.getImplClass();

            if (clazz.getName().equals(eventClassName)) {
                return clazz;
            }
        }

        return null;
    }

    public static boolean $isTimeBased(Class entityClass) {
        return SimpleEvent.class.isAssignableFrom(entityClass) || TimeEvent.class.isAssignableFrom(entityClass);
    }

    /**
     * @param cluster
     * @param entityClass
     * @return -1 if not applicable.
     */
    public static long $extractTTL(Cluster cluster, Class entityClass) {
        if (!$isTimeBased(entityClass)) {
            return -1;
        }

        RuleServiceProvider rsp = cluster.getRuleServiceProvider();
        Ontology ontology = rsp.getProject().getOntology();
        TypeManager typeManager = rsp.getTypeManager();
        GlobalVariables gvs = rsp.getGlobalVariables();

        Collection<Event> events = ontology.getEvents();
        for (Event event : events) {
            String uri = event.getFullPath();
            TypeDescriptor typeDescriptor = typeManager.getTypeDescriptor(uri);
            Class clazz = typeDescriptor.getImplClass();

            if (entityClass.equals(clazz)) {
                return $ttlToMillis(event, gvs);
            }
        }

        return -1;
    }

    /**
     * @param cluster
     * @param entityClass
     * @return -1 if not applicable.
     */
    public static long $extractOtherTTL(Cluster cluster, Class entityClass) {
        RuleServiceProvider rsp = cluster.getRuleServiceProvider();
        Properties properties = rsp.getProperties();

        String expiryStr = properties.getProperty(entityClass.getName() + ".cache.expiryMillis", "-1");
        return Long.parseLong(expiryStr);
    }

    public static long $ttlToMillis(Event event, GlobalVariables gvs) {
    	int ttl = Event.DEFAULT_TTL;
    	try {
    		String ttlStr = event.getTTL();
    		ttlStr = gvs.substituteVariables(ttlStr).toString();
    		ttl = Integer.parseInt(ttlStr);
    	} catch (NumberFormatException nfe) {
    		nfe.printStackTrace();
    	}
    	
    	int units = event.getTTLUnits();
        long multiplier = 1;
        switch (units) {
            case Event.MILLISECONDS_UNITS:
                multiplier = 1L;
                break;
            case Event.SECONDS_UNITS:
                multiplier = 1000L;
                break;
            case Event.MINUTES_UNITS:
                multiplier = 60 * 1000L;
                break;
            case Event.HOURS_UNITS:
                multiplier = 60 * 60 * 1000L;
                break;
            case Event.DAYS_UNITS:
                multiplier = 24 * 60 * 60 * 1000L;
                break;
            default:
                multiplier = 1L;
        }

        return (long)ttl * multiplier;
    }
}
