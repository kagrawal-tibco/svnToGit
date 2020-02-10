package com.tibco.cep.dashboard.psvr.alerts;

import java.util.List;

import com.tibco.cep.dashboard.common.data.Tuple;
import com.tibco.cep.dashboard.psvr.common.NonFatalException;
import com.tibco.cep.dashboard.psvr.mal.MALFieldMetaInfo;

public final class AlertEvaluationCoordinator implements AlertEvaluator {
	
	private List<AlertEvaluator> evaluators;
	
	public AlertEvaluationCoordinator(List<AlertEvaluator> evaluators) {
		super();
		this.evaluators = evaluators;
	}

	@Override
	public AlertActionable[] evaluate(MALFieldMetaInfo field, Tuple tuple) throws EvalException {
		for (AlertEvaluator evaluator : evaluators) {
			AlertActionable[] results = evaluator.evaluate(field,tuple);
			if (results != null){
				return results;
			}
		}
		return null;
	}

	@Override
	public void shutdown() throws NonFatalException {
		for (AlertEvaluator evaluator : evaluators) {
			evaluator.shutdown();
		}
		evaluators.clear();
	}

}