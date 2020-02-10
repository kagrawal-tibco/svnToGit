package com.tibco.cep.query.stream.impl.expression;

import com.tibco.cep.query.stream.context.GlobalContext;
import com.tibco.cep.query.stream.context.QueryContext;
import com.tibco.cep.query.stream.expression.ExpressionEvaluator;
import com.tibco.cep.query.stream.tuple.Tuple;
import com.tibco.cep.query.stream.util.FixedKeyHashMap;

import java.io.Serializable;

/*
* Author: Ashwin Jayaprakash Date: Jun 9, 2008 Time: 4:17:11 PM
*/
public class SimpleEvaluator implements ExpressionEvaluator, Serializable {
    private static final long serialVersionUID = 1L;

    protected ExpressionEvaluator lhs;

    protected ExpressionEvaluator rhs;

    protected JavaType sourceType;

    protected Operation operation;

    /**
     * @param lhs
     * @param rhs
     * @param sourceType The data type to which the left and right expression results should be
     *                   upcasted to.
     * @param operation
     */
    public SimpleEvaluator(ExpressionEvaluator lhs,
                           ExpressionEvaluator rhs,
                           JavaType sourceType, Operation operation) {
        this.lhs = lhs;
        this.rhs = rhs;
        this.sourceType = sourceType;
        this.operation = operation;
    }

    public ExpressionEvaluator getLhs() {
        return lhs;
    }

    public ExpressionEvaluator getRhs() {
        return rhs;
    }

    public JavaType getSourceType() {
        return sourceType;
    }

    public Operation getOperation() {
        return operation;
    }

    protected boolean compareBoolean(GlobalContext globalContext, QueryContext queryContext,
                                     FixedKeyHashMap<String, ? extends Tuple> aliasAndTuples) {
        boolean leftVal = lhs.evaluateBoolean(globalContext, queryContext, aliasAndTuples);
        boolean rightVal = rhs.evaluateBoolean(globalContext, queryContext, aliasAndTuples);

        switch (operation) {
            case EQUAL:
                return leftVal == rightVal;

            case NOT_EQUAL:
                return leftVal != rightVal;

            default:
                throw new UnsupportedOperationException(
                        "Unrecognized operation: " + operation.name());
        }
    }

    protected boolean compareInteger(GlobalContext globalContext, QueryContext queryContext,
                                     FixedKeyHashMap<String, ? extends Tuple> aliasAndTuples) {
        int leftVal = lhs.evaluateInteger(globalContext, queryContext, aliasAndTuples);
        int rightVal = rhs.evaluateInteger(globalContext, queryContext, aliasAndTuples);

        switch (operation) {
            case GREATER:
                return leftVal > rightVal;

            case GREATER_OR_EQUAL:
                return leftVal >= rightVal;

            case EQUAL:
                return leftVal == rightVal;

            case NOT_EQUAL:
                return leftVal != rightVal;

            case LESS:
                return leftVal < rightVal;

            case LESS_OR_EQUAL:
                return leftVal <= rightVal;

            default:
                throw new UnsupportedOperationException(
                        "Unrecognized operation: " + operation.name());
        }
    }

    protected boolean compareLong(GlobalContext globalContext, QueryContext queryContext,
                                  FixedKeyHashMap<String, ? extends Tuple> aliasAndTuples) {
        long leftVal = lhs.evaluateLong(globalContext, queryContext, aliasAndTuples);
        long rightVal = rhs.evaluateLong(globalContext, queryContext, aliasAndTuples);

        switch (operation) {
            case GREATER:
                return leftVal > rightVal;

            case GREATER_OR_EQUAL:
                return leftVal >= rightVal;

            case EQUAL:
                return leftVal == rightVal;

            case NOT_EQUAL:
                return leftVal != rightVal;

            case LESS:
                return leftVal < rightVal;

            case LESS_OR_EQUAL:
                return leftVal <= rightVal;

            default:
                throw new UnsupportedOperationException(
                        "Unrecognized operation: " + operation.name());
        }
    }

    protected boolean compareFloat(GlobalContext globalContext, QueryContext queryContext,
                                   FixedKeyHashMap<String, ? extends Tuple> aliasAndTuples) {
        float leftVal = lhs.evaluateFloat(globalContext, queryContext, aliasAndTuples);
        float rightVal = rhs.evaluateFloat(globalContext, queryContext, aliasAndTuples);

        switch (operation) {
            case GREATER:
                return leftVal > rightVal;

            case GREATER_OR_EQUAL:
                return leftVal >= rightVal;

            case EQUAL:
                return leftVal == rightVal;

            case NOT_EQUAL:
                return leftVal != rightVal;

            case LESS:
                return leftVal < rightVal;

            case LESS_OR_EQUAL:
                return leftVal <= rightVal;

            default:
                throw new UnsupportedOperationException(
                        "Unrecognized operation: " + operation.name());
        }
    }

