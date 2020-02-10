package com.tibco.cep.query.model.validation.validators;

import com.tibco.cep.query.model.TumblingWindow;
import com.tibco.cep.query.model.validation.ValidationException;
import com.tibco.cep.query.model.validation.Validator;

/*
 * Author: Ashwin Jayaprakash Date: Dec 13, 2007 Time: 5:21:57 PM
 */

public class TumblingWindowValidator implements Validator<TumblingWindow> {
    public boolean validate(TumblingWindow info) throws ValidationException {
        long l = info.getMaxSize();
        if (l <= 0) {
            throw new ValidationException("Window must have a valid non-zero size.", info
                    .getLine(), info.getCharPosition());
        }

        return true;
    }
}
