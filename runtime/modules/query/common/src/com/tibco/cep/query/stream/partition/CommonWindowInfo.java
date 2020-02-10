package com.tibco.cep.query.stream.partition;

/*
 * Author: Ashwin Jayaprakash Date: Nov 13, 2007 Time: 3:26:53 PM
 */

public class CommonWindowInfo implements WindowInfo {
    protected final int size;

    public CommonWindowInfo(int size) {
        this.size = size;
    }

    public int getSize() {
        return size;
    }
}