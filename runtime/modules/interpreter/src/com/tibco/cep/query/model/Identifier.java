package com.tibco.cep.query.model;

import com.tibco.cep.query.exception.ResolveException;
import com.tibco.cep.query.model.validation.Resolvable;
import com.tibco.cep.query.model.validation.Validatable;

/**
 * Created by IntelliJ IDEA. User: pdhar Date: Jul 10, 2007 Time: 4:36:35 PM To
 * change this template use File | Settings | File Templates.
 */
public interface Identifier extends Expression, Resolvable, Validatable {

    String getName();

    /**
     * @return ModelContext the identified context
     */
    ModelContext getIdentifiedContext() throws ResolveException;

    /**
     * Set the identified context
     * 
     * @param context
     */
    void setIdentifiedContext(ModelContext context) throws Exception;

}
