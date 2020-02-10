package com.tibco.cep.studio.debug.ui.model;

import java.util.Map;

import com.tibco.cep.studio.debug.core.model.RuleBreakpoint;
@SuppressWarnings({"rawtypes","unchecked"})
public class BreakpointUtils {

	public static void addBreakpointAttributesWithMemberDetails(Map attributes,
			String typeName, int start, int end) {
		attributes.put(RuleBreakpoint.MEMBER_START, new Integer(start));
		attributes.put(RuleBreakpoint.MEMBER_END, new Integer(end));
		
	}

}
