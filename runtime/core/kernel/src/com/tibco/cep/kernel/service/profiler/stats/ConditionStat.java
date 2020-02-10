package com.tibco.cep.kernel.service.profiler.stats;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.tibco.cep.kernel.core.rete.FilterNode;
import com.tibco.cep.kernel.core.rete.Node;
import com.tibco.cep.kernel.model.rule.Condition;

/**
 * Created by IntelliJ IDEA.
 * User: kpang
 * Date: Mar 25, 2008
 * Time: 4:47:51 PM
 * To change this template use File | Settings | File Templates.
 */
public class ConditionStat extends Stats {
    public Condition cond;
    protected String ruleDesc;
    protected Date timeStamp;

    //variables for statistic info
    int timeEval   = 0;
    int timeSuccess = 0;
    int maxTime    = 0;
    int minTime    = Integer.MAX_VALUE;
    long totalTime = 0L;

    //variables for calculation
    long stime;

    public ConditionStat(Node node) {
        this.ruleDesc = node.getRule().getDescription().replace('.', '/');
        this.cond = ((FilterNode)node).getCondition();
        this.timeStamp = new Date();
    }

    protected ConditionStat() {};

    public int hashCode() {
        int hc = cond.hashCode();
        return cond.hashCode();
    }

    public boolean equals(Object obj) {
        if(obj != null && obj instanceof ConditionStat) {
            return this.cond.equals(((ConditionStat)obj).cond);
        }
        return false;
    }

    public void write(StringBuilder sb, String delim){

        int mint =  (minTime == Integer.MAX_VALUE? 0 : minTime);
        double avgTime = (timeEval != 0) ? ((double)totalTime / (double)timeEval) : 0.0;

        String strCond = "Internal_Filter_Condition";
        if (cond != null) {
            strCond = cond.toString();
            if (strCond.indexOf(';') != -1) {
                strCond = strCond.substring(0, strCond.indexOf(';'));
            } else if (strCond.indexOf("...") != -1) {
                strCond = strCond.substring(0, strCond.indexOf("...")+3);
            } else if (strCond.startsWith(CODEGEN_PREFIX + RULE_SEPARATOR_CHAR)) {
                strCond = "Internal_Statemachine_Transition_Rule_Filter_Condition";
            }
        }

        sb.append(BRK);
        sb.append("CONDITION-Filter").append(delim).append(new StringBuffer(new SimpleDateFormat(TIMESTAMP_FORMAT).format(timeStamp)));
        sb.append(delim).append(ruleDesc).append(delim).append(strCond).append(delim).append(timeEval);
        sb.append(delim).append(timeSuccess).append(delim).append(totalTime).append(delim);
        sb.append(decimalFormat.format(avgTime)).append(delim).append(maxTime).append(delim).append(mint);
    }

    public void reset() {
        timeEval   = 0;
        timeSuccess = 0;
        maxTime    = 0;
        minTime    = Integer.MAX_VALUE;
        totalTime = 0L;
    }

    public void start() {
        timeEval++;
        stime = System.currentTimeMillis();
        //System.out.println("Condition started.");
    }

    public void end(boolean pass) {
        if (pass)
            timeSuccess++;
        long time = System.currentTimeMillis();
        int diff = (int) (time - stime);
        //System.out.println("Condition ended.");
        totalTime += diff;
        if(diff > maxTime)
            maxTime = diff;
        if(diff < minTime)
            minTime = diff;
    }
}
