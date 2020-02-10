package com.tibco.be.migration;

/*
 * Author: Ashwin Jayaprakash Date: Feb 25, 2008 Time: 1:45:21 PM
 */

/**
 * Simple class that contains the status of the CSV write operation.
 */
public class Report {
    protected final long totalBytesWritten;

    protected final int totalMainFileFragments;

    public Report(long totalBytesWritten, int totalMainFileFragments) {
        this.totalBytesWritten = totalBytesWritten;
        this.totalMainFileFragments = totalMainFileFragments;
    }

    public long getTotalBytesWritten() {
        return totalBytesWritten;
    }

    public int getTotalMainFileFragments() {
        return totalMainFileFragments;
    }
}