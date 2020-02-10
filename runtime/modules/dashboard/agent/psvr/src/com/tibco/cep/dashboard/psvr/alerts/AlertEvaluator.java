package com.tibco.cep.dashboard.psvr.alerts;

import com.tibco.cep.dashboard.common.data.Tuple;
import com.tibco.cep.dashboard.psvr.common.NonFatalException;
import com.tibco.cep.dashboard.psvr.mal.MALFieldMetaInfo;

public interface AlertEvaluator {

	//TODO may be add the category field to the API 
	public AlertActionable[] evaluate(MALFieldMetaInfo field, Tuple tuple) throws EvalException;
	
	public void shutdown() throws NonFatalException;
}
