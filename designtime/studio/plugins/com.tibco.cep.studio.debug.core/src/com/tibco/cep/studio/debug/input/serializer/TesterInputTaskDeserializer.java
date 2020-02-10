package com.tibco.cep.studio.debug.input.serializer;

import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.model.IValue;
import org.eclipse.debug.core.model.IVariable;

import com.tibco.cep.studio.debug.core.StudioDebugCorePlugin;
import com.tibco.cep.studio.debug.input.TesterInputTask;
import com.tibco.cep.studio.debug.input.VmResponseTask;

public class TesterInputTaskDeserializer implements IResponseDeserializer {

	public TesterInputTaskDeserializer() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public Object deserialize(IVariable var) {
		try {
			IValue value = var.getValue();
			return value.getValueString();
		} catch (DebugException e) {
			StudioDebugCorePlugin.log(e);
		}
		return null;
	}

	@Override
	public boolean handlesTask(VmResponseTask task) {
		return task instanceof TesterInputTask;
	}

}
