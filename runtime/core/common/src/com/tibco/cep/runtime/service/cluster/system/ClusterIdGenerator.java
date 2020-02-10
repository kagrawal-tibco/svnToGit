/*
 * Copyright (c) 2010.  TIBCO Software Inc.
 *  All Rights Reserved.
 *
 *  This software is confidential and proprietary information of
 *  TIBCO Software Inc.
 */

package com.tibco.cep.runtime.service.cluster.system;

import com.tibco.cep.kernel.service.IdGenerator;
import com.tibco.cep.runtime.service.cluster.Cluster;

public interface ClusterIdGenerator extends IdGenerator {
    void init(Cluster cluster);

    void start() throws Exception;
}
