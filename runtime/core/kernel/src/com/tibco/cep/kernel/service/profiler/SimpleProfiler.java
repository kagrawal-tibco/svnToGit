package com.tibco.cep.kernel.service.profiler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.tibco.cep.kernel.core.rete.FilterNode;
import com.tibco.cep.kernel.core.rete.JoinNode;
import com.tibco.cep.kernel.core.rete.ReteListener;
import com.tibco.cep.kernel.model.rule.RuleFunction;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.kernel.service.profiler.stats.ActionStat;
import com.tibco.cep.kernel.service.profiler.stats.ConditionStat;
import com.tibco.cep.kernel.service.profiler.stats.JoinConditionStat;
import com.tibco.cep.kernel.service.profiler.stats.RtcStat;
import com.tibco.cep.kernel.service.profiler.stats.Stats;

/**
 * Created by IntelliJ IDEA.
 * User: nleong
 * Date: Mar 10, 2008
 * Time: 8:30:10 PM
 * To change this template use File | Settings | File Templates.
 */
public class SimpleProfiler implements ReteListener {

    static protected ThreadLocal rtc_stack = new ThreadLocal();
    String fileName;
    int level;
    long duration;
    boolean isTimered;
    long startTime;
    long interval;
    boolean on;
    boolean turnedOn;
    Map rtcStatMap;
    List rtcStatList;
    ProfileWriter pWriter;
    Logger logger;
    String delimiter = Stats.DELIMITER;;

    public SimpleProfiler(String fileName, int level, long duration, long interval, String delimiter, Logger logger) {
        if (level < 1)
            this.level = -1;
        else if (level > 1)
            this.level = 1;
        else
            this.level = level;

        this.fileName = fileName;
        isTimered = duration > 0 ? true : false;
        this.duration = duration > 0 ? duration * 1000 : -1; // millisecond
        this.interval = interval;
        startTime = 0L;
        this.logger = logger;
        if (delimiter != null) {
            delimiter = delimiter.trim();
            final int length = delimiter.length();
            if (length > 2 && delimiter.charAt(0) == '"' && delimiter.charAt(length - 1) == '"') {
                String delim = delimiter.substring(1, length - 1);
                this.delimiter = delim;
            } else {
                logger.log(Level.WARN,"Profiler: Invalid delimiter configured. Output will be tab delimited.");
            }
        }

        on = false;
        turnedOn = false;
        rtcStatMap = new ConcurrentHashMap<RtcStat, RtcStat>();
        rtcStatList = Collections.synchronizedList(new ArrayList<RtcStat>());

        try {
            pWriter = new CSVWriter(this.level, this.delimiter, this.fileName);
            ((CSVWriter)pWriter).write(pWriter.printHeaders());
        }
        catch (IOException ie) {
            logger.log(Level.ERROR,ie.getMessage(), ie);
        }
    }

    public void on() {
        //TODO: need to reset all stats first
        turnedOn = true;
    }

    synchronized public void off() {
        if (on) {
            on = false;
            turnedOn = false;
            try {
                write();
                pWriter.close();
            }
            catch (IOException e) {
                logger.log(Level.ERROR,e.getMessage(), e);
            }
            this.reset();
            logger.log(Level.INFO,"End profiling.");
        }
        else {
        	on = false;
        	turnedOn = false;
        	logger.log(Level.INFO,"Profiler stopped.");
    	}
    }


    public boolean isOn() {
        return on;
    }

