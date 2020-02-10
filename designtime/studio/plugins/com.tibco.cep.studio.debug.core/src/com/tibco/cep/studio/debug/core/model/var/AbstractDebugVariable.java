package com.tibco.cep.studio.debug.core.model.var;

import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.model.IValue;
import org.eclipse.debug.core.model.IVariable;

import com.sun.jdi.LongValue;
import com.sun.jdi.ObjectReference;
import com.sun.jdi.PrimitiveValue;
import com.sun.jdi.StringReference;
import com.sun.jdi.Value;
import com.tibco.cep.studio.debug.core.model.DebuggerSupport;
import com.tibco.cep.studio.debug.core.model.RuleDebugElement;
import com.tibco.cep.studio.debug.core.model.RuleDebugStackFrame;
import com.tibco.cep.studio.debug.core.model.RuleDebugThread;

public abstract class AbstractDebugVariable extends RuleDebugElement implements IVariable{
	public static String PROPERTY_PREFIX_1 = "$1z";
    public static String PROPERTY_PREFIX_2 = "$2z";
    public static String VAR_PREFIX = "$3z";
    	
	private RuleDebugThread thread;
	private Value jdiValue;
	private String name;
	private RuleDebugStackFrame stackFrame;

	public AbstractDebugVariable(RuleDebugStackFrame frame, RuleDebugThread tinfo,String name, Value value) {
		super(frame.getDebugTarget());
		setStackFrame(frame);
		setThreadInfo(tinfo);
		setJdiValue(value);
		setName(name);
	}


	public Value getJdiValue() {
		return jdiValue;
	}
	
	public void setJdiValue(Value jdiValue) {
		Value oldValue = this.jdiValue;
		this.jdiValue = jdiValue;
		if(getStackFrame().getVarMap().containsKey(oldValue)) {
			AbstractDebugVariable oldVar = getStackFrame().getVarMap().remove(oldValue);
			if(oldVar != null) {
				oldVar.clearChildren();
			}
		}
		getStackFrame().getVarMap().put(jdiValue,this);
	}
	

	

	/**
	 * @return the stackFrame
	 */
	public RuleDebugStackFrame getStackFrame() {
		return stackFrame;
	}

	/**
	 * @param stackFrame the stackFrame to set
	 */
	public void setStackFrame(RuleDebugStackFrame stackFrame) {
		this.stackFrame = stackFrame;
	}

	/**
	 * 
	 */
	public abstract void init() throws DebugException;
	
	/**
	 * 
	 */
	public abstract void processChildren() throws DebugException;
	
	
	public abstract void clearChildren();
	
	/**
	 * 
	 * @return
	 */
	public RuleDebugThread getThreadInfo() {
		return thread;
	}
	
	/**
	 * @param thread the thread to set
	 */
	public void setThreadInfo(RuleDebugThread thread) {
		this.thread = thread;
	}
	
	
	/******************************* IVariable impl ************************************/
	
	
	/* (non-Javadoc)
	 * @see org.eclipse.debug.core.model.IVariable#getName()
	 */
	public String getName() {
		return cleanCodeGenPrefix(this.name);
	}
	
	/**
	 * 
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.debug.core.model.IVariable#hasValueChanged()
	 */
	public boolean hasValueChanged() throws DebugException {
		// TODO Auto-generated method stub
		return false;
	}
	/* (non-Javadoc)
	 * @see org.eclipse.debug.core.model.IValueModification#setValue(java.lang.String)
	 */
	public void setValue(String expression) throws DebugException {
	}
	/* (non-Javadoc)
	 * @see org.eclipse.debug.core.model.IValueModification#setValue(org.eclipse.debug.core.model.IValue)
	 */
	public void setValue(IValue value) throws DebugException {
	}
	/* (non-Javadoc)
	 * @see org.eclipse.debug.core.model.IValueModification#supportsValueModification()
	 */
	public boolean supportsValueModification() {
		return false;
	}
	/* (non-Javadoc)
	 * @see org.eclipse.debug.core.model.IValueModification#verifyValue(java.lang.String)
	 */
	public boolean verifyValue(String expression) throws DebugException {
		return false;
	}
	/* (non-Javadoc)
	 * @see org.eclipse.debug.core.model.IValueModification#verifyValue(org.eclipse.debug.core.model.IValue)
	 */
	public boolean verifyValue(IValue value) throws DebugException {
		return false;
	}
	
	/**
	 * 
	 * @return
	 */
	protected Object getPrimitiveValue() {
        if(jdiValue != null) {
            return DebuggerSupport.getPrimitiveValue(jdiValue);
        } else {
            return "";
        }
    }
	
	/**
	 * 
	 * @return
	 * @throws DebugException
	 */
	public boolean hasVariables() throws DebugException {
		if (jdiValue instanceof PrimitiveValue) return false;
	    if (jdiValue instanceof StringReference) return false;
	    return true;
	}
	
	
	/**
	 * @param eventThread
	 * @param jdiValue
	 * @return
	 * @throws Exception
	 */
	protected LongValue getEntityId(RuleDebugThread eventThread,
			ObjectReference jdiValue) throws DebugException {
			return (DebuggerSupport.getEntityId(eventThread,
				(ObjectReference) jdiValue));
	}

	

	/**
	 * @param eventThread
	 * @param jdiValue
	 * @return
	 * @throws Exception
	 */
	protected StringReference getEntityExtId(RuleDebugThread eventThread,
			ObjectReference jdiValue) throws DebugException {
			return (DebuggerSupport.getEntityExtId(eventThread,
				(ObjectReference) jdiValue));
	}
	
	/**
	 * 
	 * @param vName
	 * @return
	 */
	protected String cleanCodeGenPrefix(String vName) {
        if(vName.startsWith(VAR_PREFIX)) {
            return vName.substring(VAR_PREFIX.length());
        } else if(vName.startsWith(PROPERTY_PREFIX_1)) {
            return vName.substring(PROPERTY_PREFIX_1.length());
        } else if(vName.startsWith(PROPERTY_PREFIX_2)) {
            return vName.substring(PROPERTY_PREFIX_2.length());
        }
        return vName;
    }
	
	

}
