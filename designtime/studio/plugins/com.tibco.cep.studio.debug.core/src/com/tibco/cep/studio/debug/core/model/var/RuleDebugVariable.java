package com.tibco.cep.studio.debug.core.model.var;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.model.IValue;
import org.eclipse.debug.core.model.IVariable;

import com.sun.jdi.ArrayReference;
import com.sun.jdi.ClassObjectReference;
import com.sun.jdi.ObjectReference;
import com.sun.jdi.PrimitiveValue;
import com.sun.jdi.StringReference;
import com.sun.jdi.Value;
import com.tibco.cep.studio.debug.core.StudioDebugCorePlugin;
import com.tibco.cep.studio.debug.core.model.DebuggerSupport;
import com.tibco.cep.studio.debug.core.model.RuleDebugStackFrame;
import com.tibco.cep.studio.debug.core.model.RuleDebugThread;

public class RuleDebugVariable extends AbstractDebugVariable implements IValue{
    
//    protected String name;
    protected List<IVariable> children;
    
    /**
     * 
     * @param tinfo
     * @param name
     * @param value
     * @param target
     */
	public RuleDebugVariable(RuleDebugStackFrame frame,RuleDebugThread tinfo,String name, Value value) {
		super(frame,tinfo, name , value);
        children = null;
	}
	
	/**
	 * 
	 */
	public void init() throws DebugException {
    }
	
	
	
	@Override
	public void clearChildren() {
		if(getChildren() != null && getChildren().size() > 0) {
			for(IVariable child: getChildren()) {
				if(getStackFrame().getVarMap().containsKey(getJdiValue())) {
					getStackFrame().getVarMap().remove(getJdiValue());
				}
				((AbstractDebugVariable)child).clearChildren();				
			}
			setChildren(null);
		}
		
	}
	
	
	/**
	 * 
	 */
	public void processChildren() throws DebugException {
		if(getJdiValue() == null) {
			return;
		}
		List<IVariable> vChildren = new ArrayList<IVariable>();
		if (getJdiValue() instanceof PrimitiveValue) {

		} else if (getJdiValue() instanceof ArrayReference) {
			vChildren = Arrays.asList(RuleDebugVariableFactory
					.getChildrenFromArrayReference(getStackFrame(),
							getThreadInfo(), (ArrayReference) getJdiValue()));
		} else if (getJdiValue() instanceof ObjectReference) {
			if (getJdiValue() instanceof StringReference || getJdiValue() instanceof ClassObjectReference ) {

			} else {
				vChildren = Arrays.asList(RuleDebugVariableFactory
						.getChildrenFromObjectReference(getStackFrame(),
								getThreadInfo(),
								(ObjectReference) getJdiValue()));
			}
		}
		setChildren(vChildren);
	}
	
	/**
	 * 
	 * @return
	 */
	public List<IVariable> getChildren() {
		return children;
	}
	
	/**
	 * 
	 * @param children
	 */
	public void setChildren(List<IVariable> children) {
		this.children = children;
	}
	
	
	
	
	/************************************* IValue impl ******************************************/
	

	/* (non-Javadoc)
	 * @see org.eclipse.debug.core.model.IVariable#getValue()
	 */
	@Override
	public IValue getValue() throws DebugException {
		return this;
	}	

	/* (non-Javadoc)
	 * @see org.eclipse.debug.core.model.IValue#getReferenceTypeName()
	 */
	@Override
	public String getReferenceTypeName() throws DebugException {
		if(getJdiValue() != null) {
			return getJdiValue().type().name();
		} else {
			return "";
		}
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.debug.core.model.IValue#getValueString()
	 */
	@Override
	public String getValueString() throws DebugException {
//		return DebuggerSupport.value2String(getThreadInfo().thread(),getJdiValue());
		return DebuggerSupport.value2String(getThreadInfo(),getJdiValue());
	}


	/* (non-Javadoc)
	 * @see org.eclipse.debug.core.model.IValue#getVariables()
	 */
	@Override
	public IVariable[] getVariables() throws DebugException {
		if (getChildren() == null) {
			processChildren();
        }         
        return getChildren().toArray(new IVariable[children.size()]);
	}


	/* (non-Javadoc)
	 * @see org.eclipse.debug.core.model.IValue#isAllocated()
	 */
	@Override
	public boolean isAllocated() throws DebugException {
		if(getJdiValue() != null) {
			if(getJdiValue() instanceof  ObjectReference) {
				return !((ObjectReference)getJdiValue()).isCollected();
			}
		}
		return false;
	}

	@Override
	public boolean hasVariables() throws DebugException {
		if(getChildren() == null) {
			processChildren();
			if(getChildren() != null) {
				return getChildren().size() > 0;
			} else {
				return false;
			}
		} else {
			return getChildren().size() > 0;
		}
	}
	
	
	@Override
		public Object getAdapter(Class adapter) {
			if(adapter == ObjectReference.class) {
				if(getJdiValue() instanceof ObjectReference) {
					return getJdiValue();
				}				
			}
			return super.getAdapter(adapter);
		}
	

}