    public void rtcStart(int rtcType, Object context) {

        if(!on) {
            if (turnedOn) {
                on = true;
                startTime = System.currentTimeMillis();
                logger.log(Level.INFO,"Start profiling.");
            } else {
                return;
            }
        }
        else if (isTimered && ((System.currentTimeMillis() - startTime) >= duration)) {
            this.off();
            return;
        }

//        logger.log(Level.DEBUG,"############################### RtcType=" + rtcTypes[rtcType]);

        String desc = null;
        switch(rtcType) {
            case RTC_OBJECT_ASSERTED:
            case RTC_OBJECT_MODIFIED:
            case RTC_OBJECT_DELETED:
            case RTC_EVENT_EXPIRED:
            case RTC_REPEAT_TIMEEVENT:
                desc = context.getClass().getName();  //name of the affected object class
                break;
            case RTC_EXECUTE_RULE:
                desc = "API: ReteWM.executeRules()";
                break;
            case RTC_INVOKE_ACTION :
                desc = context.getClass().getName();  //this should be the action class name
                break;
            case RTC_INVOKE_FUNCTION:
                desc = ((RuleFunction)context).getSignature();
                break;
            case RTC_POST_PROCESS:
                desc = (String) context;
                break;
            case RTC_REEVALUATE_ELEMENT:
                desc = "API: ReteWM.reevaluateElements(ids)";
                break;
        }
        RtcStat dummy = new RtcStat(rtcType, rtcTypes[rtcType], desc, Thread.currentThread());
        RtcStat rtcStat = (RtcStat) rtcStatMap.get(dummy);
        if(rtcStat == null) {
            rtcStat = dummy;
            rtcStatMap.put(dummy, rtcStat);
            rtcStatList.add(rtcStat);
        }
        rtcStat.start();
        LinkedList stack = (LinkedList) rtc_stack.get();
        if(stack == null) {
            stack = new LinkedList();
            rtc_stack.set(stack);
        }
        stack.addLast(rtcStat);
    }

    public void rtcResolved() {

        if(!on) return;

//        logger.log(Level.DEBUG,"^^^^^^^^^^^^^^^^^^^^^^^^^^^^Inside rtcResolved");

        LinkedList stack = (LinkedList) rtc_stack.get();
        if(stack == null) return;  //rtc hasn't been started
        Object lastObject = stack.getLast();
        if(lastObject == null || !(lastObject instanceof RtcStat)) {
            //something wrong, should throws exception!!!
            logger.log(Level.ERROR,"!!!ERROR!!! lastObject in the stack is " + (lastObject == null ? "null" : lastObject.getClass().getName()));
            return;
        }
        ((RtcStat)lastObject).resolved();
    }

    public void rtcEnd() {

        if(!on) return;

//        logger.log(Level.DEBUG,"+++++++++++++++++++++++++ Inside rtcEnd");

        LinkedList stack = (LinkedList) rtc_stack.get();
        if(stack == null) return;  //rtc hasn't been started
        Object lastObject = stack.getLast();
        if(lastObject == null || !(lastObject instanceof RtcStat)) {
            //something wrong, should throws exception!!!
            logger.log(Level.ERROR,"!!!ERROR!!! lastObject in the stack is " + (lastObject == null ? "null" : lastObject.getClass().getName()));
            return;
        }
        RtcStat rtcStat = (RtcStat) stack.removeLast();
        rtcStat.end();

        rtc_stack.set(null);
    }


    public void actionStart(int actionType, Object context) {

        if(!on || level != -1) return; //Profiler hasn't been turned on

//        logger.log(Level.DEBUG,"@@@@@@@@@@@@@@@@@@@@@@@@@@ActionType=" + actionTypes[actionType]);

        LinkedList stack = (LinkedList) rtc_stack.get();
        if(stack == null) return;  //rtc hasn't been started
        Object lastObject = stack.getLast();
        if(lastObject == null || !(lastObject instanceof RtcStat)) {
            //something wrong, should throws exception!!!
            logger.log(Level.ERROR,"!!!ERROR!!! lastObject in the stack is " + (lastObject == null ? "null" : lastObject.getClass().getName()));
            return;
        }

        String desc = null;
        switch(actionType) {
            case ACTION_RULE_ACTION:
                desc = context.getClass().getName();
                break;
            case ACTION_EVENT_EXPIRY:
                desc = context.getClass().getName();
                break;
            case ACTION_INVOKE_ACTION:
                desc = context.getClass().getName();
                break;
            case ACTION_INVOKE_FUNCTION:
                desc = ((RuleFunction)context).getSignature();
                break;
        }
        ActionStat dummy = new ActionStat(actionType, desc);
        HashMap actionStatMap = ((RtcStat)lastObject).actionStatMap;
        ActionStat actionStat = (ActionStat) actionStatMap.get(dummy);
        if(actionStat == null) {
            actionStat = dummy;
            actionStatMap.put(dummy, actionStat);
            ((RtcStat)lastObject).actionStatList.add(actionStat);
        }
        actionStat.start();
        stack.addLast(actionStat);
    }

