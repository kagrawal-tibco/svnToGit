package com.tibco.cep.runtime.perf.stats;

public final class TPoolJQueueStats {

    private String name;

    private int maximumThreads;

    private int activeThreads;

    private int queueCapacity;

    private int queueSize;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMaximumThreads() {
        return maximumThreads;
    }

    public void setMaximumThreads(int maximumThreads) {
        this.maximumThreads = maximumThreads;
    }

    public int getActiveThreads() {
        return activeThreads;
    }

    public void setActiveThreads(int activeThreads) {
        this.activeThreads = activeThreads;
    }

    public int getQueueCapacity() {
        return queueCapacity;
    }

    public void setQueueCapacity(int queueCapacity) {
        this.queueCapacity = queueCapacity;
    }

    public int getQueueSize() {
        return queueSize;
    }

    public void setQueueSize(int queueSize) {
        this.queueSize = queueSize;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(TPoolJQueueStats.class.getSimpleName());
        sb.append("[name=");
        sb.append(name);
        sb.append(",maxThreads=");
        sb.append(maximumThreads);
        sb.append(",activeThreads=");
        sb.append(activeThreads);
        sb.append(",queueCapacity=");
        sb.append(queueCapacity);
        sb.append(",queueSize=");
        sb.append(queueSize);
        sb.append("]");
        return sb.toString();
    }

}
