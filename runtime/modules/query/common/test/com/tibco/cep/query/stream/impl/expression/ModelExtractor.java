package com.tibco.cep.query.stream.impl.expression;

import com.tibco.cep.query.stream.context.GlobalContext;
import com.tibco.cep.query.stream.context.QueryContext;
import com.tibco.cep.runtime.model.element.*;
import com.tibco.cep.runtime.model.event.SimpleEvent;

import java.io.Serializable;
import java.util.Calendar;

/*
* Author: Ashwin Jayaprakash Date: Jun 10, 2008 Time: 12:25:17 PM
*/
public class ModelExtractor implements Serializable {
    private static final long serialVersionUID = 1L;

    protected ModelType sourceType;

    protected String propertyToExtract;

    public ModelExtractor(ModelType sourceType) {
        this.sourceType = sourceType;
    }

    /**
     * @param sourceType
     * @param propertyToExtract Required if {@link ModelType#isExtractablePropertyNameNeeded()}
     *                          returns <code>true</code>.
     */
    public ModelExtractor(ModelType sourceType, String propertyToExtract) {
        this.sourceType = sourceType;
        this.propertyToExtract = propertyToExtract;
    }

    public ModelType getSourceType() {
        return sourceType;
    }

    /**
     * @return Can be <code>null</code>.
     */
    public String getPropertyToExtract() {
        return propertyToExtract;
    }

    public int extractInteger(GlobalContext globalContext, QueryContext queryContext,
                              Object source) {
        switch (sourceType) {
            case PROPERTY_ATOM_INT:
                PropertyAtomInt atomInt = (PropertyAtomInt) source;
                return atomInt.getInt();

            default:
                throw new UnsupportedOperationException(
                        "Unsupported type: " + sourceType.name() + " on: " + source.getClass());
        }
    }

    public float extractFloat(GlobalContext globalContext, QueryContext queryContext,
                              Object source) {
        switch (sourceType) {
            default:
                throw new UnsupportedOperationException(
                        "Unsupported type: " + sourceType.name() + " on: " + source.getClass());
        }
    }

    public long extractLong(GlobalContext globalContext, QueryContext queryContext,
                            Object source) {
        switch (sourceType) {
            case PROPERTY_ATOM_LONG:
                PropertyAtomLong atomLong = (PropertyAtomLong) source;
                return atomLong.getLong();

            default:
                throw new UnsupportedOperationException(
                        "Unsupported type: " + sourceType.name() + " on: " + source.getClass());
        }
    }

    public double extractDouble(GlobalContext globalContext, QueryContext queryContext,
                                Object source) {
        switch (sourceType) {
            case PROPERTY_ATOM_DOUBLE:
                PropertyAtomDouble atomDouble = (PropertyAtomDouble) source;
                return atomDouble.getDouble();

            default:
                throw new UnsupportedOperationException(
                        "Unsupported type: " + sourceType.name() + " on: " + source.getClass());
        }
    }

    public Calendar extractDateTime(GlobalContext globalContext, QueryContext queryContext,
                                    Object source) {
        switch (sourceType) {
            case PROPERTY_ATOM_DATETIME:
                PropertyAtomDateTime atomDateTime = (PropertyAtomDateTime) source;
                return atomDateTime.getDateTime();

            default:
                throw new UnsupportedOperationException(
                        "Unsupported type: " + sourceType.name() + " on: " + source.getClass());
        }
    }

    public boolean extractBoolean(GlobalContext globalContext, QueryContext queryContext,
                                  Object source) {
        switch (sourceType) {
            case PROPERTY_ATOM_BOOLEAN:
                PropertyAtomBoolean atomBoolean = (PropertyAtomBoolean) source;
                return atomBoolean.getBoolean();

            default:
                throw new UnsupportedOperationException(
                        "Unsupported type: " + sourceType.name() + " on: " + source.getClass());
        }
    }

    public String extractString(GlobalContext globalContext, QueryContext queryContext,
                                Object source) {
        switch (sourceType) {
            case PROPERTY_ATOM_STRING:
                PropertyAtomString atomString = (PropertyAtomString) source;
                return atomString.getString();

            default:
                throw new UnsupportedOperationException(
                        "Unsupported type: " + sourceType.name() + " on: " + source.getClass());
        }
    }

    public Object extractObject(GlobalContext globalContext, QueryContext queryContext,
                                Object source) {
        switch (sourceType) {
            case CONCEPT:
                Concept concept = (Concept) source;
                return concept.getProperty(propertyToExtract);

            case SIMPLE_EVENT:
                try {
                    SimpleEvent simpleEvent = (SimpleEvent) source;

                    return simpleEvent.getProperty(propertyToExtract);
                }
                catch (NoSuchFieldException e) {
                    throw new RuntimeException(e);
                }

            case PROPERTY_ATOM_CONCEPT:
            case PROPERTY_ATOM_CONCEPT_REFERENCE:
            case PROPERTY_ATOM_CONTAINED_CONCEPT:
                PropertyAtomConcept atomConcept = (PropertyAtomConcept) source;
                return atomConcept.getConcept();

            default:
                throw new UnsupportedOperationException(
                        "Unsupported type: " + sourceType.name() + " on: " + source.getClass());
        }
    }
}