    public void actionExecuted() {

        if(!on || level != -1) return; //Profiler hasn't been turned on

//        logger.log(Level.DEBUG,"========================= Inside actionExecuted");

        LinkedList stack = (LinkedList) rtc_stack.get();
        if(stack == null) return;  //rtc hasn't been started
        Object lastObject = stack.getLast();
        if(lastObject == null || !(lastObject instanceof ActionStat)) {
            //something wrong, should throws exception!!!
            logger.log(Level.ERROR,"!!!ERROR!!! lastObject in the stack is " + (lastObject == null ? "null" : lastObject.getClass().getName()));
            return;
        }

        ((ActionStat)lastObject).executed();
    }

    public void actionEnd(int agendaSize) {

        if(!on || level != -1) return; //Profiler hasn't been turned on

//        logger.log(Level.DEBUG,">>>>>>>>>>>>>>>>>>>>>>>>>>>> Inside actionEnd. agendaSize=" + agendaSize);

        LinkedList stack = (LinkedList) rtc_stack.get();
        if(stack == null) return;  //rtc hasn't been started
        Object lastObject = stack.getLast();
        if(lastObject == null || !(lastObject instanceof ActionStat)) {
            //something wrong, should throws exception!!!
            logger.log(Level.ERROR,"!!!ERROR!!! lastObject in the stack is " + (lastObject == null ? "null" : lastObject.getClass().getName()));
            return;
        }
        ActionStat actionStat = (ActionStat)stack.removeLast();
        actionStat.recordAgendaSize(agendaSize);
        actionStat.endOps();
    }

    public void filterConditionStart(FilterNode filterNode) {

        if(!on || level != -1) return; //Profiler hasn't been turned on

//        logger.log(Level.DEBUG,"************************ FilterNode = " + filterNode.toString());

        LinkedList stack = (LinkedList) rtc_stack.get();
        if(stack == null) return;  //rtc hasn't been started
        Object firstObject = stack.getFirst();  // TODO: what should be the last Stat object in the stack?
        if(firstObject == null || !(firstObject instanceof RtcStat)) { // TODO: what instance the lastObject should be? tests show conditions can be after several actions and can be after directly the rtcStart.
            //something wrong, should throws exception!!!
            logger.log(Level.ERROR,"!!!ERROR!!! firstObject in the stack is " + (firstObject == null ? "null" : firstObject.getClass().getName()));
            return;
        }

        HashMap conditionStatMap = ((RtcStat)firstObject).conditionStatMap;
        ConditionStat conditionStat = (ConditionStat) conditionStatMap.get(filterNode);
        if(conditionStat == null) {
            conditionStat = new ConditionStat(filterNode);
            conditionStatMap.put(filterNode, conditionStat);
            ((RtcStat)firstObject).conditionStatList.add(conditionStat);
        }
        conditionStat.start();
        stack.addLast(conditionStat);
    }

    public void filterConditionEnd(boolean success) {

        if(!on || level != -1) return; //Profiler hasn't been turned on

//        logger.log(Level.DEBUG,"************************ success = " + success);

        LinkedList stack = (LinkedList) rtc_stack.get();
        if(stack == null) return;  //rtc hasn't been started
        Object lastObject = stack.getLast();
        if(lastObject == null || !(lastObject instanceof ConditionStat)) {
            //something wrong, should throws exception!!!
            logger.log(Level.ERROR,"!!!ERROR!!! lastObject in the stack is " + (lastObject == null ? "null" : lastObject.getClass().getName()));
            return;
        }

        ConditionStat conditionStat = (ConditionStat)stack.removeLast();
        conditionStat.end(success);
    }

