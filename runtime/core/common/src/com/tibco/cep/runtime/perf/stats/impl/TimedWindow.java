package com.tibco.cep.runtime.perf.stats.impl;

final class TimedWindow {

    private long startTime;

    private long endTime;

    private long lastModifiedTime;

    private long value;

    public void add(long value) {
        set(this.value + value);
    }

    public void set(long value) {
        if (hasExpired() == false) {
            this.value = value;
            this.lastModifiedTime = System.currentTimeMillis();
        }
    }

    public boolean hasExpired() {
        return System.currentTimeMillis() > endTime;
    }

    public boolean hasExpired(long time){
        return time > endTime;
    }

    public long getStartTime() {
        return startTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public long getValue() {
        return value;
    }

    public long getLastModifiedTime() {
        return lastModifiedTime;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(TimedWindow.class.getSimpleName());
        sb.append("[stime=");
        sb.append(startTime);
        sb.append(",etime=");
        sb.append(endTime);
        sb.append(",value=");
        sb.append(value);
        sb.append(",lastModifiedTime=");
        sb.append(lastModifiedTime);
        sb.append("]");
        return sb.toString();
    }

    final static TimedWindow create(long duration, long initialValue) {
        TimedWindow w = new TimedWindow();
        w.startTime = System.currentTimeMillis();
        w.endTime = w.startTime + duration;
        w.set(initialValue);
        return w;
    }

    final static TimedWindow create(long duration) {
        return create(duration, 0);
    }
}
