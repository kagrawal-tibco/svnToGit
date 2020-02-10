package com.tibco.cep.bemm.monitoring.metric.probe.accumulator.jmx;

public class JmxException extends Exception {
	
	private static final long serialVersionUID = 7497752081121479629L;

	JmxException(String message) {
		super(message);
	}

	JmxException(Throwable t) {
		super(t);
	}

	JmxException(String message, Throwable t) {
		super(message, t);
	}

}
