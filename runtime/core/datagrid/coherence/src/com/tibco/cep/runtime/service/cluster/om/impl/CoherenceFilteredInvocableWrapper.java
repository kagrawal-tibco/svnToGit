package com.tibco.cep.runtime.service.cluster.om.impl;

import com.tangosol.util.InvocableMap.Entry;
import com.tangosol.util.processor.AbstractProcessor;
import com.tibco.cep.runtime.service.om.api.DefaultInvocableResult;
import com.tibco.cep.runtime.service.om.api.Invocable;
import com.tibco.cep.runtime.service.om.api.Invocable.Status;

/**
 * 
 * @author bgokhale
 * 
 * Coherence wrapper class to marshall and execute FilteredInvocable
 * 
 */
public class CoherenceFilteredInvocableWrapper extends AbstractProcessor {
	private static final long serialVersionUID = -58005976220123637L;
	Invocable invocable;
	
	public CoherenceFilteredInvocableWrapper(Invocable invocable) {
		this.invocable = invocable;
	}

	
	@Override
	public Object process(Entry entry) {
		DefaultInvocableResult r = new DefaultInvocableResult();
		try {
			Object result = null;
			if (invocable != null) {
				result = invocable.invoke(entry);
			} 
			r.setResult(result);
			r.setStatus(Status.SUCCESS);
		} catch (Exception e) {
			r.setException(e);
			r.setStatus(Status.ERROR);
		}
		return r;
	}
}
