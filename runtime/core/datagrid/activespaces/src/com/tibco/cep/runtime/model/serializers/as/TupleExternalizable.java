package com.tibco.cep.runtime.model.serializers.as;

import com.tibco.as.space.Tuple;

/**
 * Created by IntelliJ IDEA.
 * User: suresh
 * Date: Jun 21, 2010
 * Time: 1:07:57 PM
 * To change this template use File | Settings | File Templates.
 */
public interface TupleExternalizable<E> {

    /**
     * create a Tuple from an instance of E (Concept, Event, Java object etc)
     * @param e
     * @return
     * @throws Exception
     */
    public Tuple toTuple(E e) throws Exception;

    public E fromTuple(Tuple tuple) throws Exception;
}
