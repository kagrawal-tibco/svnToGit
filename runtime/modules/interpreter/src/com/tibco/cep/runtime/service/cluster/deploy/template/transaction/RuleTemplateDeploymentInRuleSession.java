package com.tibco.cep.runtime.service.cluster.deploy.template.transaction;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.tibco.cep.interpreter.template.TemplatedRuleFactory;
import com.tibco.cep.kernel.core.base.WorkingMemoryImpl;
import com.tibco.cep.kernel.model.knowledgebase.RuleLoader;
import com.tibco.cep.kernel.model.rule.Rule;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.runtime.service.cluster.deploy.template.operation.RuleTemplateDeploymentOperationDescriptor;
import com.tibco.cep.runtime.service.cluster.deploy.template.operation.RuleTemplateSetDescriptor;
import com.tibco.cep.runtime.service.cluster.deploy.template.operation.RuleTemplateUnsetDescriptor;
import com.tibco.cep.runtime.service.cluster.deploy.util.Change;
import com.tibco.cep.runtime.session.RuleSession;
import com.tibco.cep.runtime.session.RuleSessionManager;
import com.tibco.cep.runtime.session.impl.RuleSessionImpl;
import com.tibco.cep.webstudio.model.rule.instance.RuleTemplateInstance;


/**
 * User: nprade
 * Date: 3/14/12
 * Time: 3:33 PM
 */
public class RuleTemplateDeploymentInRuleSession {


    protected final Map<String, Change<Rule>> idToChange = new HashMap<String, Change<Rule>>();
    protected final Set<Change<Rule>> committed = new HashSet<Change<Rule>>();
    protected final Logger logger;
    protected final RuleSession ruleSession;
    protected final TemplatedRuleFactory templatedRuleFactory;


    public RuleTemplateDeploymentInRuleSession(
            RuleSession ruleSession,
            TemplatedRuleFactory templatedRuleFactory)
            throws Exception {

        this.logger = ruleSession.getRuleServiceProvider().getLogger(RuleTemplateDeploymentInRuleSession.class);
        this.ruleSession = ruleSession;
        this.templatedRuleFactory = templatedRuleFactory;
    }


    private void addRule(
            Rule rule,
            RuleLoader ruleLoader,
            boolean continueOnError)
            throws Exception {

        try {
            ruleLoader.loadRule(rule);
        }
        catch (Exception e) {
            logger.log(Level.ERROR, "Rollback: Failed to load RT in [%s]: %s", ruleSession.getName(),
                    rule.getUri());
            if (!continueOnError) {
                throw e;
            }
        }

        try {
            ruleLoader.deployRuleToWM(rule.getUri());
        }
        catch (Exception e) {
            logger.log(Level.ERROR, "Rollback: Failed to deploy RT in [%s]: %s", ruleSession.getName(),
                    rule.getUri());
            if (!continueOnError) {
                throw e;
            }
        }

        try {
            if (!rule.isActive()) {
                rule.activate();
            }
        }
        catch (Exception e) {
            logger.log(Level.ERROR, "Rollback: Failed to activate RT in [%s]: %s", ruleSession.getName(),
                    rule.getUri());
            if (!continueOnError) {
                throw e;
            }
        }
    }


    public void commit()
            throws Exception {

        final RuleLoader ruleLoader = findRuleLoader();

        boolean hasChanged = false;
        try {
            for (final Change<Rule> change : idToChange.values()) {
                if (null != change.before) {
                    removeRule(change.before, ruleLoader, false);
                    hasChanged = true;
                }
                if (null != change.after) {
                    addRule(change.after, ruleLoader, false);
                    hasChanged = true;
                }
                committed.add(change);
            }
        }
        finally {
            if (hasChanged) {
                ((WorkingMemoryImpl) ((RuleSessionImpl) ruleSession).getWorkingMemory()).initEntitySharingLevels();
                // lookup and assert previously loaded entities
                // check for current rule session, if null, set this rule session as the current
                if (RuleSessionManager.getCurrentRuleSession() == null) RuleSessionManager.currentRuleSessions.set(ruleSession);
                ((WorkingMemoryImpl) ((RuleSessionImpl) ruleSession).getWorkingMemory()).loadObjectToAddedRule();
            }
        }
    }


    private RuleLoader findRuleLoader() {
        return ((RuleSessionImpl) this.ruleSession).getWorkingMemory().getRuleLoader();
    }


