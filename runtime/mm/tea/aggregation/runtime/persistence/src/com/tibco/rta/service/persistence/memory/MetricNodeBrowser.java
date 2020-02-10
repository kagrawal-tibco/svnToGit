package com.tibco.rta.service.persistence.memory;

import java.util.Iterator;

import com.tibco.rta.query.Browser;
import com.tibco.rta.query.impl.EmptyIterator;
import com.tibco.rta.runtime.model.MetricNode;

public class MetricNodeBrowser implements Browser<MetricNode> {

	Iterator<MetricNode> metricNodeIterator;
	Boolean isStopped;

	public MetricNodeBrowser(Iterator<MetricNode> iterator) {
		metricNodeIterator = iterator;
	}

	@Override
	public boolean hasNext() {
		return metricNodeIterator.hasNext();
	}

	@Override
	public MetricNode next() {
		return metricNodeIterator.next();
	}

	@Override
	public void remove() {
		// Not supported
	}

	@Override
	public void stop() {
		isStopped = true;
		metricNodeIterator = new EmptyIterator<MetricNode>();
	}

}
