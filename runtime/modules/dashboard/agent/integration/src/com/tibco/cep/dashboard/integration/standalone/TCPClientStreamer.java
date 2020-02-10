package com.tibco.cep.dashboard.integration.standalone;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.concurrent.LinkedBlockingQueue;

import com.tibco.cep.dashboard.management.STATE;
import com.tibco.cep.dashboard.psvr.biz.BusinessActionsController;
import com.tibco.cep.dashboard.psvr.biz.BizRequest;
import com.tibco.cep.dashboard.psvr.biz.XMLBizRequestImpl;
import com.tibco.cep.dashboard.psvr.streaming.Streamer;
import com.tibco.cep.dashboard.psvr.streaming.StreamerRegistry;

public class TCPClientStreamer extends Streamer implements Runnable {
	
	private static final String EOQ = "EOQ";
	
	private static final char EOL_CHAR = '\0';
	
	private char endOfLineChar; 
	private Socket clientSocket;
	private BufferedReader reader;
	private BufferedWriter writer;
	
	private STATE state;
	
	private LinkedBlockingQueue<String> dataQueue;
	
	private String toString;
	
	protected TCPClientStreamer(Socket clientSocket) throws IOException{
		this.clientSocket = clientSocket;
		this.reader = new BufferedReader(new InputStreamReader(this.clientSocket.getInputStream()));
		this.writer = new BufferedWriter(new OutputStreamWriter(this.clientSocket.getOutputStream()));
		dataQueue = new LinkedBlockingQueue<String>();
		state = STATE.RUNNING;
		endOfLineChar = EOL_CHAR;
		StringBuilder sb = new StringBuilder("TCPClientStreamer[");
		sb.append("client="+clientSocket);
		sb.append("]");
		toString = sb.toString();		
	}
	
	public char getEndOfLineChar() {
		return endOfLineChar;
	}

	public void setEndOfLineChar(char endOfLineChar) {
		this.endOfLineChar = endOfLineChar;
	}

	@Override
	public void run() {
		try {
			if (state == STATE.RUNNING) {
				String incomingRequest = readIncomingData();
				processRequest(incomingRequest);
				while (state == STATE.RUNNING){
					try {
						String data = dataQueue.take();
						if (state == STATE.RUNNING && data != EOQ){
							writer.write(data);
							writer.write(endOfLineChar);
							writer.flush();
						}
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}
	
	private String readIncomingData() throws IOException {
        StringBuilder sb = new StringBuilder();
        char ch = (char) reader.read();
        while (ch != -1 && ch != endOfLineChar) {
        	sb.append(ch);
            ch = (char) reader.read();
        }
        return sb.toString();
	}	
	
	private void processRequest(String requestXML) {
		//register the streamer with the streaming registry
		String name = Integer.toString(hashCode());
		StreamerRegistry.getInstance().registerStreamer(name, this);
		//generate the request for processing
		BizRequest request = new XMLBizRequestImpl(requestXML);
		//add the streamer's id
		request.addParameter(Streamer.class.getName(), name);
		request.addParameter("command", "startstreaming");
		//process the request
		BusinessActionsController.getInstance().process(request);
	}

	@Override
	public void init() throws IOException {
		//does nothing
	}	

	@Override
	protected void doStream(String data) throws IOException {
		dataQueue.offer(data);
	}

	@Override
	protected void doClose() {
		state = STATE.STOPPED;
		dataQueue.offer(EOQ);
	}

	@Override
	public String toString() {
		return toString;
	}
}
