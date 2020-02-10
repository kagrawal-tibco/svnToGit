package com.tibco.cep.studio.debug.core.model;

import java.util.Map;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;

/**
 * @author Pranab Dhar
 *
 */
public class RuleJavaBreakpoint extends IntermediateBreakpoint implements IRuleJavaBreakpoint {

	public RuleJavaBreakpoint() {
		super();
	}

	public RuleJavaBreakpoint(IResource resource, String typeName, int lineNumber, int charStart, int charEnd, int hitCount, boolean register, Map attributes,int suspendPolicy)
			throws CoreException {
		super(resource, typeName, lineNumber, charStart, charEnd, hitCount, register, attributes,suspendPolicy);
	}
	
	public  String getMarkerType() {
		return JAVA_INTERMEDIATE_BREAKPOINT_MARKER_TYPE;
	}
	
}
