package com.tibco.cep.query.model.validation.validators;

import com.tibco.cep.query.exception.ResolveException;
import com.tibco.cep.query.model.Identifier;
import com.tibco.cep.query.model.validation.ValidationException;
import com.tibco.cep.query.model.validation.Validator;

/**
 * Created by IntelliJ IDEA. User: nprade Date: Sep 10, 2007 Time: 4:28:13 PM To
 * change this template use File | Settings | File Templates.
 */
public class IdentifierValidator implements Validator<Identifier> {
    public boolean validate(Identifier identifier) throws ValidationException {
        try {
            return null != identifier.getIdentifiedContext();
        }
        catch (ResolveException e) {
            throw new ValidationException(identifier.getLine(), identifier.getCharPosition());
        }
    }
}
