package com.tibco.cep.query.stream.core;

import java.util.Properties;

/*
 * Author: Ashwin Jayaprakash Date: Oct 15, 2007 Time: 10:51:35 AM
 */

public interface Component extends ControllableResource {
    public void init(Properties properties) throws Exception;

    public void discard() throws Exception;
}
