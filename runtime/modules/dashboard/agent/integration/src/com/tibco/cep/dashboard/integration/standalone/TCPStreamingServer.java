package com.tibco.cep.dashboard.integration.standalone;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

import com.tibco.cep.dashboard.management.STATE;

public class TCPStreamingServer implements Runnable {
	
	public static final int DEFAULT_PORT = 6171;
	
	public static final char DEFAULT_EOL = '\0';

	private int port;
	private char endOfLineChar;
	
	private STATE state;
	
	private ServerSocket serverSocket;
	private ExecutorService clientRequestThreadFactory;
	private List<TCPClientStreamer> clients;
	
	private ThreadGroup threadGroup;

	private Thread serverThread;
	
	public TCPStreamingServer(){
		state = STATE.STOPPED;
		port = DEFAULT_PORT;
		endOfLineChar = DEFAULT_EOL;
		threadGroup = new ThreadGroup("TCP Streamer Group");
		clients = new LinkedList<TCPClientStreamer>();
	}
	
	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public char getEndOfLineChar() {
		return endOfLineChar;
	}

	public void setEndOfLineChar(char endOfLineChar) {
		this.endOfLineChar = endOfLineChar;
	}

	public void init() throws IOException{
		clientRequestThreadFactory = Executors.newCachedThreadPool(new ThreadFactory(){

			private int counter = 0;
			
			@Override
			public Thread newThread(Runnable r) {
				counter++;
				if (counter == Integer.MAX_VALUE){
					counter = 1;
				}				
				return new Thread(threadGroup,r,"Client Thread-"+counter);
			}
			
		});
		serverSocket = new ServerSocket(port);
	}
	
	public void start(){
		state = STATE.RUNNING;
		serverThread = new Thread(threadGroup,this,serverSocket.getLocalPort()+" Listener");
		serverThread.setDaemon(true);
		serverThread.start();
	}
	
	public void stop(){
		for (TCPClientStreamer client : clients) {
			client.close();
		}
		clients.clear();
		state = STATE.STOPPED;
		try {
			clientRequestThreadFactory.awaitTermination(10L, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		state = STATE.STOPPED;
		try {
			serverSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		while(state == STATE.RUNNING){
			try {
				Socket clientSocket = serverSocket.accept();
				if (state == STATE.RUNNING){
					TCPClientStreamer client = new TCPClientStreamer(clientSocket);
					client.setEndOfLineChar(endOfLineChar);
					client.init();
					clients.add(client);
					clientRequestThreadFactory.execute(client);
				}
			} catch (IOException e) {
				if (serverSocket.isClosed() == false) {
					e.printStackTrace();
				}
			}
		}
	}
}
