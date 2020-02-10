package com.tibco.rta.common.service.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import javax.management.openmbean.CompositeDataSupport;
import javax.management.openmbean.CompositeType;
import javax.management.openmbean.OpenDataException;
import javax.management.openmbean.OpenType;
import javax.management.openmbean.SimpleType;
import javax.management.openmbean.TabularDataSupport;
import javax.management.openmbean.TabularType;

import com.tibco.rta.common.ConfigProperty;
import com.tibco.rta.common.service.StickyThreadAllocator;
import com.tibco.rta.common.service.StickyThreadPool;
import com.tibco.rta.log.Level;
import com.tibco.rta.log.LogManagerFactory;
import com.tibco.rta.log.Logger;
import com.tibco.rta.log.impl.LoggerCategory;
import com.tibco.rta.util.RejectedTaskResubmitter;

public class StickyThreadPoolImpl implements StickyThreadPool, StickyThreadPoolImplMBean {

    protected int threadCount;

    protected int queueSize;

    protected Random random = new Random(1000);

    protected Map<String, ExecutorService> executorServices = new HashMap<String, ExecutorService>();

    protected String moduleName;

    protected StickyThreadAllocator defaultAllocator = new DefaultAllocator();

	private static String[] itemNames = { "ThreadName" , "CurrentQueueSize", "CompletedTasks" };
	private static String[] itemDescriptions = { "Sticky Thread Name" , "Current queue size of thread", "Completed tasks for thread" };
	private static OpenType[] itemTypes = { SimpleType.STRING, SimpleType.INTEGER, SimpleType.LONG };
	private static String[] indexNames = { "ThreadName" };

	private static CompositeType compositeType = null;
	private static TabularType tabularType = null;

	static {
		try {
			compositeType = new CompositeType("Thread", "Thread info", itemNames, itemDescriptions, itemTypes);
			tabularType = new TabularType("Threads", "List of Threads", compositeType, indexNames);
		} catch (OpenDataException e) {
			throw new RuntimeException(e);
		}
	}
	
    protected static final Logger LOGGER = LogManagerFactory.getLogManager().getLogger(
            LoggerCategory.RTA_SERVICES_STICKY_THREADPOOL.getCategory());

    public StickyThreadPoolImpl(String moduleName) {
        this.moduleName = moduleName;
    }

    @Override
    public <T> Future<T> submit(Callable<T> task) {
        int threadNo = random.nextInt();
        threadNo = (threadNo < 0 ? -threadNo : threadNo) % threadCount;
        return sendToThread(task, threadNo);
    }

    @Override
    public <T> Future<T> submit(String key, Callable<T> task, StickyThreadAllocator allocator) {
        int currentThreadNo = allocator.allocateThread(threadCount, key);
        return sendToThread(task, currentThreadNo);
    }

    @Override
    public <T> Future<T> submit(String key, Callable<T> task) {
        int currentThreadNo = defaultAllocator.allocateThread(threadCount, key);
        return sendToThread(task, currentThreadNo);
    }

