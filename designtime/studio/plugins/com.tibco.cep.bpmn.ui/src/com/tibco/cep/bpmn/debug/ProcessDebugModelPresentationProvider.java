package com.tibco.cep.bpmn.debug;

import org.eclipse.core.runtime.CoreException;

import com.tibco.cep.studio.debug.core.model.IRuleBreakpoint;
import com.tibco.cep.studio.debug.core.process.IProcessBreakpoint;
import com.tibco.cep.studio.debug.core.process.IProcessBreakpointInfo;
import com.tibco.cep.studio.debug.ui.model.IRuleDebugPresentationProvider;
import com.tibco.cep.studio.debug.ui.model.RuleDebugModelPresentation;

public class ProcessDebugModelPresentationProvider extends RuleDebugModelPresentation implements IRuleDebugPresentationProvider {

	@Override
	public String getMarkerType() {
		return IProcessBreakpoint.PROCESS_BREAKPOINT_MARKER_TYPE;
	}

	@Override
	public String getRuleBreakpointText(IRuleBreakpoint breakpoint) throws CoreException {
		if(breakpoint instanceof IProcessBreakpoint) {
			String typeName= breakpoint.getResourceName();
			StringBuffer label= new StringBuffer();
			label.append(getQualifiedName(typeName));
			appendNodeInfo(((IProcessBreakpoint)breakpoint).getProcessBreakpointInfo(), label);
			appendThreadFilter(breakpoint, label);
			appendHitCount(breakpoint, label);
			return label.toString();
		} else {
			return super.getRuleBreakpointText(breakpoint);
		}
	}

	private StringBuffer appendNodeInfo(IProcessBreakpointInfo breakpoint, StringBuffer label) {
		if (!breakpoint.getGraphObjectId().isEmpty() && breakpoint.getLocation() != null) {
			label.append(" ["); 
			label.append("Node:");  
			label.append(' ');
			label.append(breakpoint.getGraphObjectId());
			label.append(' ');
			label.append("Location:");  
			label.append(' ');  
			label.append(breakpoint.getLocation().name());  
			label.append(']');
		}
		return label;
	}
	
	

}
