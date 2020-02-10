package com.tibco.cep.query.model.validation;

import com.tibco.cep.query.model.ModelContext;

/**
 * Created by IntelliJ IDEA. User: pdhar Date: Sep 6, 2007 Time: 10:33:55 PM To
 * change this template use File | Settings | File Templates.
 */
public interface Validator<T extends ModelContext> {
    boolean validate(T info) throws ValidationException;
}