    @Override
    public void init(Properties configuration) throws Exception {
    	
    	try {
    		String propName = ConfigProperty.RTA_STICKY_WORKER_THREADS.getPropertyName();
    		if (moduleName != null) {
    			propName = propName + "." + moduleName;
    			String thrdCnt = configuration.getProperty(propName);
    			if (thrdCnt == null) {
    				try {
    	                 threadCount = Integer.parseInt((String) ConfigProperty.RTA_STICKY_WORKER_THREADS.getValue(configuration));
    	            } catch (Exception e2) {
    	            }
    			} else {
    				threadCount = Integer.parseInt(thrdCnt);
    			}
    		} else {
    			try {
	                 threadCount = Integer.parseInt((String) ConfigProperty.RTA_STICKY_WORKER_THREADS.getValue(configuration));
	            } catch (Exception e2) {
	            }
    		}
        } catch (Exception e) {
        	 try {
                 threadCount = Integer.parseInt((String) ConfigProperty.RTA_STICKY_WORKER_THREADS.getValue(configuration));
             } catch (Exception e2) {
             }
        }
    	
    	try {
    		
    		String propName = ConfigProperty.RTA_STICKY_WORKER_QUEUE_SIZE.getPropertyName();
    		if (moduleName != null) {
    			propName = propName + "." + moduleName;
    			String qSz = configuration.getProperty(propName);
    			if (qSz == null) {
    				try {
    					 queueSize = Integer.parseInt((String) ConfigProperty.RTA_STICKY_WORKER_QUEUE_SIZE.getValue(configuration));
    	            } catch (Exception e2) {
    	            }
    			} else {
    				queueSize = Integer.parseInt(qSz);
    			}
    		} else {
    			try {
					 queueSize = Integer.parseInt((String) ConfigProperty.RTA_STICKY_WORKER_QUEUE_SIZE.getValue(configuration));
	            } catch (Exception e2) {
	            }
    		}
        } catch (Exception e) {
        	 try {
        		 queueSize = Integer.parseInt((String) ConfigProperty.RTA_STICKY_WORKER_QUEUE_SIZE.getValue(configuration));
             } catch (Exception e2) {
             }
        }

        for (int i = 0; i < threadCount; i++) {
			ThreadPoolExecutor executorService = new ThreadPoolExecutor(1, 1, 60, TimeUnit.MINUTES,
					new LinkedBlockingQueue<Runnable>(queueSize), new StickyThreadFactory(moduleName, i),
                    new RejectedTaskResubmitter(LOGGER));
            executorServices.put(moduleName+i, executorService);
        }
        if (LOGGER.isEnabledFor(Level.INFO)) {
            LOGGER.log(Level.INFO, "Thread pool %s initialized with %d threads and %d queue size.", moduleName,
                    threadCount, queueSize);
        }

    }

    @Override
    public void start() throws Exception {

    }

    @Override
    public void stop() throws Exception {

    	for (Map.Entry<String, ExecutorService> entry : executorServices.entrySet()) {
    		try {
    			entry.getValue().shutdownNow();
    		} catch (Exception e) {

    		}
    	}
    }

    @Override
    public void suspend() {
        // TODO Auto-generated method stub

    }

    @Override
    public void resume() {
        // TODO Auto-generated method stub

    }

    @Override
    public boolean isStarted() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public Map<String, ExecutorService> getAllExecutors() {
        return executorServices;
    }


    private <T> Future<T> sendToThread(Callable<T> callable, int threadNo) {
        ExecutorService e = executorServices.get(moduleName+threadNo);
        return e.submit(callable);
    }

    private static class StickyThreadFactory implements ThreadFactory {
        private int threadNo;
        private String name;

        StickyThreadFactory(String name, int threadNo) {
            this.name = name;
            this.threadNo = threadNo;
        }

        @Override
        public Thread newThread(Runnable runnable) {
            Thread t = new Thread(runnable, name + "-" + threadNo);
            t.setDaemon(true);
            return t;
        }
    }


    class DefaultAllocator implements StickyThreadAllocator {

        @Override
        public int allocateThread(int totalThreads, String key) {
            int hashCode = key.hashCode();
            if (hashCode < 0) {
                //make it positive.
                hashCode *= -1;
            }
            return hashCode % threadCount;
        }

    }

	@Override
	public TabularDataSupport getThreadDetails() {
		// TODO Auto-generated method stub
		TabularDataSupport threadData = new TabularDataSupport(tabularType);
		for(int i = 0;i<threadCount;i++){
			Map<String, Object> items = new HashMap<String, Object>();

			items.put(itemNames[0], moduleName+i);
			items.put(itemNames[1], ((ThreadPoolExecutor) executorServices.get(moduleName+i)).getQueue().size());
			items.put(itemNames[2], ((ThreadPoolExecutor) executorServices.get(moduleName+i)).getCompletedTaskCount());
			
			CompositeDataSupport value = null;
			try {
				value = new CompositeDataSupport(compositeType, items);
			} catch (OpenDataException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
						
			threadData.put(value);
		}
		return threadData;
	}

	@Override
	public long getCompletedTasks() {
		long totalCompletedTasks = 0;
		for (int i = 0; i < threadCount; i++) {
			totalCompletedTasks += ((ThreadPoolExecutor) executorServices.get(moduleName+i)).getCompletedTaskCount();
		}
		return totalCompletedTasks;
	}

	@Override
	public long getCurrentQueueSize() {
		long totalTasksInQueues = 0;
		for (int i = 0; i < threadCount; i++) {
			totalTasksInQueues += ((ThreadPoolExecutor) executorServices.get(moduleName+i)).getQueue().size();
		}
		return totalTasksInQueues;
	}

	@Override
	public long getQueueCapacity() {
		return queueSize;
	}
}
