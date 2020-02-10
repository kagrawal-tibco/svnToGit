package com.tibco.rta.service.persistence.memory;

import java.util.Iterator;

import com.tibco.rta.query.Browser;
import com.tibco.rta.query.impl.EmptyIterator;
import com.tibco.rta.runtime.model.rule.RuleMetricNodeState;

public class RuleMetricNodeStateBrowser implements Browser<RuleMetricNodeState> {

	Iterator<RuleMetricNodeState> metricNodeIterator;
	Boolean isStopped;

	public RuleMetricNodeStateBrowser(Iterator<RuleMetricNodeState> iterator) {
		metricNodeIterator = iterator;
	}

	@Override
	public boolean hasNext() {
		return metricNodeIterator.hasNext();
	}

	@Override
	public RuleMetricNodeState next() {
		return metricNodeIterator.next();
	}

	@Override
	public void remove() {
		// Not supported
	}

	@Override
	public void stop() {
		isStopped = true;
		metricNodeIterator = new EmptyIterator<RuleMetricNodeState>();
	}

}
