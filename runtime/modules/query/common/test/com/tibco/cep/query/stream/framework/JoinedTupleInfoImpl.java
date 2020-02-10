package com.tibco.cep.query.stream.framework;

import com.tibco.cep.query.stream.join.JoinedTuple;
import com.tibco.cep.query.stream.join.JoinedTupleInfo;
import com.tibco.cep.query.stream.tuple.AbstractTupleInfo;

import java.lang.reflect.Constructor;

/*
* Author: Ashwin Jayaprakash Date: Apr 4, 2008 Time: 1:36:47 PM
*/
public class JoinedTupleInfoImpl extends AbstractTupleInfo implements JoinedTupleInfo {
    protected Constructor<? extends JoinedTuple> tupleCtor;

    public JoinedTupleInfoImpl(Class<? extends JoinedTuple> containerClass, String[] columnNames,
                               Class[] columnTypes) {
        super(containerClass, columnNames, columnTypes);

        try {
            this.tupleCtor = containerClass.getConstructor(Number.class);
        }
        catch (NoSuchMethodException e) {
            throw new RuntimeException("Error occurred while obtaining " +
                    "Constructor<Number.class> for: " + containerClass.getName(), e);
        }
    }

    @Override
    public Class<? extends JoinedTuple> getContainerClass() {
        return (Class<? extends JoinedTuple>) super.getContainerClass();
    }

    public JoinedTuple createTuple(Number id) {
        try {
            return tupleCtor.newInstance(id);
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
