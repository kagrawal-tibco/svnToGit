//package com.tibco.cep.kernel.model.rule.impl;
//
//import com.tibco.cep.kernel.model.rule.RuleSet;
//import com.tibco.cep.kernel.model.rule.Rule;
//import com.tibco.cep.kernel.model.rule.InvalidRuleException;
//
//import java.util.HashSet;
//import java.util.Iterator;
//import java.util.Collection;
//
///**
// * Created by IntelliJ IDEA.
// * User: nleong
// * Date: Apr 13, 2006
// * Time: 4:05:49 PM
// * To change this template use File | Settings | File Templates.
// */
//final public class RuleSetImpl implements RuleSet {
//
//    String     m_uri;
//    boolean    m_active;
//    HashSet    m_rules;
//
//    static public RuleSet createRuleSet(String URI) {
//        return new RuleSetImpl(URI);
//    }
//
//    private RuleSetImpl(String URI) {
//        m_uri       = URI;
//        m_active    = false;
//        m_rules     = new HashSet();
//    }
//
//    public String getURI() {
//        return m_uri;
//    }
//
//    public boolean isActive() {
//        return m_active;
//    }
//
//    public void activate() {
//        Iterator ite = m_rules.iterator();
//        while(ite.hasNext()) {
//            RuleImpl rule = (RuleImpl) ite.next();
//            rule.activate();
//        }
//        m_active = true;
//    }
//
//    public void deactivate() {
//        m_active = false;
//        Iterator ite = m_rules.iterator();
//        while(ite.hasNext()) {
//            RuleImpl rule = (RuleImpl) ite.next();
//            rule.deactivate();
//        }
//    }
//
//    public Collection rules() {
//        return m_rules;
//    }
//
////    public void start() {
////        activate();
////    }
//
//    public void addRule(Rule rule) throws InvalidRuleException {
//        if(rule instanceof RuleImpl) {
//            ((RuleImpl)rule).validate();
//        }
//        m_rules.add(rule);
//    }
//
//    public void removeRule(Rule rule) {
//        m_rules.remove(rule);
//    }
//
//}
