package com.tibco.cep.studio.debug.input;

import org.eclipse.debug.core.model.IDebugTarget;

/*
@author ssailapp
@date Jul 30, 2009 3:52:48 PM
 */

public interface VmTask {
	/**
	 * get the task unique key which is probably the task hashCode
	 * @return
	 */
	Object  getKey();
	
	/**
	 * Return the IDebugTarget this task is associated with
	 * @return
	 */
	IDebugTarget getDebugTarget();
	

}
