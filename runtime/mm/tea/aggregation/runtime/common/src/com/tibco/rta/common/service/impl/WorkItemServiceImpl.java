package com.tibco.rta.common.service.impl;

import java.lang.management.ManagementFactory;
import java.util.Properties;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import javax.management.MBeanServer;
import javax.management.ObjectName;

import com.tibco.rta.common.ConfigProperty;
import com.tibco.rta.common.service.WorkItem;
import com.tibco.rta.common.service.WorkItemService;
import com.tibco.rta.log.Level;
import com.tibco.rta.log.LogManagerFactory;
import com.tibco.rta.log.Logger;
import com.tibco.rta.log.impl.LoggerCategory;
import com.tibco.rta.util.RejectedTaskResubmitter;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 23/11/12
 * Time: 12:24 PM
 * To change this template use File | Settings | File Templates.
 */
public class WorkItemServiceImpl extends AbstractStartStopServiceImpl implements WorkItemService, WorkItemServiceImplMBean {

    protected int threadCount = 10;

    protected int queueSize = 10;

    protected ThreadPoolExecutor executorService;


    protected String moduleName = "worker-thread";

	protected long timeToLive;

	protected int corePoolSize;

    protected static final Logger LOGGER = LogManagerFactory.getLogManager().getLogger(LoggerCategory.RTA_SERVICES_GENERIC_THREADPOOL.getCategory());

    @Override
    public <T, W extends WorkItem<T>> Future<T> addWorkItem(W workitem) {
        return executorService.submit(workitem);
    }

    public WorkItemServiceImpl() {

    }

    public WorkItemServiceImpl(String name) {
        this.moduleName = name;
    }


    @Override
    public void setThreadPoolName(String threadPoolName) {
        this.moduleName = threadPoolName;
    }
    
    @Override
    public void init(Properties configuration) throws Exception {
    	
    	readProperties(configuration);

        LinkedBlockingQueue<Runnable> jobQueue = new LinkedBlockingQueue<Runnable>(queueSize);
        executorService = new ThreadPoolExecutor(corePoolSize, threadCount, timeToLive, TimeUnit.SECONDS, jobQueue,
                new WorkItemServiceThreadFactory(moduleName), new RejectedTaskResubmitter(LOGGER));
        if (LOGGER.isEnabledFor(Level.INFO)) {
            LOGGER.log(Level.INFO, "Thread pool [%s] initialized with [%d] threads and [%d] queue size.", moduleName, threadCount, queueSize);
        }
        registerMBean(configuration);
    }

	protected void readProperties(Properties configuration) {
		try {
    		String propName = ConfigProperty.RTA_WORKER_THREADS.getPropertyName();
    		if (moduleName != null) {
    			propName = propName + "." + moduleName;
    			String thrdCnt = configuration.getProperty(propName);
    			if (thrdCnt == null) {
    				try {
    	                 threadCount = Integer.parseInt((String) ConfigProperty.RTA_WORKER_THREADS.getValue(configuration));
    	            } catch (Exception e2) {
    	            }
    			} else {
    				threadCount = Integer.parseInt(thrdCnt);
    			}
    		} else {
    			try {
	                 threadCount = Integer.parseInt((String) ConfigProperty.RTA_WORKER_THREADS.getValue(configuration));
	            } catch (Exception e2) {
	            }
    		}
        } catch (Exception e) {
        	 try {
                 threadCount = Integer.parseInt((String) ConfigProperty.RTA_WORKER_THREADS.getValue(configuration));
             } catch (Exception e2) {
             }
        }
    	
    	try {
    		
    		String propName = ConfigProperty.RTA_WORKER_QUEUE_SIZE.getPropertyName();
    		if (moduleName != null) {
    			propName = propName + "." + moduleName;
    			String qSz = configuration.getProperty(propName);
    			if (qSz == null) {
    				try {
    					 queueSize = Integer.parseInt((String) ConfigProperty.RTA_WORKER_QUEUE_SIZE.getValue(configuration));
    	            } catch (Exception e2) {
    	            }
    			} else {
    				queueSize = Integer.parseInt(qSz);
    			}
    		} else {
    			try {
					 queueSize = Integer.parseInt((String) ConfigProperty.RTA_WORKER_QUEUE_SIZE.getValue(configuration));
	            } catch (Exception e2) {
	            }
    		}
        } catch (Exception e) {
        	 try {
        		 queueSize = Integer.parseInt((String) ConfigProperty.RTA_WORKER_QUEUE_SIZE.getValue(configuration));
             } catch (Exception e2) {
             }
        }
    	
        
    	try {
    		String propName = ConfigProperty.RTA_WORKER_THREADS_ALIVE_TIME.getPropertyName();
    		if (moduleName != null) {
    			propName = propName + "." + moduleName;
    			String ttlSt = configuration.getProperty(propName);
    			if (ttlSt == null) {
    				timeToLive = Long.parseLong((String)ConfigProperty.RTA_WORKER_THREADS_ALIVE_TIME.getValue(configuration));
    			} else {
    				timeToLive = Long.parseLong(ttlSt);
    			}
    		} else {
    			timeToLive = Long.parseLong((String)ConfigProperty.RTA_WORKER_THREADS_ALIVE_TIME.getValue(configuration));
    		}
    		
    	} catch (Exception e) {
    		try {
    			timeToLive = Long.parseLong((String)ConfigProperty.RTA_WORKER_THREADS_ALIVE_TIME.getValue(configuration));
    		} catch (Exception e2) {
    			
    		}
    	}
    	
    	try {
    		String propName = ConfigProperty.RTA_WORKER_THREADS_CORE_POOL_SIZE.getPropertyName();
    		if (moduleName != null) {
    			propName = propName + "." + moduleName;
    			String cPSz = configuration.getProperty(propName);
    			if (cPSz == null) {
    				corePoolSize = Integer.parseInt((String)ConfigProperty.RTA_WORKER_THREADS_CORE_POOL_SIZE.getValue(configuration));
    			} else {
    				corePoolSize = Integer.parseInt(cPSz);
    			}
    		} else {
    			corePoolSize = Integer.parseInt((String)ConfigProperty.RTA_WORKER_THREADS_CORE_POOL_SIZE.getValue(configuration));
    		}
    		
    	} catch (Exception e) {
    		try {
    			corePoolSize = Integer.parseInt((String)ConfigProperty.RTA_WORKER_THREADS_CORE_POOL_SIZE.getValue(configuration));
    		} catch (Exception e2) {
    			
    		}
    	}
	}

