package com.tibco.cep.studio.debug.input.serializer;

import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.model.IVariable;

import com.tibco.cep.studio.debug.core.StudioDebugCorePlugin;
import com.tibco.cep.studio.debug.input.DebugInputTask;
import com.tibco.cep.studio.debug.input.VmResponseTask;

public class DebugInputTaskDeserializer implements IResponseDeserializer {

	public DebugInputTaskDeserializer() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public Object deserialize(IVariable var) {
		try {
			if(var != null) {
				return var.getValue().getValueString();
			}
		} catch (DebugException e) {
			StudioDebugCorePlugin.log(e);
		}
		return null;
	}
	
	@Override
	public boolean handlesTask(VmResponseTask task) {
		return task instanceof DebugInputTask;
	}

}
