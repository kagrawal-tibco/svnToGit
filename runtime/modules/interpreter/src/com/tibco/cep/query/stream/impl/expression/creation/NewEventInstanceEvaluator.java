package com.tibco.cep.query.stream.impl.expression.creation;

import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.util.Map;

import com.tibco.cep.kernel.model.entity.Event;
import com.tibco.cep.query.stream.context.GlobalContext;
import com.tibco.cep.query.stream.context.QueryContext;
import com.tibco.cep.query.stream.expression.ExpressionEvaluator;
import com.tibco.cep.query.stream.tuple.Tuple;
import com.tibco.cep.query.stream.util.FixedKeyHashMap;
import com.tibco.cep.runtime.model.element.Property;
import com.tibco.cep.runtime.model.element.PropertyArray;
import com.tibco.cep.runtime.model.element.PropertyAtom;
import com.tibco.cep.runtime.session.RuleSession;
import com.tibco.cep.runtime.session.RuleSessionManager;


/**
 * User: nprade
 * Date: 6/26/12
 * Time: 7:04 PM
 */
public class NewEventInstanceEvaluator
        implements ExpressionEvaluator, Serializable {


    private ExpressionEvaluator extIdEvaluator;
    private Map<String, ExpressionEvaluator> fieldEvaluators;
    private String uri;


    public NewEventInstanceEvaluator(
            String uri,
            Map<String, ExpressionEvaluator> fieldEvaluators) {

        this.uri = uri;
        this.extIdEvaluator = fieldEvaluators.get("extId");
        this.fieldEvaluators = fieldEvaluators;
    }


    @SuppressWarnings({ "rawtypes", "unchecked" })
    public Object evaluate(
            GlobalContext globalContext,
            QueryContext queryContext,
            FixedKeyHashMap<String, ? extends Tuple> aliasAndTuples) {

        final String extId = (null == extIdEvaluator)
                ? null
                : (String) extIdEvaluator.evaluate(globalContext, queryContext, aliasAndTuples);

        final Event instance;
        try {
            RuleSession session = RuleSessionManager.getCurrentRuleSession();
            Class entityClass = session.getRuleServiceProvider().getTypeManager().getTypeDescriptor(uri).getImplClass();
            Constructor cons = entityClass.getConstructor(new Class[]{long.class, String.class});
            instance = (Event) cons.newInstance(new Object[]{
                    new Long(session.getRuleServiceProvider().getIdGenerator().nextEntityId(entityClass)),
                    extId});

        }
        catch (Exception e) {
            throw new RuntimeException("Could not create instance of event with URI as "+uri, e);
        }

        for (final Map.Entry<String, ExpressionEvaluator> e : fieldEvaluators.entrySet()) {
            final String propName = e.getKey();
            if (!"extId".equals(propName)) {
                try {
                    Object propValue = e.getValue().evaluate(globalContext, queryContext, aliasAndTuples);
                    instance.setPropertyValue(propName, propValue);
                } catch (Exception ex) {
                    throw new RuntimeException("could not set "+propName+" on event with URI as "+uri, ex);
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
