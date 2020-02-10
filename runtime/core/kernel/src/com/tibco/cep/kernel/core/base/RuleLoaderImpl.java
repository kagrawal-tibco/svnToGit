package com.tibco.cep.kernel.core.base;


import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.tibco.cep.kernel.helper.Format;
import com.tibco.cep.kernel.model.knowledgebase.RuleLoader;
import com.tibco.cep.kernel.model.knowledgebase.SetupException;
import com.tibco.cep.kernel.model.rule.InnerClassRule;
import com.tibco.cep.kernel.model.rule.InvalidRuleException;
import com.tibco.cep.kernel.model.rule.Rule;
import com.tibco.cep.kernel.service.ResourceManager;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.Logger;

/*
* User: nleong
* Date: Apr 17, 2006
* Time: 6:30:58 PM
*/


public class RuleLoaderImpl
        implements RuleLoader {


    final WorkingMemoryImpl workingMemory;
    final Map<Object, RuleInfo> ruleInfoMap;     //Rule uri or rule Class to RuleInfo map
    final Logger logger;


    public RuleLoaderImpl(
            WorkingMemoryImpl wm) {
        this.workingMemory = wm;
        this.ruleInfoMap = new HashMap<Object, RuleInfo>();
        this.logger = wm.getLogManager().getLogger(RuleLoaderImpl.class);
    }


    synchronized public Rule getRule(
            Class ruleClass) {

        if (ruleClass == null) {
            return null;
        }
        final RuleInfo info = this.ruleInfoMap.get(ruleClass);
        if (info == null) {
            return null;
        }
        return info.rule;
    }


    synchronized public Rule getRule(
            String ruleUri) {

        if (ruleUri == null) {
            return null;
        }
        final RuleInfo info = this.ruleInfoMap.get(ruleUri);
        if (info == null) {
            return null;
        }
        return info.rule;
    }


    synchronized public Set<Rule> getRules() {
        final Set<Rule> ret = new HashSet<Rule>();
        for (RuleInfo ruleInfo : this.ruleInfoMap.values()) {
            ret.add(ruleInfo.rule);
        }
        return ret;
    }


    /**
     * Get all the ruleSet that is deployed in working memory
     *
     * @return Set of ruleSet that is deployed in working memory
     */
    synchronized public Set<Rule> getDeployedRules() {
        final Set<Rule> ret = new HashSet<Rule>();
        for (RuleInfo ruleInfo : this.ruleInfoMap.values()) {
            if (ruleInfo.deployed) {
                ret.add(ruleInfo.rule);
            }
        }
        return ret;
    }


    synchronized public Rule loadRule(
            Rule ruleObj)
            throws SetupException, InvalidRuleException {
        return this.loadRule(ruleObj, true);
    }


    synchronized private Rule loadRule(
            Object ruleClassOrRuleObject,
            boolean traceOut)
            throws SetupException, InvalidRuleException {

        final Object ruleId = (ruleClassOrRuleObject instanceof Class)
                ? ruleClassOrRuleObject : ((Rule) ruleClassOrRuleObject).getUri();

        RuleInfo ruleInfo = this.ruleInfoMap.get(ruleId);
        if (ruleInfo != null) {
            return ruleInfo.rule;
        }

        Rule rule;
        if (ruleClassOrRuleObject instanceof Class) {
            try {
                rule = (Rule) ((Class) ruleClassOrRuleObject).newInstance();
            } catch (InstantiationException e) {
                throw new SetupException(ResourceManager.formatString("ruleloader.rule.createRuleError", ruleId));
            } catch (IllegalAccessException e) {
                throw new SetupException(ResourceManager.formatString("ruleloader.rule.createRuleError", ruleId));
            }
        } else {
            rule = (Rule) ruleClassOrRuleObject;
        }

        ruleInfo = new RuleInfo(rule);
        this.ruleInfoMap.put(rule.getClass(), ruleInfo);
        this.ruleInfoMap.put(rule.getUri(), ruleInfo);

        //todo - hack to set state machine rule
        ruleInfo.smRule = (rule instanceof InnerClassRule);

        if (logger.isEnabledFor(Level.DEBUG)) {
            this.logger.log(Level.DEBUG, ResourceManager.formatString("ruleloader.rule.loaded", rule.getUri()));
        }

        return rule;
    }


    synchronized public Rule unloadRule(
            String ruleName)
            throws SetupException {
        return unloadRule(ruleName, true);
    }


    synchronized private Rule unloadRule(
            Object ruleClassOrRuleName,
            boolean traceOut)
            throws SetupException {

        final RuleInfo ruleInfo = this.ruleInfoMap.remove(ruleClassOrRuleName);
        if (ruleInfo == null) {
            throw new SetupException(ResourceManager.formatString("ruleloader.rule.unload.unknown", ruleClassOrRuleName));
        }

        if (ruleInfo.deployed) {
            synchronized (this.workingMemory) {
                this.workingMemory.removeRule(ruleInfo.rule);
            }
        }
        if (logger.isEnabledFor(Level.DEBUG)) {
            this.logger.log(Level.DEBUG,ResourceManager.formatString("ruleloader.rule.unloaded", ruleClassOrRuleName));
        }

        return ruleInfo.rule;
    }


    synchronized public Rule loadRule(
            Class ruleClazz)
            throws SetupException, InvalidRuleException {
        if (!Rule.class.isAssignableFrom(ruleClazz)) {
            throw new InvalidRuleException(
                    ResourceManager.formatString("ruleloader.rule.load.invalid", ruleClazz.getName()));
        } else {
            return this.loadRule(ruleClazz, true);
        }
    }


    synchronized public Rule unloadRule(Class ruleClass)
            throws SetupException {
        return unloadRule(ruleClass, true);
    }


    synchronized public Rule deployModifiedRuleToWM(
            Class ruleClass,
            boolean traceOut)
            throws SetupException, InvalidRuleException {

        synchronized (workingMemory) {
            this.unloadRule(ruleClass, false);
            this.loadRule(ruleClass, false);
        }
        
        final Rule rule = this.getRule(ruleClass);
        if (logger.isEnabledFor(Level.DEBUG)) {
            this.logger.log(Level.DEBUG, ResourceManager.formatString("ruleloader.rule.modified", rule.getUri()));
        }

        return rule;
    }


    synchronized public Rule deployModifiedRuleToWM(Class ruleClass)
            throws SetupException, InvalidRuleException {
        return deployModifiedRuleToWM(ruleClass, true);
    }


    synchronized public Rule deployRuleToWM(
            String ruleUri)
            throws SetupException {
        if (ruleUri == null) {
            return null;
        }

        RuleInfo info = this.ruleInfoMap.get(ruleUri);
        if (info == null) {
            throw new SetupException(
                    ResourceManager.formatString("ruleloader.rule.deployNonExist", ruleUri));
        }
        if (!info.deployed) {
            info.rule.deactivate();
            synchronized (this.workingMemory) {
                this.workingMemory.addRule(info.rule);
                info.rule.activate();
            }
            info.deployed = true;
            this.logger.log(Level.DEBUG,ResourceManager.formatString("ruleloader.rule.deployed", ruleUri));
        }
        return info.rule;
    }


    synchronized public boolean deployRulesToWM(
            Set<String> ruleUris)
            throws SetupException {
        boolean changes = false;
        final Set<Object> deleteSet = new HashSet<Object>(this.ruleInfoMap.keySet());
        final Set<String> newSet = new HashSet<String>();
        for (String ruleUri : ruleUris) {
            final RuleInfo info = this.ruleInfoMap.get(ruleUri);
            if (info == null) {
                newSet.add(ruleUri);
            } else if (info.deployed) {
                //already deployed
                deleteSet.remove(ruleUri);
                deleteSet.remove(info.rule.getClass());
            } else {
                //not deployed
                newSet.add(ruleUri);
                deleteSet.remove(ruleUri);
                deleteSet.remove(info.rule.getClass());
            }
        }

        //deploy new rules
        for (String ruleUri : newSet) {
            this.deployRuleToWM(ruleUri);
            changes = true;
        }

        //undeploy rules
        for (Object ruleUriOrClass : deleteSet) {
            final RuleInfo info = this.ruleInfoMap.get(ruleUriOrClass);
            if (info.deployed && !info.smRule) {
                final String ruleUri = (ruleUriOrClass instanceof String)
                        ? (String) ruleUriOrClass
                        : this.getRuleUri((Class) ruleUriOrClass);
                this.undeployRuleFromWM(ruleUri);
                changes = true;
            }
        }

        return changes;
    }


    synchronized public Rule undeployRuleFromWM(
            String ruleUri)
            throws SetupException {

        final RuleInfo info = this.ruleInfoMap.get(ruleUri);
        if (info == null) {
            throw new SetupException(
                    ResourceManager.formatString("ruleloader.rule.undeployNonExist", ruleUri));
        }
        if (info.deployed) {
            synchronized (workingMemory) {
                this.workingMemory.removeRule(info.rule);
            }
            info.deployed = false;
            this.logger.log(Level.INFO, ResourceManager.formatString("ruleloader.rule.undeployed", ruleUri));
        }
        return info.rule;
    }


    synchronized public Rule activateRule(
            String ruleUri) {

        if (ruleUri == null) {
            return null;
        }
        final RuleInfo info = this.ruleInfoMap.get(ruleUri);
        if (info == null) {
            return null;
        }
        if (!info.deployed) {
            throw new RuntimeException(ResourceManager.formatString("ruleloader.rule.activate.undeployed", ruleUri));
        }

        synchronized (workingMemory) {
            info.rule.activate();
        }

        this.logger.log(Level.DEBUG,ResourceManager.formatString("ruleloader.rule.activated", ruleUri));

        return info.rule;
    }


    synchronized public Rule deactivateRule(
            String ruleUri) {

        if (ruleUri == null) {
            return null;
        }
        final RuleInfo info = this.ruleInfoMap.get(ruleUri);
        if (info == null) {
            return null;
        }
        if (!info.deployed) {
            throw new RuntimeException(ResourceManager.formatString("ruleloader.rule.deactivate.undeployed", ruleUri));
        }
        synchronized (workingMemory) {
            info.rule.deactivate();
        }
        this.logger.log(Level.INFO,ResourceManager.formatString("ruleloader.rule.deactivated", ruleUri));

        return info.rule;
    }


    public String getRuleUri(
            Class ruleClass) {

        String ruleSetURI;
        final Package rulePackage = ruleClass.getPackage();
        if (InnerClassRule.class.isAssignableFrom(ruleClass)) {  //state machine rule
            if (rulePackage == null) {
                ruleSetURI = ruleClass.getName().substring(0, ruleClass.getName().indexOf('$'));
            } else {
                int lenPkgName = rulePackage.getName().length() + 1;
                ruleSetURI = ruleClass.getName().substring(0, ruleClass.getName().indexOf('$', lenPkgName));
            }
        } else {  //regular rule
            if (rulePackage != null) {
                ruleSetURI = rulePackage.getName();
            } else {
                ruleSetURI = "";
            }
        }
        if (ruleSetURI.startsWith(Format.CODEGEN_PREFIX)) {
            ruleSetURI = ruleSetURI.substring(Format.CODEGEN_PREFIX.length());
        }
        ruleSetURI = ruleSetURI.replace('.', '/');
        
        if (!ruleSetURI.endsWith("/")) ruleSetURI += "/";

        return ruleSetURI + ruleClass.getSimpleName();
    }


    static private class RuleInfo {


        Rule rule;
        boolean deployed;
        boolean smRule;


        RuleInfo(
                Rule rule) {
            this.rule = rule;
            this.deployed = false;
            this.smRule = false;
        }


        void deploy() {
            this.deployed = true;
        }
    }

}

