package com.tibco.cep.designtime.model.rule.mutable.impl;


import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;

import com.tibco.cep.designtime.model.BEModelBundle;
import com.tibco.cep.designtime.model.Folder;
import com.tibco.cep.designtime.model.ModelException;
import com.tibco.cep.designtime.model.ModelUtils;
import com.tibco.cep.designtime.model.mutable.MutableFolder;
import com.tibco.cep.designtime.model.mutable.MutableOntology;
import com.tibco.cep.designtime.model.mutable.impl.AbstractMutableEntity;
import com.tibco.cep.designtime.model.mutable.impl.DefaultMutableFolder;
import com.tibco.cep.designtime.model.mutable.impl.DefaultMutableOntology;
import com.tibco.cep.designtime.model.rule.Rule;
import com.tibco.cep.designtime.model.rule.RuleSetEx;
import com.tibco.cep.designtime.model.rule.RulesetEntry;
import com.tibco.cep.designtime.model.rule.Symbol;
import com.tibco.cep.designtime.model.rule.mutable.MutableRule;
import com.tibco.cep.designtime.model.rule.mutable.MutableRuleSet;
import com.tibco.cep.designtime.model.rule.mutable.MutableRuleSetEx;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.datamodel.XiFactory;
import com.tibco.xml.datamodel.XiNode;
import com.tibco.xml.datamodel.helpers.XiChild;
import com.tibco.xml.schema.build.MutableComponentFactoryTNS;
import com.tibco.xml.schema.impl.DefaultComponentFactory;


/**
 * User: ishaan
 * Date: Jul 20, 2004
 * Time: 8:35:23 PM
 */
public class DefaultMutableRuleSet extends AbstractMutableEntity implements MutableRuleSet, MutableRuleSetEx {


    public static final ExpandedName RULES_NAME = ExpandedName.makeName("rules");

    static MutableComponentFactoryTNS cf = DefaultComponentFactory.getTnsAwareInstance();

    protected LinkedHashMap m_rules;


    public DefaultMutableRuleSet(DefaultMutableOntology ontology, DefaultMutableFolder folder, String name) {
        super(ontology, folder, name);
        m_rules = new LinkedHashMap();
    }


    public void setOntology(MutableOntology ontology) {
        super.setOntology(ontology);
        Iterator it = m_rules.values().iterator();
        while (it.hasNext()) {
            final MutableRule rule = (MutableRule) it.next();
            rule.setOntology(ontology);
        }
    }


    public void setFolderPath(String fullPath) throws ModelException {
        super.setFolderPath(fullPath);
        notifyListeners();
    }


    public void setFolder(MutableFolder folder) throws ModelException {
        super.setFolder(folder);
        notifyListeners();
    }


    /**
     * Notify the rules that our path, and hence their internal rep has changed.
     */
    public void pathChanged(String oldPath, String newPath) {
        Iterator it = m_rules.values().iterator();
        while (it.hasNext()) {
            DefaultMutableRule dr = (DefaultMutableRule) it.next();
            String ruleName = dr.getName();
            dr.pathChanged(oldPath, ruleName, newPath, ruleName);
        }
    }


    public List getRules() {
        final List l = new ArrayList(m_rules.size());
        for (Iterator it = m_rules.values().iterator(); it.hasNext();) { // The Iterator guarantees the order.
            l.add(it.next());
        }
        return l;
    }


    public RulesetEntry getRule(String name) {
        return (Rule) m_rules.get(name);
    }


    public void deleteRule(String name) {
        DefaultMutableRule rule = removeDefaultRule(name);
        if (rule != null) {
            rule.delete();
            notifyListeners();
            notifyOntologyOnChange();
        }
    }


    public void clear() {
        m_rules.clear();
        notifyListeners();
        notifyOntologyOnChange();
    }


    public MutableRule createRule(String name, boolean renameOnConflict, boolean isFunction) throws ModelException {
        name = makeName(name, renameOnConflict);
        DefaultMutableRule dr = new DefaultMutableRule(m_ontology, this, name, isFunction);
        addRule(name, dr);
        return dr;
    }


    public MutableRule createRule(String name, boolean renameOnConflict, int ruleType) throws ModelException {
        if (ruleType == RuleSetEx.RULE_DEFAULT_TYPE) {
            return createRule(name, renameOnConflict, false);
        } else if (ruleType == RuleSetEx.RULE_FUNCTION_TYPE) {
            return createRule(name, renameOnConflict, true);
        }
        //else if (ruleType == RuleSetEx.RULE_DECISION_TABLE_TYPE) {
        //    return createDecisionTable(name, renameOnConflict);
        //}
        else {
            throw new ModelException("Rule type :" + ruleType + " not supported");
        }
    }

    //public Rule createDecisionTable(String name, boolean renameOnConflict) throws ModelException {
    //
    //    String dtName = makeName(name, renameOnConflict);
    //    DefaultRule dr = new DefaultDecisionTable(m_ontology, this, dtName);
    //    addRule(dtName, dr);
    //    return dr;
    //
    //}


    protected void addRule(String name, DefaultMutableRule r) throws ModelException {

        addDefaultRule(r);
        //if (getOntology() != null) {
        //    ((DefaultOntology) getOntology()).notifyEntityAdded(r);
        //}

        notifyListeners();
        notifyOntologyOnChange();

    }


