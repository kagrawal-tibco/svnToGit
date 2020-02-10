package com.tibco.cep.studio.debug.input;

import org.eclipse.debug.core.model.IDebugTarget;

import com.sun.jdi.ReferenceType;
import com.sun.jdi.VirtualMachine;
import com.tibco.cep.runtime.service.debug.DebugTaskFactory;
import com.tibco.cep.studio.debug.core.model.DebuggerSupport;
import com.tibco.cep.studio.debug.core.model.RuleDebugThread;
/**
 * 
 * @author smarathe
 *
 */

public class WorkingMemoryManipulatorInputTask extends AbstractVmResponseTask {
	
	/**
	 * Name of the rule session whose WM we are interested in.
	 */
	private String ruleSessionName;
	
	/**
	 * Fetch these number of objects at a time.
	 */
	private int numberOfObjects;
	private Long id;
	private String[] index;
	private String[] properties;
	
	@Override
	public Object getKey() {
		return hashCode();
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((ruleSessionName == null) ? 0 : ruleSessionName.hashCode());
		return result;
	}

	/**
	 * @param ruleSessionName
	 * @param numberOfObjects
	 */
	public WorkingMemoryManipulatorInputTask(IDebugTarget target,String ruleSessionName, Long id, String[] index, String[] properties) {
		super(target);
		this.ruleSessionName = ruleSessionName;
		this.id = id;
		this.index = index;
		this.properties = properties;
	
	}
	
	public void execute(RuleDebugThread ruleDebugThread) throws Exception {
		final VirtualMachine vm = ruleDebugThread.getVM();
		final Class<DebugTaskFactory> clazz = DebugTaskFactory.class;
		ReferenceType debugTaskFactoryType = DebuggerSupport.findClass(vm, clazz.getName());
		DebuggerSupport.mainpulateWM(ruleDebugThread, 
				                        debugTaskFactoryType, 
				                        ruleSessionName, id, index, properties);
	}

	/**
	 * @return the ruleSessionName
	 */
	public final String getRuleSessionName() {
		return ruleSessionName;
	}

	/**
	 * @return the numberOfObjects
	 */
	public final int getNumberOfObjects() {
		return numberOfObjects;
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.debug.input.AbstractVmResponseTask#hasResponse()
	 */
	@Override
	public boolean hasResponse() {
		return true;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj == this) {
			return true;
		}
		if (!(obj instanceof WorkingMemoryManipulatorInputTask)) {
			return false;
		}
		WorkingMemoryManipulatorInputTask other = (WorkingMemoryManipulatorInputTask)obj;
		
		if (!ruleSessionName.equals(other.getRuleSessionName())) {
			return false;
		}
		return true;
	}
}