    public void joinConditionStart(JoinNode joinNode, boolean leftSearch) {

        if(!on || level != -1) return; //Profiler hasn't been turned on

//        logger.log(Level.DEBUG,"&&&&&&&&&&&&&&&&&&&&&&&&&& JoinNode = " + joinNode.toString());
//        logger.log(Level.DEBUG,"&&&&&&&&&&&&&&&&&&&&&&&&&& leftSerch = " + leftSearch);

        LinkedList stack = (LinkedList) rtc_stack.get();
        if(stack == null) return;  //rtc hasn't been started
        Object firstObject = stack.getFirst();  // TODO: what should be the last Stat object in the stack?
        if(firstObject == null || !(firstObject instanceof RtcStat)) { // TODO: what instance the lastObject should be? tests show conditions can be after several actions and can be after directly the rtcStart.
            //something wrong, should throws exception!!!
            logger.log(Level.ERROR,"!!!ERROR!!! firstObject in the stack is " + (firstObject == null ? "null" : firstObject.getClass().getName()));
            return;
        }

        HashMap conditionStatMap = ((RtcStat)firstObject).conditionStatMap;
        JoinConditionStat conditionStat = (JoinConditionStat) conditionStatMap.get(joinNode);
        if(conditionStat == null) {
            conditionStat = new JoinConditionStat(joinNode);
            conditionStatMap.put(joinNode, conditionStat);
            ((RtcStat)firstObject).conditionStatList.add(conditionStat);
        }
        conditionStat.startSearch(leftSearch);
        stack.addLast(conditionStat);
    }

    public void joinConditionEnd(int numSuccess, int numFailed) {

        if(!on || level != -1) return; //Profiler hasn't been turned on

//        logger.log(Level.DEBUG,"%%%%%%%%%%%%%%%%%%%%%%%%%% numSuccess = " + numSuccess + ", numFailed = " + numFailed);

        LinkedList stack = (LinkedList) rtc_stack.get();
        if(stack == null) return;  //rtc hasn't been started
        Object lastObject = stack.getLast();
        if(lastObject == null || !(lastObject instanceof JoinConditionStat)) {
            //something wrong, should throws exception!!!
            logger.log(Level.ERROR,"!!!ERROR!!! lastObject in the stack is " + (lastObject == null ? "null" : lastObject.getClass().getName()));
            return;
        }

        JoinConditionStat conditionStat = (JoinConditionStat)stack.removeLast();
        conditionStat.endSearch(numSuccess, numFailed);
    }

    public void write() {

        logger.log(Level.INFO,"Writing profiling data...");

        StringBuilder sb = new StringBuilder();
        synchronized(rtcStatList) {
            Iterator i = rtcStatList.iterator();
            while (i.hasNext()) {
                RtcStat rtcStat = (RtcStat)i.next();
            sb.append(Stats.BRK);
                rtcStat.write(sb, delimiter);
            sb.append(Stats.BRK);
        }
        if (sb.length() > 0) {
            try {
                pWriter.write(sb.toString());
            }
            catch (IOException e) {
                logger.log(Level.ERROR,e.getMessage(), e);
                }
            }
        }
    }

    public void reset() {
        ArrayList<RtcStat> list = new ArrayList<RtcStat>(rtcStatMap.values());
        for (RtcStat rtcStat : list) {
            rtcStat.reset();
        }
        rtcStatMap.clear();
        rtcStatList.clear();
    }

    public String getFileName() {
        if (pWriter instanceof CSVWriter) {
            return ((CSVWriter)pWriter).getFileName();
        }

        return null;
    }

    public long getDuration() {
        return this.duration;
    }

    public int getLevel() {
        return this.level;
    }
}
