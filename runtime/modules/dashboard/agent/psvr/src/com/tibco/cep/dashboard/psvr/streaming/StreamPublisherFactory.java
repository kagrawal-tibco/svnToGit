package com.tibco.cep.dashboard.psvr.streaming;

import java.util.Properties;

import com.tibco.cep.dashboard.logging.LoggingService;
import com.tibco.cep.dashboard.management.ExceptionHandler;
import com.tibco.cep.dashboard.management.MessageGenerator;
import com.tibco.cep.kernel.service.logging.Logger;

class StreamPublisherFactory {

	private static enum STREAMING_MODE { STEADY, PERIODIC, IMMEDIATE };
	
	static StreamPublisher getPublisher(Properties properties, Logger logger, ExceptionHandler exceptionHandler, MessageGenerator messageGenerator){
		STREAMING_MODE streamingMode;
		try {
			streamingMode = STREAMING_MODE.valueOf(StreamingProperties.STREAMING_MODE.getValue(properties).toString().toUpperCase());
		} catch (IllegalArgumentException e) {
			exceptionHandler.handleException("Illegal value ["+StreamingProperties.STREAMING_MODE.getRawValue(properties)+"] for "+StreamingProperties.STREAMING_MODE.getName()+", defaulting to "+STREAMING_MODE.STEADY.toString().toLowerCase(), e);
			streamingMode = STREAMING_MODE.STEADY;
		} 
		StreamPublisher publisher = null;
		switch (streamingMode){
			case STEADY : 
				publisher = new SteadyBurstStreamPublisher();
				publisher.logger = LoggingService.getChildLogger(logger,"steadyburstpublisher");
				break;
			case PERIODIC :
				publisher = new PeriodicBurstStreamPublisher();
				publisher.logger = LoggingService.getChildLogger(logger,"periodicburstpublisher");
				break;
			case IMMEDIATE :
				publisher = new ImmediateStreamPublisher();
				publisher.logger = LoggingService.getChildLogger(logger,"immediatepublisher");
				break;
		}
		publisher.exceptionHandler = exceptionHandler;
		publisher.messageGenerator = messageGenerator;
		publisher.setType(streamingMode.toString());
		return publisher;		
	}

}