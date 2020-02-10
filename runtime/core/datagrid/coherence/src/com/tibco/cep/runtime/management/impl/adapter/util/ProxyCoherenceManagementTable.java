package com.tibco.cep.runtime.management.impl.adapter.util;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

import com.tibco.cep.runtime.management.Domain;
import com.tibco.cep.runtime.management.ManagementTable;
import com.tibco.cep.runtime.management.impl.adapter.RemoteCoherenceManagementTable;
import com.tibco.cep.runtime.util.FQName;

/*
* Author: Ashwin Jayaprakash Date: Mar 18, 2009 Time: 12:53:34 AM
*/
public class ProxyCoherenceManagementTable implements ManagementTable {
    protected volatile RemoteCoherenceManagementTable target;

    public ProxyCoherenceManagementTable(RemoteCoherenceManagementTable target) {
        this.target = target;
    }

    public RemoteCoherenceManagementTable getTarget() {
        return target;
    }

    public void resetTarget(RemoteCoherenceManagementTable target) {
        this.target = target;
    }

    public void putOrRenewDomain(Domain domain, long leaseDurationMillis) {
        try {
            target.putOrRenewDomain(domain, leaseDurationMillis);
        }
        catch (Exception e) {
            throw new RuntimeException("Error from remote process.", e);
        }
    }

    public Collection<Domain> getDomains() {
        try {
            return target.getDomains();
        }
        catch (Exception e) {
            throw new RuntimeException("Error from remote process.", e);
        }
    }

    public Domain getDomain(FQName key) {
        try {
            return target.getDomain(key);
        }
        catch (Exception e) {
            throw new RuntimeException("Error from remote process.", e);
        }
    }

    public Domain removeDomain(FQName key) {
        try {
            return target.removeDomain(key);
        }
        catch (Exception e) {
            throw new RuntimeException("Error from remote process.", e);
        }
    }

    public void discard() {

    }
    
    public void setMemberInfo(String info){
    	
    }
    
    @Override
	public Map<String, String> getMembersInfo() {
		return Collections.emptyMap(); //Nop
	}
    
}
