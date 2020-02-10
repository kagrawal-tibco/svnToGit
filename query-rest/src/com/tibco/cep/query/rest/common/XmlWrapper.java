package com.tibco.cep.query.rest.common;

import com.tibco.cep.kernel.model.entity.Entity;
import com.tibco.cep.runtime.model.element.Property;
import com.tibco.cep.runtime.model.element.impl.ConceptImpl;
import com.tibco.cep.runtime.model.event.impl.SimpleEventImpl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: mgharat
 * Date: 4/15/14
 * Time: 1:39 PM
 * To change this template use File | Settings | File Templates.
 */
public class XmlWrapper<T> implements TypeWrapper<T> {

    List<T> resultList;
    int queryLimit;
    int offset;
    String[] projAtrributes;
    EntityType entityType;

    XmlWrapper(int queryLimit, int offset, String[] selectAttributes, EntityType entityType) {
        this.resultList = new ArrayList<>();
        this.queryLimit = queryLimit;
        this.offset = offset;
        this.projAtrributes = selectAttributes;
        this.entityType = entityType;
    }

    @Override
    public Object convert(Iterator resultItr) throws Exception {
        Iterator<Map.Entry> resultIterator = resultItr;

        StringBuilder resultBuilder = new StringBuilder();
        resultBuilder.append("<cacheResult>");

        for (int i = 0; resultIterator.hasNext() && i <= queryLimit; i++) {

            Entity entity = (Entity) resultIterator.next().getValue();
            Property[] properties;
            ConceptImpl concept;

            StringBuilder entry = new StringBuilder();
            entry.append("<entry>");

            switch (entityType) {

                case Concept:
                    concept = (ConceptImpl) entity;
                    properties = concept.getProperties();

                    if(projAtrributes == null)
                    {
                        for (Property property : properties) {
                            String propName = property.getName();

                            entry.append(startTag(propName));
                            entry.append(concept.getPropertyValue(propName));
                            entry.append(endTag(propName));
                        }
                    }
                    else
                    {
                        for(String propName : projAtrributes)
                        {
                            entry.append(startTag(propName));
                            entry.append(concept.getPropertyValue(propName));
                            entry.append(endTag(propName));
                        }
                    }

                    break;

                case Event:
                    SimpleEventImpl event= (SimpleEventImpl) entity;
                    String propertyNames[] = event.getPropertyNames();

                    if(projAtrributes == null)
                    {
                        for (String propName : propertyNames) {
                            entry.append(startTag(propName));
                            entry.append(event.getPropertyValue(propName));
                            entry.append(endTag(propName));
                        }
                    }
                    else
                    {
                        for(String propName : projAtrributes)
                        {
                            entry.append(startTag(propName));
                            entry.append(event.getPropertyValue(propName));
                            entry.append(endTag(propName));
                        }
                    }

//                    for(String property : propertyNames)
//                    {
//                        resultBuilder.append("<");
//                        resultBuilder.append(property);
//                        resultBuilder.append(">");
//
//                        resultBuilder.append(event.getPropertyValue(property));
//
//                        resultBuilder.append("</");
//                        resultBuilder.append(property);
//                        resultBuilder.append(">");
//                    }

                    break;
            }


//            for (Property property : properties) {
//                String propName = property.getName();
//
//                resultBuilder.append("<");
//                resultBuilder.append(propName);
//                resultBuilder.append(">");
//
//                resultBuilder.append(concept.getPropertyValue(propName));
//
//                resultBuilder.append("</");
//                resultBuilder.append(propName);
//                resultBuilder.append(">");
//            }




            entry.append("</entry>");
            resultBuilder.append(entry.toString());
        }

        resultBuilder.append("</cacheResult>");

       // resultList.add((T) resultBuilder.toString());
       // return resultList;

        return resultBuilder.toString();
    }


    private String startTag(String propName)
    {
        StringBuilder sBuilder = new StringBuilder();

        sBuilder.append("<");
        sBuilder.append(propName);
        sBuilder.append(">");

        return sBuilder.toString();
    }

    private String endTag(String propName)
    {
        StringBuilder sBuilder = new StringBuilder();

        sBuilder.append("</");
        sBuilder.append(propName);
        sBuilder.append(">");

        return sBuilder.toString();
    }
}

