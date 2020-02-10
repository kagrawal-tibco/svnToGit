package com.tibco.cep.studio.debug.core.model.var;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.model.IVariable;

import com.sun.jdi.Field;
import com.sun.jdi.LocalVariable;
import com.sun.jdi.LongValue;
import com.sun.jdi.ObjectReference;
import com.sun.jdi.StringReference;
import com.sun.jdi.Value;
import com.tibco.cep.studio.debug.core.model.DebuggerSupport;
import com.tibco.cep.studio.debug.core.model.RuleDebugStackFrame;
import com.tibco.cep.studio.debug.core.model.RuleDebugThread;

public class EventVariable extends RuleDebugLocalVariable implements IVariable {
	
	public final static String FIELD_ID = "id";
	public final static String FIELD_EXTID = "extId";
	public final static String FIELD_TYPEID = "TYPEID";
	public final static String FIELD_DESTINATION = "destinationURI";
	public final static String FIELD_PAYLOAD = "m_payload";
	public final static String FIELD_LOCATION = "loadedFromCache";
	public final static String FIELD_PROPERTY_NAMES = "propertyNames";
	
	protected static LongValue entityId = null;
    protected StringReference entityExtId = null;
    protected boolean isEventEntity = false;
    
    /**
     * @param tinfo
     * @param name
     * @param value
     * @param target
     */
	public EventVariable(RuleDebugStackFrame frame,RuleDebugThread tinfo,
			String name, Value value) throws DebugException {
		super(frame,tinfo, name, value);
		init();
	}
	/**
	 * 
	 * @param frame
	 * @param tinfo
	 * @param localVar
	 */
	public EventVariable(RuleDebugStackFrame frame,RuleDebugThread tinfo,
			LocalVariable localVar) throws DebugException {
		super(frame,tinfo,localVar);
		init();
	}

	@Override
	public void init() throws DebugException {
		if (getJdiValue() instanceof ObjectReference 
    			&& DebuggerSupport.isEvent((ObjectReference) getJdiValue()) ) {
			entityId = getEntityId(getThreadInfo(), (ObjectReference)getJdiValue());
			entityExtId = getEntityExtId(getThreadInfo(), (ObjectReference)getJdiValue());
			isEventEntity = true;
    	}
	}
	
	@Override
	public void processChildren() {
		ObjectReference obj = (ObjectReference) getJdiValue();
        if (obj == null)
        	return;
        List<Field> fields = obj.referenceType().visibleFields();
        List<IVariable> vars = new ArrayList<IVariable>();
        RuleDebugVariable idVar = new RuleDebugVariable(getStackFrame(),getThreadInfo(),"ID",entityId);
        vars.add(idVar);
        RuleDebugVariable extIdVar = new RuleDebugVariable(getStackFrame(),getThreadInfo(),"extID",entityExtId);
        vars.add(extIdVar);
        for (Field f : fields) {
            Value v = obj.getValue(f);
            RuleDebugVariable var = getEventFieldVariable(f, v);
            if (var != null) {
//            	var.init();
            	vars.add(var);
            }
        }
        setChildren(vars);
	}

	private RuleDebugVariable getEventFieldVariable(Field f, Value v) {
		RuleDebugVariable row = null;
//		if(f.name().equals(FIELD_ID)) {
//			row = new RuleDebugVariable(getStackFrame(),getThreadInfo(),FIELD_ID,v);
//			entityId = (LongValue) v;
//		} else if( f.name().equals(FIELD_EXTID)) {
//			new RuleDebugVariable(getStackFrame(),getThreadInfo(),FIELD_EXTID,v);
//			entityExtId = (StringReference) v;
//		} 
		if (f.name().equalsIgnoreCase(TypeVariable.FIELD_FILTER)) {
			row = new TypeVariable(getStackFrame(), getThreadInfo(), f.name(),
					v, RuleDebugVariableFactory.ROWTYPE_EVENT);
		} else if (f.name().equalsIgnoreCase(FIELD_TYPEID)) {
			row = new TypeIdVariable(getStackFrame(), getThreadInfo(),
					f.name(), v);
		} else if (f.name().equalsIgnoreCase(FIELD_DESTINATION)) {
			row = new DestinationVariable(getStackFrame(), getThreadInfo(), f
					.name(), v);
		} else if (f.name().equalsIgnoreCase(FIELD_PAYLOAD)) {
			row = new PayloadVariable(getStackFrame(), getThreadInfo(), f
					.name(), v);
		} else if (f.name().equalsIgnoreCase(FIELD_LOCATION)) {
			// row = (EventRow) new LocationRow(threadReference, f.name(), v,
			// (ObjectReference) jdiValue);
		} else if (f.name().startsWith(FIELD_PROPERTY_NAMES)) {
			row = new EventPropertiesVariable(getStackFrame(), getThreadInfo(),
					(ObjectReference) getJdiValue(),
					EventPropertiesVariable.PROPERTIES_ROW_NAME, v);
		}
		return row;
	}

	 

}
