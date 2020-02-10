/**
 * 
 */
package com.tibco.cep.runtime.service.debug;

import java.lang.reflect.Method;

import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.runtime.session.RuleServiceProvider;
import com.tibco.cep.runtime.session.RuleSession;

class DeployerTaskImpl implements DebugTask {
        /**
		 * 
		 */
		private final DebuggerService instance;
		public static final String DEBUG_RULE_SESSION_NAME = "DebuggerRuleSession";
        RuleServiceProvider provider;
        String[] rules;
        boolean deploy;
        RuleSession ruleSession;

        DeployerTaskImpl( RuleServiceProvider provider, String[] rules,boolean deploy) {
            instance = DebuggerService.getInstance();
			this.deploy = deploy;
            this.provider = provider;
            this.rules = rules;
            ruleSession = provider.getRuleRuntime().getRuleSession(DEBUG_RULE_SESSION_NAME);
            provider.getLogger(this.getClass()).log(Level.INFO, "Creating deployer task...");
        }

        public void run() {
            try {
                if (deploy) {
//                    ruleSession.deployRules(rules);
                    Class ruleSessionClazz = Class.forName("com.tibco.be.ui.tools.debugger.DebuggerRuleSession");
                    Class[] parmClazz = new Class[]{ String[].class};
                    Method method = ruleSessionClazz.getDeclaredMethod("deployRules",String[].class);
//                    provider.getLogger().logError("class:"+ruleSession.getClass().getName());
                    if(method != null) {
//                        provider.getLogger().logError("method:"+method.toGenericString());
//                        for(Class c:method.getParameterTypes()) {
//                            provider.getLogger().logError("param:"+c.getName());
//                        }
//                        for(String s:rules) {
//                            provider.getLogger().logError("rule:"+s);
//                        }
//                        provider.getLogger().logError("session:"+ruleSession.getName());
                        Object[] val = new Object[]{ rules};
                        method.invoke(ruleSession,val);
                    }
                } else {
//                    ruleSession.undeployRules(rules);
                    Class ruleSessionClazz = Class.forName("com.tibco.be.ui.tools.debugger.DebuggerRuleSession");
                    Class[] parmClazz = new Class[]{ String[].class};
                    Method method = ruleSessionClazz.getDeclaredMethod("undeployRules",parmClazz);
//                    provider.getLogger().logError("class:"+ruleSession.getClass().getName());
                    if(method != null) {
//                        provider.getLogger().logDebug("method:"+method.toGenericString());
//                        for(Class c:method.getParameterTypes()) {
//                            provider.getLogger().logError("param:"+c.getName());
//                        }
//                        for(String s:rules) {
//                            provider.getLogger().logError("rule:"+s);
//                        }
//                        provider.getLogger().logError("session:"+ruleSession.getName());
                        Object[] val = new Object[]{ rules};
                        method.invoke(ruleSession,val);
                    }
                }
            } catch (Exception e) {
                this.provider.getLogger(this.getClass()).log(Level.ERROR, e,
                        "Rule deploy/undeploy failed in debugger service");
            }

        }
        
    }