package com.tibco.cep.kernel.core.rete;


/**
 * Created by IntelliJ IDEA.
 * User: nleong
 * Date: Feb 29, 2008
 * Time: 4:26:32 PM
 * To change this template use File | Settings | File Templates.
 */
public interface ReteListener {

    static final public int RTC_OBJECT_ASSERTED    = 0;
    static final public int RTC_OBJECT_MODIFIED    = 1;
    static final public int RTC_OBJECT_DELETED     = 2;
    static final public int RTC_EVENT_EXPIRED      = 3;
    static final public int RTC_EXECUTE_RULE       = 4;
    static final public int RTC_INVOKE_ACTION      = 5;
    static final public int RTC_INVOKE_FUNCTION    = 6;
    static final public int RTC_POST_PROCESS       = 7;
    static final public int RTC_REPEAT_TIMEEVENT   = 8;
    static final public int RTC_REEVALUATE_ELEMENT = 9;

    static final public int ACTION_RULE_ACTION     = 0;
    static final public int ACTION_EVENT_EXPIRY    = 1;
    static final public int ACTION_INVOKE_ACTION   = 2;
    static final public int ACTION_INVOKE_FUNCTION = 3;

    static final public String[] rtcTypes=new String[]{
            "RTC-Object-Asserted",
            "RTC-Object-Modified",
            "RTC-Object-Deleted",
            "RTC-Event-Expired",
            "RTC-Execute-Rule",
            "RTC-Invoke-Action",
            "RTC-Invoke-Function",
            "RTC-Post-Process",
            "RTC-Repeat-TimeEvent",
            "RTC-Reevaluate-Element"
    };

    static final public String[] actionTypes = new String[] {
            "ACTION-Rule-Action",
            "ACTION-Event-Expiry",
            "ACTION-Invoke-Action",
            "ACTION-Invoke-Function"
    };

    void on();

    void off();

    boolean isOn();
    
    void rtcStart(int rtcType, Object context);

    void rtcResolved();

    void rtcEnd();

    void actionStart(int actionType, Object context);

    void actionExecuted();

    void actionEnd(int agendaSize);

    void filterConditionStart(FilterNode filerNode);

    void filterConditionEnd(boolean success);

    void joinConditionStart(JoinNode joinNode, boolean leftSearch);

    void joinConditionEnd(int numSuccess, int numFailed);

}
