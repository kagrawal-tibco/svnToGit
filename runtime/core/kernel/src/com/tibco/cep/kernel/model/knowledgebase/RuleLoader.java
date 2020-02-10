package com.tibco.cep.kernel.model.knowledgebase;


import java.util.Set;

import com.tibco.cep.kernel.model.rule.InvalidRuleException;
import com.tibco.cep.kernel.model.rule.Rule;

/*
 * Created by IntelliJ IDEA.
 * User: nleong
 * Date: Apr 17, 2006
 * Time: 6:31:52 PM
 * To change this template use File | Settings | File Templates.
 */

/**
 * RuleLoader manages all the rules for a working memory.  It responsible to load rules, deploy ruleSets,
 * and activate ruleSets for the working memory.
 *
 * There are 2 ways to load a rule into a working memory.
 *
 * 1. Load by Class - Using this method, RuleLoader will create a ruleObject of this rule class type for loading
 *    into the working memory.  If this method is used, only one rule object of this type will be loaded.  The
 *    rule class is unique for a RuleLoader.
 *
 * 2. Load by Rule Object - Using this method, RuleLoader will use this rule object to load into the working memory.
 *    It doesn't require uniqueness of the rule class but the name of the rule has to be unique in this case.
 * @version 2.0.0
 * @since 2.0.0
 */
public interface RuleLoader {

    /**
     * Get the rule object of type ruleClass that is loaded in the working memory
     * @param ruleClass this should be unique for a given working memory
     * @return the rule object of type ruleClass
     * @.category public-api
     */
    Rule getRule(Class ruleClass);

//    /**
//     * Get the ruleSet given the ruleSetURI
//     * @param ruleSetURI
//     * @return the ruleSet of URI set to ruleSetURI
//     * @.category public-api
//     */
//    RuleSet getRuleSet(String ruleSetURI);

    /**
     * Get the rule given the URI
     * @param ruleUri String URI of the rule
     * @return Rule
     * @.category public-api
     */
    Rule getRule(String ruleUri);

//    /**
//     * Get all the ruleSets loaded including the deployed and non-deployed ruleSets in the working memory
//     * @return Set of ruleSets loaded in the working memory
//     * @.category public-api
//     */
//    Set getRuleSets();
//
    /**
     * Get all the rules loaded including the deployed and non-deployed rules in the working memory
     * @return Set of Rule's loaded in the working memory
     * @.category public-api
     */
    Set<Rule> getRules();

//    /**
//     * Get all the ruleSet that is deployed in working memory
//     * @return Set of ruleSet that is deployed in working memory
//     * @.category public-api
//     */
//    Set getDeployedRuleSets();

    /**
     * Get all the rules that are deployed in working memory
     * @return Set of Rule that are deployed in working memory
     * @.category public-api
     */
    Set<Rule> getDeployedRules();

//    /**
//     * Add the ruleObj to the ruleSet of ruleSetURI into the working memory.  This is the load by rule object method.
//     * The rule name has to be unique for the working memory.  If a rule object is loaded by this method, it has to be
//     * unloaded using the removeRule(String ruleName) method.
//     *
//     * @param ruleSetURI the URI of the ruleSet
//     * @param ruleObj the rule object to be added into the working memory
//     * @return the RuleSet for which the rule is added to
//     * @throws SetupException
//     * @.category public-api
//     */
//    RuleSet addRule(String ruleSetURI, Rule ruleObj) throws SetupException, InvalidRuleException;

    /**
     * Add the Rule into the working memory.  This is the load by rule object method.
     * The rule URI has to be unique for the working memory.  If a rule object is loaded by this method, it has to be
     * unloaded using the {@link #unloadRule(String ruleUri)} method.
     *
     * @param ruleObj Rule to be added into the working memory
     * @return Rule added.
     * @throws com.tibco.cep.kernel.model.rule.InvalidRuleException if the Rule is not valid.
     * @throws SetupException upon problem.
     * @.category public-api
     */
    Rule loadRule(Rule ruleObj) throws SetupException, InvalidRuleException;

    /**
     * Remove the rule from the working memory given the rule name.  This can be used to unload/remove the rule object
     * that is loaded by the addRule(String ruleSetURI, Rule ruleObj) method.
     *
     * @param ruleName Name of the rule to be removed
     * @return the removed rule object
     * @throws SetupException
     * @.category public-api
     */
    Rule unloadRule(String ruleName) throws SetupException;

    /**
     * Create a rule object based on the input ruleClass and add the rule obj into the working memory.
     * Only of rule object of type ruleClass will be created and it is unique for the working memory.
     * RuleSet will be generated automatically based on the class of the rule using the getRuleSetURI(Class ruleClass)
     * method of this interface.
     * This is the load by rule class method.  If a rule is loaded by this method, it has to be unloaded by using the
     * removeRule(Class ruleClass) method.
     *
     * @param ruleClass Class of the rule object
     * @return the RuleSet for which the rule object is added to
     * @throws com.tibco.cep.kernel.model.rule.InvalidRuleException if the Class is not valid.
     * @throws SetupException upon problem.
     * @.category public-api
     */
    Rule loadRule(Class ruleClass) throws SetupException, InvalidRuleException;

