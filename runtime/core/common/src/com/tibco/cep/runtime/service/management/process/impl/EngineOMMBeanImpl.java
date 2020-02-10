package com.tibco.cep.runtime.service.management.process.impl;

import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.runtime.service.management.OMMethodsImpl;
import com.tibco.cep.runtime.service.management.process.EngineMBeansSetter;
import com.tibco.cep.runtime.service.management.process.EngineOMMBean;
import com.tibco.cep.runtime.session.RuleServiceProvider;

/**
 * Created by IntelliJ IDEA.
 * User: hlouro
 * Date: Sep 22, 2009
 * Time: 12:29:17 PM
 * To change this template use File | Settings | File Templates.
 */
public class EngineOMMBeanImpl extends OMMethodsImpl implements EngineOMMBean, EngineMBeansSetter {

    public void setRuleServiceProvider(RuleServiceProvider ruleServiceProvider) {
        super.ruleServiceProvider = ruleServiceProvider;
    }

    public void setLogger(Logger logger) {
        super.logger = logger;
    }

    //interface methods implemented in the superclass

} //class
