package com.tibco.cep.query.model.validation.validators;

import com.tibco.cep.query.model.Expression;
import com.tibco.cep.query.model.Function;
import com.tibco.cep.query.model.TypeInfo;
import com.tibco.cep.query.model.impl.AggregateFunctionIdentifierImpl;
import com.tibco.cep.query.model.validation.ValidationException;
import com.tibco.cep.query.model.validation.Validator;

/*
 * Author: Ashwin Jayaprakash Date: Dec 10, 2007 Time: 4:56:07 PM
 */

public class AggregateFunctionValidator implements Validator<AggregateFunctionIdentifierImpl> {
    public boolean validate(AggregateFunctionIdentifierImpl info) throws ValidationException {
        try {
            Function function = (Function) info.getIdentifiedContext();
            Expression[] argExpressions = info.getArguments();

            if (argExpressions.length != 1) {
                throw new ValidationException("Incorrect number of arguments", info.getLine(), info
                        .getCharPosition());
            }

            TypeInfo actualTypeInfo = argExpressions[0].getTypeInfo();
            String fName = function.getName();

            if (actualTypeInfo.isAtom() == false) {
                throw new ValidationException("Only atoms are allowed as arguments.",
                        argExpressions[0].getLine(), argExpressions[0].getCharPosition());
            }

            if (fName.equalsIgnoreCase(Function.AGGREGATE_FUNCTION_MAX)
                    || fName.equalsIgnoreCase(Function.AGGREGATE_FUNCTION_MIN)) {
                if (actualTypeInfo.isComparable() == false) {
                    throw new ValidationException(
                            "Only objects that implement Comparable are allowed as arguments.",
                            argExpressions[0].getLine(), argExpressions[0].getCharPosition());
                }
            }
            else if (fName.equalsIgnoreCase(Function.AGGREGATE_FUNCTION_AVG)
                    || fName.equalsIgnoreCase(Function.AGGREGATE_FUNCTION_SUM)) {
                if (actualTypeInfo.isNumber() == false) {
                    throw new ValidationException("Only numbers are allowed as arguments.",
                            argExpressions[0].getLine(), argExpressions[0].getCharPosition());
                }
            }
            else if (fName.equalsIgnoreCase(Function.AGGREGATE_FUNCTION_COUNT)
                    || fName.equalsIgnoreCase(Function.AGGREGATE_FUNCTION_PENDING_COUNT)) {
                // Nothing specific. Allow anything.
            }
            else {
                throw new ValidationException("Invalid aggregate function.", info.getLine(),
                        info.getCharPosition());
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
