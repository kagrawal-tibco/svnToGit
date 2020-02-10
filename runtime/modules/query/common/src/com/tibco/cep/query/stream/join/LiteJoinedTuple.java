package com.tibco.cep.query.stream.join;

import com.tibco.cep.query.stream.tuple.LiteTuple;
import com.tibco.cep.query.stream.tuple.Tuple;

/*
 * Author: Ashwin Jayaprakash Date: Feb 20, 2008 Time: 1:11:35 PM
 */

/**
 * Columns <b>have</b> to be {@link LiteTuple} types.
 */
public class LiteJoinedTuple extends LiteTuple implements JoinedTuple {
    /**
     * <p> Default {@link #timestamp} is the time of instantiation. </p>
     *
     * @param id
     * @param columns
     */
    public LiteJoinedTuple(Number id, Object[] columns) {
        super(id, columns);
    }

    /**
     * @param id
     * <p> For sub-Classes. </p> <p> Default {@link #timestamp} is the time of instantiation. </p>
     */
    protected LiteJoinedTuple(Number id) {
        this(id, null);
    }

    @Override
    public Tuple getColumn(int index) {
        return (Tuple) super.getColumn(index);
    }
}