    @Override
    public void start() throws Exception {
        if (LOGGER.isEnabledFor(Level.INFO)) {
            LOGGER.log(Level.INFO, "Thread pool %s started.", moduleName);
        }
    }

    @Override
    public void stop() throws Exception {
        try {
            executorService.shutdownNow();
            if (LOGGER.isEnabledFor(Level.INFO)) {
                LOGGER.log(Level.INFO, "Thread pool %s stopped.", moduleName);
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, "Error shutting down session", e);
        }
    }

    @Override
    public int getLiveThreadCount() {
        return executorService.getPoolSize();
    }

    @Override
    public int getCurrentQueueSize() {
        return executorService.getQueue().size();
    }

    @Override
    public int getCompletedTasks() {
        return (int) executorService.getCompletedTaskCount();
    }

    protected class WorkItemServiceThreadFactory implements ThreadFactory {

        private AtomicInteger threadCounter = new AtomicInteger(0);

        public WorkItemServiceThreadFactory(String name) {
            moduleName = name;
        }

        @Override
        public Thread newThread(Runnable runnable) {
            Thread t = new Thread(runnable, moduleName + "-" + threadCounter.getAndIncrement());
            t.setDaemon(true);
            return t;
        }
    }

    public static void main(String[] args) throws Exception {

        WorkItemServiceImpl wi = new WorkItemServiceImpl("test");
        Properties p = new Properties();
        p.put("tea.agent.worker.thread.count", "1");
        p.put("tea.agent.worker.queue.size", "1");

        wi.init(p);

        MyJob j1 = new MyJob();

        wi.addWorkItem(j1);

        MyJob j2 = new MyJob();

        try {
            wi.addWorkItem(j2);
        } catch (Exception e) {
            System.out.println("Exception adding j2");
            e.printStackTrace();
        }

        MyJob j3 = new MyJob();

        try {
            wi.addWorkItem(j3);
        } catch (Exception e) {
            System.out.println("Exception adding j3");
            e.printStackTrace();
        }


    }

    static class MyJob implements WorkItem<String> {

        @Override
        public String call() throws Exception {
            System.out.println(Thread.currentThread().getName() + " Sleeping for 30 secs..");
            try {
                Thread.sleep(30000);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + " Sleeping for 30 secs done.");
            return null;
        }

        @Override
        public String get() {
            // TODO Auto-generated method stub
            return null;
        }

    }

    protected void registerMBean(Properties configuration) throws Exception {
        MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
        String objectName = moduleName;
        String mbeanPrefix = (String) ConfigProperty.BE_TEA_AGENT_SERVICE_MBEANS_PREFIX.getValue(configuration);
        ObjectName name = new ObjectName(mbeanPrefix + ".threadpool:type=" + objectName);
        if (!mbs.isRegistered(name)) {
            mbs.registerMBean(this, name);
        }
    }

	@Override
	public int getMinThreads() {
		return corePoolSize;
	}

	@Override
	public int getMaxThreads() {
		return threadCount;
	}

}
