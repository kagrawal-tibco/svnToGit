package com.tibco.cep.query.stream._join_.impl.join;

import com.tibco.cep.query.stream.misc.IdGenerator;

/*
* Author: Ashwin Jayaprakash Date: Nov 20, 2008 Time: 1:22:30 PM
*/
public class TwoWayNonMergingJTP extends MultiWayNonMergingJTP {
    /**
     * @param idGenerator
     * @param leftKey
     * @param rightKey
     */
    public TwoWayNonMergingJTP(IdGenerator idGenerator, String leftKey, String rightKey) {
        super(idGenerator, leftKey, rightKey);
    }
}