package com.tibco.cep.runtime.service.management.process.impl;

import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.runtime.service.cluster.CacheAgent;
import com.tibco.cep.runtime.service.management.EntityMBeansHelper;
import com.tibco.cep.runtime.service.management.EntityMBeansSetter;
import com.tibco.cep.runtime.service.management.process.EngineRSPMBean;
import com.tibco.cep.runtime.session.RuleServiceProvider;
import com.tibco.cep.runtime.session.impl.RuleServiceProviderImpl;

/**
 * Created by IntelliJ IDEA.
 * User: hlouro
 * Date: Sep 22, 2009
 * Time: 12:32:04 PM
 * To change this template use File | Settings | File Templates.
 */
public class EngineRSPMBeanImpl extends EntityMBeansHelper implements EngineRSPMBean, EntityMBeansSetter {

    private RuleServiceProvider ruleServiceProvider;
    private Logger logger;

    //constructor

    public EngineRSPMBeanImpl() {
    }

    public void setRuleServiceProvider(RuleServiceProvider ruleServiceProvider) {
        this.ruleServiceProvider = ruleServiceProvider;
    }

    public void setLogger(Logger logger) {
        this.logger = logger;
    }

    public void setCacheAgent(CacheAgent cacheAgent) {
        //not used in here
    }

    //RSP Operations
    public void ResumeRuleServiceProvider() {
        try {
            logger.log(Level.INFO, "resuming rule service provider...");
            if(ruleServiceProvider instanceof RuleServiceProviderImpl) {
              ((RuleServiceProviderImpl)ruleServiceProvider).resumeRSP();
            }
            logger.log(Level.INFO,"RuleServiceProvider " + ruleServiceProvider.getName() + " has been resumed.");
        } catch (Exception e) {
            logger.log(Level.ERROR, e, e.getMessage());
            throw new RuntimeException(e.getMessage(), e);
        }//catch
    } //resumeRuleServiceProvider

    public void SuspendRuleServiceProvider() {
        try {
            logger.log(Level.INFO, "suspending rule service provider...");
            if(ruleServiceProvider instanceof RuleServiceProviderImpl) {
              ((RuleServiceProviderImpl)ruleServiceProvider).suspendRSP(RuleServiceProviderImpl.SUSPEND_MODE_ADMIN);
            }
            logger.log(Level.INFO,"RuleServiceProvider " + ruleServiceProvider.getName() + " has been suspended");
        } catch (Exception e) {
            logger.log(Level.ERROR, e, e.getMessage());
            throw new RuntimeException(e.getMessage(), e);
        }//catch
    }//suspendRuleServiceProvider
}
