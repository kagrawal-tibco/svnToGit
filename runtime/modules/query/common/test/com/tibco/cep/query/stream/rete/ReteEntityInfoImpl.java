package com.tibco.cep.query.stream.rete;

import com.tibco.cep.query.stream.impl.rete.ReteEntity;
import com.tibco.cep.query.stream.impl.rete.ReteEntityInfo;
import com.tibco.cep.query.stream.tuple.AbstractTupleInfo;

import java.lang.reflect.Constructor;

/*
* Author: Ashwin Jayaprakash Date: Apr 4, 2008 Time: 1:33:54 PM
*/
public class ReteEntityInfoImpl extends AbstractTupleInfo implements ReteEntityInfo {
    protected Constructor<? extends ReteEntity> reteEntityCtor;

    /**
     * @param containerClass
     * @param columnNames
     * @param columnTypes
     */
    public ReteEntityInfoImpl(Class<? extends ReteEntity> containerClass, String[] columnNames,
                              Class[] columnTypes) {
        super(containerClass, columnNames, columnTypes);

        try {
            this.reteEntityCtor = containerClass.getConstructor(Number.class);
        }
        catch (NoSuchMethodException e) {
            throw new RuntimeException("Error occurred while obtaining " +
                    "Constructor<Number.class> for: " + containerClass.getName(), e);
        }
    }

    @Override
    public Class<? extends ReteEntity> getContainerClass() {
        return (Class<? extends ReteEntity>) super.getContainerClass();
    }

    public ReteEntity createTuple(Number id) {
        try {
            return reteEntityCtor.newInstance(id);
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
