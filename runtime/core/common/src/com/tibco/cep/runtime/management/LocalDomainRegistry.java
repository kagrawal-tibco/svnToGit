/*
 * Copyright (c) TIBCO Software Inc 2010.
 * All rights reserved.
 *
 * This software is confidential and proprietary information of
 * TIBCO Software Inc.
 *
 * Author: Suresh Subramani (suresh.subramani@tibco.com)
 * Date  : 19/8/2010
 */

package com.tibco.cep.runtime.management;

//import com.tibco.cep.kernel.service.logging.Level;
//import com.tibco.cep.kernel.service.logging.LogManagerFactory;
import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledFuture;

import com.tibco.cep.runtime.config.Configuration;
import com.tibco.cep.runtime.service.Service;
import com.tibco.cep.runtime.service.ServiceRegistry;
import com.tibco.cep.runtime.service.cluster.util.ScheduledWorkManager;
import com.tibco.cep.runtime.service.cluster.util.WorkManagerFactory;
import com.tibco.cep.runtime.session.RuleServiceProvider;
import com.tibco.cep.runtime.util.FQName;

/*
* Author: Ashwin Jayaprakash Date: Feb 27, 2009 Time: 2:53:59 PM
*/

public class LocalDomainRegistry implements Service {
    /**
     * {@value}.
     */
    public static final long REPUBLISH_DELAY_MILLIS = 5 * 60 * 1000;

    protected ConcurrentHashMap<FQName, Domain> domains;

    protected ScheduledWorkManager scheduledWorkManager;

    protected ScheduledFuture republisherJobHandle;

    protected DomainRepublisherJob republisherJob;

    public String getId() {
        return getClass().getName();
    }

    /**
     * @param configuration
     * @param otherArgs     Expects {@link com.tibco.cep.runtime.session.RuleServiceProvider} at
     *                      position 0 and clusterName as {@link String} at position 1.
     * @throws Exception
     */
    public void init(Configuration configuration, Object... otherArgs) throws Exception {
        domains = new ConcurrentHashMap<FQName, Domain>();

        RuleServiceProvider rsp = (RuleServiceProvider) otherArgs[0];
        String clusterName = (String) otherArgs[1];
        String pid = (String) otherArgs[2];

        scheduledWorkManager = WorkManagerFactory.fetchCommonScheduledWorkManager(clusterName, rsp);

        republisherJob = new DomainRepublisherJob(domains, pid);
    }

    public void start() throws Exception {
        republisherJobHandle =
                scheduledWorkManager.scheduleJobAtFixedRate(republisherJob, REPUBLISH_DELAY_MILLIS);
    }

    public void stop() throws Exception {
        domains.clear();

        if (republisherJobHandle != null) {
            republisherJobHandle.cancel(true);
        }
    }

    //-------------

    public void registerDomain(Domain domain) {
        FQName name = domain.safeGet(DomainKey.NAME);

        Domain existing = domains.putIfAbsent(name, domain);

        if (existing != null) {
            throw new RuntimeException(
                    "A domain already exists under the Name: " + name + ", Domain: " + domain);
        }
    }

    public void unregisterDomain(FQName name) {
        domains.remove(name);
    }

    public Collection<Domain> getDomains() {
        return domains.values();
    }

    public Domain getDomain(FQName name) {
        return domains.get(name);
    }

    //-------------

    protected static class DomainRepublisherJob implements Runnable {
        protected final ConcurrentHashMap<FQName, Domain> domains;
        String pid;

        public DomainRepublisherJob(ConcurrentHashMap<FQName, Domain> domains, String pid) {
            this.domains = domains;
            this.pid = pid;
        }

        public void run() {
            ServiceRegistry registry = ServiceRegistry.getSingletonServiceRegistry();

//            com.tibco.cep.kernel.service.logging.Logger logger = LogManagerFactory.getLogManager().getLogger("Domain_Registry");
//            logger.log(Level.DEBUG, "************starting registry****************");
            try{
	            ManagementCentral managementCentral = registry.getService(ManagementCentral.class);
	            ManagementTable managementTable = managementCentral.getManagementTable();
	            if (managementTable != null) {
		            managementTable.setMemberInfo(pid);
		
		            for (Domain domain : domains.values()) {
		                managementTable.putOrRenewDomain(domain, Integer.MAX_VALUE);
		            }
	            }
//	            logger.log(Level.DEBUG, "************finishing registry****************");
            }
            catch(Exception e){
//            	logger.log(Level.ERROR, "************Error registering Domains****************");
            }
        }
    }
}
