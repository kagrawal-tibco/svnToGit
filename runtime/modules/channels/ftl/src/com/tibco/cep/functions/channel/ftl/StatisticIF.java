package com.tibco.cep.functions.channel.ftl;

import java.io.PrintStream;

interface StatisticIF {

    public abstract void count(long when, long value);

    public abstract void count(String threadName, long when, long value, PrintStream printStream);

    public abstract void printResult(String threadName, PrintStream ps);

    public abstract void reset(long timestamp);

    public abstract void printHeader(PrintStream out);

}
