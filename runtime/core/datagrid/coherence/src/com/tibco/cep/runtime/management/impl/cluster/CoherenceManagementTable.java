package com.tibco.cep.runtime.management.impl.cluster;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

import com.tangosol.net.CacheFactory;
import com.tangosol.net.NamedCache;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.LogManagerFactory;
import com.tibco.cep.runtime.management.Domain;
import com.tibco.cep.runtime.management.DomainKey;
import com.tibco.cep.runtime.util.FQName;

/*
* Author: Ashwin Jayaprakash Date: Jan 28, 2009 Time: 2:35:27 PM
*/

/**
 * This class has to be independent from all runtime BE dependencies. This class will also be
 * instantiated and used by non-BE clients.
 */
public class CoherenceManagementTable implements InternalManagementTable {
    /**
     * {@value}.
     */
    public static final String NAME_CACHE_PREFIX = "tibco-be-internal-domain-data-cache$";

    protected NamedCache cache;

    public void init(String clusterName, String role) {
        ClassLoader contextCL = Thread.currentThread().getContextClassLoader();

        cache = CacheFactory.getCache(NAME_CACHE_PREFIX + clusterName, contextCL);
    }

    public void discard() {
        cache = null;
    }

    public void setMemberInfo(String info){
    	
    }
    
    @Override
	public Map<String, String> getMembersInfo() {
		return Collections.emptyMap() ; //Nop
	}    
    //-------------

    public void putOrRenewDomain(Domain domain, long leaseDurationMillis) {
        FQName domainName = domain.safeGet(DomainKey.NAME);
        try{
        	cache.put(domainName, domain);
        }catch(Throwable t){
        	if(t.getMessage().contains("No storage-enabled nodes")){
        		LogManagerFactory.getLogManager().getLogger(getClass())
                .log(Level.DEBUG, "Domain renew failed", t);
        	}
        	else{
        		throw new RuntimeException(t);
        	}
        }
        
    }

    public Collection<Domain> getDomains() {
        return cache.values();
    }

    public Domain getDomain(FQName name) {
        return (Domain) cache.get(name);
    }

    public Domain removeDomain(FQName name) {
        return (Domain) cache.remove(name);
    }
}
