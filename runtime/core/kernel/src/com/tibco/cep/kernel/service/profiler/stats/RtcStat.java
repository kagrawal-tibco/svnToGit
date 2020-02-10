package com.tibco.cep.kernel.service.profiler.stats;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;


/**
 * Created by IntelliJ IDEA.
 * User: kpang
 * Date: Mar 19, 2008
 * Time: 8:24:11 PM
 * To change this template use File | Settings | File Templates.
 */
public class RtcStat extends Stats {
    int type;
    String strType;
    String description;
    Date timeStamp;
    
    public HashMap actionStatMap = new HashMap();
    public HashMap conditionStatMap = new HashMap();
    public ArrayList actionStatList = new ArrayList();
    public ArrayList conditionStatList = new ArrayList();
    
    //variables for statistic info
    long maxTime = 0L;
    long minTime = Long.MAX_VALUE;
    long maxResolvedTime = 0L;
    long minResolvedTime = Long.MAX_VALUE;
    int numExecuted;
    long totalTime = 0L;
    Thread currentThread;

    //variable for calculation
    long startTime;

    public RtcStat(int type, String strType, String description, Thread currentThread) {
        this.type = type;
        this.strType = strType;
        this.description = description;
        this.timeStamp = new Date();
        this.currentThread=currentThread;
    }

    public int hashCode() {
        return description.hashCode();
    }

    public boolean equals(Object obj) {
        if(obj instanceof RtcStat) {
//            return ((this.currentThread== ((RtcStat) obj).currentThread) && (this.type == ((RtcStat)obj).type) &&
//                    (this.description.equals(((RtcStat)obj).description)));
            return ( (this.type == ((RtcStat)obj).type) && (this.description.equals(((RtcStat)obj).description)));
        }
        return false;
    }

    public void write(StringBuilder sb, String delim){

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

        long minT =  (minTime == Long.MAX_VALUE ? 0 : minTime);
        long minRT =  (minResolvedTime == Long.MAX_VALUE ? 0 : minResolvedTime);
        double avgTime = (numExecuted != 0) ? ((double)totalTime) / ((double)numExecuted) : 0.00;
        sb.append(strType).append(delim).append(new StringBuffer(new SimpleDateFormat(TIMESTAMP_FORMAT).format(timeStamp))).append(delim);
        sb.append(desc).append(delim).append(delim).append(numExecuted).append(delim).append(delim).append(totalTime).append(delim).append(decimalFormat.format(avgTime)).append(delim);
        sb.append(maxTime).append(delim).append(minT).append(delim).append(maxResolvedTime).append(delim).append(minRT);

        ConditionStat conditionStat = null;
        Object obj;
        for (int i = 0; i < conditionStatList.size(); i++) {
            obj = conditionStatList.get(i);
            if (obj instanceof JoinConditionStat)
                conditionStat = (JoinConditionStat)obj;
            else if (obj instanceof ConditionStat)
                conditionStat = (ConditionStat)obj;

            conditionStat.write(sb, delim);
        }

        ActionStat actionStat;
        for (int i = 0; i < actionStatList.size(); i++) {
            actionStat = (ActionStat)actionStatList.get(i);
            actionStat.write(sb, delim);
        }
    }

    public void start() {
    numExecuted++;
    startTime = System.currentTimeMillis();
    //System.out.println(strType + " started.");
    }

    public void resolved() {
        long endTime = System.currentTimeMillis();
        int diff = (int) (endTime - startTime);
        //System.out.println(strType + " resolved.");
        if(diff > maxResolvedTime)
            maxResolvedTime = diff;
        if(diff < minResolvedTime) {
            minResolvedTime = diff;
        }
    }

    public void end() {
        long endTime = System.currentTimeMillis();
        long diff = endTime - startTime;
        //System.out.println(strType + " ended.");
        if(diff > maxTime)
            maxTime = diff;
        if(diff < minTime)
            minTime = diff;
        totalTime += diff;
    }

    public void reset() {
        maxTime = 0L;
        minTime = Long.MAX_VALUE;
        maxResolvedTime = 0L;
        minResolvedTime = Long.MAX_VALUE;
        totalTime = 0L;
        numExecuted = 0;

        ConditionStat conditionStat = null;
        Object obj;
        for (Iterator it = conditionStatMap.values().iterator(); it.hasNext();) {
            obj = it.next();
            if (obj instanceof JoinConditionStat)
                conditionStat = (JoinConditionStat)obj;
            else if (obj instanceof ConditionStat)
                conditionStat = (ConditionStat)obj;

            conditionStat.reset();
        }

        ActionStat actionStat;
        for (Iterator it = actionStatMap.values().iterator(); it.hasNext();) {
            actionStat = (ActionStat)it.next();
            actionStat.reset();
        }

        conditionStatMap.clear();
        actionStatMap.clear();
        conditionStatList.clear();
        actionStatList.clear();
    }
}
