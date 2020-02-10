package com.tibco.cep.query.model.validation.validators;

import com.tibco.cep.query.model.Expression;
import com.tibco.cep.query.model.Function;
import com.tibco.cep.query.model.FunctionArg;
import com.tibco.cep.query.model.TypeInfo;
import com.tibco.cep.query.model.impl.AbstractFunctionIdentifierImpl;
import com.tibco.cep.query.model.validation.ValidationException;
import com.tibco.cep.query.model.validation.Validator;
import com.tibco.cep.query.utils.TypeHelper;

/*
 * Author: Ashwin Jayaprakash Date: Dec 10, 2007 Time: 4:56:07 PM
 */

public class FunctionValidator implements Validator<AbstractFunctionIdentifierImpl> {
    public boolean validate(AbstractFunctionIdentifierImpl info) throws ValidationException {
        try {
            Function function = (Function) info.getIdentifiedContext();
            FunctionArg[] expectedArgs = function.getArguments();
            Expression[] argExpressions = info.getArguments();

            if (argExpressions.length != expectedArgs.length) {
                throw new ValidationException("Incorrect number of arguments", info.getLine(), info
                        .getCharPosition());
            }

            for (int i = 0; i < argExpressions.length; i++) {
                TypeInfo expectedTypeInfo = expectedArgs[i].getTypeInfo();
                TypeInfo actualTypeInfo = argExpressions[i].getTypeInfo();

                boolean ok = false;
                if (expectedTypeInfo.equals(actualTypeInfo)) {
                    // Ok, perfect match.
                    ok = true;
                }
                else if (expectedTypeInfo.isNumber() && actualTypeInfo.isNumber()) {
                    // Arguments are numeric and can be coerced.
                    ok = TypeHelper.canCoerceClass(actualTypeInfo.getTypeClass(), expectedTypeInfo
                            .getTypeClass());
                }

                if (ok == false) {
                    throw new ValidationException("Argument " + (i + 1)
                            + " does not match the expected data type", info.getLine(), info
                            .getCharPosition());
                }
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
