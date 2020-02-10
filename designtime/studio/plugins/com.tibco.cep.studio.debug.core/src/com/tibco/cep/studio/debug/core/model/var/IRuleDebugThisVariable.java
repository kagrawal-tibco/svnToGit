package com.tibco.cep.studio.debug.core.model.var;

import org.eclipse.debug.core.model.IValue;
import org.eclipse.debug.core.model.IVariable;

import com.sun.jdi.ObjectReference;

public interface IRuleDebugThisVariable extends IVariable,IValue{

	public ObjectReference retrieveValue();

}
