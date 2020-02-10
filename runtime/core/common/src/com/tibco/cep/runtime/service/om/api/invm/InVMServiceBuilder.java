/*
 * Copyright (c) 2010.  TIBCO Software Inc.
 * All Rights Reserved.
 *
 * This software is confidential and proprietary information of
 * TIBCO Software Inc.
 */

package com.tibco.cep.runtime.service.om.api.invm;


import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.runtime.config.Configuration;
import com.tibco.cep.runtime.service.cluster.agent.AgentConfiguration;
import com.tibco.cep.runtime.service.cluster.agent.InferenceAgent;
import com.tibco.cep.runtime.service.om.impl.invm.BlockingTxnKeeper;
import com.tibco.cep.runtime.service.om.impl.invm.DefaultLocalCache;
import com.tibco.cep.runtime.service.om.impl.invm.NoOpTxnKeeper;
import com.tibco.cep.runtime.util.SystemProperty;

/*
* Author: Ashwin Jayaprakash Date: Nov 18, 2008 Time: 11:27:34 AM
*/

public class InVMServiceBuilder {
    protected LocalCache localCache;

    protected InProgressTxnKeeper txnKeeper;

    /**
     * Should call {@link #discard()} to reuse this instance again.
     *
     * @param ruleSessionConfig
     * @param inferenceAgent
     * @throws Exception
     */
    public void build(Configuration ruleSessionConfig, InferenceAgent inferenceAgent)
            throws Exception {
        final AgentConfiguration agentConfig = inferenceAgent.getAgentConfig();
        final int cacheSize = agentConfig.getL1CacheSize();
        final long cacheExpiryMillis = agentConfig.getL1CacheExpiryMillis();
        final boolean isLenient = agentConfig.isLenient();
        final Logger logger = inferenceAgent.getLogger(InVMServiceBuilder.class);

        //------------

        String className = ruleSessionConfig
                .getPropertyRecursively(SystemProperty.VM_LOCALCACHE_CLASSNAME.getPropertyName());
        if (className == null || className.length() == 0) {
            className = DefaultLocalCache.class.getName();
        }

        Class<? extends LocalCache> cacheClass = (Class<? extends LocalCache>) Class.forName(className);
        localCache = cacheClass.newInstance();

        if (localCache instanceof DefaultLocalCache) {
            String propNameSize = SystemProperty.VM_LOCALCACHE_SIZE.getPropertyName();
            String propNameExpiry = SystemProperty.VM_LOCALCACHE_EXPIRY_MILLIS.getPropertyName();

            String propSize = ruleSessionConfig.getPropertyRecursively(propNameSize);
            String propExpiry = ruleSessionConfig.getPropertyRecursively(propNameExpiry);

            //Not specified. So, let's use this.
            if (propSize == null) {
                ruleSessionConfig.setProperty(propNameSize,
                        Integer.toString(cacheSize));
            }
            if (propExpiry == null) {
                ruleSessionConfig.setProperty(propNameExpiry, Long.toString(cacheExpiryMillis));
            }

            localCache.init(ruleSessionConfig, inferenceAgent);

            //Reset it.
            if (propSize == null) {
                //Use empty string. Setting to null throws NPE.
                ruleSessionConfig.setProperty(propNameSize, "");
            }
            if (propExpiry == null) {
                //Use empty string. Setting to null throws NPE.
                ruleSessionConfig.setProperty(propNameExpiry, "");
            }
        }
        else {
            localCache.init(ruleSessionConfig);
        }

        //------------

        className = ruleSessionConfig
                .getPropertyRecursively(SystemProperty.VM_TXNMGR_CLASSNAME.getPropertyName());
        if (className == null || className.length() == 0) {
            if (isLenient) {
                className = NoOpTxnKeeper.class.getName();
            }
            else {
                className = BlockingTxnKeeper.class.getName();
            }
        }

        Class<? extends InProgressTxnKeeper> txnKeeperClass =
                (Class<? extends InProgressTxnKeeper>) Class.forName(className);
        txnKeeper = txnKeeperClass.newInstance();
        txnKeeper.init(ruleSessionConfig);

        //------------

        logger.log(Level.INFO, "Initialized Local Cache [" + cacheClass.getName() + "].");
        logger.log(Level.INFO, "Initialized Transaction Keeper [" + txnKeeperClass.getName() + "].");
    }

    public InProgressTxnKeeper getTxnKeeper() {
        return txnKeeper;
    }

    public LocalCache getLocalCache() {
        return localCache;
    }

    public void discard() {
        localCache = null;
        txnKeeper = null;
    }
}