    protected boolean compareDouble(GlobalContext globalContext, QueryContext queryContext,
                                    FixedKeyHashMap<String, ? extends Tuple> aliasAndTuples) {
        double leftVal = lhs.evaluateDouble(globalContext, queryContext, aliasAndTuples);
        double rightVal = rhs.evaluateDouble(globalContext, queryContext, aliasAndTuples);

        switch (operation) {
            case GREATER:
                return leftVal > rightVal;

            case GREATER_OR_EQUAL:
                return leftVal >= rightVal;

            case EQUAL:
                return leftVal == rightVal;

            case NOT_EQUAL:
                return leftVal != rightVal;

            case LESS:
                return leftVal < rightVal;

            case LESS_OR_EQUAL:
                return leftVal <= rightVal;

            default:
                throw new UnsupportedOperationException(
                        "Unrecognized operation: " + operation.name());
        }
    }

    protected boolean compareString(GlobalContext globalContext, QueryContext queryContext,
                                    FixedKeyHashMap<String, ? extends Tuple> aliasAndTuples) {
        Object left =
                lhs.evaluate(globalContext, queryContext, aliasAndTuples);
        Object right = rhs.evaluate(globalContext, queryContext, aliasAndTuples);

        String leftVal = (String) left;
        String rightVal = (String) right;

        switch (operation) {
            case GREATER:
                return leftVal.compareTo(rightVal) > 0;

            case GREATER_OR_EQUAL:
                return leftVal.compareTo(rightVal) >= 0;

            case EQUAL:
                return leftVal.compareTo(rightVal) == 0;

            case NOT_EQUAL:
                return leftVal.compareTo(rightVal) != 0;

            case LESS:
                return leftVal.compareTo(rightVal) < 0;

            case LESS_OR_EQUAL:
                return leftVal.compareTo(rightVal) <= 0;

            default:
                throw new UnsupportedOperationException(
                        "Unrecognized operation: " + operation.name());
        }
    }

    protected int calcInteger(GlobalContext globalContext, QueryContext queryContext,
                              FixedKeyHashMap<String, ? extends Tuple> aliasAndTuples) {
        int leftVal =
                lhs.evaluateInteger(globalContext, queryContext, aliasAndTuples);
        int rightVal = rhs.evaluateInteger(globalContext, queryContext, aliasAndTuples);

        switch (operation) {
            case ADD:
                return leftVal + rightVal;

            case SUBTRACT:
                return leftVal - rightVal;

            case DIVIDE:
                return leftVal / rightVal;

            case MULTIPLY:
                return leftVal * rightVal;

            default:
                throw new UnsupportedOperationException(
                        "Unrecognized operation: " + operation.name());
        }
    }

    protected long calcLong(GlobalContext globalContext, QueryContext queryContext,
                            FixedKeyHashMap<String, ? extends Tuple> aliasAndTuples) {
        long leftVal =
                lhs.evaluateLong(globalContext, queryContext, aliasAndTuples);
        long rightVal =
                rhs.evaluateLong(globalContext, queryContext, aliasAndTuples);

        switch (operation) {
            case ADD:
                return leftVal + rightVal;

            case SUBTRACT:
                return leftVal - rightVal;

            case DIVIDE:
                return leftVal / rightVal;

            case MULTIPLY:
                return leftVal * rightVal;

            default:
                throw new UnsupportedOperationException(
                        "Unrecognized operation: " + operation.name());
        }
    }

    protected float calcFloat(GlobalContext globalContext, QueryContext queryContext,
                              FixedKeyHashMap<String, ? extends Tuple> aliasAndTuples) {
        float leftVal =
                lhs.evaluateFloat(globalContext, queryContext, aliasAndTuples);
        float rightVal =
                rhs.evaluateFloat(globalContext, queryContext, aliasAndTuples);

        switch (operation) {
            case ADD:
                return leftVal + rightVal;

            case SUBTRACT:
                return leftVal - rightVal;

            case DIVIDE:
                return leftVal / rightVal;

            case MULTIPLY:
                return leftVal * rightVal;

            default:
                throw new UnsupportedOperationException(
                        "Unrecognized operation: " + operation.name());
        }
    }

