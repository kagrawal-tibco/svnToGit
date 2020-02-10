package com.tibco.cep.bpmn.core.debug;

import static com.tibco.cep.studio.debug.core.model.DebuggerConstants.DEBUG_ACTION_STATUS_COMPLETED;

import java.util.List;

import org.eclipse.debug.core.DebugEvent;
import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.model.IVariable;
import org.eclipse.emf.ecore.EClass;

import com.sun.jdi.Location;
import com.sun.jdi.ObjectReference;
import com.sun.jdi.event.BreakpointEvent;
import com.sun.jdi.event.EventSet;
import com.sun.jdi.event.LocatableEvent;
import com.sun.jdi.event.StepEvent;
import com.tibco.cep.bpmn.core.BpmnCorePlugin;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnMetaModel;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnModelClass;
import com.tibco.cep.studio.debug.core.StudioDebugCorePlugin;
import com.tibco.cep.studio.debug.core.model.DebuggerConstants;
import com.tibco.cep.studio.debug.core.model.DebuggerSupport;
import com.tibco.cep.studio.debug.core.model.IBreakpointHandler;
import com.tibco.cep.studio.debug.core.model.IResourcePosition;
import com.tibco.cep.studio.debug.core.model.IRuleBreakpoint;
import com.tibco.cep.studio.debug.core.model.IRuleDebugThread;
import com.tibco.cep.studio.debug.core.model.RuleDebugElement;
import com.tibco.cep.studio.debug.core.model.RuleDebugStackFrame;
import com.tibco.cep.studio.debug.core.model.RuleDebugThread;
import com.tibco.cep.studio.debug.core.model.impl.AbstractBreakpointHandler;
import com.tibco.cep.studio.debug.core.model.impl.AbstractSession;
import com.tibco.cep.studio.debug.core.model.impl.AbstractSession.EventState;
import com.tibco.cep.studio.debug.core.process.IProcessBreakpoint;
import com.tibco.cep.studio.debug.core.process.IProcessBreakpointInfo;
import com.tibco.cep.studio.debug.core.process.ProcessBreakpointInfo;
import com.tibco.xml.data.primitive.ExpandedName;

/**
 * @author pdhar
 *
 */
public class ProcessBreakpointHandler extends AbstractBreakpointHandler implements IBreakpointHandler {

	public ProcessBreakpointHandler() {
	}

	@Override
	public boolean handlesMarkerType(String markerType) {
		return IProcessBreakpoint.PROCESS_BREAKPOINT_MARKER_TYPE.equals(markerType) || IProcessBreakpoint.PROCESS_INTERMEDIATE_BREAKPOINT_MARKER_TYPE.equals(markerType) ;
	}

	@Override
	public EventState handleBreakpoint(AbstractSession session,EventSet eventSet, BreakpointEvent bpe, String bpType, RuleDebugThread rdt, IRuleBreakpoint bp,
			EventState eventState) {
		
        eventState = super.handleBreakpoint(session,eventSet,bpe, bpType, rdt, bp, eventState);
        try {
        	if (!eventState.isHandled()) {
        		if (bpType.equals(DebuggerConstants.BREAKPOINT_PROCESS)) {
        			eventState.setResume(onLocatableEvent(session,bpe,rdt));
					eventState.setHandled(true);
					StudioDebugCorePlugin.debug("Process breakpoint hit.");
				} else if(bpType.equals(IProcessBreakpoint.PROCESS_INTERMEDIATE_BREAKPOINT_STEP_OVER)){
					session.cleanBreakPointRequestsByType(IProcessBreakpoint.PROCESS_INTERMEDIATE_BREAKPOINT_STEP_OVER);
        			eventState.setResume(onLocatableEvent(session,bpe,rdt));
					eventState.setHandled(true);
					StudioDebugCorePlugin.debug("Process Step Over breakpoint hit.");
				}
        		
        	}
        	
        } catch (Exception e) {
        	session.logError(e);
		}
        
        return eventState;
	}
	


