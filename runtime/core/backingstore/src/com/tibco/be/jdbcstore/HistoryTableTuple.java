package com.tibco.be.jdbcstore;

public class HistoryTableTuple {
    public int howMany;
    public HistoryTuple[] historyTable;

    public HistoryTableTuple(int howMany, HistoryTuple[] historyTable) {
        this.howMany = howMany;
        this.historyTable = historyTable;
    }
}
