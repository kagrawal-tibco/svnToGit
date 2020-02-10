package com.tibco.cep.runtime.service.management.process.impl;

import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.runtime.service.management.RecorderMethodsImpl;
import com.tibco.cep.runtime.service.management.process.EngineMBeansSetter;
import com.tibco.cep.runtime.service.management.process.EngineRecorderMBean;
import com.tibco.cep.runtime.session.RuleServiceProvider;

/**
 * Created by IntelliJ IDEA.
 * User: hlouro
 * Date: Mar 8, 2010
 * Time: 6:47:54 PM
 * To change this template use File | Settings | File Templates.
 */
public class EngineRecorderMBeanImpl extends RecorderMethodsImpl implements EngineRecorderMBean, EngineMBeansSetter {
    public void setRuleServiceProvider(RuleServiceProvider ruleServiceProvider) {
        super.ruleServiceProvider = ruleServiceProvider;
    }

    public void setLogger(Logger logger) {
        super.logger = logger;
    }

    //interface methods implemented in the superclass
}
