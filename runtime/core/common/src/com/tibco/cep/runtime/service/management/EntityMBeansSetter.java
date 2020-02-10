package com.tibco.cep.runtime.service.management;

import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.runtime.session.RuleServiceProvider;

/**
 * Created by IntelliJ IDEA.
 * User: hlouro
 * Date: Sep 22, 2009
 * Time: 11:50:14 AM
 * To change this template use File | Settings | File Templates.
 */

//every instance (either engine or agent) MBean must implement this interface and the corresponding sub-interface
public interface EntityMBeansSetter {
    void setRuleServiceProvider(RuleServiceProvider ruleServiceProvider);
    void setLogger(Logger logger);
}
