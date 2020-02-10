package com.tibco.rta.service.purge;


import com.tibco.rta.common.service.StartStopService;

public interface PurgeService extends StartStopService {

	void purge() throws Exception;

}
