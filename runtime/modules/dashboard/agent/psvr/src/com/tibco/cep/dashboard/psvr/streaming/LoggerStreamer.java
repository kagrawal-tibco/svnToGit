package com.tibco.cep.dashboard.psvr.streaming;

import java.io.IOException;

import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.Logger;

public class LoggerStreamer extends Streamer {
	
	private Logger logger;
	
	private String toString;
	
	public LoggerStreamer(Logger logger) {
		super();
		this.logger = logger;
		StringBuilder sb = new StringBuilder("LoggerStreamer[");
		sb.append("logger="+logger.getName());
		sb.append("]");
		toString = sb.toString();		
	}

	@Override
	public void init() throws IOException {
	}	

	@Override
	protected void doStream(String data) throws IOException {
		logger.log(Level.INFO, data);		
	}

	@Override
	protected void doClose() {
	}
	
	@Override
	public String toString() {
		return toString;
	}
}
