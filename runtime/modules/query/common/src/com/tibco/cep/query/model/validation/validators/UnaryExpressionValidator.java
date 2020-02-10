package com.tibco.cep.query.model.validation.validators;

import com.tibco.cep.query.model.Operator;
import com.tibco.cep.query.model.TypeInfo;
import com.tibco.cep.query.model.UnaryExpression;
import com.tibco.cep.query.model.validation.ValidationException;
import com.tibco.cep.query.model.validation.Validator;

/*
 * Author: Ashwin Jayaprakash Date: Dec 10, 2007 Time: 1:13:48 PM
 */

public class UnaryExpressionValidator implements Validator<UnaryExpression> {
    public boolean validate(UnaryExpression info) throws ValidationException {
        ValidationException exception = null;

        try {
            TypeInfo typeInfo = info.getTypeInfo();
            Operator operator = info.getOperator();

            int opType = operator.getOpType();

            // --------

            switch (opType) {
                case Operator.OP_PLUS:
                case Operator.OP_UMINUS:
                case Operator.OP_ABS: {
                    if (typeInfo.isAtom() && typeInfo.isNumber() == false) {
                        exception = new ValidationException(
                                "This operator is only allowed on numbers.", info.getLine(), info
                                        .getCharPosition());
                    }

                    break;
                }

                case Operator.OP_NOT: {
                    if (typeInfo.isAtom() && typeInfo.isBoolean() == false) {
                        exception = new ValidationException(
                                "This operator is only allowed on boolean operands.", info
                                        .getLine(), info.getCharPosition());
                    }

                    break;
                }

                case Operator.OP_GROUP:
                case Operator.OP_EXISTS: {
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
}
