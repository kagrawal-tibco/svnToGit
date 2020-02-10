package com.tibco.cep.query.model.validation.validators;

import com.tibco.cep.query.model.BinaryExpression;
import com.tibco.cep.query.model.Expression;
import com.tibco.cep.query.model.FieldList;
import com.tibco.cep.query.model.Operator;
import com.tibco.cep.query.model.TypeInfo;
import com.tibco.cep.query.model.validation.ValidationException;
import com.tibco.cep.query.model.validation.Validator;
import com.tibco.cep.query.utils.TypeHelper;

/*
 * Author: Ashwin Jayaprakash Date: Dec 7, 2007 Time: 11:54:24 AM
 */

public class BinaryExpressionValidator implements Validator<BinaryExpression> {
    public boolean validate(BinaryExpression info) throws ValidationException {
        ValidationException exception = null;

        try {
            Expression leftExpression = info.getLeftExpression();
            Expression rightExpression = info.getRightExpression();
            Operator operator = info.getOperator();

            TypeInfo leftTypeInfo = leftExpression.getTypeInfo();
            TypeInfo rightTypeInfo = rightExpression.getTypeInfo();

            int opType = operator.getOpType();

            // --------

            boolean bothAreAtoms = leftTypeInfo.isAtom() && rightTypeInfo.isAtom();

            switch (opType) {
                // Math.
                case Operator.OP_PLUS:
                case Operator.OP_MINUS:
                case Operator.OP_MULTIPLY:
                case Operator.OP_DIVIDE:
                case Operator.OP_MOD: {
                    boolean allowed = leftTypeInfo.isNumber() && rightTypeInfo.isNumber();

                    allowed = allowed && bothAreAtoms;

                    if (allowed == false) {
                        exception = new ValidationException(
                                "This operator is only allowed on numbers.", info.getLine(), info
                                        .getCharPosition());
                    }

                    break;
                }

                    // Comparison.
                case Operator.OP_NE:
                case Operator.OP_GT:
                case Operator.OP_LT:
                case Operator.OP_GE:
                case Operator.OP_LE: {
                    boolean allowed = leftTypeInfo.isNumber() && rightTypeInfo.isNumber();

                    if (allowed == false) {
                        allowed = leftTypeInfo.isString() && rightTypeInfo.isString();
                    }

                    if (allowed == false) {
                        allowed = leftTypeInfo.isBoolean() && rightTypeInfo.isBoolean();
                    }

                    if (allowed == false) {
                        allowed = leftTypeInfo.isDateTime() && rightTypeInfo.isDateTime();
                    }

                    if (allowed == false) {
                        // Only POJOs that are Comparable.
                        boolean leftIsComparable = leftTypeInfo.isJavaObject()
                                && leftTypeInfo.isComparable();

                        allowed = leftIsComparable
                                && (leftTypeInfo.getTypeClass()
                                        .equals(rightTypeInfo.getTypeClass()));
                    }

                    allowed = allowed && bothAreAtoms;

                    if (allowed == false) {
                        exception = new ValidationException(
                                "This operator is allowed only on numbers, objects "
                                        + "implementing Comparable or strings."
                                        + " Both operands must be of similar data types.", info
                                        .getLine(), info.getCharPosition());
                    }

                    break;
                }

                case Operator.OP_EQ: {
                    exception = validateEquals(info, leftTypeInfo, rightTypeInfo, bothAreAtoms);

                    break;
                }

                    // Boolean.
                case Operator.OP_AND:
                case Operator.OP_OR: {
                    boolean allowed = leftTypeInfo.isBoolean() && rightTypeInfo.isBoolean();

                    allowed = allowed && bothAreAtoms;

                    if (allowed == false) {
                        exception = new ValidationException(
                                "This operator is only allowed on boolean operands.", info
                                        .getLine(), info.getCharPosition());
                    }

                    break;
                }

                    // String.
                case Operator.OP_CONCAT:
                case Operator.OP_LIKE: {
                    boolean allowed = leftTypeInfo.isString() && rightTypeInfo.isString();

                    allowed = allowed && bothAreAtoms;

                    if (allowed == false) {
                        exception = new ValidationException(
                                "This operator is only allowed on strings.", info.getLine(), info
                                        .getCharPosition());
                    }

                    break;
                }

                case Operator.OP_BETWEEN: {
                    boolean allowed = leftTypeInfo.isDateTime() && rightTypeInfo.isDateTime();

                    if (allowed == false) {
                        allowed = leftTypeInfo.isString() && rightTypeInfo.isString();
                    }

                    if (allowed == false) {
                        allowed = leftTypeInfo.isNumber() && rightTypeInfo.isNumber();
                    }

                    allowed = allowed && bothAreAtoms;

                    if (allowed == false) {
                        exception = new ValidationException(
                                "This operator is only allowed on similar data types: date/time"
                                        + " or string or numbers.", info.getLine(), info
                                        .getCharPosition());
                    }

                    break;
                }

                case Operator.OP_ARRAY: {
                    boolean allowed = leftTypeInfo.isArray();

                    allowed = allowed
                            && (rightTypeInfo.isAtom() && rightTypeInfo.isNumber() && TypeHelper
                                    .canCoerceClass(rightTypeInfo.getTypeClass(), int.class));

                    if (allowed == false) {
                        exception = new ValidationException(
                                "This operator is only allowed on arrays and the index "
                                        + "must either be an integer or an expression that returns an integer.",
                                info.getLine(), info.getCharPosition());
                    }

                    break;
                }

                case Operator.OP_IN: {
                    if (rightExpression instanceof FieldList) {
                        FieldList fieldList = (FieldList) rightExpression;
                        int x = fieldList.getChildCount();
                        for (int i = 0; i < x; i++) {
                            Expression inElement = fieldList.getExpression(i);
                            TypeInfo inElementInfo = inElement.getTypeInfo();

                            boolean innerAndOuterAreAtoms = leftTypeInfo.isAtom()
                                    && inElementInfo.isAtom();
                            ValidationException ve = validateEquals(info, leftTypeInfo,
                                    inElementInfo, innerAndOuterAreAtoms);
                            if (ve != null) {
                                throw new ValidationException(
                                        "Elements in the In clause must be of the same "
                                                + "data type as the element being compared.", ve
                                                .getLineNumber(), ve.getCharPosition());
                            }
                        }
                    }
                    else {
                        // todo Must be a sub-query.
                    }

                    break;
                }

                case Operator.OP_RANGE: {
                    // todo
                }

                default: {
                    break;
                }
            }

            if (exception != null) {
                throw exception;
            }
        }
        catch (ValidationException e) {
            throw e;
        }
        catch (Exception e) {
            throw new ValidationException(e, info.getLine(), info.getCharPosition());
        }

        return true;
    }

    protected ValidationException validateEquals(BinaryExpression info, TypeInfo leftTypeInfo,
            TypeInfo rightTypeInfo, boolean bothAreAtoms) {
        ValidationException exception = null;

        boolean allowed = leftTypeInfo.isNumber() && rightTypeInfo.isNumber();

        if (allowed == false) {
            allowed = leftTypeInfo.isString() && rightTypeInfo.isString();
        }

        if (allowed == false) {
            allowed = leftTypeInfo.isBoolean() && rightTypeInfo.isBoolean();
        }

        if (allowed == false) {
            allowed = leftTypeInfo.isDateTime() && rightTypeInfo.isDateTime();
        }

        if (allowed == false) {
            // BE Entity or just POJO.
            allowed = leftTypeInfo.getTypeClass().equals(rightTypeInfo.getTypeClass());
        }

        allowed = allowed && bothAreAtoms;

        if (allowed == false) {
            exception = new ValidationException(
                    "This operator is only allowed on numbers, strings or POJOs. "
                            + "Both operands must be of similar data types.", info.getLine(), info
                            .getCharPosition());
        }

        return exception;
    }
}
