package com.tibco.cep.query.stream.impl.expression.creation;

import com.tibco.be.functions.object.ObjectHelper;
import com.tibco.cep.query.stream.context.GlobalContext;
import com.tibco.cep.query.stream.context.QueryContext;
import com.tibco.cep.query.stream.expression.ExpressionEvaluator;
import com.tibco.cep.query.stream.tuple.Tuple;
import com.tibco.cep.query.stream.util.FixedKeyHashMap;
import com.tibco.cep.runtime.model.element.Concept;
import com.tibco.cep.runtime.model.element.Property;
import com.tibco.cep.runtime.model.element.PropertyArray;
import com.tibco.cep.runtime.model.element.PropertyAtom;

import java.io.Serializable;
import java.util.Map;


/**
 * User: nprade
 * Date: 6/26/12
 * Time: 7:04 PM
 */
public class NewConceptInstanceEvaluator
        implements ExpressionEvaluator, Serializable {


    private ExpressionEvaluator extIdEvaluator;
    private Map<String, ExpressionEvaluator> fieldEvaluators;
    private String uri;


    public NewConceptInstanceEvaluator(
            String uri,
            Map<String, ExpressionEvaluator> fieldEvaluators) {

        this.uri = uri;
        this.extIdEvaluator = fieldEvaluators.get("extId");
        this.fieldEvaluators = fieldEvaluators;
    }


    public Object evaluate(
            GlobalContext globalContext,
            QueryContext queryContext,
            FixedKeyHashMap<String, ? extends Tuple> aliasAndTuples) {

        final String extId = (null == extIdEvaluator)
                ? null
                : (String) extIdEvaluator.evaluate(globalContext, queryContext, aliasAndTuples);

        final Concept instance;
        try {
            instance = ObjectHelper.newInstance(this.uri, extId);
        }
        catch (Exception e) {
            throw new RuntimeException("Failed to create Concept instance", e);
        }

        for (final Map.Entry<String, ExpressionEvaluator> e : fieldEvaluators.entrySet()) {
            final String propName = e.getKey();
            if (!"extId".equals(propName)) {
                final Property property = instance.getProperty(propName);
                if (property instanceof PropertyArray) {
                    //todo initialize PropertyArray value!!!
                }
                else {
                    ((PropertyAtom) property).setValue(
                            e.getValue().evaluate(globalContext, queryContext, aliasAndTuples));
                }
            }
        }


        return instance;
    }


    public boolean evaluateBoolean(
            GlobalContext globalContext,
            QueryContext queryContext,
            FixedKeyHashMap<String, ? extends Tuple> aliasAndTuples) {
        throw new UnsupportedOperationException();
    }


    public double evaluateDouble(
            GlobalContext globalContext,
            QueryContext queryContext,
            FixedKeyHashMap<String, ? extends Tuple> aliasAndTuples) {
        throw new UnsupportedOperationException();
    }


    public float evaluateFloat(
            GlobalContext globalContext,
            QueryContext queryContext,
            FixedKeyHashMap<String, ? extends Tuple> aliasAndTuples) {
        throw new UnsupportedOperationException();
    }


    public int evaluateInteger(
            GlobalContext globalContext,
            QueryContext queryContext,
            FixedKeyHashMap<String, ? extends Tuple> aliasAndTuples) {
        throw new UnsupportedOperationException();
    }


    public long evaluateLong(
            GlobalContext globalContext,
            QueryContext queryContext,
            FixedKeyHashMap<String, ? extends Tuple> aliasAndTuples) {
        throw new UnsupportedOperationException();
    }


}