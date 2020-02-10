package com.tibco.cep.query.stream._join_.impl.join;

import com.tibco.cep.query.stream.misc.IdGenerator;

/*
* Author: Ashwin Jayaprakash Date: Nov 20, 2008 Time: 1:22:30 PM
*/
public class TwoWayMergingJTP extends MultiWayMergingJTP {
    public TwoWayMergingJTP(IdGenerator idGenerator, TupleInput leftTupleInput,
                            TupleInput rightTupleInput) {
        super(idGenerator, leftTupleInput, rightTupleInput);
    }
}
