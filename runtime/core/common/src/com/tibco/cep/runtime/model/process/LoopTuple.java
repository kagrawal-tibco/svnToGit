package com.tibco.cep.runtime.model.process;

import java.util.List;

import com.tibco.cep.runtime.model.serializers.SerializableLite;


public interface LoopTuple extends ObjectBean,SerializableLite {
	
	public static final int NOT_INITIALIZED = -1;
	
	String getJobKey();
	
	String getLoopKey();
	
	String getTaskName();
	
	int getCounter();
	
	int getCounterMax();

	void incrementCounter() throws Exception;

	boolean isComplete();
	
	void setComplete() throws Exception;

	void initialize(int evalCounterMax) throws Exception;
	
	boolean isInitialized();

	public abstract void setComplete(boolean complete);

	public abstract void setCounterMax(int counterMax);

	public abstract void setCounter(int counter);

	public abstract void setTaskName(String taskName);

	public abstract void setJobKey(String jobKey);

	public abstract void setLoopKey(String loopKey);

	public abstract void setState(BeanOp state);

	public abstract BeanOp getState();

	public abstract void setEventIds(List<Long> eventIds);

	public abstract List<Long> getEventIds();

	public abstract BeanOp getBeanOp();

	public abstract Class getType();

	public abstract String getKey();

}
