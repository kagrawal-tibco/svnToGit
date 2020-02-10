package com.tibco.cep.dashboard.psvr.streaming;

import java.io.IOException;

public class SysOutStreamer extends Streamer {
	
	private String toString;
	
	public SysOutStreamer(){
		toString = "SysOutStreamer";
	}
	
	@Override
	public void init() throws IOException {
	}	

	@Override
	protected void doStream(String data) throws IOException {
		System.out.println(data);
	}

	@Override
	protected void doClose() {
	}

	@Override
	public String toString() {
		return toString;
	}
}