	public boolean onLocatableEvent(AbstractSession session,LocatableEvent le, RuleDebugThread rdt) throws DebugException {
		
        
        final Location location = le.location();
        final String nodeId = (String) le.request().getProperty(IProcessBreakpoint.PROCESS_BREAKPOINT_NODE_ID);
        final IProcessBreakpoint.TASK_BREAKPOINT_LOCATION nodeLocation = (IProcessBreakpoint.TASK_BREAKPOINT_LOCATION) le.request().getProperty(IProcessBreakpoint.PROCESS_BREAKPOINT_NODE_LOCATION);
        final String nodeTaskType = (String) le.request().getProperty(IProcessBreakpoint.PROCESS_BREAKPOINT_NODE_TASK_TYPE);
        final String resourceUri = (String) le.request().getProperty(IProcessBreakpoint.PROCESS_BREAKPOINT_PROCESS_URI);
        final int uniqueId = (Integer) le.request().getProperty(IProcessBreakpoint.PROCESS_BREAKPOINT_NODE_UNIQUE_ID);
        final ProcessBreakpointInfo bpInfo = new ProcessBreakpointInfo(resourceUri, nodeId, nodeTaskType, nodeLocation,uniqueId);
        IResourcePosition loc = null;
        
        synchronized (rdt) {
        	try {
        		boolean isSuspended = rdt.isSuspended();
        		try {
        			if(!isSuspended){
        				rdt.setRunning(false);
        			}
        			IProcessBreakpointInfo currentBreakpointInfo = ProcessDebugModel.getProcessBreakPointInfo(rdt);
        			// match breakpoint info from the event to the info from current stack frame.
        			if(currentBreakpointInfo != null && currentBreakpointInfo.equals(bpInfo)) {
//        				loc = new IResourcePosition(bpInfo.getIndex(), resourceUri);
        				loc = GraphPosition.fromBreakPointInfo(bpInfo);
        			}
        		} finally {
        			if(!isSuspended) {
        				rdt.setRunning(true);
        			}
        		}
        		if(loc != null) {
        			if (!loc.equals(rdt.getLastPosition())) {
						rdt.setLastPosition(loc);
					} else {
						// check if the same line match caused by step,breakpoint event pair then the 
						// first event in the pair must have suspended it already
						if(!rdt.isSuspended()) {
								// do not remove the existing step request and let it continue
								rdt.setRunning(true);
								//rdt.resumedByVM();
								return true;
						}
					}
        			int lastActionType = rdt.getDebugActionType();
        			if (le instanceof BreakpointEvent) {
        				IRuleBreakpoint bp = RuleDebugElement.getBreakPointFromEvent((BreakpointEvent) le);
        				if (bp != null) {  
        					// this is user defined breakpoint                  	
        					StudioDebugCorePlugin.debug("Suspending at Location:" + location.toString());
        					rdt.setRunning(false);
        					rdt.clearPreviousStep();
        					rdt.addCurrentBreakpoint(bp);
        					rdt.setDebugActionStateFlags(lastActionType | DEBUG_ACTION_STATUS_COMPLETED);
        					int stepFlags = getStepFlags(rdt, nodeTaskType);
        					rdt.setProcessStepping(stepFlags);
        					rdt.queueSuspendEvent(DebugEvent.BREAKPOINT);
        					return false;
        					
        				} 
        			} else if (le instanceof StepEvent) {
        				// found the next step line
        				StudioDebugCorePlugin.debug("Stepping on line: " + location);
        				rdt.setRunning(false);
        				rdt.setStepping(false);
        				rdt.queueSuspendEvent(DebugEvent.STEP_END);
        				rdt.setDebugActionStateFlags(lastActionType | DEBUG_ACTION_STATUS_COMPLETED);
        			}
        			
        		} 
        		else {
        			// no matching task node found for the current breakpoint event
        			//rdt.setRunning(true);
        			//rdt.resumedByVM();
        			return true;
        		}
        		
        		
        	} catch(Exception e) {
        		BpmnCorePlugin.log(e);
        	}
			
		}
        
        
		return true;
	}

	private int getStepFlags(RuleDebugThread rdt, final String nodeTaskType) throws DebugException {
		int stepFlags = 0;
		EClass nodeClass = BpmnMetaModel.INSTANCE.getEClass(ExpandedName.parse(nodeTaskType));
		IVariable[] vars = rdt.getTopStackFrame().getVariables();
		if(vars.length > 2) {
			if(BpmnModelClass.FLOW_NODE.isSuperTypeOf(nodeClass) && !(BpmnModelClass.END_EVENT.isSuperTypeOf(nodeClass))) {
				RuleDebugStackFrame frame = (RuleDebugStackFrame) rdt.getTopStackFrame();
				List<ObjectReference> tasks = DebuggerSupport.getOutgoingTasksFromBreakpoint(rdt,frame.getUnderlyingThisObject());
				if(tasks.size() > 0) {
					stepFlags |= IRuleDebugThread.PROCESS_STEP_OVER;
				}
				if(BpmnModelClass.FLOW_NODE.isSuperTypeOf(nodeClass) && BpmnModelClass.RULE_FUNCTION_TASK.isSuperTypeOf(nodeClass) ||
						BpmnModelClass.FLOW_NODE.isSuperTypeOf(nodeClass) && BpmnModelClass.SUB_PROCESS.isSuperTypeOf(nodeClass)) {
					stepFlags |= IRuleDebugThread.PROCESS_STEP_INTO;
				}
				boolean isSubProcess = DebuggerSupport.isSubProcessContext((ObjectReference) vars[0].getAdapter(ObjectReference.class));
				if(isSubProcess) {
					//stepFlags |= IRuleDebugThread.PROCESS_STEP_RETURN;
				}
			}
		}
		return stepFlags;
	}

}
