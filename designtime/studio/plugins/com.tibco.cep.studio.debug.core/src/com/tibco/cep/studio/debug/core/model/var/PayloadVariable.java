/**
 * 
 */
package com.tibco.cep.studio.debug.core.model.var;

import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.model.IVariable;

import com.sun.jdi.ObjectReference;
import com.sun.jdi.Value;
import com.tibco.cep.studio.debug.core.StudioDebugCorePlugin;
import com.tibco.cep.studio.debug.core.model.DebuggerSupport;
import com.tibco.cep.studio.debug.core.model.RuleDebugStackFrame;
import com.tibco.cep.studio.debug.core.model.RuleDebugThread;

/**
 * @author pdhar
 *
 */
public class PayloadVariable extends RuleDebugVariable implements IVariable {
	private String payload = "";
	
	/**
	 * @param target
	 * @param tinfo
	 * @param name
	 * @param value
	 */
	public PayloadVariable(RuleDebugStackFrame frame, RuleDebugThread tinfo,
			String name, Value value) {
		super(frame, tinfo, "Payload", value);
	}
	
	
	@Override
	public void init() {
		if (getJdiValue() instanceof ObjectReference) {
            StringBuilder builder = new StringBuilder();
            ObjectReference objRef = (ObjectReference) getJdiValue();
            builder.append(objRef.type().name()).
            		append("@").
            		append("id=").
            		append(objRef.uniqueID()).
            		append(" toString() = ");
            try {
//                List<Method> methods = ((ReferenceType) objRef.type()).methodsByName("toString");
//                StringReference s = (StringReference) getThreadInfo().invokeMethod(null, objRef, methods.get(0), Collections.EMPTY_LIST, false);            
                String s = DebuggerSupport.toString(getThreadInfo(), objRef);            
                builder.append(s);
                String data[] = s.split("\"");
                payload =  data[1];
            }
            catch (Throwable t) {
            	builder.setLength(0);
            }
            builder.toString();
    	}
		StudioDebugCorePlugin.debug("************ Initialized Payload Variable *************");
	}
	
	@Override
	public String getValueString() throws DebugException {
		return payload;
	}

}
