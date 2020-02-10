package com.tibco.rta.service.recovery;


import com.tibco.rta.Fact;
import com.tibco.rta.common.service.StartStopService;

public interface RecoveryService extends StartStopService {


	void resubmitFact(Fact facts);

}
