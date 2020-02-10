package com.tibco.cep.kernel.service.profiler.stats;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.tibco.cep.kernel.core.rete.ReteListener;


/**
 * Created by IntelliJ IDEA.
 * User: kpang
 * Date: Mar 19, 2008
 * Time: 8:24:11 PM
 * To change this template use File | Settings | File Templates.
 */
public class ActionStat extends Stats {
    int type;
    String strType;
    String description;
    Date timeStamp;

    //variables for statistic info
    int  numExecuted = 0;
    int  maxExeTime = 0;
    int  minExeTime = Integer.MAX_VALUE;
    long totalExeTime = 0L;

    int  maxOpTime = 0;
    int  minOpTime = Integer.MAX_VALUE;
    long totalOpTime = 0L;

    int  maxActTime = 0;
    int  minActTime = Integer.MAX_VALUE;
    long totalActTime = 0L;

    int maxAgendaSize = 0;
    int minAgendaSize = Integer.MAX_VALUE;

    //variables for calculation
    long stime;
    long exeTime;
    
    public ActionStat(int type, String description) {
        this.type = type;
        this.strType = ReteListener.actionTypes[type];
        this.description = description;
        this.timeStamp = new Date();
    }

    public int hashCode() {
        return description.hashCode();
    }

    public boolean equals(Object obj) {
        if(obj instanceof ActionStat) {
            return ((this.type == ((ActionStat)obj).type) &&
                    (this.description.equals(((ActionStat)obj).description)));
        }
        return false;
    }

    public void write(StringBuilder sb, String delim){

        double avgExeTime = (numExecuted != 0) ? ((double)totalExeTime / (double)numExecuted) : 0.00;
        int minASize = (minAgendaSize == Integer.MAX_VALUE? 0 : minAgendaSize);
        int minExtT     =   (minExeTime == Integer.MAX_VALUE? 0 : minExeTime);

        double avgActTime = (numExecuted != 0) ? ((double)totalActTime / (double)numExecuted) : 0.00;
        int minActT    = (minActTime == Integer.MAX_VALUE? 0 : minActTime);

        double avgOpTime = (numExecuted != 0) ? ((double)totalOpTime / (double)numExecuted) : 0.00;
        int minOpT    = (minOpTime == Integer.MAX_VALUE? 0 : minOpTime);

        String desc = description;
        if (description.equals(ACTIVATE_ACTION_CLASS_NAME) || description.equals(STATETIMEOUT_EVENT_CLASS_NAME)) {
            desc = description.substring(description.lastIndexOf('$') + 1);
        } else if (description.endsWith(STARTUP_ACTION_CLASS_NAME_ENDING)) {
            desc = "StartupAction";
        } else if (description.endsWith(SHUTDOWN_ACTION_CLASS_NAME_ENDING)) {
            desc = "ShutdownAction";
        } else if(description.startsWith(CODEGEN_PREFIX + RULE_SEPARATOR_CHAR)) {
            desc = description.substring((CODEGEN_PREFIX + RULE_SEPARATOR_CHAR).length());
            desc = desc.replace('.', '/');
        }

        sb.append(BRK);
        sb.append(strType).append(delim).append(new StringBuffer(new SimpleDateFormat(TIMESTAMP_FORMAT).format(timeStamp)));
        sb.append(delim).append(desc).append(delim).append(delim).append(numExecuted).append(delim).append(delim).append(totalActTime).append(delim).append(decimalFormat.format(avgActTime));
        sb.append(delim).append(maxActTime).append(delim).append(minActT);
        sb.append(delim).append(totalExeTime).append(delim).append(decimalFormat.format(avgExeTime)).append(delim).append(maxExeTime).append(delim).append(minExtT);
        sb.append(delim).append(totalOpTime).append(delim).append(decimalFormat.format(avgOpTime)).append(delim).append(maxOpTime).append(delim).append(minOpT);
        sb.append(delim).append(maxAgendaSize).append(delim).append(minASize);

    }                                                                                                       

    public void start() {
        numExecuted++;
        stime = System.currentTimeMillis();
        //System.out.println(strType + " started.");
    }

    public void executed() {
        exeTime = System.currentTimeMillis();
        int diff = (int) (exeTime - stime);
        //System.out.println(strType + " executed.");
        if(diff > maxExeTime)
            maxExeTime = diff;
        if(diff < minExeTime) {
            minExeTime = diff;
        }
        totalExeTime += diff;
    }

    public void endOps() {
        long actTime = System.currentTimeMillis();
        //System.out.println(strType + " ended.");
        int opTime = (int) (actTime - exeTime);
        if(opTime > maxOpTime) {
            maxOpTime = opTime;
        }
        if(opTime < minOpTime) {
            minOpTime = opTime;
        }
        int diff = (int) (actTime - stime);
        if(diff > maxActTime)
            maxActTime = diff;
        if(diff < minActTime) {
            minActTime = diff;
        }
        totalOpTime += opTime;
        totalActTime += diff;
    }

    public void recordAgendaSize(int agendaSize) {
        if(agendaSize > maxAgendaSize)
            maxAgendaSize = agendaSize;
        if(agendaSize < minAgendaSize)
            minAgendaSize = agendaSize;
    }

    public void reset() {
        numExecuted = 0;
        maxExeTime = 0;
        minExeTime = Integer.MAX_VALUE;
        totalExeTime = 0L;
        maxOpTime = 0;
        minOpTime = Integer.MAX_VALUE;
        totalOpTime = 0L;
        maxAgendaSize = 0;
        minAgendaSize = Integer.MAX_VALUE;
        maxActTime = 0;
        minActTime = Integer.MAX_VALUE;
        totalActTime = 0L;
    }
}
