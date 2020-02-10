package com.tibco.cep.driver.http.client.impl.httpcomponents;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.LogManager;
import com.tibco.cep.kernel.service.logging.LogManagerFactory;
import com.tibco.cep.kernel.service.logging.Logger;

/**
 * 
 * @author majha
 */
public class JobProcessor<E> {
    
    private DaemonThreadFactory factory = new DaemonThreadFactory();

    private ExecutorService executor;

    private Callable<E> jobToBeExecuted;

    private long timeOut;

    private String jobName;
	
	//Logger manager
     LogManager m_LogManager;
    //Logger
     Logger m_Logger;

     /**
      * LoggerInit - initialization of logger is done here
      */
     public void LoggerInit() {
		// Get the LogManager object from LogManagerFactory
		m_LogManager = LogManagerFactory.getLogManager();
		// Get the logger from LogManager
		m_Logger = m_LogManager.getLogger(JobProcessor.class);
	}


	/**
	 * Default constructor.
	 */
	public JobProcessor(Callable<E> job, String name, long time) {
		this.executor = Executors.newSingleThreadExecutor(factory);
		jobToBeExecuted = job;
		jobName = name;
		timeOut = time;
		LoggerInit();
	}

	/**
	 * This method provides facility to perform job and collect result.
	 */
	public E perform() throws Exception {
		E result = null;
		Future<E> future = executor.submit(jobToBeExecuted);
		Exception exception = null;
		try {
			if (timeOut == -1) {
				result = future.get();
            } else {
				result = future.get(timeOut, TimeUnit.MILLISECONDS);
            }
        } catch (InterruptedException e) {
			m_Logger.log(Level.ERROR, e, "Interruption occured while making http request");
			result = null;
			exception = e;
		} catch (ExecutionException e) {
			m_Logger.log(Level.ERROR, e , e.getMessage());
			result = null;
			exception = e;
		} catch (TimeoutException e) {
			m_Logger.log(Level.ERROR, e , "Time out happened while making http request: %1$s ", e.getMessage());
			result = null;
			exception = e;
		} catch(Throwable e) {
			m_Logger.log(Level.ERROR, e , e.getMessage());
			result = null;
			exception = new Exception(e);
		}
		finally {
			executor.shutdownNow();
		}
		if (exception != null) throw exception;
		return result;
	}

	/**
	 * This class provides a facility to create daemon threads.
	 */
	private class DaemonThreadFactory implements ThreadFactory {
		public Thread newThread(Runnable r) {
			Thread t = Executors.defaultThreadFactory().newThread(r);
			t.setName(jobName);
			t.setDaemon(true);
			return t;
		}
	}
}