    protected String makeName(String name, boolean renameOnConflict) throws ModelException {

        BEModelBundle bundle = BEModelBundle.getBundle();

        if (ModelUtils.IsEmptyString(name)) {
            String msg = bundle.getString(AbstractMutableEntity.NAME_CONFLICT_KEY);
            throw new ModelException(msg);
        }

        if (renameOnConflict) {
            String baseName = name;
            for (int i = 1; m_rules.containsKey(baseName); i++) {
                baseName = name + "_" + i;
            }
            name = baseName;
        } else if (m_rules.containsKey(name)) {
            String msg = bundle.formatString(DefaultMutableRule.NAME_CONFLICT_KEY, name, getFullPath());
            throw new ModelException(msg);
        }
        return name;

    }


    public void delete() {
        super.delete();
        Collection c = getRules(); /** Creates a copy */
        Iterator it = c.iterator();
        while (it.hasNext()) {
            Rule r = (Rule) it.next();
            deleteRule(r.getName());
        }
    }


    /**
     * Start methods used by default implementation
     */
    public void addDefaultRule(DefaultMutableRule rule) throws ModelException {
        if (rule == null) {
            return;
        }
        /** TODO: USE BUNDLE */

        DefaultMutableRule dr = (DefaultMutableRule) m_rules.get(rule.getName());
        if (rule.equals(dr)) {
            return;
        }

        if (m_rules.containsKey(rule.getName())) {
            BEModelBundle bundle = BEModelBundle.getBundle();
            String msg = bundle.formatString(DefaultMutableRule.NAME_CONFLICT_KEY, rule.getName(), getFullPath());
            throw new ModelException(msg);
        }
        m_rules.put(rule.getName(), rule);
    }


    public DefaultMutableRule removeDefaultRule(String name) {
        DefaultMutableRule dr = (DefaultMutableRule) m_rules.remove(name);

        return dr;
    }


    public XiNode toXiNode(
            XiFactory factory) {
        return this.toXiNode(factory, "ruleset");
    }


    public XiNode toXiNode(
            XiFactory factory,
            String typeName) {
        final XiNode root = super.toXiNode(factory, typeName);
        root.removeAttribute(GUID_NAME);
        
        String name = getName();
        String folderPath = getFolderPath();
        String description = getDescription();

        root.setAttributeStringValue(NAME_NAME, name);
        root.setAttributeStringValue(FOLDER_NAME, folderPath);
        root.setAttributeStringValue(DESCRIPTION_NAME, description);

        final XiNode rules = root.appendElement(RULES_NAME);
        for (Iterator it = m_rules.values().iterator(); it.hasNext();) {
            final DefaultMutableRule rule = (DefaultMutableRule) it.next();
            rules.appendChild(rule.toXiNode(factory));
        }

        return root;
    }


    public static DefaultMutableRuleSet createDefaultRuleSetFromNode(
            XiNode root)
            throws ModelException {

        final String name = root.getAttributeStringValue(NAME_NAME);
        final String folderPath = root.getAttributeStringValue(FOLDER_NAME);
        final DefaultMutableFolder rootFolder = new DefaultMutableFolder(null, null,
                String.valueOf(Folder.FOLDER_SEPARATOR_CHAR));
        final DefaultMutableFolder folder = DefaultMutableOntology.createFolder(rootFolder, folderPath, false);

        final DefaultMutableRuleSet ruleSet = new DefaultMutableRuleSet(null, folder, name);
        populateRuleSetFromNode(root, ruleSet);
        return ruleSet;
    }


    protected static void populateRuleSetFromNode(
            XiNode root,
            DefaultMutableRuleSet drs)
            throws ModelException {

        String description = root.getAttributeStringValue(DESCRIPTION_NAME);
        drs.setDescription(description);

        XiNode rulesNode = XiChild.getChild(root, RULES_NAME);
        Iterator rulesIt = rulesNode.getChildren();
        while (rulesIt.hasNext()) {
            XiNode ruleNode = (XiNode) rulesIt.next();
            if (RuleSetEx.DEFAULT_RULE_ELEMENT_NAME.equals(ruleNode.getName())) {
                DefaultMutableRule.createDefaultRuleFromNode(ruleNode, drs);
            } else {
                throw new ModelException("UnSupported Rule type - " + ruleNode.getName());
            }
        }
    }


    public Collection getEntities() {
        HashMap map = new HashMap();

        Iterator i = this.getRules().iterator();
        while (i.hasNext()) {
            Rule r = (Rule) i.next();
            for (Iterator it = r.getDeclarations().values().iterator(); it.hasNext();) {
                final Symbol symbol = (Symbol) it.next();
                final String entityPath = symbol.getType();
                map.put(entityPath, getOntology().getEntity(entityPath));
            }

        }
        return map.values();
    }


//    public static void main(String args[]) {
//        try {
//
//            DefaultMutableRuleSet set = new DefaultMutableRuleSet(null, new DefaultMutableFolder(null, null, "/"), "abc");
//            set.createRule("dt", true, RuleSetEx.RULE_DECISION_TABLE_TYPE);
//        }
//        catch (Exception e) {
//
//        }
//    }
    /*** End methods used by default implementation ***/
}


