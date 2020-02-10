/*
 * Copyright (c) 2010.  TIBCO Software Inc.
 *  All Rights Reserved.
 *
 *  This software is confidential and proprietary information of
 *  TIBCO Software Inc.
 */

package com.tibco.cep.runtime.service.cluster;



public interface ClusterState {
    void init (Cluster cluster);
    void transitionTo(State toState);

    enum State {
                
    }

}
