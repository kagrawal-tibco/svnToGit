package com.tibco.cep.runtime.management.impl.cluster;

import com.tibco.cep.runtime.management.Domain;
import com.tibco.cep.runtime.management.DomainKey;
import com.tibco.cep.runtime.util.FQName;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/*
* Author: Ashwin Jayaprakash Date: Apr 10, 2009 Time: 4:54:48 PM
*/
public class LocalManagementTable implements InternalManagementTable {
    protected ConcurrentHashMap<FQName, Domain> domains;

    public void init(String clusterURL, String role) {
        domains = new ConcurrentHashMap<FQName, Domain>();
    }

    public void discard() {
        domains.clear();
    }

    public void setMemberInfo(String info){

    }

    @Override
	public Map<String, String> getMembersInfo() {
		return Collections.emptyMap(); //Nop
	}

    //-----------

    public void putOrRenewDomain(Domain domain, long leaseDurationMillis) {
        FQName name = domain.safeGet(DomainKey.NAME);
        domains.put(name, domain);
    }

    public Collection<Domain> getDomains() {
        //need to return a set to make it serializable. Serializing is necessary
        //to make this data available through JMX calls, which is necessary for
        //MM In-Memory mode
        return new HashSet<Domain> (domains.values());
    }

    public Domain getDomain(FQName key) {
        return domains.get(key);
    }

    public Domain removeDomain(FQName key) {
        return domains.remove(key);
    }
}