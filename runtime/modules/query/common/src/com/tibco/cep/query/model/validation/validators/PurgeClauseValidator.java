package com.tibco.cep.query.model.validation.validators;

import com.tibco.cep.query.model.Expression;
import com.tibco.cep.query.model.PurgeClause;
import com.tibco.cep.query.model.TypeInfo;
import com.tibco.cep.query.model.validation.ValidationException;
import com.tibco.cep.query.model.validation.Validator;

/*
 * Author: Ashwin Jayaprakash Date: Dec 13, 2007 Time: 6:16:31 PM
 */

public class PurgeClauseValidator implements Validator<PurgeClause> {
    public boolean validate(PurgeClause info) throws ValidationException {
        try {
            Expression expression = info.getFirst();
            TypeInfo typeInfo = expression.getTypeInfo();
            if (typeInfo.isAtom() == false || typeInfo.isNumber() == false) {
                throw new ValidationException("Purge-first expression should return a number.",
                        expression.getLine(), expression.getCharPosition());
            }

            expression = info.getWhen();
            typeInfo = expression.getTypeInfo();
            if (typeInfo.isAtom() == false || typeInfo.isBoolean() == false) {
                throw new ValidationException(
                        "Purge-when expression should evaluate to a boolean value.", expression
                                .getLine(), expression.getCharPosition());
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
