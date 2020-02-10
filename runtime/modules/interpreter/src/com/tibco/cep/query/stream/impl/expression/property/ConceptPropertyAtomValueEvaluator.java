package com.tibco.cep.query.stream.impl.expression.property;

import com.tibco.cep.query.stream.context.GlobalContext;
import com.tibco.cep.query.stream.context.QueryContext;
import com.tibco.cep.query.stream.expression.ExpressionEvaluator;
import com.tibco.cep.query.stream.tuple.Tuple;
import com.tibco.cep.query.stream.util.FixedKeyHashMap;
import com.tibco.cep.runtime.model.element.PropertyAtom;

/*
* Created by IntelliJ IDEA.
* User: nprade
* Date: Jul 29, 2008
* Time: 5:47:59 PM
*/
public class ConceptPropertyAtomValueEvaluator extends PropertyEvaluator {


    /**
     * @param containerEvaluator ExpressionEvaluator for the container of the property.
     * @param propertyName       String name of the property.
     */
    public ConceptPropertyAtomValueEvaluator(ExpressionEvaluator containerEvaluator, String propertyName) {
        super(new ConceptPropertyAtomEvaluator(containerEvaluator, propertyName), propertyName);
    }


    /**
     * @param propertyAtomEvaluator ConceptPropertyAtomEvaluator for the propertyAtom.
     */
    public ConceptPropertyAtomValueEvaluator(ConceptPropertyAtomEvaluator propertyAtomEvaluator) {
        super(propertyAtomEvaluator, propertyAtomEvaluator.getPropertyName());
    }


    public Object evaluate(GlobalContext globalContext, QueryContext queryContext, FixedKeyHashMap<String, ? extends Tuple> aliasAndTuples) {
        final Object container = this.containerEvaluator.evaluate(globalContext, queryContext, aliasAndTuples);
        return ((PropertyAtom) container).getValue();
    }


    public boolean evaluateBoolean(GlobalContext globalContext, QueryContext queryContext, FixedKeyHashMap<String, ? extends Tuple> aliasAndTuples) {
        final Object container = this.containerEvaluator.evaluate(globalContext, queryContext, aliasAndTuples);
        return (Boolean) ((PropertyAtom) container).getValue();
    }


    public double evaluateDouble(GlobalContext globalContext, QueryContext queryContext, FixedKeyHashMap<String, ? extends Tuple> aliasAndTuples) {
        final Object container = this.containerEvaluator.evaluate(globalContext, queryContext, aliasAndTuples);
        return ((Number) ((PropertyAtom) container).getValue()).doubleValue();
    }


    public float evaluateFloat(GlobalContext globalContext, QueryContext queryContext, FixedKeyHashMap<String, ? extends Tuple> aliasAndTuples) {
        final Object container = this.containerEvaluator.evaluate(globalContext, queryContext, aliasAndTuples);
        return ((Number) ((PropertyAtom) container).getValue()).floatValue();
    }


    public int evaluateInteger(GlobalContext globalContext, QueryContext queryContext, FixedKeyHashMap<String, ? extends Tuple> aliasAndTuples) {
        final Object container = this.containerEvaluator.evaluate(globalContext, queryContext, aliasAndTuples);
        return ((Number) ((PropertyAtom) container).getValue()).intValue();
    }


    public long evaluateLong(GlobalContext globalContext, QueryContext queryContext, FixedKeyHashMap<String, ? extends Tuple> aliasAndTuples) {
        final Object container = this.containerEvaluator.evaluate(globalContext, queryContext, aliasAndTuples);
        return ((Number) ((PropertyAtom) container).getValue()).longValue();
    }


}