    public void prepare(
            RuleTemplateDeploymentOperationDescriptor operationDescriptor)
            throws Exception {

        if (operationDescriptor instanceof RuleTemplateSetDescriptor) {
            prepareSet((RuleTemplateSetDescriptor) operationDescriptor);
        }

        else if (operationDescriptor instanceof RuleTemplateUnsetDescriptor) {
            prepareUnset((RuleTemplateUnsetDescriptor) operationDescriptor);
        }

        else {
            throw new IllegalArgumentException();
        }
    }


    protected void prepareSet(
            RuleTemplateSetDescriptor set)
            throws Exception {

        final String ruleSessionName = this.ruleSession.getName();

        // Checks.
        for (final RuleTemplateInstance config : set.getConfigs()) {
            final String id = config.getId();

            logger.log(Level.DEBUG, "RuleTemplate deployment: RS[%s] add SET %s %s",
                    ruleSessionName, config.getImplementsPath(), id);

            final Change<Rule> existingChange = this.idToChange.get(id);
            if (null != existingChange) {
                if (null == existingChange.after) {
                    throw new IllegalArgumentException("RuleTemplate config is both set and unset.");
                }
                else {
                    throw new IllegalArgumentException("RuleTemplate config is set multiple times.");
                }
            }
        }

        // Updates.
        for (final RuleTemplateInstance config : set.getConfigs()) {
            final Rule newRule = this.templatedRuleFactory.newTemplatedRule(config);
            final Rule oldRule = findRuleLoader().getRule(newRule.getUri());

            final String id = config.getId();
            logger.log(Level.DEBUG, "RuleTemplate deployment: RS[%s] add SET %s %s new[%s]",
                    ruleSessionName, config.getImplementsPath(), id, (null == oldRule));

            this.idToChange.put(id, new Change<Rule>(oldRule, newRule));
        }
    }


    protected void prepareUnset(
            RuleTemplateUnsetDescriptor set) {

        final String ruleSessionName = this.ruleSession.getName();

        // Checks.
        for (final String id : set.getIds()) {

            logger.log(Level.DEBUG, "RuleTemplate deployment: RS[%s] add UNSET %s",
                    this.ruleSession.getName(), id);

            final Change<Rule> existingChange = this.idToChange.get(id);
            if (null != existingChange) {
                if (null == existingChange.after) {
                    logger.log(Level.WARN,
                            "RuleTemplate config is unset multiple times in RuleSession '%s': %s",
                            ruleSessionName, id);
                }
                else {
                    throw new IllegalArgumentException("RuleTemplate config is both set and unset.");
                }
            }
        }

        // Updates.
        for (final String id : set.getIds()) {

            final Rule oldRule = findRuleLoader().getRule("RT" + id); //todo cleanup URI generation

            if (null == oldRule) {
                logger.log(Level.WARN, "Attempted to UNSET RuleTemplate not found in RuleSession '%s': %s",
                        ruleSessionName, id);
            }

            this.idToChange.put(id, new Change<Rule>(oldRule, null));
        }
    }


    private void removeRule(
            Rule rule,
            RuleLoader ruleLoader,
            boolean continueOnError)
            throws Exception {

        try {
            if (rule.isActive()) {
                rule.deactivate();
            }
        }
        catch (Exception e) {
            logger.log(Level.ERROR, "Rollback: Failed to deactivate RT in [%s]: %s",
                    ruleSession.getName(),
                    rule.getUri());
            if (!continueOnError) {
                throw e;
            }
        }


        try {
            ruleLoader.undeployRuleFromWM(rule.getUri());
        }
        catch (Exception e) {
            logger.log(Level.ERROR, "Rollback: Failed to undeploy RT in [%s]: %s", ruleSession.getName(),
                    rule.getUri());
            if (!continueOnError) {
                throw e;
            }
        }

        try {
            ruleLoader.unloadRule(rule.getUri());
        }
        catch (Exception e) {
            logger.log(Level.ERROR, "Rollback: Failed to unload RT in [%s]: %s", ruleSession.getName(),
                    rule.getUri());
            if (!continueOnError) {
                throw e;
            }
        }
    }


    public void rollback()
            throws Exception {
        rollback(false);
    }


    public void rollback(
            boolean continueOnError)
            throws Exception {

        final RuleLoader ruleLoader = findRuleLoader();

        for (final Change<Rule> change : committed) {
            if (null != change.after) {
                removeRule(change.after, ruleLoader, continueOnError);
            }
            if (null != change.before) {
                addRule(change.before, ruleLoader, continueOnError);
            }
        }

    }


}
