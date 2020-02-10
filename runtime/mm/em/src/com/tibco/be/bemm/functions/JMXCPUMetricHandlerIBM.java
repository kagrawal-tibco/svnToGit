package com.tibco.be.bemm.functions;

import java.io.IOException;

import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.runtime.model.event.SimpleEvent;


class JMXCPUMetricHandlerIBM extends JMXMetricTypeHandler {


	JMXCPUMetricHandlerIBM() {
		super();
	}

	@Override
	protected void init() throws IOException {
		logger.log(Level.WARN, "Process level CPU usage is not available for IBM jre.");
		//do nothing
	}

	@Override
	protected SimpleEvent[] populate(EventCreator eventCreator) throws IOException {
		return null;
	}

}
