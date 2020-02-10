package com.tibco.cep.kernel.service.profiler.stats;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.tibco.cep.kernel.core.rete.JoinNode;
import com.tibco.cep.kernel.core.rete.Node;

/**
 * Created by IntelliJ IDEA.
 * User: kpang
 * Date: Mar 25, 2008
 * Time: 4:52:24 PM
 * To change this template use File | Settings | File Templates.
 */
public class JoinConditionStat extends ConditionStat {

    int  maxLeftSearch      = 0;
    int  minLeftSearch      = Integer.MAX_VALUE;
    int successLeftSearch   = 0;
    int totalLeftSearch    = 0;

    int  maxRightSearch       = 0;
    int  minRightSearch       = Integer.MAX_VALUE;
    int  successRightSearch   = 0;
    int totalRightSearch     = 0;

    boolean leftSearch = false;
    
    public JoinConditionStat(Node node) {
        this.ruleDesc = node.getRule().getDescription().replace('.', '/');
        this.cond = ((JoinNode)node).getCondition();
        this.timeStamp = new Date();
    }
/*
    public void writeHeader(ProfileWriter writer) throws IOException {
        StringBuffer sb = new StringBuffer();
        sb.append(BRK + BRK + "Stats Type, Rule, Condition, NumEvaluated, NumSuccess, TotalTime, AvgTime, MaxTime, MinTime, ");
        sb.append("NumSuccessLeftSearch, AvgSuccessLeftSearch, MaxLeftSuccessSearch, MinLeftSuccessSearch, ");
        sb.append("NumSuccessRightSearch, AvgSuccessRightSearch, MaxRightSuccessSearch, MinRightSuccessSearch");
        writer.writeln(sb.toString());
    }
*/
    public void write(StringBuilder sb, String delim){
        int mint =  (minTime == Integer.MAX_VALUE? 0 : minTime);
        int totalSearch = totalLeftSearch + totalRightSearch;
        //int avgTime = (totalSearch != 0) ? ( (int) (totalTime / totalSearch) ) : 0;
        double avgTime = (totalSearch != 0) ? ((double)totalTime / (double)totalSearch) : 0.0;
        int numSuccess = successLeftSearch + successRightSearch;
        //int avgLeftSearch = (totalLeftSearch != 0) ? ((int) (successLeftSearch/totalLeftSearch)) : 0;
        //int avgRightSearch = (totalRightSearch !=0) ? ((int) (successRightSearch/totalRightSearch)) : 0;
        double avgLeftSearch = (totalLeftSearch != 0) ? ((double)successLeftSearch / (double)totalLeftSearch) : 0.0;
        double avgRightSearch = (totalRightSearch !=0) ? ((double)successRightSearch / (double)totalRightSearch) : 0.0;
        int minLSearch = (minLeftSearch  == Integer.MAX_VALUE ? 0 : minLeftSearch);
        int minRSearch = (minRightSearch  == Integer.MAX_VALUE ? 0 : minRightSearch);

        String strCond = "Internal_Join_Condition";
        if (cond != null) {
            strCond = cond.toString();
            if (strCond.indexOf(';') != -1) {
                strCond = strCond.substring(0, strCond.indexOf(';'));
            } else if (strCond.indexOf("...") != -1) {
                strCond = strCond.substring(0, strCond.indexOf("...")+3);
            } else if (strCond.startsWith(CODEGEN_PREFIX + RULE_SEPARATOR_CHAR)) {
                strCond = "Internal_Statemachine_Transition_Rule_Join_Condition";
            }
        }

        sb.append(BRK );
        sb.append("CONDITION-Join").append(delim).append(new StringBuffer(new SimpleDateFormat("yyyy/MM/dd HH:mm:ss.SSS").format(timeStamp)));
        sb.append(delim).append(ruleDesc).append(delim).append(strCond).append(delim).append(totalSearch).append(delim).append(numSuccess);
        sb.append(delim).append(totalTime).append(delim).append(decimalFormat.format(avgTime)).append(delim).append(maxTime);
        sb.append(delim).append(mint).append(delim).append(totalLeftSearch).append(delim).append(successLeftSearch).append(delim).append(decimalFormat.format(avgLeftSearch));
        sb.append(delim).append(maxLeftSearch).append(delim).append(minLSearch).append(delim).append(totalRightSearch).append(delim).append(successRightSearch);
        sb.append(delim).append(decimalFormat.format(avgRightSearch)).append(delim).append(maxRightSearch).append(delim).append(minRSearch);
//        sb.append(delim).append(timeEval).append(delim).append(timeSuccess);
    }

    public void startSearch(boolean leftSearch) {
        start();
        this.leftSearch = leftSearch;
    }

    public void endSearch(int numS, int numF) {
        if (numS > 0) {
            super.end(true);
        } else {
            end(false);
        }

        if (leftSearch) {
            totalLeftSearch += (numS + numF);
            successLeftSearch += numS;
            if(numS > maxLeftSearch)
                maxLeftSearch = numS;
            if(numS < minLeftSearch)
                minLeftSearch = numS;
        }
        else {
            totalRightSearch += (numS + numF);
            successRightSearch += numS;
            if(numS > maxRightSearch)
                maxRightSearch = numS;
            if(numS < minRightSearch)
                minRightSearch = numS;
        }
    }

    public void reset() {
        super.reset();
        maxLeftSearch   = 0;
        minLeftSearch   = Integer.MAX_VALUE;
        successLeftSearch   = 0;
        totalLeftSearch = 0;

        maxRightSearch   = 0;
        minRightSearch   = Integer.MAX_VALUE;
        successRightSearch   = 0;
        totalRightSearch = 0;

        leftSearch = false;
    }

}
