package com.tibco.cep.query.model.validation.validators;

import java.sql.Timestamp;

import com.tibco.cep.query.model.Expression;
import com.tibco.cep.query.model.TimeWindow;
import com.tibco.cep.query.model.TypeInfo;
import com.tibco.cep.query.model.validation.ValidationException;
import com.tibco.cep.query.model.validation.Validator;

/*
 * Author: Ashwin Jayaprakash Date: Dec 13, 2007 Time: 5:21:57 PM
 */

public class TimeWindowValidator implements Validator<TimeWindow> {
    public boolean validate(TimeWindow info) throws ValidationException {
        try {
            long l = info.getMaxTime();
            if (l <= 0) {
                throw new ValidationException("Window must have a valid non-zero size.", info
                        .getLine(), info.getCharPosition());
            }

            Expression expression = info.getUsingProperty();
            if (expression != null) {
                TypeInfo typeInfo = expression.getTypeInfo();
                Class clazz = typeInfo.getTypeClass();
                if (typeInfo.isAtom() == false || Timestamp.class.isAssignableFrom(clazz) == false) {
                    throw new ValidationException(
                            "The Using property must be a Timestamp data type.", info.getLine(),
                            info.getCharPosition());
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
