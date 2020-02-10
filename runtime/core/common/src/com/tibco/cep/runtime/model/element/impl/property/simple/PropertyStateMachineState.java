package com.tibco.cep.runtime.model.element.impl.property.simple;

import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.LogManagerFactory;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.runtime.model.element.Property;
import com.tibco.cep.runtime.service.time.BETimeManager;
import com.tibco.cep.runtime.session.RuleSession;
import com.tibco.cep.runtime.session.RuleSessionManager;

/**
 * Created by IntelliJ IDEA.
 * User: apuneet
 * Date: Nov 8, 2006
 * Time: 2:20:23 PM
 * To change this template use File | Settings | File Templates.
 */
public abstract class PropertyStateMachineState extends PropertyAtomIntSimple {
    public final static int STATE_INACTIVE          = 0x000;   //change the constructor if this value change
    public final static int STATE_ACTIVE            = 0x001;
    public final static int STATE_READY             = 0x002;
    public final static int STATE_TIMEOUT           = 0x003;
    public final static int STATE_COMPLETE          = 0x004;
    public final static int STATE_EXIT              = 0x005;

    public final static int STATE_PROBABLY_ACTIVE   = 0x006;
    public final static int STATE_PROBABLY_READY    = 0x007;
    public final static int STATE_PROBABLY_COMPLETE = 0x008;
    public final static int STATE_PROBABLY_EXIT     = 0x009;

    public final static int STATE_READY_TIMEOUT     = 10;
    public final static int STATE_ACTIVE_TIMEOUT  = 11;
    public final static int STATE_PROBABLY_READY_TIMEOUT  = 12;
    public final static int STATE_PROBABLY_ACTIVE_TIMEOUT  = 13;

    public final static Logger gLogger = LogManagerFactory.getLogManager().getLogger(PropertyStateMachineState.class);


    protected PropertyStateMachineState(Object owner) {
        //super(owner, STATE_INACTIVE);
        super(owner);  //nick -  default value is 0 == inactive to save memory
    }

    protected PropertyStateMachineState(Object owner, int defaultValue) {
        super(owner, defaultValue);
    }

//this could still work if .self() reset the state timeout and handled all the composite state cases but did not change the state status for performance reasons
//    public void self(Object [] args) {
//        if (gLogger.isEnabledFor(Level.DEBUG)) {
//            gLogger.log(Level.DEBUG,args[0] + "[State-Self] " + this.getFullName());
//        }
//        onExit(args);
//        onEntry(args);
//    }

    //abstract StateVertex getModel();
    /**
    *
    * @param args
    */
   public  void enter(Object [] args) {
       this._enter(args,true, false);
   }
   
    /**
     *
     * @param args
     */
    public  void enter(Object [] args, boolean executeEntryMethods) {
        this._enter(args, executeEntryMethods, false);
    }
    
    public  void enter(Object [] args, boolean executeEntryMethods, boolean selfTransition) {
        _enter(args, executeEntryMethods, selfTransition);
    }
    
    /**
     *
     * @param args
     * @param executeEntryMethods
     */
    private void _enter(Object [] args, boolean executeEntryMethods, boolean selfTransition) {
        if (gLogger.isEnabledFor(Level.DEBUG))
            gLogger.log(Level.DEBUG,args[0] + "[State-Entry] " + this.getFullName());

        //do this in case there was a pre-existing state timeout lingering around for whatever reason
        //retractStateTimeoutEvent();
        long timeout= getTimeout(args) * getTimeoutMultiplier();
        if (timeout > 0) {
            incrementCount(selfTransition);
            startExpiryTimer(timeout);
        }
        if (executeEntryMethods) {
            if (timeout > 0)
                this.setState(STATE_ACTIVE_TIMEOUT, selfTransition);
            else
                this.setState(STATE_ACTIVE, selfTransition);
            onEntry(args);
            if (timeout > 0)
                this.setState(STATE_READY_TIMEOUT, selfTransition);
            else
                this.setState(STATE_READY, selfTransition);
        } else {
            if (timeout > 0) {
                this.setState(STATE_PROBABLY_ACTIVE_TIMEOUT, selfTransition);
                this.setState(STATE_PROBABLY_READY_TIMEOUT, selfTransition);
            } else {
                this.setState(STATE_PROBABLY_ACTIVE, selfTransition);
                this.setState(STATE_PROBABLY_READY, selfTransition);
            }
        }
    }

