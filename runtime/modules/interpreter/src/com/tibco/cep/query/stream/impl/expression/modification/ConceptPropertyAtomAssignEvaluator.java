package com.tibco.cep.query.stream.impl.expression.modification;

import com.tibco.cep.query.stream.context.GlobalContext;
import com.tibco.cep.query.stream.context.QueryContext;
import com.tibco.cep.query.stream.expression.ExpressionEvaluator;
import com.tibco.cep.query.stream.impl.expression.property.ConceptPropertyAtomEvaluator;
import com.tibco.cep.query.stream.tuple.Tuple;
import com.tibco.cep.query.stream.util.FixedKeyHashMap;
import com.tibco.cep.query.utils.TypeHelper;
import com.tibco.cep.runtime.model.element.PropertyAtom;
import com.tibco.cep.runtime.model.element.PropertyAtomDateTime;
import com.tibco.cep.runtime.model.element.impl.property.simple.PropertyAtomDateTimeSimple;

import java.util.Calendar;

public class ConceptPropertyAtomAssignEvaluator
        extends AssignEvaluator {


    /**
     * @param atomEvaluator  ConceptPropertyAtomEvaluator for the property atom receiving the value.
     * @param valueEvaluator ExpressionEvaluator for the value.
     */
    public ConceptPropertyAtomAssignEvaluator(
            ConceptPropertyAtomEvaluator atomEvaluator,
            ExpressionEvaluator valueEvaluator) {
        super(atomEvaluator, valueEvaluator);
    }


    public Object evaluate(
            GlobalContext globalContext,
            QueryContext queryContext,
            FixedKeyHashMap<String, ? extends Tuple> aliasAndTuples) {

        final PropertyAtom propertyAtom = (PropertyAtom)
                this.leftEvaluator.evaluate(globalContext, queryContext, aliasAndTuples);

        Object value = this.rightEvaluator.evaluate(globalContext, queryContext, aliasAndTuples);

        if ((propertyAtom instanceof PropertyAtomDateTime)
                && (value instanceof String)) {
            value = parseDate((String) value);
        }

        propertyAtom.setValue(value);
        return propertyAtom.getValue();
    }


    public static Calendar parseDate(
            String value) {

        try {
            return PropertyAtomDateTimeSimple.objectToDateTime(value);
        }
        catch (ClassCastException ignored) {
        }
        try {
            return TypeHelper.toDateTime(value);
        }
        catch (Exception ignored) {
        }
        return TypeHelper.toDateTime(value + ".000+0000");
    }

}
