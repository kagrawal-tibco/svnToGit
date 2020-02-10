package com.tibco.cep.studio.debug.core.model;

import java.util.ArrayList;
import java.util.Map;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.DebugException;

import com.sun.jdi.Location;
import com.sun.jdi.request.EventRequest;
import com.sun.jdi.request.EventRequestManager;

/*
@author ssailapp
@date Jul 21, 2009
*/

/**
 * A Debug session represents a vm that is being debugged by debugger Application
 *
 */
public interface DebugSession extends RunSession {

    
    
    
    /**
     * returns the debug session state
     * @return
     */
    int getDebugState();
    /**
     * returns the debug thread
     * @return
     */
    RuleDebugThread getDebugThreadInfo();
    /**
     * returns the debug target
     * @return
     */
    <T extends IRuleDebugTarget> T getRuleDebugTarget();
    /**
     * performs step over
     * @param propertyMap
     * @return
     * @throws CoreException
     */
    //boolean stepOver(RuleDebugThread rdt, Map propertyMap) throws DebugException;
    /**
     * performs step into
     * @param propertyMap
     * @return
     * @throws CoreException
     */
    //boolean stepInto(RuleDebugThread rdt, Map propertyMap) throws DebugException;
    /**
     * performs step out
     * @param propertyMap
     * @return
     * @throws CoreException
     */
    //boolean stepReturn(RuleDebugThread rdt, Map propertyMap) throws DebugException;
    /**
     * checks if there is a break point at the given location
     * @param location
     * @return
     * @throws CoreException
     */
    boolean hasBreakPoint(Location location) throws CoreException;
    /**
     * set a breakpoint 
     * @param beEntityName
     * @param lineNumber
     * @param propertyMap
     * @throws CoreException
     */
    //void setBreakPoint(String beEntityName, int lineNumber, Map propertyMap) throws CoreException;
    

    /**
     * add step out break points
     * @param props
     * @throws CoreException
     */
    @SuppressWarnings("rawtypes")
	void createStepOutBreakPoints(RuleDebugThread rdt, Map props) throws CoreException;
    

    /**
     * deploy rules to the debugged engine
     * @param thread
     * @param rules
     * @param deploy
     * @throws DebugException 
     */
	void deployRules(RuleDebugThread thread, String[] rules, boolean deploy) throws CoreException;
	
	/**
	 * Returns the event RequestManager
	 * @return
	 */
	EventRequestManager getEventRequestManager();
	
	/**
	 * @return the breakpoints
	 */
	public ArrayList<EventRequest> getBreakpoints();
	
	
}