    /**
     *
     */
    public  void exit(Object [] args) {
        _exit(args, true, false);
    }

    /**
     *
     * @param args
     * @param executeExitMethods
     */
    public void exit(Object [] args, boolean executeExitMethods) {
        _exit(args, executeExitMethods, false);
    }

    public void exit(Object [] args, boolean executeExitMethods, boolean notifyParent) {
        _exit(args, executeExitMethods, notifyParent);
    }

    public void exit(Object [] args, boolean executeExitMethods, boolean notifyParent, boolean timedOut) {
        _exit(args, executeExitMethods, notifyParent, timedOut, false);
    }

    public void exit(Object [] args, boolean executeExitMethods, boolean notifyParent, boolean timedOut, boolean selfTransition) {
        _exit(args, executeExitMethods, notifyParent, timedOut, selfTransition);
    }
    
    protected String getFullName() {
        return getStateMachineName() + "." + getName();
    }


    protected void _exit(Object [] args, boolean executeExitMethods, boolean notifyParent) {
        _exit(args, executeExitMethods, notifyParent, false, false);
    }
    /**
     *
     * @param args
     * @param executeExitMethods
     */
    protected void _exit(Object [] args, boolean executeExitMethods, boolean notifyParent, boolean timedOut, boolean selfTransition) {
        if (gLogger.isEnabledFor(Level.DEBUG)) {
            gLogger.log(Level.DEBUG,args[0] + "[State-Exit] " + this.getFullName() + ", timedOut= " + timedOut);
        }

        if (isEndState()) {
            if (isAmbiguous()) {
                rollbackExclusiveStates();
            }
            if (executeExitMethods) {
                if (gLogger.isEnabledFor(Level.DEBUG)) {
                    gLogger.log(Level.DEBUG,args[0] + "[State-Exit] " + this.getFullName());
                }
                onExit(args);
                if(!timedOut)cancelExpiryTimer(RuleSessionManager.getCurrentRuleSession());
                this.setState(STATE_EXIT, selfTransition);
            } else {
                if (gLogger.isEnabledFor(Level.DEBUG)) {
                    gLogger.log(Level.DEBUG,args[0] + "[State-Ambiguous-Exit] " + this.getFullName());
                }
                this.setState(STATE_PROBABLY_EXIT, selfTransition);
            }
            // Delete the current state machine
        } else if (hasCompletion()) {
            if (executeExitMethods) {
                if (gLogger.isEnabledFor(Level.DEBUG)) {
                    gLogger.log(Level.DEBUG,args[0] + "[State-Complete] " + this.getFullName());
                }

                this.setState(STATE_COMPLETE, selfTransition);
            } else {
                if (gLogger.isEnabledFor(Level.DEBUG)) {
                    gLogger.log(Level.DEBUG,args[0] + "[State-Ambiguous-Complete] " + this.getFullName());
                }

                this.setState(STATE_PROBABLY_COMPLETE, selfTransition);
            }
        } else {
            if (isAmbiguous()) {
                rollbackExclusiveStates();
            }

            if (!notifyParent) {
                if (executeExitMethods) {
                    if (gLogger.isEnabledFor(Level.DEBUG)) {
                        gLogger.log(Level.DEBUG,args[0] + "[State-Exit] " + getFullName());
                    }
                    onExit(args);
                    if(!timedOut)cancelExpiryTimer(RuleSessionManager.getCurrentRuleSession());
                    this.setState(STATE_EXIT, selfTransition);
                } else {
                    if (gLogger.isEnabledFor(Level.DEBUG)) {
                        gLogger.log(Level.DEBUG,args[0] + "[State-Ambiguous-Exit] " + getFullName());
                    }
                    this.setState(STATE_PROBABLY_EXIT, selfTransition);
                }
            }
        }
    }

