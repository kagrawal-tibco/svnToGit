package com.tibco.cep.query.stream.misc;

/*
 * Author: Ashwin Jayaprakash Date: Oct 2, 2007 Time: 3:22:46 PM
 */

public class IdGenerator {
    protected long counter;

    public IdGenerator() {
    }

    public IdGenerator(long start) {
        this.counter = start;
    }

    public Number generateNewId() {
        return counter++;
    }

    public void discard() {
    }
}
