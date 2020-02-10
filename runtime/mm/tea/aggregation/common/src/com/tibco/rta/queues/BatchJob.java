package com.tibco.rta.queues;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 3/12/12
 * Time: 10:22 AM
 * To change this template use File | Settings | File Templates.
 */
public class BatchJob {
    
    private Object wrappedObject;

    private long creationTime;

    public BatchJob(Object wrappedObject) {
        this.wrappedObject = wrappedObject;
    }

    public Object getWrappedObject() {
        return wrappedObject;
    }

    public long getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(long creationTime) {
        this.creationTime = creationTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BatchJob batchJob = (BatchJob) o;

        if (creationTime != batchJob.creationTime) return false;
        if (wrappedObject != null ? !wrappedObject.equals(batchJob.wrappedObject) : batchJob.wrappedObject != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = wrappedObject != null ? wrappedObject.hashCode() : 0;
        result = 31 * result + (int) (creationTime ^ (creationTime >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return wrappedObject.toString();
    }
}
