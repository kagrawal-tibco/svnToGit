package com.tibco.cep.interpreter.template;

import java.util.HashSet;
import java.util.Set;

import com.tibco.cep.kernel.model.knowledgebase.RuleLoader;
import com.tibco.cep.kernel.model.knowledgebase.SetupException;
import com.tibco.cep.kernel.model.rule.InvalidRuleException;
import com.tibco.cep.kernel.model.rule.Rule;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.runtime.session.RuleServiceProvider;
import com.tibco.cep.runtime.session.RuleSession;
import com.tibco.cep.runtime.session.RuleSessionManager;
import com.tibco.cep.webstudio.model.rule.instance.RuleTemplateInstance;


/*
 * User: nprade
 * Date: 3/9/12
 * Time: 3:27 PM
 *
 * Used in RuleSessionImpl.
 */
@SuppressWarnings("UnusedDeclaration")
public class TemplatedRuleLoader {


    private RuleLoader ruleloader;
    private TemplatedRuleFactory templatedRuleFactory;

    public TemplatedRuleLoader(
            RuleLoader ruleloader,
            RuleServiceProvider rsp)
            throws Exception {

        this.ruleloader = ruleloader;
        this.templatedRuleFactory = new TemplatedRuleFactory(rsp);
    }


    public TemplatedRuleLoader(
            RuleLoader ruleloader,
            TemplatedRuleFactory factory)
            throws Exception {

        this.ruleloader = ruleloader;
        this.templatedRuleFactory = factory;
    }


    public Set<Rule> loadRuleTemplates()
            throws SetupException, InvalidRuleException {

        final Set<Rule> rules = new HashSet<Rule>();
        for (final RuleTemplateInstance rtConfig : TemplateConfigRegistry.getInstance().getTemplateConfigs()) {
            try {
                rules.add(this.ruleloader.loadRule(this.templatedRuleFactory.newTemplatedRule(rtConfig)));
            }
            catch (Exception e) {
            	RuleSession session = RuleSessionManager.getCurrentRuleSession();
            	if (session != null) {
            		session.getRuleServiceProvider().getLogger(getClass()).log(Level.ERROR, e, "%s - id:%s", rtConfig.getImplementsPath(),rtConfig.getId());
            	}
                throw new InvalidRuleException(rtConfig.getImplementsPath() + " - id:" + rtConfig.getId());
            }
        }

        return rules;
    }

}
