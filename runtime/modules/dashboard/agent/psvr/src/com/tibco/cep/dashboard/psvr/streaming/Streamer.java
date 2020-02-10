package com.tibco.cep.dashboard.psvr.streaming;

import java.io.IOException;

import com.tibco.cep.dashboard.psvr.util.PerformanceMeasurement;

public abstract class Streamer {
	
	protected PerformanceMeasurement streamingTime;
	
	private boolean switching;
	
	protected Streamer(){
		switching = false;
		streamingTime = new PerformanceMeasurement("Streaming Time");		
	}
	
	void setSwitching(boolean switching){
		this.switching = switching;
	}
	
	boolean isSwitching(){
		return switching;
	}
	
	public abstract void init() throws IOException;
	
	public final void stream(String data) throws IOException{
		long stime = System.currentTimeMillis();
		try {
			doStream(data);
		} finally {
			long etime = System.currentTimeMillis();
			long timeTaken = etime - stime;
			streamingTime.add(System.currentTimeMillis(), timeTaken);
		}
	}
	
	protected abstract void doStream(String data) throws IOException;

	public final void close() {
		if (switching == false){
			doClose();
		}
		else {
			//do nothing
		}
	}
	
	protected abstract void doClose();

}