    protected double calcDouble(GlobalContext globalContext, QueryContext queryContext,
                                FixedKeyHashMap<String, ? extends Tuple> aliasAndTuples) {
        double leftVal =
                lhs.evaluateDouble(globalContext, queryContext, aliasAndTuples);
        double rightVal =
                rhs.evaluateDouble(globalContext, queryContext, aliasAndTuples);

        switch (operation) {
            case ADD:
                return leftVal + rightVal;

            case SUBTRACT:
                return leftVal - rightVal;

            case DIVIDE:
                return leftVal / rightVal;

            case MULTIPLY:
                return leftVal * rightVal;

            default:
                throw new UnsupportedOperationException(
                        "Unrecognized operation: " + operation.name());
        }
    }

    protected String calcString(GlobalContext globalContext, QueryContext queryContext,
                                FixedKeyHashMap<String, ? extends Tuple> aliasAndTuples) {
        Object leftVal =
                lhs.evaluate(globalContext, queryContext, aliasAndTuples);
        Object rightVal =
                rhs.evaluate(globalContext, queryContext, aliasAndTuples);

        switch (operation) {
            case ADD:
                return (leftVal == null ? "" : leftVal.toString()) +
                        (rightVal == null ? "" : rightVal.toString());

            default:
                throw new UnsupportedOperationException(
                        "Unrecognized operation: " + operation.name());
        }
    }

    /**
     * @param globalContext
     * @param queryContext
     * @param aliasAndTuples
     * @return
     * @throws IllegalArgumentException      if the {@link #sourceType} is not supported.
     * @throws UnsupportedOperationException if the {#operation} is not supported.
     */
    public Object evaluate(GlobalContext globalContext, QueryContext queryContext,
                           FixedKeyHashMap<String, ? extends Tuple> aliasAndTuples) {
        if (operation.isComparator()) {
            return evaluateBoolean(globalContext, queryContext, aliasAndTuples);
        }

        //-------------

        switch (sourceType) {
            case BYTE:
            case SHORT:
            case INTEGER:
                return calcInteger(globalContext, queryContext, aliasAndTuples);

            case LONG:
                return calcLong(globalContext, queryContext, aliasAndTuples);

            case FLOAT:
                return calcFloat(globalContext, queryContext, aliasAndTuples);

            case DOUBLE:
                return calcDouble(globalContext, queryContext, aliasAndTuples);

            case STRING:
                return calcString(globalContext, queryContext, aliasAndTuples);

            default:
                throw new IllegalArgumentException("Unrecognized type: " + sourceType.name());
        }
    }

    /**
     * @param globalContext
     * @param queryContext
     * @param aliasAndTuples
     * @return
     * @throws IllegalArgumentException      if the {@link #sourceType} is not supported.
     * @throws UnsupportedOperationException if the {#operation} is not supported.
     */
    public boolean evaluateBoolean(GlobalContext globalContext, QueryContext queryContext,
                                   FixedKeyHashMap<String, ? extends Tuple> aliasAndTuples) {
        switch (sourceType) {
            case BYTE:
            case SHORT:
            case INTEGER:
                return compareInteger(globalContext, queryContext, aliasAndTuples);

            case LONG:
                return compareLong(globalContext, queryContext, aliasAndTuples);

            case FLOAT:
                return compareFloat(globalContext, queryContext, aliasAndTuples);

            case DOUBLE:
                return compareDouble(globalContext, queryContext, aliasAndTuples);

            case STRING:
                return compareString(globalContext, queryContext, aliasAndTuples);

            case BOOLEAN:
                return compareBoolean(globalContext, queryContext, aliasAndTuples);

            default:
                throw new IllegalArgumentException("Unrecognized type: " + sourceType.name());
        }
    }

    public int evaluateInteger(GlobalContext globalContext, QueryContext queryContext,
                               FixedKeyHashMap<String, ? extends Tuple> aliasAndTuples) {
        return calcInteger(globalContext, queryContext, aliasAndTuples);
    }

    public long evaluateLong(GlobalContext globalContext, QueryContext queryContext,
                             FixedKeyHashMap<String, ? extends Tuple> aliasAndTuples) {
        return calcLong(globalContext, queryContext, aliasAndTuples);
    }

    public float evaluateFloat(GlobalContext globalContext, QueryContext queryContext,
                               FixedKeyHashMap<String, ? extends Tuple> aliasAndTuples) {
        return calcFloat(globalContext, queryContext, aliasAndTuples);
    }

    public double evaluateDouble(GlobalContext globalContext, QueryContext queryContext,
                                 FixedKeyHashMap<String, ? extends Tuple> aliasAndTuples) {
        return calcDouble(globalContext, queryContext, aliasAndTuples);
    }
}
