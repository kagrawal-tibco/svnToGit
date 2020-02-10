package com.tibco.cep.query.stream.util;

import java.util.Iterator;

/*
 * Author: Ashwin Jayaprakash Date: Feb 27, 2008 Time: 2:32:36 PM
 */

public interface ReusableIterator<X> extends Iterator<X> {
    public void reset();
}
