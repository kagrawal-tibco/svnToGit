package com.tibco.cep.studio.debug.core.model.var;

import java.util.ArrayList;
import java.util.Arrays;
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

public class ConceptVariable extends RuleDebugLocalVariable implements IVariable {
	public final static String FIELD_ID = "id";
	public final static String FIELD_EXTID = "extId";
	
	protected boolean isConceptEntity;
	protected StringReference entityExtId;
	protected LongValue entityId;
	protected ObjectReference conceptRef;
	
	/**
	 * 
	 * @param frame
	 * @param tinfo
	 * @param concept
	 * @param name
	 * @param value
	 */
	public ConceptVariable(RuleDebugStackFrame frame,RuleDebugThread tinfo,
			ObjectReference concept, String name, Value value) throws DebugException {
		super(frame,tinfo, name, value);
		setConceptRef(concept);
		init();
	}
	/**
	 * 
	 * @param frame
	 * @param tinfo
	 * @param localVar
	 */
	public ConceptVariable(RuleDebugStackFrame frame, RuleDebugThread tinfo,
			LocalVariable localVar) throws DebugException {
		super(frame,tinfo,localVar);
		setConceptRef((ObjectReference) getJdiValue());
		init();
	}
	
	

	/**
	 * @return the conceptRef
	 */
	public ObjectReference getConceptRef() {
		return conceptRef;
	}
	/**
	 * @param conceptRef the conceptRef to set
	 */
	public void setConceptRef(ObjectReference conceptRef) {
		this.conceptRef = conceptRef;
	}
	protected StringReference getEntityExtId() {
        return entityExtId;
    }
	
	protected LongValue getEntityId() {
		return entityId;
	}
	
	@Override
	public void init() throws DebugException {
		if (getJdiValue() instanceof ObjectReference
			&& (DebuggerSupport.isConcept((ObjectReference) getJdiValue()) || DebuggerSupport
						.isScorecard((ObjectReference) getJdiValue()))) {
			this.entityId = getEntityId(getThreadInfo(),
					(ObjectReference) getJdiValue());
			this.entityExtId = getEntityExtId(getThreadInfo(),
					(ObjectReference) getJdiValue());
			this.isConceptEntity = true;
		}
	}
	
	
	
	/**
	 * 
	 */
	public void processChildren() {
		setChildren(Arrays.asList(getConceptChildrenFromObjectReference((ObjectReference) getJdiValue()))); 
	}
	

	/**
	 * 
	 * @param obj
	 * @return
	 */
	protected IVariable[] getConceptChildrenFromObjectReference(ObjectReference obj) {
        if (obj == null)
        	return new IVariable[0];
        List<IVariable> vars = new ArrayList<IVariable>();
        RuleDebugVariable idVar = new RuleDebugVariable(getStackFrame(),getThreadInfo(),"ID",entityId);
        vars.add(idVar);
        RuleDebugVariable extIdVar = new RuleDebugVariable(getStackFrame(),getThreadInfo(),"extID",entityExtId);
        vars.add(extIdVar);
        List<Field> fields = obj.referenceType().visibleFields();

        for (Field f : fields) {
            Value v = obj.getValue(f);
            RuleDebugVariable row = getConceptVar(f, v);
            if (row != null) {
            	vars.add(row);
            }
        }
//        RuleDebugVariable cvrow = getCacheVersionRow();
//        if(cvrow != null) {
//        	vars.add(cvrow);	
//        }	
        RuleDebugVariable crow = getConceptPropertyRows();
        if(crow != null) {
        	vars.add(crow);
        }
        return vars.toArray(new IVariable[vars.size()]);
    }
	/**
	 * 
	 * @param f
	 * @param v
	 * @return
	 */
	private RuleDebugVariable getConceptVar(Field f, Value v) {
		RuleDebugVariable row = null;
//		if(f.name().equals(FIELD_ID)) {
//			row = new RuleDebugVariable(getStackFrame(),getThreadInfo(),FIELD_ID,v);
//			entityId = (LongValue) v;
//		} else if( f.name().equals(FIELD_EXTID)) {
//			row = new RuleDebugVariable(getStackFrame(),getThreadInfo(),FIELD_EXTID,v);
//			entityExtId = (StringReference) v;
//		} else 
		if (f.name().equalsIgnoreCase(TypeVariable.FIELD_FILTER)) {
    		
    		row = new TypeVariable(getStackFrame(),
    								getThreadInfo(), 
    								f.name(), 
    								v, 
    								RuleDebugVariableFactory.ROWTYPE_CONCEPT);
    		
    	} else if (f.name().equalsIgnoreCase(VersionVariable.FIELD_FILTER)) {
    	
    		row = new VersionVariable(getStackFrame(),
    									getThreadInfo(), 
    									f.name(), 
    									v, 
    									VersionVariable.VERSION_TYPE_SIMPLE);
    	}
    	return row;
    }
	/**
	 * 
	 * @return
	 */
	@SuppressWarnings("unused")
	private RuleDebugVariable getCacheVersionRow() {
		RuleDebugVariable versionVar = new VersionVariable(getStackFrame(),
										getThreadInfo(), 
										"Cache Version", 
										(ObjectReference) getJdiValue(), 
										VersionVariable.VERSION_TYPE_CACHE);
    	return versionVar;
	}
    /**
     * 
     * @return
     */
	private RuleDebugVariable getConceptPropertyRows() {
		
		ConceptPropertiesVariable properties = new ConceptPropertiesVariable(getStackFrame(),
																	getThreadInfo(),
																	ConceptPropertiesVariable.PROPERTIES_ROW_NAME,
																	getConceptRef());	
		
		
    	return properties;
	}

}
