/**
 * 
 */
package com.tibco.cep.studio.debug.input.serializer;

import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.model.IValue;
import org.eclipse.debug.core.model.IVariable;

import com.tibco.cep.studio.debug.core.StudioDebugCorePlugin;
import com.tibco.cep.studio.debug.input.VmResponseTask;
import com.tibco.cep.studio.debug.input.WMContentsInputTask;

/**
 * @author aathalye
 *
 */
public class WMTaskContentsDeserializer implements IResponseDeserializer {

	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.debug.input.serializer.IResponseDeserializer#deserialize(org.eclipse.debug.core.model.IVariable)
	 */
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

	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.debug.input.serializer.IResponseDeserializer#handlesTask(com.tibco.cep.studio.debug.input.VmResponseTask)
	 */
	@Override
	public boolean handlesTask(VmResponseTask task) {
		return task instanceof WMContentsInputTask;
	}
}
