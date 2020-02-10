package com.tibco.cep.studio.debug.input;

import org.eclipse.debug.core.model.IDebugTarget;

import com.sun.jdi.ReferenceType;
import com.sun.jdi.VirtualMachine;
import com.tibco.cep.runtime.service.debug.DebugTaskFactory;
import com.tibco.cep.studio.debug.core.model.DebuggerSupport;
import com.tibco.cep.studio.debug.core.model.RuleDebugThread;

public class WMContentsInputTask extends AbstractVmResponseTask {
	
	/**
	 * Name of the rule session whose WM we are interested in.
	 */
	private String ruleSessionName;
	
	/**
	 * Fetch these number of objects at a time.
	 */
	private int numberOfObjects;
	
	private boolean reset = false;

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
	 * @param target
	 * @param ruleSessionName
	 * @param numberOfObjects
	 */
	public WMContentsInputTask(IDebugTarget target,String ruleSessionName, int numberOfObjects) {
		super(target);
		this.ruleSessionName = ruleSessionName;
		this.numberOfObjects = numberOfObjects;
	}
	
	/**
	 * @param target
	 * @param ruleSessionName
	 * @param numberOfObjects
	 * @param reset
	 */
	public WMContentsInputTask(IDebugTarget target,String ruleSessionName, int numberOfObjects, boolean reset) {
		this(target, ruleSessionName, numberOfObjects);
		this.reset = reset;
	}
	
	/**
	 * @param ruleDebugThread
	 * @throws Exception
	 */
	public void execute(RuleDebugThread ruleDebugThread) throws Exception {
		final VirtualMachine vm = ruleDebugThread.getVM();
		final Class<DebugTaskFactory> clazz = DebugTaskFactory.class;
		ReferenceType debugTaskFactoryType = DebuggerSupport.findClass(vm, clazz.getName());
		DebuggerSupport.fetchWMContents(ruleDebugThread, 
				                        debugTaskFactoryType, 
				                        ruleSessionName, 
				                        numberOfObjects);
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
		if (!(obj instanceof WMContentsInputTask)) {
			return false;
		}
		WMContentsInputTask other = (WMContentsInputTask)obj;
		
		if (!ruleSessionName.equals(other.getRuleSessionName())) {
			return false;
		}
		return true;
	}
	
	
	public boolean isReset() {
		return reset;
	}

}
