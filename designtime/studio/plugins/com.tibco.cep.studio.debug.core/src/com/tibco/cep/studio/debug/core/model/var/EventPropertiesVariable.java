package com.tibco.cep.studio.debug.core.model.var;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.model.IVariable;

import com.sun.jdi.ArrayReference;
import com.sun.jdi.Field;
import com.sun.jdi.ObjectReference;
import com.sun.jdi.StringReference;
import com.sun.jdi.Value;
import com.tibco.cep.studio.debug.core.model.DebuggerSupport;
import com.tibco.cep.studio.debug.core.model.RuleDebugStackFrame;
import com.tibco.cep.studio.debug.core.model.RuleDebugThread;

public class EventPropertiesVariable extends RuleDebugVariable {
	
	public static final String PROPERTIES_ROW_NAME = "Properties";
	public final static String FIELD_PROPERTY_TYPES = "propertyTypes";
	
	private ObjectReference eventRef;
	private String propCount = "";

	public EventPropertiesVariable(RuleDebugStackFrame frame,RuleDebugThread tinfo,
			ObjectReference eventRef, String name, Value value) {
		super(frame,tinfo, name, value);
		setEventRef(eventRef);
	}
	
	
	
	/**
	 * @return the eventRef
	 */
	public ObjectReference getEventRef() {
		return eventRef;
	}



	/**
	 * @param eventRef the eventRef to set
	 */
	public void setEventRef(ObjectReference eventRef) {
		this.eventRef = eventRef;
	}


	/**
	 * @return the propCount
	 */
	public String getPropCount() {
		return propCount;
	}



	/**
	 * @param propCount the propCount to set
	 */
	public void setPropCount(String propCount) {
		this.propCount = propCount;
	}
	
	
	
	@Override
	public String getValueString() throws DebugException {
		return String.valueOf(getPropCount());
	}
	
	private Value getPropertyValue(String varName) {
		List<Field> fields = eventRef.referenceType().visibleFields();
		for(Field f: fields) {
            if(f.name().startsWith(RuleDebugVariable.PROPERTY_PREFIX_2) && cleanCodeGenPrefix(f.name()).equals(varName)) {
                return eventRef.getValue(f);
            }
        }
		return null;
	}
	
	private Value getValue(String fieldName) {
    	ObjectReference obj = (ObjectReference) eventRef;
    	List<Field> fields = obj.referenceType().visibleFields();
        for (Field f : fields) {
            Value v = obj.getValue(f);
            if (f.name().equalsIgnoreCase(fieldName)) {
            	return (v);
            }
        }
        return null;
    }



	@Override
	public void init() {
		processChildren();
	}
	
	@Override
	public void processChildren() {
		
		if (getJdiValue() != null && getJdiValue() instanceof ArrayReference) {
			if(((ArrayReference) getJdiValue()).length() < 1) 
				return;
			setPropCount(String.valueOf(((ArrayReference) getJdiValue()).length()));
			
			
			List<IVariable> vars = new ArrayList<IVariable>();
			
			List<Value> propNameValues = ((ArrayReference) getJdiValue()).getValues();
			List<Value> propTypeValues = ((ArrayReference) getValue(FIELD_PROPERTY_TYPES)).getValues();
			
			int i = 0;
			for (Value nameVal : propNameValues) {
				Value typeValue = propTypeValues.get(i++);
				final int propertyType = ((Integer) DebuggerSupport.getPrimitiveValue(typeValue)).intValue();
				final String propName = ((StringReference) nameVal).value();
				final Value propValue = getPropertyValue(propName);
				EventPropertyVariable row = new EventPropertyVariable(
						getStackFrame(), 
						getThreadInfo(), 
						propName, 
						propValue,
						propertyType);
//				row.init();
				vars.add(row);
			}
			setChildren(vars);
		}
		
	}

}