    /**
     *
     * @param args
     */
    public void timeout(Object [] args) {
        if (isReady() || isActive() ) {
            onTimeout(args);

            byte policy = getTimeoutPolicy();
            if(policy != com.tibco.cep.runtime.model.element.stategraph.StateVertex.NO_ACTION_TIMEOUT_POLICY) {
                exit(args,false,true, true);
                this.setState(STATE_PROBABLY_EXIT, false);
                RuleSession session = RuleSessionManager.getCurrentRuleSession();
                if (null != session) {
                    Logger logger = session.getRuleServiceProvider().getLogger(PropertyStateMachine.class);
                    if (logger.isEnabledFor(Level.DEBUG)) {
                        logger.log(Level.DEBUG,args[0] + "[State-Ambiguous-Exit] " + getFullName());
                    }
                }

                if ((policy == com.tibco.cep.runtime.model.element.stategraph.StateVertex.DETERMINISTIC_STATE_POLICY) ||
                        (policy == com.tibco.cep.runtime.model.element.stategraph.StateVertex.NON_DETERMINISTIC_STATE_TIMEOUT_POLICY)) {
                    jumpToState(args);
                }
            }
            else {
                //do this in case there was a pre-existing state timeout lingering around for whatever reason
                //retractStateTimeoutEvent();
                this.incrementCount(false);
                startExpiryTimer(args);
            }
        }
    }
    
    public void startExpiryTimer(long timeout) {
        if (timeout > 0) {
            ((BETimeManager)RuleSessionManager.getCurrentRuleSession().getTimeManager()).scheduleStateExpiry(this, timeout);
        }
    }
    public void startExpiryTimer(Object[] args) {
        startExpiryTimer(getTimeout(args) * getTimeoutMultiplier());
    }
    
    public void cancelExpiryTimer(RuleSession rs) {
        if(isTimeoutSet() || isCompleteOrExited()) ((BETimeManager)rs.getTimeManager()).cancelStateExpiry(this);
    }

    //called by timeoutChildren of PropertyStateMachineCompositeState
    public void parentTimedOut(RuleSession rs, Object[] args) {
        cancelExpiryTimer(rs);
        incrementCount(false);
        startExpiryTimer(args);
    }

    /**
     *
     * @param args
     */
    public void complete (Object [] args, boolean selfTransition) {
        if (hasCompletion()) {
            if (gLogger.isEnabledFor(Level.DEBUG)) {
                gLogger.log(Level.DEBUG,args[0] + "[State-Complete] " + getFullName());
            }
            if (isAmbiguous()) {
                rollbackExclusiveStates();
            }
            this.onExit(args);
            cancelExpiryTimer(RuleSessionManager.getCurrentRuleSession());
            this.setState(STATE_EXIT, selfTransition);
        }
    }

    public void rollback() {
        if (isReady() || isActive()) {
            if (gLogger.isEnabledFor(Level.DEBUG)) {
                gLogger.log(Level.DEBUG,this.getOwner() + "[State-Rollback-Inactive] " + getFullName());
            }
            setState(STATE_INACTIVE, false);
        }
    }
    public void jumpToState(Object []args) {
    }
    /**
     *
     * @return
     */
    public int getHistoryPolicy() {
        return Property.HISTORY_POLICY_ALL_VALUES;
    }

    public static int getHistoryPolicy_static() {
        return Property.HISTORY_POLICY_ALL_VALUES;
    }

    /**
     *
     * @return
     */
    public boolean isActive() {

        return (isInState(m_value, STATE_ACTIVE) || isInState(m_value, STATE_ACTIVE_TIMEOUT) || isInState(m_value, STATE_PROBABLY_ACTIVE) || isInState(m_value, STATE_PROBABLY_ACTIVE_TIMEOUT)) ;
    }

    public boolean isReady() {
        return isInState(m_value,STATE_READY) || isInState(m_value,STATE_READY_TIMEOUT) || isInState(m_value,STATE_PROBABLY_READY) || isInState(m_value,STATE_PROBABLY_READY_TIMEOUT);
    }

    public boolean isTimeoutSet() {
        return isInState(m_value,STATE_ACTIVE_TIMEOUT) || isInState(m_value,STATE_READY_TIMEOUT) || isInState(m_value,STATE_PROBABLY_ACTIVE_TIMEOUT) || isInState(m_value,STATE_PROBABLY_READY_TIMEOUT);
    }

    public boolean isComplete() {
        return isInState(m_value, STATE_COMPLETE) || isInState(m_value, STATE_PROBABLY_COMPLETE);
    }

