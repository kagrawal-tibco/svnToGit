/**
 * 
 */
package com.tibco.cep.studio.rms.client;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.Date;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import com.tibco.cep.studio.rms.core.RMSCorePlugin;
import com.tibco.cep.studio.util.StudioConfig;

/**
 * @author aathalye
 *
 */
public class Pinger implements Runnable {
	
	private String host;
	
	private int port;
	
	private SocketAddress socketAddress;
	
	private ScheduledExecutorService executorService = new ScheduledThreadPoolExecutor(1, new PingerThreadFactory(), new ThreadPoolExecutor.CallerRunsPolicy());
	
	private boolean isRunning;
	
	private long heartbeatStoppedElapsedTime;
	
	/**
	 * Whether the pinged server is up or not
	 */
	private boolean isServerUp;
	
	private int delay;

	private static final boolean PINGER_STATUS_DEFAULT = false;

	public Pinger(String host, int port, int delay) {
		super();
		this.host = host;
		this.port = port;
		this.delay = delay;
	}
	

	public Pinger(SocketAddress socketAddress, int delay) {
		super();
		this.socketAddress = socketAddress;
		this.delay = delay;
	}


	/* (non-Javadoc)
	 * @see java.util.concurrent.Callable#call()
	 */
	@Override
	public void run() {
	String pingAllow = StudioConfig.getInstance().getProperty("rms.request.ping", Boolean.toString(PINGER_STATUS_DEFAULT));
	if (Boolean.parseBoolean(pingAllow)) {
		RMSCorePlugin.debug(Pinger.class.getName(), "Attempting connection to server [host {0} : port {1}]", host, port);
		//open a socket and read something from it
		Socket clientSocket = new Socket();
		socketAddress = (socketAddress != null) ? socketAddress : new InetSocketAddress(host, port);
		synchronized (this) {
			try {
				clientSocket.connect(socketAddress);
				isServerUp = true;
				heartbeatStoppedElapsedTime = 0;
			} catch (IOException e) {
				isServerUp = false;
				if (heartbeatStoppedElapsedTime == 0) heartbeatStoppedElapsedTime = new Date().getTime();
				RMSCorePlugin.log(e);
			} finally {
				try {
					clientSocket.close();
				} catch (IOException e) {
					RMSCorePlugin.log(e);
				}
			}
			//This will guarantee that the isRunning flag will be set to true only when
			//the server connection attempt is made, and also avoid race condition.
			isRunning = true;
		}
		}
	}
	
	public synchronized void start() throws Exception {
		if (!isRunning) {
			executorService.scheduleAtFixedRate(this, 2, delay, TimeUnit.SECONDS);
		}
	}
	
	public synchronized void stop() {
		executorService.shutdown();
		isRunning = false;
	}

	/**
	 * @return the isRunning
	 */
	public final synchronized boolean isRunning() {
		return isRunning;
	}
	

	/**
	 * @return the isServerUp
	 */
	public final synchronized boolean isConnectionSuccessful() {
		return isServerUp;
	}
	
	/**
	 * @return the heartbeatStoppedElapsedTime
	 */
	public final synchronized long getHeartbeatStoppedElapsedTime(){
		return heartbeatStoppedElapsedTime;
	}


	/**
	 * @return the delay
	 */
	public final int getDelay() {
		return delay;
	}
	
	class PingerThreadFactory implements ThreadFactory {
		private ThreadGroup threadGroup;

	    private AtomicInteger threadNumber = new AtomicInteger(1);

	    private AtomicInteger poolNumber = new AtomicInteger(1);

	    private String threadName;

	    private static final String NAME_PREFIX = "RMS-Pinger-";

	    public PingerThreadFactory() {
	        SecurityManager systemManager = System.getSecurityManager();
	        threadGroup = (systemManager != null)? systemManager.getThreadGroup() :
	                                 Thread.currentThread().getThreadGroup();
	        threadName = NAME_PREFIX + poolNumber.getAndIncrement();
	    }

	    
	    /**
	     * Constructs a new <tt>Thread</tt>.  Implementations may also initialize
	     * priority, name, daemon status, <tt>ThreadGroup</tt>, etc.
	     *
	     * @param r a runnable to be executed by new thread instance
	     * @return constructed thread
	     */
	    public Thread newThread(Runnable r) {
	        Thread thread = new Thread(threadGroup, r,
	                                   threadName + "-" + threadNumber.getAndIncrement());
	        //Similar to default implementation
	        if (thread.isDaemon()) {
	            thread.setDaemon(false);
	        }
	        if (thread.getPriority() != Thread.NORM_PRIORITY) {
	            thread.setPriority(Thread.NORM_PRIORITY);
	        }
	        return thread;
	    }
	}
}
