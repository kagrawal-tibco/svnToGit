package com.tibco.cep.runtime.management;

import java.util.Collection;
import java.util.Map;

import com.tibco.cep.runtime.util.FQName;

/*
* Author: Ashwin Jayaprakash Date: Jan 27, 2009 Time: 5:28:51 PM
*/

/**
 * This class has to be independent from all runtime BE dependencies. This class will also be
 * instantiated and used by non-BE clients.
 */
public interface ManagementTable {
    /**
     * @param domain              Uses {@link com.tibco.cep.runtime.management.DomainKey#NAME}.
     * @param leaseDurationMillis
     */
    void putOrRenewDomain(Domain domain, long leaseDurationMillis);

    Collection<Domain> getDomains();

    Domain getDomain(FQName key);

    Domain removeDomain(FQName key);
    
    void setMemberInfo(String info);
    
    Map<String, String> getMembersInfo();
}
