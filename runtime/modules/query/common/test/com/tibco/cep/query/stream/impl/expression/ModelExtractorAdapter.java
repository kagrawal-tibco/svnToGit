package com.tibco.cep.query.stream.impl.expression;

import com.tibco.cep.query.stream.context.GlobalContext;
import com.tibco.cep.query.stream.context.QueryContext;

import java.io.Serializable;

/*
* Author: Ashwin Jayaprakash Date: Jun 10, 2008 Time: 6:49:31 PM
*/
public class ModelExtractorAdapter implements Serializable {
    private static final long serialVersionUID = 1L;

    protected ModelExtractor modelExtractor;

    public ModelExtractorAdapter(ModelExtractor modelExtractor) {
        this.modelExtractor = modelExtractor;
    }

    public ModelExtractor getModelExtractor() {
        return modelExtractor;
    }

    public boolean extractBoolean(GlobalContext globalContext, QueryContext queryContext,
                                  Object object) {
        ModelType lastType = modelExtractor.getSourceType();
        JavaType lastJavaType = lastType.getJavaTypeOfExtractableValue();
        if (lastJavaType == JavaType.BOOLEAN) {
            return modelExtractor.extractBoolean(globalContext, queryContext, object);
        }

        throw new UnsupportedOperationException(
                "Mismatched extractor type. Valid type is: " +
                        JavaType.BOOLEAN.getJavaClass().getName());
    }

    public int extractInteger(GlobalContext globalContext, QueryContext queryContext,
                              Object object) {
        ModelType lastType = modelExtractor.getSourceType();
        JavaType lastJavaType = lastType.getJavaTypeOfExtractableValue();
        switch (lastJavaType) {
            case BYTE:
            case SHORT:
            case INTEGER:
                return modelExtractor.extractInteger(globalContext, queryContext, object);

            case FLOAT:
                return (int) modelExtractor.extractFloat(globalContext, queryContext, object);

            case LONG:
                return (int) modelExtractor.extractLong(globalContext, queryContext, object);

            case DOUBLE:
                return (int) modelExtractor.extractDouble(globalContext, queryContext, object);
        }

        throw new UnsupportedOperationException(
                "Mismatched extractor type. Valid type is: " +
                        JavaType.INTEGER.getJavaClass().getName());
    }

    public float extractFloat(GlobalContext globalContext, QueryContext queryContext,
                              Object object) {
        ModelType lastType = modelExtractor.getSourceType();
        JavaType lastJavaType = lastType.getJavaTypeOfExtractableValue();
        switch (lastJavaType) {
            case BYTE:
            case SHORT:
            case INTEGER:
                return modelExtractor.extractInteger(globalContext, queryContext, object);

            case FLOAT:
                return modelExtractor.extractFloat(globalContext, queryContext, object);

            case LONG:
                return modelExtractor.extractLong(globalContext, queryContext, object);

            case DOUBLE:
                return (float) modelExtractor
                        .extractDouble(globalContext, queryContext, object);
        }

        throw new UnsupportedOperationException(
                "Mismatched extractor type. Valid type is: " +
                        JavaType.FLOAT.getJavaClass().getName());
    }

    public long extractLong(GlobalContext globalContext, QueryContext queryContext,
                            Object object) {
        ModelType lastType = modelExtractor.getSourceType();
        JavaType lastJavaType = lastType.getJavaTypeOfExtractableValue();
        switch (lastJavaType) {
            case BYTE:
            case SHORT:
            case INTEGER:
                return modelExtractor.extractInteger(globalContext, queryContext, object);

            case FLOAT:
                return (long) modelExtractor.extractFloat(globalContext, queryContext, object);

            case LONG:
                return modelExtractor.extractLong(globalContext, queryContext, object);

            case DOUBLE:
                return (long) modelExtractor.extractDouble(globalContext, queryContext, object);
        }

        throw new UnsupportedOperationException(
                "Mismatched extractor type. Valid type is: " +
                        JavaType.LONG.getJavaClass().getName());
    }

    public double extractDouble(GlobalContext globalContext, QueryContext queryContext,
                                Object object) {
        ModelType lastType = modelExtractor.getSourceType();
        JavaType lastJavaType = lastType.getJavaTypeOfExtractableValue();
        switch (lastJavaType) {
            case BYTE:
            case SHORT:
            case INTEGER:
                return modelExtractor.extractInteger(globalContext, queryContext, object);

            case FLOAT:
                return modelExtractor.extractFloat(globalContext, queryContext, object);

            case LONG:
                return modelExtractor.extractLong(globalContext, queryContext, object);

            case DOUBLE:
                return modelExtractor.extractDouble(globalContext, queryContext, object);
        }

        throw new UnsupportedOperationException(
                "Mismatched extractor type. Valid type is: " +
                        JavaType.DOUBLE.getJavaClass().getName());
    }

    public String extractString(GlobalContext globalContext, QueryContext queryContext,
                                Object object) {
        ModelType lastType = modelExtractor.getSourceType();
        JavaType lastJavaType = lastType.getJavaTypeOfExtractableValue();
        if (lastJavaType == JavaType.STRING) {
            return modelExtractor.extractString(globalContext, queryContext, object);
        }

        throw new UnsupportedOperationException(
                "Mismatched extractor type. Valid type is: " +
                        JavaType.STRING.getJavaClass().getName());
    }

    public Object extractObject(GlobalContext globalContext, QueryContext queryContext,
                                Object object) {
        ModelType lastType = modelExtractor.getSourceType();
        JavaType lastJavaType = lastType.getJavaTypeOfExtractableValue();
        switch (lastJavaType) {
            case BOOLEAN:
                return modelExtractor.extractBoolean(globalContext, queryContext, object);

            case BYTE:
            case SHORT:
            case INTEGER:
                return modelExtractor.extractInteger(globalContext, queryContext, object);

            case FLOAT:
                return modelExtractor.extractFloat(globalContext, queryContext, object);

            case LONG:
                return modelExtractor.extractLong(globalContext, queryContext, object);

            case DOUBLE:
                return modelExtractor.extractDouble(globalContext, queryContext, object);

            case STRING:
                return modelExtractor.extractString(globalContext, queryContext, object);

            case CALENDAR:
                return modelExtractor.extractDateTime(globalContext, queryContext, object);

            case OBJECT:
                return modelExtractor.extractObject(globalContext, queryContext, object);
        }

        throw new UnsupportedOperationException(
                "Mismatched extractor type. Valid type is: " +
                        JavaType.OBJECT.getJavaClass().getName());
    }
}
