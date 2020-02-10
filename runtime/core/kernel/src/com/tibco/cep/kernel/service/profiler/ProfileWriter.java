package com.tibco.cep.kernel.service.profiler;

import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * User: kpang
 * Date: May 12, 2008
 * Time: 5:50:44 PM
 * To change this template use File | Settings | File Templates.
 */
public abstract class ProfileWriter {

    protected static final String FILE_SEPARATOR = System.getProperty("file.separator");
    public static final String BRK = System.getProperty("line.separator");
    int level=-1;
    protected String delim;

    public ProfileWriter() {
    }
    
    public ProfileWriter(int level, String delim) {
        this.level = level;
        this.delim = delim;
    }

    public String printHeaders() {
        StringBuilder headers = new StringBuilder();
        headers.append("RTC_Stats_Type").append(delim).append("Timestamp").append(delim).append("Description").append(delim).append(delim).append("NumExecuted").append(delim).append(delim);
        headers.append("TotalRtcTime").append(delim).append("AvgRtcTime").append(delim).append("MaxRtcTime").append(delim).append("MinRtcTime").append(delim).append("MaxResolvedTime").append(delim).append("MinResolvedTime");
        headers.append(BRK);
        if (level == -1) {
            headers.append("CONDITION_Stats_Type").append(delim).append("Timestamp").append(delim).append("RuleDescription").append(delim).append("ConditionDescription").append(delim);
            headers.append("NumEvaluated").append(delim).append("NumEvalTrue").append(delim).append("TotalTime").append(delim).append("AvgTime").append(delim).append("MaxTime").append(delim).append("MinTime").append(delim);
            headers.append("NumEvalPropagatedLeft").append(delim).append("NumEvalTruePropagatedLeft").append(delim).append("AvgRateEvalTruePropagatedLeft").append(delim).append("MaxNumEvalTruePropagatedLeft").append(delim).append("MinNumEvalTruePropagatedLeft").append(delim);
            headers.append("NumEvalPropagatedRight").append(delim).append("NumEvalTruePropagatedRight").append(delim).append("AvgRateEvalTruePropagatedRight").append(delim).append("MaxNumEvalTruePropagatedRight").append(delim).append("MinNumEvalTruePropagatedRight");
            headers.append(BRK);
            headers.append("ACTION_Stats_Type").append(delim).append("Timestamp").append(delim).append("Description").append(delim).append(delim).append("NumExecuted").append(delim).append(delim);
            headers.append("TotalActionTime").append(delim).append("AvgActionTime").append(delim).append("MaxActionTime").append(delim).append("MinActionTime").append(delim);
            headers.append("TotalExecutionTime").append(delim).append("AvgExecutionTime").append(delim).append("MaxExecutionTime").append(delim).append("MinExecutionTime").append(delim);
            headers.append("TotalOperationTime").append(delim).append("AvgOperationTime").append(delim).append("MaxOperationTime").append(delim).append("MinOperationTime").append(delim);
            headers.append("MaxAgenda").append(delim).append("MinAgenda");

            //headers.append(BRK);
        }
        return headers.toString();
    }

    public abstract void write(String out) throws IOException;

    public void close() throws IOException {}
    
    public int getLevel() {
        return this.level;
    }

    public void setLevel(int level) {
        this.level=level;

    }
}
