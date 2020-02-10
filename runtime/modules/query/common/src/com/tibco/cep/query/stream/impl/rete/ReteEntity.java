package com.tibco.cep.query.stream.impl.rete;

import com.tibco.cep.query.stream.cache.SharedPointer;
import com.tibco.cep.query.stream.tuple.Tuple;

/*
 * Author: Ashwin Jayaprakash Date: Oct 11, 2007 Time: 12:32:29 PM
 */

public interface ReteEntity extends Tuple {
    public Long getReteId();

    public void setReteId(Long reteId);

    /**
     * Should be used instead of {@link Tuple#setColumns(Object[])}.
     *
     * @param pointer
     */
    public void setPointer(SharedPointer pointer);

    /**
     * Should be used instead if {@link Tuple#getColumn(int)}.
     *
     * @return
     */
    public SharedPointer getPointer();
}
