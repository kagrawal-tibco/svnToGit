package com.tibco.cep.query.stream.impl.rete;

import com.tibco.cep.query.stream.core.Source;
import com.tibco.cep.query.stream.impl.rete.expression.ReteEntityFilter;

/*
 * Author: Ashwin Jayaprakash Date: Oct 11, 2007 Time: 1:49:45 PM
 */

public interface ReteEntitySource extends Source {
    ReteEntityInfo getOutputInfo();

    /**
     * @return The runtime class that will be consumed by this source.
     */
    Class getReteEntityClass();

    /**
     * @return Optional filter. Can be <code>null</code> if there never was a filter configured.
     */
    ReteEntityFilter getReteEntityFilter();
}
