package com.tibco.cep.query.model.validation.validators;

import com.tibco.cep.query.model.SlidingWindow;
import com.tibco.cep.query.model.validation.ValidationException;
import com.tibco.cep.query.model.validation.Validator;

/*
 * Author: Ashwin Jayaprakash Date: Dec 13, 2007 Time: 5:21:57 PM
 */

public class SlidingWindowValidator implements Validator<SlidingWindow> {
    public boolean validate(SlidingWindow info) throws ValidationException {
        try {
            long l = info.getMaxSize();
            if (l <= 0) {
                throw new ValidationException("Window must have a valid non-zero size.", info
                        .getLine(), info.getCharPosition());
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
