package com.tibco.cep.dashboard.timer;

import java.util.concurrent.Future;

import com.tibco.cep.dashboard.management.ExceptionHandler;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.Logger;

public abstract class ExceptionResistentTimerTask implements Runnable {

	protected String name;

	protected ExceptionHandler exceptionHandler;

	protected boolean cancelOnException;

	protected Logger logger;

	private Future<?> future;

	public ExceptionResistentTimerTask(String name, Logger logger, ExceptionHandler exceptionHandler, boolean cancelOnException) {
		super();
		this.name = name;
		this.logger = logger;
		this.exceptionHandler = exceptionHandler;
		this.cancelOnException = cancelOnException;
	}

	void setFuture(Future<?> future) {
		this.future = future;
	}

	@Override
	public final void run() {
		if (logger.isEnabledFor(Level.TRACE) == true){
			logger.log(Level.TRACE,"Running "+name);
		}
		try {
			doRun();
		} catch (Throwable t) {
			handleThrowable(t);
		}
	}

	protected abstract void doRun();

	protected void handleThrowable(Throwable t) {
		String msg = "";
		if (cancelOnException == true) {
			boolean cancel = cancel();
			if (cancel == true) {
				msg = "Cancelled "+name+" since it generated an exception";
			}
			else {
				msg = "Could not cancel "+name+" although it generated an exception";
			}
		}
		if (exceptionHandler != null){
			exceptionHandler.handleException(msg,t, Level.ERROR, Level.ERROR);
		}
	}

	public boolean cancel() {
		if (logger.isEnabledFor(Level.DEBUG) == true){
			logger.log(Level.DEBUG,"Cancelling "+name);
		}
		return future.cancel(true);
	}

	public boolean isCancelled() {
		return future.isCancelled();
	}

	@Override
	public String toString() {
		return name;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ExceptionResistentTimerTask other = (ExceptionResistentTimerTask) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

}
