package com.tibco.cep.studio.debug.input;

import org.eclipse.debug.core.model.IDebugTarget;

import com.sun.jdi.ReferenceType;
import com.sun.jdi.VirtualMachine;
import com.tibco.cep.runtime.service.debug.DebugTaskFactory;
import com.tibco.cep.studio.debug.core.model.DebuggerSupport;
import com.tibco.cep.studio.debug.core.model.RuleDebugThread;

/**
 * Input task used by Tester for debugger engine.
 *
 */
public class TesterInputTask extends AbstractVmResponseTask  {
	
	private String[] inputData;
	
	private String sessionName;
	
	private String ruleSessionName;
	
	/**
	 * Serialized for of object to assert
	 * @param inputData
	 */
	public TesterInputTask(IDebugTarget target,String[] inputData, String ruleSessionName, String sessionName) {
		
		super(target);
		
		this.inputData = inputData;
				
		this.sessionName = sessionName;
		
		this.ruleSessionName = ruleSessionName;
	}

	/**
	 * @return the inputData
	 */
	public String[] getInputData() {
		return inputData;
	}

		
	
	@Override
	public boolean hasResponse() {
		return true;
	}
	
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
				+ ((inputData == null) ? 0 : inputData.hashCode());
		return result;
	}
	
	/**
	 * 
	 * @param ruleDebugThread
	 * @throws Exception
	 */
	public void execute(RuleDebugThread ruleDebugThread) throws Exception {
		final VirtualMachine vm = ruleDebugThread.getVM();
		final Class<DebugTaskFactory> clazz = DebugTaskFactory.class;
		ReferenceType debugTaskFactoryType = DebuggerSupport.findClass(vm, clazz.getName());
		DebuggerSupport.testData(ruleDebugThread, 
				                 debugTaskFactoryType, 
				                 inputData,
				                 ruleSessionName,
				                 sessionName);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof TesterInputTask)) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		TesterInputTask other = (TesterInputTask) obj;
		if (inputData == null) {
			if (other.inputData != null) {
				return false;
			}
		} else if (!inputData.equals(other.inputData)) {
			return false;
		}
		return true;
	}
}
