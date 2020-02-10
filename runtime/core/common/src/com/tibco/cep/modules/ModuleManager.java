package com.tibco.cep.modules;

import com.tibco.cep.runtime.session.RuleServiceProvider;

/**
 * Created by IntelliJ IDEA.
 * User: ssubrama
 * Date: Jun 17, 2007
 * Time: 12:10:58 AM
 * To change this template use File | Settings | File Templates.
 */
public interface ModuleManager {

    void init() throws Exception;
    void startModules(int mode) throws Exception;
    void stopModules() throws Exception;
    RuleServiceProvider getRuleServiceProvider();

}
