package com.tibco.cep.common.resource;

import java.io.Serializable;

import com.tibco.cep.common.exception.RecoveryException;

/*
* Author: Ashwin Jayaprakash Date: Jul 13, 2009 Time: 1:21:15 PM
*/

public interface Recoverable<T> extends Serializable {
    /**
     * Whoever implements this should use this method to "freshen-up" after being de-serialized. The
     * framework will call this method on the serialization root. If a class has other {@link
     * Recoverable} fields, then this method should be called on them by the enclosing class - deep
     * graph recovery.
     *
     * @param resourceProvider
     * @param params
     * @return <code>this</code> or self or a new instance.
     * @throws RecoveryException
     */
    T recover(ResourceProvider resourceProvider, Object... params) throws RecoveryException;
}