    public boolean isExited() {
        return isInState(m_value,STATE_EXIT) || isInState(m_value, STATE_PROBABLY_EXIT);
    }
    public boolean isCompleteOrExited() {
        return isComplete() || isExited();
    }
    public boolean isAmbiguous() {
        return isInState(m_value,STATE_PROBABLY_READY) || isInState(m_value, STATE_PROBABLY_ACTIVE) || isInState(m_value,STATE_PROBABLY_COMPLETE);
    }
    public boolean hasCompletion() {
        return false;
    }

    public boolean isEndState() {
        return false;
    }
    public abstract byte getTimeoutPolicy();

    public abstract long getTimeoutMultiplier();

    public abstract long getTimeout(Object [] args);

    public abstract void rollbackExclusiveStates();
    public abstract void retractReferenced();

    protected  void onEntry(Object [] objects) {
    }

    protected  void onExit(Object [] objects) {
    }

    protected  void onTimeout(Object [] objects) {
    }

    abstract public java.lang.String getStateMachineName();

    //abstract public Class [] getEnabledRules();

    protected void setState(int stateId, boolean selfTransition) {
        int val = SetState(m_value, stateId);
        this.setInt(val, !selfTransition);
    }

    public int getCount() {
        return GetCount(m_value);
    }

    public int getCurrentState() {
        int currState=getInt();
        final int stateMask = 0xFF;
        return ((currState & stateMask));
    }



    public static int SetState(int currState, int stateId) {
        // currState = (GetCount(currState) + 1) << 24;
        currState = currState & 0xFFFFFF00;
        return (currState  | stateId); //maximum 16 internal states
    }

    public void incrementCount(boolean selfTransition) {
        int currState=getInt();
        currState = incrementCount(currState);
        setInt(currState, !selfTransition);
    }

    public static int GetCount(int currState) {
        return (currState & 0xFF000000) >> 24 ;
    }

    public static boolean isInState(int currState, int stateId) {

        final int stateMask = 0xFF;
        return ((currState & stateMask) == stateId);

    }

    public static int incrementCount(int currState) {
        int state = currState & 0xFF;
        int count = GetCount(currState) + 1;
        int retState = (state | (count << 24));
        return retState;
    }

//    public static void main(String[] args) {
//
//        int currState = STATE_INACTIVE;
//        currState = SetState(currState, STATE_ACTIVE);
//        currState = incrementCount(currState);
//        System.out.println("tmpGetCount(currState) == 1 is " + GetCount(currState));
//
//        assert GetCount(currState) == 1;
//        assert isInState(currState, STATE_ACTIVE);
//
//        currState = SetState(currState, STATE_READY);
//        currState = incrementCount(currState);
//        assert GetCount(currState) == 2;
//        assert isInState(currState, STATE_READY);
//
//        currState = SetState(currState, STATE_TIMEOUT);
//        currState = incrementCount(currState);
//        assert GetCount(currState) == 3;
//        assert isInState(currState, STATE_TIMEOUT);
//
//        currState = SetState(currState, STATE_TIMEOUT);
//        currState = incrementCount(currState);
//        assert GetCount(currState) == 4;
//
//        currState = SetState(currState, STATE_TIMEOUT);
//        currState = incrementCount(currState);
//        assert GetCount(currState) == 5;
//
//        currState = SetState(currState, STATE_COMPLETE);
//        currState = incrementCount(currState);
//        currState = SetState(currState, STATE_EXIT);
//        currState = incrementCount(currState);
//
//        currState = SetState(currState, STATE_PROBABLY_ACTIVE);
//        currState = incrementCount(currState);
//
//        currState = SetState(currState, STATE_PROBABLY_READY);
//        currState = incrementCount(currState);
//        currState = SetState(currState, STATE_PROBABLY_COMPLETE);
//        currState = incrementCount(currState);
//        currState = SetState(currState, STATE_PROBABLY_EXIT);
//        currState = incrementCount(currState);
//
//        assert isInState(currState, STATE_PROBABLY_READY);
//
//
//
//        assert GetCount(currState) ==  11;
//
//        for (int i=0; i < 250; i++) {
//            currState = SetState(currState, STATE_EXIT);
//            incrementCount(currState);
//        }
//
//        System.out.println("Get Count is = " + GetCount(currState));
//
//    }

}
