package com.tibco.cep.runtime.metrics;

/*
* Author: Ashwin Jayaprakash Date: Feb 17, 2009 Time: 10:53:01 AM
*/
public enum StopWatchOwnerOption {
    OPTION_ACCUMULATE_COUNT(CountingStopWatch.class),
    OPTION_MEASURE_LATENCY(CountingStopWatch.class);

    //-----------

    Class<? extends StopWatch> appliesTo;

    StopWatchOwnerOption[] dependsOn;

    StopWatchOwnerOption(Class<? extends StopWatch> appliesTo) {
        this.appliesTo = appliesTo;
    }

    StopWatchOwnerOption(Class<? extends StopWatch> appliesTo, StopWatchOwnerOption... dependsOn) {
        this.appliesTo = appliesTo;
        this.dependsOn = dependsOn;
    }

    public Class<? extends StopWatch> getAppliesTo() {
        return appliesTo;
    }

    public StopWatchOwnerOption[] getDependsOn() {
        return dependsOn;
    }
}
