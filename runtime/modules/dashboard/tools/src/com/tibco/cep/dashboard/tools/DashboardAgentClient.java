package com.tibco.cep.dashboard.tools;

import java.io.IOException;

import com.tibco.cep.dashboard.psvr.biz.BizRequest;

public interface DashboardAgentClient {
	
	public void start() throws IOException;
	
	public void pause() throws IOException;
	
	public void resume() throws IOException;
	
	public String execute(BizRequest request) throws IOException;
	
	public void startStreaming(BizRequest request) throws IOException;
	
	public void stopStreaming(BizRequest request) throws IOException;
	
	public void stop() throws IOException;
	
	public void close() throws IOException;

}
