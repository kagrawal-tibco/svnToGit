package com.tibco.cep.query.stream.core;

import com.tibco.cep.query.stream.context.Context;

/*
 * Author: Ashwin Jayaprakash Date: Oct 1, 2007 Time: 6:43:09 PM
 */

public interface StreamListener {
    public void process(Context context) throws Exception;

    public void stop() throws Exception;
}
