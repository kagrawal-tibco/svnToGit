package com.tibco.cep.runtime.perf.stats;


public final class DestinationStats {

    private String name;

    private long totalEventsReceived;

    private long totalEventsSent;

    private double eventsReceivedPerSecond;

    private long lastEPSModification = -1;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getTotalEventsReceived() {
        return totalEventsReceived;
    }

    public void setTotalEventsReceived(long totalEventsReceived) {
        this.totalEventsReceived = totalEventsReceived;
    }

    public long getTotalEventsSent() {
        return totalEventsSent;
    }

    public void setTotalEventsSent(long totalEventsSent) {
        this.totalEventsSent = totalEventsSent;
    }

    public double getEventsReceivedPerSecond() {
        return eventsReceivedPerSecond;
    }

    public void setEventsReceivedPerSecond(double d) {
        this.eventsReceivedPerSecond = d;
    }

    public String getLastEventReceived() {
        if (lastEPSModification == -1) {
            return "No ERPS Data";
        }
        return TimeUnitUtil.convert(System.currentTimeMillis() - lastEPSModification);
    }

    public void setLastEPSModification(long lastEPSModification) {
        this.lastEPSModification = lastEPSModification;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(DestinationStats.class.getSimpleName());
        sb.append("[name=");
        sb.append(name);
        sb.append(",TotalEventsReceived=");
        sb.append(totalEventsReceived);
        sb.append(",TotalEventsSent=");
        sb.append(totalEventsSent);
        sb.append(",eps=");
        sb.append(eventsReceivedPerSecond);
        sb.append(",lastepsmodification=");
        sb.append(lastEPSModification);
        sb.append("]");
        return sb.toString();
    }

}
