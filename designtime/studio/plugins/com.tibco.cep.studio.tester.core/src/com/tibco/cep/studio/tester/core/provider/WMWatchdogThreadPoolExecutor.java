/**
 * 
 */
package com.tibco.cep.studio.tester.core.provider;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author aathalye
 *
 */
public class WMWatchdogThreadPoolExecutor extends ThreadPoolExecutor {
	
	private static WMWatchdogThreadPoolExecutor INSTANCE;
	
	/**
	 * @param arg0
	 * @param arg1
	 * @param arg2
	 * @param arg3
	 * @param arg4
	 */
	private WMWatchdogThreadPoolExecutor(int corePoolSize,
										 int maximumPoolSize, 
			                             long keepAliveTime,
			                             TimeUnit timeUnit, 
			                             BlockingQueue<Runnable> workQueue,
			                             ThreadFactory threadFactory,
			                             RejectedExecutionHandler rejectedExecutionHandler) {
		super(corePoolSize, maximumPoolSize, keepAliveTime, timeUnit, workQueue, threadFactory, rejectedExecutionHandler);
		
	}
	
	public static synchronized WMWatchdogThreadPoolExecutor createExecutor(int maximumPoolSize, 
			                                                  long keepAliveTime,
			                                                  TimeUnit timeUnit, 
			                                                  int maxQueueDepth) {
		if (maxQueueDepth <= 0) {
			//Negative value signifies unbounded queue
			maxQueueDepth = Integer.MAX_VALUE;
		}
		if (INSTANCE != null) {
			return INSTANCE;
		}
		BlockingQueue<Runnable> workQueue = new LinkedBlockingQueue<Runnable>(maxQueueDepth);
		
		ThreadFactory threadFactory = new WMWatchdogThreadFactory();
		
		INSTANCE = 
			new WMWatchdogThreadPoolExecutor(0, 
					       maximumPoolSize,
					       keepAliveTime,
					       timeUnit,
					       workQueue,
					       threadFactory,
					       new ReteChangeWtachdogTaskRejectionHandler());
		
		return INSTANCE;
	}
	
	static class WMWatchdogThreadFactory implements ThreadFactory {
		
		private ThreadGroup threadGroup;

        private AtomicInteger threadNumber = new AtomicInteger(1);

        private AtomicInteger poolNumber = new AtomicInteger(1);

        private String threadName;

        private static final String NAME_PREFIX = "Tester-WM-Watchdog-";
        
        WMWatchdogThreadFactory() {
            SecurityManager systemManager = System.getSecurityManager();
            threadGroup = (systemManager != null)? systemManager.getThreadGroup() :
                                     Thread.currentThread().getThreadGroup();
            threadName = NAME_PREFIX + poolNumber.getAndIncrement();
        }

        public WMWatchdogThreadFactory(final ThreadGroup threadGroup) {
            this.threadGroup = threadGroup;
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
                                       threadName + "-Thread-" + threadNumber.getAndIncrement());
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
	
	private static class ReteChangeWtachdogTaskRejectionHandler implements RejectedExecutionHandler {

		/* (non-Javadoc)
		 * @see java.util.concurrent.RejectedExecutionHandler#rejectedExecution(java.lang.Runnable, java.util.concurrent.ThreadPoolExecutor)
		 */
		@Override
		public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
			// TODO Auto-generated method stub
			
		}
		
	}
}