    /**
     * Remove the rule from the working memory given the class of the rule.  This can be used to unload/remove the rule
     * object that is loaded by addRule(Class ruleClasse) method.
     *
     * @param ruleClass Class of the rule to be removed
     * @return the removed rule object
     * @throws SetupException
     * @.category public-api
     */
    Rule unloadRule(Class ruleClass) throws SetupException;

    /**
     * Modify the rule in the working memory given the class of the rule.  This can be used to modify the rule
     * that is loaded by addRule(Class ruleClasse) method.
     *
     * @param ruleClass Class of the rule to be removed
     * @return the modified rule object
     * @throws SetupException
     * @.category public-api
     * @throws com.tibco.cep.kernel.model.rule.InvalidRuleException if the Class is not valid.
     */
    Rule deployModifiedRuleToWM(Class ruleClass) throws SetupException, InvalidRuleException;

//    /**
//     * Deploy a ruleSet into the working memory.  The ruleSet will be deactivated first before it is being deployed
//     * and then activated after the deployment.  Do nothing if the ruleSet is already deployed.  i.e. this won't
//     * activate a deactivated ruleSet if it is already been deployed.
//     * @param ruleSetURI
//     * @return the ruleSet of URI set to ruleSetURI
//     * @throws SetupException if the ruleSet does not exist
//     * @.category public-api
//     */
//    RuleSet deployRuleSet(String ruleSetURI) throws SetupException;

    /**
     * Deploy a rule into the working memory.  The rule will be deactivated first before it is being deployed
     * and then activated after the deployment.  Do nothing if the rule is already deployed.  i.e. this won't
     * activate a deactivated rule if it is already been deployed.
     * @param ruleUri String URI of the rule
     * @return Rule
     * @throws SetupException if the rule does not exist
     * @.category public-api
     */
    Rule deployRuleToWM(String ruleUri) throws SetupException;

//    /**
//     * Deploy a collection of ruleSet into the working memory.  The following actions will be performed depending on
//     * the status of the ruleSet
//     *      - If a ruleSet is already deployed, do nothing.
//     *      - If it is a new ruleSet, the ruleSet will be activated after it is deployed.
//     *      - If a ruleSet is not in passed in collection, it will be undeployed.
//     * @param ruleSetURIs, a collection of ruleSetURI to be deployed
//     * @return true if there is any changes - any newly deployed RuleSet or undeployed RuleSet
//     * @throws SetupException if some ruleSet does not exist
//     * @.category public-api
//     */
//    boolean deploy(Collection ruleSetURIs) throws SetupException;

    /**
     * Deploy a collection of rules into the working memory.  The following actions will be performed depending on
     * the status of the rule:
     *      - If a rule is already deployed, do nothing.
     *      - If it is a new rule, the rule will be activated after it is deployed.
     *      - If a rule is not in passed in collection, it will be undeployed.
     * @param ruleUris Set of String URIs of rules to be deployed
     * @return true if there is any changes - any newly deployed Rule or undeployed Rule
     * @throws SetupException if some rule does not exist
     * @.category public-api
     */
    boolean deployRulesToWM(Set<String> ruleUris) throws SetupException;

//    /**
//     * Undeploy a ruleSet from the working memory.  Do nothing if the ruleSet is already undeployed.
//     * @param ruleSetURI
//     * @return the ruleSet of URI set to ruleSetURI
//     * @throws SetupException if the ruleSet does not exist
//     * @.category public-api
//     */
//    RuleSet undeployRuleSet(String ruleSetURI) throws SetupException;

    /**
     * Undeploy a rule from the working memory.  Do nothing if the rule is already undeployed.
     * @param ruleUri String URI fo the rule
     * @return Rule undeployed.
     * @throws SetupException if the rule does not exist
     * @.category public-api
     */
    Rule undeployRuleFromWM(String ruleUri) throws SetupException;

//    /**
//     * A ruleSet can be activated only if it is deployed in the working memory
//     * @param ruleSetURI
//     * @return the deactivated ruleSet.  Null there is no ruleSet of ruleSetURI.
//     * @.category public-api
//     */
//    RuleSet activateRuleSet(String ruleSetURI);

    /**
     * A rule can be activated only if it is deployed in the working memory
     * @param ruleUri String URI of the rule.
     * @return Rule deactivated.  Null if there is no rule at the given URI.
     * @.category public-api
     */
    Rule activateRule(String ruleUri);

//    /**
//     * A ruleSet can be deactivated only if it is deployed in the working memory
//     * @param ruleSetURI
//     * @return the activated ruleSet.  Null there is no ruleSet of ruleSetURI.
//     * @.category public-api
//     */
//    RuleSet deactivateRuleSet(String ruleSetURI);

    /**
     * A rule can be deactivated only if it is deployed in the working memory
     * @param ruleUri String URI of the Rule.
     * @return Rule deactivated. Null if there is no Rule at the given URI.
     * @.category public-api
     */
    Rule deactivateRule(String ruleUri);

//    /**
//     * Get the corresponding RuleSetURI given the rule class
//     * @param ruleClass for getting the ruleSetURI
//     * @return the corresponding RuleSetURI
//     * @.category public-api
//     */
//    String getRuleSetURI(Class ruleClass);

    /**
     * Get the Rule URI given the rule class
     * @param ruleClass Class of the Rule
     * @return String URI
     * @.category public-api
     */
    String getRuleUri(Class ruleClass);

}
