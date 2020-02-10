package com.tibco.cep.bpmn.runtime.activity.gateways;

import com.tibco.cep.runtime.model.process.MergeTuple.MergeEntry;

public class DefaultMergeEntry implements MergeEntry {
    long processId;

    String transitionName;

    long timeMillis;
    
    boolean isMerged = true;
    
    boolean hasError = false;
    
    public DefaultMergeEntry() {
    	
    }

    public DefaultMergeEntry(long processId, String transitionName,boolean hasError) {
        this.processId = processId;
        this.transitionName = transitionName;
        this.timeMillis = System.currentTimeMillis();
        this.hasError = hasError;
        isMerged=true;
    }

    public DefaultMergeEntry(long processId, String transitionName,boolean hasError, long timeMillis) {
        this.processId = processId;
        this.transitionName = transitionName;
        this.timeMillis = timeMillis;
        this.hasError = hasError;
        isMerged=true;
    }

    @Override
	public boolean hasError() {
		return hasError;
	}

	@Override
    public long getProcessId() {
        return processId;
    }

    @Override
    public String getTransitionName() {
        return transitionName;
    }

    @Override
    public long getTimeMillis() {
        return timeMillis;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DefaultMergeEntry)) {
            return false;
        }

        DefaultMergeEntry that = (DefaultMergeEntry) o;

        if (processId != that.processId) {
            return false;
        }
        if (!transitionName.equals(that.transitionName)) {
            return false;
        }
        if( hasError != that.hasError){
        	return false;
        }

        return true;
    }
    
    
    
    
    @Override
	public void setProcessId(long processId) {
		this.processId = processId;
	}

	@Override
	public void setTransitionName(String transitionName) {
		this.transitionName = transitionName;
	}

	@Override
	public void setTimeMillis(long timeMillis) {
		this.timeMillis = timeMillis;
	}

	@Override
	public void setMerged(boolean isMerged) {
		this.isMerged = isMerged;
	}

	@Override
	public void setMerged() {
		this.isMerged = false;
	}

    @Override
    public int hashCode() {
        int result = (int) (processId ^ (processId >>> 32));
        result = 31 * result + transitionName.hashCode();
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append("{processId=").append(processId);
        sb.append(", transitionName='").append(transitionName).append('\'');
        sb.append(", timeMillis=").append(timeMillis);
        sb.append('}');
        return sb.toString();
    }

	@Override
	public boolean isMerged() {
		return isMerged;
	}
}