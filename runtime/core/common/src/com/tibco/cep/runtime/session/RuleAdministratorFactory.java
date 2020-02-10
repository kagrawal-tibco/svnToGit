package com.tibco.cep.runtime.session;


import java.lang.reflect.Constructor;

import com.tibco.be.util.BEProperties;
import com.tibco.cep.runtime.util.SystemProperty;


public class RuleAdministratorFactory {


    public static RuleAdministrator createRuleAdministrator(RuleServiceProvider rsp, BEProperties env)
            throws Exception {
        final String className = env.getString(SystemProperty.RULE_ADMINISTRATOR.getPropertyName());

        if (className == null) {
            return new EmptyRuleAdministrator(rsp, env);
        }

        final Class clazz = Class.forName(className);
        final Constructor constructor = clazz.getConstructor(new Class[]{
                RuleServiceProvider.class, BEProperties.class});
        return (RuleAdministrator) constructor.newInstance(new Object[]{rsp, env});
    }


    static final public class EmptyRuleAdministrator implements RuleAdministrator {


        public EmptyRuleAdministrator(RuleServiceProvider rsp, BEProperties env) {
        }


        public void init() throws Exception {
        }


        public void shutdown() {
        }


        public void start() throws Exception {
        }


        public void stop() {
        }


        public void updateState(byte state) {
        }
    }

}
