package com.tibco.cep.query.model.validation.validators;

import com.tibco.cep.query.model.Expression;
import com.tibco.cep.query.model.TypeInfo;
import com.tibco.cep.query.model.WhereClause;
import com.tibco.cep.query.model.validation.ValidationException;
import com.tibco.cep.query.model.validation.Validator;

/*
 * Author: Ashwin Jayaprakash Date: Dec 13, 2007 Time: 3:57:33 PM
 */

public class WhereClauseValidator implements Validator<WhereClause> {
    public boolean validate(WhereClause info) throws ValidationException {
        try {
            Expression expression = info.getExpression();

            TypeInfo typeInfo = expression.getTypeInfo();
            if (typeInfo.isAtom() == false || typeInfo.isBoolean() == false) {
                throw new ValidationException(
                        "The Where clause expression must be evaluate to a boolean value.", info
                                .getLine(), info.getCharPosition());
            }
        }
        catch (ValidationException exception) {
            throw exception;
        }
        catch (Exception exception) {
            throw new ValidationException(exception, info.getLine(), info.getCharPosition());
        }

        return true;
    }
}
