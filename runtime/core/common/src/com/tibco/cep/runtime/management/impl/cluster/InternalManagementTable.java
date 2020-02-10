package com.tibco.cep.runtime.management.impl.cluster;

import com.tibco.cep.runtime.management.ManagementTable;

/*
* Author: Ashwin Jayaprakash Date: Jan 28, 2009 Time: 6:19:56 PM
*/
public interface InternalManagementTable extends ManagementTable {
    /**
     * @param clusterURL
     */
    void init(String clusterURL, String role);

    void discard();
}
