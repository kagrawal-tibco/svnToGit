package com.tibco.rta.service.cluster;

import com.tibco.rta.common.service.StartStopService;

public interface ClusterService extends StartStopService {

	boolean isCacheNode();

}
