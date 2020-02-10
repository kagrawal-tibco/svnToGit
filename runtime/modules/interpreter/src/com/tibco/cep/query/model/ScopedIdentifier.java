package com.tibco.cep.query.model;

import com.tibco.cep.query.exception.ResolveException;
import com.tibco.cep.query.model.validation.Resolvable;

/**
 * Created by IntelliJ IDEA. User: pdhar Date: Oct 11, 2007 Time: 3:44:53 PM To
 * change this template use File | Settings | File Templates.
 */
public interface ScopedIdentifier extends Expression, Resolvable {

    String getName();

    /**
     * @return ModelContext the identified context
     */
    ModelContext[] getIdentifiedContexts() throws ResolveException;

}
