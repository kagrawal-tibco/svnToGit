package com.tibco.cep.bpmn.runtime.activity.tasks;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import com.tibco.cep.runtime.model.process.LoopTuple;

public class DefaultLoopTuple implements LoopTuple {
	
	String loopKey;
	String jobKey;
	String taskName;
	AtomicInteger counter = new AtomicInteger(0);
	int counterMax;
	boolean complete;
	ArrayList<Long> eventIds = new ArrayList<Long>();
	BeanOp state;

	public DefaultLoopTuple() {
		super();
	}
	
	public DefaultLoopTuple(String jobKey, String lkey, String name,int max ) {
		this.jobKey = jobKey;
		this.loopKey = lkey;
		taskName = name;
		counterMax = max;
		state = BeanOp.BEAN_CREATED;
	}


	public DefaultLoopTuple(String jobKey, String lkey, String name) {
		this(jobKey,lkey,name,NOT_INITIALIZED);
	}
	
    @Override
	public String getKey() {
    	return loopKey;
    }
	
	

	@Override
	public Class getType() {
		return LoopTuple.class;
	}

	@Override
	public BeanOp getBeanOp() {
		return state;
	}

	@Override
	public String getJobKey() {
		return jobKey;
	}

	@Override 
	public String getLoopKey() {
		return loopKey;
	}

	@Override
	public String getTaskName() {
		return taskName;
	}

	@Override
	public int getCounter() {
		return counter.get();
	}

	@Override
	public int getCounterMax() {
		return counterMax;
	}
	
	
	@Override
	public boolean isComplete() {
		return complete || (counter.get() == counterMax);
	}
	
	public void  setComplete(){
		state = BeanOp.BEAN_DELETED;
		complete = true;
	}

	@Override
	public boolean isInitialized() {
		return counterMax != NOT_INITIALIZED;
	}

	@Override
	public void initialize(int evalCounterMax) {
		this.counterMax = evalCounterMax;
		
	}
	
	


	@Override
	public List<Long> getEventIds() {
		return eventIds;
	}

	@Override
	public void setEventIds(List<Long> eventIds) {
		this.eventIds.addAll(eventIds);
	}

	@Override
	public BeanOp getState() {
		return state;
	}

	@Override
	public void setState(BeanOp state) {
		this.state = state;
	}

	@Override
	public void setLoopKey(String loopKey) {
		this.loopKey = loopKey;
	}

	@Override
	public void setJobKey(String jobKey) {
		this.jobKey = jobKey;
	}

	@Override
	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	@Override
	public void setCounter(int counter) {
		this.counter = new AtomicInteger(counter);
	}

	@Override
	public void setCounterMax(int counterMax) {
		this.counterMax = counterMax;
	}

	@Override
	public void setComplete(boolean complete) {
		this.complete = complete;
	}

	public void incrementCounter() {
		state = BeanOp.BEAN_UPDATED;
		counter.incrementAndGet();
	}
	

	
	@Override
	public void writeExternal(DataOutput out) throws IOException {
		out.writeUTF(jobKey);
		out.writeUTF(loopKey);
		out.writeUTF(taskName);
		out.writeInt(counterMax);
		out.writeInt(counter.get());
		out.writeBoolean(complete);
		out.writeUTF(state.toString());

	}

	@Override
	public void readExternal(DataInput in) throws IOException {
		jobKey = in.readUTF();
		loopKey = in.readUTF();
		taskName = in.readUTF();
		counterMax = in.readInt();
		counter.set(in.readInt());
		complete=in.readBoolean();
		state = BeanOp.valueOf(in.readUTF());

	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((jobKey == null) ? 0 : jobKey.hashCode());
		result = prime * result + ((loopKey == null) ? 0 : loopKey.hashCode());
		result = prime * result + ((taskName == null) ? 0 : taskName.hashCode());
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
		DefaultLoopTuple other = (DefaultLoopTuple) obj;
		if (jobKey == null) {
			if (other.jobKey != null)
				return false;
		} else if (!jobKey.equals(other.jobKey))
			return false;
		if (loopKey == null) {
			if (other.loopKey != null)
				return false;
		} else if (!loopKey.equals(other.loopKey))
			return false;
		if (taskName == null) {
			if (other.taskName != null)
				return false;
		} else if (!taskName.equals(other.taskName))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "DefaultLoopTuple [loopKey=" + loopKey + ", jobKey=" + jobKey + ", taskName=" + taskName + ", counter=" + counter + ", counterMax=" + counterMax
				+ ", complete=" + complete + ", state=" + state + "]";
	}
	
	
	
	
	
	


	
	
}
