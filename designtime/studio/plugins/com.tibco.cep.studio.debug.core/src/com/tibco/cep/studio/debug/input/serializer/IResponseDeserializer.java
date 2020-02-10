package com.tibco.cep.studio.debug.input.serializer;

import org.eclipse.debug.core.model.IVariable;

import com.tibco.cep.studio.debug.input.VmResponseTask;

/**
 * This interface has to be implemented for every task which
 * is a response task.
 * @author pdhar
 *
 */
public interface IResponseDeserializer {
	
	/**
	 * 
	 * @param task
	 * @return
	 */
	boolean handlesTask(VmResponseTask task);
	
	/**
	 * 
	 * @param var
	 * @return
	 */
	Object deserialize(IVariable var);

}
