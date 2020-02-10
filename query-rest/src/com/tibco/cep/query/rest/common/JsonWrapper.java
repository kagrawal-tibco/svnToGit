package com.tibco.cep.query.rest.common;

import com.google.gson.Gson;
import com.tibco.cep.kernel.model.entity.Entity;
import com.tibco.cep.runtime.model.element.Property;
import com.tibco.cep.runtime.model.element.impl.ConceptImpl;
import com.tibco.cep.runtime.model.event.impl.SimpleEventImpl;

import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: mgharat
 * Date: 4/2/14
 * Time: 3:08 PM
 * To change this template use File | Settings | File Templates.
 */
public class JsonWrapper<T> implements TypeWrapper<T> {

    List<T> resultList;
    int queryLimit;
    int offset;
    String[] projAtrributes;
    EntityType entityType;

    JsonWrapper(int queryLimit, int offset, String[] selectAttributes, EntityType entityType) {
        this.resultList = new ArrayList<>();
        this.queryLimit = queryLimit;
        this.offset = offset;
        this.projAtrributes = selectAttributes;
        this.entityType = entityType;
    }

    @Override
     public Object convert(Iterator resultItr) throws NoSuchFieldException {

        Iterator<Map.Entry> resultIterator = resultItr;
        JsonHelper jsonHelper = new JsonHelper();

        for (int i = 0; resultIterator.hasNext() && i <= queryLimit; i++) {

            Entity entity = (Entity) resultIterator.next().getValue();
            Map<String, String> value = new HashMap<>();

            switch(entityType)
            {
                case Concept:
                    ConceptImpl concept = (ConceptImpl) entity;
                    Property[] properties = concept.getProperties();

                    long conceptId = concept.getId();

                    if(projAtrributes == null)
                    {
                        for (Property property : properties) {
                            String propName = property.getName();
                            value.put(propName, String.valueOf(concept.getPropertyValue(propName)));
                        }
                    }
                    else
                    {
                        for (String propName : projAtrributes) {
                            value.put(propName, String.valueOf(concept.getPropertyValue(propName)));
                        }
                    }

                    jsonHelper.put(conceptId, value);

                   break;

                case Event:

                    SimpleEventImpl event= (SimpleEventImpl) entity;
                    String propertyNames[] = event.getPropertyNames();

                    long eventId = event.getId();

                    if(projAtrributes == null)
                    {
                        for (String propName : propertyNames)  {
                            value.put(propName, String.valueOf(event.getPropertyValue(propName)));
                        }
                    }
                    else
                    {
                        for (String propName : projAtrributes)  {
                            value.put(propName, String.valueOf(event.getPropertyValue(propName)));
                        }
                    }

                    jsonHelper.put(eventId, value);

                    break;

            }
        }
        Gson gson = new Gson();
        String json = gson.toJson(jsonHelper);

        return json;
    }
}
