package com.tibco.cep.query.stream.impl.rete.integ.filter.as;

import com.tibco.cep.designtime.model.element.Concept;
import com.tibco.cep.designtime.model.element.PropertyDefinition;
import com.tibco.cep.designtime.model.event.Event;
import com.tibco.cep.designtime.model.event.EventPropertyDefinition;
import com.tibco.cep.query.stream.core.Component;
import com.tibco.cep.query.stream.monitor.ResourceId;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/*
* Author: Karthikeyan Subramanian / Date: May 3, 2010 / Time: 2:25:44 PM
*/
public class FilterHelperCache implements Component {
    private ResourceId resourceId;

    private final ConcurrentMap<Concept, ConcurrentMap<String, PropertyDefinition>> conceptProperties =
            new ConcurrentHashMap<Concept, ConcurrentMap<String, PropertyDefinition>>();

    private final ConcurrentMap<Event, ConcurrentMap<String, EventPropertyDefinition>> eventProperties =
            new ConcurrentHashMap<Event, ConcurrentMap<String, EventPropertyDefinition>>();

    public FilterHelperCache() {
        this.resourceId = new ResourceId(FilterHelperCache.class.getName());
    }

    @Override
    public void init(Properties properties) throws Exception {
    }

    @Override
    public void discard() throws Exception {
    }

    @Override
    public void start() throws Exception {
    }

    @Override
    public void stop() throws Exception {
    }

    @Override
    public ResourceId getResourceId() {
        return resourceId;
    }

    public Map<String, EventPropertyDefinition> addEventProperties(Event event) {
        if (event != null) {
            ConcurrentMap<String, EventPropertyDefinition> properties =
                    new ConcurrentHashMap<String, EventPropertyDefinition>();
            ConcurrentMap<String, EventPropertyDefinition> oldProps = eventProperties.putIfAbsent(event, properties);
            if (oldProps != null) {
                properties = oldProps;
            }
            Iterator<? extends EventPropertyDefinition> iterator = event.getUserProperties();
            while (iterator.hasNext()) {
                EventPropertyDefinition propertyDefinition = iterator.next();
                properties.putIfAbsent(propertyDefinition.getPropertyName(), propertyDefinition);
            }
            return Collections.unmodifiableMap(properties);
        }
        return Collections.emptyMap();
    }

    public Map<String, PropertyDefinition> addConceptProperties(Concept concept) {
        if (concept != null) {
            ConcurrentMap<String, PropertyDefinition> properties = new ConcurrentHashMap<String, PropertyDefinition>();
            ConcurrentMap<String, PropertyDefinition> oldProps = conceptProperties.putIfAbsent(concept, properties);
            if (oldProps != null) {
                properties = oldProps;
            }
            List propertyDefinitions = concept.getPropertyDefinitions(false);

            //-----Entering Super Concept Properties if inheritance exist-----
            if (concept.getSuperConcept() != null) {
             	Concept parentConcept = concept.getParentConcept();
             	propertyDefinitions.addAll(parentConcept.getLocalPropertyDefinitions());
            }

            for (Object propertyDefinition : propertyDefinitions) {
                PropertyDefinition propertyDefn = (PropertyDefinition) propertyDefinition;
                properties.putIfAbsent(propertyDefn.getName(), propertyDefn);
            }
            return Collections.unmodifiableMap(properties);
        }
        return Collections.emptyMap();
    }

    public Map<String, PropertyDefinition> getConceptProperties(Concept concept) {
        return conceptProperties.get(concept);
    }

    public Map<String, EventPropertyDefinition> getEventProperties(Event event) {
        return eventProperties.get(event);
    }
}
