package com.tibco.cep.query.stream.impl.rete.integ.filter.coherence;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicReference;

import com.tibco.cep.designtime.model.element.Concept;
import com.tibco.cep.designtime.model.element.PropertyDefinition;
import com.tibco.cep.designtime.model.event.Event;
import com.tibco.cep.designtime.model.event.EventPropertyDefinition;
import com.tibco.cep.query.stream.core.Component;
import com.tibco.cep.query.stream.monitor.ResourceId;

/*
* Author: Karthikeyan Subramanian / Date: May 3, 2010 / Time: 2:25:44 PM
*/
public class FilterHelperCache implements Component {

    private ResourceId resourceId;
    private final AtomicReference<IndexManager> indexManagerRef = new AtomicReference<IndexManager>();
    private final ConcurrentMap<Concept, ConcurrentMap<String, PropertyDefinition>> conceptProperties =
            new ConcurrentHashMap<Concept, ConcurrentMap<String, PropertyDefinition>>();
    private final ConcurrentMap<Event, ConcurrentMap<String, EventPropertyDefinition>> eventProperties =
            new ConcurrentHashMap<Event, ConcurrentMap<String, EventPropertyDefinition>>();

    public FilterHelperCache() {
        this.resourceId = new ResourceId(FilterHelperCache.class.getName());
    }

    @Override
    public void init(Properties properties) throws Exception {
        indexManagerRef.set(null);
    }

    @Override
    public void discard() throws Exception {
        indexManagerRef.set(null);
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

    public IndexManager setIndexManager(IndexManager indexManager) {
        if (indexManagerRef.compareAndSet(null, indexManager) == false) {
            return indexManagerRef.get();
        }
        return indexManager;
    }

    public IndexManager getIndexManager() {
        return indexManagerRef.get();
    }

    public Map<String, EventPropertyDefinition> addEventProperties(Event event) {
        if (event != null) {
            ConcurrentMap<String, EventPropertyDefinition> properties = new ConcurrentHashMap<String, EventPropertyDefinition>();
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
            List propertyDefinitions = concept.getLocalPropertyDefinitions();

            //----Entering Super Concept Properties if inheritance exist-----
            if (concept.getSuperConcept() != null) {
                Concept parentConcept = concept.getSuperConcept();
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
