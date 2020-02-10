package com.tibco.cep.query.stream.join;

import com.tibco.cep.query.stream.tuple.Tuple;

/*
 * Author: Ashwin Jayaprakash Date: Jan 7, 2008 Time: 3:13:28 PM
 */

/**
 * Elements are {@link Tuple}s.
 */
public interface JoinedTuple extends Tuple {
    Tuple getColumn(int index);
}
