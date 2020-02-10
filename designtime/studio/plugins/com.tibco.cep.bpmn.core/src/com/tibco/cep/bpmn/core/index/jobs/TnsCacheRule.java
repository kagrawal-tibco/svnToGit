package com.tibco.cep.bpmn.core.index.jobs;

import org.eclipse.core.runtime.jobs.ISchedulingRule;

import com.tibco.cep.repo.BETargetNamespaceCache;

public class TnsCacheRule implements ISchedulingRule {
	
	BETargetNamespaceCache tnsCache;
	
	public TnsCacheRule(BETargetNamespaceCache tns) {
		this.tnsCache = tns;
	}

	@Override
	public boolean contains(ISchedulingRule rule) {		
		return rule == this;
	}
	
	public BETargetNamespaceCache getTnsCache() {
		return tnsCache;
	}

	@Override
	public boolean isConflicting(ISchedulingRule rule) {
		if (contains(rule)) {
            return true;
        }
		
		if(rule instanceof TnsCacheRule) {
			return ((TnsCacheRule)rule).getTnsCache().equals(tnsCache);
		}
		return false;
	}

}
