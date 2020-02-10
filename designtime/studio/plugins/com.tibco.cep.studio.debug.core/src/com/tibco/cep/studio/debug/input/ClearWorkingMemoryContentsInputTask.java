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
public class ClearWorkingMemoryContentsInputTask extends AbstractVmResponseTask{

	private String ruleSessionName;
	@SuppressWarnings("unused")
	private IDebugTarget target;

	public ClearWorkingMemoryContentsInputTask(IDebugTarget target, String ruleSessionName) {
		super(target);
		this.ruleSessionName = ruleSessionName;
		this.target = target;
	}

	@Override
	public Object getKey() {
		return hashCode();
	}

	public int hashCode() {
		final int prime = 43;
		int result = 1;
		result = prime * result
				+ ((ruleSessionName == null) ? 0 : ruleSessionName.hashCode());
		return result;
	}
	
	public void execute(RuleDebugThread ruleDebugThread) throws Exception {
		final VirtualMachine vm = ruleDebugThread.getVM();
		final Class<DebugTaskFactory> clazz = DebugTaskFactory.class;
		ReferenceType debugTaskFactoryType = DebuggerSupport.findClass(vm, clazz.getName());
		DebuggerSupport.clearWMContents(ruleDebugThread, 
				                        debugTaskFactoryType, 
				                        ruleSessionName);
	}

	public String getRuleSessionName() {
		return ruleSessionName;
	}

	
	
}
