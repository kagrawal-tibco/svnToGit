package com.tibco.cep.query.model.impl;

import org.antlr.runtime.tree.CommonTree;

import com.tibco.cep.query.model.BinaryOperator;
import com.tibco.cep.query.model.Expression;
import com.tibco.cep.query.model.ModelContext;
import com.tibco.cep.query.model.RangeExpression;

/**
 * Created by IntelliJ IDEA.
 * User: pdhar
 * Date: Oct 17, 2007
 * Time: 6:10:44 PM
 * To change this template use File | Settings | File Templates.
 */
public class RangeExpressionImpl extends BinaryExpressionImpl implements RangeExpression {

    public RangeExpressionImpl(ModelContext parentContext, CommonTree tree, BinaryOperator operator) {
        super(parentContext, tree, operator);
    }

    /**
     * Returns the lower boundary expression for the range
     *
     * @return Expression
     */
    public Expression getLowerBound() {
        return getLeftExpression();
    }

    /**
     * Returns the upper boundary expression for the range
     *
     * @return Expression
     */
    public Expression getUpperBound() {
        return getRightExpression();
    }
}
