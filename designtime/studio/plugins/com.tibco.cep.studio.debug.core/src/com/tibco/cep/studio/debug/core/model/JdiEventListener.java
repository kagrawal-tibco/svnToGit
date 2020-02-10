package com.tibco.cep.studio.debug.core.model;

import com.sun.jdi.event.BreakpointEvent;
import com.sun.jdi.event.ClassPrepareEvent;
import com.sun.jdi.event.ClassUnloadEvent;
import com.sun.jdi.event.EventSet;
import com.sun.jdi.event.ExceptionEvent;
import com.sun.jdi.event.MethodEntryEvent;
import com.sun.jdi.event.MethodExitEvent;
import com.sun.jdi.event.StepEvent;
import com.sun.jdi.event.ThreadDeathEvent;
import com.sun.jdi.event.ThreadStartEvent;
import com.sun.jdi.event.VMDeathEvent;
import com.sun.jdi.event.VMDisconnectEvent;
import com.sun.jdi.event.VMStartEvent;
import com.sun.jdi.event.WatchpointEvent;

/*
@author ssailapp
@date Jul 21, 2009
*/

public interface JdiEventListener {
    void onEventSet(EventSet eset);
    ResultState onVmStartEvent(EventSet eventSet,VMStartEvent event);
    ResultState onVMDeathEvent(EventSet eventSet,VMDeathEvent event);
    ResultState onVmDisconnect(EventSet eventSet,VMDisconnectEvent event);
    ResultState onClassPrepare(EventSet eventSet, ClassPrepareEvent cpe);
    ResultState onClassUnload(EventSet eventSet,ClassUnloadEvent cpe);
    ResultState onException(EventSet eventSet,ExceptionEvent ee); 
    ResultState onBreakpoint(EventSet eventSet,BreakpointEvent bpe);
    ResultState onStep(EventSet eventSet,StepEvent se);
    ResultState onThreadStart(EventSet eventSet,ThreadStartEvent tse);
    ResultState onThreadDeath(EventSet eventSet,ThreadDeathEvent tde);
    ResultState onWatchpoint(EventSet eventSet,WatchpointEvent awe);
    ResultState onMethodEntry(EventSet eventSet,MethodEntryEvent event);
    ResultState onMethodExit(EventSet eventSet,MethodExitEvent event);
    
    public static interface ResultState {
    	boolean isHandled();
    	boolean canResume();
    }
}
