package com.tibco.cep.studio.debug.core.model.var;

import org.eclipse.debug.core.model.IVariable;

import com.sun.jdi.LocalVariable;

public interface IRuleDebugLocalVariable extends IVariable{

	void setLocal(LocalVariable localVariable);

	LocalVariable getLocal();

}
