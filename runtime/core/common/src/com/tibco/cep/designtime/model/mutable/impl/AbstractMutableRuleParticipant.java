package com.tibco.cep.designtime.model.mutable.impl;


import com.tibco.cep.designtime.model.mutable.MutableRuleParticipant;


/**
 * @author ishaan
 * @version Sep 3, 2004, 3:46:18 PM
 *          <p/>
 *          A RuleParticipant is an Entity that can be referenced in a Rule.
 */
public abstract class AbstractMutableRuleParticipant extends AbstractMutableEntity implements MutableRuleParticipant {


//    public static final ExpandedName REFERRING_RULESETS_NAME = ExpandedName.makeName("referringRuleSets");
//    public static final ExpandedName REFERRING_RULESET_NAME = ExpandedName.makeName("referringRuleSet");
//    public static final ExpandedName REFERRING_RULESET_PATH_NAME = ExpandedName.makeName("path");
//    public static final ExpandedName REFERRING_RULE_NAME = ExpandedName.makeName("referringRule");
//
//    protected LinkedHashMap m_referringRulesMap;


    public AbstractMutableRuleParticipant(DefaultMutableOntology ontology, DefaultMutableFolder folder, String name) {
        super(ontology, folder, name);
//        m_referringRulesMap = new LinkedHashMap();
    }


//    public void clearReferringRules() {
//        m_referringRulesMap.clear();
//    }


//    public void addRule(String ruleSetPath, String name) {
//        if (ModelUtils.IsEmptyString(ruleSetPath)) {
//            return;
//        }
//        if (ModelUtils.IsEmptyString(name)) {
//            return;
//        }
//
//        Collection c = (Collection) m_referringRulesMap.get(ruleSetPath);
//        if (c == null) {
//            c = new LinkedHashSet();
//            m_referringRulesMap.put(ruleSetPath, c);
//        }
//
//        c.add(name);
//        notifyListeners();
//        notifyOntologyOnChange();
//    }


//    public void removeRule(String ruleSetPath, String name) {
//        if (ModelUtils.IsEmptyString(ruleSetPath)) {
//            return;
//        }
//        if (ModelUtils.IsEmptyString(name)) {
//            return;
//        }
//
//        Collection c = (Collection) m_referringRulesMap.get(ruleSetPath);
//        if (c == null) {
//            return;
//        }
//
//        c.remove(name);
//
//        if (c.size() == 0) {
//            m_referringRulesMap.remove(ruleSetPath);
//        }
//        notifyListeners();
//        notifyOntologyOnChange();
//    }


//    public Collection getRuleNames(String ruleSetPath) {
//        Collection c = (Collection) m_referringRulesMap.get(ruleSetPath);
//        return (c != null) ? c : new LinkedHashSet();
//    }


//    public boolean participatesIn(Compilable comp) {
//        if (comp == null) {
//            return false;
//        }
//
//        // TODO: get rid of instanceof stuff
//        if (comp instanceof RuleFunction) {
//            RuleFunction rf = (RuleFunction) comp;
//            return (m_referringRulesMap.containsKey(rf.getFullPath()));
//        }
//
//        Rule r = (Rule) comp;
//        RuleSet rs = r.getRuleSet();
//        String rsPath = rs.getFullPath();
//
//        Collection c = (Collection) m_referringRulesMap.get(rsPath);
//        if (c == null) {
//            return false;
//        }
//
//        String ruleName = comp.getName();
//        return c.contains(ruleName);
//    }


//    public Collection getRuleSetPaths() {
//        return m_referringRulesMap.keySet();
//    }


//    public void setReferringRuleCompilationStatus(int status) {
//        if (m_ontology == null) {
//            return;
//        }
//
//        Collection rsPaths = getRuleSetPaths();
//        Iterator rsPathIt = rsPaths.iterator();
//        while (rsPathIt.hasNext()) {
//            String rsPath = (String) rsPathIt.next();
//
//            Entity e = m_ontology.getEntity(rsPath);
//            if (e instanceof RuleFunction) {
//                final MutableRuleFunction rf = (MutableRuleFunction) e;
//                rf.setCompilationStatus(status);
//            } else {
//                RuleSet rs = m_ontology.getRuleSet(rsPath);
//                if (rs == null) {
//                    continue;
//                }
//
//                Collection ruleNames = getRuleNames(rsPath);
//                Iterator ruleNameIt = ruleNames.iterator();
//                while (ruleNameIt.hasNext()) {
//                    String ruleName = (String) ruleNameIt.next();
//                    DefaultMutableRule rule = (DefaultMutableRule) rs.getRule(ruleName);
//                    if (rule == null) {
//                        continue;
//                    }
//
//                    rule.setCompilationStatus(status);
//                }
//            }
//        }
//    }


//    /**
//     * Notifies all referring Rules that this RuleParticipant's path has changed.
//     *
//     * @param oldPath The oldPath of this RuleParticipant.
//     * @param newPath The newPath of this RuleParticipant.
//     */
//    public void pathChanged(String oldPath, String newPath) throws ModelException {
//        if (m_ontology == null) {
//            return;
//        }
//
//        Collection rsPaths = getRuleSetPaths();
//        Iterator rsPathIt = rsPaths.iterator();
//        while (rsPathIt.hasNext()) {
//            String rsPath = (String) rsPathIt.next();
//            AbstractMutableEntity e = (AbstractMutableEntity) m_ontology.getEntity(rsPath);
//
//            if (e instanceof RuleSet) {
//                RuleSet rs = (RuleSet) e;
//                Collection ruleNames = getRuleNames(rsPath);
//                Iterator ruleNameIt = ruleNames.iterator();
//                while (ruleNameIt.hasNext()) {
//                    String ruleName = (String) ruleNameIt.next();
//                    DefaultMutableRule rule = (DefaultMutableRule) rs.getRule(ruleName);
//                    if (rule == null) {
//                        continue;
//                    }
//
//                    rule.participantPathChanged(oldPath, newPath);
//                }
//            } else if (e instanceof RuleFunction) {
//                DefaultMutableRuleFunction drf = (DefaultMutableRuleFunction) e;
//                drf.participantPathChanged(oldPath, newPath);
//            }
//
//            if (e != null) {
//                e.notifyListeners();
//                e.notifyOntologyOnChange();
//            }
//        }
//    }


//    protected XiNode rulesToXiNode() {
//        XiFactory factory = XiFactoryFactory.newInstance();
//        XiNode referringRuleSetsNode = factory.createElement(REFERRING_RULESETS_NAME);
//
//        Collection ruleSetPaths = getRuleSetPaths();
//        for (Iterator ruleSetPathIt = new TreeSet(ruleSetPaths).iterator(); ruleSetPathIt.hasNext(); ) {
//            String ruleSetPath = (String) ruleSetPathIt.next();
//            XiNode referringRuleSetNode = referringRuleSetsNode.appendElement(REFERRING_RULESET_NAME);
//            referringRuleSetNode.setAttributeStringValue(REFERRING_RULESET_PATH_NAME, ruleSetPath);
//
//            Collection ruleNames = getRuleNames(ruleSetPath);
//            for (Iterator ruleNamesIt = new TreeSet(ruleNames).iterator(); ruleNamesIt.hasNext(); ) {
//                String name = (String) ruleNamesIt.next();
//
//                XiNode ruleNameNode = referringRuleSetNode.appendElement(REFERRING_RULE_NAME);
//                ruleNameNode.setStringValue(name);
//            }
//        }
//
//        return referringRuleSetsNode;
//    }


//    protected static void rulesFromXiNode(AbstractMutableRuleParticipant arp, XiNode referringRuleSetsNode) {
//        if (arp == null) {
//            return;
//        }
//        if (referringRuleSetsNode == null) {
//            return;
//        }
//
//        Iterator referringRuleSetsIt = referringRuleSetsNode.getChildren();
//        while (referringRuleSetsIt.hasNext()) {
//            XiNode referringRuleSetNode = (XiNode) referringRuleSetsIt.next();
//            String path = referringRuleSetNode.getAttributeStringValue(REFERRING_RULESET_PATH_NAME);
//
//            LinkedHashSet names = new LinkedHashSet();
//            Iterator ruleNameNodes = referringRuleSetNode.getChildren();
//            while (ruleNameNodes.hasNext()) {
//                XiNode ruleNameNode = (XiNode) ruleNameNodes.next();
//                String name = ruleNameNode.getStringValue();
//                names.add(name);
//            }
//
//            arp.m_referringRulesMap.put(path, names);
//        }
//    }
}
