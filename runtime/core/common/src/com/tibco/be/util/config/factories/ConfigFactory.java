package com.tibco.be.util.config.factories;


import java.io.IOException;

import com.tibco.be.util.config.cdd.ClusterConfig;


/*
* User: Nicolas Prade
* Date: Sep 1, 2009
* Time: 2:59:08 PM
*/
public interface ConfigFactory {


    ClusterConfig newConfig(String uri) throws IOException;
}
