package com.tibco.cep.runtime.service.management.process.impl;

import javax.management.openmbean.TabularDataSupport;

import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.runtime.service.management.WMMethodsImpl;
import com.tibco.cep.runtime.service.management.process.EngineMBeansSetter;
import com.tibco.cep.runtime.service.management.process.EngineWMMBean;
import com.tibco.cep.runtime.session.RuleServiceProvider;

/**
 * Created by IntelliJ IDEA.
 * User: hlouro
 * Date: Mar 8, 2010
 * Time: 7:10:39 PM
 * To change this template use File | Settings | File Templates.
 */
public class EngineWMMBeanImpl extends WMMethodsImpl implements EngineWMMBean, EngineMBeansSetter {

    public void setRuleServiceProvider(RuleServiceProvider ruleServiceProvider) {
            super.ruleServiceProvider = ruleServiceProvider;
        }

        public void setLogger(Logger logger) {
            super.logger = logger;
        }
    
    //Get has to be with capital G. Otherwise JMX will consider it an attribute, and we want it to be an operation
    public TabularDataSupport GetRuleSessions() throws Exception {
        return geTRuleSessions(null);
    } //getSessions



}
